package wgu.edu.BrinaBright.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wgu.edu.BrinaBright.Entities.*;
import wgu.edu.BrinaBright.Enums.MeasureUnit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

import static wgu.edu.BrinaBright.Enums.BaseFee.sewer;
import static wgu.edu.BrinaBright.Enums.BaseFee.water;

@Service
@RequiredArgsConstructor
public class RateCalculatorService {

    private static final int SCALE_MONEY = 2;
    private static final RoundingMode ROUND_MONEY = RoundingMode.HALF_UP;



    public BigDecimal calculateWaterCharge(Municipality m, int usageGal) {
        WaterRate rate = m.getWaterRates().stream().findFirst().orElse(null);
        if (rate == null) return BigDecimal.ZERO.setScale(SCALE_MONEY, ROUND_MONEY);

        BigDecimal total = nvl(rate.getBaseRate());
        int baseGal = rate.getBaseGal() != null ? Math.max(rate.getBaseGal(), 0) : 0;
        int overageGal = Math.max(usageGal - baseGal, 0);

        if (!Boolean.TRUE.equals(rate.getFixedRate()) && overageGal > 0) {
            List<RateVariance> variances = m.getRateVariances().stream()
                    .filter(rv -> rv.getWaterPPU() != null)
                    .sorted(Comparator.comparingInt(rv -> nvlInt(rv.getWaterRangeMin())))
                    .toList();

            total = total.add(calculateTieredWaterCharge(variances, usageGal, overageGal));
        }

        total = total.add(calculateWaterBaseFees(m));
        return total.setScale(SCALE_MONEY, ROUND_MONEY);
    }

    public BigDecimal calculateSewerCharge(Municipality m, int usageGal) {
        SewerRate rate = m.getSewerRates().stream().findFirst().orElse(null);
        if (rate == null) return calculateSewerBaseFees(m).setScale(SCALE_MONEY, ROUND_MONEY);

        BigDecimal total = nvl(rate.getBaseRate());
        int baseGal = rate.getBaseIncludedGal() != null ? Math.max(rate.getBaseIncludedGal(), 0) : 0;

        // Fixed = base only + base fees
        if (Boolean.TRUE.equals(rate.getFixedRate())) {
            return total.add(calculateSewerBaseFees(m)).setScale(SCALE_MONEY, ROUND_MONEY);
        }

        // Variable = base + units(overage)*PPU + base fees
        int overageGal = Math.max(usageGal - baseGal, 0);
        if (overageGal > 0) {
            SewerRateVariance var = m.getSewerRateVariances().stream().findFirst().orElse(null);
            BigDecimal ppu = var != null ? var.getSewerPpu() : null;
            if (ppu != null) {
                BigDecimal unit = unitSizeGallons(var != null ? var.getMeasureUnit() : null);
                BigDecimal units = unitsCeil(BigDecimal.valueOf(overageGal), unit);
                total = total.add(ppu.multiply(units));
            }
        }

        total = total.add(calculateSewerBaseFees(m));
        return total.setScale(SCALE_MONEY, ROUND_MONEY);
    }



    // Convert a MeasureUnit to its size in gallons. Default: 1 kgal.
    private static BigDecimal unitSizeGallons(MeasureUnit u) {
        if (u == null) return BigDecimal.valueOf(1000);
        return switch (u) {
            case KGAL         -> BigDecimal.valueOf(1000);       // 1 kgal
            case HCF          -> BigDecimal.valueOf(748.051948); // 100 cubic feet
            case CUBIC_METER  -> BigDecimal.valueOf(264.172052); // 1 m^3
            case GALLON       -> BigDecimal.ONE;                 // 1 gallon
        };
    }

    // Ceiling division: gallons -> whole billing units (any fraction bills as 1 unit).
    private static BigDecimal unitsCeil(BigDecimal gallons, BigDecimal unitSizeGallons) {
        if (gallons.signum() <= 0) return BigDecimal.ZERO;
        return gallons.divide(unitSizeGallons, 0, RoundingMode.CEILING);
    }

    // Tiered water charging by UNIT (not per gallon).
    private BigDecimal calculateTieredWaterCharge(List<RateVariance> variances, int usageGal, int overageGal) {
        BigDecimal additional = BigDecimal.ZERO;
        int remainingGal = overageGal;

        for (RateVariance rv : variances) {
            int min = nvlInt(rv.getWaterRangeMin());
            int maxRaw = nvlInt(rv.getWaterRangeMax());
            int max = (maxRaw == 0) ? Integer.MAX_VALUE : maxRaw;

            // Flat fee tier: apply once if total usage falls in the range
            if (Boolean.TRUE.equals(rv.getWaterFlatRateRange())) {
                if (usageGal > min && usageGal <= max) {
                    additional = additional.add(nvl(rv.getWaterPPU()));
                    break;
                }
                continue;
            }

            // Variable tier: bill per UNIT within this tier's gallon span
            int start = Math.max(min, usageGal - remainingGal); // first gallon to bill in this tier
            int end = Math.min(usageGal, max);                  // last gallon considered in this tier

            if (end <= start) continue;

            int gallonsInTier = end - start;
            BigDecimal unitSize = unitSizeGallons(rv.getMeasureUnit());
            BigDecimal units = unitsCeil(BigDecimal.valueOf(gallonsInTier), unitSize);

            additional = additional.add(nvl(rv.getWaterPPU()).multiply(units));

            remainingGal -= gallonsInTier;
            if (remainingGal <= 0) break;
        }

        return additional;
    }

    private BigDecimal calculateWaterBaseFees(Municipality m) {
        return m.getFees().stream()
                .filter(f -> f.getBaseFee() != null && f.getBaseFee().equals(water))
                .map(Fee::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateSewerBaseFees(Municipality m) {
        return m.getFees().stream()
                .filter(f -> f.getBaseFee() != null && f.getBaseFee().equals(sewer))
                .map(Fee::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal nvl(BigDecimal x) { return x != null ? x : BigDecimal.ZERO; }
    private static int nvlInt(Integer x) { return x != null ? x : 0; }
}

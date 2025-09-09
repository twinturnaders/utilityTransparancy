package wgu.edu.BrinaBright;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wgu.edu.BrinaBright.Entities.Fee;
import wgu.edu.BrinaBright.Entities.Municipality;
import wgu.edu.BrinaBright.Entities.RateVariance;
import wgu.edu.BrinaBright.Entities.WaterRate;
import wgu.edu.BrinaBright.Enums.BaseFee;
import wgu.edu.BrinaBright.Enums.MeasureUnit;
import wgu.edu.BrinaBright.Services.RateCalculatorService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RateCalculatorServiceTest {

    private RateCalculatorService svc;

    @BeforeEach
    void setUp() {
        svc = new RateCalculatorService();
    }



    private Municipality muniWithWater(WaterRate wr, List<RateVariance> variances, List<Fee> fees) {
        Municipality m = new Municipality();
        m.setWaterRates(wr != null ? List.of(wr) : List.of());
        m.setRateVariances(variances != null ? variances : List.of());
        m.setFees(fees != null ? fees : List.of());
        return m;
    }

    private WaterRate waterRate(BigDecimal baseRate, Integer baseGal, Boolean fixed) {
        WaterRate wr = new WaterRate();
        wr.setBaseRate(baseRate);
        wr.setBaseGal(baseGal);
        wr.setFixedRate(fixed);
        return wr;
    }

    private RateVariance waterVar(Integer min, Integer max, BigDecimal ppu, boolean flat, MeasureUnit unit) {
        RateVariance rv = new RateVariance();
        rv.setWaterRangeMin(min);
        rv.setWaterRangeMax(max);
        rv.setWaterPPU(ppu);
        rv.setWaterFlatRateRange(flat);
        rv.setMeasureUnit(unit);
        return rv;
    }

    private Fee waterFee(BigDecimal amt) {
        Fee f = new Fee();
        f.setBaseFee(BaseFee.water);
        f.setAmount(amt);
        return f;
    }

    private static void assertMoneyEquals(BigDecimal expected, BigDecimal actual) {

        assertEquals(0, expected.compareTo(actual), "Expected " + expected + " but was " + actual);
    }



    @Test
    void fixedRate_ignoresOverage_addsWaterBaseFees() {
        // Base $30, fixed = true, base fee $5 -> total $35 no matter the usage
        WaterRate wr = waterRate(new BigDecimal("30.00"), 3000, true);
        Municipality m = muniWithWater(wr, List.of(), List.of(waterFee(new BigDecimal("5.00"))));

        BigDecimal total = svc.calculateWaterCharge(m, /*usageGal*/ 12000);

        assertMoneyEquals(new BigDecimal("35.00"), total);
    }

    @Test
    void variableRate_kgalUnits_overageCeiling() {
//water base rate 20
// 3kgal included
// 2.5/kgal after basegal usage of 1.5 units (always rounded up to nearest whole) -> 2 units
// = 20 + (2.5 * 2)
        WaterRate wr = waterRate(new BigDecimal("20.00"), 3000, false);
        RateVariance v = waterVar(0, 0, new BigDecimal("2.50"), false, MeasureUnit.KGAL);

        Municipality m = muniWithWater(wr, List.of(v), List.of());
        BigDecimal total = svc.calculateWaterCharge(m, 4500);

        assertMoneyEquals(new BigDecimal("25.00"), total);
    }

    @Test
    void variableRate_hcfUnits_roundUpPartialUnit() {
//base = 18
// 2 kgal included
// 3.1 / kgal 1 extra kgal usage
// fee 1.9
// -> = 18 + (3.1 * 1) + 1.9
        WaterRate wr = waterRate(new BigDecimal("18.00"), 2000, false);
        RateVariance v = waterVar(0, 0, new BigDecimal("3.10"), false, MeasureUnit.HCF);

        Municipality m = muniWithWater(wr, List.of(v), List.of(waterFee(new BigDecimal("1.90"))));
        BigDecimal total = svc.calculateWaterCharge(m, 2700);

        assertMoneyEquals(new BigDecimal("23.00"), total);
    }

    @Test
    void variableRate_multipleTiers_unitsByTier() {
        // Base $10, base 1000, not fixed
        // Two variable tiers in kgal:
        //  - Tier1: 0..5000, $2.00 per kgal
        //  - Tier2: 5001.., $3.00 per kgal
        // usage 7000 -> overage 6000 gallons
        // gallons in tier1 = 5000 - max(min, usage - remaining)
        //   (Overage first fills the higher end of usage within the tier window.)
        // gallons in tier2 = remaining 2000
        // units1 = ceil(4000/1000) = 4  -> $8.00
        // units2 = ceil(2000/1000) = 2  -> $6.00
        // total = 10 + 8 + 6 = 24.00
        WaterRate wr = waterRate(new BigDecimal("10.00"), 1000, false);
        List<RateVariance> vars = new ArrayList<>();
        vars.add(waterVar(0, 5000, new BigDecimal("2.00"), false, MeasureUnit.KGAL));
        vars.add(waterVar(5001, 0, new BigDecimal("3.00"), false, MeasureUnit.KGAL));

        Municipality m = muniWithWater(wr, vars, List.of());
        BigDecimal total = svc.calculateWaterCharge(m, 7000);

        assertMoneyEquals(new BigDecimal("24.00"), total);
    }

    @Test
    void flatRateTier_appliesOnce_whenUsageInRange() {
        // Base $12, base 0, not fixed
        // Flat tier (0..5000) has PPU 7.50 (applied once if usage falls in range)
        // Variable tier (5001..) $2/kgal covers any above (but not used here since usage is 4000)
        // usage 4000 -> falls in flat range -> total = 12 + 7.50 = 19.50
        WaterRate wr = waterRate(new BigDecimal("12.00"), 0, false);

        RateVariance flat = waterVar(0, 5000, new BigDecimal("7.50"), true, MeasureUnit.KGAL);
        RateVariance var = waterVar(5001, 0, new BigDecimal("2.00"), false, MeasureUnit.KGAL);

        Municipality m = muniWithWater(wr, List.of(flat, var), List.of());
        BigDecimal total = svc.calculateWaterCharge(m, 4000);

        assertMoneyEquals(new BigDecimal("19.50"), total);
    }
}

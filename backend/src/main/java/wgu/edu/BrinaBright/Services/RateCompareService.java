package wgu.edu.BrinaBright.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wgu.edu.BrinaBright.DTOs.NearbyCostDTO;
import wgu.edu.BrinaBright.Entities.Municipality;
import wgu.edu.BrinaBright.Repos.MunicipalityRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
@Service
@RequiredArgsConstructor
public class RateCompareService {

    private MunicipalityRepository municipalityRepository;
    private RateCalculatorService rateCalculatorService;
    public List<NearbyCostDTO> getNearbyCostEstimates(String zip, int usageGal, double radiusMeters) {
        List<Object[]> results = municipalityRepository.findMunicipalityIdsAndDistance(zip, radiusMeters);

            return results.stream().map(row -> {
                Long muniId = ((Number) row[0]).longValue();
            double distanceMeters = ((Number) row[1]).doubleValue();
            double distanceMiles = distanceMeters / 1609;

            Municipality m = municipalityRepository.findById(muniId).orElse(null);
            if (m == null) return null;

            BigDecimal estWater = rateCalculatorService.calculateWaterCharge(m, usageGal);
            BigDecimal estSewer = rateCalculatorService.calculateSewerCharge(m, usageGal);



            return new NearbyCostDTO(
                    m.getName(),
                    m.getCounty(),
                    m.getState(),
                    distanceMiles,
                    estWater,
                    estSewer
            );
        }).filter(Objects::nonNull).toList();
    }

}

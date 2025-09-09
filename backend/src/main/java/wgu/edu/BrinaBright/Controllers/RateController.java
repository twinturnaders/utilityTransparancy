package wgu.edu.BrinaBright.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wgu.edu.BrinaBright.DTOs.NearbyCostDTO;
import wgu.edu.BrinaBright.DTOs.RateSummaryDTO;
import wgu.edu.BrinaBright.Services.MunicipalityService;
import wgu.edu.BrinaBright.Services.RateCompareService;

import java.util.List;
@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
@Validated
public class RateController {

    private final MunicipalityService municipalityService;
    private final RateCompareService rateCompareService;

    // KEEP THIS ONE
    @GetMapping("/nearby")
    public ResponseEntity<List<RateSummaryDTO>> getRatesNearZip(
            @RequestParam String zip,
            @RequestParam(name = "radiusMiles", defaultValue = "50") double radiusMiles,
            @RequestParam(name = "usageGallons", defaultValue = "5000") int usageGallons
    ) {
        double radiusMeters = radiusMiles * 1609.34;
        List<RateSummaryDTO> results =
                municipalityService.findNearbyRates(zip, radiusMeters, usageGallons);
        return ResponseEntity.ok(results);
    }


    @GetMapping("/costs/nearby")
    public List<NearbyCostDTO> getNearbyRates(
            @RequestParam String zip,
            @RequestParam int gallons,
            @RequestParam(defaultValue = "96560") double radiusMeters // ~60 miles
    ) {

        var out = rateCompareService.getNearbyCostEstimates(zip, gallons, radiusMeters);
        return out == null ? List.of() : out;
    }
    }

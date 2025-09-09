package wgu.edu.BrinaBright.Controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wgu.edu.BrinaBright.DTOs.MunicipalityLiteDTO;
import wgu.edu.BrinaBright.DTOs.MunicipalityOption;
import wgu.edu.BrinaBright.Repos.MunicipalityNearProjection;
import wgu.edu.BrinaBright.Repos.MunicipalityRepository;

import java.util.List;

@RestController
@RequestMapping("/api/municipalities")
@RequiredArgsConstructor
public class MunicipalityLookupController {

    private final MunicipalityRepository municipalities;

    @GetMapping("/near")
    public ResponseEntity<List<MunicipalityOption>> nearZip(
            @RequestParam String zip,
            @RequestParam(defaultValue = "16093") double radiusMeters, // ~10 miles
            @RequestParam(defaultValue = "15") int limit
    ) {
        var rows = municipalities.findNearZip(zip, radiusMeters, limit);
        return ResponseEntity.ok(rows.stream().map(MunicipalityOption::from).toList());
    }

    @GetMapping("/nearest")
    public ResponseEntity<MunicipalityOption> nearest(@RequestParam String zip) {
        var rows = municipalities.findNearestByZip(zip);
        if (rows.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(MunicipalityOption.from(rows.get(0)));
    }
    @GetMapping("/search")
    public List<MunicipalityLiteDTO> search(@RequestParam("q") String q) {
        if (q == null || q.isBlank()) return List.of();
        return municipalities.findTop10ByNameContainingIgnoreCaseOrderByNameAsc(q)
                .stream().map(MunicipalityLiteDTO::from).toList();
    }
}
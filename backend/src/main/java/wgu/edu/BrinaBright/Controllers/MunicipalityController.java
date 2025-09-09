package wgu.edu.BrinaBright.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wgu.edu.BrinaBright.DTOs.MunicipalityDTO;
import wgu.edu.BrinaBright.Repos.MunicipalityRepository;

import java.util.List;

@RestController
@RequestMapping("/api/municipalities")
@RequiredArgsConstructor
public class MunicipalityController {

    private final MunicipalityRepository municipalityRepository;

    @GetMapping
    public List<MunicipalityDTO> getAllMunicipalities() {
        return municipalityRepository.findAll()
                .stream()
                .map(m -> new MunicipalityDTO(m.getId(), m.getName(), m.getState(), m.getCounty()))
                .toList();
    }
}
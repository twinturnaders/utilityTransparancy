package wgu.edu.BrinaBright.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wgu.edu.BrinaBright.DTOs.TownOnlyDTO;
import wgu.edu.BrinaBright.Repos.IncTownRepository;

import java.util.List;

@RestController
@RequestMapping("/api/towns")
@RequiredArgsConstructor
public class TownListController {
    private final IncTownRepository repo;

    @GetMapping("/names")
    public List<TownOnlyDTO> listNames() {
        return repo.findAllNamesAsc(); 
    }
}
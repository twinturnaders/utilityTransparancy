package wgu.edu.BrinaBright.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wgu.edu.BrinaBright.DTOs.CrowdSubmissionDTO;
import wgu.edu.BrinaBright.Entities.User;
import wgu.edu.BrinaBright.Security.CurrentUser;
import wgu.edu.BrinaBright.Security.UserPrincipal;
import wgu.edu.BrinaBright.Services.CrowdSubmissionService;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class CrowdSubmissionController {

    private final CrowdSubmissionService crowdSubmissionService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitStructuredData(

            @RequestBody CrowdSubmissionDTO dto
    ) {

        crowdSubmissionService.saveStructuredSubmission(dto);
        return ResponseEntity.ok("Submission received!");
    }
}
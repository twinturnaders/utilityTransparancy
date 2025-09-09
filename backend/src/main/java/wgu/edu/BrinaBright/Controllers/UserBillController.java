
package wgu.edu.BrinaBright.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wgu.edu.BrinaBright.DTOs.CreateBillRequest;
import wgu.edu.BrinaBright.DTOs.UserBillDTO;
import wgu.edu.BrinaBright.Entities.User;
import wgu.edu.BrinaBright.Security.CurrentUser;
import wgu.edu.BrinaBright.Services.ViewBillService;

import java.util.List;

@RestController
@RequestMapping("/api/userbills")
@RequiredArgsConstructor
public class UserBillController {

    private final ViewBillService viewBillService;

    @GetMapping
    public ResponseEntity<List<UserBillDTO>> list(@CurrentUser User user) {
        return ResponseEntity.ok(viewBillService.getUserBillsForUser(user.getId()));
    }

    @PostMapping
    public ResponseEntity<String> create(@CurrentUser User user,
                                         @RequestBody CreateBillRequest req) {
        viewBillService.saveBillForUser(user.getId(), req);
        return ResponseEntity.ok("Saved");
    }
}

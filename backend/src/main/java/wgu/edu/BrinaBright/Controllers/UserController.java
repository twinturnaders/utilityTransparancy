package wgu.edu.BrinaBright.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wgu.edu.BrinaBright.DTOs.UpdateAccountRequest;
import wgu.edu.BrinaBright.DTOs.UserDTO;
import wgu.edu.BrinaBright.Entities.User;
import wgu.edu.BrinaBright.Security.CurrentUser;
import wgu.edu.BrinaBright.Security.UserPrincipal;
import wgu.edu.BrinaBright.Services.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserDTO me(@AuthenticationPrincipal UserPrincipal user) {
        return userService.findDTOById(user.getId());
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public UserDTO findOne(@PathVariable Long id) {
        return userService.findDTOById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@CurrentUser User user,
                                           @RequestBody UpdateAccountRequest request) {
        userService.updateUserAccount(user.getId(), request);
        return ResponseEntity.ok().build();
    }
}

package wgu.edu.BrinaBright.Security;

import wgu.edu.BrinaBright.DTOs.UserDTO;
import wgu.edu.BrinaBright.Entities.Municipality;
import wgu.edu.BrinaBright.Entities.User;

public record AuthResponse(
        String token,
        UserDTO user
) {
    public static AuthResponse from(User u, String token) {
        Municipality m = u.getMunicipality();
        return new AuthResponse(
                token,
                new UserDTO(
                        u.getId(),
                        u.getEmail(),
                        u.getRole().name(),
                        m != null ? m.getId() : null,
                        m != null ? m.getName() : null
                )
        );
    }
}
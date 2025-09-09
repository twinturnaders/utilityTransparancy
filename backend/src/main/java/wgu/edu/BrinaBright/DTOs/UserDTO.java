package wgu.edu.BrinaBright.DTOs;

public record UserDTO(
        Long id,
        String email,
        String role,
        Long municipalityId,
        String municipalityName
) {}
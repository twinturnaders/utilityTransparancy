package wgu.edu.BrinaBright.DTOs;

public record UpdateAccountRequest(
        String email,
        String password,
        Long municipalityId
) {}
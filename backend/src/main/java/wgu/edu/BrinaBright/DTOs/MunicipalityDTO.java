package wgu.edu.BrinaBright.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wgu.edu.BrinaBright.Entities.Municipality;

public record MunicipalityDTO(
        Long id,
        String name,
        String state,
        String county
) {}

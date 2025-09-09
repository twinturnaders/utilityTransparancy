package wgu.edu.BrinaBright.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data

@NoArgsConstructor

public class BillFeeDTO {
    private String name;
    private BigDecimal amount;

    public BillFeeDTO(String name, BigDecimal amount) {
    }
}
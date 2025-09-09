package wgu.edu.BrinaBright.DTOs;

import java.math.BigDecimal;



public record CrowdFeeDTO(String feeType, BigDecimal amount) {
    public String getName() {
        return feeType;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
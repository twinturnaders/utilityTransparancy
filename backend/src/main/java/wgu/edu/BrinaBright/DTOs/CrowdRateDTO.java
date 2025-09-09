package wgu.edu.BrinaBright.DTOs;

import java.math.BigDecimal;

public record CrowdRateDTO(BigDecimal pricePerUnit, Integer minRange, Integer maxRange) {
    public Integer getUpTo() {
        return maxRange;
    }

    public BigDecimal getRate() {
        return pricePerUnit;
    }
}

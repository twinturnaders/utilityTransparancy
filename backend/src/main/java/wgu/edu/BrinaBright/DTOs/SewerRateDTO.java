package wgu.edu.BrinaBright.DTOs;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wgu.edu.BrinaBright.Entities.SewerRate;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SewerRateDTO {
    private BigDecimal baseRate;
    private Integer baseIncludedGal;
    private Boolean fixedRate;

    public SewerRateDTO(BigDecimal baseRate, Integer baseIncludedGal, Boolean fixedRate, String s) {

    }

    public static SewerRateDTO fromEntity(SewerRate rate) {
        return new SewerRateDTO(
                rate.getBaseRate(),
                rate.getBaseIncludedGal(),
                rate.getFixedRate(),
                Boolean.TRUE.equals(rate.getFixedRate()) ? "Fixed rate" : "Rate varies by use"
        );
    }
}

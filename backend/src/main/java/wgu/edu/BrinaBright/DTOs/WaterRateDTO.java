package wgu.edu.BrinaBright.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import wgu.edu.BrinaBright.Entities.WaterRate;
import wgu.edu.BrinaBright.Entities.RateVariance;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaterRateDTO {
    private BigDecimal baseRate;
    private Integer baseGal;
    private Boolean fixedRate;
    private BigDecimal waterPPU;

private RateVariance rateVariance;

    public WaterRateDTO(BigDecimal baseRate, Integer baseGal, Boolean fixedRate, String s) {

    }


    public static WaterRateDTO fromEntity(WaterRate rate, RateVariance rateVariance) {
        return new WaterRateDTO(
                rate.getBaseRate(),
                rate.getBaseGal(),
                rate.getFixedRate(),
                Boolean.TRUE.equals(rate.getFixedRate()) ? "Fixed rate" : "Rate varies by use"
        );
    }
}

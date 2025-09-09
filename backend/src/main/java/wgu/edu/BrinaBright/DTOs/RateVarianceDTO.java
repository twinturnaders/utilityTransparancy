package wgu.edu.BrinaBright.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wgu.edu.BrinaBright.Entities.Municipality;
import wgu.edu.BrinaBright.Entities.RateVariance;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateVarianceDTO {
    private Integer rangeMin;
    private Integer rangeMax;
    private BigDecimal pricePerUnit;

    public RateVarianceDTO(BigDecimal sewerPpu) {
    }


    public static RateVarianceDTO fromWater(RateVariance rv) {
        return new RateVarianceDTO(
                rv.getWaterRangeMin(),
                rv.getWaterRangeMax(),
                rv.getWaterPPU()
        );
    }



}

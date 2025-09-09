package wgu.edu.BrinaBright.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wgu.edu.BrinaBright.Entities.Municipality;
import wgu.edu.BrinaBright.Entities.RateVariance;
import wgu.edu.BrinaBright.Entities.SewerRateVariance;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SewerRateVarianceDTO {

    private BigDecimal sewerPpu;



    public static SewerRateVarianceDTO from(SewerRateVariance rv) {
        return new SewerRateVarianceDTO(

                rv.getSewerPpu()
        );
    }



}

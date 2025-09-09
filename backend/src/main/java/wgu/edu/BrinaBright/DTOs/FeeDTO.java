package wgu.edu.BrinaBright.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wgu.edu.BrinaBright.Entities.Fee;
import wgu.edu.BrinaBright.Enums.BaseFee;
import wgu.edu.BrinaBright.Enums.FeeType;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeDTO {
    private FeeType type;
    private BigDecimal amount;
    private BaseFee baseFee;

    public static FeeDTO fromEntity(Fee fee) {
        return new FeeDTO(fee.getFeeType(), fee.getAmount(), fee.getBaseFee());
    }
}

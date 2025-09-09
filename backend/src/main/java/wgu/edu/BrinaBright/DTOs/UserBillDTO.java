package wgu.edu.BrinaBright.DTOs;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Getter
@Setter


public class UserBillDTO {
    private long id;
    private LocalDate billDate;





    private LocalDate dueDate;
    private LocalDate paidDate;

    private boolean paid;
    private BigDecimal waterUsage;     // Gallons
    private BigDecimal sewerUsage;     // Gallons

    private BigDecimal waterCharge;
    private BigDecimal sewerCharge;





    private Map<String, BigDecimal> fees;




    public Boolean getPaid() {
        return paid;
    }


}

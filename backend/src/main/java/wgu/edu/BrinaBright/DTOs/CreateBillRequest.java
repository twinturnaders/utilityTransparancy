package wgu.edu.BrinaBright.DTOs;



import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
public class CreateBillRequest {
    private LocalDate billDate;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private Boolean paid;

    private BigDecimal waterUsage;
    private BigDecimal sewerUsage;
    private BigDecimal waterCharge;
    private BigDecimal sewerCharge;

    private Map<String, BigDecimal> fees;
}

package wgu.edu.BrinaBright.DTOs;

import lombok.Data;
import wgu.edu.BrinaBright.Entities.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ViewBillDTO {
    private Long id;
    private User user;
    private LocalDate billDate;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private Boolean paid;

    private BigDecimal waterUsage;
    private BigDecimal sewerUsage;
    private BigDecimal waterCharge;
    private BigDecimal sewerCharge;

    private List<BillFeeDTO> fees;


}

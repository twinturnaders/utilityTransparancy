package wgu.edu.BrinaBright.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import wgu.edu.BrinaBright.DTOs.BillFeeDTO;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity @Table(name = "user_bills",
        indexes = @Index(name="bill_user_muni_date_idx", columnList = "user_id, billDate"))
@Data
@NoArgsConstructor
public class UserBill {

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "paid_date")
    private LocalDate paidDate;


    private Boolean paid;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(name = "bill_date")
    private LocalDate billDate;


    @Column(name = "water_charge")
    private BigDecimal waterCharge;

    @Column(name = "sewer_charge")
    private BigDecimal sewerCharge;

    @Column(name = "water_use_amount")
    private BigDecimal waterUsage;

    @Column(name = "sewer_use_amount")
    private BigDecimal sewerUsage;


    public Map<String, BigDecimal> getFees() {
        return fees;
    }

    public void setFees(Map<String, BigDecimal> fees) {
        this.fees = fees;
    }

    @ElementCollection
    @CollectionTable(name = "bill_fees", joinColumns = @JoinColumn(name = "bill_id"))
    @MapKeyColumn(name = "fee_type")
    @Column(name = "fee_amount")
    private Map<String, BigDecimal> fees = new HashMap<>();



    private Boolean isPaid;



    public boolean billPaid() {
        if (getPaidDate() != null) {
            return true;
        } else {
            return false;
        }
    }
}
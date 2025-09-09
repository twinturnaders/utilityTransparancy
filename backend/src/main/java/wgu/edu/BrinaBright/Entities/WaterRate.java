package wgu.edu.BrinaBright.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity @Table(name = "water_rates")
@Data @NoArgsConstructor
public class WaterRate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "municipality_id", nullable = false)
    private Municipality municipality;

    @Column(name = "base_rate")
    private BigDecimal baseRate;          // e.g., monthly base charge

    @Column(name = "base_gallons")
    private Integer baseGal;      // optional: gallons included in base

    @Column(name = "start_date")
    private LocalDate effectiveStart;

    @Column(name = "end_date")
    private LocalDate effectiveEnd;// nullable for open-ended

    @Column(name = "fixed_rate")
    private Boolean fixedRate;
}



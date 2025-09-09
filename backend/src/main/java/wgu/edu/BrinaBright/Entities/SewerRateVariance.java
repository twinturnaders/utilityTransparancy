package wgu.edu.BrinaBright.Entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import wgu.edu.BrinaBright.Enums.MeasureUnit;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sewer_rate_variance")
public class SewerRateVariance {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "sewer_ppu")
    private BigDecimal sewerPpu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id", nullable = false)
    private Municipality municipality;

    @Column(name = "created_at")
    private LocalDate effectiveStart;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "measure_unit")
    private MeasureUnit measureUnit;

    @Column(name = "end_date")
    private LocalDate effectiveEnd;
}

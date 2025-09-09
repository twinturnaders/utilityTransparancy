package wgu.edu.BrinaBright.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wgu.edu.BrinaBright.Enums.RateType;
import wgu.edu.BrinaBright.Enums.SubmissionStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "crowdsources")
public class CrowdSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    // FILE upload fields
    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "stored_file_path")
    private String storedFilePath;

    // TEXT input fields
    @Column(name = "town_name")
    private String townName;

    @Enumerated(EnumType.STRING)
    @Column(name = "rate_type", nullable = false)
    private RateType rateType;


    @Column(name = "fixed_rate")
    private Boolean fixedRate;

    @Column(name = "base_rate")
    private BigDecimal baseRate;

    @Column(name = "created_at")
    private LocalDate createdAt;    // e.g., bill date


    @Column(name = "notes")
    private String notes;

    @Column(name = "submission_file_uploaded")
    private boolean submittedViaUpload;

    @Enumerated(EnumType.STRING)
    @Column(name = "submission_status")
    private SubmissionStatus status;

    private Long resolvedMunicipalityId;


    private Instant updatedAt = Instant.now();

    @OneToMany(mappedBy = "submission", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<CrowdFee> fees = new ArrayList<>();

    @OneToMany(mappedBy = "submission", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<CrowdRate> rateTiers = new ArrayList<>();



}

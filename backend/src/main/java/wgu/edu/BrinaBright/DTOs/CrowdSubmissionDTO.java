package wgu.edu.BrinaBright.DTOs;

import lombok.*;
import wgu.edu.BrinaBright.Enums.RateType;

import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CrowdSubmissionDTO {

    // Main submission fields
    private String townName;
    private RateType rateType;
    private BigDecimal baseRate;
    private String notes;
    private boolean submittedViaUpload;

    // Sub-objects
    private List<CrowdRateDTO> rateTiers;
    private List<CrowdFeeDTO> fees;
}

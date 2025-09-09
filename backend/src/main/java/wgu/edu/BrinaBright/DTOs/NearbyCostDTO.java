
package wgu.edu.BrinaBright.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class NearbyCostDTO {
    private String name;
    private String county;
    private String state;
    private Double distanceMiles; // need to convert back to meters
    private BigDecimal estimatedWater;
    private BigDecimal estimatedSewer;
}

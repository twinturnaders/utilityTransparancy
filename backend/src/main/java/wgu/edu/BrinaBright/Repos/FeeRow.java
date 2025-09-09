
package wgu.edu.BrinaBright.Repos;

import java.math.BigDecimal;

public interface FeeRow {
    Long getBillId();
    String getName();
    BigDecimal getAmount();
}

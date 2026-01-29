package onion.whnazv.biblioteca.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleItem {
    private Long id;
    private Long bookId;
    private int quantity;
    private BigDecimal unitPrice;
}

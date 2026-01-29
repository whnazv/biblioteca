package onion.whnazv.biblioteca.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItem {

    private Long bookId;
    private String title;
    private BigDecimal price;
    private int quantity;

    public BigDecimal getTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}

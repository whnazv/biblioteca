package onion.whnazv.biblioteca.infrastructure.adapter.in.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long bookId;
    private String title;
    private BigDecimal price;
    private int quantity;
    private BigDecimal total;
}

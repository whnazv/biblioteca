package onion.whnazv.biblioteca.infrastructure.adapter.in.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private List<CartItemDto> items = new ArrayList<>();
    private BigDecimal total = BigDecimal.ZERO;
}

package onion.whnazv.biblioteca.infrastructure.adapter.in.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemDto {

    private Long id;

    @NotNull
    private Long bookId;

    @Positive
    private int quantity;

    @NotNull
    private BigDecimal unitPrice;
}

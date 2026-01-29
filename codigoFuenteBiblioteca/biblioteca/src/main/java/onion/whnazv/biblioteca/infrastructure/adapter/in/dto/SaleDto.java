package onion.whnazv.biblioteca.infrastructure.adapter.in.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {

    private Long id;

    @NotNull
    private Long userId;

    private BigDecimal total;
    private LocalDateTime createdAt;

    private List<SaleItemDto> items;
}

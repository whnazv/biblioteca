package onion.whnazv.biblioteca.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Sale {
    private Long id;
    private User user;
    private BigDecimal total = BigDecimal.ZERO;
    private LocalDateTime createdAt;

    private List<SaleItem> items = new ArrayList<>();

    public Sale(Long id, User user, BigDecimal total, List<SaleItem> items) {
        this.id = id;
        this.user = user;
        this.total = total != null ? total : BigDecimal.ZERO;
        this.items = items != null ? items : new ArrayList<>();
    }

    public void addItem(SaleItem item) {
        this.items.add(item);
        recalculateTotal();
    }

    public void removeItem(SaleItem item) {
        this.items.remove(item);
        recalculateTotal();
    }

    private void recalculateTotal() {
        this.total = this.items.stream()
                .map(i -> i.getUnitPrice().multiply(new BigDecimal(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

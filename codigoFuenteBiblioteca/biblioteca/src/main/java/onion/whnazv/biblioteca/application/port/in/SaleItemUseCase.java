package onion.whnazv.biblioteca.application.port.in;

import onion.whnazv.biblioteca.domain.model.SaleItem;

import java.util.List;
import java.util.Optional;

public interface SaleItemUseCase {

    List<SaleItem> getAllItems();

    List<SaleItem> getItemsBySaleId(Long saleId);

    List<SaleItem> getItemsByBookId(Long bookId);

    Optional<SaleItem> getItemById(Long id);

    SaleItem saveItem(SaleItem item);

    Optional<SaleItem> updateItem(Long id, SaleItem updatedItem);

    void deleteItem(Long id);
}

package onion.whnazv.biblioteca.application.usecase;

import onion.whnazv.biblioteca.application.port.in.SaleItemUseCase;
import onion.whnazv.biblioteca.application.port.out.SaleItemRepositoryPort;
import onion.whnazv.biblioteca.domain.model.SaleItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleItemService implements SaleItemUseCase {

    private final SaleItemRepositoryPort saleItemRepositoryPort;

    public SaleItemService(SaleItemRepositoryPort saleItemRepositoryPort) {
        this.saleItemRepositoryPort = saleItemRepositoryPort;
    }

    @Override
    public List<SaleItem> getAllItems() {
        return saleItemRepositoryPort.findAll();
    }

    @Override
    public List<SaleItem> getItemsBySaleId(Long saleId) {
        return saleItemRepositoryPort.findBySaleId(saleId);
    }

    @Override
    public List<SaleItem> getItemsByBookId(Long bookId) {
        return saleItemRepositoryPort.findByBookId(bookId);
    }

    @Override
    public Optional<SaleItem> getItemById(Long id) {
        return saleItemRepositoryPort.findById(id);
    }

    @Override
    public SaleItem saveItem(SaleItem item) {
        return saleItemRepositoryPort.save(item);
    }

    @Override
    public Optional<SaleItem> updateItem(Long id, SaleItem updatedItem) {
        return saleItemRepositoryPort.findById(id).map(existingItem -> {

            existingItem.setQuantity(updatedItem.getQuantity());
            existingItem.setUnitPrice(updatedItem.getUnitPrice());
            existingItem.setBookId(updatedItem.getBookId());

            return saleItemRepositoryPort.save(existingItem);
        });
    }

    @Override
    public void deleteItem(Long id) {
        saleItemRepositoryPort.deleteById(id);
    }
}

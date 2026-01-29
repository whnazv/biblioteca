package onion.whnazv.biblioteca.application.port.out;

import onion.whnazv.biblioteca.domain.model.SaleItem;

import java.util.List;
import java.util.Optional;

public interface SaleItemRepositoryPort {

    List<SaleItem> findAll();

    List<SaleItem> findBySaleId(Long saleId);

    List<SaleItem> findByBookId(Long bookId);

    Optional<SaleItem> findById(Long id);

    SaleItem save(SaleItem item);

    void deleteById(Long id);
}

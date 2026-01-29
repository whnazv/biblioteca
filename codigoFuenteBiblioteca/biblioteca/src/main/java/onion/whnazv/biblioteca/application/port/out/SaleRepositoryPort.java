package onion.whnazv.biblioteca.application.port.out;

import onion.whnazv.biblioteca.domain.model.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleRepositoryPort {

    Sale save(Sale sale);

    List<Sale> findAll();

    List<Sale> findByUserId(Long userId);

    Optional<Sale> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<Sale> searchSales(String query);



}

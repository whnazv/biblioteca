package onion.whnazv.biblioteca.application.port.in;

import onion.whnazv.biblioteca.domain.model.Sale;
import onion.whnazv.biblioteca.domain.model.SaleItem;
import onion.whnazv.biblioteca.domain.model.User;

import java.util.List;

public interface SaleUseCase {

    Sale createSale(User user, List<SaleItem> items);

    List<Sale> getAllSales();

    List<Sale> getSalesByUserId(Long userId);

    Sale getSaleById(Long saleId);

    boolean deleteSale(Long saleId);

    Sale updateSale(Long saleId, User user, List<SaleItem> updatedItems);

    List<Sale> searchSales(String query);


}

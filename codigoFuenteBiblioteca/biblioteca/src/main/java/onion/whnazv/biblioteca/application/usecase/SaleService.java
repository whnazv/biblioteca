package onion.whnazv.biblioteca.application.usecase;

import onion.whnazv.biblioteca.application.port.in.SaleUseCase;
import onion.whnazv.biblioteca.application.port.out.BookRepositoryPort;
import onion.whnazv.biblioteca.application.port.out.SaleRepositoryPort;
import onion.whnazv.biblioteca.domain.exception.InsufficientStockException;
import onion.whnazv.biblioteca.domain.exception.NotFoundException;
import onion.whnazv.biblioteca.domain.model.Book;
import onion.whnazv.biblioteca.domain.model.Sale;
import onion.whnazv.biblioteca.domain.model.SaleItem;
import onion.whnazv.biblioteca.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService implements SaleUseCase {

    private final SaleRepositoryPort saleRepositoryPort;
    private final BookRepositoryPort bookRepositoryPort;

    public SaleService(SaleRepositoryPort saleRepositoryPort, BookRepositoryPort bookRepositoryPort) {
        this.saleRepositoryPort = saleRepositoryPort;
        this.bookRepositoryPort = bookRepositoryPort;
    }

    @Override
    public Sale createSale(User user, List<SaleItem> items) {

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("La venta debe contener al menos un libro.");
        }

        Sale sale = new Sale();
        sale.setUser(user);

        for (SaleItem item : items) {

            Book book = bookRepositoryPort.findById(item.getBookId())
                    .orElseThrow(() -> new NotFoundException("Libro no encontrado"));

            if (book.getStock() < item.getQuantity()) {
                throw new InsufficientStockException("Stock insuficiente para el libro: " + book.getTitle());
            }

            book.setStock(book.getStock() - item.getQuantity());
            bookRepositoryPort.save(book);

            item.setUnitPrice(book.getPrice());

            sale.addItem(item);
        }

        return saleRepositoryPort.save(sale);
    }

    @Override
    public List<Sale> getAllSales() {
        return saleRepositoryPort.findAll();
    }

    @Override
    public List<Sale> getSalesByUserId(Long userId) {
        return saleRepositoryPort.findByUserId(userId);
    }

    @Override
    public Sale getSaleById(Long saleId) {
        return saleRepositoryPort.findById(saleId)
                .orElseThrow(() -> new NotFoundException("Venta no encontrada con ID: " + saleId));
    }

    @Override
    public boolean deleteSale(Long saleId) {
        if (saleRepositoryPort.existsById(saleId)) {
            saleRepositoryPort.deleteById(saleId);
            return true;
        }
        return false;
    }

    @Override
    public Sale updateSale(Long saleId, User user, List<SaleItem> updatedItems) {
        Sale sale = saleRepositoryPort.findById(saleId)
                .orElseThrow(() -> new NotFoundException("Venta no encontrada"));

        for (SaleItem oldItem : sale.getItems()) {

            Book oldBook = bookRepositoryPort.findById(oldItem.getBookId())
                    .orElseThrow(() -> new NotFoundException("Libro no encontrado"));

            oldBook.setStock(oldBook.getStock() + oldItem.getQuantity());
            bookRepositoryPort.save(oldBook);
        }

        sale.getItems().clear();
        sale.setUser(user);

        for (SaleItem item : updatedItems) {

            Book book = bookRepositoryPort.findById(item.getBookId())
                    .orElseThrow(() -> new NotFoundException("Libro no encontrado"));

            if (book.getStock() < item.getQuantity()) {
                throw new InsufficientStockException("Stock insuficiente para el libro: " + book.getTitle());
            }

            book.setStock(book.getStock() - item.getQuantity());
            bookRepositoryPort.save(book);

            item.setUnitPrice(book.getPrice());
            sale.addItem(item);
        }

        return saleRepositoryPort.save(sale);
    }

    @Override
    public List<Sale> searchSales(String query) {
        return saleRepositoryPort.searchSales(query);
    }




}

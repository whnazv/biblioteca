package onion.whnazv.biblioteca.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {

    private List<CartItem> items = new ArrayList<>();

    // Añadir libro al carrito (si ya existe, acumula cantidad)
    public void addItem(Long bookId, String title, BigDecimal price, int quantity) {
        for (CartItem item : items) {
            if (item.getBookId().equals(bookId)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }

        CartItem item = new CartItem();
        item.setBookId(bookId);
        item.setTitle(title);
        item.setPrice(price);
        item.setQuantity(quantity);
        items.add(item);
    }

    // Calcular total del carrito
    public BigDecimal getTotal() {
        return items.stream()
                .map(CartItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Vaciar carrito
    public void clear() {
        items.clear();
    }

    // Eliminar un libro concreto del carrito
    public void removeItem(Long bookId) {
        items.removeIf(item -> item.getBookId().equals(bookId));
    }

    // Actualizar cantidad de un libro
    public void updateQuantity(Long bookId, int quantity) {
        for (CartItem item : items) {
            if (item.getBookId().equals(bookId)) {
                item.setQuantity(quantity);
                return;
            }
        }
    }

    // Saber si el carrito está vacío
    public boolean isEmpty() {
        return items.isEmpty();
    }
}

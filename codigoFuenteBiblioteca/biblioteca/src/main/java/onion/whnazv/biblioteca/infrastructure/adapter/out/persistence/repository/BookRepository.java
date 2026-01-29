package onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.repository;

import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByAuthorContainingIgnoreCase(String author);
    List<BookEntity> findByTitleContainingIgnoreCase(String title);
    boolean existsByIsbn(String isbn);
    List<BookEntity> findByStockGreaterThan(int stock);
    List<BookEntity> findByPriceBetween(BigDecimal min, BigDecimal max);

    
    List<BookEntity> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String title, String author, String isbn, String description
    );
}

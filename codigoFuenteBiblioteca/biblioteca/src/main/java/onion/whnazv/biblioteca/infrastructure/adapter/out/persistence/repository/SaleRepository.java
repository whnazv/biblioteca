package onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.repository;

import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity, Long> {

    List<SaleEntity> findByUserId(Long userId);

    @Query("SELECT DISTINCT s FROM SaleEntity s " +
            "JOIN s.user u " +
            "JOIN s.items i " +
            "JOIN i.book b " +
            "WHERE CAST(s.id AS string) LIKE CONCAT('%', :query, '%') " +          // búsqueda por ID de venta
            "   OR CAST(u.id AS string) LIKE CONCAT('%', :query, '%') " +          // búsqueda por ID de usuario
            "   OR LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) " +      // búsqueda por username
            "   OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) " +     // búsqueda por nombre
            "   OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%')) " +      // búsqueda por apellidos
            "   OR LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) " +         // búsqueda por título de libro
            "   OR LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%'))")          // búsqueda por autor de libro
    List<SaleEntity> searchSales(String query);
}

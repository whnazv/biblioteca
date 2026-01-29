package onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.repository;

import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.entity.SaleItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItemEntity, Long> {
    List<SaleItemEntity> findBySaleId(Long saleId);
    List<SaleItemEntity> findByBookId(Long bookId);
}

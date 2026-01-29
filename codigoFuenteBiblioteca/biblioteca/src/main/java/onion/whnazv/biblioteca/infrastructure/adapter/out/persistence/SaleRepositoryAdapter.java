package onion.whnazv.biblioteca.infrastructure.adapter.out.persistence;

import onion.whnazv.biblioteca.application.port.out.SaleRepositoryPort;
import onion.whnazv.biblioteca.domain.model.Sale;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.entity.BookEntity;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.entity.SaleEntity;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.repository.SaleRepository;
import onion.whnazv.biblioteca.infrastructure.mapper.entity.SaleMapperEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SaleRepositoryAdapter implements SaleRepositoryPort {

    private final SaleRepository saleRepository;
    private final SaleMapperEntity saleMapperEntity;

    public SaleRepositoryAdapter(SaleRepository saleRepository, SaleMapperEntity saleMapperEntity) {
        this.saleRepository = saleRepository;
        this.saleMapperEntity = saleMapperEntity;
    }

    @Override
    public Sale save(Sale sale) {

        
        SaleEntity entity = saleMapperEntity.toEntity(sale);

        
        entity.getItems().forEach(item -> item.setSale(entity));

        
        entity.getItems().forEach(itemEntity -> {
            var domainItem = sale.getItems().stream()
                    .filter(i -> i.getBookId() != null)
                    .filter(i -> i.getBookId().equals(itemEntity.getBook() != null ? itemEntity.getBook().getId() : null)
                            || itemEntity.getBook() == null)
                    .findFirst()
                    .orElse(null);

            if (domainItem != null) {
                var bookEntity = new BookEntity();
                bookEntity.setId(domainItem.getBookId());
                itemEntity.setBook(bookEntity);
            }
        });

        
        SaleEntity saved = saleRepository.save(entity);

        
        return saleMapperEntity.toModel(saved);
    }


    @Override
    public List<Sale> findAll() {
        return saleRepository.findAll()
                .stream()
                .map(saleMapperEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Sale> findByUserId(Long userId) {
        return saleRepository.findByUserId(userId)
                .stream()
                .map(saleMapperEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Sale> findById(Long id) {
        return saleRepository.findById(id).map(saleMapperEntity::toModel);
    }

    @Override
    public boolean existsById(Long id) {
        return saleRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        saleRepository.deleteById(id);
    }


    @Override
    public List<Sale> searchSales(String query) {
        return saleRepository.searchSales(query)
                .stream()
                .map(saleMapperEntity::toModel)
                .toList();
    }




}

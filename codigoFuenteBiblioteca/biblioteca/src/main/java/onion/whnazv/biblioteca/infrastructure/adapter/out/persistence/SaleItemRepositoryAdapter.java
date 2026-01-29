package onion.whnazv.biblioteca.infrastructure.adapter.out.persistence;

import onion.whnazv.biblioteca.application.port.out.SaleItemRepositoryPort;
import onion.whnazv.biblioteca.domain.model.SaleItem;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.entity.SaleItemEntity;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.repository.SaleItemRepository;
import onion.whnazv.biblioteca.infrastructure.mapper.entity.SaleItemMapperEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SaleItemRepositoryAdapter implements SaleItemRepositoryPort {

    private final SaleItemRepository saleItemRepository;
    private final SaleItemMapperEntity saleItemMapperEntity;

    public SaleItemRepositoryAdapter(SaleItemRepository saleItemRepository, SaleItemMapperEntity saleItemMapperEntity) {
        this.saleItemRepository = saleItemRepository;
        this.saleItemMapperEntity = saleItemMapperEntity;
    }

    @Override
    public List<SaleItem> findAll() {
        return saleItemRepository.findAll()
                .stream()
                .map(saleItemMapperEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleItem> findBySaleId(Long saleId) {
        return saleItemRepository.findBySaleId(saleId)
                .stream()
                .map(saleItemMapperEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleItem> findByBookId(Long bookId) {
        return saleItemRepository.findByBookId(bookId)
                .stream()
                .map(saleItemMapperEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SaleItem> findById(Long id) {
        return saleItemRepository.findById(id).map(saleItemMapperEntity::toModel);
    }

    @Override
    public SaleItem save(SaleItem item) {
        SaleItemEntity entity = saleItemMapperEntity.toEntity(item);
        return saleItemMapperEntity.toModel(saleItemRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        saleItemRepository.deleteById(id);
    }
}

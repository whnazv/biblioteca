package onion.whnazv.biblioteca.infrastructure.mapper.entity;

import onion.whnazv.biblioteca.domain.model.Sale;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.entity.SaleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapperEntity.class, SaleItemMapperEntity.class})
public interface SaleMapperEntity {
    Sale toModel(SaleEntity entity);
    SaleEntity toEntity(Sale model);
}

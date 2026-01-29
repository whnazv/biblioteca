package onion.whnazv.biblioteca.infrastructure.mapper.dto;

import onion.whnazv.biblioteca.domain.model.Sale;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.SaleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapperDto.class, SaleItemMapperDto.class})
public interface SaleMapperDto {

    @Mapping(target = "userId", source = "user.id")
    SaleDto toDto(Sale model);

    Sale toModel(SaleDto dto);
}


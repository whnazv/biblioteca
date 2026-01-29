package onion.whnazv.biblioteca.infrastructure.mapper.dto;

import onion.whnazv.biblioteca.domain.model.SaleItem;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.SaleItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BookMapperDto.class})
public interface SaleItemMapperDto {
    SaleItem toModel(SaleItemDto dto);
    SaleItemDto toDto(SaleItem model);
}

package onion.whnazv.biblioteca.infrastructure.mapper.dto;

import onion.whnazv.biblioteca.domain.model.CartItem;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.CartItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapperDto {

    
    @Mapping(target = "total", expression = "java(item.getTotal())")
    CartItemDto toDto(CartItem item);

    
    @Mapping(target = "quantity", source = "quantity")
    CartItem toModel(CartItemDto dto);
}
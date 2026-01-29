package onion.whnazv.biblioteca.infrastructure.mapper.dto;

import onion.whnazv.biblioteca.domain.model.Cart;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.CartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CartItemMapperDto.class})
public interface CartMapperDto {

    
    @Mapping(target = "total", expression = "java(cart.getTotal())")
    CartDto toDto(Cart cart);

    
    Cart toModel(CartDto dto);
}
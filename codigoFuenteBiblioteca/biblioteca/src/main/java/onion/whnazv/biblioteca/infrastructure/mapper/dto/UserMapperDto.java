package onion.whnazv.biblioteca.infrastructure.mapper.dto;

import onion.whnazv.biblioteca.domain.model.User;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperDto {

    
    User toModel(UserDto dto);

    
    UserDto toDto(User model);
}

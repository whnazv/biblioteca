package onion.whnazv.biblioteca.infrastructure.mapper.dto;

import onion.whnazv.biblioteca.domain.model.User;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.UserRegisterDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegisterMapper {
    User toModel(UserRegisterDto dto);
}

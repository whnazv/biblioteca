package onion.whnazv.biblioteca.infrastructure.mapper.entity;

import onion.whnazv.biblioteca.domain.model.User;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperEntity {
    User toModel(UserEntity entity);
    UserEntity toEntity(User model);
}

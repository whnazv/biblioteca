package onion.whnazv.biblioteca.infrastructure.mapper.entity;

import onion.whnazv.biblioteca.domain.model.Book;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.entity.BookEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapperEntity {
    Book toModel(BookEntity entity);
    BookEntity toEntity(Book model);
}

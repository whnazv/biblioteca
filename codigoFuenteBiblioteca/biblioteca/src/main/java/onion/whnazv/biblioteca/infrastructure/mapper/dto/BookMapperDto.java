package onion.whnazv.biblioteca.infrastructure.mapper.dto;

import onion.whnazv.biblioteca.domain.model.Book;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.BookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapperDto {
    Book toModel(BookDto dto);
    BookDto toDto(Book model);
}

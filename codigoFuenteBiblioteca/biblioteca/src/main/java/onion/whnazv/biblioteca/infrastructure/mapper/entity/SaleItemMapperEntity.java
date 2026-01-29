package onion.whnazv.biblioteca.infrastructure.mapper.entity;

import onion.whnazv.biblioteca.domain.model.SaleItem;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.entity.BookEntity;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.entity.SaleItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleItemMapperEntity {

    @Mapping(target = "bookId", source = "book.id")
    SaleItem toModel(SaleItemEntity entity);

    @Mapping(target = "book", expression = "java(toBookEntity(model.getBookId()))")
    @Mapping(target = "sale", ignore = true)
    SaleItemEntity toEntity(SaleItem model);

    // Método auxiliar para construir el BookEntity
    default BookEntity toBookEntity(Long bookId) {
        if (bookId == null) return null;
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(bookId);
        return bookEntity;
    }
}

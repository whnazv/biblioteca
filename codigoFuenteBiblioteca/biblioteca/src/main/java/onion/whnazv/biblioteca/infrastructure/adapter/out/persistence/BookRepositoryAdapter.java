package onion.whnazv.biblioteca.infrastructure.adapter.out.persistence;

import onion.whnazv.biblioteca.application.port.out.BookRepositoryPort;
import onion.whnazv.biblioteca.domain.model.Book;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.entity.BookEntity;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.repository.BookRepository;
import onion.whnazv.biblioteca.infrastructure.mapper.entity.BookMapperEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookRepositoryAdapter implements BookRepositoryPort {

    private final BookRepository bookRepository;
    private final BookMapperEntity bookMapperEntity;

    public BookRepositoryAdapter(BookRepository bookRepository, BookMapperEntity bookMapperEntity) {
        this.bookRepository = bookRepository;
        this.bookMapperEntity = bookMapperEntity;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id).map(bookMapperEntity::toModel);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapperEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Book save(Book book) {
        BookEntity entity = bookMapperEntity.toEntity(book);
        return bookMapperEntity.toModel(bookRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    @Override
    public List<Book> searchByAnyField(String query) {
        
        List<BookEntity> entities = bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        query, query, query, query);

        return entities.stream()
                .map(bookMapperEntity::toModel)
                .collect(Collectors.toList());
    }

}

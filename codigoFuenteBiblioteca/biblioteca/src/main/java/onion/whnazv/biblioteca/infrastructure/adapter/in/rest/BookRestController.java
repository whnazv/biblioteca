package onion.whnazv.biblioteca.infrastructure.adapter.in.rest;

import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.BookDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.BookMapperDto;
import onion.whnazv.biblioteca.application.port.in.BookUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/books")
public class BookRestController {

    private final BookUseCase bookUseCase;
    private final BookMapperDto bookMapperDto;

    public BookRestController(BookUseCase bookUseCase, BookMapperDto bookMapperDto) {
        this.bookUseCase = bookUseCase;
        this.bookMapperDto = bookMapperDto;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> listBooks() {
        return ResponseEntity.ok(
                bookUseCase.getAllBooks().stream()
                        .map(bookMapperDto::toDto)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> bookDetails(@PathVariable Long id) {
        return bookUseCase.getBookById(id)
                .map(bookMapperDto::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        var savedBook = bookUseCase.saveBook(bookMapperDto.toModel(bookDto));
        return ResponseEntity.ok(bookMapperDto.toDto(savedBook));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        var book = bookMapperDto.toModel(bookDto);
        book.setId(id);
        var updatedBook = bookUseCase.saveBook(book);
        return ResponseEntity.ok(bookMapperDto.toDto(updatedBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookUseCase.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}

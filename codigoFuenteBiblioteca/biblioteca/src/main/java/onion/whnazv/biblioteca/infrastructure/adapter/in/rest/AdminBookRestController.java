package onion.whnazv.biblioteca.infrastructure.adapter.in.rest;

import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.BookDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.BookMapperDto;
import onion.whnazv.biblioteca.application.port.in.BookUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/private/admin/books")
@PreAuthorize("hasRole('ADMIN')")
public class AdminBookRestController {

    private final BookUseCase bookUseCase;
    private final BookMapperDto bookMapperDto;

    public AdminBookRestController(BookUseCase bookUseCase, BookMapperDto bookMapperDto) {
        this.bookUseCase = bookUseCase;
        this.bookMapperDto = bookMapperDto;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> list() {
        return ResponseEntity.ok(
                bookUseCase.getAllBooks().stream()
                        .map(bookMapperDto::toDto)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id) {
        return bookUseCase.getBookById(id)
                .map(bookMapperDto::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestPart("book") BookDto bookDto,
                                              @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        var savedBook = bookUseCase.saveBook(bookMapperDto.toModel(bookDto));
        if (imageFile != null && !imageFile.isEmpty()) {
            bookUseCase.uploadBookImage(savedBook.getId(), imageFile);
        }
        return ResponseEntity.ok(bookMapperDto.toDto(savedBook));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id,
                                              @RequestPart("book") BookDto bookDto,
                                              @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        var book = bookMapperDto.toModel(bookDto);
        book.setId(id);
        var updatedBook = bookUseCase.saveBook(book);
        if (imageFile != null && !imageFile.isEmpty()) {
            bookUseCase.uploadBookImage(updatedBook.getId(), imageFile);
        }
        return ResponseEntity.ok(bookMapperDto.toDto(updatedBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookUseCase.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}

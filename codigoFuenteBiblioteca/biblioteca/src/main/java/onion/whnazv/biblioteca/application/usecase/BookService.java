package onion.whnazv.biblioteca.application.usecase;

import onion.whnazv.biblioteca.application.port.in.BookUseCase;
import onion.whnazv.biblioteca.application.port.out.BookRepositoryPort;
import onion.whnazv.biblioteca.domain.exception.ImageUploadException;
import onion.whnazv.biblioteca.domain.exception.NotFoundException;
import onion.whnazv.biblioteca.domain.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements BookUseCase {

    private final BookRepositoryPort bookRepositoryPort;

    public BookService(BookRepositoryPort bookRepositoryPort) {
        this.bookRepositoryPort = bookRepositoryPort;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepositoryPort.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepositoryPort.findById(id);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepositoryPort.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepositoryPort.deleteById(id);
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return bookRepositoryPort.existsByIsbn(isbn);
    }

    @Override
    public String uploadBookImage(Long bookId, MultipartFile file) {
        Book book = bookRepositoryPort.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Libro no encontrado"));

        try {
            String folderPath = "uploads/images/";
            String fileName = "book_" + bookId + ".jpg";
            Path filePath = Paths.get(folderPath + fileName);

            Files.createDirectories(Paths.get(folderPath));
            Files.write(filePath, file.getBytes());

            book.setImageUrl("/images/" + fileName);
            saveBook(book);

            return "Imagen subida correctamente.";

        } catch (IOException e) {
            throw new ImageUploadException("Error al subir la imagen", e);
        }
    }

    @Override
    public List<Book> searchBooks(String query) {
        if (query == null || query.isBlank()) {
            return bookRepositoryPort.findAll();
        }
        return bookRepositoryPort.searchByAnyField(query);
    }
}

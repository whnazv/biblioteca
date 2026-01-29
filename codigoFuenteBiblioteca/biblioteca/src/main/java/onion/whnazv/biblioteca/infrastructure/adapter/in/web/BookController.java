package onion.whnazv.biblioteca.infrastructure.adapter.in.web;

import onion.whnazv.biblioteca.infrastructure.mapper.dto.BookMapperDto;
import onion.whnazv.biblioteca.application.port.in.BookUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/books")
@PreAuthorize("permitAll()")   
public class BookController {

    private final BookUseCase bookUseCase;
    private final BookMapperDto bookMapperDto;

    public BookController(BookUseCase bookUseCase, BookMapperDto bookMapperDto) {
        this.bookUseCase = bookUseCase;
        this.bookMapperDto = bookMapperDto;
    }

    
    @GetMapping
    public String listBooks(@RequestParam(required = false) String query, Model model) {
        var books = bookUseCase.searchBooks(query).stream()
                .map(bookMapperDto::toDto)
                .toList();

        model.addAttribute("books", books);
        return "public/allBook"; 
    }

    
    @GetMapping("/{id}")
    public String bookDetails(@PathVariable Long id, Model model) {
        var book = bookUseCase.getBookById(id)
                .map(bookMapperDto::toDto)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        model.addAttribute("book", book);
        return "public/details_book"; 
    }
}

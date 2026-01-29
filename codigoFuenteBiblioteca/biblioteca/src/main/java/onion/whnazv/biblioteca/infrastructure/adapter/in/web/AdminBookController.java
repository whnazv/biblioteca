package onion.whnazv.biblioteca.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.BookDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.BookMapperDto;
import onion.whnazv.biblioteca.application.port.in.BookUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/web/admin/books")
@PreAuthorize("hasRole('ADMIN')")
public class AdminBookController {

    private final BookUseCase bookUseCase;
    private final BookMapperDto bookMapperDto;

    public AdminBookController(BookUseCase bookUseCase, BookMapperDto bookMapperDto) {
        this.bookUseCase = bookUseCase;
        this.bookMapperDto = bookMapperDto;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String query, Model model) {
        var books = (query != null && !query.isEmpty())
                ? bookUseCase.searchBooks(query).stream()
                .map(bookMapperDto::toDto)
                .toList()
                : bookUseCase.getAllBooks().stream()
                .map(bookMapperDto::toDto)
                .toList();

        model.addAttribute("books", books);
        return "admin/book/list";
    }


    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new BookDto());
        return "admin/book/form";
    }

    @PostMapping("/save")
    public String saveBook(@Valid @ModelAttribute("book") BookDto bookDto,
                           BindingResult result,
                           @RequestParam("imageFile") MultipartFile imageFile,
                           Model model) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("book", bookDto);
            return "admin/book/form";
        }

        if (bookDto.getId() != null && imageFile.isEmpty()) {
            var existing = bookUseCase.getBookById(bookDto.getId()).orElseThrow();
            bookDto.setImageUrl(existing.getImageUrl());
        }

        var savedBook = bookUseCase.saveBook(bookMapperDto.toModel(bookDto));

        if (!imageFile.isEmpty()) {
            bookUseCase.uploadBookImage(savedBook.getId(), imageFile);
        }

        return "redirect:/web/admin/books";
    }



    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        var book = bookUseCase.getBookById(id)
                .map(bookMapperDto::toDto)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        model.addAttribute("book", book);
        return "admin/book/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookUseCase.deleteBook(id);
        return "redirect:/web/admin/books";
    }
}

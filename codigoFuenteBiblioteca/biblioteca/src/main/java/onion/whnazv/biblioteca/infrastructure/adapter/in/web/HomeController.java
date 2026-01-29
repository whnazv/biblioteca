package onion.whnazv.biblioteca.infrastructure.adapter.in.web;

import onion.whnazv.biblioteca.application.port.in.BookUseCase;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.BookMapperDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model; 

@Controller
public class HomeController {

    private final BookUseCase bookUseCase;
    private final BookMapperDto bookMapperDto;

    public HomeController(BookUseCase bookUseCase, BookMapperDto bookMapperDto) {
        this.bookUseCase = bookUseCase;
        this.bookMapperDto = bookMapperDto;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("books",
                bookUseCase.getAllBooks().stream()
                        .map(bookMapperDto::toDto)
                        .toList()
        );
        return "index";
    }
    @GetMapping("/informacionApi")
    public String informacionApi() {
        return "informacionApi";
    }

    @GetMapping("/informacionWeb")
    public String informacionWeb() {
        return "informacionWeb";
    }

}

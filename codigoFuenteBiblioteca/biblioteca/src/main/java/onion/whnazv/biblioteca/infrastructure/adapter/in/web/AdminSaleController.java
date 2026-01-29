package onion.whnazv.biblioteca.infrastructure.adapter.in.web;

import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.CartDto;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.SaleItemDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.CartMapperDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.SaleMapperDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.SaleItemMapperDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.UserMapperDto;
import onion.whnazv.biblioteca.application.port.in.SaleUseCase;
import onion.whnazv.biblioteca.application.port.in.UserUseCase;
import onion.whnazv.biblioteca.application.port.in.BookUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/admin/sales")
@PreAuthorize("hasRole('ADMIN')")
@SessionAttributes("cart")
public class AdminSaleController {

    private final SaleUseCase saleUseCase;
    private final UserUseCase userUseCase;
    private final BookUseCase bookUseCase;
    private final SaleMapperDto saleMapperDto;
    private final SaleItemMapperDto saleItemMapperDto;
    private final UserMapperDto userMapperDto;
    private final CartMapperDto cartMapperDto;

    public AdminSaleController(SaleUseCase saleUseCase,
                               UserUseCase userUseCase,
                               BookUseCase bookUseCase,
                               SaleMapperDto saleMapperDto,
                               SaleItemMapperDto saleItemMapperDto,
                               UserMapperDto userMapperDto,
                               CartMapperDto cartMapperDto) {
        this.saleUseCase = saleUseCase;
        this.userUseCase = userUseCase;
        this.bookUseCase = bookUseCase;
        this.saleMapperDto = saleMapperDto;
        this.saleItemMapperDto = saleItemMapperDto;
        this.userMapperDto = userMapperDto;
        this.cartMapperDto = cartMapperDto;
    }

    @ModelAttribute("cart")
    public CartDto cart() {
        return new CartDto();
    }

    @GetMapping
    public String listSales(@RequestParam(required = false) String query, Model model) {
        var sales = (query == null || query.isBlank())
                ? saleUseCase.getAllSales()
                : saleUseCase.searchSales(query);

        model.addAttribute("sales", sales.stream()
                .map(saleMapperDto::toDto)
                .toList());

        return "admin/sale/list";
    }

    @GetMapping("/new")
    public String newSale(Model model, @ModelAttribute("cart") CartDto cartDto) {
        model.addAttribute("books", bookUseCase.getAllBooks());
        model.addAttribute("users", userUseCase.findAllUsers().stream().map(userMapperDto::toDto).toList());
        model.addAttribute("cart", cartDto);
        return "admin/sale/new";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long bookId,
                            @RequestParam int quantity,
                            @ModelAttribute("cart") CartDto cartDto,
                            Model model) {

        var book = bookUseCase.getBookById(bookId).orElseThrow();

        var cart = cartMapperDto.toModel(cartDto);
        cart.addItem(book.getId(), book.getTitle(), book.getPrice(), quantity);

        model.addAttribute("cart", cartMapperDto.toDto(cart));
        return "redirect:/web/admin/sales/new";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam Long userId,
                           @ModelAttribute("cart") CartDto cartDto,
                           Model model) {

        var user = userUseCase.findById(userId).orElseThrow();
        var cart = cartMapperDto.toModel(cartDto);

        var saleItems = cart.getItems().stream()
                .map(item -> {
                    var dto = new SaleItemDto();
                    dto.setBookId(item.getBookId());
                    dto.setQuantity(item.getQuantity());
                    dto.setUnitPrice(item.getPrice());
                    return saleItemMapperDto.toModel(dto);
                })
                .toList();

        saleUseCase.createSale(user, saleItems);

        cart.clear();
        model.addAttribute("cart", cartMapperDto.toDto(cart));
        return "redirect:/web/admin/sales";
    }

    @PostMapping("/clear")
    public String clearCart(@ModelAttribute("cart") CartDto cartDto, Model model) {
        var cart = cartMapperDto.toModel(cartDto);
        cart.clear();
        model.addAttribute("cart", cartMapperDto.toDto(cart));
        return "redirect:/web/admin/sales/new";
    }

    @PostMapping("/remove")
    public String removeItem(@RequestParam Long bookId,
                             @ModelAttribute("cart") CartDto cartDto,
                             Model model) {
        var cart = cartMapperDto.toModel(cartDto);
        cart.removeItem(bookId);
        model.addAttribute("cart", cartMapperDto.toDto(cart));
        return "redirect:/web/admin/sales/new";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long bookId,
                                 @RequestParam int quantity,
                                 @ModelAttribute("cart") CartDto cartDto,
                                 Model model) {
        var cart = cartMapperDto.toModel(cartDto);
        cart.updateQuantity(bookId, quantity);
        model.addAttribute("cart", cartMapperDto.toDto(cart));
        return "redirect:/web/admin/sales/new";
    }

    @GetMapping("/{id}")
    public String viewSale(@PathVariable Long id, Model model) {
        var sale = saleMapperDto.toDto(saleUseCase.getSaleById(id));
        model.addAttribute("sale", sale);
        return "admin/sale/detail";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        var sale = saleMapperDto.toDto(saleUseCase.getSaleById(id));
        model.addAttribute("sale", sale);
        model.addAttribute("books", bookUseCase.getAllBooks());
        model.addAttribute("users", userUseCase.findAllUsers().stream().map(userMapperDto::toDto).toList());
        return "admin/sale/edit";
    }

    @PostMapping("/update/{id}")
    public String updateSale(@PathVariable Long id,
                             @RequestParam Long userId,
                             @ModelAttribute SaleItemDto saleItemDto) {
        var user = userUseCase.findById(userId).orElseThrow();
        saleUseCase.updateSale(id, user, List.of(saleItemMapperDto.toModel(saleItemDto)));
        return "redirect:/web/admin/sales";
    }

    @GetMapping("/delete/{id}")
    public String deleteSale(@PathVariable Long id) {
        saleUseCase.deleteSale(id);
        return "redirect:/web/admin/sales";
    }
}

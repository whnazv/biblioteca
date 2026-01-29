package onion.whnazv.biblioteca.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import onion.whnazv.biblioteca.domain.model.User;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.UserDto;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.SaleDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.UserMapperDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.SaleMapperDto;
import onion.whnazv.biblioteca.application.port.in.SaleUseCase;
import onion.whnazv.biblioteca.application.port.in.UserUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/web/client")
@PreAuthorize("hasAnyRole('CLIENTE','ADMIN')") 
public class ClientSaleController {

    private final SaleUseCase saleUseCase;
    private final UserUseCase userUseCase;
    private final UserMapperDto userMapperDto;
    private final SaleMapperDto saleMapperDto;

    public ClientSaleController(SaleUseCase saleUseCase,
                                UserUseCase userUseCase,
                                UserMapperDto userMapperDto,
                                SaleMapperDto saleMapperDto) {
        this.saleUseCase = saleUseCase;
        this.userUseCase = userUseCase;
        this.userMapperDto = userMapperDto;
        this.saleMapperDto = saleMapperDto;
    }

    
    private UserDto getAuthenticatedUser(Authentication authentication) {
        String email = authentication.getName();
        return userUseCase.findByEmail(email)
                .map(userMapperDto::toDto)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    
    private List<SaleDto> getUserSales(Long userId) {
        return saleUseCase.getSalesByUserId(userId).stream()
                .map(saleMapperDto::toDto)
                .toList();
    }

    @GetMapping("/perfil")
    public String perfil(Model model, Authentication authentication) {
        UserDto user = getAuthenticatedUser(authentication);
        model.addAttribute("user", user);
        model.addAttribute("sales", getUserSales(user.getId()));
        return "client/perfil";
    }

    
    @GetMapping("/purchases")
    public String purchases(Model model, Authentication authentication) {
        UserDto user = getAuthenticatedUser(authentication);
        model.addAttribute("sales", getUserSales(user.getId()));
        return "client/details_sales";
    }

    @GetMapping("/edit")
    public String editProfile(Model model, Authentication authentication) {
        UserDto user = getAuthenticatedUser(authentication);
        model.addAttribute("user", user);
        return "client/edit_profile";
    }

    @PostMapping("/update")
    public String updateProfile(@Valid @ModelAttribute("user") UserDto userDto,
                                BindingResult result,
                                Authentication authentication,
                                Model model) {

        UserDto current = getAuthenticatedUser(authentication);
        userDto.setId(current.getId());

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "client/edit_profile";
        }

        User userDomain = userMapperDto.toModel(userDto);
        userUseCase.updateUser(userDto.getId(), userDomain);

        return "redirect:/web/client/perfil";
    }

    @GetMapping("/purchases/search")
    public String searchPurchases(@RequestParam String query,
                                  Model model,
                                  Authentication authentication) {
        UserDto user = getAuthenticatedUser(authentication);

        // Buscar todas las ventas que coincidan con el query
        var sales = saleUseCase.searchSales(query).stream()
                // Filtrar solo las ventas del usuario autenticado
                .filter(s -> s.getUser().getId().equals(user.getId()))
                .map(saleMapperDto::toDto)
                .toList();

        model.addAttribute("sales", sales);
        return "client/details_sales";
    }



}

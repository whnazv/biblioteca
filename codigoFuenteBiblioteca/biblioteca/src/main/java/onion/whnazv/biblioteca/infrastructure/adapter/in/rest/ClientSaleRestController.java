package onion.whnazv.biblioteca.infrastructure.adapter.in.rest;

import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.SaleDto;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.UserDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.SaleMapperDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.UserMapperDto;
import onion.whnazv.biblioteca.application.port.in.SaleUseCase;
import onion.whnazv.biblioteca.application.port.in.UserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/client")
public class ClientSaleRestController {

    private final SaleUseCase saleUseCase;
    private final UserUseCase userUseCase;
    private final SaleMapperDto saleMapperDto;
    private final UserMapperDto userMapperDto;

    public ClientSaleRestController(SaleUseCase saleUseCase,
                                    UserUseCase userUseCase,
                                    SaleMapperDto saleMapperDto,
                                    UserMapperDto userMapperDto) {
        this.saleUseCase = saleUseCase;
        this.userUseCase = userUseCase;
        this.saleMapperDto = saleMapperDto;
        this.userMapperDto = userMapperDto;
    }

    
    @GetMapping("/perfil")
    public ResponseEntity<UserDto> perfil(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }

        String email = authentication.getName();
        var user = userUseCase.findByEmail(email)
                .map(userMapperDto::toDto)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(user);
    }

    
    @GetMapping("/purchases")
    public ResponseEntity<List<SaleDto>> purchases(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }

        String email = authentication.getName();
        var user = userUseCase.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        var sales = saleUseCase.getSalesByUserId(user.getId()).stream()
                .map(saleMapperDto::toDto)
                .toList();

        return ResponseEntity.ok(sales);
    }

    @GetMapping("/purchases/{saleId}")
    public ResponseEntity<SaleDto> getSale(@PathVariable Long saleId, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }

        String email = authentication.getName();
        var user = userUseCase.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        
        var sale = saleUseCase.getSaleById(saleId);

        
        if (sale.getUser() == null || !sale.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build(); 
        }

        return ResponseEntity.ok(saleMapperDto.toDto(sale));
    }
}

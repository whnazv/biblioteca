package onion.whnazv.biblioteca.infrastructure.adapter.in.rest;

import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.SaleDto;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.SaleItemDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.SaleItemMapperDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.SaleMapperDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.UserMapperDto;
import onion.whnazv.biblioteca.application.port.in.SaleUseCase;
import onion.whnazv.biblioteca.application.port.in.UserUseCase;
import onion.whnazv.biblioteca.application.port.in.BookUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/admin/sales")
@PreAuthorize("hasRole('ADMIN')")
public class AdminSaleRestController {

    private final SaleUseCase saleUseCase;
    private final UserUseCase userUseCase;
    private final BookUseCase bookUseCase;
    private final SaleMapperDto saleMapperDto;
    private final SaleItemMapperDto saleItemMapperDto;
    private final UserMapperDto userMapperDto;

    public AdminSaleRestController(SaleUseCase saleUseCase,
                                   UserUseCase userUseCase,
                                   BookUseCase bookUseCase,
                                   SaleMapperDto saleMapperDto,
                                   SaleItemMapperDto saleItemMapperDto,
                                   UserMapperDto userMapperDto) {
        this.saleUseCase = saleUseCase;
        this.userUseCase = userUseCase;
        this.bookUseCase = bookUseCase;
        this.saleMapperDto = saleMapperDto;
        this.saleItemMapperDto = saleItemMapperDto;
        this.userMapperDto = userMapperDto;
    }

    @GetMapping
    public ResponseEntity<List<SaleDto>> listSales() {
        return ResponseEntity.ok(
                saleUseCase.getAllSales().stream()
                        .map(saleMapperDto::toDto)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDto> viewSale(@PathVariable Long id) {
        var sale = saleUseCase.getSaleById(id);
        return ResponseEntity.ok(saleMapperDto.toDto(sale));
    }

    @PostMapping
    public ResponseEntity<SaleDto> createSale(@RequestParam Long userId,
                                              @RequestBody SaleItemDto saleItemDto) {
        var user = userUseCase.findById(userId).orElseThrow();
        var sale = saleUseCase.createSale(user, List.of(saleItemMapperDto.toModel(saleItemDto)));
        return ResponseEntity.ok(saleMapperDto.toDto(sale));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDto> updateSale(@PathVariable Long id,
                                              @RequestParam Long userId,
                                              @RequestBody SaleItemDto saleItemDto) {
        var user = userUseCase.findById(userId).orElseThrow();
        var updatedSale = saleUseCase.updateSale(id, user, List.of(saleItemMapperDto.toModel(saleItemDto)));
        return ResponseEntity.ok(saleMapperDto.toDto(updatedSale));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        saleUseCase.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}

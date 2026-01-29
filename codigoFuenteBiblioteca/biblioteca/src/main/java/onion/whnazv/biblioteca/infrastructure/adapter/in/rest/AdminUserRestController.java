package onion.whnazv.biblioteca.infrastructure.adapter.in.rest;

import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.UserDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.UserMapperDto;
import onion.whnazv.biblioteca.application.port.in.UserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/private/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserRestController {

    private final UserUseCase userUseCase;
    private final UserMapperDto userMapperDto;

    public AdminUserRestController(UserUseCase userUseCase, UserMapperDto userMapperDto) {
        this.userUseCase = userUseCase;
        this.userMapperDto = userMapperDto;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> listUsers() {
        return ResponseEntity.ok(
                userUseCase.findAllUsers().stream()
                        .map(userMapperDto::toDto)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return userUseCase.findById(id)
                .map(userMapperDto::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        var savedUser = userUseCase.registerUser(userMapperDto.toModel(userDto));
        return ResponseEntity.ok(userMapperDto.toDto(savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userUseCase.updateUser(id, userMapperDto.toModel(userDto))
                .map(userMapperDto::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

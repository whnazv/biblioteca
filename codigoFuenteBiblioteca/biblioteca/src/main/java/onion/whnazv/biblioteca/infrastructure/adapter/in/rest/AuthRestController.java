package onion.whnazv.biblioteca.infrastructure.adapter.in.rest;


import onion.whnazv.biblioteca.application.usecase.UserService;
import onion.whnazv.biblioteca.domain.model.User;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.UserLoginDto;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.UserRegisterDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.UserRegisterMapper;
import onion.whnazv.biblioteca.infrastructure.security.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/auth")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserRegisterMapper userRegisterMapper;

    public AuthRestController(AuthenticationManager authenticationManager,
                              JwtUtil jwtUtil,
                              UserService userService,
                              UserRegisterMapper userRegisterMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userRegisterMapper = userRegisterMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        String token = jwtUtil.generateToken(authentication);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto dto) {
        // Usamos el mapper para convertir el DTO en User
        User user = userRegisterMapper.toModel(dto);

        User saved = userService.registerUser(user);

        return ResponseEntity.ok(saved);
    }
}



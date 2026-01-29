package onion.whnazv.biblioteca.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.UserDto;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.UserDtoCredenciales;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.UserLoginDto;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.UserRegisterDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.UserMapperDto;
import onion.whnazv.biblioteca.application.port.in.UserUseCase;
import onion.whnazv.biblioteca.application.port.in.SaleUseCase;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.UserRegisterMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/auth")
public class AuthController {

    private final UserUseCase userUseCase;
    private final SaleUseCase saleUseCase;
    private final UserMapperDto userMapperDto;
    private final PasswordEncoder passwordEncoder;
    private final UserRegisterMapper userRegisterMapper;


    public AuthController(UserUseCase userUseCase,
                          SaleUseCase saleUseCase,
                          UserMapperDto userMapperDto,
                          UserRegisterMapper userRegisterMapper,
                          PasswordEncoder passwordEncoder) {
        this.userUseCase = userUseCase;
        this.saleUseCase = saleUseCase;
        this.userMapperDto = userMapperDto;
        this.userRegisterMapper = userRegisterMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "public/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@Valid @ModelAttribute("login") UserLoginDto dto,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return "public/login";
        }

        boolean ok = userUseCase.login(dto.getEmail(), dto.getPassword());

        if (!ok) {
            model.addAttribute("error", "Credenciales inválidas");
            return "public/login";
        }

        return "redirect:/web/auth/perfil";
    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("register", new UserRegisterDto());
        return "public/register";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid @ModelAttribute("register") UserRegisterDto dto,
                               BindingResult result,
                               Model model) {

        if (result.hasErrors()) {
            model.addAttribute("register", dto);
            return "public/register";
        }

        userUseCase.registerUser(userRegisterMapper.toModel(dto));
        return "redirect:/web/auth/login";
    }



    @GetMapping("/perfil")
    public String perfil(Model model, Authentication authentication) {
        String email = authentication.getName();
        var usuario = userUseCase.findByEmail(email)
                .map(userMapperDto::toDto)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("user", usuario);
        model.addAttribute("sales", saleUseCase.getSalesByUserId(usuario.getId()));

        if ("ADMIN".equalsIgnoreCase(usuario.getRole())) {
            return "admin/perfil";
        } else {
            return "client/perfil";
        }
    }

    
    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("credenciales", new UserDtoCredenciales());
        return "client/edit_password"; 
    }

    
    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute("credenciales") UserDtoCredenciales dto,
                                 Authentication authentication,
                                 Model model) {
        String email = authentication.getName();

        var usuarioOpt = userUseCase.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            model.addAttribute("error", "Usuario no encontrado");
            return "client/edit_password";
        }

        var usuario = usuarioOpt.get(); 

        
        if (!passwordEncoder.matches(dto.getOldPassword(), usuario.getPassword())) {
            model.addAttribute("error", "La contraseña actual es incorrecta");
            return "client/edit_password";
        }

        
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "client/edit_password";
        }

        
        usuario.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userUseCase.updateUser(usuario.getId(), usuario);

        
        return "redirect:/web/auth/perfil";
    }
}

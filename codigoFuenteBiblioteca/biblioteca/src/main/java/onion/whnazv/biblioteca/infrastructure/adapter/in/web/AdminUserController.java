package onion.whnazv.biblioteca.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.UserDto;
import onion.whnazv.biblioteca.infrastructure.adapter.in.dto.UserRegisterDto;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.UserMapperDto;
import onion.whnazv.biblioteca.application.port.in.UserUseCase;
import onion.whnazv.biblioteca.infrastructure.mapper.dto.UserRegisterMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserUseCase userUseCase;
    private final UserMapperDto userMapperDto;
    private final UserRegisterMapper userRegisterMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminUserController(UserUseCase userUseCase,
                               UserMapperDto userMapperDto,
                               UserRegisterMapper userRegisterMapper,
                               PasswordEncoder passwordEncoder) {
        this.userUseCase = userUseCase;
        this.userMapperDto = userMapperDto;
        this.userRegisterMapper = userRegisterMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping
    public String listUsers(@RequestParam(required = false) String query, Model model) {
        var users = (query == null || query.isBlank())
                ? userUseCase.findAllUsers()
                : userUseCase.searchUsers(query);

        model.addAttribute("usuarios",
                users.stream()
                        .map(userMapperDto::toDto)
                        .toList());

        return "admin/users/users_list";
    }



    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDto());
        return "admin/users/formusers";
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user") UserRegisterDto userDto,
                           BindingResult result,
                           Model model) {

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "admin/users/formusers";
        }

        // Convertir con el mapper de registro
        var entity = userRegisterMapper.toModel(userDto);

        // Codificar la contraseña
        entity.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userUseCase.registerUser(entity);
        return "redirect:/web/admin/users";
    }


    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id,
                             @Valid @ModelAttribute("user") UserDto userDto,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "admin/users/formusers";
        }

        userUseCase.updateUser(id, userMapperDto.toModel(userDto));
        return "redirect:/web/admin/users";
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        var user = userUseCase.findById(id).map(userMapperDto::toDto);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "admin/users/formusers";
        }
        return "redirect:/web/admin/users";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userUseCase.deleteUser(id);
        return "redirect:/web/admin/users";
    }
}

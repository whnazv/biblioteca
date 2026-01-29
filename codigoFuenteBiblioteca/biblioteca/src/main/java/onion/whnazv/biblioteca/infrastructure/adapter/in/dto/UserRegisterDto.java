package onion.whnazv.biblioteca.infrastructure.adapter.in.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {
    private String username;
    private String firstName;
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}

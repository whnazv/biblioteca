package onion.whnazv.biblioteca.infrastructure.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoCredenciales {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}

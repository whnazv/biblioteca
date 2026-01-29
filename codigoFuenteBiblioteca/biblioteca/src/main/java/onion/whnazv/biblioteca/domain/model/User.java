package onion.whnazv.biblioteca.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String password; 
    private String firstName;
    private String lastName;
    private String email;
    private String role = "CLIENTE";
}

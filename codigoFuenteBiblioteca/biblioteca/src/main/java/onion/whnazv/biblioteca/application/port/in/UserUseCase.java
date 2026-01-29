package onion.whnazv.biblioteca.application.port.in;

import onion.whnazv.biblioteca.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserUseCase {

    User registerUser(User user);

    boolean login(String email, String rawPassword);

    List<User> findAllUsers();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> updateUser(Long id, User updatedUser);

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    List<User> findByRole(String role);

    boolean deleteUser(Long id);

    List<User> searchUsers(String query);

}

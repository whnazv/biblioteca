package onion.whnazv.biblioteca.application.port.out;

import onion.whnazv.biblioteca.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    List<User> findByRole(String role);

    boolean existsById(Long id);

    void deleteById(Long id);
    List<User> search(String query);

}

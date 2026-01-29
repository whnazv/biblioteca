package onion.whnazv.biblioteca.application.usecase;

import onion.whnazv.biblioteca.application.port.in.UserUseCase;
import onion.whnazv.biblioteca.application.port.out.UserRepositoryPort;
import onion.whnazv.biblioteca.domain.exception.UserAlreadyExistsException;
import onion.whnazv.biblioteca.domain.exception.ValidationException;
import onion.whnazv.biblioteca.domain.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepositoryPort userRepositoryPort, PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {

        if (userRepositoryPort.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("El email ya está registrado.");
        }

        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("CLIENTE");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepositoryPort.save(user);
    }

    @Override
    public boolean login(String email, String rawPassword) {
        return userRepositoryPort.findByEmail(email)
                .map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepositoryPort.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepositoryPort.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepositoryPort.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepositoryPort.findByEmail(email);
    }

    @Override
    public Optional<User> updateUser(Long id, User updatedUser) {

        return userRepositoryPort.findById(id).map(existingUser -> {

            if (!existingUser.getEmail().equals(updatedUser.getEmail())) {
                userRepositoryPort.findByEmail(updatedUser.getEmail()).ifPresent(owner -> {
                    if (!owner.getId().equals(id)) {
                        throw new UserAlreadyExistsException("El nuevo email ya está en uso.");
                    }
                });

                existingUser.setEmail(updatedUser.getEmail());
            }

            existingUser.setUsername(updatedUser.getUsername());

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            existingUser.setRole(updatedUser.getRole());

            return userRepositoryPort.save(existingUser);
        });
    }

    @Override
    public List<User> findByFirstName(String firstName) {
        return userRepositoryPort.findByFirstName(firstName);
    }

    @Override
    public List<User> findByLastName(String lastName) {
        return userRepositoryPort.findByLastName(lastName);
    }

    @Override
    public List<User> findByRole(String role) {
        return userRepositoryPort.findByRole(role);
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepositoryPort.existsById(id)) {
            userRepositoryPort.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    public List<User> searchUsers(String query) {
        return userRepositoryPort.search(query);
    }

}

package onion.whnazv.biblioteca.infrastructure.adapter.out.persistence;

import onion.whnazv.biblioteca.application.port.out.UserRepositoryPort;
import onion.whnazv.biblioteca.domain.model.User;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.entity.UserEntity;
import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.repository.UserRepository;
import onion.whnazv.biblioteca.infrastructure.mapper.entity.UserMapperEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;
    private final UserMapperEntity userMapperEntity;

    public UserRepositoryAdapter(UserRepository userRepository, UserMapperEntity userMapperEntity) {
        this.userRepository = userRepository;
        this.userMapperEntity = userMapperEntity;
    }

    @Override
    public User save(User user) {
        UserEntity entity = userMapperEntity.toEntity(user);
        return userMapperEntity.toModel(userRepository.save(entity));
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id).map(userMapperEntity::toModel);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapperEntity::toModel);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username).map(userMapperEntity::toModel);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream().map(userMapperEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public List<User> findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName).stream().map(userMapperEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public List<User> findByLastName(String lastName) {
        return userRepository.findByLastName(lastName).stream().map(userMapperEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public List<User> findByRole(String role) {
        return userRepository.findByRole(role).stream().map(userMapperEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> search(String query) {
        return userRepository.searchUsers(query)
                .stream()
                .map(userMapperEntity::toModel)
                .toList();
    }

}

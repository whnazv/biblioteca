package onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.repository;

import onion.whnazv.biblioteca.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findByFirstName(String firstName);
    List<UserEntity> findByLastName(String lastName);
    List<UserEntity> findByRole(String role);
    @Query("SELECT u FROM UserEntity u " +
            "WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "   OR LOWER(u.role) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<UserEntity> searchUsers(String query);

}

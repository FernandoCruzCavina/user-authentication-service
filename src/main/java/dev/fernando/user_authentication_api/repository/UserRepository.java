package dev.fernando.user_authentication_api.repository;

import dev.fernando.user_authentication_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 * Provides methods to perform CRUD operations on User entities.
 * 
 * @author Fernando Cruz Cavina
 * @since 1.0.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

}

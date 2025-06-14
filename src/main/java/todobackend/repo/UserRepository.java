package backend.todo.todobackend.repo;

import backend.todo.todobackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entities.
 *
 * Provides methods to perform CRUD operations and user-specific queries.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     *
     * @param email user's email
     * @return optional containing the user, if found
     */
    Optional<User> findByEmail(String email);
}

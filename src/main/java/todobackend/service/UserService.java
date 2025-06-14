package backend.todo.todobackend.service;

import backend.todo.todobackend.entity.User;
import backend.todo.todobackend.repo.UserRepository;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

/**
 * Service class for accessing and managing User entities.
 */
@Service
public class UserService {
    private final UserRepository repo;

    /**
     * Constructs a UserService with the given UserRepository.
     *
     * @param repo the user repository
     */
    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the user ID
     * @return the found User
     * @throws NoSuchElementException if no user with the given ID is found
     */
    public User findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + id));
    }

    /**
     * Finds a user by their email.
     *
     * @param email the user's email
     * @return the found User
     * @throws NoSuchElementException if no user with the given email is found
     */
    public User findByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + email));
    }
}


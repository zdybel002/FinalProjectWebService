package backend.todo.todobackend.service;

import backend.todo.todobackend.entity.User;
import backend.todo.todobackend.repo.UserRepository;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + id));
    }

    public User findByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + email));
    }
}

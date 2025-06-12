package backend.todo.todobackend.controller;

import backend.todo.todobackend.entity.Profile;
import backend.todo.todobackend.entity.User;
import backend.todo.todobackend.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository repo;

    public ProfileController(UserRepository repo) {
        this.repo = repo;
    }

    // GET your saved profile
    @PostMapping("/get")
    public Profile get(@RequestBody Long userId) {
        return repo.findById(userId)
                .map(u -> new Profile(u.getId(), u.getLocation()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // SAVE updated location
    @PutMapping("/update")
    public void update(@RequestBody Profile dto) {
        User u = repo.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        u.setLocation(dto.getLocation());
        repo.save(u);
    }
}

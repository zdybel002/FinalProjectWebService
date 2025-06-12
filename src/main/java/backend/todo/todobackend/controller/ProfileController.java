package backend.todo.todobackend.controller;

import backend.todo.todobackend.entity.Profile;
import backend.todo.todobackend.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository repo;

    public ProfileController(UserRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/get")
    public ResponseEntity<Profile> get(@RequestBody Long userId) {
        return repo.findById(userId)
                .map(u -> new Profile(u.getId(), u.getLocation()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody Profile dto) {
        return repo.findById(dto.getUserId())
                .map(u -> {
                    u.setLocation(dto.getLocation());
                    repo.save(u);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}

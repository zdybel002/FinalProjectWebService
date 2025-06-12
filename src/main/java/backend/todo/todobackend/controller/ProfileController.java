package backend.todo.todobackend.controller;

import backend.todo.todobackend.entity.ProfileDto;
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

    @PostMapping("/get")
    public ProfileDto get(@RequestBody Long userId) {
        var u = repo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new ProfileDto(u.getId(), u.getLocation(), u.getTelegramChat());
    }

    @PutMapping("/update")
    public void update(@RequestBody ProfileDto dto) {
        var u = repo.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        u.setLocation(dto.getLocation());
        u.setTelegramChat(dto.getChatId());   // ← must call this
        repo.save(u);
    }
}

package backend.todo.todobackend.controller;

import backend.todo.todobackend.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/telegram")
public class TelegramController {
    private final UserRepository repo;

    public TelegramController(UserRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody Map<String,Long> body) {
        Long userId = body.get("userId");
        Long chatId = body.get("chatId");
        return repo.findById(userId)
                .map(u -> {
                    u.setTelegramChat(chatId);
                    repo.save(u);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


}

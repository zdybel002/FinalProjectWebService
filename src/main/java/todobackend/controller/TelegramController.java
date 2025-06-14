package backend.todo.todobackend.controller;

import backend.todo.todobackend.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * TelegramController links a user's account with their Telegram chat.
 *
 * POST /telegram/register — saves Telegram chat ID for a user
 *
 * Uses UserRepository to update user records.
 */
@RestController
@RequestMapping("/telegram")
public class TelegramController {

    private final UserRepository repo;

    /**
     * Constructor with injected UserRepository.
     */
    public TelegramController(UserRepository repo) {
        this.repo = repo;
    }

    /**
     * Registers a Telegram chat ID for the given user.
     * Expects userId and chatId in request body.
     * → 200 OK or 404 Not Found
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody Map<String, Long> body) {
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

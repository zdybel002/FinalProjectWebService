package backend.todo.todobackend.controller;

import backend.todo.todobackend.entity.Stat;
import backend.todo.todobackend.service.StatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * StatController handles retrieving statistics for a user by their email.
 *
 * POST /stat — returns a Stat object based on email input.
 *
 * Uses StatService to fetch data.
 */
@RestController
public class StatController {

    private final StatService statService;

    /**
     * Constructor with injected StatService.
     */
    public StatController(StatService statService) {
        this.statService = statService;
    }

    /**
     * Returns user statistics by email.
     * Responds with 200 OK and the corresponding Stat object.
     */
    @PostMapping("/stat")
    public ResponseEntity<Stat> findByEmail(@RequestBody String email) {
        return ResponseEntity.ok(statService.findStat(email));
    }
}

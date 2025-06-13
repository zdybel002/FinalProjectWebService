package backend.todo.todobackend.controller;

import backend.todo.todobackend.entity.Task;
import backend.todo.todobackend.entity.User;
import backend.todo.todobackend.requests.TaskRequest;
import backend.todo.todobackend.service.TaskService;
import backend.todo.todobackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService,
                          UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    /** Fetch all tasks for a given user ID */
    @PostMapping("/all")
    public ResponseEntity<List<Task>> findAll(@RequestBody Long userId) {
        List<Task> tasks = taskService.findAllByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    /** Create a new task */
    @PostMapping("/add")
    public ResponseEntity<Task> add(@RequestBody TaskRequest req) {
        // Basic validation
        if (req.getId() != null
                || req.getTitle() == null || req.getTitle().isBlank()
                || req.getUserId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        User u = userService.findById(req.getUserId());
        if (u == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        Task t = new Task();
        t.setTitle(req.getTitle());
        t.setCompleted(Boolean.TRUE.equals(req.getCompleted()));
        t.setTaskDate(parseDateTime(req.getTaskDate()));
        t.setUser(u);

        Task saved = taskService.add(t);
        return ResponseEntity.ok(saved);
    }

    /** Update an existing task */
    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody TaskRequest req) {
        if (req.getId() == null || req.getUserId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        Task t = taskService.findById(req.getId());
        if (t == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        User u = userService.findById(req.getUserId());
        if (u == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        t.setTitle(req.getTitle());
        t.setCompleted(Boolean.TRUE.equals(req.getCompleted()));
        t.setTaskDate(parseDateTime(req.getTaskDate()));
        t.setUser(u);

        taskService.update(t);
        return ResponseEntity.ok(t);
    }

    /** Delete by ID */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /** Fetch a single task by ID */
    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        Task t = taskService.findById(id);
        if (t == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(t);
    }

    /**
     * Parses either an offset-bearing ISO datetime or a plain ISO datetime
     * into a LocalDateTime. Returns null if the input is null or blank.
     */
    private LocalDateTime parseDateTime(String s) {
        if (s == null || s.isBlank()) {
            return null;
        }
        try {
            // handles "2025-06-14T02:25:00+03:00" or "2025-06-14T02:25:00Z"
            if (s.contains("+") || s.endsWith("Z")) {
                return OffsetDateTime.parse(s)
                        .toLocalDateTime();
            }
            // handles "2025-06-14T23:11:00"
            return LocalDateTime.parse(s);
        } catch (Exception ex) {
            // you might want to log this
            throw new IllegalArgumentException(
                    "Invalid taskDate format, expected ISO-8601", ex);
        }
    }
}
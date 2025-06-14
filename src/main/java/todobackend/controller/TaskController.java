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

/**
 * TaskController manages CRUD operations for tasks.
 *
 * POST /task/all     — fetch all tasks by user ID
 * POST /task/add     — create a new task
 * PUT  /task/update  — update an existing task
 * DELETE /task/delete/{id} — delete task by ID
 * GET  /task/{id}    — get a single task by ID
 *
 * Uses TaskService and UserService.
 */
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

    /**
     * Returns all tasks for a specific user.
     * → 200 OK
     */
    @PostMapping("/all")
    public ResponseEntity<List<Task>> findAll(@RequestBody Long userId) {
        List<Task> tasks = taskService.findAllByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Creates a new task after validating the request.
     * → 200 OK or 406 Not Acceptable
     */
    @PostMapping("/add")
    public ResponseEntity<Task> add(@RequestBody TaskRequest req) {
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

    /**
     * Updates an existing task if found.
     * → 200 OK or 406 Not Acceptable
     */
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

    /**
     * Deletes a task by its ID.
     * → 200 OK
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Returns a single task by ID.
     * → 200 OK or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        Task t = taskService.findById(id);
        if (t == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(t);
    }

    /**
     * Parses ISO-8601 date strings into LocalDateTime.
     * Supports time zone offsets or plain date-time.
     */
    private LocalDateTime parseDateTime(String s) {
        if (s == null || s.isBlank()) {
            return null;
        }
        try {
            if (s.contains("+") || s.endsWith("Z")) {
                return OffsetDateTime.parse(s).toLocalDateTime();
            }
            return LocalDateTime.parse(s);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid taskDate format, expected ISO-8601", ex);
        }
    }
}

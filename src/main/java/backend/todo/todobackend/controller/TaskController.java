package backend.todo.todobackend.controller;

import backend.todo.todobackend.entity.Category;
import backend.todo.todobackend.entity.Task;
import backend.todo.todobackend.entity.User;
import backend.todo.todobackend.requests.TaskRequest;
import backend.todo.todobackend.service.TaskService;
import backend.todo.todobackend.service.UserService; // or UserRepository directly
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService     taskService;
    private final UserService     userService;    // you can also use UserRepository

    public TaskController(TaskService taskService,
                          UserService userService) {
        this.taskService     = taskService;
        this.userService     = userService;
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
        // 1) Validate
        if (req.id != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        if (req.title == null || req.title.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        if (req.userId == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }

        User user = userService.findById(req.userId);

        // 3) Build Task entity
        Task t = new Task();
        t.setTitle(req.title);
        t.setCompleted(req.completed != null && req.completed);
        if (req.taskDate != null) {
            t.setTaskDate(req.taskDate.toLocalDateTime());
        }
        t.setUser(userService.findById(req.userId));
        Task saved = taskService.add(t);
        return ResponseEntity.ok(saved);
    }

    /** Update an existing task */
    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody TaskRequest r) {
        Task existing = taskService.findById(r.id);
        existing.setTitle(r.title);
        existing.setCompleted(r.completed != null && r.completed);
        if (r.taskDate != null) {
            existing.setTaskDate(r.taskDate.toLocalDateTime());
        }
        existing.setUser(userService.findById(r.userId));
        taskService.update(existing);
        return ResponseEntity.ok(existing);
    }

    /** Delete by ID */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            taskService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    /** Fetch a single task by ID */
    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        try {
            Task t = taskService.findById(id);
            return ResponseEntity.ok(t);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }
}

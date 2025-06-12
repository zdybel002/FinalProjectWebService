package backend.todo.todobackend.controller;

import backend.todo.todobackend.entity.Category;
import backend.todo.todobackend.entity.Task;
import backend.todo.todobackend.entity.User;
import backend.todo.todobackend.requests.TaskRequest;
import backend.todo.todobackend.service.CategoryService;
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
    private final CategoryService categoryService;
    private final UserService     userService;    // you can also use UserRepository

    public TaskController(TaskService taskService,
                          CategoryService categoryService,
                          UserService userService) {
        this.taskService     = taskService;
        this.categoryService = categoryService;
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
        t.setTaskDate(req.taskDate);
        t.setUser(user);

        // 4) Optionally set Category
        if (req.categoryId != null) {
            Category cat = categoryService.findById(req.categoryId);
            t.setCategory(cat);
        }

        // 5) Save and return 201
        Task created = taskService.add(t);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** Update an existing task */
    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody TaskRequest req) {
        // 1) Validate
        if (req.id == null || req.id == 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        if (req.title == null || req.title.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        if (req.userId == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }

        // 2) Lookup existing
        Task existing = taskService.findById(req.id);

        // 3) Overwrite fields
        existing.setTitle(req.title);
        existing.setCompleted(req.completed != null && req.completed);
        existing.setTaskDate(req.taskDate);

        // 4) Re-attach User (if needed)
        User user = userService.findById(req.userId);
        existing.setUser(user);

        // 5) Re-attach Category
        if (req.categoryId != null) {
            Category cat = categoryService.findById(req.categoryId);
            existing.setCategory(cat);
        } else {
            existing.setCategory(null);
        }

        // 6) Save and return
        Task updated = taskService.update(existing);
        return ResponseEntity.ok(updated);
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

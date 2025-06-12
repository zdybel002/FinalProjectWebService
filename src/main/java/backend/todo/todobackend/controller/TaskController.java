package backend.todo.todobackend.controller;

import backend.todo.todobackend.entity.Task;
import backend.todo.todobackend.service.TaskService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /** Fetch all tasks for a given user email */
    @PostMapping("/all")
    public ResponseEntity<List<Task>> findAll(@RequestBody String email) {
        List<Task> tasks = taskService.findAll(email);
        return ResponseEntity.ok(tasks);
    }

    /** Fetch tasks by category ID */
    @PostMapping("/category")
    public ResponseEntity<List<Task>> getTasksByCategory(@RequestBody Map<String, Long> body) {
        Long categoryId = body.get("categoryId");
        List<Task> tasks = taskService.findByCategoryId(categoryId);
        return ResponseEntity.ok(tasks);
    }

    /** Create a new task */
    @PostMapping("/add")
    public ResponseEntity<Task> add(@RequestBody Task task) {
        if (task.getId() != null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(null);
        }
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(null);
        }
        Task created = taskService.add(task);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    /** Update an existing task */
    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody Task task) {
        if (task.getId() == null || task.getId() == 0) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(null);
        }
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(null);
        }
        Task updated = taskService.update(task);
        return ResponseEntity.ok(updated);
    }

    /** Delete by ID */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            taskService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .build();
        }
    }

    /** Fetch a single task by ID */
    @PostMapping("/id")
    public ResponseEntity<Task> findById(@RequestBody Long id) {
        try {
            Task t = taskService.findById(id);
            return ResponseEntity.ok(t);
        } catch (NoSuchElementException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(null);
        }
    }
}

package backend.todo.todobackend.service;

import backend.todo.todobackend.entity.Task;
import backend.todo.todobackend.repo.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    // Called by TaskController.findAll()
    public List<Task> findAllByUserId(Long userId) {
        return repo.findAllByUser_Id(userId);
    }

    // Called by TaskController.add() and update()
    public Task add(Task t) {
        return repo.save(t);
    }
    public Task update(Task t) {
        return repo.save(t);
    }

    // Called by TaskController.delete()
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    // Called by TaskController.findById()
    public Task findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found: " + id));
    }

    // And if you still need by-category:
    public List<Task> findByCategoryId(Long catId) {
        return repo.findByCategory_Id(catId);
    }
}

package backend.todo.todobackend.service;

import backend.todo.todobackend.entity.Task;
import backend.todo.todobackend.repo.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service layer for handling Task-related business logic.
 * Provides methods to create, retrieve, update, and delete tasks.
 */
@Service
public class TaskService {
    private final TaskRepository repo;

    /**
     * Constructor for TaskService.
     *
     * @param repo the TaskRepository instance
     */
    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    /**
     * Returns all tasks for the specified user.
     *
     * @param userId the ID of the user
     * @return list of tasks belonging to the user
     */
    public List<Task> findAllByUserId(Long userId) {
        return repo.findAllByUser_Id(userId);
    }

    /**
     * Adds a new task.
     *
     * @param t the task to be added
     * @return the saved task
     */
    public Task add(Task t) {
        return repo.save(t);
    }

    /**
     * Updates an existing task.
     *
     * @param t the task to be updated
     * @return the updated task
     */
    public Task update(Task t) {
        return repo.save(t);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     */
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    /**
     * Finds a task by its ID.
     *
     * @param id the ID of the task
     * @return the found task
     * @throws NoSuchElementException if task not found
     */
    public Task findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found: " + id));
    }
}

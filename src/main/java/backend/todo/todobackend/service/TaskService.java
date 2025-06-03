package backend.todo.todobackend.service;

import backend.todo.todobackend.entity.Task;
import backend.todo.todobackend.repo.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    // find all task by user email
    public List<Task> findAll(String email) {
        return repository.findByUserEmailOrderByTaskDateDesc(email);
    }

    // add new task
    public Task add(Task task) {
        return repository.save(task);
    }

    // update exsosting task
    public Task update(Task task) {
        return repository.save(task);
    }


}

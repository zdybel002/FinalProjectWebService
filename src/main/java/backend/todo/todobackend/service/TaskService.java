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

    // find task by task id
    public Task findById(Long id) {
        return repository.findById(id).get();
    }

    // find by category id
    public List<Task> findByCategoryId(Long categoryId){
        return repository.findByCategory_IdOrderByTaskDateAsc(categoryId);
    }

    // add new task
    public Task add(Task task) {
        return repository.save(task);
    }

    // update exsosting task
    public Task update(Task task) {
        return repository.save(task);
    }

    // delete task
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}

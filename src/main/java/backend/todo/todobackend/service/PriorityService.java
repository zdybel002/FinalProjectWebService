package backend.todo.todobackend.service;

import backend.todo.todobackend.entity.Priority;
import backend.todo.todobackend.repo.CategoryRepository;
import backend.todo.todobackend.repo.PriorityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Transactional
public class PriorityService {

    private final PriorityRepository repository;

    public PriorityService(PriorityRepository repository) {
        this.repository = repository;
    }

    // find all priorities
    public List<Priority> findAll(String email) {
        return repository.findByUserEmailOrderByIdAsc(email);
    }

    // Add endpoint to create a new priority
    public Priority add(Priority priority)
    {
        return repository.save(priority);
    }

    //Add endpoint to update priority with validation
    public Priority update(Priority priority) {
        return repository.save(priority);
    }

    // find priority by id
    public Priority findById(Long id) {
        return repository.findById(id).get();
    }

    //delete priority by id
    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}


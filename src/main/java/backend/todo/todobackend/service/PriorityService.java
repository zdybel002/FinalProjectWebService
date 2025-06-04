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



}


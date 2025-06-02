package backend.todo.todobackend.service;

import backend.todo.todobackend.entity.Category;
import backend.todo.todobackend.repo.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;



import java.util.List;



@Service


@Transactional
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll(String email) {
        System.out.println("-------------" + repository.findAllByUserEmail(email) + "----------------");
        return repository.findAllByUserEmail(email);
    }


}


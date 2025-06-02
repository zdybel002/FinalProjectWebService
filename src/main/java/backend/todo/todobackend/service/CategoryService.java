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

    //find All category by email
    public List<Category> findAll(String email) {
        System.out.println("-------------" + repository.findAllByUserEmail(email) + "----------------");
        return repository.findAllByUserEmail(email);
    }

    // find user categories by title
    public List<Category> findByTitle(String text, String email) {
        return repository.findByTitle(text, email);
    }
    // add new category
    public Category add(Category category) {
        return repository.save(category);
    }




}


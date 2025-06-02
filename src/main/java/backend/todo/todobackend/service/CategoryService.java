package backend.todo.todobackend.service;

import backend.todo.todobackend.entity.Category;
import backend.todo.todobackend.repo.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;
import java.util.NoSuchElementException;


@Service


@Transactional
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    //find All category by email
    public List<Category> findAll(String email) {
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

    // update category with new data
    public Category update(Category category) {
        return repository.save(category);
    }

    // delete categoty by id
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // find category by ID
    public Category findById(Long id) {
        return repository.findById(id).get(); // since an Optional is returned, we get the object using get()
    }




}


package backend.todo.todobackend.controller;


import backend.todo.todobackend.DTO.EmailRequest;
import backend.todo.todobackend.entity.Category;
import backend.todo.todobackend.search.CategorySearchValues;
import backend.todo.todobackend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/category") // base URI for all methods in this controller
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/all")
    public List<Category> findAll(@RequestBody EmailRequest request) {
        return categoryService.findAll(request.getEmail());
    }

    // search categories by title and email
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues) {

        // check if email is missing
        if (categorySearchValues.getEmail() == null || categorySearchValues.getEmail().trim().length() == 0) {
            return new ResponseEntity("missed param: email", HttpStatus.NOT_ACCEPTABLE);
        }

        // find all categories for this user with a specific title
        List<Category> list = categoryService.findByTitle(categorySearchValues.getTitle(), categorySearchValues.getEmail());

        return ResponseEntity.ok(list);
    }

}

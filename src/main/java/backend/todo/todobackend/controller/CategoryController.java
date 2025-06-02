package backend.todo.todobackend.controller;


import backend.todo.todobackend.DTO.EmailRequest;
import backend.todo.todobackend.entity.Category;
import backend.todo.todobackend.service.CategoryService;
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

}

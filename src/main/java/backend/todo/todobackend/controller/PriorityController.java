package backend.todo.todobackend.controller;


import backend.todo.todobackend.entity.Priority;
import backend.todo.todobackend.service.PriorityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/priority") // base URI
public class PriorityController {


    private PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }


    //
    @PostMapping("/all")
    public List<Priority> findAll(@RequestBody String email) {

        return priorityService.findAll(email);
    }



}

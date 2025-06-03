package backend.todo.todobackend.controller;


import backend.todo.todobackend.entity.Task;
import backend.todo.todobackend.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/task") // base URI
public class TaskController {


    public static final String ID_COLUMN = "id";
    private final TaskService taskService;



    // we don't use @Autowired on the class field because "Field injection is not recommended"
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }



    // find all tasks for the specific user by email
    @PostMapping("/all")
    public ResponseEntity<List<Task>> findAll(@RequestBody String email) {
        return ResponseEntity.ok(taskService.findAll(email));
    }


}

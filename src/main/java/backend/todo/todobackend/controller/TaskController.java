package backend.todo.todobackend.controller;


import backend.todo.todobackend.entity.Task;
import backend.todo.todobackend.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


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

    // add new task
    @PostMapping("/add")
    public ResponseEntity<Task> add(@RequestBody Task task) {

        // check required fields
        if (task.getId() != null && task.getId() != 0) {
            // id is auto-generated in DB (autoincrement), so it shouldn't be passed to avoid uniqueness conflicts
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // if title is empty or null
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(taskService.add(task)); // return created object with generated id

    }

    // update existing task
    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody Task task) {

        // check required fields
        if (task.getId() == null || task.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // if title is empty or null
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }


        // save works for both adding and updating
        taskService.update(task);

        return new ResponseEntity(HttpStatus.OK); // just return status 200 (operation succeeded)

    }

    // get task by id
    @PostMapping("/id")
    public ResponseEntity<Task> findById(@RequestBody Long id) {

        Task task = null;

        // try-catch is optional, without it stacktrace will be returned on error
        // here is an example of handling exceptions and sending custom message/status
        try {
            task = taskService.findById(id);
        } catch (NoSuchElementException e) { // if object not found
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(task);
    }



}

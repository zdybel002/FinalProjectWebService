package backend.todo.todobackend.controller;

import backend.todo.todobackend.entity.Stat;
import backend.todo.todobackend.service.StatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;





@RestController
public class StatController {

    private final StatService statService;
    public StatController(StatService statService) {
        this.statService = statService;
    }


    @PostMapping("/stat")
    public ResponseEntity<Stat> findByEmail(@RequestBody String email) {
        return ResponseEntity.ok(statService.findStat(email));
    }


}

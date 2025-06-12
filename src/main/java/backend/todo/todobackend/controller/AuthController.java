package backend.todo.todobackend.controller;

import backend.todo.todobackend.entity.User;
import backend.todo.todobackend.repo.UserRepository;
import backend.todo.todobackend.requests.LoginRequest;
import backend.todo.todobackend.requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (user.getPassword().equals(request.getPassword())) {
                return ResponseEntity.ok(String.valueOf(user.getId()));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Błąd logowania");
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Email already in use");
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());
        userRepository.save(newUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(String.valueOf(newUser.getId()));
    }

}

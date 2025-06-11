package backend.todo.todobackend.controller;

import backend.todo.todobackend.entity.Category;
import backend.todo.todobackend.entity.User;
import backend.todo.todobackend.repo.UserRepository;
import backend.todo.todobackend.search.LoginRequest;
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
    public ResponseEntity<String> register(@RequestBody User user) {
        // ID musi być puste
        if (user.getId() != null && user.getId() != 0) {
            return new ResponseEntity<>("Redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // Sprawdzenie poprawności emaila
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            return new ResponseEntity<>("Invalid email: email MUST contain @", HttpStatus.NOT_ACCEPTABLE);
        }

        // Sprawdzenie, czy hasło nie jest puste
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return new ResponseEntity<>("Missing param: password MUST NOT be empty", HttpStatus.NOT_ACCEPTABLE);
        }

//        // Sprawdzenie, czy username nie jest pusty
//        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
//            return new ResponseEntity<>("Missing param: username MUST NOT be empty", HttpStatus.NOT_ACCEPTABLE);
//        }

        // Sprawdzenie, czy email już istnieje
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
        }

        // Tutaj ewentualnie dodaj szyfrowanie hasła
        // user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Zapis nowego użytkownika
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
    }

}

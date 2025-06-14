package backend.todo.todobackend.controller;

import backend.todo.todobackend.entity.ProfileDto;
import backend.todo.todobackend.entity.User;
import backend.todo.todobackend.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * ProfileController provides endpoints to fetch and update user profile data.
 *
 * POST /profile/get — returns user's profile info by ID
 * PUT /profile/update — updates user's location and Telegram chat ID
 *
 * Uses UserRepository to access user data.
 */
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository repo;

    /**
     * Constructor for ProfileController with injected UserRepository.
     *
     * @param repo the repository used to access users
     */
    public ProfileController(UserRepository repo) {
        this.repo = repo;
    }

    /**
     * Returns profile data (location and chat ID) for a given user ID.
     *
     * @param userId ID of the user
     * @return ProfileDto with profile information
     * @throws ResponseStatusException 404 if user not found
     */
    @PostMapping("/get")
    public ProfileDto get(@RequestBody Long userId) {
        var u = repo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new ProfileDto(u.getId(), u.getLocation(), u.getTelegramChat());
    }

    /**
     * Updates a user's profile (location and Telegram chat ID).
     *
     * @param dto ProfileDto with updated information
     * @throws ResponseStatusException 404 if user not found
     */
    @PutMapping("/update")
    public void update(@RequestBody ProfileDto dto) {
        var u = repo.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        u.setLocation(dto.getLocation());
        u.setTelegramChat(dto.getChatId());
        repo.save(u);
    }
}

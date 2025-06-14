package backend.todo.todobackend.service;

import backend.todo.todobackend.entity.Stat;
import backend.todo.todobackend.repo.StatRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service class for retrieving statistics (Stat) related to user tasks.
 *
 * This service interacts with the StatRepository to fetch task statistics
 * based on the user's email.
 */
@Service
@Transactional
public class StatService {

    private final StatRepository repository;

    /**
     * Constructor for StatService with repository injection.
     *
     * @param repository the StatRepository instance
     */
    public StatService(StatRepository repository) {
        this.repository = repository;
    }

    /**
     * Finds and returns a Stat entity by the user's email.
     *
     * @param email the email of the user
     * @return the corresponding Stat entity
     */
    public Stat findStat(String email) {
        return repository.findByUserEmail(email);
    }
}

package backend.todo.todobackend.repo;

import backend.todo.todobackend.entity.Stat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing Stat entities.
 *
 * Provides CRUD operations and a method to fetch statistics by user email.
 */
@Repository
public interface StatRepository extends CrudRepository<Stat, Long> {

    /**
     * Finds the Stat record associated with a user's email.
     * Assumes a one-to-one relationship: one user has one Stat.
     *
     * @param email user email
     * @return Stat object for the user
     */
    Stat findByUserEmail(String email);
}

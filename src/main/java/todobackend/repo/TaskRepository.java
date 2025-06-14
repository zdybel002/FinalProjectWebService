package backend.todo.todobackend.repo;

import backend.todo.todobackend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Task entities.
 *
 * Provides basic CRUD operations and custom queries for task filtering.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Finds all tasks for a specific user.
     *
     * @param userId ID of the user
     * @return list of tasks
     */
    List<Task> findAllByUser_Id(Long userId);

    /**
     * Finds all uncompleted tasks that fall within the specified date range.
     *
     * @param start start datetime
     * @param end end datetime
     * @return list of uncompleted tasks
     */
    List<Task> findAllByCompletedFalseAndTaskDateBetween(LocalDateTime start, LocalDateTime end);
}

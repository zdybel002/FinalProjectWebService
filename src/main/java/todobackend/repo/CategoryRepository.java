package backend.todo.todobackend.repo;

import backend.todo.todobackend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing Category entities.
 *
 * Provides built-in CRUD operations via JpaRepository and custom queries for filtering.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Retrieves all categories for a specific user, sorted by ID.
     *
     * @param email user email
     * @return list of Category entities
     */
    List<Category> findByUserEmailOrderByIdAsc(String email);

    /**
     * Searches categories by title (partial match) for a specific user.
     * If the title is null or empty, returns all categories.
     *
     * @param title category title to search (nullable)
     * @param email user email
     * @return list of matched Category entities sorted by title
     */
    @Query("SELECT c FROM Category c where " +
            "(:title is null or :title='' " +
            " or lower(c.title) like lower(concat('%', :title,'%'))) " +
            " and c.user.email=:email  " +
            " order by c.title asc")
    List<Category> findByTitle(@Param("title") String title, @Param("email") String email);
}

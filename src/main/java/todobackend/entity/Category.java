package backend.todo.todobackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;

/**
 * Category is a reference entity used to group tasks by user-defined labels.
 * It also stores statistics for completed and uncompleted tasks per category.
 *
 * Mapped to the table: todolist.category
 *
 * Uses Hibernate's second-level cache with READ_WRITE strategy.
 */
@Entity
@Table(name = "category", schema = "todolist", catalog = "postgres")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category {

    /** Auto-generated primary key */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    /** Category name */
    private String title;

    /** Number of completed tasks in this category (DB-managed) */
    @Column(name = "completed_count", updatable = false)
    private Long completedCount;

    /** Number of uncompleted tasks in this category (DB-managed) */
    @Column(name = "uncompleted_count", updatable = false)
    private Long uncompletedCount;

    /** Reference to the user who owns this category (write-only in JSON) */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return title;
    }
}

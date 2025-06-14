package backend.todo.todobackend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;

/**
 * Stat represents overall task statistics for a user,
 * independent of task categories.
 *
 * Mapped to the table: todolist.stat
 *
 * Uses Hibernate's second-level cache with READ_WRITE strategy.
 */
@Entity
@Table(name = "stat", schema = "todolist", catalog = "postgres")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Stat {

    /** Primary key (same as user's ID due to @MapsId) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Total number of completed tasks (managed by DB) */
    @Column(name = "completed_total", updatable = false)
    private Long completedTotal;

    /** Total number of uncompleted tasks (managed by DB) */
    @Column(name = "uncompleted_total", updatable = false)
    private Long uncompletedTotal;

    /** One-to-one reference to the user who owns this stat */
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stat stat = (Stat) o;
        return id.equals(stat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

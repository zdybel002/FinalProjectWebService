package backend.todo.todobackend.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Task is an entity representing a user's to-do item.
 *
 * Mapped to the table: todolist.task
 */
@Entity
@Table(name = "task", schema = "todolist")
public class Task {

    /** Auto-generated primary key */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Title of the task */
    private String title;

    /** Completion status (true = done, false = pending) */
    private Boolean completed;

    /** Optional date and time associated with the task */
    @Column(name = "task_date", columnDefinition = "timestamp without time zone")
    private LocalDateTime taskDate;

    /** Reference to the user who owns the task (ignored in JSON) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Task() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }

    public LocalDateTime getTaskDate() { return taskDate; }
    public void setTaskDate(LocalDateTime taskDate) { this.taskDate = taskDate; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}

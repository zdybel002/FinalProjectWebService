package backend.todo.todobackend.entity;

import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Objects;



@Entity
@Table(name = "task", schema = "todolist")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Boolean completed;
    @Column(name = "task_date", columnDefinition = "timestamp without time zone")
    private LocalDateTime taskDate;

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
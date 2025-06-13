package backend.todo.todobackend.requests;

import java.time.OffsetDateTime;
import java.time.LocalDateTime;

public class TaskRequest {
    public Long           id;
    public String         title;
    public Boolean        completed;
    public LocalDateTime taskDate;   // ← use OffsetDateTime here
    public Long           userId;
}

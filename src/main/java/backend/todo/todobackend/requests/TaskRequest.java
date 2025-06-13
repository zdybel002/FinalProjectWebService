package backend.todo.todobackend.requests;

import java.time.OffsetDateTime;

public class TaskRequest {
    public Long           id;
    public String         title;
    public Boolean        completed;
    public OffsetDateTime taskDate;   // ← use OffsetDateTime here
    public Long           userId;
}

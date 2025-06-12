package backend.todo.todobackend.requests;

import java.time.LocalDateTime;
import java.util.Date;

public class TaskRequest {
    public Long id;
    public String title;
    public Boolean completed;
    public LocalDateTime taskDate;
    public Long userId;
}

package backend.todo.todobackend.requests;

import java.util.Date;

public class TaskRequest {
    public Long    id;
    public String  title;
    public Boolean completed;
    public Date    taskDate;
    public Long    categoryId;
    public Long    userId;
}
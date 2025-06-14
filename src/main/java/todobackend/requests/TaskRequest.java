package backend.todo.todobackend.requests;

/**
 * DTO for transferring task data between client and server.
 * Contains task ID, title, completion status, scheduled date, and user ID.
 */
public class TaskRequest {

    /** Task ID (null for new tasks) */
    private Long id;

    /** Title of the task */
    private String title;

    /** Whether the task is completed */
    private Boolean completed;

    /** Scheduled date/time in ISO-8601 string format */
    private String taskDate;

    /** ID of the user who owns the task */
    private Long userId;

    public TaskRequest() {}


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Boolean getCompleted() { return completed; }

    public void setCompleted(Boolean completed) { this.completed = completed; }

    public String getTaskDate() { return taskDate; }

    public void setTaskDate(String taskDate) { this.taskDate = taskDate; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }
}

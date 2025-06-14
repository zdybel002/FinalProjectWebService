package backend.todo.todobackend.entity;

/**
 * ProfileDto is a simple data transfer object used for
 * fetching or updating a user's profile data.
 *
 * Includes user ID, location, and Telegram chat ID.
 */
public class ProfileDto {

    private Long userId;
    private String location;
    private Long chatId;

    /** Default constructor */
    public ProfileDto() { }

    /** Full constructor */
    public ProfileDto(Long userId, String location, Long chatId) {
        this.userId   = userId;
        this.location = location;
        this.chatId   = chatId;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long u) { this.userId = u; }

    public String getLocation() { return location; }
    public void setLocation(String l) { this.location = l; }

    public Long getChatId() { return chatId; }
    public void setChatId(Long c) { this.chatId = c; }
}

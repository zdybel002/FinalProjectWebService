package backend.todo.todobackend.entity;

public class ProfileDto {
    private Long   userId;
    private String location;
    private Long   chatId;      // ← new

    public ProfileDto() { }

    public ProfileDto(Long userId, String location, Long chatId) {
        this.userId   = userId;
        this.location = location;
        this.chatId   = chatId;
    }

    // getters & setters for all three
    public Long getUserId()          { return userId; }
    public void setUserId(Long u)    { this.userId = u; }

    public String getLocation()      { return location; }
    public void setLocation(String l){ this.location = l; }

    public Long getChatId()          { return chatId; }
    public void setChatId(Long c)    { this.chatId = c; }
}

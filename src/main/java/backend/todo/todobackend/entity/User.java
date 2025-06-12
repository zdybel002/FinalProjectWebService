package backend.todo.todobackend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;

@Entity
@Table(name = "user_data", schema = "todolist", catalog = "postgres")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getTelegramChat() {
        return telegramChat;
    }

    public void setTelegramChat(Long telegramChat) {
        this.telegramChat = telegramChat;
    }

    private String email;
    private String username;

    @Column(name = "userpassword")
    private String password;

    // ← new field for weather location
    private String location;

    // ← new field for Telegram chat ID
    @Column(name = "telegram_chat")
    private Long   telegramChat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

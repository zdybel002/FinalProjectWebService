package backend.todo.todobackend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;

/**
 * User is the main entity representing an application user.
 *
 * Mapped to the table: todolist.user_data
 *
 * Uses Hibernate's second-level cache with NONSTRICT_READ_WRITE strategy.
 */
@Entity
@Table(name = "user_data", schema = "todolist", catalog = "postgres")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User {

    /** Auto-generated primary key */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** User's email address */
    private String email;

    /** User's display name */
    private String username;

    /** User's login password (should be stored hashed in production) */
    @Column(name = "userpassword")
    private String password;

    /** Location string used for weather features */
    private String location;

    /** Telegram chat ID used for sending reminders */
    @Column(name = "telegram_chat")
    private Long telegramChat;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return username;
    }
}

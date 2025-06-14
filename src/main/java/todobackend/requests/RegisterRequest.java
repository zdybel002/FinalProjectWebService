package backend.todo.todobackend.requests;

/**
 * DTO for handling user registration requests.
 * Contains user's email, username, and password.
 */
public class RegisterRequest {

    /** User's email address */
    private String email;

    /** User's chosen username */
    private String username;

    /** User's password */
    private String password;


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
}

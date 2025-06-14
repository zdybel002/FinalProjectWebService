package backend.todo.todobackend.requests;

/**
 * DTO for user login request.
 * Contains user's email and password.
 */
public class LoginRequest {

    /** User email */
    private String email;

    /** User password */
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

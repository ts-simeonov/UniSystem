import java.io.Serializable;
import java.util.Objects;

public abstract class User implements Serializable {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

    public abstract UserType getUserType();

    @Override
    public String toString() {
        return "User: " + "Username: " + getUsername() + " Password: " + getPassword();
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User other = (User) o;
        return Objects.equals(getUsername(), other.getUsername()) && Objects.equals(getPassword(), other.getPassword());
    }
}

import java.util.regex.Pattern;

public class UserBuilder {
    private static final Pattern facNumPattern = Pattern.compile("^\\d{9}$");
    private static final Pattern egnPattern = Pattern.compile("^\\d{10}$");
    private static final Pattern emailPattern = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

    public static User createUser(UserType userType, String username, String password) throws Exception {
        switch (userType) {
            case ADMIN:
                return new Admin(username, password);
            case STUDENT:
                if (!facNumPattern.matcher(username).matches()) {
                    throw new Exception("Wrong faculty number!");
                }
                if (!egnPattern.matcher(password).matches()) {
                    throw new Exception("Wrong EGN Number!");
                }
                return new Student(username, password);
            case TEACHER:
                if (!emailPattern.matcher(username).matches()) {
                    throw new Exception("Wrong email!");
                }
                if (password.length() < 5) {
                    throw new Exception("The password is too short!");
                }
                return new Teacher(username, password);

            default:
                return null;
        }
    }
}

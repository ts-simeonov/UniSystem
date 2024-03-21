public class Teacher extends User {
    public Teacher(String username, String password) {
        super(username, password);
    }

    @Override
    public UserType getUserType() {
        return UserType.TEACHER;
    }

    @Override
    public String toString() {
        return "Teacher: " + super.toString();
    }
}

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<Grade> grades;

    public Student(String username, String password) {
        super(username, password);
        this.grades = new ArrayList<>();
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    @Override
    public UserType getUserType() {
        return UserType.STUDENT;
    }

    @Override
    public String toString() {
        return "Student: Grades:" + grades + super.toString();
    }
}

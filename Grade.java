import java.io.*;
import java.util.Objects;

public class Grade implements Serializable {
    private String subject;
    private int semester;
    private int mark;

    public Grade(String subject, int semester, int mark) {
        this.subject = subject;
        this.semester = semester;
        this.mark = mark;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Grade: Subject: " + getSubject() + " Semester: " + getSemester() + " Mark: " + getMark();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade gr = (Grade) o;
        return Objects.equals(getSubject(), gr.getSubject()) && semester == gr.getSemester() && mark == gr.getMark();
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, semester, mark);
    }
}

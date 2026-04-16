// Assigned to: Мамедов Н. Т.
package model.users;

import model.enums.Title;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a teacher in the university system
 */
public class Teacher extends Employee {
    private static final long serialVersionUID = 1L;
    
    private Title title;
    private List<String> coursesTeaching; // Course IDs
    private boolean isProfessor;
    
    public Teacher(String username, String password, String firstName, String lastName, 
                  String email, String employeeId, double salary, String department, Title title) {
        super(username, password, firstName, lastName, email, employeeId, salary, department);
        this.title = title;
        this.coursesTeaching = new ArrayList<>();
        this.isProfessor = (title == Title.PROFESSOR);
    }
    
    // Getters and setters
    public Title getTitle() {
        return title;
    }
    
    public void setTitle(Title title) {
        this.title = title;
        this.isProfessor = (title == Title.PROFESSOR);
    }
    
    public List<String> getCoursesTeaching() {
        return new ArrayList<>(coursesTeaching);
    }
    
    public void addCourseTeaching(String courseId) {
        // TODO: Add courseId to coursesTeaching only if it is not already present.
    }
    
    public void removeCourseTeaching(String courseId) {
        coursesTeaching.remove(courseId);
    }
    
    public boolean isProfessor() {
        return isProfessor;
    }
    
    // Check if this teacher is automatically a researcher (professors are always researchers)
    public boolean isAutoResearcher() {
        // TODO: Return true when this teacher should automatically be treated as a researcher.
        // Hint: professors are auto-researchers in this project.
        return false;
    }
    
    @Override
    public String getUserType() {
        return "Teacher";
    }
    
    @Override
    public String toString() {
        // TODO: Extend Employee.toString() with title and number of courses taught.
        return "";
    }
}
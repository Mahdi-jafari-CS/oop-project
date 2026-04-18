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
        if (!coursesTeaching.contains(courseId)) {
            coursesTeaching.add(courseId);
        }
    }
    
    public void removeCourseTeaching(String courseId) {
        coursesTeaching.remove(courseId);
    }
    
    public boolean isProfessor() {
        return isProfessor;
    }
    
    // Check if this teacher is automatically a researcher (professors are always researchers)
    public boolean isAutoResearcher() {
        return isProfessor;
    }
    
    @Override
    public String getUserType() {
        return "Teacher";
    }
    
    @Override
    public String toString() {
        return String.format("%s, title=%s, coursesTeaching=%d",
                             super.toString(), title, coursesTeaching.size());
    }
}
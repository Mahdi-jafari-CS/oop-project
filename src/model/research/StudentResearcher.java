package model.research;

import model.users.Student;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete decorator for adding researcher functionality to Student objects
 */
public class StudentResearcher extends ResearcherDecorator {
    
    private Student student;
    
    public StudentResearcher(Student student) {
        super(new BasicResearcher(student.getUsername(), student.getFullName()));
        this.student = student;
    }
    
    @Override
    public String getResearcherId() {
        return "SR-" + student.getStudentId();
    }
    
    @Override
    public String getResearcherName() {
        return student.getFullName() + " (Student)";
    }
    
    /**
     * Get the underlying Student object
     */
    public Student getStudent() {
        return student;
    }
    
    /**
     * Check if student is in 4th year and needs a research supervisor
     */
    public boolean needsResearchSupervisor() {
        return student.needsResearchSupervisor();
    }
    
    @Override
    public String toString() {
        return "StudentResearcher{" +
                "student=" + student.getFullName() +
                ", hIndex=" + getHIndex() +
                ", papers=" + getResearchPapers().size() +
                ", projects=" + getResearchProjects().size() +
                '}';
    }
}


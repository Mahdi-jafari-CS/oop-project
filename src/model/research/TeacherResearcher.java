package model.research;

import model.users.Teacher;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete decorator for adding researcher functionality to Teacher objects
 */
public class TeacherResearcher extends ResearcherDecorator {
    
    private Teacher teacher;
    
    public TeacherResearcher(Teacher teacher) {
        super(new BasicResearcher(teacher.getUsername(), teacher.getFullName()));
        this.teacher = teacher;
    }
    
    @Override
    public String getResearcherId() {
        return "TR-" + teacher.getEmployeeId();
    }
    
    @Override
    public String getResearcherName() {
        return teacher.getFullName() + " (Teacher)";
    }
    
    /**
     * Get the underlying Teacher object
     */
    public Teacher getTeacher() {
        return teacher;
    }
    
    /**
     * Check if this teacher researcher is automatically a researcher (professors are always researchers)
     */
    public boolean isAutoResearcher() {
        return teacher.isProfessor();
    }
    
    @Override
    public String toString() {
        return "TeacherResearcher{" +
                "teacher=" + teacher.getFullName() +
                ", hIndex=" + getHIndex() +
                ", papers=" + getResearchPapers().size() +
                ", projects=" + getResearchProjects().size() +
                '}';
    }
}


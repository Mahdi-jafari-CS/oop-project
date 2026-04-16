// Assigned to: Каналхан Б. Н.
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
        // TODO: Delegate to Teacher and return whether this teacher is auto-researcher.
        return false;
    }
    
    @Override
    public String toString() {
        // TODO: Return compact summary with teacher name, h-index, paper count, and project count.
        return "";
    }
}


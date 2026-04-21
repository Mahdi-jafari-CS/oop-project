// Assigned to: Каналхан Б. Н.
package model.research;

import model.users.Teacher;

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
        return teacher.isAutoResearcher();
    }
    
    @Override
    public String toString() {
        return String.format(
                "TeacherResearcher{name='%s', hIndex=%d, papers=%d, projects=%d}",
                teacher.getFullName(),
                getHIndex(),
                getResearchPapers().size(),
                getResearchProjects().size()
        );
    }
}


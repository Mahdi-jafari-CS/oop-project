// Assigned to: Каналхан Б. Н.
package model.research;

import model.users.Student;

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
        return String.format(
                "StudentResearcher{name='%s', hIndex=%d, papers=%d, projects=%d}",
                student.getFullName(),
                getHIndex(),
                getResearchPapers().size(),
                getResearchProjects().size()
        );
    }
}


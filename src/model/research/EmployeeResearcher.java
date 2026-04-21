// Assigned to: Каналхан Б. Н.
package model.research;

import model.users.Employee;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete decorator for adding researcher functionality to Employee objects
 * This handles employees who are neither Teachers nor Students but can be Researchers
 */
public class EmployeeResearcher extends ResearcherDecorator {
    
    private Employee employee;
    
    public EmployeeResearcher(Employee employee) {
        super(new BasicResearcher(employee.getUsername(), employee.getFullName()));
        this.employee = employee;
    }
    
    @Override
    public String getResearcherId() {
        return "ER-" + employee.getEmployeeId();
    }
    
    @Override
    public String getResearcherName() {
        return employee.getFullName() + " (Employee)";
    }
    
    /**
     * Get the underlying Employee object
     */
    public Employee getEmployee() {
        return employee;
    }
    
    @Override
    public String toString() {
        return String.format(
                "EmployeeResearcher{name='%s', hIndex=%d, papers=%d, projects=%d}",
                employee.getFullName(),
                getHIndex(),
                getResearchPapers().size(),
                getResearchProjects().size()
        );
    }
}
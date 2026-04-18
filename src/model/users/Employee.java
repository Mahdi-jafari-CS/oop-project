// Assigned to: Мамедов Н. Т.
package model.users;

import java.io.Serializable;

/**
 * Abstract class representing an employee in the university system
 * Extends User and adds employee-specific fields
 */
public abstract class Employee extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String employeeId;
    private double salary;
    private String department;
    
    public Employee(String username, String password, String firstName, String lastName, 
                   String email, String employeeId, double salary, String department) {
        super(username, password, firstName, lastName, email);
        this.employeeId = employeeId;
        this.salary = salary;
        this.department = department;
    }
    
    // Getters and setters
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    @Override
    public String toString() {
        return String.format("%s, employeeId=%s, department=%s", super.toString(), getEmployeeId(), getDepartment());
    }
}
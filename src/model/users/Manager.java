package model.users;

import model.enums.ManagerType;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a manager in the university system
 */
public class Manager extends Employee {
    private static final long serialVersionUID = 1L;
    
    private ManagerType managerType;
    private List<String> managedDepartments;
    private List<String> approvedRequests; // Request IDs
    private List<String> pendingRequests; // Request IDs
    
    public Manager(String username, String password, String firstName, String lastName, 
                  String email, String employeeId, double salary, String department, ManagerType managerType) {
        super(username, password, firstName, lastName, email, employeeId, salary, department);
        this.managerType = managerType;
        this.managedDepartments = new ArrayList<>();
        this.approvedRequests = new ArrayList<>();
        this.pendingRequests = new ArrayList<>();
    }
    
    // Getters and setters
    public ManagerType getManagerType() {
        return managerType;
    }
    
    public void setManagerType(ManagerType managerType) {
        this.managerType = managerType;
    }
    
    public List<String> getManagedDepartments() {
        return new ArrayList<>(managedDepartments);
    }
    
    public void addManagedDepartment(String department) {
        if (!managedDepartments.contains(department)) {
            managedDepartments.add(department);
        }
    }
    
    public void removeManagedDepartment(String department) {
        managedDepartments.remove(department);
    }
    
    public List<String> getApprovedRequests() {
        return new ArrayList<>(approvedRequests);
    }
    
    public void approveRequest(String requestId) {
        // TODO: Move requestId from pendingRequests to approvedRequests.
        // Rule: do this only if requestId exists in pendingRequests.
    }
    
    public List<String> getPendingRequests() {
        return new ArrayList<>(pendingRequests);
    }
    
    public void addPendingRequest(String requestId) {
        if (!pendingRequests.contains(requestId)) {
            pendingRequests.add(requestId);
        }
    }
    
    public void removePendingRequest(String requestId) {
        pendingRequests.remove(requestId);
    }
    
    @Override
    public String getUserType() {
        return "Manager";
    }
    
    @Override
    public String toString() {
        // TODO: Extend Employee.toString() with managerType and managed department count.
        return "";
    }
}
package model.users;

import service.NotificationService;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an admin in the university system
 * Implements Observer pattern for action logging
 */
public class Admin extends Employee {
    private static final long serialVersionUID = 1L;
    
    private List<String> userActionsLog; // Log of user actions
    private List<String> managedUsers; // User IDs managed by this admin
    
    public Admin(String username, String password, String firstName, String lastName, 
                String email, String employeeId, double salary, String department) {
        super(username, password, firstName, lastName, email, employeeId, salary, department);
        this.userActionsLog = new ArrayList<>();
        this.managedUsers = new ArrayList<>();
    }
    
    // Getters and setters
    public List<String> getUserActionsLog() {
        return new ArrayList<>(userActionsLog);
    }
    
    public void logUserAction(String action) {
        // TODO: Add a timestamped log entry to userActionsLog.
        // TODO: Notify observers via NotificationService about this action.
        // TODO: Keep only the latest 1000 entries (remove oldest when limit is exceeded).
    }
    
    private String extractUsernameFromAction(String action) {
        // TODO: Extract the username from action text patterns:
        // "Added user: <username>", "Removed user: <username>", "Updated user: <username>".
        // Return "unknown" if no supported pattern exists.
        return "unknown";
    }
    
    public List<String> getManagedUsers() {
        return new ArrayList<>(managedUsers);
    }
    
    public void addManagedUser(String userId) {
        if (!managedUsers.contains(userId)) {
            managedUsers.add(userId);
        }
    }
    
    public void removeManagedUser(String userId) {
        managedUsers.remove(userId);
    }
    
    // Admin-specific methods
    public void addUser(User user) {
        logUserAction("Added user: " + user.getUsername());
        // Implementation would add to system user list
    }
    
    public void removeUser(String username) {
        logUserAction("Removed user: " + username);
        // Implementation would remove from system user list
    }
    
    public void updateUser(User user) {
        logUserAction("Updated user: " + user.getUsername());
        // Implementation would update user in system
    }
    
    public void viewLogs() {
        for (String log : userActionsLog) {
            System.out.println(log);
        }
    }
    
    @Override
    public String getUserType() {
        return "Admin";
    }
    
    @Override
    public String toString() {
        // TODO: Extend Employee.toString() with admin metrics.
        // Include number of managed users and number of log entries.
        return "";
    }
}
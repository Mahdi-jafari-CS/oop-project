// Assigned to: Мамедов Н. Т.
package model.users;

import service.NotificationService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String logEntry = String.format("%s - %s", timestamp, action);
        userActionsLog.add(logEntry);
        if (userActionsLog.size() > 1000) {
            userActionsLog.remove(0);
        }

        String targetUser = extractUsernameFromAction(action);
        NotificationService.getInstance().notifyUserAction(getUsername(), action, targetUser);
    }
    
    private String extractUsernameFromAction(String action) {
        if (action == null) {
            return "unknown";
        }

        String prefix = "Added user: ";
        if (action.startsWith(prefix)) {
            return action.substring(prefix.length()).trim();
        }

        prefix = "Removed user: ";
        if (action.startsWith(prefix)) {
            return action.substring(prefix.length()).trim();
        }

        prefix = "Updated user: ";
        if (action.startsWith(prefix)) {
            return action.substring(prefix.length()).trim();
        }

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
        return String.format("%s, managedUsers=%d, logEntries=%d", 
                             super.toString(), managedUsers.size(), userActionsLog.size());
    }
}
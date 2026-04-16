// Assigned to: Тайкпанов Р. Р.
package service;

import java.util.ArrayList;
import java.util.List;

/**
 * Notification service implementing the Observer pattern (Subject)
 * Manages observers and notifies them of important events in the system
 */
public class NotificationService {
    private static NotificationService instance;
    private List<NotificationObserver> observers;
    
    private NotificationService() {
        observers = new ArrayList<>();
    }
    
    /**
     * Get singleton instance of NotificationService
     */
    public static synchronized NotificationService getInstance() {
        if (instance == null) {
            instance = new NotificationService();
        }
        return instance;
    }
    
    /**
     * Register an observer to receive notifications
     */
    public void registerObserver(NotificationObserver observer) {
        // TODO: Register observer only once (avoid duplicates).
    }
    
    /**
     * Unregister an observer
     */
    public void unregisterObserver(NotificationObserver observer) {
        observers.remove(observer);
    }
    
    /**
     * Notify all observers of an event
     */
    public void notifyObservers(String eventType, String message, Object data) {
        // TODO: Iterate through observers and call update(eventType, message, data).
        // Use try/catch so one failing observer does not break the notification chain.
    }
    
    /**
     * Convenience method for notifying about grade updates
     */
    public void notifyGradeUpdate(String studentId, String courseId, double mark) {
        String message = String.format("Grade updated for student %s in course %s: %.1f", 
                                      studentId, courseId, mark);
        notifyObservers("GRADE_UPDATE", message, new GradeUpdateData(studentId, courseId, mark));
    }
    
    /**
     * Convenience method for notifying about research paper publications
     */
    public void notifyPaperPublished(String researcherId, String paperTitle) {
        String message = String.format("New research paper published by researcher %s: %s", 
                                      researcherId, paperTitle);
        notifyObservers("PAPER_PUBLISHED", message, new PaperPublishedData(researcherId, paperTitle));
    }
    
    /**
     * Convenience method for notifying about user actions (admin tracking)
     */
    public void notifyUserAction(String adminId, String action, String targetUser) {
        String message = String.format("Admin %s performed action '%s' on user %s", 
                                      adminId, action, targetUser);
        notifyObservers("USER_ACTION", message, new UserActionData(adminId, action, targetUser));
    }
    
    /**
     * Data class for grade update notifications
     */
    public static class GradeUpdateData {
        public final String studentId;
        public final String courseId;
        public final double mark;
        
        public GradeUpdateData(String studentId, String courseId, double mark) {
            this.studentId = studentId;
            this.courseId = courseId;
            this.mark = mark;
        }
    }
    
    /**
     * Data class for paper publication notifications
     */
    public static class PaperPublishedData {
        public final String researcherId;
        public final String paperTitle;
        
        public PaperPublishedData(String researcherId, String paperTitle) {
            this.researcherId = researcherId;
            this.paperTitle = paperTitle;
        }
    }
    
    /**
     * Data class for user action notifications
     */
    public static class UserActionData {
        public final String adminId;
        public final String action;
        public final String targetUser;
        
        public UserActionData(String adminId, String action, String targetUser) {
            this.adminId = adminId;
            this.action = action;
            this.targetUser = targetUser;
        }
    }
}
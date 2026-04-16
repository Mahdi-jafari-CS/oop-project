package service;

/**
 * Observer interface for the Observer pattern
 * Defines the contract for objects that want to be notified of events
 */
public interface NotificationObserver {
    /**
     * Update method called when an event occurs
     * @param eventType Type of event (e.g., "GRADE_UPDATE", "PAPER_PUBLISHED")
     * @param message Detailed message about the event
     * @param data Additional data related to the event
     */
    void update(String eventType, String message, Object data);
}
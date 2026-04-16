package exceptions;

/**
 * Custom exception thrown when a student fails more than 3 courses
 */
public class FailLimitException extends Exception {
    
    private final int failedCount;
    private final int maxFails;
    private final String studentName;
    
    public FailLimitException(String studentName, int failedCount, int maxFails) {
        super("Student '" + studentName + "' has failed " + failedCount + 
              " courses, exceeding the maximum of " + maxFails + " allowed failures");
        this.studentName = studentName;
        this.failedCount = failedCount;
        this.maxFails = maxFails;
    }
    
    public int getFailedCount() {
        return failedCount;
    }
    
    public int getMaxFails() {
        return maxFails;
    }
    
    public String getStudentName() {
        return studentName;
    }
}
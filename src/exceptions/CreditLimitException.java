package exceptions;

/**
 * Custom exception thrown when a student tries to register for more than 21 credits
 */
public class CreditLimitException extends Exception {
    
    private final int attemptedCredits;
    private final int maxCredits;
    private final String studentName;
    
    public CreditLimitException(String studentName, int attemptedCredits, int maxCredits) {
        super("Student '" + studentName + "' attempted to register for " + attemptedCredits + 
              " credits, exceeding the maximum of " + maxCredits + " credits");
        this.studentName = studentName;
        this.attemptedCredits = attemptedCredits;
        this.maxCredits = maxCredits;
    }
    
    public int getAttemptedCredits() {
        return attemptedCredits;
    }
    
    public int getMaxCredits() {
        return maxCredits;
    }
    
    public String getStudentName() {
        return studentName;
    }
}
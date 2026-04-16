package exceptions;

/**
 * Custom exception thrown when a person with h-index < 3 is assigned as a research supervisor
 */
public class HIndexTooLowException extends Exception {
    
    private final int hIndex;
    private final String personName;
    
    public HIndexTooLowException(String personName, int hIndex) {
        super("Person '" + personName + "' has h-index " + hIndex + " which is below the required minimum of 3");
        this.personName = personName;
        this.hIndex = hIndex;
    }
    
    public int getHIndex() {
        return hIndex;
    }
    
    public String getPersonName() {
        return personName;
    }
}
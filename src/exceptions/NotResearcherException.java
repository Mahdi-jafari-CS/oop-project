package exceptions;

/**
 * Custom exception thrown when a non-researcher tries to join a research project
 */
public class NotResearcherException extends Exception {
    
    private final String personName;
    private final String personType;
    
    public NotResearcherException(String personName, String personType) {
        super("Person '" + personName + "' (" + personType + ") is not a researcher and cannot join research projects");
        this.personName = personName;
        this.personType = personType;
    }
    
    public String getPersonName() {
        return personName;
    }
    
    public String getPersonType() {
        return personType;
    }
}
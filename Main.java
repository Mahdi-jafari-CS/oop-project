// Assigned to: Каналхан Б. Н.
import ui.ConsoleUI;

/**
 * Main class to run the Research University Information System
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Research University Information System...");
        System.out.println("=================================================");
        
        // Create and run the console UI
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.run();
    }
}
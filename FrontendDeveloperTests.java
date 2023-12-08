import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrontendDeveloperTests {
    /**
     * Tests the findClosestConnection() method of the FrontendInterface.
     * This method simulates a valid user input and checks if the findClosestConnection() method of the frontend
     * correctly initializes the program, verifying the output for the expected findClosestConnection() message.
     */
    @Test
    public void testClosestFriend() throws IOException {
        String input = "3\nSunny\nRachel"; // Simulated user input to start the program
        TextUITester tester = new TextUITester(input);
        Scanner scanner = new Scanner(System.in);
        BackendPlaceholder backend = new BackendPlaceholder();
        Frontend frontend = new Frontend(scanner, backend);
        frontend.mainMenu();
        String output = tester.checkOutput();
        // Check that the output contains the expected start message
        assertTrue(output.contains("Closest"));
    }
    /**
     * Tests the mainMenu method of the FrontendInterface.
     * This method simulates a user input for a valid option and checks if the main menu
     * correctly processes this input, verifying the output for the expected response.
     */
    @Test
    public void testMainMenu() throws IOException {
        String input = "2\n";
        TextUITester tester = new TextUITester(input);
        Scanner scanner = new Scanner(System.in);
        BackendPlaceholder backend = new BackendPlaceholder();
        Frontend frontend = new Frontend(scanner, backend);
        frontend.mainMenu();
        String output = tester.checkOutput();
        // Check that the output contains the expected main menu message
        assertTrue(output.contains("stat"));
    }
    /**
     * Tests the loadDataFile method of the FrontendInterface.
     * This method simulates a user input to load a data file and checks if the frontend
     * correctly processes this request, verifying the output for the expected confirmation message.
     */
    @Test
    public void testLoadDataFile() throws IOException {
        String input = "1.dot\n"; // Replace with the option to load data
        TextUITester tester = new TextUITester(input);
        Scanner scanner = new Scanner(System.in);
        BackendPlaceholder backend = new BackendPlaceholder();
        Frontend frontend = new Frontend(scanner, backend) {
        };
        frontend.loadDataFile();
        String output = tester.checkOutput();
        // Check that the output contains the expected message
        assertTrue(output.contains("Data file loaded"));
    }
    /**
     * Tests the handling of invalid options in the mainMenu method of the FrontendInterface.
     * This method simulates a user input for an invalid option and checks if the main menu
     * correctly handles this input, verifying the output for the expected error message.
     */
    @Test
    public void testInvalidOptionMainMenu() throws IOException {
        String input = "invalidOption\n"; // Simulated user input for an invalid option
        TextUITester tester = new TextUITester(input);
        Scanner scanner = new Scanner(System.in);
        BackendPlaceholder backend = new BackendPlaceholder();
        Frontend frontend = new Frontend(scanner, backend);
        frontend.mainMenu();
        String output = tester.checkOutput();
        // Check that the output contains the expected message
        assertTrue(output.contains("Invalid"));
    }
    /**
     * Tests the functionality of exiting the program from the mainMenu of the FrontendInterface.
     * This method simulates a user input for the option to exit the program and checks if the main menu
     * correctly processes this request, verifying the output for the expected exit message.
     */
    @Test
    public void testExitProgram() throws IOException {
        String input = "4\n"; // Replace with the option to exit the program
        TextUITester tester = new TextUITester(input);
        Scanner scanner = new Scanner(System.in);
        BackendPlaceholder backend = new BackendPlaceholder();
        Frontend frontend = new Frontend(scanner, backend);
        frontend.mainMenu();
        String output = tester.checkOutput();
        // Check that the output contains the expected message
        assertTrue(output.contains("Exiting"));
    }

}

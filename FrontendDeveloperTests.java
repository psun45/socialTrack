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
        String input = "1\nsocialnetwork.dot\n3\nuser1\nuser2\n4"; // Simulated user input to start the program
        TextUITester tester = new TextUITester(input);
        Scanner scanner = new Scanner(System.in);
        Backend backend = new Backend();
        Frontend frontend = new Frontend(scanner, backend);
        frontend.start();
        String output = tester.checkOutput();
        // Check that the output contains the expected start message
        assertTrue(output.contains("user1"));
    }
    /**
     * Tests the mainMenu method of the FrontendInterface.
     * This method simulates a user input for a valid option and checks if the main menu
     * correctly processes this input, verifying the output for the expected response.
     */
    @Test
    public void testMainMenu() throws IOException {
        String input = "1\nsocialnetwork.dot\n2\n4";
        TextUITester tester = new TextUITester(input);
        Scanner scanner = new Scanner(System.in);
        Backend backend = new Backend();
        Frontend frontend = new Frontend(scanner, backend);
        frontend.start();
        String output = tester.checkOutput();
        // Check that the output contains the expected main menu message
        assertTrue(output.contains("100"));
    }
    /**
     * Tests the loadDataFile method of the FrontendInterface.
     * This method simulates a user input to load a data file and checks if the frontend
     * correctly processes this request, verifying the output for the expected confirmation message.
     */
    @Test
    public void testLoadDataFile() throws IOException {
        String input = "1\n1.dot\n4"; // Replace with the option to load data
        TextUITester tester = new TextUITester(input);
        Scanner scanner = new Scanner(System.in);
        Backend backend = new Backend();
        Frontend frontend = new Frontend(scanner, backend) {
        };
        frontend.start();
        String output = tester.checkOutput();
        // Check that the output contains the expected message
        assertTrue(output.contains("Error"));
    }
    /**
     * Tests the handling of invalid options in the mainMenu method of the FrontendInterface.
     * This method simulates a user input for an invalid option and checks if the main menu
     * correctly handles this input, verifying the output for the expected error message.
     */
    @Test
    public void testInvalidOptionMainMenu() throws IOException {
        String input = "invalidOption\n4"; // Simulated user input for an invalid option
        TextUITester tester = new TextUITester(input);
        Scanner scanner = new Scanner(System.in);
        Backend backend = new Backend();
        Frontend frontend = new Frontend(scanner, backend);
        frontend.start();
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
        Backend backend = new Backend();
        Frontend frontend = new Frontend(scanner, backend);
        frontend.mainMenu();
        String output = tester.checkOutput();
        // Check that the output contains the expected message
        assertTrue(output.contains("Exiting"));
    }
    /**
     *testIntegrationGetClosestConnection simulates the scenario where a user interacts with the frontend to
     * find the closest connection between two users in a social network. The method
     * tests the integration of the Frontend with the BackendPlaceholder, specifically
     * focusing on the functionality that computes and displays the shortest path or
     * connection between two given users.
     * The test finds the closest connection between "User37" and "User86" after
     * loading the social network data from "socialnetwork.dot". It then checks if the
     * output contains the names "User37" and "User86", ensuring that the frontend
     * correctly displays the result from the backend operation.
     *
     * @throws IOException if an I/O error occurs when creating the scanner or processing the input.
     */
    @Test
    public void testIntegrationGetClosestConnection() throws IOException {
        String input = "1\nsocialnetwork.dot\n3\nuser37\nuser86\n4"; // Simulated user input to start the program
        TextUITester tester = new TextUITester(input);
        Scanner scanner = new Scanner(System.in);
        Backend backend = new Backend();
        Frontend frontend = new Frontend(scanner, backend);
        frontend.start();
        String output = tester.checkOutput();
        // Check that the output contains the expected start message
        assertTrue(output.contains("user37") && output.contains("user86"));
    }
    /**
     * testIntegrationGetAppStats uses the integration of the Frontend with the BackendPlaceholder,
     * specifically focusing on retrieving and displaying application statistics such as
     * "friends per user". It simulates a user's interaction with the frontend to load
     * the social network data and then request the application statistics.
     * This test provides simulated user input to load the social network data from the file
     * "socialnetwork.dot" and then select the option to display the application statistics.
     * The output is then checked to confirm that it contains the expected statistics information,
     * such as the average number of friends per user, indicating successful integration and
     * functionality of the frontend in displaying the backend-provided data.
     *
     * @throws IOException if an I/O error occurs when creating the scanner or processing the input.
     */
    @Test
    public void testIntegrationGetAppStats() throws IOException {
        String input = "1\nsocialnetwork.dot\n2\n4\n"; // Simulated user input to start the program
        TextUITester tester = new TextUITester(input);
        Scanner scanner = new Scanner(System.in);
        Backend backend = new Backend();
        Frontend frontend = new Frontend(scanner, backend);
        frontend.start();
        String output = tester.checkOutput();
        // Check that the output contains the expected start message
        assertTrue(output.contains("Friends"));
    }
/**
     * Tests the `getClosestConnection` method of the Backend class.
     * This test verifies that the method correctly identifies the shortest path
     * (closest connection) between two users in a social network graph.
     * The path returned should contain "user1" as part of the shortest path.
     * - The number of friends on this path should be 2.
     *
     * @throws IOException if reading from the "socialnetwork.dot" file fails.
     */
    @Test
    public void testGetClosestConnection() throws IOException {
        Backend backend = new Backend();
        backend.readDOTFile("socialnetwork.dot");
        ShortestPathInterface<String> path = backend.getClosestConnection("user0", "user1");
        assertTrue(path.getFriendPath().contains("user1"));
        assertEquals(2, path.getNumIntermediaryFriends());
    }
    /**
     * Tests the `getAppStats` method of the Backend class.
     * This test checks whether the method accurately provides statistics about
     * the social network graph after loading data from a .dot file.
     * The method should return a string containing the statistics of the graph.
     *
     * @throws IOException if reading from the "socialnetwork.dot" file fails.
     */
    @Test
    public void testShowingStat() throws IOException {
        Backend backend = new Backend();
        backend.readDOTFile("socialnetwork.dot");
        String actual = backend.getAppStats();
        assertTrue(actual.contains("100") && actual.contains("343"));
    }
}

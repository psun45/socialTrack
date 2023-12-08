import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

public class BackendDeveloperTests {

  /**
   * This test ensures that the readDOTFile() method signals when it is unsuccessful
   * by returning false.
   */
  @Test
  public void testReadFileInvalid() {
    BackendInterface backend = new Backend();
    assertFalse(backend.readDOTFile("notDOT.exe")); // not DOT file
    assertFalse(backend.readDOTFile("notreal.dot")); // DOT file, does not exist
  }

  /**
   * This test ensures that the readDOTFile() method loads a DOT file, does not throw
   * any exceptions, and returns true when given an existing, valid DOT file.
   */
  @Test
  public void testReadFileValid() {
    BackendInterface backend = new Backend();
    assertTrue(backend.readDOTFile("socialnetwork.dot")); // Checks for exceptions and return value

    String emptyGraphStats = "Total Users: 0\nTotal Friendships: 0\nAverage Friends Per User: NaN\n";
    assertFalse(backend.getAppStats().equals(emptyGraphStats)); // Graph should not be empty
  }

  /**
   * This test checks the functionality of the getAppStats() method, which returns a
   * string containing the total number of users, total number of friendships, and
   * average friendships per user. This test uses an empty app, mimicking if a user
   * were to call this method before loading a DOT File.
   */
  @Test
  public void testGetEmptyAppStats() {
    // Initially empty, no users or friendships, NaN average friends
    BackendInterface backend = new Backend();
    String emptyAppStats = backend.getAppStats();
    String emptyGraphStats = "Total Users: 0\nTotal Friendships: 0\nAverage Friends Per User: NaN\n";
    assertEquals(emptyAppStats, emptyGraphStats, "Did not properly display stats for an empty graph!");
  }

  /**
   * This test checks the functionality of the getAppStats() method, which returns a
   * string containing the total number of users, total number of friendships, and
   * average friendships per user. This test uses an empty app, mimicking if a user
   * were to call this method before loading a DOT File.
   */
  @Test
  public void testGetAppStats() {
    BackendInterface backend = new Backend();
    backend.readDOTFile("socialnetwork.dot");
    String appStats = backend.getAppStats();
    String graphStats = "Total Users: 100\nTotal Friendships: 343\nAverage Friends Per User: 3.43\n";
    assertEquals(appStats, graphStats, "Did not properly display stats for Social Track App!");
  }

  /**
   * This method tests the behavior of the backend's getClosestConnection() method.
   * For now, it only checks whether this method throws exceptions and if the returned
   * Shortest Path object is not null after calling getClosestConnection(). This test will
   * be expanded upon after the full backend implementation is developed.
   */
  @Test
  public void testGetClosestConnectionValid() {
    BackendInterface backend = new Backend();
    backend.readDOTFile("socialnetwork.dot");
    ShortestPathInterface<String> friendPath = backend.getClosestConnection("user1", "user10");
    List<String> expectedList = new LinkedList<>();
    expectedList.add("user1");
    expectedList.add("user0");
    expectedList.add("user10");
    assertEquals(friendPath.getFriendPath(), expectedList);
    assertEquals(friendPath.getNumIntermediaryFriends(), 3);
  }

  /**
   * This method tests the output of the Frontend's showStatisticsAboutDataset() method.
   * This test will ensure that the Frontend has been properly integrated with the Backend.
   */
  @Test
  public void testAppStatsIntegration() {
    Backend backend = new Backend();
    backend.readDOTFile("socialnetwork.dot");

    // used to check output, no input given within showStatisticsAboutDataset()
    TextUITester uiTester = new TextUITester("");
    Scanner input = new Scanner(System.in);

    FrontendInterface frontend = new Frontend(input, backend);

    // ends with extra newline as Frontend uses println() to separate interactions
    String graphStats = "Total Users: 100\nTotal Friendships: 343\nAverage Friends Per User: 3.43\n\n";
    frontend.showStatisticsAboutDataset();
    String output = uiTester.checkOutput();

    assertEquals(output, graphStats, "Frontend did not display app stats to the user!");
  }

  /**
   * This method tests the output of the Frontend's findClosestConnection() method.
   * This test will ensure that the Frontend has been properly integrated with the Backend.
   */
  @Test
  public void testShortestPathIntegration() {
    Backend backend = new Backend();
    backend.readDOTFile("socialnetwork.dot");


    // used to check output, gives user1 and user10 as the endpoints of the shortest path search
    TextUITester uiTester = new TextUITester("user1\nuser10\n");
    Scanner input = new Scanner(System.in);
    FrontendInterface frontend = new Frontend(input, backend);

    List<String> expectedList = new LinkedList<>(); // shortest path between user1 and user10
    expectedList.add("user1");
    expectedList.add("user0");
    expectedList.add("user10");

    frontend.findClosestConnection(); // will get input from uiTester/scanner
    String output = uiTester.checkOutput();

    assertTrue(output.contains(expectedList.toString()), "Frontend did not display the shortest path " +
      "between two users!");
  }

  /**
   * This method tests how the Frontend's loadDataFile() method behaves when given a filepath that does not
   * lead to a DOT file. In particular, it makes sure that this method gives helpful feedback to the user,
   * rather than throwing an error which would cause the app to stop functioning.
   */
  @Test
  public void testFrontendLoadNonDOTFile() {
    Backend backend = new Backend();
    TextUITester uiTester = new TextUITester("notdot.exe\n"); // gives a non-DOT file as filepath
    Scanner input = new Scanner(System.in);
    Frontend frontend = new Frontend(input, backend);
    try {
      frontend.loadDataFile();
    } catch (Exception e) {
      assertTrue(false, "Threw an exception instead of giving feedback when given non-DOT filepath!");
    }
    String output = uiTester.checkOutput();
    assertTrue(output.contains("Please enter a valid .dot file."), "Did not give feedback when given a non-DOT file!");
  }

  /**
   * This method tests how the Frontend's loadDataFile() method behaves when given no input for the filepath.
   * In particular, it makes sure that this method gives helpful feedback to the user, rather than throwing an
   * error which would cause the app to stop functioning.
   */
  @Test
  public void testFrontendLoadEmptyFilepath() {
    Backend backend = new Backend();
    TextUITester uiTester = new TextUITester("\n"); // gives no filepath
    Scanner input = new Scanner(System.in);
    Frontend frontend = new Frontend(input, backend);
    try {
      frontend.loadDataFile();
    } catch (Exception e) {
      assertTrue(false, "Threw an exception instead of giving feedback when given empty filepath!");
    }
    String output = uiTester.checkOutput();
    assertTrue(output.contains("Please enter a valid file path."), "Did not give feedback when given an empty filepath!");
  }

}

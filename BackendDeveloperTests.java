import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.util.List;
import java.util.LinkedList;

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

}

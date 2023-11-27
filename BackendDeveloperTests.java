import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.LinkedList;

public class BackendDeveloperTests {

  /**
   * This test ensures that the readDOTFile() method responds appropriately when given
   * an invalid file name (i.e. throws an exception).
   */
  @Test
  public void testReadFileInvalid() {
    BackendInterface backend = new SocialTrackBackend();
    try {
      backend.readDOTFile("notreal.dot");
    } catch (Exception notFound) {
      assertEquals(notFound.getMessage(), "File not found! Please enter a valid file name.");
    }
  }

  /**
   * This test ensures that the readDOTFile() method does not throw any exceptions when given
   * an existing, valid DOT file.
   */
  @Test
  public void testReadFileValid() {
    BackendInterface backend = new SocialTrackBackend();
    try {
      backend.readDOTFile("socialnetwork.dot");
    } catch (Exception unexpected) {
      assertTrue(false, "Encountered exception given valid file: " + unexpected.getMessage());
    }
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
    BackendInterface backend = new SocialTrackBackend();
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
    BackendInterface backend = new SocialTrackBackend();
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
    BackendInterface backend = new SocialTrackBackend();
    List<DijkstraGraph.SearchNode> friendPath = new LinkedList<>();
    try {
      friendPath = backend.getClosestConnection("user1", "user10");
    } catch (Exception unexpected) {
      assertTrue(false, "Encountered exception when finding the shortest path between two users: "
       + unexpected.getMessage());
    }
    assertTrue(friendPath != null, "ShortestPath object remained null after method call!");
  }

}

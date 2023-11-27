import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class SocialTrackBackend implements BackendInterface {

  private DijkstraGraph<String, Integer> userGraph;

  public SocialTrackBackend() {
    this.userGraph = new DijkstraGraph<>(new PlaceholderMap<>());
  }

  @Override
  public void readDOTFile(String filepath) {

  }

  @Override
  public List<DijkstraGraph.SearchNode> getClosestConnection(String firstUser,
      String secondUser) {
    return null;
  }

  @Override
  public String getAppStats() {
    String appStats = "";
    appStats += "Total Users: " + userGraph.getNodeCount() + "\n";
    appStats += "Total Friendships: " + userGraph.getEdgeCount() + "\n";
    double averageFriends = (double) userGraph.getEdgeCount() / userGraph.getNodeCount();
    appStats += "Average Friends Per User: " + averageFriends + "\n";
    return appStats;
  }
}

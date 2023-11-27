import java.util.List;

public interface BackendInterface {

  /**
   * Constructor method for the backend of our Social Track App.
   * Commented out to allow compiling.
   */
  //public SocialTrackBackend(GraphADT<String, Double> friendGraph);

  /**
   * Reads data from a file in DOT format.
   * This data will be stored in the GraphADT argument given to the constructor.
   *
   * @param filepath the filepath to the DOT file to read from
   */
  public void readDOTFile(String filepath);

  /**
   * Gets the closest connection (the shortest path) between two users.
   */
  public List<DijkstraGraph.SearchNode> getClosestConnection(String firstUser, String secondUser);

  /**
   * Gets statistics for the users of this Social Track app, and returns a string
   * containing these stats (# of users, # of friendships, avg. friends per user).
   */
  public String getAppStats();

}

import java.io.IOException;

public interface BackendInterface {

  /**
   * Constructor method for the backend of our Social Track App.
   * Commented out to allow compiling.
   */
  // public SocialTrackBackend(GraphADT<String, Double> friendGraph);

  /**
   * Reads data from a file in DOT format.
   * This data will be stored in the GraphADT argument given to the constructor.
   *
   * @param filepath the filepath to the DOT file to read from
   * @return true if the file was read successfully, false otherwise
   */
  public boolean readDOTFile(String filepath);

  /**
   * Gets the closest connection (the shortest path) between two users.
   */
  public ShortestPathInterface<String> getClosestConnection(String firstUser, String secondUser);

  /**
   * Gets statistics for the users of this Social Track app, and returns a string
   * containing these stats (# of users, # of friendships, avg. friends per user).
   */
  public String getAppStats();

}

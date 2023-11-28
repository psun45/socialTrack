import java.util.List;

public interface ShortestPathInterface<T> {

  /**
   * Constructor method for a Shortest Path instance.
   * Run Dijkstra's algorithm to find this path, then store it as a
   * list of nodes (users).
   * Commented out to allow compiling.
   */
  // public ShortestPath<T>(List<T> path);

  /**
   * Getter method for the shortest path between two users.
   *
   * @return the list of users that form the shortest path, provided in the
   *         constructor
   */
  public List<T> getFriendPath();

  /**
   * Getter method for the number of intermediary friends
   * in the shortest path between two users.
   *
   * @return number of friends in the shortest path
   */
  public int getNumIntermediaryFriends();

}

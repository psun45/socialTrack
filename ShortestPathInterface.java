import java.util.List;

public interface ShortestPathInterface<T> {

  /**
   * Constructor method for a Shortest Path instance.
   * Stores the results of a Dijkstra's algorithm search as a list of SearchNodes (users).
   * Commented out to allow compiling.
   */
  //public ShortestPath<T>(List<T> userList);

  /**
   * Getter method for the shortest path between two users (as a list of users).
   *
   * @return the list of users that form the shortest path
   */
  public List<T> getFriendPath();

  /**
   * Getter method for the number of friends in the shortest path between two users.
   *
   * @return number of friends in the shortest path
   */
  public int getNumIntermediaryFriends();

}

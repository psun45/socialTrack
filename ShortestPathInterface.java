import java.util.List;

public interface ShortestPathInterface<T> {

  /**
   * Constructor method for a Shortest Path instance.
   * Runs Dijkstra's algorithm to find this path, then stores it as a
   * list of nodes (users), and stores the number of nodes in this path.
   * Commented out to allow compiling.
   */
  //public ShortestPath<T>(T firstUser, T secondUser);

  /**
   * Getter method for the shortest path between two users.
   *
   * @return the list of users that form the shortest path
   */
  public List<T> getFriendPath(String firstUser, String secondUser);

  /**
   * Getter method for the number of intermediary friends
   * in the shortest path between two users.
   *
   * @return number of friends in the shortest path
   */
  public int getNumIntermediaryFriends();

}

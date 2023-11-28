import java.util.List;

public class ShortestPath implements ShortestPathInterface<String> {

  // The result of a Dijkstra's Algorithm search
  private List<String> userList;

  public ShortestPath(List<String> userList) {
    this.userList = userList;
  }

  @Override
  public List<String> getFriendPath() { return userList; }

  @Override
  public int getNumIntermediaryFriends() {
    return userList.size();
  }

}

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Backend implements BackendInterface {

  private DijkstraGraph<String, Double> userGraph;
  private int numFriendships = 0; // different from edge count

  public Backend() {
    this.userGraph = new DijkstraGraph<>(new PlaceholderMap<>());
  }

  @Override
  public boolean readDOTFile(String filepath) {
    // Creates File Object
    if (!filepath.contains(".dot")) { return false; }
    File dotFile = new File(filepath);
    if (!dotFile.exists() || !dotFile.canRead()) { return false; }

    // Creates Scanner for DOT File
    Scanner scnr;
    try {
      scnr = new Scanner(dotFile);
    } catch (FileNotFoundException notFound) {
      return false;
    }

    // Stores a lines in a linked list, letting us skip the first and last line easily
    List<String> lineList = new LinkedList<>();
    while (scnr.hasNextLine()) {
      String line = scnr.nextLine();
      lineList.add(line);
    }

    // Skips first and last lines
    for (int i = 1; i < lineList.size()-1; ++i) {
      // Retrieve line and split into two substrings, each containing a user
      String line = lineList.get(i);
      String[] lineData = line.trim().split(" -- ");

      // Must remove quotation marks from first users
      String firstUser = lineData[0];
      firstUser = firstUser.replaceAll("\"", "");

      // Must remove quotation marks and semicolons from second users
      String secondUser = lineData[1];
      secondUser = secondUser.replaceAll("\"","").replaceAll(";","");

      // Add users if they are not already in the graph
      if (!userGraph.containsNode(firstUser)) { userGraph.insertNode(firstUser); }
      if (!userGraph.containsNode(secondUser)) { userGraph.insertNode(secondUser); }

      // All edges will be 1.0 weight, inserts edges going both ways to allow searches
      userGraph.insertEdge(firstUser, secondUser, 1.0);
      userGraph.insertEdge(secondUser, firstUser, 1.0);
      ++numFriendships; // edge count increased by two, only one new friendship
    }

    return true; // no exceptions encountered, file loaded successfully
  }

  @Override
  public ShortestPathInterface<String> getClosestConnection(String firstUser,
      String secondUser) {
    // Run Dijkstra's Algorithm Search
    List<String> shortestPathList = userGraph.shortestPathData(firstUser, secondUser);
    return new ShortestPath(shortestPathList); // Shortest Path object passed to frontend
  }

  @Override
  public String getAppStats() {
    String appStats = "";
    appStats += "Total Users: " + userGraph.getNodeCount() + "\n";
    appStats += "Total Friendships: " + numFriendships + "\n";
    double averageFriends = (double) numFriendships / userGraph.getNodeCount();
    appStats += "Average Friends Per User: " + averageFriends + "\n";
    return appStats;
  }
}

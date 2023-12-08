// --== CS400 File Header Information ==--
// Name: Patrick Sun
// Email: psun45@wisc.edu
// Group and Team: F29
// Group TA: MATTHEW SCHWENNESEN
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.nio.file.Path;
import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     * <p>
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     *
     * @param map the map that the graph uses to map a data object to the node
     *            object it is stored in
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
            throw new NoSuchElementException("Start or end node not in graph");
        }
        PlaceholderMap<NodeType, SearchNode> nodeMap = new PlaceholderMap<>();
        PriorityQueue<SearchNode> queue = new PriorityQueue<>();

        // Initialize start node
        queue.add(new SearchNode(nodes.get(start), 0, null));
        // Continue until the queue is empty
        while (!queue.isEmpty()) {
            SearchNode currentSearchNode = queue.poll();
            Node currentNode = currentSearchNode.node;

            if (currentNode.data.equals(end)) {
                return currentSearchNode;  // Return the SearchNode for the end node
            }

            // Skip if the current node is already visited with a shorter path
            if (nodeMap.containsKey(currentNode.data) && nodeMap.get(currentNode.data).cost <= currentSearchNode.cost) {
                continue;
            }

            nodeMap.put(currentNode.data, currentSearchNode);

            for (Edge edge : currentNode.edgesLeaving) {
                NodeType neighborData = edge.successor.data; // Get the data of the neighbor node
                double newCost = currentSearchNode.cost + edge.data.doubleValue(); // Calculate the new cost
                // Add the neighbor node to the queue if it is not already visited or if the new cost is lower
                if (!nodeMap.containsKey(neighborData) || newCost < nodeMap.get(neighborData).cost) {
                    SearchNode newSearchNode = new SearchNode(edge.successor, newCost, currentSearchNode);
                    queue.add(newSearchNode);
                }
            }
        }


        throw new NoSuchElementException("Path does not exist from start to end node");

    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        // Compute the shortest path from start to end
        SearchNode endNode = computeShortestPath(start, end);
        LinkedList<NodeType> path = new LinkedList<>();

        while (endNode != null) {
            path.addFirst(endNode.node.data); // Add the node data to the front of the list
            endNode = endNode.predecessor;
        }

        return path;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        // Compute the shortest path from start to end
        SearchNode endNode = computeShortestPath(start, end);
        return endNode.cost;
    }

    /**
     * Tests the shortest path from node D to I using a traced example from the lecture.
     * This test recreates the specific graph structure and checks if the computed shortest path
     * matches the expected path that was previously calculated by hand during the lecture.
     */
    @Test
    public void testPathFromLectureExample() {
        //inserting the example in lecture
        DijkstraGraph<String, Double> graph = new DijkstraGraph(new PlaceholderMap<>());
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("L");
        graph.insertNode("M");
        graph.insertEdge("M", "E", 3.0);
        graph.insertEdge("A", "B", 1.0);
        graph.insertEdge("A", "H", 8.0);
        graph.insertEdge("A", "M", 5.0);
        graph.insertEdge("D", "A", 7.0);
        graph.insertEdge("D", "G", 2.0);
        graph.insertEdge("B", "M", 3.0);
        graph.insertEdge("F", "G", 9.0);
        graph.insertEdge("G", "L", 7.0);
        graph.insertEdge("H", "B", 6.0);
        graph.insertEdge("H", "I", 2.0);
        graph.insertEdge("I", "H", 2.0);
        graph.insertEdge("I", "D", 1.0);
        graph.insertEdge("I", "L", 5.0);

        //checking if the shortest path is correct
        List<String> shortestPath = graph.shortestPathData("D", "I");
        // Define the expected path as was determined in the lecture
        List<String> expectedPath = new ArrayList<>();
        expectedPath.add("D");
        expectedPath.add("A");
        expectedPath.add("H");
        expectedPath.add("I");
        Assertions.assertEquals(expectedPath, shortestPath);

    }

    /**
     * Tests the shortest path and cost from node D to M using the same graph from the lecture.
     * This test ensures that the graph can correctly compute the shortest path and cost between
     * two nodes that were not part of the traced example in the lecture.
     */
    @Test
    public void testPathWithDifferentStartAndEnd() {
        //inserting the example in lecture
        DijkstraGraph<String, Double> graph = new DijkstraGraph(new PlaceholderMap<>());
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("L");
        graph.insertNode("M");
        graph.insertEdge("M", "E", 3.0);
        graph.insertEdge("A", "B", 1.0);
        graph.insertEdge("A", "H", 8.0);
        graph.insertEdge("A", "M", 5.0);
        graph.insertEdge("D", "A", 7.0);
        graph.insertEdge("D", "G", 2.0);
        graph.insertEdge("B", "M", 3.0);
        graph.insertEdge("F", "G", 9.0);
        graph.insertEdge("G", "L", 7.0);
        graph.insertEdge("H", "B", 6.0);
        graph.insertEdge("H", "I", 2.0);
        graph.insertEdge("I", "H", 2.0);
        graph.insertEdge("I", "D", 1.0);
        graph.insertEdge("I", "L", 5.0);

        // Calculate the shortest path from node D to M
        List<String> shortestPath = graph.shortestPathData("D", "M");
        double cost = graph.shortestPathCost("D", "M");
        // Define the expected path and cost for this new start and end node
        double expectedCost = 11.0;
        List<String> expectedPath = new ArrayList<>();
        expectedPath.add("D");
        expectedPath.add("A");
        expectedPath.add("B");
        expectedPath.add("M");
        // Assert both the path and the cost match the expected values
        Assertions.assertEquals(expectedPath, shortestPath);
        Assertions.assertEquals(expectedCost, cost);

    }

    /**
     * Tests the behavior of the graph when attempting to find a shortest path between two nodes
     * where no such path exists. This ensures that the graph correctly handles scenarios where a
     * path between the start and end node cannot be formed due to lack of connecting edges.
     */
    @Test
    public void testPathDoesntExist() {
        //inserting the example in lecture
        DijkstraGraph<String, Double> graph = new DijkstraGraph(new PlaceholderMap<>());
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("L");
        graph.insertNode("M");
        graph.insertEdge("M", "E", 3.0);
        graph.insertEdge("A", "B", 1.0);
        graph.insertEdge("A", "H", 8.0);
        graph.insertEdge("A", "M", 5.0);
        graph.insertEdge("D", "A", 7.0);
        graph.insertEdge("D", "G", 2.0);
        graph.insertEdge("B", "M", 3.0);
        graph.insertEdge("F", "G", 9.0);
        graph.insertEdge("G", "L", 7.0);
        graph.insertEdge("H", "B", 6.0);
        graph.insertEdge("H", "I", 2.0);
        graph.insertEdge("I", "H", 2.0);
        graph.insertEdge("I", "D", 1.0);
        graph.insertEdge("I", "L", 5.0);
        // Try to compute the shortest path from node D to C, which is expected not to exist
        try {
            List<String> shortestPath = graph.shortestPathData("D", "C");
        } catch (NoSuchElementException e) {
            // Pass the test if the NoSuchElementException is caught, which is the expected behavior
            Assertions.assertTrue(true);
        }

    }
}

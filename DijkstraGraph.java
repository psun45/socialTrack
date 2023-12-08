// --== CS400 File Header Information ==--
// Name: Jordan Smith
// Email: jlsmith42@wisc.edu
// Group and Team: F29
// Group TA: Matthew Schwennesen
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.util.PriorityQueue;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions;

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
     * predecessor SearchNode within this path is referenced by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
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
     * @param map the map that the graph uses to map a data object to the node
     *        object it is stored in
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }


    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references representing
     * all the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
        // if either argument is null, throw an exception
        if (start == null) { throw new IllegalArgumentException("Start node cannot be null!"); }
        if (end == null) { throw new IllegalArgumentException("End node cannot be null!"); }

        // if either argument is not in the graph, throw an exception
        if (!this.containsNode(start)) { throw new NoSuchElementException("Start value not in graph!"); }
        if (!this.containsNode(end)) { throw new NoSuchElementException("End value not in graph!"); }

        SearchNode sourceNode = new SearchNode(nodes.get(start), 0, null);

        // Key - data, Value - SearchNode containing Node w/ data
        MapADT<NodeType, SearchNode> visitedNodes = new PlaceholderMap<>();
        visitedNodes.put(sourceNode.node.data, sourceNode); // add starting node

        // Stores Nodes to check for a shorter path, starts with first added
        PriorityQueue<SearchNode> searchQueue = new PriorityQueue<>();
        searchQueue.add(sourceNode); // add starting node
        if (start.equals(end)) { return visitedNodes.get(start); } // same node, no search needed

        // Keeps running until all paths have been searched for shortest path
        while (!searchQueue.isEmpty()) {
            SearchNode currNode = searchQueue.poll(); // returns and removes front of queue

            for (Edge leavingEdge : currNode.node.edgesLeaving) { // all adjacent nodes
                SearchNode adjacentNode = new SearchNode(nodes.get(leavingEdge.successor.data),
                    currNode.cost + leavingEdge.data.doubleValue(), currNode);

                // Case 1: Node not yet visited, always added
                if (!visitedNodes.containsKey(adjacentNode.node.data)) {
                    visitedNodes.put(adjacentNode.node.data, adjacentNode);
                    searchQueue.add(adjacentNode);
                }

                // Case 2: Node already visited, check for shorter distance
                if (visitedNodes.get(adjacentNode.node.data).cost > currNode.cost + leavingEdge.data.doubleValue()) {
                    // Update predecessor and distance
                    visitedNodes.get(adjacentNode.node.data).cost = currNode.cost + leavingEdge.data.doubleValue();
                    visitedNodes.get(adjacentNode.node.data).predecessor = currNode;
                    searchQueue.add(adjacentNode);
                }
            }
        }
        
        if (!visitedNodes.containsKey(end)) {
            throw new NoSuchElementException("No direct path found in search!");
        }

        return visitedNodes.get(end);
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shortest path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        LinkedList<NodeType> keyList = new LinkedList<>();

        // traces through predecessors to get to source node
        SearchNode currNode = this.computeShortestPath(start, end); // will detect errors
        while (currNode != null) {
            keyList.addFirst(currNode.node.data); // maintains proper node order when adding
            currNode = currNode.predecessor;
        }
        return keyList;
	}

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path from the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        return this.computeShortestPath(start, end).cost; // will detect errors
    }

    /**
     * Tests implementation of Dijkstra's algorithm using an example from lecture.
     */
    //@Test
    public static void testLectureExample() {
        // Graph Setup - Example from Week 9, Thursday
        DijkstraGraph<Character, Integer> dijkstraGraph = new DijkstraGraph<>(new PlaceholderMap<>());
        dijkstraGraph.insertNode('A');
        dijkstraGraph.insertNode('B');
        dijkstraGraph.insertNode('D');
        dijkstraGraph.insertNode('E');
        dijkstraGraph.insertNode('F');
        dijkstraGraph.insertNode('G');
        dijkstraGraph.insertNode('H');
        dijkstraGraph.insertNode('I');
        dijkstraGraph.insertNode('L');
        dijkstraGraph.insertNode('M');
        dijkstraGraph.insertEdge('A', 'B', 1);
        dijkstraGraph.insertEdge('A', 'H', 8);
        dijkstraGraph.insertEdge('A', 'M', 5);
        dijkstraGraph.insertEdge('B', 'M', 3);
        dijkstraGraph.insertEdge('D', 'A', 7);
        dijkstraGraph.insertEdge('D', 'G', 2);
        dijkstraGraph.insertEdge('F', 'G', 9);
        dijkstraGraph.insertEdge('G', 'L', 7);
        dijkstraGraph.insertEdge('H', 'I', 2);
        dijkstraGraph.insertEdge('H', 'B', 6);
        dijkstraGraph.insertEdge('I', 'D', 1);
        dijkstraGraph.insertEdge('I', 'L', 5);
        dijkstraGraph.insertEdge('M', 'F', 4);
        dijkstraGraph.insertEdge('M', 'E', 3);

        System.out.println(dijkstraGraph.shortestPathData('D', 'I'));
        System.out.println(dijkstraGraph.shortestPathCost('D', 'I'));
    }

    /**
     * Tests method to find cost and sequence of data using the same graph as the
     * previous test, but with different start and end points.
     */
    //@Test
    public static void testCostAndSequence() {
        // Graph Setup
        DijkstraGraph<Character, Integer> dijkstraGraph = new DijkstraGraph<>(new PlaceholderMap<>());
        dijkstraGraph.insertNode('A');
        dijkstraGraph.insertNode('B');
        dijkstraGraph.insertNode('D');
        dijkstraGraph.insertNode('E');
        dijkstraGraph.insertNode('F');
        dijkstraGraph.insertNode('G');
        dijkstraGraph.insertNode('H');
        dijkstraGraph.insertNode('I');
        dijkstraGraph.insertNode('L');
        dijkstraGraph.insertNode('M');
        dijkstraGraph.insertEdge('A', 'B', 1);
        dijkstraGraph.insertEdge('A', 'H', 8);
        dijkstraGraph.insertEdge('A', 'M', 5);
        dijkstraGraph.insertEdge('B', 'M', 3);
        dijkstraGraph.insertEdge('D', 'A', 7);
        dijkstraGraph.insertEdge('D', 'G', 2);
        dijkstraGraph.insertEdge('F', 'G', 9);
        dijkstraGraph.insertEdge('G', 'L', 7);
        dijkstraGraph.insertEdge('H', 'I', 2);
        dijkstraGraph.insertEdge('H', 'B', 6);
        dijkstraGraph.insertEdge('I', 'D', 1);
        dijkstraGraph.insertEdge('I', 'L', 5);
        dijkstraGraph.insertEdge('M', 'F', 4);
        dijkstraGraph.insertEdge('M', 'E', 3);

        System.out.println(dijkstraGraph.shortestPathData('I', 'E'));
        System.out.println(dijkstraGraph.shortestPathCost('I', 'E'));
    }

    /**
     * Tests implementation behavior when there is no directed path between the two
     * nodes the user is attempting to find a path between.
     */
    //@Test
    public static void testNoDirectedPath() {
        // Graph Setup
        DijkstraGraph<Character, Integer> dijkstraGraph = new DijkstraGraph<>(new PlaceholderMap<>());
        dijkstraGraph.insertNode('A');
        dijkstraGraph.insertNode('B');
        dijkstraGraph.insertNode('D');
        dijkstraGraph.insertNode('E');
        dijkstraGraph.insertNode('F');
        dijkstraGraph.insertNode('G');
        dijkstraGraph.insertNode('H');
        dijkstraGraph.insertNode('I');
        dijkstraGraph.insertNode('L');
        dijkstraGraph.insertNode('M');
        dijkstraGraph.insertEdge('A', 'B', 1);
        dijkstraGraph.insertEdge('A', 'H', 8);
        dijkstraGraph.insertEdge('A', 'M', 5);
        dijkstraGraph.insertEdge('B', 'M', 3);
        dijkstraGraph.insertEdge('D', 'A', 7);
        dijkstraGraph.insertEdge('D', 'G', 2);
        dijkstraGraph.insertEdge('F', 'G', 9);
        dijkstraGraph.insertEdge('G', 'L', 7);
        dijkstraGraph.insertEdge('H', 'I', 2);
        dijkstraGraph.insertEdge('H', 'B', 6);
        dijkstraGraph.insertEdge('I', 'D', 1);
        dijkstraGraph.insertEdge('I', 'L', 5);
        dijkstraGraph.insertEdge('M', 'F', 4);
        dijkstraGraph.insertEdge('M', 'E', 3);

        try {
            dijkstraGraph.computeShortestPath('G', 'I');
        } catch (NoSuchElementException noPath) {
            System.out.println(noPath.getMessage());
        }
        try {
            dijkstraGraph.shortestPathCost('G', 'I');
        } catch (NoSuchElementException noPath) {
            System.out.println(noPath.getMessage());
        }
        try {
            dijkstraGraph.shortestPathData('G', 'I');
        } catch (NoSuchElementException noPath) {
            System.out.println(noPath.getMessage());
        }
    }
    public static void main(String[] args) {
        testLectureExample();
        testNoDirectedPath();
        testCostAndSequence();
    }
}

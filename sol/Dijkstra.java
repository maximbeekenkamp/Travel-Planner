package sol;

import src.IDijkstra;
import src.IGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.function.Function;

/**
 * The Dijkstra class uses the IDijkstra interface and contains methods that implements 
 * Dijkstra's algorithm to find the shortest path between two cities.
 * @param <V>
 * @param <E>
 * 
 */

public class Dijkstra<V, E> implements IDijkstra<V, E> {
    private IGraph<V, E> currentGraph;
    private HashMap<V, E> historyMap;

    // TODO: implement the getShortestPath method!
    /**
     * The method getShortestPath returns a list of edges that represents the shortest path
     * between the source and destination cities whilst recognising the cost of edges defined 
     * by the edgeWeight function. The method can be called by TravelController when the you
     * input fast or cheap in the terminal (after data has been loaded).
     * find the shortest path.
     * @param graph       the graph which has the vertices
     * @param source      the source vertex
     * @param destination the destination vertex
     * @param edgeWeight A function to determine the cost or time of an edge depending on which method 
     * in the TravelController calls getShortestPath.
     * @return A list of edges that connects the source vertex to the destination vertex.
     */
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {
        // when you get to using a PriorityQueue, remember to remove and re-add a vertex to the
        // PriorityQueue when its priority changes!
        if (graph == null) {
            throw new IllegalArgumentException("Graph provided is not valid");
        }
        this.historyMap = new HashMap<>();
        this.currentGraph = graph;
        HashMap<V, Double> pathDistances = new HashMap<>();
        Comparator<V> whichCloser = (vertex1, vertex2) -> {
            return Double.compare(pathDistances.get(vertex1), pathDistances.get(vertex2));
        };
        PriorityQueue<V> checkingPQ = new PriorityQueue<>(whichCloser);

        this.fillpathDistances(pathDistances, source, checkingPQ);

        while(!checkingPQ.isEmpty()) {
            V checkingV = checkingPQ.poll();
            for (E edge : this.currentGraph.getOutgoingEdges(checkingV)) {
                V neighbour = this.currentGraph.getEdgeTarget(edge);
                double neighbourDistance = pathDistances.get(checkingV) + edgeWeight.apply(edge);
                if (neighbourDistance < pathDistances.get(neighbour)) {
                    pathDistances.replace(neighbour, neighbourDistance);
                    this.historyMap.put(neighbour, edge);
                    checkingPQ.remove(neighbour);
                    checkingPQ.add(neighbour);
                }
            }
        }

        if (this.historyMap.get(destination) != null){ //substitute for a canReach helper method
            return this.reconstructPath(source, destination);
        } else {
            return new ArrayList<>();
        }
    }

    // TODO: feel free to add your own methods here!
    
    /**
     * The method reconstructPath is a helper method for getShortestPath. It reconstructs the path
     * from the source to the destination by using the historyMap. Is only called if the destination
     * is reachable from the source (i.e. the historyMap contains the destination).
     * @param source
     * @param destination
     * @return A list of edges that connects the source vertex to the destination vertex.
     */
    private List<E> reconstructPath(V source, V destination) {
        List<E> path = new ArrayList<>();
        V currentVertex = destination;
        while (currentVertex != source) {
            E edge = this.historyMap.get(currentVertex);
            path.add(0, edge);
            currentVertex = this.currentGraph.getEdgeSource(edge);
        }
        return path;
    }
    /**
     * The method fillpathDistances is a helper method for getShortestPath. It fills the pathDistances
     * HashMap with the vertices in the graph and sets the distance of each vertex to infinity. The source
     * vertex is unique and is set to 0 and added to the checkingPQ.
     * @param pathDistances
     * @param source
     * @param checkingPQ
     */
    private void fillpathDistances(HashMap<V, Double> pathDistances, V source, PriorityQueue<V> checkingPQ) {
        for (V vertex : this.currentGraph.getVertices()) {
            if (vertex.equals(source)) {
                pathDistances.put(source, 0.0);
            } else {
                pathDistances.put(vertex, Double.POSITIVE_INFINITY);
            }
            checkingPQ.add(vertex);
        }
    }
}

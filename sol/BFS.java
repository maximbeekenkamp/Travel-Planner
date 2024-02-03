package sol;

import src.IBFS;
import src.IGraph;

import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Set;

/**
 * The BFS class uses the IBFS interface and contains methods that implements the 
 * Breadth First Search algorithm to find the shortest path between two cities.
 * @param <V>
 * @param <E>
 * 
 */
public class BFS<V, E> implements IBFS<V, E> {
    /**
     * An instance of the HashMap used to maintain the path.
     */
    private HashMap<V, E> mapOfPath;

    // TODO: implement the getPath method!
    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {
        if(canGetTo(graph, start, end)) {
            return findPath(graph, start, end);
        } else return new ArrayList<>();
    }

    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name

    /**
     * The method canGetTo returns a boolean if you can get to the end city (end param) 
     * from the start city (start param). When returning true, the method establishes a
     * HashMap that is used to retrace the path in the findPath method.
     * @param graph
     * @param start
     * @param end
     * @return
     */

    private boolean canGetTo(IGraph<V,E> graph, V start, V end) {
        HashSet<V> verified = new HashSet<>();
        Queue<V> next = new LinkedList<>();
        this.mapOfPath = new HashMap<>();
        if(graph == null) {
            throw new IllegalArgumentException("Graph provided is not valid");
        }
        next.add(start);
        while(!next.isEmpty()) {
            V current = next.poll();
            if(current.equals(end)) {
                return true;
            }
            Set<E> transports = graph.getOutgoingEdges(current);
            Set<V> neighbouringCities = new HashSet<>();
            for(E e : transports) {
                neighbouringCities.add(graph.getEdgeTarget(e));
            }
            for(V vertex : neighbouringCities) {
                if(vertex.equals(end)) {
                    this.mapOfPath.put(vertex, this.connectsVia(graph, current, vertex));
                    return true;
                }
                if(!verified.contains(vertex)) {
                    next.add(vertex);
                    this.mapOfPath.put(vertex, this.connectsVia(graph, current, vertex));
                    verified.add(vertex);
                }
            }
        }
        return false;
    }

    /**
     * The method findPath is a helper method that is used to find the path from the start city (start param)
     * to the end city (end param). It uses the HashMap established in canGetTo to find that path.
     * @param graph
     * @param start
     * @param end
     * @return
     */
    private List<E> findPath(IGraph<V,E> graph, V start, V end) {
        List<E> path = new ArrayList<>();
        V current = end;
        while(!current.equals(start)) {
            path.add(0, this.mapOfPath.get(current));
            current = graph.getEdgeSource(this.mapOfPath.get(current));
        }
        return path;
    }

    /**
     * The method connectsVia is a helper method that is used to find the edge that connects the start city (start param)
     * to the end city (end param).
     * @param graph
     * @param start
     * @param end
     * @return
     */
    private E connectsVia(IGraph<V,E> graph, V start, V end) {
        Set<E> transports = graph.getOutgoingEdges(start);
        for(E edge : transports) {
            if(graph.getEdgeTarget(edge).equals(end)) {
                return edge;
            }
        }
        return null;
    }
}

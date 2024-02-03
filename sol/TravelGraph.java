package sol;

import src.City;
import src.IGraph;
import src.Transport;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;


public class TravelGraph implements IGraph<City, Transport> {
    private HashMap<String, City> mapOfCities;

    /**
     * A constructor for TravelGraph that creates the hashmap used to get 
     * city objects from their names in the graph.
     */
    public TravelGraph() {
        this.mapOfCities = new HashMap<>();
    }

    @Override
    public void addVertex(City vertex) {
        // TODO: implement this method!
        this.mapOfCities.put(vertex.toString(), vertex);

    }

    @Override
    public void addEdge(City origin, Transport edge) {
        // TODO: implement this method!
        origin.addOut(edge);
    }

    @Override
    public Set<City> getVertices() {
        // TODO: implement this method!
        return new HashSet<>(this.mapOfCities.values());
    }

    @Override
    public City getEdgeSource(Transport edge) {
        // TODO: implement this method!
        return edge.getSource();
    }

    @Override
    public City getEdgeTarget(Transport edge) {
        // TODO: implement this method!
        return edge.getTarget();
    }

    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        // TODO: implement this method!
        return fromVertex.getOutgoing();
    }

    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name
    /**
     * The getCity method returns a City object from the string of the city
     * name (from the csv file) using a hashmap. The method will return null
     * if the string doesn't exist in the graph.
     * @param cityStringName
     * @return the desired City object.
     */
    public City getCity(String cityStringName) {
        return this.mapOfCities.get(cityStringName);
    }
}
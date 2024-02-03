package sol;

import src.City;
import src.ITravelController;
import src.Transport;
import src.TransportType;
import src.TravelCSVParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.Map;

/**
 * The TravelController class is the main class that takes the inputs from the repl and passes them
 * to the appropriate classes (BFS and Dijkstra) to determine the best routes in the manner requested.
 */

public class TravelController implements ITravelController<City, Transport> {

    // Why is this field of type TravelGraph and not IGraph?
    // Are there any advantages to declaring a field as a specific type rather than the interface?
    // If this were of type IGraph, could you access methods in TravelGraph not declared in IGraph?
    // Hint: perhaps you need to define a method!
    private TravelGraph graph;

    public TravelController() {
    }

    /**
     * The load method takes in the names of two csv files and uses the TravelCSVParser class to
     * parse the data in those files and create a TravelGraph object from that data.
     * @param citiesFile    the name of the csv file containing the cities
     * @param transportFile the name of the csv file containing the transportation
     * @return a message indicating whether the files were successfully loaded or not
     */

    @Override
    public String load(String citiesFile, String transportFile) {
        // TODO: instantiate a new Graph object in the graph field
        this.graph = new TravelGraph();
        // TODO: create an instance of the TravelCSVParser
        TravelCSVParser parser = new TravelCSVParser();
        // TODO: create a variable of type Function<Map<String, String>, Void>
        //       that will build the nodes in a graph. Keep in mind
        //       that the input to this function is a hashmap that relates the
        //       COLUMN NAMES of the csv to the VALUE IN THE COLUMN of the csv.
        //       It might be helpful to write a method in this class that takes the
        //       information from the csv needed to create an edge and uses that to
        //       build the edge on the graph.

        Function<Map<String, String>, Void> addVertex = map -> {
            this.graph.addVertex(new City(map.get("name")));
            return null; // need explicit return null to account for Void type
        };

        // TODO: create another variable of type Function<Map<String, String>, Void> which will
        //  build connections between nodes in a graph.
        Function<Map<String, String>, Void> addEdge = map -> {
            this.graph.addEdge(this.graph.getCity(map.get("origin")),
                    new Transport(this.graph.getCity(map.get("origin")),
                            this.graph.getCity(map.get("destination")),
                            TransportType.fromString(map.get("type")),
                            Double.parseDouble(map.get("price")),
                            Double.parseDouble(map.get("duration"))));
            return null;
        };
        // TODO: call parseLocations with the first Function variable as an argument and the right
        //  file
        try {
            parser.parseLocations(citiesFile, addVertex);
        } catch (IOException e) {
            return "Error parsing file: " + citiesFile;
        }

        // TODO: call parseTransportation with the second Function variable as an argument and
        //  the right file

        try{
            parser.parseTransportation(transportFile, addEdge);
        } catch (IOException e) {
            return "Error parsing file: " + transportFile;
        }
        // hint: note that parseLocations and parseTransportation can
        //       throw IOExceptions. How can you handle these calls cleanly?

        return "Successfully loaded cities and transportation files.";
    }

    /**
     * The fastestRoute method takes in the names of two cities and uses the Dijkstra class to
     * determine the fastest route between those two cities.
     * @param source      the name of the vertex (city) to start from
     * @param destination the name of the city to end at
     * @return a list of the edges that make up the fastest route between the two cities
     * @throws IllegalArgumentException if either of the cities are not in the graph
     */

    @Override
    public List<Transport> fastestRoute(String source, String destination) throws IllegalArgumentException {
        // TODO: implement this method!
        Function<Transport, Double> getDuration = transport -> {
            return transport.getMinutes();
        };
        City sourceCity = this.graph.getCity(source);
        City destinationCity = this.graph.getCity(destination);
        if (sourceCity == null || destinationCity == null) {
            throw new IllegalArgumentException("At least one city not found");
        }
        Dijkstra<City, Transport> myDijkstra = new Dijkstra<>();
        return myDijkstra.getShortestPath(this.graph, sourceCity, destinationCity, getDuration);
    }

    /**
     * The cheapestRoute method takes in the names of two cities and uses the Dijkstra class to
     * determine the cheapest route between those two cities.
     * @param source      the name of the vertex (city) to start from
     * @param destination the name of the city to end at
     * @return a list of the edges that make up the cheapest route between the two cities
     * @throws IllegalArgumentException if either of the cities are not in the graph
     */

    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        // TODO: implement this method!
        Function<Transport, Double> getPrice = transport -> {
            return transport.getPrice();
        };
        City sourceCity = this.graph.getCity(source);
        City destinationCity = this.graph.getCity(destination);
        if (sourceCity == null || destinationCity == null) {
            throw new IllegalArgumentException("At least one city not found");
        }
        Dijkstra<City, Transport> myDijkstra = new Dijkstra<>();
        return myDijkstra.getShortestPath(this.graph, sourceCity, destinationCity, getPrice);
    }

    /**
     * The mostDirectRoute method takes in the names of two cities and uses the BFS class to
     * determine the most direct route between those two cities.
     * @param source      the name of the vertex (city) to start from
     * @param destination the name of the city to end at
     * @return a list of the edges that make up the most direct route between the two cities
     * @throws IllegalArgumentException if either of the cities are not in the graph
     */
    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {
        // TODO: implement this method!
        City sourceCity = this.graph.getCity(source);
        City destinationCity = this.graph.getCity(destination);
        if (sourceCity == null || destinationCity == null) {
            throw new IllegalArgumentException("At least one city not found");
        }

        BFS<City, Transport> myBFS = new BFS<>();
        return myBFS.getPath(this.graph, sourceCity, destinationCity);
    }
}

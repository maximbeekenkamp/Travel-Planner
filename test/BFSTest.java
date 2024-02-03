package test;

import org.junit.Test;
import sol.BFS;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Your BFS tests should all go in this class!
 * The test we've given you will pass if you've implemented BFS correctly, but we still expect
 * you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class BFSTest {

    private static final double DELTA = 0.001;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;
    private SimpleVertex f;
    private SimpleGraph graph;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     * TODO: create more setup methods!
     */
    public void makeSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");
        this.f = new SimpleVertex("f");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);
        this.graph.addVertex(this.f);

        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph.addEdge(this.c, new SimpleEdge(1, this.c, this.e));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.e));
        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(100, this.f, this.e));
    }

    @Test
    public void testBasicBFS() {
        this.makeSimpleGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 200.0, DELTA);
        assertEquals(path.size(), 2);
    }

    // TODO: write more tests + make sure you test all the cases in your testing plan!
    // TODO: create a unique graph and exhaustively test your BFS implementation

    public void makeGraph(){
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");
        this.f = new SimpleVertex("f");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);
        this.graph.addVertex(this.f);

        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(2, this.b, this.c));
        this.graph.addEdge(this.c, new SimpleEdge(3, this.c, this.e));
        this.graph.addEdge(this.d, new SimpleEdge(4, this.d, this.e));
        this.graph.addEdge(this.a, new SimpleEdge(68, this.a, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(312, this.f, this.e));
        this.graph.addEdge(this.a, new SimpleEdge(231, this.a, this.d));
        this.graph.addEdge(this.d, new SimpleEdge(57, this.d, this.f));
        this.graph.addEdge(this.b, new SimpleEdge(22, this.b, this.d));
        this.graph.addEdge(this.d, new SimpleEdge(34, this.d, this.b));
        this.graph.addEdge(this.c, new SimpleEdge(8, this.c, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(97, this.f, this.c));
    }

    @Test
    public void testBFS(){
        this.makeGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 6.0, DELTA);
        assertEquals(path.size(), 3);
        path = bfs.getPath(this.graph, this.b, this.f);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 10.0, DELTA);
        assertEquals(path.size(), 2);
        path = bfs.getPath(this.graph, this.a, this.a);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 0.0, DELTA);
        assertEquals(path.size(), 0);

    }
}

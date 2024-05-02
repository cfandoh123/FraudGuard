import edu.princeton.cs.algs4.CC;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.KruskalMST;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RedBlackBST;

public class Clustering {
    private CC connectedPoints;       // connected points
    private int k;          // number of clusters
    private int m;          // number of data points


    // run the clustering algorithm and create the clusters
    public Clustering(Point2D[] locations, int k) {
        if (locations == null)
            throw new IllegalArgumentException("location cannot be null");
        this.k = k;
        m = locations.length;
        if (k < 1 || k > m)
            throw new IllegalArgumentException(
                    "k should be greater than one or less than m");

        EdgeWeightedGraph graph = createGraph(locations);
        RedBlackBST<Edge, Integer> edgesBST = sortEdges(graph);
        EdgeWeightedGraph clusters = createClusters(edgesBST);
        connectedPoints = new CC(clusters);


    }

    // create a graph from the locations
    private EdgeWeightedGraph createGraph(Point2D[] locations) {
        EdgeWeightedGraph graph = new EdgeWeightedGraph(m);
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                double distance = locations[i].distanceTo(locations[j]);
                Edge edge = new Edge(i, j, distance);
                graph.addEdge(edge);
            }
        }
        return graph;
    }

    // sort edges by putting them in a BST
    private RedBlackBST<Edge, Integer> sortEdges(EdgeWeightedGraph graph) {
        RedBlackBST<Edge, Integer> edges = new RedBlackBST<>();
        KruskalMST mst = new KruskalMST(graph);
        for (Edge e : mst.edges()) {
            edges.put(e, 1);
        }
        return edges;
    }

    // create clusters by adding the m-k smallest edges
    private EdgeWeightedGraph createClusters(RedBlackBST<Edge, Integer> edgesBST) {
        int count = 0;
        EdgeWeightedGraph clusters = new EdgeWeightedGraph(m);
        for (Edge e : edgesBST.keys()) {
            if (count == (m - k)) break;
            clusters.addEdge(e);
            count++;
        }
        return clusters;
    }

    // return the cluster of the ith point
    public int clusterOf(int i) {
        if (i < 0 || i >= m)
            throw new IllegalArgumentException("i is out of range");
        return connectedPoints.id(i);
    }

    // use the clusters to reduce the dimensions of an input
    public int[] reduceDimensions(int[] input) {
        if (input == null || input.length != m)
            throw new IllegalArgumentException(
                    "input array length should be equal to number of locations");
        int[] reducedArr = new int[k];
        for (int i = 0; i < m; i++) {
            int id = clusterOf(i);
            reducedArr[id] += input[i];
        }
        return reducedArr;
    }

    // unit testing (required)
    public static void main(String[] args) {
        In input = new In(args[0]);
        int m = input.readInt();
        int k = Integer.parseInt(args[1]);
        Point2D[] locations = new Point2D[m];
        for (int i = 0; i < m; i++) {
            double x = input.readDouble();
            double y = input.readDouble();
            locations[i] = new Point2D(x, y);
        }
        Clustering cluster = new Clustering(locations, k);
        System.out.println("Cluster IDs:");
        for (int i = 0; i < locations.length; i++) {
            System.out.println("Point " + i + " belongs to cluster " +
                                       cluster.clusterOf(i));
        }

        int[] samplePoints = { 1, 2, 3, 4, 5 };
        int[] reduced = cluster.reduceDimensions(samplePoints);
        System.out.print("Reduced dimensions: ");
        for (int value : reduced) {
            System.out.print(value + " ");
        }
        System.out.println();


    }
}

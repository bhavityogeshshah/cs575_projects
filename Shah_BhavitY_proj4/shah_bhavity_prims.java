import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class shah_bhavity_prims {

    /**
     * This function implements the Prims alogrithm using a priority implemented as
     * a min heap based on the weight of the graph.
     * 
     * @param graph
     * @param n
     */
    public void Prims(int graph[][], int n) {
        int visited[] = new int[n];
        ArrayList<String> mstList = new ArrayList<>();
        int[][] adj = new int[n][n];
        PriorityQueue<Edge> pq = new PriorityQueue<>(n, Comparator.comparingInt(edge -> edge.distance));
        pq.add(new Edge(0, -1, 0));
        for (int i = 0; i < n; i++) {
            visited[i] = 0;
        }

        while (pq.size() > 0) {
            int wt = pq.peek().distance;
            int node = pq.peek().vertex;
            int parent = pq.peek().parent;
            pq.remove();

            if (visited[node] == 1)
                continue;
            visited[node] = 1;
            if (parent != -1) {
                String s = "V" + (parent + 1) + "->" + "V" + (node + 1) + ":" + wt;
                mstList.add(s);
                adj[parent][node] = wt;
                adj[node][parent] = wt;
            }

            for (int i = 0; i < graph[node].length; i++) {
                if (i == node)
                    continue;
                int dist = graph[node][i];
                if (visited[i] == 0) {
                    pq.add(new Edge(i, node, dist));
                }
            }

        }

        System.out.println("MST Path:");
        for (int i = 0; i < mstList.size(); i++) {
            System.out.println(mstList.get(i));
        }
        System.out.println("Adj matrix after prims");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(adj[i][j] + "\t");
            }
            System.out.println();
        }

    }
}

class Edge {
    int vertex;
    int distance;
    int parent;

    public Edge(int vertexIn, int parentIn, int distanceIn) {
        vertex = vertexIn;
        parent = parentIn;
        distance = distanceIn;
    }
}

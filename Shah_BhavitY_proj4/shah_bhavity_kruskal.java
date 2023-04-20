import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class shah_bhavity_kruskal {
    /**
     * Kruskal's function used to craete the mst using kruskals algorithm
     * 
     * @param graph
     * @param n
     */

    public void kruskal(int[][] graph, int n) {
        int rank[] = new int[n];
        int parent[] = new int[n];
        ArrayList<String> mstList = new ArrayList<>();
        int[][] adj = new int[n][n];
        PriorityQueue<Edge> pq = new PriorityQueue<>(n, Comparator.comparingInt(edge -> edge.weight));

        // Intializing the priority queue to store the values of edges in sorted order
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i == j)
                    continue;
                pq.add(new Edge(i, j, graph[i][j]));
            }
        }

        // Intializing the rank and parent array to its respective values

        for (int i = 0; i < n; i++) {
            rank[i] = 0;
            parent[i] = i;
        }
        // Going over all the entries in priority queue

        while (pq.size() > 0) {
            Edge e = pq.poll();
            int src = e.src;
            int dst = e.dst;
            int wt = e.weight;

            int ucomp = find3(src, parent);
            int vcomp = find3(dst, parent);

            if (ucomp != vcomp) {
                String s = "V" + (src + 1) + " -> V" + (dst + 1) + " : " + wt;
                mstList.add(s);
                adj[src][dst] = wt;
                adj[dst][src] = wt;
                union3(ucomp, vcomp, parent, rank);
            }
        }

        // Printing MST path
        System.out.println("MST path after kruskal");
        for (int i = 0; i < mstList.size(); i++) {
            System.out.println(mstList.get(i));
        }

        // Printing adj matrix
        System.out.println("Adj after kruskal algorithm");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(adj[i][j] + "\t");
            }
            System.out.println();
        }

    }

    public int find3(int x, int parent[]) {
        int root = x;
        int node = x;
        while (root != parent[root]) {
            root = parent[root];
        }

        while (node != root) {
            int temp = parent[node];
            parent[node] = root;
            node = temp;
        }

        return root;
    }

    public void union3(int x, int y, int parent[], int rank[]) {
        if (rank[x] < rank[y])
            parent[x] = y;
        else if (rank[x] > rank[y])
            parent[y] = x;
        else {
            parent[y] = x;
            rank[x]++;
        }
    }

    class Edge {
        int src;
        int dst;
        int weight;

        public Edge(int srcIn, int dstIn, int weightIn) {
            src = srcIn;
            dst = dstIn;
            weight = weightIn;
        }
    }
}
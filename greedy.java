import java.io.*;
import java.util.*;

class greedy {
    static class Edge {
        int[] v = new int[2];
        int w;
    }

    static void union3(int[] height, int[] set, int repx, int repy) {
        if (height[repx] == height[repy]) {
            height[repx]++;
            set[repy] = repx; // y's tree points to x's tree
        } else if (height[repy] < height[repx]) {
            set[repy] = repx; // y's tree points to x's tree
        } else {
            set[repx] = repy; // x's tree points to y's tree
        }
    }

    static int find3(int x, int[] set) {
        int root, node, parent;

        // find root of tree with x
        root = x;
        while (root != set[root])
            root = set[root];

        // compress path from x to root
        node = x;
        while (node != root) {
            parent = set[node];
            set[node] = root; // node points to root
            node = parent;
        }
        return root;

    }

    static class EdgeComparator implements java.util.Comparator<Edge> {
        @Override
        public int compare(Edge a, Edge b) {
            return a.w - b.w;
        }
    }

    public static void kruskal(int n, int[][] adj_mat) {
        int total_edges = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (adj_mat[i][j] != 0) {
                    total_edges++;
                }
            }
        }

        Edge[] edgeArray = new Edge[total_edges];
        // filling the edges and their weights in edgeArray
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (adj_mat[i][j] != 0) {
                    edgeArray[count] = new Edge();
                    edgeArray[count].v[0] = i;
                    edgeArray[count].v[1] = j;
                    edgeArray[count].w = adj_mat[i][j];
                    count++;
                }
            }
        }

        Edge[] mstEdges = new Edge[n - 1];
        for (int i = 0; i < n - 1; i++) {
            mstEdges[i] = new Edge();
            mstEdges[i].w = Integer.MAX_VALUE;
        }

        // First edges should be sorted
        Arrays.sort(edgeArray, new EdgeComparator());

        int[] set = new int[n];
        int[] height = new int[n];

        for (int i = 0; i < n; i++) {
            set[i] = i;
            height[i] = 0;
        }

        int no_of_edges = 0;
        int[][] final_kruskal_matrix = new int[n][n];
        count = 0;
        // Since total edges in mst = vertices - 1
        while (count < n - 1 && (no_of_edges < total_edges)) {
            int ucomp = find3(edgeArray[no_of_edges].v[0], set);
            int vcomp = find3(edgeArray[no_of_edges].v[1], set);

            // If edge not in mst, add it to mst
            if (ucomp != vcomp) {
                mstEdges[count] = edgeArray[no_of_edges];
                union3(height, set, ucomp, vcomp);
                final_kruskal_matrix[edgeArray[no_of_edges].v[0]][edgeArray[no_of_edges].v[1]] = edgeArray[no_of_edges].w;
                final_kruskal_matrix[edgeArray[no_of_edges].v[1]][edgeArray[no_of_edges].v[0]] = edgeArray[no_of_edges].w;
                count++;
            }
            no_of_edges++;
        }

        // Initializing Kruskal's matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                final_kruskal_matrix[i][j] = 0;
            }
        }

        // Filling Kruskal's matrix
        int i = 0;
        while (i < n - 1) {
            if (mstEdges[i].w != Integer.MAX_VALUE) {
                final_kruskal_matrix[mstEdges[i].v[0]][mstEdges[i].v[1]] = mstEdges[i].w;
                final_kruskal_matrix[mstEdges[i].v[1]][mstEdges[i].v[0]] = mstEdges[i].w;
            }
            i++;
        }

        // Displaying Kruskal's matrix
        System.out.println("Kruskal's matrix:");
        for (i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%d  ", final_kruskal_matrix[i][j]);
            }
            System.out.println();
        }

        System.out.println("Minimimum spanning tree");
        i = 0;
        while (i < n - 1) {
            if (mstEdges[i].w != Integer.MAX_VALUE) {
                System.out.printf("V%d - V%d : %d\n", mstEdges[i].v[0] + 1, mstEdges[i].v[1] + 1, mstEdges[i].w);
            }
            i++;
        }

    }

    // ___________Prims______________
    static class Pair {
        int node;
        int distance;

        public Pair(int distance, int node) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static void prim(int n, int[][] adj_mat) {
        // int n = adjMat.length;
        int[] cost = new int[n];
        int[] prev = new int[n];
        boolean[] visit = new boolean[n];
        for (int i = 0; i < n; i++) {
            cost[i] = Integer.MAX_VALUE;
            prev[i] = -1;
            visit[i] = false;
        }
        cost[0] = 0; // cost for starting node is zero
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>((x, y) -> x.distance - y.distance);
        pq.add(new Pair(0, 0)); // begin at node 0
        while (!pq.isEmpty()) {
            int node = pq.poll().node;
            visit[node] = true;
            for (int vert = 0; vert < n; vert++) {
                if (adj_mat[node][vert] > 0 && !visit[vert] && adj_mat[node][vert] < cost[vert]) {
                    cost[vert] = adj_mat[node][vert];
                    prev[vert] = node;
                    pq.add(new Pair(cost[vert], vert));
                }
            }
        }
        System.out.println("Minimum Spanning Tree:");
        for (int i = 1; i < n; i++) {
            System.out.println("V" + (prev[i] + 1) + "-V" + (i + 1) + ": " + adj_mat[i][prev[i]]);
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = 10;// rand.nextInt(6) + 5;
        // Print randomly selected number of vertice s
        System.out.println("Randomly selected " + n + "vertices");
        // int[][] adj_mat = new int[n][n]; // initialize new adjency matrix
        // for(int i =0 ; i<n; i++){
        // for(int j =0;j<n;j++)
        // {
        // if(i==j){
        // adj_mat[i][j] = 0;
        // }
        // else {
        // adj_mat[i][j] = rand.nextInt(10) + 1;
        // adj_mat[j][i] = adj_mat[i][j];
        // }
        // }
        // }
        int[][] adj_mat = { { 0, 1, 4, 1, 6, 3, 3, 9, 3, 5 },
                { 1, 0, 1, 10, 9, 6, 7, 5, 7, 1 },
                { 4, 1, 0, 8, 8, 6, 5, 10, 6, 10 },
                { 1, 10, 8, 0, 10, 5, 2, 5, 3, 2 },
                { 6, 9, 8, 10, 0, 6, 8, 6, 1, 2 },
                { 3, 6, 6, 5, 6, 0, 1, 5, 8, 2 },
                { 3, 7, 5, 2, 8, 1, 0, 1, 6, 7 },
                { 9, 5, 10, 5, 6, 5, 1, 0, 2, 8 },
                { 3, 7, 6, 3, 1, 8, 6, 2, 0, 8 },
                { 5, 1, 10, 2, 2, 2, 7, 8, 8, 0 } };
        // int[][] adj_mat = {
        // {0, 5, 8, 7, 9},
        // {5, 0, 2, 8, 6},
        // {8, 2, 0, 7, 7},
        // {7, 8, 7, 0, 6},
        // {9, 6, 7, 6, 0}
        // };
        System.out.println("Random matrix");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%d ", adj_mat[i][j]);
            }
            System.out.println();
        }

        System.out.println("Select algorithm (prim|kruskal):");
        try {
            String name = br.readLine().toLowerCase();
            switch (name) {
                case "prim":
                    prim(n, adj_mat);
                    break;
                case "kruskal":
                    kruskal(n, adj_mat);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid input : expected prim or kruskal");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println();
        }

    }
}
import java.util.Random;

public class Shah_BhavitY_pa3_floyd {
    public static void main(String[] args) {

        // Generating the random integer n.

        Random r = new Random();

        int n = r.nextInt(5 + 1) + 5;

        System.out.println("Number of nodes in the graph " + n);

        // Generating the adj matrix with random integers.
        int[][] adjc = new int[n][n];

        for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
        adjc[i][j] = -1;
        }
        }

        for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
        if (i == j) {
        adjc[i][j] = 0;
        } else {
        if (adjc[i][j] == -1) {
        adjc[i][j] = r.nextInt(10) + 1;
        adjc[j][i] = adjc[i][j];
        }
        }
        }
        }


        // Printing adj matrix before calling the floyd function

        System.out.println("Adj Matrix Before floyd");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(adjc[i][j] + "\t");
            }
            System.out.println();
        }

        // Calling floyd function
        floyd(adjc, n);

    }

    public static void floyd(int[][] adj, int n) {

        int[][] temp = new int[n][n];
        int[][] path = new int[n][n];

        // Intializing the Path matrix

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                path[i][j] = 0;
            }
        }

        // Applying floyds algorithm to get shortest path for all source node.

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (adj[i][j] > (adj[i][k] + adj[k][j])) {
                        temp[i][j] = adj[i][k] + adj[k][j];
                        path[i][j] = (k + 1);
                    } else {
                        temp[i][j] = adj[i][j];
                    }
                }
            }
            adj = temp;
        }

        // Printing the final Adj and Path matrix

        System.out.println("Adj Matrix after floyd");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(adj[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("Path Matrix");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(path[i][j] + "\t");
            }
            System.out.println();
        }

        // Printing path from all source nodes to destination nodes
        for (int i = 0; i < n; i++) {
            System.out.println("V" + (i + 1) + "-VJ: Shortest path and length");
            for (int j = 0; j < n; j++) {
                System.out.print("V" + (i + 1));
                printPath(i, j, path);
                System.out.print(" V" + (j + 1) + ": " + adj[i][j] + "\n");
            }
            System.out.println();
        }

    }

    // Print Path method to print all the paths of the

    public static void printPath(int start, int end, int[][] path) {
        if (path[start][end] > 0) {
            printPath(start, (path[start][end] - 1), path);
            System.out.print(" V" + path[start][end]);
            printPath((path[start][end] - 1), end, path);
        } else {
            return;
        }
    }
}

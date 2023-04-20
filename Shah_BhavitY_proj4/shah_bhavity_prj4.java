import java.util.Random;
import java.util.Scanner;

public class shah_bhavity_prj4 {
    public static void main(String[] args) {

        Random r = new Random();

        // Generating random size for the number of vertex in a graph
        int n = r.nextInt(5 + 1) + 5;

        System.out.println("Number of nodes in the graph " + n);

        int[][] adjc = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjc[i][j] = -1;
            }
        }

        // Generating the adj matrix with random integers.

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

        // Printing the generate matrix

        System.out.println("Generated Adj Matrix");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(adjc[i][j] + "\t");
            }
            System.out.println();
        }

        // Asking user to select which algorrithm to be used

        Scanner sc = new Scanner(System.in);
        System.out.println("Please the algotirht to be used 1. Prim 2. Kruskal");
        String s = sc.nextLine();
        sc.close();

        // validating and calling appropiate fucntion to execute ahead

        if (s.equalsIgnoreCase("Prim")) {
            shah_bhavity_prims prim = new shah_bhavity_prims();
            prim.Prims(adjc, n);
        } else if (s.equalsIgnoreCase("Kruskal")) {
            shah_bhavity_kruskal kru = new shah_bhavity_kruskal();
            kru.kruskal(adjc, n);
        } else {
            System.err.println("Error Incorrect Algorithm selected");
            System.exit(-1);
        }
    }
}
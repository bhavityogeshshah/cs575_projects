import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

public class createkn01 {
    public static void main(String[] args) {
        File createFile = null;
        if (args.length != 2) {
            System.err.println("Incorrect number of arguments");
            System.exit(-1);
        } else {
            createFile = new File(args[1]);
        }
        Random r = new Random();

        int n = r.nextInt(4 + 1) + 4;

        int profit[] = new int[n];
        int weight[] = new int[n];
        int sum = 0, capacity;
        for (int i = 0; i < n; i++) {
            profit[i] = r.nextInt(20 + 1) + 10;
            weight[i] = r.nextInt(15 + 1) + 5;
            sum += weight[i];
        }
        capacity = (int) Math.floor(sum * 0.6);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(createFile);
        } catch (Exception e) {
            System.err.println("File not found");
            System.exit(-1);
        }

        try {
            pw.println(n + "\t" + capacity);
            for (int i = 0; i < n; i++) {
                pw.println("Item" + (i + 1) + "\t" + profit[i] + "\t" + weight[i]);
            }
            pw.close();
        } catch (Exception e) {

        }

    }
}
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class bruteforce {
    public static void main(String[] args) {
        File input = null;
        if (args.length != 2) {
            System.err.println("Incorrect number of arguments");
            System.exit(-1);
        } else {
            input = new File(args[1]);
        }
        Scanner sc = null;
        try {
            sc = new Scanner(input);
        } catch (Exception e) {
            System.err.println("File not found exception");
            System.exit(-1);
        }

        String line = sc.nextLine();
        String s[] = line.split("\t");
        int items = Integer.parseInt(s[0]);
        int max_weight = Integer.parseInt(s[1]);
        int profit[] = new int[items];
        int weight[] = new int[items];
        boolean selected[] = new boolean[items];
        for (int i = 0; i < items; i++) {
            line = sc.nextLine();
            String s1[] = line.split("\t");
            profit[i] = Integer.parseInt(s1[1]);
            weight[i] = Integer.parseInt(s1[2]);
        }
        sc.close();
        int totalprofit = knapsack(profit, weight, max_weight, items, selected);
        ArrayList<String> out = new ArrayList<>();
        int count = 0, twt = 0;
        for (int i = 0; i < items; i++) {
            if (selected[i]) {
                String s2 = "Item" + (i + 1) + "\t" + profit[i] + "\t" + weight[i];
                out.add(s2);
                count++;
                twt += weight[i];
            }
        }

        File outpu = new File("output1.txt");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(outpu);
        } catch (Exception e) {
            System.err.println("Could not open the file");
            System.exit(1);
        }

        try {
            pw.println(count + "\t" + totalprofit + "\t" + twt);
            for (int i = 0; i < out.size(); i++) {
                pw.println(out.get(i));
            }
            pw.close();
        } catch (Exception e) {
            System.err.println("Could not write to file");
            System.exit(1);
        }

    }

    /**
     * performs brute force knapsack with recursion
     * 
     * @param values
     * @param weights
     * @param capacity
     * @param n
     * @param selected
     * @return
     */
    public static int knapsack(int[] values, int[] weights, int capacity, int n, boolean[] selected) {
        
        if (n == 0 || capacity == 0) {
            return 0;
        }

       
        if (weights[n - 1] > capacity) {
            return knapsack(values, weights, capacity, n - 1, selected);
        }
        
        else {
            boolean[] includeSelected = Arrays.copyOf(selected, selected.length);
            includeSelected[n - 1] = true;
            int includeValue = values[n - 1]
                    + knapsack(values, weights, capacity - weights[n - 1], n - 1, includeSelected);
            int excludeValue = knapsack(values, weights, capacity, n - 1, selected);
            if (includeValue > excludeValue) {
                
                System.arraycopy(includeSelected, 0, selected, 0, includeSelected.length);
                return includeValue;
            } else {
                
                return excludeValue;
            }
        }
    }

}

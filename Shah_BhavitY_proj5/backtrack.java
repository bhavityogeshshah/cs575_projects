import java.io.File;
import java.util.Scanner;

public class backtrack {
    static int select_count = 0;
    static int max_profit = 0;
    static int[] selected;
    static int[] best;

    /**
     * read contents from the file
     * 
     * @param nodeIn
     * @param filename
     */
    public static void read_file(Node nodeIn, String filename) {
        try {
            Scanner sc = new Scanner(new File(filename));
            nodeIn.length = sc.nextInt();
            nodeIn.max_capacity = sc.nextInt();
            sc.nextLine();

            for (int i = 0; i < nodeIn.length; i++) {
                String line = sc.nextLine();
                String s[] = line.split("\t");

                nodeIn.item[i] = i;
                nodeIn.pro[i] = Integer.parseInt(s[1]);
                nodeIn.wt[i] = Integer.parseInt(s[2]);

            }
        } catch (Exception e) {
            System.err.println("Could not find the file in read_file");
            System.exit(-1);
        }
    }

    /**
     * sort all the elements by p/w ratio from higher to lower
     * 
     * @param nodeIn
     */
    public static void sort_by_pw(Node nodeIn) {
        double p_w_1, p_w_2;
        for (int i = 0; i < nodeIn.length; i++) {
            p_w_1 = (double) nodeIn.pro[i] / (double) nodeIn.wt[i];
            for (int j = 0; j < nodeIn.length; j++) {
                p_w_2 = nodeIn.pro[j] / nodeIn.wt[j];
                if (p_w_2 < p_w_1) {
                    int temp = nodeIn.item[i];
                    nodeIn.item[i] = nodeIn.item[j];
                    nodeIn.item[j] = temp;

                    temp = nodeIn.pro[i];
                    nodeIn.pro[i] = nodeIn.pro[j];
                    nodeIn.pro[j] = temp;

                    temp = nodeIn.wt[i];
                    nodeIn.wt[i] = nodeIn.wt[j];
                    nodeIn.wt[j] = temp;
                }
            }
        }
    }

    /**
     * check if the node is promising or not
     * 
     * @param nodeIn
     * @param i
     * @param wt
     * @param pro
     * @return
     */
    public static boolean promising(Node nodeIn, int i, int wt, int pro) {
        if (wt > nodeIn.max_capacity) {
            return false;
        }
        double bound = kwf2(i, wt, pro, nodeIn);
        return bound > max_profit;
    }

    /**
     * calculate the bound of each item
     * 
     * @param i
     * @param weight
     * @param profit
     * @param nodeIn
     * @return
     */

    public static double kwf2(int i, int weight, int profit, Node nodeIn) {
        double bound = profit;
        double fraction;
        while (weight < nodeIn.max_capacity && i < nodeIn.length) {
            if (weight + nodeIn.wt[i] <= nodeIn.max_capacity) {
                weight += nodeIn.wt[i];
                bound += nodeIn.pro[i];
            } else {
                fraction = (double) (nodeIn.max_capacity - weight) / (double) nodeIn.wt[i];
                weight = nodeIn.max_capacity;
                bound += (int) (fraction * nodeIn.pro[i]);
            }
            i++;
        }
        return bound;
    }

    public static void perform_kanpsack(Node nodeIn, int i, int profit, int weight, ResultWriter resIn) {
        if (weight <= nodeIn.max_capacity && profit > max_profit) {
            max_profit = profit;
            System.arraycopy(selected, 0, best, 0, nodeIn.length);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Node: ");
        sb.append(" Total Profit: " + profit);
        sb.append(" Total Weight: " + weight);
        if (promising(nodeIn, i, weight, profit)) {
            double bound = kwf2(i, weight, profit, nodeIn);
            sb.append(" Bound: " + bound);
            resIn.write(sb.toString());
            selected[i] = 1;
            perform_kanpsack(nodeIn, i + 1, profit + nodeIn.pro[i], weight + nodeIn.wt[i], resIn);
            selected[i] = 0;
            perform_kanpsack(nodeIn, i + 1, profit, weight, resIn);
        } else {
            double bound = kwf2(i, weight, profit, nodeIn);
            sb.append(" Bound: " + bound);
            resIn.write(sb.toString());
        }
    }

    public static void printorder(Node nodeIn) {
        for (int i = 0; i < nodeIn.length; i++) {
            System.out.print("Item" + nodeIn.item[i] + "\t" + nodeIn.wt[i] + "\t" + nodeIn.pro[i] + "\n");
        }
    }

    public static void start_knapsack(Node nodeIn) {
        sort_by_pw(nodeIn);
        // printorder(nodeIn);
        int totalweight = 0;
        selected = new int[nodeIn.length];
        best = new int[nodeIn.length];
        max_profit = Integer.MIN_VALUE;
        ResultWriter res = new ResultWriter("entries3.txt");
        perform_kanpsack(nodeIn, 0, 0, 0, res);
        res.close();
    }

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Incorrect Number of arguments give");
            System.exit(-1);
        }
        int length = 0;
        // create knapsack and initialize with random values
        try (Scanner sc = new Scanner(new File(args[1]))) {
            length = sc.nextInt();
        } catch (Exception e) {
            System.err.println("Input file does not exist, Please Check");
            System.exit(-1);
        }
        Node node = new Node(length);
        read_file(node, args[1]);
        start_knapsack(node);
        int totalweight = 0;
        ResultWriter resout = new ResultWriter("output3.txt");
        resout.write("Total Profit:" + max_profit);
        resout.write("Item\tProfit\tWeight");
        for (int i = 0; i < node.length; i++) {
            if (best[i] == 1) {
                resout.write("Item" + (node.item[i] + 1) + "\t" + node.pro[i] + "\t" + node.wt[i]);
                totalweight += node.wt[i];
                select_count++;
            }
        }
        resout.write("\nTotal Weight:" + totalweight + "\nSelected Items: " + select_count);
        resout.close();

    }
}

class Node {
    int length;
    int[] wt, pro, item;
    int max_capacity;

    public Node(int lengthIn) {
        length = lengthIn;
        wt = new int[length];
        pro = new int[length];
        item = new int[length];
        max_capacity = -1;
    }
}

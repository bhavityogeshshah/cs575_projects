import java.io.File;
import java.util.Scanner;

public class dynpro {
    static int values[];
    static int weight[], item[];
    static int length, max_capacity;
    static int[][] dp;
    static int[][] selected;

    static int dynapro(int items_left, int max_cap) {
        if (items_left == 0 || max_cap == 0) {
            selected[items_left][max_cap] = 0;
            return 0;
        } else {
            if (weight[items_left - 1] > max_cap) {
                selected[items_left][max_cap] = 0;
                return dynapro(items_left - 1, max_cap);
            } else {
                int part1 = dynapro(items_left - 1, max_cap);
                int part2 = dynapro(items_left - 1, max_cap - weight[items_left - 1]) + values[items_left - 1];

                if (part1 > part2) {
                    selected[items_left][max_cap] = 0;
                    dp[items_left][max_cap] = part1;
                } else {
                    selected[items_left][max_cap] = 1;
                    dp[items_left][max_cap] = part2;
                }
            }
            return dp[items_left][max_cap];
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Incorrect number of arguments given");
            System.exit(-1);
        }
        try {
            Scanner sc = new Scanner(new File(args[1]));
            length = sc.nextInt();
            max_capacity = sc.nextInt();
            sc.nextLine();
            weight = new int[length];
            values = new int[length];
            item = new int[length];
            dp = new int[length + 1][max_capacity + 1];
            selected = new int[length + 1][max_capacity + 1];

            for (int i = 0; i < length; i++) {
                String line = sc.nextLine();
                String s[] = line.split("\t");

                item[i] = i;
                values[i] = Integer.parseInt(s[1]);
                weight[i] = Integer.parseInt(s[2]);

            }
        } catch (Exception e) {
            System.err.println("Could not find the file in read_file");
            System.exit(-1);
        }
        int max_profit = dynapro(length, max_capacity);

        ResultWriter resent = new ResultWriter("entries02.txt");

        for (int i = 1; i <= length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int w = 1; w <= max_capacity; w++) {
                sb.append(dp[i][w] + "\t");
            }
            resent.write(sb.toString());
        }

        resent.close();

        ResultWriter resout = new ResultWriter("output2.txt");
        resout.write("Max profit: " + max_profit);
        resout.write("Item\tProfit\tWeight");
        int itemtrack = length;
        int selectcount = 0;
        int totalweight = 0;
        int wlf = max_capacity;
        while (itemtrack > 0 && wlf > 0) {
            if (selected[itemtrack][wlf] == 1) {
                totalweight += weight[itemtrack - 1];
                resout.write("Item" + itemtrack + "\t" + values[itemtrack - 1] + "\t" + weight[itemtrack - 1]);
                wlf -= weight[itemtrack - 1];
                selectcount++;
            }
            itemtrack--;
        }

        resout.write("Total weight: " + totalweight + "\nSelected Items: " + selectcount);
        resout.close();
    }
}

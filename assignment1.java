
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//java assignment1.java -m market_price.txt -p price_list.txt

public class assignment1 {
    static HashMap<String, Integer> pricemap = new HashMap<>();

    public static void main(String[] args) {
        File marketF = null;
        File priceF = null;
        if (args.length != 4) {
            System.err.println("Incorrect number of arguments expected only 4 arguments");
        } else {
            if (args[0].equalsIgnoreCase("-m")) {
                marketF = new File(args[1]);
            } else if (args[0].equalsIgnoreCase("-p")) {
                priceF = new File(args[1]);
            }
            if (args[2].equalsIgnoreCase("-m")) {
                marketF = new File(args[3]);
            } else if (args[2].equalsIgnoreCase("-p")) {
                priceF = new File(args[3]);
            }
        }
        File outfile = new File("output.txt");
        Scanner marketList = null;
        Scanner priceList = null;
        try {
            marketList = new Scanner(marketF);
            priceList = new Scanner(priceF);
        } catch (Exception e) {
            System.err.println("File not found exception");
            System.exit(1);
        }
        String line;
        try {
            String size = marketList.nextLine();
            int length = 0;
            try {
                length = Integer.parseInt(size);
            } catch (Exception e) {
                System.err.println("Unable to convert string to int");
                System.exit(1);
            }
            for (int i = 0; i < length; i++) {
                if (!marketList.hasNext()) {
                    System.err.println("Incorrect input file data");
                    System.exit(1);
                }
                line = marketList.nextLine();
                String[] s = line.split(" ");
                int n = Integer.parseInt(s[1]);
                pricemap.put(s[0], n);
            }
        } catch (Exception e) {
            System.err.println("Input file not found exception,Market Price");
            System.exit(1);
        }
        ResultWriter rw = new ResultWriter(outfile);

        // reading pricelist file
        try {
            while (priceList.hasNext()) {
                line = priceList.nextLine();
                String[] s = line.split(" ");
                ArrayList<String> sline = new ArrayList<>();
                int sizeofava = Integer.parseInt(s[0]);
                if (sizeofava < 0) {
                    System.err.print("Incorrect input file price list");
                    System.exit(1);
                }
                int maxmoney = Integer.parseInt(s[1]);
                for (int i = 0; i < sizeofava; i++) {
                    if (!priceList.hasNext()) {
                        System.err.println("Incorrect Input file Expected line " + sizeofava + " got " + i);
                        System.exit(1);
                    }
                    line = priceList.nextLine();
                    sline.add(line);
                }
                getmax(sline, maxmoney, rw);
            }
        } catch (Exception e) {
            System.err.println("Input file not found exception,Price List");
            System.exit(1);
        }
        marketList.close();
        priceList.close();
        rw.close();
    }

    /**
     * 
     * @param availableCards
     * @param pricemap
     * @param maxMoney
     */

    public static void getmax(ArrayList<String> subsetList, int maxMoney, ResultWriter rwIn) {
        long start = System.currentTimeMillis();
        int profitTemp = 0, i, totalCost = 0, marketValue = 0, countOfCardsPurchasedTemp = 0,
                profit = Integer.MIN_VALUE, count = 0;
        int maxMoneyTemp = maxMoney;
        String out = "";
        // ArrayList<ArrayList<String>> result = getSubsets(subsetList);
        int maxsize = 1 << subsetList.size();
        for (i = 1; i < maxsize; i++) {
            ArrayList<String> temp = getSubsets(subsetList, i);
            maxMoneyTemp = maxMoney;
            marketValue = 0;
            totalCost = 0;
            countOfCardsPurchasedTemp = 0;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < temp.size(); j++) {
                String s[] = temp.get(j).split(" ");
                int cost = Integer.parseInt(s[1]);
                if (!pricemap.containsKey(s[0])) {
                    System.err.println("incorrect input file price list");
                    System.exit(1);
                }

                if (cost <= maxMoneyTemp) {
                    totalCost += cost;
                    maxMoneyTemp -= cost;
                    marketValue += pricemap.get(s[0]);
                    countOfCardsPurchasedTemp++;
                    sb.append(s[0] + "\n");
                }
            }

            profitTemp = marketValue - totalCost;
            if (profit < profitTemp) {
                profit = profitTemp;
                count = countOfCardsPurchasedTemp;
                out = sb.toString();
            }
        }
        long endtime = System.currentTimeMillis();
        float duration = (endtime - start) / 1000F;
        rwIn.write(subsetList.size() + " " + profit + " " + count + " " + duration);
        rwIn.write(out);
    }

    /**
     * 
     * @param input
     * @return
     */
    public static ArrayList<String> getSubsets(ArrayList<String> input, int maxNumber) {
        ArrayList<ArrayList<String>> l = new ArrayList<>();
        for (int i = 0; i <= maxNumber; i++) {
            ArrayList<String> subset = new ArrayList<>();
            for (int j = 0; j < input.size(); j++) {
                if (((i >> j) & 1) == 1) {
                    subset.add(input.get(j));
                }
            }
            l.add(subset);
        }
        return l.get(maxNumber);
    }
}

class ResultWriter {
    private PrintWriter regResults = null;

    public ResultWriter(File regResultsIn) {
        try {
            regResults = new PrintWriter(regResultsIn);
        } catch (Exception e) {
            System.err.println("Exception: Output File not found");
            System.exit(1);
        } finally {
        }
    }

    public void write(String s) {
        regResults.println(s);
        regResults.flush();
    }

    public void close() {
        regResults.close();
    }
}

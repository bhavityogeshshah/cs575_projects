public class Shah_BhavitY_pa3_lcs {
    public static void main(String[] args) {

        /*
         * verifying whether we have exactly 2 input parameters
         */
        if (args.length != 2) {
            System.err.println("The program accepts only 2 arguments as input");
            System.exit(-1);
        }

        String s1 = args[0];
        String s2 = args[1];

        /*
         * verifying the length of each String
         */
        if ((s1.length() <= 100 && s2.length() <= 100) && (s1.length() > 0 && s2.length() > 0))
            LCS(s1, s2);
        else {
            System.err.println("Length of string greater than 100");
            System.exit(-2);
        }
    }

    /*
     * The LCS function that uses memoization technique to fill up the 2D array of numbers and String 
     */
    public static void LCS(String s1, String s2) {
        int lens1 = s1.length() + 1;
        int lens2 = s2.length() + 1;

        int[][] lcs = new int[lens1][lens2];
        String lcss[][] = new String[lens1][lens2];

        for (int i = 0; i < lens1; i++) {
            lcs[i][0] = 0;
        }
        for (int i = 0; i < lens2; i++) {
            lcs[0][i] = 0;
        }


        for (int i = 1; i < lens1; i++) {
            for (int j = 1; j < lens2; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    lcs[i][j] = lcs[i - 1][j - 1] + 1;
                    lcss[i][j] = "D";
                } else {
                    if (lcs[i - 1][j] >= lcs[i][j - 1]) {
                        lcs[i][j] = lcs[i - 1][j];
                        lcss[i][j] = "U";
                    } else {
                        lcs[i][j] = lcs[i][j - 1];
                        lcss[i][j] = "L";
                    }
                }
            }
        }


        // Following code responsible for finding the lcs string  
        int size = lcs[lens1 - 1][lens2 - 1];
        char[] outstring = new char[size + 1];
        int index = size;

        lens1 = lens1 - 1;
        lens2 = lens2 - 1;

        while (lens1 > 0 && lens2 > 0) {
            if (s1.charAt(lens1 - 1) == s2.charAt(lens2 - 1)) {
                outstring[index - 1] = s1.charAt(lens1 - 1);
                lens1--;
                lens2--;
                index--;
            }

            else if (lcs[lens1 - 1][lens2] > lcs[lens1][lens2 - 1])
                lens1--;
            else
                lens2--;
        }


        // Printing the output
       if(size != 0){
        System.out.print("Longest Common Subsquence is: ");
        for (int i = 0; i < size; i++)
            System.out.print(outstring[i]);
        System.out.println("\nSize of the LCS: " + size);
       }
       else{
        System.out.println("No lcs found");
       }
        
    }
}
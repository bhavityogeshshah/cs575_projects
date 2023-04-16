import java.util.Scanner;

public class program2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter size of board (0 to quit):");
        int size = sc.nextInt();
        if (size < 1) {
            System.exit(1);
        }
        int temp = size;
        while (temp % 2 == 0) {
            temp = temp / 2;
        }
        if (temp != 1) {
            System.err.println("The size entered is does not belong to 2^n please enter a correct value.");
            System.exit(1);
        }

        System.out.println("Please enter coordinates of missing square (separate by a space) in the range of 0 to "
                + (size - 1) + ":");
        int x_missing = sc.nextInt();
        int y_missing = sc.nextInt();
        if (y_missing >= size || x_missing >= size || x_missing < 0 || y_missing < 0) {
            System.out.println("Missing Square out of bound");
            System.exit(1);
        }
        Tromino tr = new Tromino(size, x_missing, y_missing);
        if (size > 1) {
            tr.TrominoSet(size, 0, 0, x_missing, y_missing);
        }
        tr.print();
        sc.close();

    }
}

// Tromino Class to arrange the tiles and print the board
class Tromino {

    String[][] chessBoard;

    public Tromino(int size, int x_missing, int y_missing) {
        chessBoard = new String[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                chessBoard[i][j] = "X";
            }
        }
        chessBoard[x_missing][y_missing] = "MS";
        rotateMatrix r = new rotateMatrix();
        chessBoard = r.rotateMatrixImpl(chessBoard);
    }

    public void TrominoSet(int size, int x_start, int y_start, int x_missing, int y_missing) {
        String s = "";
        for (int i = x_start; i < x_start + size; i++) {
            for (int j = y_start; j < y_start + size; j++) {
                if (chessBoard[i][j] != "X") {
                    x_missing = i;
                    y_missing = j;
                }
            }
        }
        if (size == 2) {
            if (x_start == x_missing && y_start == y_missing) {
                s = "LR";
            } else if (x_start == x_missing && (y_start + 1) == y_missing) {
                s = "LL";
            } else if (x_start + 1 == x_missing && y_start == y_missing) {
                s = "UR";
            } else {
                s = "UL";
            }
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (chessBoard[x_start + i][y_start + j].equals("X"))
                        chessBoard[x_start + i][y_start + j] = s;
                }
            }
        } else {

            // Check for the quadrant where the hole is there and initialize the other holes
            // a tromino and make recursive calls.

            // upperleft
            if (x_missing < x_start + size / 2 && y_missing < y_start + size / 2) {

                TrominoSet(size / 2, x_start, y_start, 0, 0);

                chessBoard[x_start + size / 2][y_start + size / 2 - 1] = "LR";
                chessBoard[x_start + size / 2][y_start + size / 2] = "LR";
                chessBoard[x_start + size / 2 - 1][y_start + size / 2] = "LR";

                TrominoSet(size / 2, x_start, y_start + size / 2, 0, 0);
                TrominoSet(size / 2, x_start + size / 2, y_start, 0, 0);
                TrominoSet(size / 2, x_start + size / 2, y_start + size / 2, 0, 0);
            }

            // Hole in upper right quadrant
            else if (x_missing < x_start + size / 2 && y_missing >= y_start + size / 2) {

                TrominoSet(size / 2, x_start, y_start + size / 2, 0, 0);

                chessBoard[x_start + size / 2][y_start + size / 2 - 1] = "LL";
                chessBoard[x_start + size / 2][y_start + size / 2] = "LL";
                chessBoard[x_start + size / 2 - 1][y_start + size / 2 - 1] = "LL";

                TrominoSet(size / 2, x_start, y_start, 0, 0);
                TrominoSet(size / 2, x_start + size / 2, y_start, 0, 0);
                TrominoSet(size / 2, x_start + size / 2, y_start + size / 2, 0, 0);

            }
            // botton left
            else if (x_missing >= x_start + size / 2 && y_missing < y_start + size / 2) {

                TrominoSet(size / 2, x_start + size / 2, y_start, 0, 0);

                chessBoard[x_start + size / 2 - 1][y_start + size / 2] = "UR";
                chessBoard[x_start + size / 2][y_start + size / 2] = "UR";
                chessBoard[x_start + size / 2 - 1][y_start + size / 2 - 1] = "UR";

                TrominoSet(size / 2, x_start, y_start, 0, 0);
                TrominoSet(size / 2, x_start, y_start + size / 2, 0, 0);
                TrominoSet(size / 2, x_start + size / 2, y_start + size / 2, 0, 0);
            }
            // botton right
            else {
                TrominoSet(size / 2, x_start + size / 2, y_start + size / 2, 0, 0);

                chessBoard[x_start + size / 2 - 1][y_start + size / 2] = "UL";
                chessBoard[x_start + size / 2][y_start + size / 2 - 1] = "UL";
                chessBoard[x_start + size / 2 - 1][y_start + size / 2 - 1] = "UL";

                TrominoSet(size / 2, x_start + size / 2, y_start, 0, 0);
                TrominoSet(size / 2, x_start, y_start + size / 2, 0, 0);
                TrominoSet(size / 2, x_start, y_start, 0, 0);
            }
        }
    }

    public void print() {
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                System.out.print(chessBoard[i][j] + " ");
            }
            System.out.println();
        }
    }
}
import java.io.*;
import java.util.*;

public class Main {

    public static void printboard(char[][] board) {
        System.out.println();
        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {
                System.out.print(" " + (board[i][j] == '_' ? ' ' : board[i][j]) + " ");
                if (j < 2)
                    System.out.print("|");
            }
            System.out.println();

            if (i < 2) {
                System.out.println("---+---+---");
            }
        }
        System.out.println();
    }

    public static boolean haswon(char[][] board, char ch) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == ch && board[i][1] == ch && board[i][2] == ch)
                return true;
            if (board[0][i] == ch && board[1][i] == ch && board[2][i] == ch)
                return true;
        }

        if (board[0][0] == ch && board[1][1] == ch && board[2][2] == ch)
            return true;
        if (board[0][2] == ch && board[1][1] == ch && board[2][0] == ch)
            return true;
        return false;
    }

    public static boolean isFull(char[][] board) {
        for (char[] r : board) {
            for (char ch : r) {
                if (ch == ' ')
                    return false;
            }
        }
        return true;
    }

    public static int vinayakAi(char[][] board, boolean turn) {

        if (haswon(board, 'X'))
            return 1;
        if (haswon(board, 'O'))
            return -1;
        if (isFull(board))
            return 0;

        int max_score = turn ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = turn ? 'X' : 'O';
                    int score = vinayakAi(board, !turn);
                    board[i][j] = ' ';
                    if (turn)
                        max_score = Math.max(score, max_score);
                    else
                        max_score = Math.min(score, max_score);
                }
            }
        }
        return max_score;
    }

    public static void whatTomv(char[][] board) {
        int max_score = Integer.MIN_VALUE;
        List<int[]> equal_scores = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'X';
                    int score = vinayakAi(board, false);
                    board[i][j] = ' ';

                    if (score > max_score) {
                        equal_scores.clear();
                        max_score = score;
                        equal_scores.add(new int[] { i, j });
                    } else if (score == max_score)
                        equal_scores.add(new int[] { i, j });
                }
            }
        }

        for (int[] mv : equal_scores) {
            board[mv[0]][mv[1]] = 'X';
            if (haswon(board, 'X')) {
                System.out.println("Ai-Vinayak (X) made a move:");
                printboard(board);
                return;
            }
            board[mv[0]][mv[1]] = ' ';
        }

        int[] mv = equal_scores.get(0);
        board[mv[0]][mv[1]] = 'X';
        System.out.println("Ai-Vinayak (X) made a move:");
        printboard(board);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter cout = new PrintWriter(System.out, true);

        char[][] board = {
                { ' ', ' ', ' ' },
                { ' ', ' ', ' ' },
                { ' ', ' ', ' ' }
        };

        cout.println();
        cout.println("Hehehehehehe Tic Tac Toe!");
        cout.println("you are O, AI-Vinayak is X");
        printboard(board);

        while (true) {
            int r = -1, c = -1;
            while (true) {
                cout.print("enter your move (row and column: 1-3 1-3):- ");
                cout.flush();
                String[] input = cin.readLine().trim().split("\\s+");
                if (input.length == 2) {
                    try {
                        r = Integer.parseInt(input[0]) - 1;
                        c = Integer.parseInt(input[1]) - 1;
                        if (r >= 0 && r < 3 && c >= 0 && c < 3 && board[r][c] == ' ') {
                            board[r][c] = 'O';
                            break;
                        }
                    } catch (NumberFormatException ignored) {
                    }

                }

                cout.println("\nu can't even make proper move what you will win try again!!..\n");

            }

            printboard(board);

            if (haswon(board, 'O')) {
                cout.println("uuuu won!");
                break;
            }

            if (isFull(board)) {
                cout.println("ooo noise draw!");
                break;
            }

            whatTomv(board);

            if (haswon(board, 'X')) {
                cout.println("hehehehe Ai-Vinayak wonnnnn!...");
                break;
            }

            if (isFull(board)) {
                cout.println("ooo noise draw!");
                break;
            }
        }

        cin.close();
    }
}

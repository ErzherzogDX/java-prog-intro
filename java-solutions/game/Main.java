package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int m, n, k;

        int toVictory;
        Scanner init = new Scanner(System.in);
        System.out.println("Please, set the dimensions of the board and the required k to win");
        while (true) {
            try {
                m = init.nextInt();
                n = init.nextInt();
                k = init.nextInt();

                if (k > n && k > m) {
                    System.out.println("Incorrect input. Please, set the correct value of k (k <=n or k<=m)");
                } else if (m <= 0 || n <= 0 || k <= 0) {
                    System.out.println("Incorrect input. Please, set the correct value of the dimensions - it must be positive numbers");
                } else break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input. Please enter numbers and ONLY numbers!");
                init.nextLine();
            }
        }

        System.out.println("Please enter the desired number of wins");
        while (true) {
            try {
                toVictory = init.nextInt();
                if (toVictory <= 0) {
                    System.out.println("Incorrect input. Please, set the correct value of the number of victories - it must be a positive number");
                } else break;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input. Please enter the number and ONLY the number!");
                init.nextLine();
            }
        }


        Player player1 = new HumanPlayer(new Scanner(System.in), m, n); //Player A
        Player player2 = new RandomPlayer(m, n); //Player B

        String winner = new Match(m, n, k).playMatch(player1, player2, toVictory);
        System.out.println("CONGRATULATIONS to player " + winner + " on a glorious VICTORY!");
    }
}

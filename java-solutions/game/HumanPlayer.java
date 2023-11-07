package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;
    private final int m;
    private final int n;

    public HumanPlayer(Scanner in, int m, int n) {
        this.in = in;
        this.m = m;
        this.n = n;
    }

    @Override
    public Move makeMove(Position position) {
        System.out.println();
        System.out.println("Current position");
        System.out.println(position);
        System.out.println("Enter you move for " + position.getTurn());

        int row;
        int col;
        while (true) {
            try {
                row = in.nextInt();
                col = in.nextInt();

                if (row < 1 || col < 1) {
                    System.out.println("Incorrect enter. Please enter positive move numbers!");
                    continue;
                }
                if (row > n || col > m) {
                    System.out.println("Incorrect enter. Please enter numbers less than or equal to the size of the board!");
                    continue;
                }

                boolean pos = position.isValid(new Move(row - 1, col - 1, position.getTurn()));
                if (!pos) {
                    System.out.println("Incorrect enter. Please go to an unoccupied cell.");
                    continue;
                }
                break;

            } catch (InputMismatchException e) {
                System.out.println("Incorrect enter. Please enter integer move numbers!");
                in.nextLine();
            }
        }
        return new Move(row - 1, col - 1, position.getTurn());
    }
}

package game;

public class Match {

    private final int m;
    private final int n;
    private final int k;

    public Match(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
    }

    public String playMatch(Player player1, Player player2, int toVictory){
        int total_match = 1;
        int w1 = 0;
        int w2 = 0;

        while (Math.max(w1, w2) < toVictory) {
            System.out.println("Player A has " + (toVictory - w1) + " games left to win the series");
            System.out.println("Player B has " + (toVictory - w2) + " games left to win the series");

            final int result = new TwoPlayerGame(
                    new TicTacToeBoard(m, n, k),
                    player1,
                    player2
            ).play(true);

            System.out.println();

            if (result == 1) {
                System.out.println("Crosses-player won!");
                if (total_match % 2 != 0) {
                    w1++;
                } else w2++;
            } else if (result == 2) {
                System.out.println("Noughts-player won!");
                if (total_match % 2 != 0) {
                    w2++;
                } else w1++;
            } else if (result == 0) {
                System.out.println("Draw!");
            } else {
                throw new AssertionError("Unknown result " + result);
            }

            total_match++;

            Player player_swap = player1;
            player1 = player2;
            player2 = player_swap;
        }

        if(w1 > w2)
            return "A";
        else return "B";
    }
}

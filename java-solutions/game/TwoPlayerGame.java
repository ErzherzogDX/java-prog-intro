package game;

public class TwoPlayerGame {
    private final Board board;
    private final Player player1;
    private final Player player2;

    public TwoPlayerGame(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(boolean log) {
        while (true) {
            final int result1 = makeMove(player1, 1, log);
            if (result1 != -1) {
                return result1;
            }
            final int result2 = makeMove(player2, 2, log);
            if (result2 != -1) {
                return result2;
            }
        }
    }

    private int makeMove(Player player, int no, boolean log) {
        // :NOTE Нет защиты от null и exception

        GameResult result;
        Move move = null;
        try {
            move = player.makeMove(board.getPosition());
            result = board.makeMove(move);
        } catch (RuntimeException e) {
            result = GameResult.LOOSE;
        }

        if (log) {
            System.out.println();
            System.out.println("Player: " + no);
            System.out.println(move);
            System.out.println(board);
        }
        return switch (result) {
            case WIN -> no;
            case LOOSE -> 3 - no;
            case DRAW -> 0;
            case UNKNOWN -> -1;
            default -> throw new AssertionError("Unknown makeMove result " + result);
        };
    }
}

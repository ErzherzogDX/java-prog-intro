package game;

import java.util.Arrays;
import java.util.Map;

public class TicTacToeBoard implements Board, Position {
    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0"
    );

    private final Cell[][] field;
    private Cell turn;

    private final int m;
    private final int n;
    private final int k;

    public TicTacToeBoard(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;

        field = new Cell[n][m];
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public GameResult makeMove(Move move) {
        if (!isValid(move)) {
            return GameResult.LOOSE;
        }

        field[move.getRow()][move.getCol()] = move.getValue();
        if (checkWin()) {
            return GameResult.WIN;
        }

        if (checkDraw()) {
            return GameResult.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return GameResult.UNKNOWN;
    }

    private boolean checkDraw() {
        int count = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (field[r][c] == Cell.E) {
                    count++;
                }
            }
        }
        return count == 0;
    }

    private int inversionDiagonales(int num, boolean isDirect) {
        if (isDirect) return num;
        else return m - num - 1;
    }

    public int[] inversionRow(boolean isRow) {
        if (isRow) return new int[]{n, m};
        else return new int[]{m, n};
    }

    private boolean checkDiagonales(boolean isDirect) {
        if (k > n || k > m)
            return false;

        for (int i = n - 1; i > 0; i--) {
            int col = 0;
            int count = 0;
            int max_count = 0;
            for (int j = i; j < n; j++) {
                if (field[j][inversionDiagonales(col, isDirect)] == turn) {
                    count++;
                } else {
                    max_count = Math.max(max_count, count);
                    count = 0;
                }
                col++;
            }

            max_count = Math.max(max_count, count);
            if (max_count >= k) {
                return true;
            }
        }

        for (int i = 0; i < m - 1; i++) {
            int count = 0;
            int max_count = 0;
            for (int j = 0; j < n; j++) {
                if (i + j < m) {
                    if (field[j][inversionDiagonales(i + j, isDirect)] == turn) {
                        count++;
                    } else {
                        max_count = Math.max(max_count, count);
                        count = 0;
                    }
                } else break;

                max_count = Math.max(max_count, count);
                if (max_count >= k) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkLines(boolean isRow) {
        int[] vals = inversionRow(isRow);
        for (int r = 0; r < vals[0]; r++) {
            if (k > vals[1]) return false;

            int count = 0;
            int max_count = 0;

            for (int c = 0; c < vals[1]; c++) {
                Cell check;
                if (isRow) {
                    check = field[r][c];
                } else check = field[c][r];

                if (check == turn) {
                    count++;
                } else {
                    max_count = Math.max(max_count, count);
                    count = 0;
                }
            }

            max_count = Math.max(max_count, count);
            if (max_count >= k) {
                return true;
            }
        }

        return false;
    }

    private boolean checkWin() {
        return (checkLines(true) | checkLines(false) | checkDiagonales(true) | checkDiagonales(false));
    }

    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < n
                && 0 <= move.getCol() && move.getCol() < m
                && field[move.getRow()][move.getCol()] == Cell.E
                && turn == move.getValue();
    }

    @Override
    public Cell getCell(int row, int column) {
        return field[row][column];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" ");
        for (int i = 0; i < m; i++) {
            sb.append(i + 1);
        }
        sb.append(System.lineSeparator());
        for (int r = 0; r < n; r++) {
            sb.append(r + 1);
            for (Cell cell : field[r]) {
                sb.append(CELL_TO_STRING.get(cell));
            }
            sb.append(System.lineSeparator());
        }
        sb.setLength(sb.length() - System.lineSeparator().length());
        return sb.toString();
    }
}

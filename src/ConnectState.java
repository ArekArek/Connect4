import sac.game.GameState;
import sac.game.GameStateImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConnectState extends GameStateImpl {
    public static int DIMEN_X;
    public static int DIMEN_Y;
    private byte[][] board = null;      //maximizing is "O"

    public ConnectState(int dimenX, int dimenY) {
        this.DIMEN_X = dimenX;
        this.DIMEN_Y = dimenY;

        board = new byte[DIMEN_X][DIMEN_Y];
        for (int i = 0; i < DIMEN_X; i++) {
            for (int j = 0; j < DIMEN_Y; j++)
                board[i][j] = 0;
        }

        setMaximizingTurnNow(true);
    }

    public ConnectState(ConnectState parent) {
        board = new byte[DIMEN_X][DIMEN_Y];
        for (int i = 0; i < DIMEN_X; i++) {
            for (int j = 0; j < DIMEN_Y; j++)
                board[i][j] = parent.getBoard()[i][j];
        }
    }

    public int makeMove(byte pos) {
        if (pos > DIMEN_X) {
            System.err.println("move is out of range");
            return -1;
        }
        pos--;
        byte[] tmpArr = board[pos];
        int height = 0;
        try {
            while (tmpArr[height] != 0)        //TODO zrobic ze jak sie dojdzie do sufitu
                height++;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("stack is overload");
            return -1;
        }
        if (isMaximizingTurnNow()) tmpArr[height] = 2;
        else tmpArr[height] = 1;

        setMaximizingTurnNow(!maximizingTurnNow);
        return 1;
    }

    @Override
    public List<GameState> generateChildren() {
        List<ConnectState> children = new ArrayList<>();
        for (byte i = 1; i <= DIMEN_X; i++) {
            ConnectState child = new ConnectState(this);
            if (makeMove(i) != -1){
                child.setMoveName(Byte.toString(i));
                children.add(child);}
        }
        return null;
    }

    public byte[][] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Connect4:\n");
        for (int i = 0; i < DIMEN_Y; i++) {
            sb.append(Character.toString((char) (65 + i)));
            for (int j = 0; j < DIMEN_X; j++) {
                sb.append("|");
                if (board[j][DIMEN_Y - i - 1] == 0) sb.append(" ");
                else if (board[j][DIMEN_Y - i - 1] == 1) sb.append("X");
                else if (board[j][DIMEN_Y - i - 1] == 2) sb.append("O");
            }
            sb.append("|\n");
        }
        sb.append(" -");
        for (int i = 0; i < DIMEN_X; i++) {
            sb.append("--");
        }
        sb.append("\n ");
        for (int i = 1; i <= DIMEN_X; i++) {
            sb.append(" ");
            sb.append(i);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ConnectState that = (ConnectState) o;

        return Arrays.deepEquals(board, that.board);

    }

    @Override
    public int hashCode() {
        byte[] copy = new byte[DIMEN_Y * DIMEN_X];
        int k = 0;
        for (int i = 0; i < DIMEN_X; i++)
            for (int j = 0; j < DIMEN_Y; j++) {
                copy[k] = board[i][j];
                k++;
            }

        return Arrays.hashCode(copy);
    }
}

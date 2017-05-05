import sac.game.GameState;
import sac.game.GameStateImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConnectState extends GameStateImpl {
    public static int DIMEN_X;
    public static int DIMEN_Y;
    private byte[][] board = null;      //maximizing is "O"

    static {
        setHFunction(new MyHeuristic());
    }

    public ConnectState(int dimenX, int dimenY) {
        this.DIMEN_X = dimenX;
        this.DIMEN_Y = dimenY;

        board = new byte[DIMEN_X][DIMEN_Y];
        for (int i = 0; i < DIMEN_X; i++) {
            for (int j = 0; j < DIMEN_Y; j++)
                board[i][j] = 0;
        }

        setMaximizingTurnNow(false);
    }

    public ConnectState(ConnectState parent) {
        board = new byte[DIMEN_X][DIMEN_Y];
        for (int i = 0; i < DIMEN_X; i++) {
            for (int j = 0; j < DIMEN_Y; j++)
                board[i][j] = parent.getBoard()[i][j];
        }
        setMaximizingTurnNow(parent.isMaximizingTurnNow());
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
        //refresh();
        return 1;
    }

    public List<Byte> getPossibleMoves(){
        List<Byte> result = new ArrayList<>();
        for(byte i=1;i<=DIMEN_X;i++){
            if(board[i][DIMEN_Y-1]==0)
                result.add(i);
        }
        return result;
    }

    @Override
    public List<GameState> generateChildren() {
        List<GameState> children = new ArrayList<>();
        for (byte i = 1; i <= DIMEN_X; i++) {
            ConnectState child = new ConnectState(this);
            if (child.makeMove(i) != -1){
                child.setMoveName(Byte.toString(i));
                children.add(child);}
        }
        return children;
    }

    public byte[][] getBoard() {
        return board;
    }

    public boolean isTerminal(){
        //gdy dochodzi do konca planszy
        for(int i=0;i<DIMEN_X;i++){
            if(board[i][DIMEN_Y-1]!=0)
                return true;
        }

        //gdy jest 4 w linii
        for(int i=0;i<DIMEN_X;i++){
            for(int j=0;j<DIMEN_Y;j++){
                if(board[i][j]!=0){
                    boolean result = (checkRight(i, j) ||
                    checkUp(i, j) ||
                    checkUpRight(i, j) ||
                    checkUpLeft(i, j));
                    if(result)
                        return true;
                }
            }
        }
        return false;
    }

    private boolean checkUpLeft(int i, int j) {
        if(j>DIMEN_Y-4 || i<3)
            return false;
        else{
            byte tmp=board[i][j];
            for(int k=1;k<=3;k++){
                if(board[i-k][j+k]!=tmp)
                    return false;
            }
            return true;
        }
    }

    private boolean checkUpRight(int i, int j) {
        if(j>DIMEN_Y-4 || i>DIMEN_X-4)
            return false;
        else{
            byte tmp=board[i][j];
            for(int k=1;k<=3;k++){
                if(board[i+k][j+k]!=tmp)
                    return false;
            }
            return true;
        }
    }

    private boolean checkUp(int i, int j) {
        if(j>DIMEN_Y-4)
            return false;
        else{
            byte tmp=board[i][j];
            for(int k=1;k<=3;k++){
                if(board[i][j+k]!=tmp)
                    return false;
            }
            return true;
        }
    }

    private boolean checkRight(int i, int j) {
        if(i>DIMEN_X-4)
            return false;
        else{
            byte tmp=board[i][j];
            for(int k=1;k<=3;k++){
                if(board[i+k][j]!=tmp)
                    return false;
            }
            return true;
        }
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

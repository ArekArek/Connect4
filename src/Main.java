import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConnectState connectState = new ConnectState(7, 6);
        System.out.println(connectState.toString());
        Scanner scanner = new Scanner(System.in);

        while(true){
            byte move=scanner.nextByte();
            connectState.makeMove(move);
            System.out.println(connectState.toString());
        }
    }
}

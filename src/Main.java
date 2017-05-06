import sac.game.AlphaBetaPruning;
import sac.game.GameSearchAlgorithm;
import sac.game.GameSearchConfigurator;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        ConnectState connectState = new ConnectState(7, 6);
        System.out.println(connectState.toString());
        Scanner scanner = new Scanner(System.in);

        GameSearchConfigurator gameSearchConfigurator = new GameSearchConfigurator();
        Map<String, GameSearchAlgorithm> algorithmsMap = new TreeMap<>();
        algorithmsMap.put("alfa-beta pruning", new AlphaBetaPruning());

        GameSearchAlgorithm algorithm = algorithmsMap.get("alfa-beta pruning");
        if (algorithm != null) {
            algorithm.setConfigurator(gameSearchConfigurator);
        }

        while (true) {
            if (!connectState.isMaximizingTurnNow()) {
                System.out.println("minimazing turn now (player)");
                byte newMove = scanner.nextByte();
                connectState.makeMove(newMove);
                System.out.println(connectState.toString());
            } else {
                System.out.println("maximizing turn now (comp)");

                algorithm.setInitial(connectState);
                algorithm.execute();
                String bestMove = algorithm.getFirstBestMove();
                connectState.makeMove(Byte.parseByte(bestMove));
                System.out.println(connectState.toString());
            }
            if (connectState.isTerminal()) {
                if (connectState.isMaximizingTurnNow()) System.out.println("END OF GAME: X WIN");
                else System.out.println("END OF GAME: O WIN");
                return;
            }
        }

    }
}

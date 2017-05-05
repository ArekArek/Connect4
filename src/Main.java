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
        Map<String, GameSearchAlgorithm> algorithmsMap = new TreeMap<String, GameSearchAlgorithm>();
        algorithmsMap.put("alfa-beta pruning", new AlphaBetaPruning());

        GameSearchAlgorithm algorithm = algorithmsMap.get("alfa-beta pruning");
        if (algorithm != null) {
            algorithm.setConfigurator(gameSearchConfigurator);
        }
        boolean startFlag = true;
        /*
        int moves = 0;
        int player = 0;
        String humanMove="";
        byte move;*/
        while (startFlag) {
            if (!connectState.isMaximizingTurnNow()) {
                System.out.println("minimazing turn now (player)");
                byte newMove = scanner.nextByte();
                connectState.makeMove(newMove);
                System.out.println(connectState.toString());
            }
            else {
                System.out.println("maximizing turn now (comp)");

                algorithm.setInitial(connectState);
                algorithm.execute();
                String bestMove=algorithm.getFirstBestMove();
                connectState.makeMove(Byte.parseByte(bestMove));
                System.out.println(connectState.toString());
            }
            if(connectState.isTerminal()){
                System.out.println("END OF GAME: O WIN");
                return;
            }


            /*
            moves++;
            List<String> bestMoves;
            byte bestMoveIndex;
            String bestMove;
            player = 1;

            //if (algorithm != null) {
                algorithm.setInitial(connectState);
                AlgorithmExecuteRunnable executeRunnable = new AlgorithmExecuteRunnable(algorithm);
                (new Thread(executeRunnable)).start();
                while (executeRunnable.isRunning()) {
                    if (!startFlag) {
                        algorithm.forceStop();
                        break;
                    }
                }
                bestMoves = algorithm.getBestMoves();
                if ((bestMoves == null) || (bestMoves.isEmpty())) {
                    List<Byte> bestMovesList = connectState.getPossibleMoves();
                    bestMoves = new LinkedList<String>();
                    for (Byte tmpMove : bestMovesList) {
                        bestMoves.add(tmpMove.toString());
                    }
                }
                bestMoveIndex = (byte) (Math.random() * bestMoves.size());
                bestMove = bestMoves.get(bestMoveIndex).toString();
                humanMove = bestMove;
                move = Byte.parseByte(humanMove);
            //}
            connectState.makeMove(move);
            System.out.println(connectState.toString());

            player=2;

            if ((connectState.isWinTerminal()) && (!connectState.isMaximizingTurnNow())) {
                String whoWinsString = null;
                if (algorithm == null) {
                    whoWinsString = "HUMAN WINS";
                } else
                    whoWinsString = "WHITE - ALGORITHM "
                            + algorithm.getClass().getName() + " - WINS.";
                System.out.println(whoWinsString);
                break;
            }
            if(!startFlag)
                break;

            if (!connectState.isMaximizingTurnNow()) {
                byte newMove = scanner.nextByte();
                connectState.makeMove(newMove);
            } else {

            }

            System.out.println(connectState.toString());
            */
        }

    }

    private static class AlgorithmExecuteRunnable implements Runnable {
        private boolean running = false;
        private GameSearchAlgorithm algorithm = null;

        public AlgorithmExecuteRunnable(GameSearchAlgorithm algorithm) {
            this.algorithm = algorithm;
            running = true;
        }

        public boolean isRunning() {
            return running;
        }

        @Override
        public void run() {
            algorithm.execute();
            running = false;
        }
    }
}

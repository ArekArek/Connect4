import sac.State;
import sac.StateFunction;

public class MyHeuristic extends StateFunction {
    @Override
    public double calculate(State state) {
        ConnectState connectState = (ConnectState) state;
        double result = 0;
        if (connectState.isTerminal()) {
            if (connectState.isMaximizingTurnNow()) return Double.NEGATIVE_INFINITY;
            else return Double.POSITIVE_INFINITY;
        } else {
            for (int i = 0; i < connectState.DIMEN_X; i++) {
                for (int j = 0; j < connectState.DIMEN_Y; j++) {
                    result += connectState.singleGrade(i, j);
                }
            }
        }
        if(connectState.isMaximizingTurnNow()) {
            return -result;
        }
        return result;
    }
}

import sac.State;
import sac.StateFunction;

public class MyHeuristic extends StateFunction{
    @Override
    public double calculate(State state) {
        ConnectState connectState = (ConnectState)state;
        if(connectState.isTerminal()) {
            if (connectState.isMaximizingTurnNow()) return Double.POSITIVE_INFINITY;
            else return Double.NEGATIVE_INFINITY;
        }
        else
            return 0;
    }
}

package Project.Game_Engine;

import javafx.application.Platform;
import javafx.scene.transform.Rotate;

public interface Action extends Constants, Percepts {

    double GO_FORWARD_STEP_COST = 1;
    double TURN_LEFT_STEP_COST = 1;
    double TURN_RIGHT_STEP_COST = 1;

    String GO_FORWARD = "goForward";
    String TURN_LEFT = "turnLeft";
    String TURN_RIGHT = "turnRight";

    default double getStepCost(Action action) {

        //Determine how to get specific action used


        return GO_FORWARD_STEP_COST;
    }



    static void turnRight(TruckAgent agent) {

        agent.setDirection(Math.abs((agent.getDirection() + 1) % 4));
        agent.setRotationAxis(Rotate.Z_AXIS);
        agent.setRotate(agent.getRotate() + 90);
    }

    static void turnLeft(TruckAgent agent) {

        agent.setDirection(Math.abs((agent.getDirection() - 1 + 4) % 4));
        agent.setRotationAxis(Rotate.Z_AXIS);
        agent.setRotate(agent.getRotate() - 90);
    }
}

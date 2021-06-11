package Project.Game_Engine;

import javafx.application.Platform;
import javafx.scene.transform.Rotate;

public interface Action extends Constants {

    double GO_FORWARD_STEP_COST = 1;
    double TURN_LEFT_STEP_COST = 1;
    double TURN_RIGHT_STEP_COST = 1;

    String GO_FORWARD = "goForward";
    String TURN_LEFT = "turnLeft";
    String TURN_RIGHT = "turnRight";

    public static double getStepCost(String action) {

        //Determine how to get specific action used
        if(action.equals("goForward"))
            return GO_FORWARD_STEP_COST;
        else if(action.equals("turnLeft"))
            return TURN_LEFT_STEP_COST;
        else if(action.equals("turnRight"))
            return TURN_RIGHT_STEP_COST;

        return 0;
    }

    public static boolean goForward(Environment environment, TruckAgent agent) {

        //Check current direction
        int shiftXValue = 0;
        int shiftYValue = 0;

        //Get shift value based on truck's direction

        switch (agent.getDirection()) {

            case FACING_SOUTH:
                shiftXValue = MOVING_SOUTH;
                break;
            case FACING_WEST:
                shiftYValue = MOVING_WEST;
                break;
            case FACING_NORTH:
                shiftXValue = MOVING_NORTH;
                break;
            default:
                shiftYValue = MOVING_EAST;
        }

        //Old location of truck
        int oldX = agent.getLocation().getX();
        int oldY = agent.getLocation().getY();

        //New location of truck
        int i = agent.getLocation().getX() + shiftXValue;
        int j = agent.getLocation().getY() + shiftYValue;

        //Percept[][] percepts = environment.getPercepts();

        //Check if the truck would be moving outside the grid
        if (i < 0 || i >= environment.getAgents().length || j < 0 || j >= environment.getAgents().length) {
            return false;
        }

        //Go forward based on shift value
        if (environment.getCells()[i][j].getPassable()) {


            //Get agent being used
            environment.getAgents()[oldX][oldY] = agent;
            //Remove truck from old view
            environment.getChildren().remove(agent);
            //Move truck to new location
            Platform.runLater(() -> {
                environment.getCells()[i][j].getChildren().add(agent);
            });

            //Remove truck from old cell and move to new cell
            environment.getCells()[oldX][oldY].setAgent(null);
            environment.getCells()[i][j].setAgent(agent);

            environment.getCells()[oldX][oldY].setVisited(true);
            //Set new location of truck


            agent.getLocation().setX(i);
            agent.getLocation().setY(j);

        }
            return true;

    }

    public static void turnRight (TruckAgent agent) {

            agent.setDirection(Math.abs((agent.getDirection() + 1) % 4));
            agent.setRotationAxis(Rotate.Z_AXIS);
            agent.setRotate(agent.getRotate() + 90);
    }

    public static void turnLeft (TruckAgent agent) {

            agent.setDirection(Math.abs((agent.getDirection() - 1 + 4) % 4));
            agent.setRotationAxis(Rotate.Z_AXIS);
            agent.setRotate(agent.getRotate() - 90);
    }
}

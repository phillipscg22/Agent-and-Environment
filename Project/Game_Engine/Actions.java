package Project.Game_Engine;

import javafx.application.Platform;
import javafx.scene.transform.Rotate;

public interface Actions extends Constants, Percepts {

    default String goForward(Environment environment, TruckAgent agent) {

        //Check current direction
        int shiftXValue = 0;
        int shiftYValue = 0;

        //Get shift value based on truck's direction

        switch(agent.getDirection()) {

            case FACING_SOUTH : shiftXValue = MOVING_SOUTH; break;
            case FACING_WEST : shiftYValue = MOVING_WEST; break;
            case FACING_NORTH : shiftXValue = MOVING_NORTH; break;
            default: shiftYValue = MOVING_EAST;
        }

        //Old location of truck
        int oldX = agent.getLocation().getX();
        int oldY = agent.getLocation().getY();

        //Change location of truck
        int i = agent.getLocation().getX() + shiftXValue;
        int j = agent.getLocation().getY() + shiftYValue;

        //Check if the truck would be moving outside the grid
        if(i < 0 || i >= environment.getAgents().length || j < 0 || j >= environment.getAgents().length) {
            return BORDER;
        }

        //Go forward based on shift value
        if(!environment.getAgents()[i][j].getBuilt() && environment.getAgents()[i][j].getPassable()
                && !environment.getCells()[i][j].getVisited()) {

            //Add truck to new location within cells
            environment.getAgents()[i][j] = agent;
            environment.getAgents()[oldX][oldY].setPassable(true);
            environment.getAgents()[oldX][oldY].setBuilt(false);
            //Remove truck from old view
            environment.getChildren().remove(agent);
            //Move truck to new location
            Platform.runLater(() -> {
                environment.getCells()[i][j].getChildren().add(agent);
            });

            environment.getCells()[oldX][oldY].setVisited(true);
            //Set new location of truck
            agent.getLocation().setX(i);
            agent.getLocation().setY(j);
            return MOVED;
        }

        return NON_PASSABLE;
    }

    default void turnRight(TruckAgent agent) {

        agent.setDirection(Math.abs((agent.getDirection() + 1) % 4));
        agent.setRotationAxis(Rotate.Z_AXIS);
        agent.setRotate(agent.getRotate() + 90);
    }

    default void turnLeft(TruckAgent agent) {

        agent.setDirection(Math.abs((agent.getDirection() - 1 + 4) % 4));
        agent.setRotationAxis(Rotate.Z_AXIS);
        agent.setRotate(agent.getRotate() - 90);
    }
}

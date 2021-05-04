package Project.Infrastructure;

import Project.Game_Engine.*;
import Project.Infrastructure.Node;
import javafx.application.Platform;

import java.util.LinkedList;

public class SuccessorFunction implements Action {

    /**
     *
     * Add in here when action is called to run through list of parent and then do new action
     *              or do list of actions in new node if possible
     *
     */

    public static LinkedList<Node> expand(Node node, Environment environment) {

        LinkedList<Node> list = new LinkedList<>();

        //If there is a node that is returned
        if(successorFunction(node, "goForward", environment) != null) {
            list.add(successorFunction(node, "goForward", environment));
           // System.out.println("Direction: " + node.getState().getAgent().getDirection());
        }

        //Add turn right and left to expanded list
        list.add(successorFunction(node, "turnRight", environment));
       // System.out.println("Direction: " + node.getState().getAgent().getDirection());
        list.add(successorFunction(node, "turnLeft", environment));
      //  System.out.println("Direction: " + node.getState().getAgent().getDirection());

     //   System.out.println("size: " + list.size());

        return list;
    }

    public static Node successorFunction(Node parent, String action, Environment environment) {

        //Use this state to test moving
        Cell state = parent.getState();

        //Check current direction
        int shiftXValue = 0;
        int shiftYValue = 0;

        switch(state.getAgent().getDirection()) {

            case FACING_SOUTH : shiftXValue = MOVING_SOUTH; break;
            case FACING_WEST : shiftYValue = MOVING_WEST; break;
            case FACING_NORTH : shiftXValue = MOVING_NORTH; break;
            default: shiftYValue = MOVING_EAST;
        }

        if(action.equals("goForward")) {

            /**  If returns "MOVING" then adjust state then create new node using state  */
            if(goForward(environment, state.getAgent())) {

                //New location of truck
                int i = state.getAgent().getLocation().getX() + shiftXValue;
                int j = state.getAgent().getLocation().getY() + shiftYValue;

                if (i < 0 || i >= environment.getAgents().length || j < 0 || j >= environment.getAgents().length) {
                    return null;
                }

                //Adjust location of node
               // state.getAgent().setLocation(new Location(i, j));
                state.setPercept(environment.getCells()[i][j].getPercept());
                state.setAgent(state.getAgent());
               // System.out.println(state.getAgent().getLocation().getX() + " : " + state.getAgent().getLocation().getY());
            }
            else {

                return null;
            }
        }
        else if(action.equals("turnRight")) {

            Action.turnRight(state.getAgent());
           // System.out.println("turn right");
        }
        else if(action.equals("turnLeft")) {

            Action.turnLeft(state.getAgent());
          //  System.out.println("turn left");
        }

        state.setAgent(state.getAgent());

        /** changed */
        return new Node(state, parent, action);
    }

    public static boolean goForward(Environment environment, TruckAgent agent) {

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

        //New location of truck
        int i = agent.getLocation().getX() + shiftXValue;
        int j = agent.getLocation().getY() + shiftYValue;

        Percept [][] percepts = environment.getPercepts();

        //Check if the truck would be moving outside the grid
        if(i < 0 || i >= environment.getAgents().length || j < 0 || j >= environment.getAgents().length) {
            return false;
        }

        //If the truck is actively moving
       // if(moving) {
            //Go forward based on shift value
            if (environment.getCells()[i][j].getPassable() && !environment.getCells()[i][j].getVisited()) {

                //Commented this out so the agent does not move when using successor funciton
                //Am playing around with this and altering for next update of code.
/**
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
                environment.getCells()[oldX][oldY].setAgent(agent);

                environment.getCells()[oldX][oldY].setVisited(true);
                //Set new location of truck


                agent.getLocation().setX(i);
                agent.getLocation().setY(j);

 */

                return true;
            }
       // }
        //Still return moved if it is possible to move
//        else if(environment.getCells()[i][j].getPassable() && !environment.getCells()[i][j].getVisited()){
//
//            return true;
//        }

        return false;
    }
}

package Project.Infrastructure;

import Project.Game_Engine.*;
import Project.Infrastructure.Node;
import javafx.application.Platform;
import javafx.scene.transform.Rotate;

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
//        if(successorFunction(node, "goForward", environment) != null) {
        try {
            list.add(successorFunction(node, "goForward", environment));
        }
        catch (NullPointerException ignored) {
        }
//            System.out.println("Direction: " + node.getState().getDirection());
//        }

        //Add turn right and left to expanded list
//        list.add(successorFunction(node, "turnRight", environment));
//        System.out.println("Direction: " + node.getState().getDirection());
//        list.add(successorFunction(node, "turnLeft", environment));
//        System.out.println("Direction: " + node.getState().getDirection());

        return list;
    }

    public static Node successorFunction(Node parent, String action, Environment environment) {

        //Use this state to test moving
        Cell state = parent.getState();

        //Check current direction
        int shiftXValue = 0;
        int shiftYValue = 0;

        switch(state.getDirection()) {

            case FACING_SOUTH : shiftXValue = MOVING_SOUTH; break;
            case FACING_WEST : shiftYValue = MOVING_WEST; break;
            case FACING_NORTH : shiftXValue = MOVING_NORTH; break;
            default: shiftYValue = MOVING_EAST;
        }

        /**
         *
         * Depending on direction of state determines direction that goforward moves
         *
         */


        if(action.equals("goForward")) {

            /**  If returns "MOVING" then adjust state then create new node using state  */
            if(goForward(environment, state)) {

                //New location of truck
                int i = state.getLocation().getX() + shiftXValue;
                int j = state.getLocation().getY() + shiftYValue;

                if (i < 0 || i >= environment.getAgents().length || j < 0 || j >= environment.getAgents().length) {
                    return null;
                }

                //Adjust location of node
                state.setPercept(environment.getCells()[i][j].getPercept());
                state.setLocation(new Location(i, j));
                System.out.println(state.getLocation().getX() + " : " + state.getLocation().getY());
            }
            else {

                return null;
            }
        }
        else if(action.equals("turnRight")) {

            turnRight(state);
           // System.out.println("turn right");
        }
        else if(action.equals("turnLeft")) {

            turnLeft(state);
          //  System.out.println("turn left");
        }

        Node node = new Node(state, parent, action);

        return node;
    }

    public static boolean goForward(Environment environment, Cell state) {

        //Check current direction
        int shiftXValue = 0;
        int shiftYValue = 0;

        //Get shift value based on truck's direction
        switch(state.getDirection()) {

            case FACING_SOUTH : shiftXValue = MOVING_SOUTH; break;
            case FACING_WEST : shiftYValue = MOVING_WEST; break;
            case FACING_NORTH : shiftXValue = MOVING_NORTH; break;
            default: shiftYValue = MOVING_EAST;
        }

        //New location of truck
        int i = state.getLocation().getX() + shiftXValue;
        int j = state.getLocation().getY() + shiftYValue;

        //Percept [][] percepts = environment.getPercepts();

        //Check if the truck would be moving outside the grid
        if(i < 0 || i >= environment.getAgents().length || j < 0 || j >= environment.getAgents().length) {
            return false;
        }

        //Go forward based on shift value
        if (environment.getCells()[i][j].getPassable()) {
            return true;
        }

        return false;
    }

    public static void turnRight (Cell state){

        state.setDirection(Math.abs((state.getDirection() + 1) % 4));
    }

    public static void turnLeft (Cell state){

        state.setDirection(Math.abs((state.getDirection() - 1 + 4) % 4));
    }
}

package Project.Infrastructure;

import Project.Game_Engine.*;
import Project.Infrastructure.Node;
import javafx.application.Platform;
import javafx.scene.transform.Rotate;

import java.util.LinkedList;

public class SuccessorFunction implements Action {

    /**
     *
     * @param node
     * @param environment
     * @return
     * @throws java.lang.CloneNotSupportedException
     */

    public static LinkedList<Node> expand(Node node, Environment environment) throws CloneNotSupportedException {

        LinkedList<Node> list = new LinkedList<>();

        //If there is a node that is returned

        Node parentNode = node.copyOfNode();

        try {
            list.add(successorFunction(parentNode, GO_FORWARD, environment));
        }
        catch (NullPointerException ignored) { }

        //Add turn right and left to expanded list
        parentNode = node.copyOfNode();
        list.add(successorFunction(parentNode, TURN_RIGHT, environment));
        parentNode = node.copyOfNode();
        list.add(successorFunction(parentNode, TURN_LEFT, environment));

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

        switch (action) {
            case GO_FORWARD:
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
                }
                else {

                    return null;
                }   break;
            case TURN_RIGHT:
                turnRight(state);
                break;
            case TURN_LEFT:
                turnLeft(state);
                break;
            default:
                break;
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

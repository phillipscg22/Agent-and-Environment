package Project.Game_Engine;

import java.util.LinkedList;

public class Successor implements Action {

    public static LinkedList<Node> expand(Node node, Environment environment) {

        LinkedList<Node> list = new LinkedList<>();

        //If there is a node that is returned
        if(successorFunction(node, "goForward", environment) != null)
            list.add(successorFunction(node, "goForward", environment));

        //Add turn right and left to expanded list
        list.add(successorFunction(node, "turnRight", environment));
        list.add(successorFunction(node, "turnLeft", environment));

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
            if(Action.goForward(environment, state.getAgent(), false).equals(MOVED)) {

                //New location of truck
                int i = state.getLocation().getX() + shiftXValue;
                int j = state.getLocation().getY() + shiftYValue;

                //Adjust location of node
                state.setLocation(new Location(i, j));
            }
            else {

                return null;
            }
        }
        else if(action.equals("turnRight")) {

            Action.turnRight(state.getAgent());
        }
        else if(action.equals("turnLeft")) {

            Action.turnLeft(state.getAgent());
        }

        Node node = new Node(state);

        return node;
    }
}

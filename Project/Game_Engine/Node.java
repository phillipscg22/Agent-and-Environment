package Project.Game_Engine;


public class Node implements Comparable<Node>{


    //Auto done when implemented comparable
    @Override
    public int compareTo(Node o) {
        return 0;
    }

    private Cell state;
    private Node parent;
    private Action action;
    private double pathCost;
    private Environment environment;

    //Root therefore no parent or action
    public Node(Cell state) {

        this.state = state;
        parent = null;
        action = null;
        pathCost = 0;
    }

    //Action will be the action taken to get to current state (parent's action taken)
    public Node(Node parent, Action action, Environment environment) {

        /**  Find way to determine what action it is instead of using toString because that is useless right now  */
        state = Successor.successorFunction(parent, action.toString(), environment).getState();
        this.parent = parent;
        this.action = action;
        pathCost = parent.getPathCost() + action.getPathCost(action);
    }

    public Cell getState() {

        return state;
    }

    public void setState(Cell state) {

        this.state = state;
    }

    public Node getParent() {

        return parent;
    }

    public void setParent(Node parent) {

        this.parent = parent;
    }

    public Action getAction() {

        return action;
    }

    public void setAction(Action action) {

        this.action = action;
    }

    public double getPathCost() {

        return pathCost;
    }

    public void setPathCost(double pathCost) {

        this.pathCost = pathCost;
    }
}

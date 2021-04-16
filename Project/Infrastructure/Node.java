package Project.Infrastructure;


import Project.Game_Engine.Action;
import Project.Game_Engine.Cell;
import Project.Game_Engine.Environment;

public class Node implements Comparable<Node>{

    private Cell state;
    private Node parent;
    private Action action;
    private double pathCost;  //g(n)
    private double hValue;  // h(n) heuristic estimate
    private double fValue;  //f(n) = g(n) + h(n)
  //  private Environment environment;

    //Root therefore no parent or action
    public Node(Cell state) {

        this.state = state;
        parent = null;
        action = null;
        pathCost = 0;
    }

    //Action will be the action taken to get to current state (parent's action taken)
    public Node childNode(Node parent, Action action, Environment environment) {

        /**  Find way to determine what action it is instead of using toString because that is useless right now  */
        Node node =  SuccessorFunction.successorFunction(parent, action.toString(), environment);  //Don't need successor

       // this.parent = parent;
       // this.action = action;
        node.setPathCost(parent.getPathCost() + action.getStepCost(action));

        return node;
    }

    //Auto done when implemented comparable
    // Comparing pathCost
    @Override
    public int compareTo(Node node) {

        if(this.fValue == node.getFValue())
            return 0;
        else if(this.fValue < node.getFValue())
            return -1;
        else
            return 1;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null) return false;  //case of parent.parent being the root/initial state

        if ( this == o ) return true; //check for self-comparison

        if ( !(o instanceof Node) ) return false;

        Node in = (Node)o;

        /**
         * error NullPointerException
         */
        //compare the data members
        if(this.state.equals(in.state) && this.parent.equals(in.parent) && this.action == in.action &&
                this.pathCost == in.pathCost && this.hValue == in.hValue)
            return true;

        return false;
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

    public double getFValue() {

        return fValue;
    }

    public double getHValue() {

        return hValue;
    }

    @Override
    public String toString() {

        return "Agent id : " + this.state.getId() + " Percept = " + this.state.getPercept() +
                " Location x = " + this.state.getLocation().getX() + " Location y = " + this.state.getLocation().getY(); // +
        // "action = " + this.action.toString();
    }
}

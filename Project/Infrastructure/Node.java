package Project.Infrastructure;


import Project.Game_Engine.Action;
import Project.Game_Engine.Cell;
import Project.Game_Engine.Environment;
import Project.Game_Engine.Location;

import java.util.ArrayList;

public class Node implements Cloneable, Comparable<Node>{

    private Cell state;
    private Node parent;
    private String action;
    private ArrayList<String> actionsList;
    //    private ArrayList<Location> locationList;
    private double pathCost;  //g(n)
    private double hValue;  // h(n) heuristic estimate
    private double fValue;  //f(n) = g(n) + h(n)

    //Root therefore no parent or action
    public Node(Cell state) {

        this.state = state;
        parent = null;
        action = "null";
        actionsList = new ArrayList<>();
//        locationList = new ArrayList<>();
        pathCost = 0;

//        locationList.add(state.getLocation());
    }

    /**
     * Added recent
     * @param state
     * @param parent
     * @param action
     */
    public Node(Cell state, Node parent, String action) {

        this.state = state;
        this.action = action;
        this.parent = parent;
        pathCost = parent.getPathCost() + Action.getStepCost(action);

        actionsList = new ArrayList<String>();

        if(parent.getActionsList().size() == 0) {
            actionsList.add(0, action);
        }
        else {

            int size = parent.getActionsList().size();

            for(int i = 0; i < size; i++) {

                actionsList.add(i, parent.getActionsList().get(i));
            }
            actionsList.add(action);
        }
//
//        if(parent.getLocationList().size() == 0) {
//            locationList.add(0, state.getLocation());
//        }
//        else {
//
//            int size = parent.getLocationList().size();
//
//            for(int i = 0; i < size; i++) {
//
//                locationList.add(i, parent.getLocationList().get(i));
//            }
//            locationList.add(state.getLocation());
//        }
    }

    //Action will be the action taken to get to current state (parent's action taken)
    public Node childNode(Node parent, String action, Environment environment) {

        Node node =  SuccessorFunction.successorFunction(parent, action.toString(), environment);  //Don't need successor

        // this.parent = parent;
        // this.action = action;
        node.setPathCost(parent.getPathCost() + Action.getStepCost(action));

        return node;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        //do a shallow copy first
        Node nodeClone = (Node) super.clone();

        //now do a deep copy
        nodeClone.state = (Cell) state.clone();

        if(parent != null)
            nodeClone.parent = (Node) parent.clone();

        nodeClone.actionsList = (ArrayList<String>) actionsList.clone();

        return nodeClone;
    }

    public Node copyOfNode() throws CloneNotSupportedException{

        return (Node) this.clone();
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

        //compare the data members
        if(this.state == null)
            System.out.println("this");
        if(in.state == null)
            System.out.println("in");

        if(this.state.equals(in.state) && this.pathCost == in.pathCost && this.hValue == in.hValue && this.action.equals(in.action))
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

    public String getAction() {

        return action;
    }

    public void setAction(String action) {

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

    public ArrayList<String> getActionsList() {

        return actionsList;
    }

//    public ArrayList<Location> getLocationList() {
//
//        return locationList;
//    }
//
//    public void addLocation(Location location) {
//
//        locationList.add(location);
//    }

    @Override
    public String toString() {

        return "Agent id : " + this.state.getId() + " Percept = " + this.state.getPercept() +
                " Location x = " + this.state.getLocation().getX() + " Location y = " + this.state.getLocation().getY(); // +
        // "action = " + this.action.toString();
    }
}

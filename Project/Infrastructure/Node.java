package Project.Infrastructure;

import Project.Algorithms.Classical_Search.*;
import Project.Game_Engine.Action;
import Project.Game_Engine.Cell;
import Project.Game_Engine.Environment;
import Project.Game_Engine.Location;

import java.util.ArrayList;
import java.util.Comparator;

public class Node implements Cloneable, Comparable<Node> {

    private Cell state;
    private Node parent;
    private String action;
    private ArrayList<String> actionsList;
    private double pathCost;  //g(n)
    private double hValue;  // h(n) heuristic estimate
    private double fValue;  //f(n) = g(n) + h(n)


    //Root therefore no parent or action
    public Node(Cell state) {

        this.state = state;
        parent = null;
        action = "null action";
        actionsList = new ArrayList<>();
        pathCost = 0;
    }

    /**
     * Added recent
     *
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

        if (parent.getActionsList().size() == 0) {
            actionsList.add(0, action);
        } else {

            actionsList = parent.getActionsList();
            actionsList.add(action);
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        //do a shallow copy first
        Node nodeClone = (Node) super.clone();

        //now do a deep copy
        nodeClone.state = (Cell) state.clone();

        if (parent != null)
            nodeClone.parent = (Node) parent.clone();

        nodeClone.actionsList = (ArrayList<String>) actionsList.clone();

        return nodeClone;
    }

    public Node copyOfNode() throws CloneNotSupportedException {

        return (Node) this.clone();
    }

    //Auto done when implemented comparable
    // Comparing pathCost
    @Override
    public int compareTo(Node node) {

        if (this.fValue == node.getFValue())
            return 0;
        else if (this.fValue < node.getFValue())
            return -1;
        else
            return 1;
    }

    /**
     * Update to include all path path costs
     */
    @Override
    public boolean equals(Object o) {

        if (o == null) return false;  //case of parent.parent being the root/initial state

        if (this == o) return true; //check for self-comparison

        if (!(o instanceof Node)) return false;

        Node in = (Node) o;

        //compare the data members
        if (this.state == null)
            System.out.println("this");
        if (in.state == null)
            System.out.println("in");

        if (this.state.equals(in.state) && this.pathCost == in.pathCost && this.hValue == in.hValue && this.action.equals(in.action))
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


    public ArrayList<String> getActionsList() {

        return actionsList;
    }

    @Override
    public String toString() {

        //Could add parent state info

        return "Agent id : " + this.state.getId() + " Percept = " + this.state.getPercept() +
                state.toString();
    }


    public static class NodeComparator <AnyType> implements Comparator<Node> {

        private Cell[] goals;
        private String searchType;

        public NodeComparator(Cell[] goals, String searchType) {
            this.goals = goals;
            this.searchType = searchType;
        }

        @Override
        public int compare(Node thisNode, Node thatNode) {

            Heuristics heuristics = new Heuristics();

            switch (searchType) {

                case "UniformedCostSearch": {

                        return contrast(thisNode.getPathCost(),thatNode.getPathCost());
                }
                case "GreedyBestFirstSearch": {

                    double thisNodeHValue = heuristics.getHValue(thisNode.getState().getLocation().getX(),
                                            thisNode.getState().getLocation().getY(), goals[0]);
                    double thatNodeHValue = heuristics.getHValue(thatNode.getState().getLocation().getX(),
                                            thatNode.getState().getLocation().getY(), goals[0]);

                        return contrast(thisNodeHValue, thatNodeHValue);
                }
                case "AStarSearch": {

                    double thisNodeFValue = heuristics.getFValue(thisNode.getState().getLocation().getX(),
                                            thisNode.getState().getLocation().getY(), goals[0], thisNode.pathCost);
                    double thatNodeFValue = heuristics.getFValue(thatNode.getState().getLocation().getX(),
                                            thatNode.getState().getLocation().getY(), goals[0], thatNode.pathCost);

                    return contrast(thisNodeFValue, thatNodeFValue);

                }
                default:
                    break;
            }

            return 0;
        }
        public int contrast(double thisNodeValue, double thatNodeValue){

            if (thisNodeValue > thatNodeValue)
                return 1;
            else if (thisNodeValue < thatNodeValue)
                return -1;

            return 0;
        }
    }

}
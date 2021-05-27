package Project.Algorithms.Classical_Search;

import Project.Game_Engine.Environment;
import Project.Infrastructure.Node;
import Project.Game_Engine.Percepts;
import Project.Infrastructure.SuccessorFunction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

public class DepthFirstSearch implements Percepts {

    private Node rootNode;
    private ArrayList<Node> nodesDB;  // HashSet
    private Stack<Node> fringe;
    private LinkedList<Node> expandedNodes;
    private Environment environment;

    public DepthFirstSearch(Environment environment) {

        fringe = new Stack<>();
        expandedNodes = new LinkedList<>();

        nodesDB = new ArrayList<>();
        this.environment = environment;
    }

    public boolean isDuplicateNode(Node thatNode) {

        //search DB
        for (Node thisNode : nodesDB) {
            if(thisNode.equals(thatNode)) {
                return true;
            }
        }

        nodesDB.add(thatNode);

        return false;
    }

    public Node search(Node rootNode) throws CloneNotSupportedException {

        this.rootNode = rootNode;

        if(rootNode == null)
            System.out.println("root node null");

        if(rootNode.getState().getPercept().equals(GOAL_CELL))
            return rootNode;

        // add the root node to the fringe
        fringe.push(rootNode);

        do {
            Node currentNode = fringe.pop();

            if(!isDuplicateNode(currentNode)) {
                expandedNodes = SuccessorFunction.expand(currentNode, environment);
            }

            while(!expandedNodes.isEmpty()) {
                Node tempNode = expandedNodes.remove();

                if(tempNode == null || tempNode.getState() == null) {
                    continue;
                }
                else if(tempNode.getState().getPercept().equals(GOAL_CELL) && !environment.getCells()[tempNode.getState().getLocation().getX()][tempNode.getState().getLocation().getY()].getGoalFound()) {
                    environment.getCells()[tempNode.getState().getLocation().getX()][tempNode.getState().getLocation().getY()].setGoalFound(true);
                    return tempNode;
                }

                fringe.push(tempNode);
            }

        } while(!fringe.isEmpty());

        return null;
    }
}


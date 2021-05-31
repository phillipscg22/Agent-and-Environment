package Project.Algorithms.Classical_Search;

import Project.Game_Engine.Environment;
import Project.Game_Engine.Percepts;
import Project.Infrastructure.Node;
import Project.Infrastructure.SuccessorFunction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class DepthLimitedSearch implements Percepts {

    private Node rootNode;
    private HashMap<String, Node> nodesDB;
    private Stack<Node> fringe;
    private LinkedList<Node> expandedNodes;
    private Environment environment;

    public DepthLimitedSearch(Environment environment) {

        fringe = new Stack<>();
        expandedNodes = new LinkedList<>();

        nodesDB = new HashMap<>();
        this.environment = environment;
    }

    public boolean isDuplicateNode(Node thatNode) {

        //search DB
        if(!nodesDB.containsKey(thatNode.toString())) {
            nodesDB.put(thatNode.toString(), thatNode);
            return false;
        }

        return true;
    }

    public Node search(Node rootNode, int depthLimit) throws CloneNotSupportedException {

        this.rootNode = rootNode;

        if(rootNode.getState().getPercept().equals(GOAL_CELL))
            return rootNode;

        // add the root node to the fringe
        fringe.push(rootNode);

        do {
            Node currentNode = fringe.pop();

            if(currentNode.getActionsList().size() <= depthLimit && !isDuplicateNode(currentNode)) {
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

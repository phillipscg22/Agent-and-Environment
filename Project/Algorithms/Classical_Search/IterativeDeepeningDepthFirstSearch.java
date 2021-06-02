package Project.Algorithms.Classical_Search;

import Project.Game_Engine.Constants;
import Project.Game_Engine.Environment;
import Project.Infrastructure.Node;
import Project.Infrastructure.SuccessorFunction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class IterativeDeepeningDepthFirstSearch implements Constants {

    private Node rootNode;
    private HashMap<String, Node> nodesDB;
    private Stack<Node> fringe;
    private LinkedList<Node> expandedNodes;
    private Environment environment;

    public IterativeDeepeningDepthFirstSearch(Environment environment) {

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

    public Node search(Node rootNode, long maxTime) throws CloneNotSupportedException {

        int depthLimit = 0;

        this.rootNode = rootNode;

        if(rootNode.getState().getPercept().equals(GOAL_CELL))
            return rootNode;

        boolean timeSensitive = false;
        if(maxTime >= 0) {
            timeSensitive = true;
        }

        long startTime = System.currentTimeMillis();

        while(depthLimit < environment.getGridSize() * 2) {

            if(System.currentTimeMillis() - startTime > maxTime && timeSensitive)
                break;

            // add the root node to the fringe
            fringe.push(rootNode);

            while(!fringe.isEmpty()) {

                if(System.currentTimeMillis() - startTime > maxTime && timeSensitive)
                    break;

                Node currentNode = fringe.pop();

                if (currentNode.getActionsList().size() <= depthLimit && !isDuplicateNode(currentNode)) {
                    expandedNodes = SuccessorFunction.expand(currentNode, environment);
                }

                while (!expandedNodes.isEmpty()) {
                    Node tempNode = expandedNodes.remove();

                    if (tempNode == null || tempNode.getState() == null) {
                        continue;
                    } else if (tempNode.getState().getPercept().equals(GOAL_CELL) && !environment.getCells()[tempNode.getState().getLocation().getX()][tempNode.getState().getLocation().getY()].getGoalFound()) {
                        environment.getCells()[tempNode.getState().getLocation().getX()][tempNode.getState().getLocation().getY()].setGoalFound(true);
                        return tempNode;
                    }

                    fringe.push(tempNode);
                }
            }

            depthLimit++;
            nodesDB.clear();
        }

        return null;
    }
}

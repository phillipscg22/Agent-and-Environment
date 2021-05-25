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

    public Node search(Node rootNode) throws CloneNotSupportedException { //  (String [][] grid, Node rootNode)  {

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
            else
                System.out.println("duplicate");

            while(!expandedNodes.isEmpty()) {
                Node tempNode = expandedNodes.remove();

                if(tempNode == null || tempNode.getState() == null) {
                    continue;
                }
                else if(tempNode.getState().getPercept().equals(GOAL_CELL)) {
                    System.out.println("testing node location " + tempNode.getState().getLocation().toString() + " and direction : " + tempNode.getState().getDirection());
                    return tempNode;
                }
                else {
                    System.out.println(tempNode.getState().getPercept());
                    System.out.println("testing node location " + tempNode.getState().getLocation().toString() + " and direction : " + tempNode.getState().getDirection());
                }

                fringe.push(tempNode);
            }

//            System.out.println("new fringe " + fringe.size());
        } while(!fringe.empty());

        return null;
    }
}


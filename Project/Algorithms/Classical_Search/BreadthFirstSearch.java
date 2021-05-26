package Project.Algorithms.Classical_Search;

import Project.Game_Engine.Environment;
import Project.Infrastructure.Node;
import Project.Game_Engine.Percepts;
import Project.Infrastructure.SuccessorFunction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch implements Percepts {
	
    private Node rootNode;
    private HashSet<Node> nodesDB;  // HashSet
    private Queue<Node> fringe;
    private LinkedList<Node> expandedNodes;
    private Environment environment;

    public BreadthFirstSearch(Environment environment) {

        fringe = new LinkedList<>();
        expandedNodes = new LinkedList<>();
        
        nodesDB = new HashSet<>();
        this.environment = environment;
    }
    
    public boolean isDuplicateNode(Node thatNode) {

        //search DB
        //avoid for loop use key to find duplicates
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
        fringe.add(rootNode);

        do {
            Node currentNode = fringe.remove();

            if(!isDuplicateNode(currentNode)) {
                expandedNodes = SuccessorFunction.expand(currentNode, environment);
            }

            while(!expandedNodes.isEmpty()) {
                Node tempNode = expandedNodes.remove();

                if(tempNode == null || tempNode.getState() == null) {
                    continue;
                }
                else if(tempNode.getState().getPercept().equals(GOAL_CELL)) {
                    return tempNode;
                }

                fringe.add(tempNode);
            }

        } while(!fringe.isEmpty());

        return null;
    }
}

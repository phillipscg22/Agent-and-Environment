package Project.Algorithms.Classical_Search;

import Project.Game_Engine.Environment;
import Project.Infrastructure.Node;
import Project.Game_Engine.Percepts;
import Project.Infrastructure.SuccessorFunction;

import java.util.*;

public class BreadthFirstSearch implements Percepts {
	
    private Node rootNode;
    private HashMap<String, Node> nodesDB;
    private Queue<Node> fringe;
    private LinkedList<Node> expandedNodes;
    private Environment environment;

    public BreadthFirstSearch(Environment environment) {

        fringe = new LinkedList<>();
        expandedNodes = new LinkedList<>();
        
        nodesDB = new HashMap<>();
        this.environment = environment;
    }
    
    public boolean isDuplicateNode(Node thatNode) {

        //search DB
        if(nodesDB.containsKey(thatNode.toString())) {
            nodesDB.put(thatNode.toString(), thatNode);
            return true;
        }

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
                else if(tempNode.getState().getPercept().equals(GOAL_CELL) && !environment.getCells()[tempNode.getState().getLocation().getX()][tempNode.getState().getLocation().getY()].getGoalFound()) {
                    environment.getCells()[tempNode.getState().getLocation().getX()][tempNode.getState().getLocation().getY()].setGoalFound(true);
                    return tempNode;
                }

                fringe.add(tempNode);
            }

        } while(!fringe.isEmpty());

        return null;
    }
}

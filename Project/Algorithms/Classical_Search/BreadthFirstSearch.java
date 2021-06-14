package Project.Algorithms.Classical_Search;

import Project.Game_Engine.Constants;
import Project.Game_Engine.Environment;
import Project.Infrastructure.Node;
import Project.Infrastructure.SuccessorFunction;

import java.util.*;

public class BreadthFirstSearch implements Constants {
	
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
        if(!nodesDB.containsKey(thatNode.toString())) {
            nodesDB.put(thatNode.toString(), thatNode);
            return false;
        }

        return true;
     }

    public Node search(Node rootNode, long maxTime) throws CloneNotSupportedException {

        this.rootNode = rootNode;

        if(rootNode == null)
            System.out.println("root node null");

        if(rootNode.getState().getPercept().equals(GOAL_CELL) && !environment.getCells()[rootNode.getState().getLocation().getX()][rootNode.getState().getLocation().getY()].getGoalFound())
            return rootNode;

        // add the root node to the fringe
        fringe.add(rootNode);

        boolean timeSensitive = false;
        if(maxTime >= 0) {
            timeSensitive = true;
        }

        Node lastNode = rootNode;

        int numOfTimesExpanded = 0;
        long startTime = System.currentTimeMillis();

        while(!fringe.isEmpty()) {

            if(System.currentTimeMillis() - startTime > maxTime && timeSensitive)
                break;

            Node currentNode = fringe.remove();

            if(!isDuplicateNode(currentNode)) {
                expandedNodes = SuccessorFunction.expand(currentNode, environment);
                numOfTimesExpanded++;
            }

            while(!expandedNodes.isEmpty()) {
                Node tempNode = expandedNodes.remove();

                if(tempNode == null || tempNode.getState() == null) {
                    continue;
                }
                else if(tempNode.getState().getPercept().equals(GOAL_CELL) && !environment.getCells()[tempNode.getState().getLocation().getX()][tempNode.getState().getLocation().getY()].getGoalFound()) {
                    environment.getCells()[tempNode.getState().getLocation().getX()][tempNode.getState().getLocation().getY()].setGoalFound(true);
                    System.out.println("Number of times expanded : " + numOfTimesExpanded);
                    System.out.println("Time taken to complete search: " + (System.currentTimeMillis() - startTime));
                    return tempNode;
                }

                fringe.add(tempNode);
                if(tempNode.getActionsList().size() > lastNode.getActionsList().size())
                    lastNode = tempNode;
            }
        }

        System.out.println("Number of times expanded : " + numOfTimesExpanded);
        System.out.println("Time taken to complete search: " + (System.currentTimeMillis() - startTime));
        return lastNode;
    }
}

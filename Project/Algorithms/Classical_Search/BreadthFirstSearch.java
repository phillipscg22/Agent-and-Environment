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
    private ArrayList<Node> nodesDB;  // HashSet
    private Queue<Node> fringe;
    private LinkedList<Node> expandedNodes;
    private Environment environment;

    public BreadthFirstSearch(Environment environment) {

        fringe = new LinkedList<>();
        expandedNodes = new LinkedList<>();
        
        nodesDB = new ArrayList<>();
        this.environment = environment;
    }
    
    public boolean isDuplicateNode(Node thatNode) {

        //search DB
        for (Node thisNode : nodesDB) {
            if(thisNode.equals(thatNode)) {
                System.out.println("duplicate in method");
                return true;
            }
        }

        nodesDB.add(thatNode);

        return false;
     }

    public Node search(Node rootNode) { //  (String [][] grid, Node rootNode)  {

        this.rootNode = rootNode;

        if(rootNode.getState().getPercept().equals(GOAL_CELL))
            return rootNode;

        // add the root node to the fringe
        fringe.add(rootNode);

        do {

            System.out.println(fringe.size() + " fringe size");
            Node currentNode = fringe.remove();

            if(!isDuplicateNode(currentNode)) {
                expandedNodes = SuccessorFunction.expand(currentNode, environment);
                System.out.println("expanded size: " + expandedNodes.size());
                //System.out.println("not duplicate");
                //System.out.println(currentNode.getState().getLocation().getX() + " : " + currentNode.getState().getLocation().getY());
            }

            while(!expandedNodes.isEmpty()) {
                Node tempNode = expandedNodes.remove();



                if(tempNode == null || tempNode.getState() == null) {
               //     System.out.println("current node is null");
                    continue;
                }
                else if(tempNode.getState().getPercept().equals(GOAL_CELL)) {
                    System.out.println("Location : " + tempNode.getState().getLocation().getX() + " : " + currentNode.getState().getLocation().getY());
                    System.out.println("goal" + tempNode.getState().getLocation().getX() + " : " + currentNode.getState().getLocation().getY());
                    return tempNode;
                }
                else {
                    System.out.println(tempNode.getState().getPercept());
                    System.out.println("temp Location : " + tempNode.getState().getLocation().getX() + " : " + currentNode.getState().getLocation().getY());
                   // System.out.println("newest " + tempNode.getState().getAgent().toString());
                }
               // System.out.println(tempNode.getState().getPercept());

               // System.out.println("Direction of currentNode is: " + tempNode.getState().getAgent().getDirection());
                fringe.add(tempNode);
              //  System.out.println("newest " + tempNode.getState().getAgent().toString());
            }

            System.out.println("new " + fringe.size());
        } while(!fringe.isEmpty());

        return null;
    }
}

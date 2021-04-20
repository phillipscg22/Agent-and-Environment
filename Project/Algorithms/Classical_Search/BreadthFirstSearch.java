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
	
    Node rootNode;
    private ArrayList<Node> nodesDB;  // HashSet
    //SuccessorFunction succFunction;
    Queue<Node> fringe;
    LinkedList<Node> expandedNodes;
    private Environment environment;

    public BreadthFirstSearch(Environment environment) {

        fringe = new LinkedList<>();
        expandedNodes = new LinkedList<>();
        
        nodesDB = new ArrayList<>();
        this.environment = environment;
    }
    
    public boolean isDuplicateNode(Node thatNode){


        //search DB
        for (Node thisNode : nodesDB) {
//            System.out.println(thisNode.getState().getLocation().getX() + " : " + thisNode.getState().getLocation().getY());
//            System.out.println(thatNode.getState().getLocation().getX() + " : " + thatNode.getState().getLocation().getY());
            if(thisNode.equals(thatNode)) {
                return true;
            }
        }

//        if(nodesDB.contains(thatNode))
//            return true;

        nodesDB.add(thatNode);

        return false;
     }

    public Node search(Node rootNode) { //  (String [][] grid, Node rootNode)  {

        this.rootNode = rootNode;

        if(rootNode.getState().getPercept().equals(GOAL_CELL))
            return rootNode;

        // add the root node to the fringe
        fringe.add(rootNode);
       // System.out.println(fringe.size());

        while(!fringe.isEmpty()) {

            //System.out.println(fringe.size());
            Node currentNode = fringe.remove();

            //Not determining if it is a duplicate correctly
            if(!isDuplicateNode(currentNode)) {
                expandedNodes = SuccessorFunction.expand(currentNode, environment);
                //System.out.println(expandedNodes.size());
                System.out.println("not duplicate");
                //System.out.println(currentNode.getState().getLocation().getX() + " : " + currentNode.getState().getLocation().getY());
            }
            else {
                System.out.println("duplicate");
            }

            while(!expandedNodes.isEmpty()) {
                currentNode = expandedNodes.remove();

                //Check if it is a goal cell
                //System.out.println(expandedNodes.size());
//                if(currentNode.getState().getPercept() == "null") {System.out.println("percept null");}
                System.out.println("current percept: " + currentNode.getState().getPercept());
                if(currentNode == null)
                    continue;
                else if(currentNode.getState() == null)
                    continue;
                else if(currentNode.getState().getPercept().equals(GOAL_CELL)) {
                    System.out.println("goal");
                    return currentNode;
                }
                else
                    System.out.println(currentNode.getState().getPercept());


                fringe.add(currentNode);
            }
         //  System.out.println("out of inner");

            System.out.println(fringe.size());
        }

        return null;
    }
}

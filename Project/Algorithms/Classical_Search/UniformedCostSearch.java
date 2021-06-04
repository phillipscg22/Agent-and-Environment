package Project.Algorithms.Classical_Search;

import Project.Game_Engine.Constants;
import Project.Game_Engine.Environment;
import Project.Infrastructure.Node;
import Project.Infrastructure.SuccessorFunction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class UniformedCostSearch implements Constants {

    private Node rootNode;
    private HashMap<String, Node> nodesDB;
    private PriorityQueue<Node> fringe;
    private LinkedList<Node> expandedNodes;
    private Environment environment;

    public UniformedCostSearch(Environment environment) {

        fringe = new PriorityQueue<>(new Node.NodeComparator());
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

    public Node search(Node rootNode, long maxTime) throws CloneNotSupportedException { //  (String [][] grid, Node rootNode)  {

        this.rootNode = rootNode;

        if(rootNode == null)
            System.out.println("root node null");

        if(rootNode.getState().getPercept().equals(GOAL_CELL))
            return rootNode;

        // add the root node to the fringe
        fringe.add(rootNode);

        boolean timeSensitive = false;
        if(maxTime >= 0) {
            timeSensitive = true;
        }

        long startTime = System.currentTimeMillis();

        while(!fringe.isEmpty()) {

            if(System.currentTimeMillis() - startTime > maxTime && timeSensitive)
                break;

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

        }

        return null;
    }
}

//package Project.Algorithms.Classical_Search;
//
//import Project.Game_Engine.Environment;
//import Project.Game_Engine.Percepts;
//import Project.Infrastructure.Node;
//import Project.Infrastructure.SuccessorFunction;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.Queue;
//
//public class BidirectionalSearch implements Constants {
//
//    private Node rootNode;
//    private HashMap<String, Node> nodesDB;
//    private Queue<Node> fringeFrontToBack;
//    private Queue<Node> fringeBackToFront;
//    private LinkedList<Node> expandedNodesFrontToBack;
//    private LinkedList<Node> expandedNodesBackToFront;
//    private Environment environment;
//
//    public BidirectionalSearch(Environment environment) {
//
//        fringeFrontToBack = new LinkedList<>();
//        fringeBackToFront = new LinkedList<>();
//        expandedNodesFrontToBack = new LinkedList<>();
//        expandedNodesBackToFront = new LinkedList<>();
//
//        nodesDB = new HashMap<>();
//        this.environment = environment;
//    }
//
//    public boolean isDuplicateNode(Node thatNode) {
//
//        //search DB
//        if(!nodesDB.containsKey(thatNode.toString())) {
//            nodesDB.put(thatNode.toString(), thatNode);
//            return false;
//        }
//
//        return true;
//    }
//
//    public Node search(Node rootNode, Node goalNode) throws CloneNotSupportedException { //  (String [][] grid, Node rootNode)  {
//
//        this.rootNode = rootNode;
//
//        if(rootNode == null)
//            System.out.println("root node null");
//        if(goalNode == null)
//            System.out.println("goal node null");
//
//        if(rootNode.getState().getPercept().equals(GOAL_CELL))
//            return rootNode;
//
//        // add the root node to the fringe
//        fringeFrontToBack.add(rootNode);
//        fringeBackToFront.add(goalNode);
//
//        do {
//
//            Node currentFrontNode = fringeFrontToBack.remove();
//            Node currentBacknode = fringeBackToFront.remove();
//
//            if(!isDuplicateNode(currentNode)) {
//                expandedNodes = SuccessorFunction.expand(currentNode, environment);
//            }
//
//            while(!expandedNodes.isEmpty()) {
//                Node tempNode = expandedNodes.remove();
//
//                if(tempNode == null || tempNode.getState() == null) {
//                    continue;
//                }
//                else if(tempNode.getState().getPercept().equals(GOAL_CELL) && !environment.getCells()[tempNode.getState().getLocation().getX()][tempNode.getState().getLocation().getY()].getGoalFound()) {
//                    environment.getCells()[tempNode.getState().getLocation().getX()][tempNode.getState().getLocation().getY()].setGoalFound(true);
//                    return tempNode;
//                }
//
//                fringe.add(tempNode);
//            }
//
//        } while(!fringe.isEmpty());
//
//        return null;
//    }
//}

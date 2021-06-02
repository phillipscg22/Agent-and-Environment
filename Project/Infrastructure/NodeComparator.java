package Project.Infrastructure;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

    /**
     * Make part of Node class
     */

    @Override
    public int compare(Node thisNode, Node thatNode) {

        if(thisNode.getPathCost() > thatNode.getPathCost())
            return 1;
        else if(thisNode.getPathCost() < thatNode.getPathCost())
            return -1;

        return 0;
    }
}

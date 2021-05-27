package Project.Game_Engine;

import Project.Infrastructure.Node;

public class testEquals {

    public static void main(String [] args) {

        Node cell1 = new Node(new Cell(new Location(1, 1), 100, 100, "null", 1));
        Node cell2 = new Node(new Cell(new Location(1, 1), 100, 100, "null", 2));

        System.out.println(cell1.equals(cell2));

    }
}

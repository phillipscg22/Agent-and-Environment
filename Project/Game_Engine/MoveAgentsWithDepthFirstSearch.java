package Project.Game_Engine;

import Project.Algorithms.Classical_Search.DepthFirstSearch;
import Project.Infrastructure.Node;

public class MoveAgentsWithDepthFirstSearch implements Action, Percepts {

    private Environment environment;
    private TruckAgent[] agents;
    private Cell[][] cells;
    private Percept[][] perceptsOfCells;
    private int numOfAgents;
    private int truckCounter;
    private Cell cell;

    public MoveAgentsWithDepthFirstSearch(Environment environment, int goalCells, int numOfAgents, int numOfNonPassableCells) throws CloneNotSupportedException {

        this.environment = environment;
        agents = environment.fillCells(numOfAgents, numOfNonPassableCells);
        this.numOfAgents = numOfAgents;
        environment.createGoals(goalCells);
        cells = environment.getCells();
        perceptsOfCells = environment.getPercepts();

        //finds where agent is
        for(int i = 0; i < environment.getGridSize(); i++)
            for(int j = 0; j < environment.getGridSize(); j++)
                if(environment.getCells()[i][j].getAgent() != null)
                    this.cell = environment.getCells()[i][j];

        System.out.println("x : " + this.cell.getLocation().getX());
        System.out.println("y : " + this.cell.getLocation().getY());

        runTruck();
    }

    public void runTruck() throws CloneNotSupportedException {

        while (truckCounter < numOfAgents) {

            TruckAgent truck = agents[truckCounter];

            new Thread(() -> {

                while(truck.getMovable()) {

                    Node node = new Node(cell);

                    DepthFirstSearch dfs = new DepthFirstSearch(environment);

                    try {
                        Node goalNode = dfs.search(node);

                        if (goalNode == null)
                            System.out.println("Null goal");
                        else {
                            System.out.println(goalNode.getState().getPercept());
                            System.out.println(goalNode.getState().getLocation().toString());

                            /**     WILL MOVE AGENT TO SPOT OF GOAL FOUND   */

                            System.out.println("Action list size: " + goalNode.getActionsList().size());
                            for (int i = 0; i < goalNode.getActionsList().size(); i++) {

                                if (goalNode.getActionsList().get(i).equals("goForward"))
                                    Action.goForward(environment, cell.getAgent());
                                else if (goalNode.getActionsList().get(i).equals("turnRight"))
                                    Action.turnRight(cell.getAgent());
                                else if (goalNode.getActionsList().get(i).equals("turnLeft"))
                                    Action.turnLeft(cell.getAgent());

                                try {
                                    Thread.sleep(250);
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                    catch (CloneNotSupportedException e) {}

                    truck.setMovable(false);
                    System.out.println(GOAL_CELL);
                }



            }).start();

            truckCounter++;
        }
    }
}

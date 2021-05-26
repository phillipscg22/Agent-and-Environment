package Project.Game_Engine;

import Project.Algorithms.Classical_Search.BreadthFirstSearch;
import Project.Infrastructure.Node;
import javafx.application.Platform;

public class MoveAgentWithBreadthFirstSearch implements Action, Percepts {

    private Environment environment;
    private TruckAgent[] agents;
    private Cell[][] cells;
    private Percept[][] perceptsOfCells;
    private int numOfAgents;
    private int truckCounter;
    private Cell cell;

    public MoveAgentWithBreadthFirstSearch(Environment environment, int goalCells, int numOfAgents, int numOfNonPassableCells) throws CloneNotSupportedException {

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

        runTruck();
    }

    public void runTruck() {

        while (truckCounter < numOfAgents) {

            TruckAgent truck = agents[truckCounter];

            new Thread(() -> {

                while(truck.getMovable()) {

                    Node node = new Node(cell);
                    System.out.println("Root node is located at: " + cell.getLocation().toString());

                    BreadthFirstSearch bfs = new BreadthFirstSearch(environment);

                    try {
                        Node goalNode = bfs.search(node);

                        if (goalNode == null)
                            System.out.println("Null goal");
                        else {
                            System.out.println("Goal node found is located at: " + goalNode.getState().getLocation().toString());

                            /**     WILL MOVE AGENT TO SPOT OF GOAL FOUND   */

                            for (int i = 0; i < goalNode.getActionsList().size(); i++) {

                                if (goalNode.getActionsList().get(i).equals("goForward")) {

                                    int oldX = cell.getAgent().getLocation().getX();
                                    int oldY = cell.getAgent().getLocation().getY();

                                    Action.goForward(environment, cell.getAgent());

                                    Platform.runLater(() -> environment.getCells()[oldX][oldY].buildArrow(cell.getAgent().getDirection()));
                                }
                                else if (goalNode.getActionsList().get(i).equals("turnRight")) {

                                    Action.turnRight(cell.getAgent());

                                    if (cell.getAgent().getDirection() == 3) {
                                        cell.getAgent().flipTruck();
                                    }
                                }
                                else if (goalNode.getActionsList().get(i).equals("turnLeft")) {

                                    Action.turnLeft(cell.getAgent());

                                    if (cell.getAgent().getDirection() == 3) {
                                        cell.getAgent().flipTruck();
                                    }
                                }

                                try {
                                    Thread.sleep(500);
                                } catch (Exception e) {}
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

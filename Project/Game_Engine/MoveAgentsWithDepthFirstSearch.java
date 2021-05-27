package Project.Game_Engine;

import Project.Algorithms.Classical_Search.BreadthFirstSearch;
import Project.Algorithms.Classical_Search.DepthFirstSearch;
import Project.Infrastructure.Node;
import javafx.application.Platform;

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

        runTruck();
    }

    public void runTruck() {

        while (truckCounter < numOfAgents) {

            new Thread(() -> {

                try {

                    int count = truckCounter++;
                    TruckAgent truck = agents[count];

                    while (truck.getMovable()) {

                        Cell cell = cells[agents[count].getLocation().getX()][agents[count].getLocation().getY()];

                        Node node = new Node(cell);
                        System.out.println("Root node is located at: " + cell.getLocation().toString());

                        DepthFirstSearch dfs = new DepthFirstSearch(environment);

                        try {
                            Node goalNode = dfs.search(node);
                            System.out.println("search");

                            if (goalNode == null)
                                System.out.println("Null goal");
                            else {
                                System.out.println("Goal node found is located at: " + goalNode.getState().getLocation().toString());
                                System.out.println("Path cost: " + goalNode.getPathCost());
                                System.out.println("Action list size: " + goalNode.getActionsList().size());

                                /**     WILL MOVE AGENT TO SPOT OF GOAL FOUND   */

                                for (int i = 0; i < goalNode.getActionsList().size(); i++) {

                                    if (goalNode.getActionsList().get(i).equals("goForward")) {

                                        int oldX = cell.getAgent().getLocation().getX();
                                        int oldY = cell.getAgent().getLocation().getY();

                                        Action.goForward(environment, cell.getAgent());

                                        Platform.runLater(() -> environment.getCells()[oldX][oldY].buildArrow(cell.getAgent().getDirection()));
                                    } else if (goalNode.getActionsList().get(i).equals("turnRight")) {

                                        Action.turnRight(cell.getAgent());

                                        if (cell.getAgent().getDirection() == 3) {
                                            cell.getAgent().flipTruck();
                                        }
                                    } else if (goalNode.getActionsList().get(i).equals("turnLeft")) {

                                        Action.turnLeft(cell.getAgent());

                                        if (cell.getAgent().getDirection() == 3) {
                                            cell.getAgent().flipTruck();
                                        }
                                    }

                                    try {
                                        Thread.sleep(250);
                                    } catch (Exception e) {
                                    }
                                }
                            }
                        }
                        catch (CloneNotSupportedException e) { }

                        truck.setMovable(false);
                        System.out.println(GOAL_CELL);
                    }
                }
                catch (Exception ignored) { }
            }).start();
        }
    }
}
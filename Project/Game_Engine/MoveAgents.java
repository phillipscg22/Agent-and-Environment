package Project.Game_Engine;

import Project.Algorithms.Classical_Search.BreadthFirstSearch;
import Project.Algorithms.Classical_Search.DepthFirstSearch;
import Project.Infrastructure.Node;
import javafx.application.Platform;

public class MoveAgents implements Action, Percepts {

    private Environment environment;
    private TruckAgent[] agents;
    private Cell[][] cells;
    private Percept[][] perceptsOfCells;
    private int numOfAgents;
    private int truckCounter;
    private String searchType;

    public MoveAgents(Environment environment, int goalCells, int numOfAgents, int numOfNonPassableCells, String searchType) {

        this.environment = environment;
        agents = environment.fillCells(numOfAgents, numOfNonPassableCells);
        this.numOfAgents = numOfAgents;
        environment.createGoals(goalCells);
        cells = environment.getCells();
        perceptsOfCells = environment.getPercepts();
        truckCounter = 0;
        this.searchType = searchType;

        runTruck();
    }

    public void runTruck() {

        while (truckCounter < numOfAgents) {

            new Thread(() -> {

                try {

                    int count = truckCounter++;
                    TruckAgent truck = agents[count];

                    if (truck.getMovable()) {

                        Cell cell = cells[agents[count].getLocation().getX()][agents[count].getLocation().getY()];

                        Node rootNode = new Node(cell);

                        Node goalNode = null;

                        switch(searchType) {
                            case "breadthFirstSearch" : BreadthFirstSearch bfs = new BreadthFirstSearch(environment); goalNode = bfs.search(rootNode); break;
                            case "depthFirstSearch" : DepthFirstSearch dfs = new DepthFirstSearch(environment); goalNode = dfs.search(rootNode); break;
                            default : break;
                        }

                        Node finalGoalNode = goalNode;
                        Platform.runLater(() -> ReachedEndState.display(count, "agent " + count, rootNode, finalGoalNode, environment));

                        if (goalNode == null)
                            System.out.println("Null goal");
                        else {

                            /**     WILL MOVE AGENT TO SPOT OF GOAL FOUND   */

                            for (int i = 0; i < goalNode.getActionsList().size(); i++) {

                                try {
                                    Thread.sleep(500);
                                } catch (Exception ignored) {}

                                switch (goalNode.getActionsList().get(i)) {
                                    case GO_FORWARD -> {
                                        int oldX = cell.getAgent().getLocation().getX();
                                        int oldY = cell.getAgent().getLocation().getY();
                                        Action.goForward(environment, cell.getAgent());
                                        Platform.runLater(() -> environment.getCells()[oldX][oldY].buildArrow(cell.getAgent().getDirection()));
                                    }
                                    case TURN_RIGHT -> {
                                        Action.turnRight(cell.getAgent());
                                        if (cell.getAgent().getDirection() == 3) {
                                            cell.getAgent().flipTruck();
                                        }
                                    }
                                    case TURN_LEFT -> {
                                        Action.turnLeft(cell.getAgent());
                                        if (cell.getAgent().getDirection() == 3) {
                                            cell.getAgent().flipTruck();
                                        }
                                    }
                                }
                            }
                        }

                        truck.setMovable(false);
                        System.out.println(GOAL_CELL);
                    }
                }
                catch (Exception ignored) {}
            }).start();
        }
    }
}
package Project.Game_Engine;

import Project.Algorithms.Classical_Search.*;
import Project.Infrastructure.Node;
import javafx.application.Platform;

public class MoveAgents implements Action, Constants {

    private Environment environment;
    private TruckAgent[] agents;
    private Cell[][] cells;
    private Percept[][] perceptsOfCells;
    private int truckCounter;
    private String searchType;
    private int waitTime;
    private int maxStep;
    private long maxTime;

    public MoveAgents(Environment environment, String searchType, int maxStep, long maxTime) {

        this.environment = environment;
        agents = environment.fillCells(environment.getNumOfAgents(), environment.getNumOfNonPassable());
        environment.createGoals(environment.getNumOfGoals());
        cells = environment.getCells();
        perceptsOfCells = environment.getPercepts();
        truckCounter = 0;
        this.searchType = searchType;
        this.maxStep = maxStep;
        this.maxTime = maxTime;

        runTruck();
    }

    public void runTruck() {

        while (truckCounter < environment.getNumOfAgents()) {

            new Thread(() -> {

                    try {

                        int count = truckCounter++;
                        TruckAgent truck = agents[count];

                        if (truck.getMovable()) {

                            Cell cell = cells[agents[count].getLocation().getX()][agents[count].getLocation().getY()];

                            Node rootNode = new Node(cell);

                            Node goalNode = null;

                            //Determine what search to do
                            switch (searchType) {
                                case BREADTH_FIRST_SEARCH: {
                                    waitTime = 500;
                                    BreadthFirstSearch bfs = new BreadthFirstSearch(environment);
                                    goalNode = bfs.search(rootNode, maxTime);
                                    break;
                                }
                                case DEPTH_FIRST_SEARCH: {
                                    waitTime = 200;
                                    DepthFirstSearch dfs = new DepthFirstSearch(environment);
                                    goalNode = dfs.search(rootNode, maxTime);
                                    break;
                                }
                                case DEPTH_LIMITED_SEARCH: {
                                    waitTime = 400;
                                    DepthLimitedSearch dls = new DepthLimitedSearch(environment);
                                    goalNode = dls.search(rootNode, maxStep, maxTime);
                                    break;
                                }
                                case ITERATIVE_DEEPENING_DEPTH_FIRST_SEARCH: {
                                    waitTime = 500;
                                    IterativeDeepeningDepthFirstSearch iddfs = new IterativeDeepeningDepthFirstSearch(environment);
                                    goalNode = iddfs.search(rootNode, maxTime);
                                    break;
                                }
                                case UNIFORMED_COST_SEARCH: {
                                    waitTime = 500;
                                    UniformedCostSearch ucs = new UniformedCostSearch(environment);
                                    goalNode = ucs.search(rootNode, maxTime);
                                    break;
                                }
                                default:
                                    break;
                            }

                            Node finalGoalNode = goalNode;
                            if (goalNode != null)
                                Platform.runLater(() -> ReachedEndState.display(count, "agent " + count, rootNode, finalGoalNode, environment));

                            if (goalNode == null)
                                System.out.println("Null goal");
                            else {

                                /**     WILL MOVE AGENT TO SPOT OF GOAL FOUND   */

                                for (int i = 0; i < goalNode.getActionsList().size(); i++) {

                                    try {
                                        Thread.sleep(waitTime);
                                    } catch (Exception ignored) {
                                    }

                                    switch (goalNode.getActionsList().get(i)) {
                                        case GO_FORWARD -> {
                                            int oldX = cell.getAgent().getLocation().getX();
                                            int oldY = cell.getAgent().getLocation().getY();
                                            Action.goForward(environment, cell.getAgent());
                                            Platform.runLater(() -> environment.getCells()[oldX][oldY].buildArrow(cell.getAgent().getDirection()));
                                        }
                                        case TURN_RIGHT -> {
                                            Action.turnRight(cell.getAgent());
                                            if (cell.getAgent().getDirection() == FACING_EAST) {
                                                cell.getAgent().flipTruck();
                                            }
                                        }
                                        case TURN_LEFT -> {
                                            Action.turnLeft(cell.getAgent());
                                            if (cell.getAgent().getDirection() == FACING_EAST) {
                                                cell.getAgent().flipTruck();
                                            }
                                        }
                                    }
                                }

                                truck.setMovable(false);
                                System.out.println(GOAL_CELL);
                            }
                        }
                    } catch (Exception ignored) {
                    }
            }).start();
        }
    }
}
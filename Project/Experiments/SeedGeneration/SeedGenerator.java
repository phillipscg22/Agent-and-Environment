package Project.Experiments.SeedGeneration;

import Project.Algorithms.Classical_Search.*;
import Project.Game_Engine.*;
import Project.Infrastructure.Node;
import javafx.application.Platform;

public class SeedGenerator extends ObstacleGeneration implements Action, Constants {

    private int gridSize;
    private String searchType;
    private Environment environment;
    private int maxStep;
    private long maxTime;
    private int truckCounter = 0;
    private int waitTime;
    private TruckAgent[] agents;
    private Cell[][] cells;

    public SeedGenerator(String searchType, Environment environment, int maxStep, long maxTime) {

        this.gridSize = environment.getGridSize();
        this.searchType = searchType;
        this.environment = environment;
        this.maxStep = maxStep;
        this.maxTime = maxTime;

        //Will have to use switch later on when determining if multiagent or not
        agents = fillCells(environment);
        cells = environment.getCells();
        createGoals(environment);
    }

    public void buildSeed(int seed) {

        /**
         * Seed 1-5 could be single goal/agent
         * Seed 6-10 could be multiple agents/goals
         */

        switch (seed) {

            case 1: {
                buildSeed1(environment);
                runTruck();
                break;
            }
            case 2: {
                buildSeed2(environment);
                runTruck();
                break;
            }
            case 3: {
                buildSeed3(environment);
                runTruck();
                break;
            }
            case 4: {
                buildSeed4(environment);
                runTruck();
                break;
            }
            case 5: {
                buildSeed5(environment);
                runTruck();
                break;
            }
            default: {
                break;
            }
        }
    }

    private TruckAgent[] fillCells(Environment environment) {

        for(int i = 0; i < environment.getGridSize(); i++) {

            for(int j = 0; j < environment.getGridSize(); j++) {

                Cell cell = new Cell(new Location(i, j), environment.getWidthOfCell(), environment.getHeightOfCell(), BLANK, Constants.FACING_WEST);
                environment.add(cell, j, i);
                environment.getCells()[i][j] = cell;
            }
        }

        return createAgents(environment);
    }

    private TruckAgent [] createAgents(Environment environment) {

        TruckAgent [] trucks = new TruckAgent[environment.getNumOfAgents()];

        int count = 0;

        int i = 0;
        int j = 0;

        if(environment.getCells()[i][j].getPassable()){
            TruckAgent truck = new TruckAgent();
            truck = truck.build(environment.getWidthOfCell(), environment.getHeightOfCell(), new Location(i, j), truck, count);
            environment.getAgents()[i][j] = truck;
            environment.getCells()[i][j].getChildren().add(truck);
            environment.getCells()[i][j].setVisited(true);
            environment.getCells()[i][j].setAgent(truck);
            trucks[count++] = truck;
            environment.getCells()[i][j].setPassable(false);
        }

        return trucks;
    }

    private void createGoals(Environment environment) {

        environment.setGoals(new Cell[environment.getNumOfGoals()]);

        int i = 0;
        int j = 0;

        if(gridSize == 1);
        else if(gridSize == 2) {
            i = 1;
            j = 1;
        }
        else if(gridSize == 3 || gridSize == 4) {
            i = gridSize - 2;
            j = gridSize - 1;
        }
        else if(gridSize <= 10) {
            i = gridSize / 2 + 1;
            j = gridSize - 1;
        }
        else {
            i = gridSize * 2 / 3 + 1;
            j = gridSize - 1;
        }

        if (environment.getCells()[i][j].getPassable() && !environment.getCells()[i][j].getEndState()) {

            Location location = new Location(i, j);
            Cell cell = new Cell(location, environment.getWidthOfCell(), environment.getHeightOfCell(), GOAL_CELL, Constants.FACING_WEST);
            cell.setEndState(true);
            environment.add(cell, j, i);
            environment.getCells()[i][j] = cell;
            environment.getCells()[i][j].setPassable(true);
            environment.getGoals()[0] = environment.getCells()[i][j];
        }
    }

    public void runTruck() {

        while (truckCounter < environment.getNumOfAgents()) {

            new Thread(() -> {

                long startTime = System.currentTimeMillis();

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
                                waitTime = 100;
                                DepthFirstSearch dfs = new DepthFirstSearch(environment);
                                goalNode = dfs.search(rootNode, maxTime);
                                break;
                            }
                            case DEPTH_LIMITED_SEARCH: {
                                waitTime = 500;
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
                        if (goalNode.getState().getPercept().equals(GOAL_CELL))
                            Platform.runLater(() -> ReachedEndState.display(count, "agent " + count, rootNode, finalGoalNode, environment));
                        else
                            Platform.runLater(() -> ReachedEndState.noGoalFound(count, "agent " + count, finalGoalNode));

                            /**     WILL MOVE AGENT TO SPOT OF GOAL FOUND   */

                            System.out.println("number of actions: " + goalNode.getActionsList().size());
                            for (int i = 0; i < goalNode.getActionsList().size(); i++) {

                                try {
                                    Thread.sleep(waitTime);
                                } catch (Exception ignored) {
                                }

                                switch (goalNode.getActionsList().get(i)) {
                                    case GO_FORWARD : {
                                        int oldX = cell.getAgent().getLocation().getX();
                                        int oldY = cell.getAgent().getLocation().getY();
                                        Action.goForward(environment, cell.getAgent());
                                        Platform.runLater(() -> environment.getCells()[oldX][oldY].buildArrow(cell.getAgent().getDirection()));
                                        break;
                                    }
                                    case TURN_RIGHT : {
                                        Action.turnRight(cell.getAgent());
                                        if (cell.getAgent().getDirection() == FACING_EAST) {
                                            cell.getAgent().flipTruck();
                                        }
                                        break;
                                    }
                                    case TURN_LEFT : {
                                        Action.turnLeft(cell.getAgent());
                                        if (cell.getAgent().getDirection() == FACING_EAST) {
                                            cell.getAgent().flipTruck();
                                        }
                                        break;
                                    }
                                    default : break;
                                }
                            }

                            truck.setMovable(false);
                            if(goalNode.getState().getPercept().equals(GOAL_CELL))
                                System.out.println(GOAL_CELL);

                    }
                    System.out.println("Total time: " + (System.currentTimeMillis() - startTime));

                } catch (Exception ignored) {
                }
            }).start();
        }
    }
}

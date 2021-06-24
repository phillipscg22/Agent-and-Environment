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

    public SeedGenerator(String searchType, Environment environment, int maxStep, long maxTime) {

        this.gridSize = environment.getGridSize();
        this.searchType = searchType;
        this.environment = environment;
        this.maxStep = maxStep;
        this.maxTime = maxTime;

        fillCells(environment);
    }

    public void buildSeed(int seed) {

        /**
         * Seed 1-5 could be single goal/agent
         * Seed 6-10 could be multiple agents/goals
         */

        switch (seed) {

            case 0: {
                buildSeed0(environment);
                break;
            }
            case 1: {
                buildSeed1(environment);
                break;
            }
            case 2: {
                buildSeed2(environment);
                break;
            }
            case 3: {
                buildSeed3(environment);
                break;
            }
            case 4: {
                buildSeed4(environment);
                break;
            }
            case 5: {
                buildSeed5(environment);
                break;
            }
            case 6: {
                buildSeed6(environment);
                break;
            }
            case 7: {
                buildSeed7(environment);
                break;
            }
            case 8: {
                buildSeed8(environment);
                break;
            }
            case 9: {
                buildSeed9(environment);
                break;
            }
            case 10: {
                buildSeed10(environment);
                break;
            }
            case 11: {
                buildSeed11(environment);
                break;
            }
            case 12: {
                buildSeed12(environment);
                break;
            }
            case 13: {
                buildSeed13(environment);
                break;
            }
            case 14: {
                buildSeed14(environment);
                break;
            }
            case 15: {
                buildSeed15(environment);
                break;
            }
            case 20: {
                buildSeed20(environment);
                break;
            }
            /**
             * Numbers reserved 21-40 and 16-19
             */
            default: {
                break;
            }
        }
        createGoals(environment);
        agents = createAgents(environment);
        runTruck();
    }

    private void fillCells(Environment environment) {

        for(int i = 0; i < environment.getGridSize(); i++) {

            for(int j = 0; j < environment.getGridSize(); j++) {

                Cell cell = new Cell(new Location(i, j), environment.getWidthOfCell(), environment.getHeightOfCell(), BLANK, Constants.FACING_WEST);
                environment.add(cell, j, i);
                environment.getCells()[i][j] = cell;
            }
        }

    }

    private TruckAgent [] createAgents(Environment environment) {

        TruckAgent [] trucks = new TruckAgent[environment.getNumOfAgents()];

        int count = 0;

        while(count < environment.getNumOfAgents()) {

            int i = (int) (Math.random() * gridSize);
            int j = (int) (Math.random() * gridSize);

            if (environment.getCells()[i][j].getPassable() && !environment.getCells()[i][j].getPercept().equals(GOAL_CELL)) {
                TruckAgent truck = new TruckAgent();
                truck = truck.build(environment.getWidthOfCell(), environment.getHeightOfCell(), new Location(i, j), truck, count);
                environment.getAgents()[i][j] = truck;
                environment.getCells()[i][j].getChildren().add(truck);
                environment.getCells()[i][j].setVisited(true);
                environment.getCells()[i][j].setAgent(truck);
                trucks[count++] = truck;
                environment.getCells()[i][j].setPassable(false);
            }
        }

        return trucks;
    }

    private void createGoals(Environment environment) {

        environment.setGoals(new Cell[environment.getNumOfGoals()]);

        int count = 0;

        while(count < environment.getNumOfGoals()) {

            int j = (int)(Math.random() * gridSize);
            int i = (int)(Math.random() * gridSize);

            if (environment.getCells()[i][j].getPassable() && !environment.getCells()[i][j].getEndState()) {

                Location location = new Location(i, j);
                Cell cell = new Cell(location, environment.getWidthOfCell(), environment.getHeightOfCell(), GOAL_CELL, Constants.FACING_WEST);
                cell.setEndState(true);
                environment.add(cell, j, i);
                environment.getCells()[i][j] = cell;
                environment.getCells()[i][j].setPassable(true);
                environment.getGoals()[count++] = environment.getCells()[i][j];
            }
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

                        Cell cell = environment.getCells()[agents[count].getLocation().getX()][agents[count].getLocation().getY()];

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
                            case GREEDY_BEST_FIRST_SEARCH: {
                                waitTime = 500;
                                GreedyBestFirstSearch gbfs = new GreedyBestFirstSearch(environment);
                                goalNode = gbfs.search(rootNode, maxTime);
                                break;
                            }
                            case A_STAR_SEARCH: {
                                waitTime = 500;
                                AStarSearch ass = new AStarSearch(environment);
                                goalNode = ass.search(rootNode, maxTime);
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

                            System.out.println("Agent " + count + " number of actions: " + goalNode.getActionsList().size());
                            for (int i = 0; i < goalNode.getActionsList().size(); i++) {

                                try {
                                    Thread.sleep(waitTime);
                                } catch (Exception ignored) {
                                }

                                switch (goalNode.getActionsList().get(i)) {
                                    case GO_FORWARD : {
                                        int oldX = truck.getLocation().getX();
                                        int oldY = truck.getLocation().getY();
                                        Action.goForward(environment, truck);
                                        Platform.runLater(() -> environment.getCells()[oldX][oldY].buildArrow(truck.getDirection()));
                                        break;
                                    }
                                    case TURN_RIGHT : {
                                        Action.turnRight(truck);
                                        if (truck.getDirection() == FACING_EAST) {
                                            truck.flipTruck();
                                        }
                                        break;
                                    }
                                    case TURN_LEFT : {
                                        Action.turnLeft(truck);
                                        if (truck.getDirection() == FACING_EAST) {
                                            truck.flipTruck();
                                        }
                                        break;
                                    }
                                    default : break;
                                }
                            }

                        try {
                            Thread.sleep(waitTime);
                        } catch (Exception ignored) {
                        }
                            truck.setMovable(false);
                    }
                    System.out.println("Agent " + count + " Total time: " + (System.currentTimeMillis() - startTime - waitTime));

                } catch (Exception ignored) {
                }
            }).start();
        }
    }
}

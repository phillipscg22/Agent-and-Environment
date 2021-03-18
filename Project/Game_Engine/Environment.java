package Project.Game_Engine;

import javafx.scene.layout.GridPane;

public class Environment extends GridPane implements Percepts {

    private double width;
    private double height;
    private TruckAgent[][] agents;
    private Cell[][] cells;
    private double widthOfCell;
    private double heightOfCell;
    private boolean multiAgent;
    private boolean deterministic;
    private int gridSize;         //Don't think we need variable for this
    private int maxStep;
    private long maxTime;
    private String searchType;

    public Environment(double width, double height, boolean multiAgent, boolean deterministic,
                       int gridSize, int maxStep, long maxTime, String searchType) {

        this.width = width;
        this.height = height;
        this.agents = new TruckAgent[gridSize][gridSize];
        this.cells = new Cell[gridSize][gridSize];
        this.multiAgent = multiAgent;
        this.deterministic = deterministic;
        this.gridSize = gridSize;
        this.maxStep = maxStep;
        this.maxTime = maxTime;
        this.searchType = searchType;

        widthOfCell = width / agents.length;
        heightOfCell = height / agents.length;

        this.setGridLinesVisible(true);
    }

    public void setAgents(TruckAgent [][] agents) {
        this.agents = agents;
    }

    public TruckAgent [][] getAgents() {

        return agents;
    }

    public Cell [][] getCells() {
        return cells;
    }

    public TruckAgent [] fillAgents(int numAgents, int numObstacles) {

        for(int i = 0; i < agents.length; i++) {

            for(int j = 0; j < agents[i].length; j++) {

                Cell cell = new Cell(false, false, true, new Location(i, j), widthOfCell, heightOfCell, "null");
                this.add(cell, j, i);
                cells[i][j] = cell;
                agents[i][j] = new TruckAgent(false, true, false, new Location(i, j));
            }
        }

        createObstacles(numObstacles, NON_PASSABLE);

        return createAgents(numAgents);
    }

    public TruckAgent [] createAgents(int numAgents) {

        TruckAgent [] trucks = new TruckAgent[numAgents];

        double widthOfSingleCell = width / agents.length;
        double heightOfSingleCell = height / agents.length;
        int count = 0;

        while(count < numAgents) {

            int i = (int)(Math.random() * agents.length);
            int j = (int)(Math.random() * agents.length);

            if((agents[i][j] == null) || (!agents[i][j].getBuilt() && agents[i][j].getPassable())) {
                TruckAgent truck = new TruckAgent();
                truck = truck.build(widthOfSingleCell, heightOfSingleCell, new Location(i, j));
                agents[i][j] = truck;
                cells[i][j].getChildren().add(truck);
                cells[i][j].setVisited(true);
                trucks[count] = truck;
                count++;
            }
        }

        return trucks;
    }

    public void createObstacles(int numOfObstacles, String percept) {

        int count = 0;

        while(count < numOfObstacles) {

            int j = (int)(Math.random() * agents.length);
            int i = (int)(Math.random() * agents[0].length);

            if((agents[i][j] == null) || (!agents[i][j].getBuilt() && agents[i][j].getPassable())) {

                Cell cell = new Cell(true, false, false, new Location(i, j), widthOfCell, heightOfCell, percept);
                this.add(cell, j, i);
                cells[i][j] = cell;
                count++;
                agents[i][j].setPassable(false);
            }
        }
    }

    public void createGoals(int numGoals) {

        int count = 0;

        while(count < numGoals) {

            int j = (int)(Math.random() * agents.length);
            int i = (int)(Math.random() * agents[0].length);

            if((agents[i][j] == null) || (!agents[i][j].getBuilt() && agents[i][j].getPassable())) {

                Location location = new Location(i, j);
                Cell cell = new Cell(true, true, true, location, widthOfCell, heightOfCell, GOAL_CELL);
                cell.setGoal(true);
                this.add(cell, j, i);
                cells[i][j] = cell;
                agents[i][j].setPassable(true);
                count++;
            }
        }
    }
}
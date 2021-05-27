package Project.Game_Engine;

import javafx.scene.layout.GridPane;

public class Environment extends GridPane implements Percepts {

    private double width;
    private double height;
    private TruckAgent[][] agents;
    private Cell[][] cells;
    private Percept[][] perceptsOfCells;
    private double widthOfCell;
    private double heightOfCell;
    private boolean multiAgent;
    private boolean deterministic;
    private int gridSize;
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

    public int getGridSize() {
        return gridSize;
    }

                            //fillCells rename
    public TruckAgent [] fillCells(int numAgents, int numObstacles) {

        for(int i = 0; i < gridSize; i++) {

            for(int j = 0; j < gridSize; j++) {

                Cell cell = new Cell(new Location(i, j), widthOfCell, heightOfCell, BLANK, Constants.FACING_WEST);
                this.add(cell, j, i);
                cells[i][j] = cell;
            }
        }

        createObstacles(numObstacles);

        return createAgents(numAgents);
    }

    private TruckAgent [] createAgents(int numAgents) {

        TruckAgent [] trucks = new TruckAgent[numAgents];

        double widthOfSingleCell = width / gridSize;
        double heightOfSingleCell = height / gridSize;
        int count = 0;

        while(count < numAgents) {

            int i = (int)(Math.random() * gridSize);
            int j = (int)(Math.random() * gridSize);

            /** only trucks that are built should have id, implement in build method*/
            if(cells[i][j].getPassable()){
                TruckAgent truck = new TruckAgent();
                truck = truck.build(widthOfSingleCell, heightOfSingleCell, new Location(i, j), truck);
                agents[i][j] = truck;
                cells[i][j].getChildren().add(truck);
                cells[i][j].setVisited(true);
                cells[i][j].setAgent(truck);
                trucks[count] = truck;
                count++;
                cells[i][j].setPassable(false);
            }
        }

        return trucks;
    }

    private void createObstacles(int numOfObstacles) {

        int count = 0;

        while(count < numOfObstacles) {

            int j = (int)(Math.random() * gridSize);
            int i = (int)(Math.random() * gridSize);

            if(cells[i][j].getPassable()) {

                Cell cell = new Cell(new Location(i, j), widthOfCell, heightOfCell, NON_PASSABLE, Constants.FACING_WEST);
                cell.setPassable(false);
                cell.fillCell();
                this.add(cell, j, i);
                cells[i][j] = cell;
                count++;
            }
        }
    }

    public void createGoals(int numGoals) {

        int count = 0;

        while(count < numGoals) {

            int j = (int)(Math.random() * gridSize);
            int i = (int)(Math.random() * gridSize);

            if(cells[i][j].getPassable()) {

                Location location = new Location(i, j);
                Cell cell = new Cell(location, widthOfCell, heightOfCell, GOAL_CELL, Constants.FACING_WEST);
                cell.setEndState(true);
                this.add(cell, j, i);
                cells[i][j] = cell;
                cells[i][j].setPassable(true);
                count++;
            }
        }
    }

    public Percept [][] getPercepts() {

        //Create loop that will go in and fill north, south, east, west percepts of each cell
        //  This will create mirror of cells array that holds percepts

        perceptsOfCells = new Percept[gridSize][gridSize];

        //Initialize array
        for(int i = 0; i < gridSize; i++) {

            for(int j = 0; j < gridSize; j++) {

                perceptsOfCells[i][j] = new Percept();
            }
        }

        for(int i = 0; i < gridSize; i++) {

            for(int j = 0; j < gridSize; j++) {

                //Set north percept
                try {
                    perceptsOfCells[i][j].setNorthPercept(cells[i - 1][j].getPercept());
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    perceptsOfCells[i][j].setNorthPercept(BORDER);
                }

                //Set west percept
                try {
                    perceptsOfCells[i][j].setWestPercept(cells[i][j - 1].getPercept());
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    perceptsOfCells[i][j].setWestPercept(BORDER);
                }

                //Set east percept
                try {
                    perceptsOfCells[i][j].setEastPercept(cells[i][j + 1].getPercept());
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    perceptsOfCells[i][j].setEastPercept(BORDER);
                }

                //Set south percept
                try {
                    perceptsOfCells[i][j].setSouthPercept(cells[i + 1][j].getPercept());
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    perceptsOfCells[i][j].setSouthPercept(BORDER);
                }
            }
        }

//        for(int i = 0; i < gridSize; i++)
//            for(int j = 0; j < gridSize; j++)
//                System.out.println(i + "," + j + " : " +perceptsOfCells[i][j].toString());

        return perceptsOfCells;
    }
}
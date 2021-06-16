package Project.Game_Engine;

import javafx.scene.layout.GridPane;

public class Environment extends GridPane implements Constants {

    private double width;
    private double height;
    private TruckAgent[][] agents;
    private Cell[][] cells;
    private Percept[][] perceptsOfCells;
    private double widthOfCell;
    private double heightOfCell;
    private int gridSize;
    private Cell[] goals;
    private int numOfGoals;
    private int numOfAgents;
    private int numOfNonPassable;


    public Environment(double width, double height, int gridSize, int numOfGoals, int numOfAgents, int numOfNonPassable) {

        this.width = width;
        this.height = height;
        this.agents = new TruckAgent[gridSize][gridSize];
        this.cells = new Cell[gridSize][gridSize];
        this.gridSize = gridSize;
        this.numOfGoals = numOfGoals;
        this.numOfAgents = numOfAgents;
        this.numOfNonPassable = numOfNonPassable;

        widthOfCell = width / agents.length;
        heightOfCell = height / agents.length;

        this.setGridLinesVisible(true);
    }

    public Environment(double width, double height, int gridSize, int numOfAgents, int numOfGoals) {

        this.width = width;
        this.height = height;
        this.gridSize = gridSize;
        this.agents = new TruckAgent[gridSize][gridSize];
        this.cells = new Cell[gridSize][gridSize];
        this.agents = new TruckAgent[gridSize][gridSize];
        this.cells = new Cell[gridSize][gridSize];
        this.numOfAgents = numOfAgents;
        this.numOfGoals = numOfGoals;
        widthOfCell = width / gridSize;
        heightOfCell = height / gridSize;

        this.setGridLinesVisible(true);
    }

    public int getNumOfGoals() {

        return numOfGoals;
    }

    public double getWidthOfCell() {

        return widthOfCell;
    }

    public double getHeightOfCell() {

        return heightOfCell;
    }

    public int getNumOfAgents() {

        return numOfAgents;
    }

    public void setNumOfAgents(int numOfAgents) {

        this.numOfAgents = numOfAgents;
    }

    public int getNumOfNonPassable() {

        return numOfNonPassable;
    }

    public void setAgents(TruckAgent[][] agents) {
        this.agents = agents;
    }

    public TruckAgent[][] getAgents() {

        return agents;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getGridSize() {
        return gridSize;
    }

    public Cell[] getGoals() {

        return goals;
    }

    public void setGoals(Cell[] goals) {

        this.goals = goals;
    }

    public TruckAgent[] fillCells(int numAgents, int numObstacles) {

        for (int i = 0; i < gridSize; i++) {

            for (int j = 0; j < gridSize; j++) {

                Cell cell = new Cell(new Location(i, j), widthOfCell, heightOfCell, BLANK, Constants.FACING_WEST);
                this.add(cell, j, i);
                cells[i][j] = cell;
            }
        }

        createObstacles(numObstacles);

        return createAgents(numAgents);
    }

    private TruckAgent[] createAgents(int numAgents) {

        TruckAgent[] trucks = new TruckAgent[numAgents];

        double widthOfSingleCell = width / gridSize;
        double heightOfSingleCell = height / gridSize;
        int count = 0;

        while (count < numAgents) {

            int i = (int) (Math.random() * gridSize);
            int j = (int) (Math.random() * gridSize);

            if (cells[i][j].getPassable()) {
                TruckAgent truck = new TruckAgent();
                truck = truck.build(widthOfSingleCell, heightOfSingleCell, new Location(i, j), truck, count);
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

        while (count < numOfObstacles) {

            int j = (int) (Math.random() * gridSize);
            int i = (int) (Math.random() * gridSize);

            if (cells[i][j].getPassable()) {

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

        this.goals = new Cell[numGoals];
        int count = 0;

        while (count < numGoals) {

            int j = (int) (Math.random() * gridSize);
            int i = (int) (Math.random() * gridSize);

            if (cells[i][j].getPassable() && !cells[i][j].getEndState()) {

                Location location = new Location(i, j);
                Cell cell = new Cell(location, widthOfCell, heightOfCell, GOAL_CELL, Constants.FACING_WEST);
                cell.setEndState(true);
                this.add(cell, j, i);
                cells[i][j] = cell;
                cells[i][j].setPassable(true);
                goals[count] = cells[i][j];
                count++;
            }
        }
    }

    public Percept[][] getPercepts() {

        //Create loop that will go in and fill north, south, east, west percepts of each cell
        //  This will create mirror of cells array that holds percepts

        perceptsOfCells = new Percept[gridSize][gridSize];

        //Initialize array
        for (int i = 0; i < gridSize; i++) {

            for (int j = 0; j < gridSize; j++) {

                perceptsOfCells[i][j] = new Percept();
            }
        }

        for (int i = 0; i < gridSize; i++) {

            for (int j = 0; j < gridSize; j++) {

                //Set north percept
                try {
                    perceptsOfCells[i][j].setNorthPercept(cells[i - 1][j].getPercept());
                } catch (ArrayIndexOutOfBoundsException e) {
                    perceptsOfCells[i][j].setNorthPercept(BORDER);
                }

                //Set west percept
                try {
                    perceptsOfCells[i][j].setWestPercept(cells[i][j - 1].getPercept());
                } catch (ArrayIndexOutOfBoundsException e) {
                    perceptsOfCells[i][j].setWestPercept(BORDER);
                }

                //Set east percept
                try {
                    perceptsOfCells[i][j].setEastPercept(cells[i][j + 1].getPercept());
                } catch (ArrayIndexOutOfBoundsException e) {
                    perceptsOfCells[i][j].setEastPercept(BORDER);
                }

                //Set south percept
                try {
                    perceptsOfCells[i][j].setSouthPercept(cells[i + 1][j].getPercept());
                } catch (ArrayIndexOutOfBoundsException e) {
                    perceptsOfCells[i][j].setSouthPercept(BORDER);
                }
            }
        }

        return perceptsOfCells;
    }
}
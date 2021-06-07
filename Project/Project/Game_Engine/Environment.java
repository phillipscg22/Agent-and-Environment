package Project.Game_Engine;

import Project.Experiments.FixedEnviornments.*;
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

    public Environment(double width, double height, String searchType, int seedNumber, int gridSize) {

        this.width = width;
        this.height = height;
        this.numOfGoals = 1;
        this.numOfAgents = 1;
        this.numOfNonPassable = 1;

        this.setGridLinesVisible(true);

        switch(seedNumber) {

            case 1 : {
                this.gridSize = gridSize;
                this.agents = new TruckAgent[gridSize][gridSize];
                this.cells = new Cell[gridSize][gridSize];
                widthOfCell = width / gridSize;
                heightOfCell = height / gridSize;
                new Seed1(this, searchType, 100, -1);
                break;
            }
            case 2 : {
                this.gridSize = gridSize;
                this.agents = new TruckAgent[gridSize][gridSize];
                this.cells = new Cell[gridSize][gridSize];
                widthOfCell = width / gridSize;
                heightOfCell = height / gridSize;
                //new Seed2(this, searchType, 100, -1);
                break;
            }
            case 3 : {
                this.gridSize = gridSize;
                this.agents = new TruckAgent[gridSize][gridSize];
                this.cells = new Cell[gridSize][gridSize];
                widthOfCell = width / gridSize;
                heightOfCell = height / gridSize;
                new Seed3(this, searchType, 100, -1);
                break;
            }
            default : {
                break;
            }
        }
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

    public int getNumOfNonPassable() {

        return numOfNonPassable;
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

    public Cell[] getGoals() {

        return goals;
    }

    public void setGoals(Cell [] goals) {

        this.goals = goals;
    }

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

            if(cells[i][j].getPassable()){
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

        this.goals = new Cell[numGoals];
        int count = 0;

        while(count < numGoals) {

            int j = (int)(Math.random() * gridSize);
            int i = (int)(Math.random() * gridSize);

            if(cells[i][j].getPassable() && !cells[i][j].getEndState()) {

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

        return perceptsOfCells;
    }

    public void buildSingleNonPassable(int i, int j) {

        if(cells[i][j].getPassable()) {

            Cell cell = new Cell(new Location(i, j), widthOfCell, heightOfCell, NON_PASSABLE, Constants.FACING_WEST);
            cell.setPassable(false);
            cell.fillCell();
            this.add(cell, j, i);
            cells[i][j] = cell;
        }
    }

    public TruckAgent [] fillCellsSituation1() {

        for(int i = 0; i < gridSize; i++) {

            for(int j = 0; j < gridSize; j++) {

                Cell cell = new Cell(new Location(i, j), widthOfCell, heightOfCell, BLANK, Constants.FACING_WEST);
                this.add(cell, j, i);
                cells[i][j] = cell;
            }
        }

        createObstaclesSituation1();

        return createAgentsSituation1();
    }

    private TruckAgent [] createAgentsSituation1() {

        TruckAgent [] trucks = new TruckAgent[1];

        double widthOfSingleCell = width / gridSize;
        double heightOfSingleCell = height / gridSize;
        int count = 0;

        int i = 1;
        int j = 1;

        if(cells[i][j].getPassable()){
            TruckAgent truck = new TruckAgent();
            truck = truck.build(widthOfSingleCell, heightOfSingleCell, new Location(i, j), truck, count);
            agents[i][j] = truck;
            cells[i][j].getChildren().add(truck);
            cells[i][j].setVisited(true);
            cells[i][j].setAgent(truck);
            trucks[count] = truck;
            cells[i][j].setPassable(false);
        }

        return trucks;
    }

    private void createObstaclesSituation1() {

        int i = 8;
        int j = 8;

        buildSingleNonPassable(i, j);

        i = 9;
        j = 9;

        buildSingleNonPassable(i, j);

        i = 6;
        j = 9;

        buildSingleNonPassable(i, j);
    }

    public void createGoalsSituation1() {

        this.goals = new Cell[1];

        int j = 9;
        int i = 8;

        if(cells[i][j].getPassable() && !cells[i][j].getEndState()) {

            Location location = new Location(i, j);
            Cell cell = new Cell(location, widthOfCell, heightOfCell, GOAL_CELL, Constants.FACING_WEST);
            cell.setEndState(true);
            this.add(cell, j, i);
            cells[i][j] = cell;
            cells[i][j].setPassable(true);
            goals[0] = cells[i][j];
        }
    }

    public TruckAgent [] fillCellsSituation2() {

        for(int i = 0; i < gridSize; i++) {

            for(int j = 0; j < gridSize; j++) {

                Cell cell = new Cell(new Location(i, j), widthOfCell, heightOfCell, BLANK, Constants.FACING_WEST);
                this.add(cell, j, i);
                cells[i][j] = cell;
            }
        }

        createObstaclesSituation2();

        return createAgentsSituation2();
    }

    private TruckAgent [] createAgentsSituation2() {

        TruckAgent [] trucks = new TruckAgent[1];

        double widthOfSingleCell = width / gridSize;
        double heightOfSingleCell = height / gridSize;
        int count = 0;

        int i = 3;
        int j = 2;

        if(cells[i][j].getPassable()){
            TruckAgent truck = new TruckAgent();
            truck = truck.build(widthOfSingleCell, heightOfSingleCell, new Location(i, j), truck, count);
            agents[i][j] = truck;
            cells[i][j].getChildren().add(truck);
            cells[i][j].setVisited(true);
            cells[i][j].setAgent(truck);
            trucks[count] = truck;
            cells[i][j].setPassable(false);
        }

        return trucks;
    }

    private void createObstaclesSituation2() {

        int i = 13;
        int j = 12;

        buildSingleNonPassable(i, j);

        i = 10;
        j = 10;

        buildSingleNonPassable(i, j);

        i = 11;
        j = 12;

        buildSingleNonPassable(i, j);

        i = 12;
        j = 9;

        buildSingleNonPassable(i, j);

        i = 10;
        j = 13;

        buildSingleNonPassable(i, j);

        i = 9;
        j = 14;

        buildSingleNonPassable(i, j);

        i = 11;
        j = 7;

        buildSingleNonPassable(i, j);

        i = 9;
        j = 11;

        buildSingleNonPassable(i, j);

        i = 14;
        j = 13;

        buildSingleNonPassable(i, j);

        i = 9;
        j = 9;

        buildSingleNonPassable(i, j);

        i = 8;
        j = 8;

        buildSingleNonPassable(i, j);

        i = 13;
        j = 10;

        buildSingleNonPassable(i, j);
    }

    public void createGoalsSituation2() {

        this.goals = new Cell[1];

        int j = 13;
        int i = 11;

        if(cells[i][j].getPassable() && !cells[i][j].getEndState()) {

            Location location = new Location(i, j);
            Cell cell = new Cell(location, widthOfCell, heightOfCell, GOAL_CELL, Constants.FACING_WEST);
            cell.setEndState(true);
            this.add(cell, j, i);
            cells[i][j] = cell;
            cells[i][j].setPassable(true);
            goals[0] = cells[i][j];
        }
    }
}
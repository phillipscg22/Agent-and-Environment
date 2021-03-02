package ImprovedAgent;

import javafx.scene.layout.GridPane;

public class Environment extends GridPane {

    private double width;
    private double height;
    private TruckAgent [][] cells; //Change to grid

    public Environment(double width, double height, TruckAgent [][] cells) {

        this.width = width;
        this.height = height;
        this.cells = cells;

        this.setGridLinesVisible(true);
    }

    public void setCells(TruckAgent [][] cells) {
        this.cells = cells;
    }

    public TruckAgent [][] getCells() {

        return cells;
    }

    public TruckAgent [] fillCells(int numAgents, int numOfNonPassableCells) {

        for(int i = 0; i < cells.length; i++) {

            for(int j = 0; j < cells[i].length; j++) {

                Cell cell = new Cell(false, false, true, new Location(i, j));
                this.add(cell, j, i);
                cells[i][j] = new TruckAgent(false, true, false, new Location(i, j));
            }
        }

        fillNonPassableCells(numOfNonPassableCells);

        return fillRandomAgent(numAgents);
    }

    public TruckAgent [] fillRandomAgent(int num) {

        TruckAgent [] trucks = new TruckAgent[num];

        double widthOfSingleCell = width / cells.length;
        double heightOfSingleCell = height / cells.length;
        int count = 0;

        while(count < num) {

            int i = (int)(Math.random() * cells.length);
            int j = (int)(Math.random() * cells.length);

            if((cells[i][j] == null) || (!cells[i][j].getBuilt() && cells[i][j].getPassable())) {
                TruckAgent truck = new TruckAgent();
                truck = truck.build(widthOfSingleCell, heightOfSingleCell, new Location(i, j));
                this.add(cells[i][j] = truck, j , i);
                trucks[count] = truck;
                count++;
            }
        }

        return trucks;
    }

    public void fillNonPassableCells(int num) {

        int count = 0;

        while(count < num) {

            int j = (int)(Math.random() * cells.length);
            int i = (int)(Math.random() * cells[0].length);

            if((cells[i][j] == null) || (!cells[i][j].getBuilt() && cells[i][j].getPassable())) {

                Cell cell = new Cell(true, false, false, new Location(i, j));
                this.add(cell, j, i);
                count++;
                cells[i][j].setPassable(false);
            }
        }
    }

    public Location [] fillEndState(int num) {

        int count = 0;
        Location [] endStateLocations = new Location[num];

        while(count < num) {

            int j = (int)(Math.random() * cells.length);
            int i = (int)(Math.random() * cells[0].length);

            if((cells[i][j] == null) || (!cells[i][j].getBuilt() && cells[i][j].getPassable())) {

                Location location = new Location(i, j);
                Cell cell = new Cell(true, true, true, location);
                this.add(cell, j, i);
                cells[i][j].setPassable(true);
                endStateLocations[count] = location;
                count++;
            }
        }

        return endStateLocations;
    }
}
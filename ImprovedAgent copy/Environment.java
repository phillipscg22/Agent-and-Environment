package ImprovedAgent;

import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;

public class Environment extends GridPane {

    private double width;
    private double height;
    private int numAgents;
    private TruckAgent [][] cells; //Change to grid
    private TruckAgent [] trucks;

    public Environment(double width, double height, int numAgents, TruckAgent [][] cells) {

        this.width = width;
        this.height = height;
        this.numAgents = numAgents;
        this.cells = cells;

        this.setGridLinesVisible(true);

        this.fillCells();
        this.trucks = fillRandomAgent(numAgents);
    }

    public void setTrucks(TruckAgent[] trucks) {
        this.trucks = trucks;
    }

    public TruckAgent [] getTrucks() {
        return trucks;
    }

    public void fillCells() {

        for(int i = 0; i < cells.length; i++) {

            for(int j = 0; j < cells[i].length; j++) {

                Pane pane = new Pane();
                pane.setPrefSize(2000, 2000);
                this.add(pane, j, i);
                cells[i][j] = new TruckAgent(false, true, false, new Location(i, j));
            }
        }
    }

    public TruckAgent [] fillRandomAgent(int num) {

        trucks = new TruckAgent[num];

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

    public void moveTruckAgent(int i, int j, TruckAgent truck) {

        if((cells[i][j] == null) || (!cells[i][j].getBuilt() && cells[i][j].getPassable())) {
            this.getChildren().remove(truck);
            this.add(truck, j , i);
            truck.getLocation().setX(i);
            truck.getLocation().setY(j);
        }

    }

    public void fillNonPassableCells(int num) {

        int count = 0;

        while(count < num) {

            int j = (int)(Math.random() * cells.length);
            int i = (int)(Math.random() * cells[0].length);

            if((cells[i][j] == null) || (!cells[i][j].getBuilt() && cells[i][j].getPassable())) {

                NonPassable nonPassable = new NonPassable();
                nonPassable.fill();
                this.add(nonPassable, j, i);
                count++;
                cells[i][j].setPassable(false);
            }
        }
    }

    // Change to using Location
    public int [] fillEndState(int num) {

        int count = 0;
        int [] endStatePosition = new int[2];

        while(count < num) {

            int j = (int)(Math.random() * cells.length);
            int i = (int)(Math.random() * cells[0].length);

            if((cells[i][j] == null) || (!cells[i][j].getBuilt() && cells[i][j].getPassable())) {

                EndState endState = new EndState();
                endState.fill();
                this.add(endState, j, i);
                count++;
                cells[i][j].setPassable(true);
                endStatePosition[0] = i;
                endStatePosition[1] = j;
            }
        }

        return endStatePosition;
    }
}
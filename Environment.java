package ImprovedAgent;

import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;

public class Environment extends GridPane {

    private double width;
    private double height;
    private TruckAgent [][] cells;

    public Environment(double width, double height, TruckAgent [][] cells) {

        this.width = width;
        this.height = height;
        this.cells = cells;

        this.setGridLinesVisible(true);

        this.fillCells();
    }

    public void fillCells() {

        for(int i = 0; i < cells.length; i++) {

            for(int j = 0; j < cells[i].length; j++) {

                Pane pane = new Pane();
                pane.setPrefSize(2000, 2000);
                this.add(pane, j, i);
                TruckAgent truck = new TruckAgent();
                cells[i][j] = truck;
                cells[i][j].setPassable(true);
            }
        }
    }

    public TruckAgent [] fillRandomAgent(int num) {

        TruckAgent [] trucks = new TruckAgent[num];

        double widthOfSingleCell = width / cells.length;
        double heightOfSingleCell = height / cells.length;
        int count = 0;

        while(count < num) {

            int i = (int)(Math.random() * cells.length);
            int j = (int)(Math.random() * cells.length);

            if(cells[i][j] == null) {
                TruckAgent truck = new TruckAgent();
                truck = truck.build(widthOfSingleCell, heightOfSingleCell);
                this.add(cells[i][j] = truck, j , i);
                trucks[count] = truck;
                count++;
            }
            else if(!cells[i][j].getBuilt() && cells[i][j].getPassable()) {
                TruckAgent truck = new TruckAgent();
                truck = truck.build(widthOfSingleCell, heightOfSingleCell);
                this.add(cells[i][j] = truck, j , i);
                trucks[count] = truck;
                count++;
            }
        }

        return trucks;
    }

    public TruckAgent buildTruckAgent(int i, int j, TruckAgent [] trucks, int num) {

        int count = 0;
        double widthOfSingleCell = width / cells.length;
        double heightOfSingleCell = height / cells.length;

        while(count < num) {

            if(cells[i][j] == null) {
                TruckAgent truck = new TruckAgent();
                truck = truck.build(widthOfSingleCell, heightOfSingleCell);
                this.add(cells[i][j] = truck, j , i);
                trucks[count] = truck;
                count++;
            }
            else if(!cells[i][j].getBuilt() && cells[i][j].getPassable()) {
                TruckAgent truck = new TruckAgent();
                truck = truck.build(widthOfSingleCell, heightOfSingleCell);
                this.add(cells[i][j] = truck, j , i);
                trucks[count] = truck;
                count++;
            }
        }

        return trucks[0];
    }

    public void fillNonPassableCells(int num) {

        int count = 0;

        while(count < num) {

            int j = (int)(Math.random() * cells.length);
            int i = (int)(Math.random() * cells[0].length);

            if(cells[i][j] == null) {

                NonPassable nonPassable = new NonPassable();
                nonPassable.fill();
                this.add(nonPassable, j, i);
                count++;
                cells[i][j].setPassable(false);
            }
            else if(!cells[i][j].getBuilt() && cells[i][j].getPassable()) {

                NonPassable nonPassable = new NonPassable();
                nonPassable.fill();
                this.add(nonPassable, j, i);
                count++;
                cells[i][j].setPassable(false);
            }
        }
    }

    public int [] fillEndState(int num) {

        int count = 0;
        int [] endStatePosition = new int[2];

        while(count < num) {

            int j = (int)(Math.random() * cells.length);
            int i = (int)(Math.random() * cells[0].length);

            if(cells[i][j] == null) {

                EndState endState = new EndState();
                endState.fill();
                this.add(endState, j, i);
                count++;
                cells[i][j].setPassable(true);
                endStatePosition[0] = i;
                endStatePosition[1] = j;
            }
            else if(!cells[i][j].getBuilt() && cells[i][j].getPassable()) {

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
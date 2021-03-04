package ImprovedAgent;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

//In charge of moving agents within environment
public class MoveAgentsThreaded {

    private Environment environment;
    private TruckAgent[] agents;
    private Cell [][] cells;
    private int numOfAgents;
    private int truckCounter;

    public MoveAgentsThreaded(Environment environment, int goalCells, int numOfAgents, int numOfNonPassableCells) {

        this.environment = environment;
        agents = environment.fillAgents(numOfAgents, numOfNonPassableCells);
        this.numOfAgents = numOfAgents;
        environment.createGoals(goalCells);
        cells = environment.getCells();

        //Start threading of trucks
        runThreads();
    }

    public void runThreads() {

        while (truckCounter < numOfAgents) {

            TruckAgent truck = agents[truckCounter];

            new Thread(() -> {

                while(truck.getMovable()) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) { }

                    //Add arrow based on old direction if truck moves forward
                    int oldX = truck.getLocation().getX();
                    int oldY = truck.getLocation().getY();

                    if (truck.goForward(environment)) {
                        //Test to make sure same UUID is being moved (Yes it works)
                        System.out.println(truck.toString());
                        cells[truck.getLocation().getX()][truck.getLocation().getY()].setVisited(true);
                        Platform.runLater(() -> {
                            cells[oldX][oldY].getChildren().add(new Arrow(truck.getDirection()));
                        });
                    }

                    //Check if truck has reached location of an End State
                    for (int i = 0; i < cells.length; i++) {
                        for (int j = 0; j < cells.length; j++) {
                            if (cells[i][j].getEndState() && truck.getLocation().getX() == i && truck.getLocation().getY() == j) {
                                truck.setMovable(false);
                            }
                        }
                    }
                }
            }).start();

            truckCounter++;
        }
    }
}

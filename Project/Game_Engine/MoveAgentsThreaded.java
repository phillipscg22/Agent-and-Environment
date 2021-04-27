package Project.Game_Engine;

import Project.Infrastructure.SuccessorFunction;
import javafx.application.Platform;

//In charge of moving agents within environment
public class MoveAgentsThreaded implements Action, Percepts {

    private Environment environment;
    private TruckAgent[] agents;
    private Cell[][] cells;
    private Percept[][] perceptsOfCells;
    private int numOfAgents;
    private int truckCounter;

    public MoveAgentsThreaded(Environment environment, int goalCells, int numOfAgents, int numOfNonPassableCells) {

        this.environment = environment;
        agents = environment.fillAgents(numOfAgents, numOfNonPassableCells);
        this.numOfAgents = numOfAgents;
        environment.createGoals(goalCells);
        cells = environment.getCells();
        perceptsOfCells = environment.getPercepts();

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

                    boolean percept = SuccessorFunction.goForward(environment, truck);
                    System.out.println(percept);

                    if (percept) {
                        //Test to make sure same UUID is being moved (Yes it works)
                        System.out.println(truck.toString());
                        cells[truck.getLocation().getX()][truck.getLocation().getY()].setVisited(true);
                        Platform.runLater(() -> cells[oldX][oldY].buildArrow(truck.getDirection()));
                    }
                    else {
                        //receive non passable percept
                    }

                    //Check if truck has reached location of an End State
                    int i = truck.getLocation().getX();
                    int j = truck.getLocation().getY();

                    //If truck location is an end state stop moving the truck
                    if (cells[i][j].getPercept().equals(GOAL_CELL)) {
                        truck.setMovable(false);
                        System.out.println(GOAL_CELL);
                        //receive goal cell percept
                    }

                }
            }).start();

            truckCounter++;
        }
    }
}

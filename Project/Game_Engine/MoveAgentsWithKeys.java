package Project.Game_Engine;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

//In charge of moving agents within environment
public class MoveAgentsWithKeys implements EventHandler<KeyEvent>, Actions, Percepts {

    private Environment environment;
    private Scene scene;
    private TruckAgent[] agents;
    private Cell[][] cells;
    private int numOfAgents;
    private int truckCounter;

    public MoveAgentsWithKeys(Environment environment, Scene scene, int goalCells, int numOfAgents, int numOfNonPassableCells) {

        this.environment = environment;
        this.scene = scene;
        agents = environment.fillAgents(numOfAgents, numOfNonPassableCells);
        this.numOfAgents = numOfAgents;
        environment.createGoals(goalCells);
        cells = environment.getCells();

        //Implement handle method
        scene.setOnKeyPressed(this);
    }

    @Override
    public void handle(KeyEvent keyEvent) {

        /**
         * Implement threads for the trucks to move independently
         */
        if (agents[truckCounter].getMovable()) {
            if (keyEvent.getCode() == KeyCode.RIGHT) {

                turnRight(agents[truckCounter]);

                if (agents[truckCounter].getDirection() == 3) {
                    agents[truckCounter].flipTruck();
                }
            } else if (keyEvent.getCode() == KeyCode.LEFT) {

                turnLeft(agents[truckCounter]);

                if (agents[truckCounter].getDirection() == 3) {
                    agents[truckCounter].flipTruck();
                }
            } else if (keyEvent.getCode() == KeyCode.UP) {

                //Add arrow based on old direction if truck moves forward
                int oldX = agents[truckCounter].getLocation().getX();
                int oldY = agents[truckCounter].getLocation().getY();

                String percept = goForward(environment, agents[truckCounter]);
                System.out.println(percept);

                if(percept.equals(MOVED)) {
                    //Test to make sure same UUID is being moved (Yes it works)
                    //System.out.println(agents[truckCounter].toString());
                    cells[agents[truckCounter].getLocation().getX()][agents[truckCounter].getLocation().getY()].setVisited(true);
                    cells[oldX][oldY].buildArrow(agents[truckCounter].getDirection());
                }

                //Check if truck has reached location of an End State
                int i = agents[truckCounter].getLocation().getX();
                int j = agents[truckCounter].getLocation().getY();

                //If truck location is an end state stop moving the truck
                if (cells[i][j].getPercept().equals(GOAL_CELL)) {
                    agents[truckCounter].setMovable(false);
                    truckCounter++;
                    System.out.println(GOAL_CELL);
                    //receive goal cell percept
                }

                if(truckCounter == numOfAgents)
                    truckCounter = 0;
            }
        }
    }
}
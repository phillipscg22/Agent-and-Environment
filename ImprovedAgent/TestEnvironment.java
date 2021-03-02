package ImprovedAgent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TestEnvironment extends Application {

    protected TruckAgent[][] environmentCells = new TruckAgent[10][10];
    protected Environment environment;
    protected TruckAgent[] trucks;
    protected Location [] endStateLocations;
    protected Scene scene;
    protected double width = 750;
    protected double height = 750;
    protected Stage primaryStage = new Stage();

    protected int endStates = 3;
    protected int numOfAgents = 3;
    protected int numOfNonPassableCells = 5;

    //Keeps track of what truck is being moved
    protected int truckCounter = 0;

    @Override
    public void start(Stage stage) {

        environment = new Environment(width, height, environmentCells);
        trucks = environment.fillCells(numOfAgents, numOfNonPassableCells);
        endStateLocations = environment.fillEndState(endStates);

        scene = new Scene(environment, width, height);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Environment");
        primaryStage.show();

        //Is this worth the extra computing power it takes to implement
        for (int i = 0; i < trucks.length; i++) {
            int finalI = i;
            scene.widthProperty().addListener(e -> trucks[finalI] = trucks[finalI].refresh(scene.getWidth() / environmentCells.length,
                    scene.getHeight() / environmentCells.length, trucks[finalI].getLocation()));
            scene.heightProperty().addListener(e -> trucks[finalI] = trucks[finalI].refresh(scene.getWidth() / environmentCells.length,
                    scene.getHeight() / environmentCells.length, trucks[finalI].getLocation()));
        }

        class HandleEvent implements EventHandler<KeyEvent> {

            @Override
            public void handle(KeyEvent keyEvent) {

                if (trucks[truckCounter].getMovable()) {
                    if (keyEvent.getCode() == KeyCode.RIGHT) {

                        trucks[truckCounter].turnRight();

                        if (trucks[truckCounter].getDirection() == 3) {
                            trucks[truckCounter].flipTruck();
                        }
                    } else if (keyEvent.getCode() == KeyCode.LEFT) {

                        trucks[truckCounter].turnLeft();

                        if (trucks[truckCounter].getDirection() == 3) {
                            trucks[truckCounter].flipTruck();
                        }
                    } else if (keyEvent.getCode() == KeyCode.UP) {

                        trucks[truckCounter].goForward(environment);

                        //Check if truck has reached location of an End State
                        for(int i = 0; i < endStates; i++) {
                            if (endStateLocations[i].getX() == trucks[truckCounter].getLocation().getX() && endStateLocations[i].getY() == trucks[truckCounter].getLocation().getY()) {

                                trucks[truckCounter].setMovable(false);
                                truckCounter++;
                                //If all trucks have reached an end state then reset
                                if(truckCounter >= numOfAgents) {

                                    //To avoid error
                                    truckCounter = 0;
                                    Platform.runLater(() -> {

                                        if (ReachedEndState.display("End State", "Refresh Environment")) {

                                            //Then reset within new scene
                                            truckCounter = 0;
                                            environment = new Environment(width, height, environmentCells);
                                            trucks = environment.fillCells(numOfAgents, numOfNonPassableCells);
                                            endStateLocations = environment.fillEndState(endStates);
                                            scene = new Scene(environment, width, height);
                                            primaryStage.setScene(scene);
                                            primaryStage.show();
                                            scene.setOnKeyPressed(new HandleEvent());
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }
        }

        scene.setOnKeyPressed(new HandleEvent());
    }
}
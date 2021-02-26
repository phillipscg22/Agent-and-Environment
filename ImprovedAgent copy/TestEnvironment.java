package ImprovedAgent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TestEnvironment extends Application {

    public TruckAgent[][] environmentCells = new TruckAgent[10][10];
    public Environment environment;
    public TruckAgent[] trucks;
    public int[] endStatePosition;
    public Scene scene;
    protected double width = 750;
    protected double height = 750;
    protected Stage primaryStage = new Stage();

    @Override
    public void start(Stage stage) {

        environment = new Environment(width, height,1,  environmentCells);
        trucks = environment.getTrucks();
        environment.fillNonPassableCells(9);
        endStatePosition = environment.fillEndState(1);

        scene = new Scene(environment, width, height);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Environment");
        primaryStage.show();

        for (int i = 0; i < trucks.length; i++) {
            int finalI = i;
            scene.widthProperty().addListener(e -> trucks[finalI] = trucks[finalI].refresh(scene.getWidth() / environmentCells.length,
                    scene.getHeight() / environmentCells.length, trucks[finalI].getLocation()));
            scene.heightProperty().addListener(e -> trucks[finalI] = trucks[finalI].refresh(scene.getWidth() / environmentCells.length,
                    scene.getHeight() / environmentCells.length, trucks[finalI].getLocation()));
        }

        /**
         * Change moving forward to keep the same agent and update the location when moving forward
         */

        class HandleEvent implements EventHandler<KeyEvent> {

            @Override
            public void handle(KeyEvent keyEvent) {

                if (trucks[0].getMovable()) {
                    if (keyEvent.getCode() == KeyCode.RIGHT) {

                        trucks[0].turnRight();

                        if (trucks[0].getDirection() == 3) {
                            trucks[0].flipTruck();
                        }
                    } else if (keyEvent.getCode() == KeyCode.LEFT) {

                        trucks[0].turnLeft();

                        if (trucks[0].getDirection() == 3) {
                            trucks[0].flipTruck();
                        }
                    } else if (keyEvent.getCode() == KeyCode.UP) {

                        if (trucks[0].getMovable()) {

                            if (trucks[0].getDirection() == 0) {

                                if (environmentCells[trucks[0].getLocation().getX() + 1][trucks[0].getLocation().getY()].getPassable()) {
                                    environmentCells[trucks[0].getLocation().getX() + 1][trucks[0].getLocation().getY()].clear();
                                    environment.moveTruckAgent(trucks[0].getLocation().getX() + 1, trucks[0].getLocation().getY(), trucks[0]);
                                }
                            } else if (trucks[0].getDirection() == 1) {

                                if (environmentCells[trucks[0].getLocation().getX()][trucks[0].getLocation().getY() - 1].getPassable()) {
                                    environmentCells[trucks[0].getLocation().getX()][trucks[0].getLocation().getY() - 1].clear();
                                    environment.moveTruckAgent(trucks[0].getLocation().getX(), trucks[0].getLocation().getY() - 1, trucks[0]);
                                }
                            } else if (trucks[0].getDirection() == 2) {

                                if (environmentCells[trucks[0].getLocation().getX() - 1][trucks[0].getLocation().getY()].getPassable()) {
                                    environmentCells[trucks[0].getLocation().getX() - 1][trucks[0].getLocation().getY()].clear();
                                    environment.moveTruckAgent(trucks[0].getLocation().getX() - 1, trucks[0].getLocation().getY(), trucks[0]);
                                }
                            } else if (trucks[0].getDirection() == 3) {

                                if (environmentCells[trucks[0].getLocation().getX()][trucks[0].getLocation().getY() + 1].getPassable()) {
                                    environmentCells[trucks[0].getLocation().getX()][trucks[0].getLocation().getY() + 1].clear();
                                    environment.moveTruckAgent(trucks[0].getLocation().getX(), trucks[0].getLocation().getY() + 1, trucks[0]);
                                }
                            }

                            if (endStatePosition[0] == trucks[0].getLocation().getX() && endStatePosition[1] == trucks[0].getLocation().getY()) {

                                trucks[0].setMovable(false);
                                Platform.runLater(() -> {

                                    if (ReachedEndState.display("End State", "Refresh Environment")) {

                                        environment = new Environment(width, height,1,  environmentCells);
                                        trucks = environment.getTrucks();
                                        environment.fillNonPassableCells(9);
                                        endStatePosition = environment.fillEndState(1);
                                        scene = new Scene(environment, width, height);
                                        primaryStage.setScene(scene);
                                        scene.setOnKeyPressed(new HandleEvent());
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }

        scene.setOnKeyPressed(new HandleEvent());
    }
}
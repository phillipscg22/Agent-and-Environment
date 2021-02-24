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
    public int[] truckPosition;
    public int[] endStatePosition;
    public Scene scene;
    protected double width = 750;
    protected double height = 750;
    protected Stage primaryStage = new Stage();

    @Override
    public void start(Stage stage) {

        environment = new Environment(width, height, environmentCells);
        environment.fillCells();
        trucks = environment.fillRandomAgent(1);
        environment.fillNonPassableCells(9);
        endStatePosition = environment.fillEndState(1);
        truckPosition = trucks[0].getTruckPosition(environmentCells);

        scene = new Scene(environment, width, height);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Environment");
        primaryStage.show();

        for (int i = 0; i < trucks.length; i++) {
            int finalI = i;
            scene.widthProperty().addListener(e -> trucks[finalI] = trucks[finalI].refresh(scene.getWidth() / environmentCells.length,
                    scene.getHeight() / environmentCells.length));
            scene.heightProperty().addListener(e -> trucks[finalI] = trucks[finalI].refresh(scene.getWidth() / environmentCells.length,
                    scene.getHeight() / environmentCells.length));
        }

        class HandleEvent implements EventHandler<KeyEvent> {

            @Override
            public void handle(KeyEvent keyEvent) {

                if (trucks[0].getMovable()) {
                    if (keyEvent.getCode() == KeyCode.RIGHT) {

                        trucks[0].turnRight();

                        if (trucks[0].getDirection() == 3) {
                            environmentCells[truckPosition[0]][truckPosition[1]].clear();
                            trucks[0] = environment.buildTruckAgent(truckPosition[0], truckPosition[1], trucks, 1);
                            trucks[0].flipTruck();
                        } else if (trucks[0].getDirection() == 1) {
                            environmentCells[truckPosition[0]][truckPosition[1]].clear();
                            trucks[0] = environment.buildTruckAgent(truckPosition[0], truckPosition[1], trucks, 1);
                        } else if (trucks[0].getDirection() == 0) {
                            environmentCells[truckPosition[0]][truckPosition[1]].clear();
                            trucks[0] = environment.buildTruckAgent(truckPosition[0], truckPosition[1], trucks, 1);
                            trucks[0].turnLeft();
                        }
                    } else if (keyEvent.getCode() == KeyCode.LEFT) {

                        trucks[0].turnLeft();

                        if (trucks[0].getDirection() == 3) {
                            environmentCells[truckPosition[0]][truckPosition[1]].clear();
                            trucks[0] = environment.buildTruckAgent(truckPosition[0], truckPosition[1], trucks, 1);
                            trucks[0].flipTruck();
                        } else if (trucks[0].getDirection() == 1) {
                            environmentCells[truckPosition[0]][truckPosition[1]].clear();
                            trucks[0] = environment.buildTruckAgent(truckPosition[0], truckPosition[1], trucks, 1);
                        } else if (trucks[0].getDirection() == 2) {
                            environmentCells[truckPosition[0]][truckPosition[1]].clear();
                            trucks[0] = environment.buildTruckAgent(truckPosition[0], truckPosition[1], trucks, 1);
                            trucks[0].turnRight();
                        }
                    } else if (keyEvent.getCode() == KeyCode.UP) {

                        if (trucks[0].getMovable()) {

                            if (trucks[0].getDirection() == 0) {

                                if (environmentCells[truckPosition[0] + 1][truckPosition[1]].getPassable()) {
                                    environmentCells[truckPosition[0]++][truckPosition[1]].clear();
                                    trucks[0] = environment.buildTruckAgent(truckPosition[0], truckPosition[1], trucks, 1);
                                    trucks[0].turnLeft();
                                    trucks[0].setDirection(0);
                                }
                            } else if (trucks[0].getDirection() == 1) {

                                if (environmentCells[truckPosition[0]][truckPosition[1] - 1].getPassable()) {
                                    environmentCells[truckPosition[0]][truckPosition[1]--].clear();
                                    trucks[0] = environment.buildTruckAgent(truckPosition[0], truckPosition[1], trucks, 1);
                                }
                            } else if (trucks[0].getDirection() == 2) {

                                if (environmentCells[truckPosition[0] - 1][truckPosition[1]].getPassable()) {
                                    environmentCells[truckPosition[0]--][truckPosition[1]].clear();
                                    trucks[0] = environment.buildTruckAgent(truckPosition[0], truckPosition[1], trucks, 1);
                                    trucks[0].turnRight();
                                    trucks[0].setDirection(2);
                                }
                            } else if (trucks[0].getDirection() == 3) {

                                if (environmentCells[truckPosition[0]][truckPosition[1] + 1].getPassable()) {
                                    environmentCells[truckPosition[0]][truckPosition[1]++].clear();
                                    trucks[0] = environment.buildTruckAgent(truckPosition[0], truckPosition[1], trucks, 1);
                                    trucks[0].flipTruck();
                                }
                            }

                            if (endStatePosition[0] == truckPosition[0] && endStatePosition[1] == truckPosition[1]) {

                                trucks[0].setMovable(false);
                                Platform.runLater(() -> {

                                    if (ReachedEndState.display("End State", "Refresh Environment")) {

                                        environment = new Environment(width, height, environmentCells);
                                        environment.fillCells();
                                        trucks = environment.fillRandomAgent(1);
                                        environment.fillNonPassableCells(9);
                                        endStatePosition = environment.fillEndState(1);
                                        truckPosition = trucks[0].getTruckPosition(environmentCells);
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
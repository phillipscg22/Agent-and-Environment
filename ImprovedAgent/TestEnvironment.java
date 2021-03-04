package ImprovedAgent;

import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;

public class TestEnvironment extends Application {

    protected TruckAgent[][] environmentCells = new TruckAgent[10][10];
    protected Environment environment;
    protected Cell [][] cells = new Cell[10][10];
    protected Scene scene;
    protected double width = 750;
    protected double height = 750;
    protected Stage primaryStage = new Stage();

    protected int goalCell = 25;
    protected int numOfAgents = 1;
    protected int numOfNonPassableCells = 5;

    @Override
    public void start(Stage stage) {

        environment = new Environment(width, height, environmentCells, cells);
        scene = new Scene(environment, width, height);

        //Control agents individually with arrow keys
        //new MoveAgentsWithKeys(environment, scene, goalCell, numOfAgents, numOfNonPassableCells);

        //Trucks controlled by threads to move forward until end state or no more places to go
        new MoveAgentsThreaded(environment, goalCell, numOfAgents, numOfNonPassableCells);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Environment");
        primaryStage.show();
    }
}
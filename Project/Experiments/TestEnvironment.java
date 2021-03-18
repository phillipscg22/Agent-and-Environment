package Project.Experiments;

import Project.Game_Engine.Cell;
import Project.Game_Engine.Environment;
import Project.Game_Engine.MoveAgentsThreaded;
import Project.Game_Engine.MoveAgentsWithKeys;
import Project.Game_Engine.TruckAgent;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;

public class TestEnvironment extends Application {

    protected Environment environment;
    protected Scene scene;
    protected double width = 750;
    protected double height = 750;
    protected boolean multiAgent = false;
    protected boolean deterministic = true;
    protected int gridSize = 25;
    protected int maxStep = -1;
    protected long maxTime = -1;
    protected String searchType = null;

    protected Stage primaryStage = new Stage();

    protected int goalCell = 5;
    protected int numOfAgents = 1;
    protected int numOfNonPassableCells = 5;

    @Override
    public void start(Stage stage) {

        environment = new Environment(width, height, multiAgent, deterministic, gridSize, maxStep, maxTime, searchType);
        scene = new Scene(environment, width, height);

        //Control agents individually with arrow keys
        new MoveAgentsWithKeys(environment, scene, goalCell, numOfAgents, numOfNonPassableCells);

        //Trucks controlled by threads to move forward until end state or no more places to go
       // new MoveAgentsThreaded(environment, goalCell, numOfAgents, numOfNonPassableCells);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Environment");
        primaryStage.show();
    }
}
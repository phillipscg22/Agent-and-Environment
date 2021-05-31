package Project.Experiments;

import Project.Game_Engine.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;

public class TestEnvironment extends Application implements Constants {

    protected Environment environment;
    protected Scene scene;
    protected double width = 750;
    protected double height = 750;
    protected boolean multiAgent = false;
    protected boolean deterministic = true;
    protected int gridSize = 10;
    protected int maxStep = -1;
    protected long maxTime = -1;
    protected int depthLimit = 10;
    protected String searchType = UNIFORMED_COST_SEARCH;

    protected Stage primaryStage = new Stage();

    protected int goalCell = 3;
    protected int numOfAgents = 2;
    protected int numOfNonPassableCells = 5;

    @Override
    public void start(Stage stage) {

        environment = new Environment(width, height, multiAgent, deterministic, gridSize, maxStep, maxTime);
        scene = new Scene(environment, width, height);

        //Control agents individually with arrow keys
        //new MoveAgentsWithKeys(environment, scene, goalCell, numOfAgents, numOfNonPassableCells);

        //Move agents based on search type
        new MoveAgents(environment, goalCell, numOfAgents, numOfNonPassableCells, searchType, depthLimit);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Environment");
        primaryStage.setX(500);
        primaryStage.setY(50);
        primaryStage.show();
    }
}
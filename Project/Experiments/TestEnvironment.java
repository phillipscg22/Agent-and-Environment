package Project.Experiments;

import Project.Experiments.FixedEnviornments.MoveAgentsSituation1;
import Project.Game_Engine.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;

public class TestEnvironment extends Application implements Constants {

    protected Stage primaryStage = new Stage();
    protected Scene scene;

    protected Environment environment;
    protected double width = 750;
    protected double height = 750;
    protected boolean deterministic = true;
    protected int gridSize = 10;

    protected long maxTime = -1;   //Use -1 for unlimited time
    protected int maxStep = 10;
    protected int numOfGoals = 1;
    protected int numOfAgents = 1;
    protected int numOfNonPassableCells = 0;
    protected String searchType = BREADTH_FIRST_SEARCH;

    @Override
    public void start(Stage stage) {

        environment = new Environment(width, height, gridSize, numOfGoals, numOfAgents, numOfNonPassableCells);
        scene = new Scene(environment, width, height);

        //Move agents based on search type
        new MoveAgentsSituation1(environment, searchType, maxStep, maxTime);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Environment");
        primaryStage.setX(500);
        primaryStage.setY(50);
        primaryStage.show();
    }
}
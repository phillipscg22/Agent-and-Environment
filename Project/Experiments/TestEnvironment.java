package Project.Experiments;

import Project.Experiments.SeedGeneration.SeedGenerator;
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

    protected long maxTime = -1;   //Use -1 for unlimited time
    protected int maxStep = 150;

    protected boolean deterministic = true;

    protected int gridSize = 10;

    protected int numOfGoals = 1;
    protected int numOfAgents = 1;
    protected int numOfNonPassableCells = 1;

    protected String searchType = BREADTH_FIRST_SEARCH;

    private int seed = 1;

    @Override
    public void start(Stage stage) {

        //Make environment with given seed
        if(seed > 0 && seed < 10) {
            environment = new Environment(width, height, gridSize, numOfAgents, numOfGoals);
            SeedGenerator generator = new SeedGenerator(searchType, environment, maxStep, maxTime);
            generator.buildSeed(seed);
        }
        //Random environment (no seed yet)
        else {
            environment = new Environment(width, height, gridSize, numOfGoals, numOfAgents, numOfNonPassableCells);
            new MoveAgents(environment, searchType, maxStep, maxTime);
        }

        scene = new Scene(environment, width, height);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Environment");
        primaryStage.setX(500);
        primaryStage.setY(50);
        primaryStage.show();
    }
}
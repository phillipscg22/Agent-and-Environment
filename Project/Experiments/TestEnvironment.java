package Project.Experiments;

import Project.Algorithms.Classical_Search.BreadthFirstSearch;
import Project.Game_Engine.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import Project.Infrastructure.Node;

public class TestEnvironment extends Application {

    protected Environment environment;
    protected Scene scene;
    protected double width = 750;
    protected double height = 750;
    protected boolean multiAgent = false;
    protected boolean deterministic = true;
    protected int gridSize = 5;
    protected int maxStep = -1;
    protected long maxTime = -1;
    protected String searchType = null;

    protected Stage primaryStage = new Stage();

    protected int goalCell = 1;
    protected int numOfAgents = 1;
    protected int numOfNonPassableCells = 4;

    @Override
    public void start(Stage stage) throws CloneNotSupportedException {

        environment = new Environment(width, height, multiAgent, deterministic, gridSize, maxStep, maxTime, searchType);
        scene = new Scene(environment, width, height);

        //Control agents individually with arrow keys
        //new MoveAgentsWithKeys(environment, scene, goalCell, numOfAgents, numOfNonPassableCells);

        //Breadth First Search
        //new MoveAgentWithBreadthFirstSearch(environment, goalCell, numOfAgents, numOfNonPassableCells);

        //Depth First Search
        new MoveAgentsWithDepthFirstSearch(environment, goalCell, numOfAgents, numOfNonPassableCells);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Environment");
        primaryStage.show();
    }
}
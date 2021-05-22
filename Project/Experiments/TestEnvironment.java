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

    protected int goalCell = 5;
    protected int numOfAgents = 1;
    protected int numOfNonPassableCells = 0;

    @Override
    public void start(Stage stage) {

        environment = new Environment(width, height, multiAgent, deterministic, gridSize, maxStep, maxTime, searchType);
        scene = new Scene(environment, width, height);

        //Control agents individually with arrow keys
        new MoveAgentsWithKeys(environment, scene, goalCell, numOfAgents, numOfNonPassableCells);

        //Trucks controlled by threads to move forward until end state or no more places to go
        //new MoveAgentsThreaded(environment, goalCell, numOfAgents, numOfNonPassableCells);

        Cell cell = new Cell(new Location(0, 0), 75, 75, "null", 1);
        for(int i = 0; i < gridSize; i++)
            for(int j = 0; j < gridSize; j++)
                if(environment.getCells()[i][j].getAgent() != null)
                    cell = environment.getCells()[i][j];

        System.out.println("x : " + cell.getLocation().getX());
        System.out.println("y : " + cell.getLocation().getY());

        Node node = new Node(cell);
        BreadthFirstSearch bfs = new BreadthFirstSearch(environment);

        Node goalNode = bfs.search(node);
        if(goalNode == null)
            System.out.println("Null goal");
        else
            System.out.println(goalNode.getState().getPercept());


        primaryStage.setScene(scene);
        primaryStage.setTitle("Environment");
        primaryStage.show();
    }
}
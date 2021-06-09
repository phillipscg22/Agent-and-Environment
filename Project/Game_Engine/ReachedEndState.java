package Project.Game_Engine;

import Project.Infrastructure.Node;
import Project.Infrastructure.SuccessorFunction;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class ReachedEndState {

    public static void display(int number, String title, Node rootNode, Node goalNode, Environment environment) {

        Stage window = new Stage();
        window.setTitle(title);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int count = 0;
        gridPane.add(new Text(rootNode.getAction()), count++, 0);
        for (int i = 0; i < goalNode.getActionsList().size(); i++) {
            gridPane.add(new Text(goalNode.getActionsList().get(i)), count++, 0);
        }

        count = 0;
        gridPane.add(new Text(rootNode.getState().getLocation().toString()), count++, 1);
        for (int i = 0; i < goalNode.getActionsList().size(); i++) {

            gridPane.add(new Text(Objects.requireNonNull(SuccessorFunction.successorFunction
                    (rootNode, goalNode.getActionsList().get(i), environment)).getState().getLocation().toString()), count++, 1);
        }

        double cost = 0;
        count = 0;
        gridPane.add(new Text(rootNode.getPathCost() + ""), count++, 2);
        for (int i = 0; i < goalNode.getActionsList().size(); i++) {

            cost += Action.getStepCost(goalNode.getActionsList().get(i));
            gridPane.add(new Text(cost + ""), count++, 2);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(scrollPane);

        Scene scene = new Scene(stackPane, 500, 150);
        window.setScene(scene);
        window.setX(0);
        window.setY(number * 200);
        window.show();
    }

    public static void noGoalFound(int number, String title, Node finalNode) {

        Stage window = new Stage();
        window.setTitle(title);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(new Text("Agent " + number + " did not find a goal, they are located at " + finalNode.getState().getLocation().toString()));

        Scene scene = new Scene(stackPane, 500, 150);
        window.setScene(scene);
        window.setX(0);
        window.setY(number * 200);
        window.show();
    }
}

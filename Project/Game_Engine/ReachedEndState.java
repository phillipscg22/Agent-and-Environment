package Project.Game_Engine;

import Project.Infrastructure.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

public class ReachedEndState {

    public static boolean display(String title, Node goalNode) {

        Stage window = new Stage();
        window.setTitle(title);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int count = 0;
        for(int i = 0; i < goalNode.getActionsList().size(); i++) {
            gridPane.add(new Text(goalNode.getActionsList().get(i)), count++, 0);
        }


//        count = 0;
//        for(int i = 0; i < goalNode.getLocationList().size(); i++) {
//
//            gridPane.add(new Text(goalNode.getLocationList().get(i).toString()), count++, 1);
//        }
       // gridPane.add(new Text(goalNode.getState().getLocation().toString()), goalNode.getActionsList().size() - 1, 1);

        double cost = 0;
        count = 0;
        for(int i = 0; i < goalNode.getActionsList().size(); i++) {

            cost += Action.getStepCost(goalNode.getActionsList().get(i));
            gridPane.add(new Text(cost + ""), count++, 2);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(scrollPane);

       // Button button = new Button("test");

        Scene scene = new Scene(stackPane, 500, 150);
        window.setScene(scene);
        window.setX(0);
        window.setY(0);
        window.show();

//        AtomicBoolean flag = new AtomicBoolean(false);
//        button.setOnAction(e -> {
//            flag.set(true);
//        });

        return false;
    }
}

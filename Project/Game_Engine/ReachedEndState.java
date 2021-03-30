package Project.Game_Engine;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReachedEndState {

    public static boolean display(String title, String message) {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Button button = new Button(message);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(button);

        button.setOnAction(e -> window.close());

        Scene scene = new Scene(stackPane);
        window.setScene(scene);
        window.showAndWait();

        while(true) {

            if(!window.isShowing())
                return true;
        }
    }
}

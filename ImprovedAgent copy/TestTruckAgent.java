package ImprovedAgent;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TestTruckAgent extends Application {

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 500);

        TruckAgent truck = new TruckAgent();
        //truck.build(scene.getWidth(), scene.getHeight());

        //truck.turnRight();
        
        root.getChildren().add(truck);
        primaryStage.setScene(scene);
        primaryStage.show();

        //scene.heightProperty().addListener(e -> truck.refresh(scene.getWidth(), scene.getHeight()));

        //scene.widthProperty().addListener(e -> truck.refresh(scene.getWidth(), scene.getHeight()));
    }
}

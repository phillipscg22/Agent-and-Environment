package ImprovedAgent;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class NonPassable extends Pane {

    private boolean filled;

    public NonPassable() {

        filled = false;
    }

    public void fill() {

        filled = true;
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void setFilled(boolean filled) {

        this.filled = filled;
    }

    public boolean getFilled() {

        return filled;
    }
}

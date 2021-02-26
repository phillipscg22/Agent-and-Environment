package ImprovedAgent;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Try to combine nonpassable and reached endstate with this class
 */

public class EndState extends Pane {

    private boolean filled;

    public EndState() {

        filled = false;
    }

    public void fill() {

        filled = true;
        this.setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void setFilled(boolean filled) {

        this.filled = filled;
    }

    public boolean getFilled() {

        return filled;
    }
}

package ImprovedAgent;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Cell extends Pane {

    private boolean filled;
    private boolean endState;
    private boolean passable;
    private Location location;

    public Cell(boolean filled, boolean endState, boolean passable, Location location) {

        this.filled = filled;
        this.endState = endState;
        this.passable = passable;
        this.location = location;
        this.setPrefSize(2000, 2000);
        fillCell();
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public boolean getFilled() {
        return filled;
    }

    public void setEndState(boolean endState) {
        this.endState = endState;
    }

    public boolean getEndState() {
        return endState;
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    public boolean getPassable() {
        return passable;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void fillCell() {

        if(!passable) {
            filled = true;
            this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        else if(endState) {
            filled = true;
            this.setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }
}
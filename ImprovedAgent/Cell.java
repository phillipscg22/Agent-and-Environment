package ImprovedAgent;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;

public class Cell extends StackPane {

    private boolean filled;
    private boolean endState;
    private boolean passable;
    private Location location;
    private boolean goalCell;
    private boolean visited;
    private double width;
    private double height;

    public Cell(boolean filled, boolean endState, boolean passable, Location location, double width, double height) {

        this.filled = filled;
        this.endState = endState;
        this.passable = passable;
        this.location = location;
        goalCell = false;
        visited = false;
        this.width = width;
        this.height = height;
        setPrefWidth(width);
        setPrefHeight(height);
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

    public void setGoal(boolean goalCell) {
        this.goalCell = goalCell;
    }

    public boolean getGoal() {
        return goalCell;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean getVisited() {
        return visited;
    }

    public void fillCell() {

        if(!passable) {
            filled = true;
            this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        else if(endState) {
            filled = true;
            buildEndState();
        }
    }

    public void buildEndState() {

        Circle outerCircle = new Circle(width / 3);
        outerCircle.setFill(Color.GOLD);
        outerCircle.setStroke(Color.YELLOW);
        Circle innerCircle = new Circle((width / 3) - (width / 25));
        innerCircle.setFill(Color.GOLD);
        innerCircle.setStroke(Color.YELLOW);

//        CubicCurve moneySign = new CubicCurve();
//        moneySign.setFill(null);
//        moneySign.setStroke(Color.BLACK);
//        moneySign.setStartX(40);
//        moneySign.setStartY(20);
//        moneySign.setEndX(20);
//        moneySign.setEndY(40);
//        moneySign.setControlX1(0);
//        moneySign.setControlY1(15);
//        moneySign.setControlX2(60);
//        moneySign.setControlY2(45);

        Label moneySign = new Label("$");
        moneySign.setFont(new Font(36));
        moneySign.setTextFill(Color.YELLOW);

        getChildren().addAll(outerCircle, innerCircle, moneySign);
    }
}
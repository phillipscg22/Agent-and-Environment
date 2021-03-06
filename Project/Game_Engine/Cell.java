package Project.Game_Engine;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;

public class Cell extends StackPane implements Constants {

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

        Label moneySign = new Label("$");
        moneySign.setFont(new Font(36));
        moneySign.setTextFill(Color.YELLOW);

        getChildren().addAll(outerCircle, innerCircle, moneySign);
    }

    public void buildArrow(int direction) {

        Polyline arrow = new Polyline();
        arrow.getPoints().addAll(37.5, 25.0, 25.0, 37.5, 37.5, 50.0);
        if(direction == FACING_NORTH)
            setRotate(90);
        else if(direction == FACING_EAST)
            setRotate(180);
        else if(direction == FACING_SOUTH)
            setRotate(-90);

        getChildren().add(arrow);
    }
}
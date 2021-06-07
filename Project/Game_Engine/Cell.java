package Project.Game_Engine;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;

public class Cell extends StackPane implements Cloneable, Constants {

    private boolean filled;
    private boolean endState;
    private boolean passable;
    private Location location;
    private boolean visited;
    private double width;
    private double height;
    private String percept;
    private TruckAgent agent;
    private int direction;
    private boolean goalFound;

    public Cell(Location location, double width, double height, String percept, int direction) {

        filled = true;
        endState = false;
        passable = true;
        this.location = location;
        visited = false;
        this.width = width;
        this.height = height;
        this.percept = percept;
        agent = null;
        this.direction = direction;
        setPrefWidth(width);
        setPrefHeight(height);
        fillCell();
    }

    public int getDirection() {

        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public boolean getFilled() {
        return filled;
    }

    public void setEndState(boolean endState) {
        this.endState = endState;
        fillCell();
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

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean getVisited() {
        return visited;
    }

    public void setPercept(String percept) {

        this.percept = percept;
    }

    public String getPercept() {
        return percept;
    }

    public void setAgent(TruckAgent agent) {

        this.agent = agent;
    }

    public TruckAgent getAgent() {

        return agent;
    }

    public void setGoalFound(boolean goalFound) {
        this.goalFound = goalFound;
    }

    public boolean getGoalFound() {

        return goalFound;
    }

    public void fillCell() {

        if(!passable) {
            filled = true;
            this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            percept = NON_PASSABLE;
        }
        else if(endState) {
            filled = true;
            buildEndState();
            percept = GOAL_CELL;
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
        moneySign.setFont(new Font(width / 3));
        moneySign.setTextFill(Color.YELLOW);

        getChildren().addAll(outerCircle, innerCircle, moneySign);
    }

    public void buildArrow(int direction) {

        Polyline arrow = new Polyline();
        arrow.getPoints().addAll(37.5, 25.0, 25.0, 37.5, 37.5, 50.0);
        if(direction == FACING_NORTH)
            arrow.setRotate(90);
        else if(direction == FACING_EAST)
            arrow.setRotate(180);
        else if(direction == FACING_SOUTH)
            arrow.setRotate(-90);

        getChildren().add(arrow);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        //do a shallow copy
        Cell cellClone = (Cell) super.clone();

        //then do a deep copy
        cellClone.location = (Location) location.clone();
        cellClone.agent = (TruckAgent) agent.clone();

        return cellClone;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null) return false;

        if ( this == o ) return true; //check for self-comparison

        if ( !(o instanceof Cell) ) return false;

        Cell in = (Cell)o;

        //compare the data members
        if(this.filled == in.filled && this.endState == in.endState &&
                this.passable == in.passable && this.location.equals(in.location) &&
                this.visited == in.visited &&
                this.width == in.width && this.height == in.height && this.direction == in.direction &&
                this.percept.equals(in.percept))// && this.agent.getId().equals(in.agent.getId()))
            return true;

        return false;
    }

    @Override
    public String toString() {

        return "filled: " + filled + ", endState: " + endState + ", passable: " + passable + ", location: " + location.toString()
                + ", visited: " + visited + ", direction: " + direction;
    }
}
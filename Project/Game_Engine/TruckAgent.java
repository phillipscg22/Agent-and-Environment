package Project.Game_Engine;

import java.util.UUID;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;

public class TruckAgent extends Group implements Constants {

    private UUID agentId;
    private boolean built;
    private boolean passable; //Property of environment not agent
    private boolean movable;
    private int direction;
    private Location location;

    public TruckAgent() {

        agentId = UUID.randomUUID();
        built = false;
        movable = false;
        direction = FACING_WEST;
        passable = true;
        location = new Location();
    }

    public TruckAgent(boolean built, boolean passable, boolean movable, Location location) {

        this.agentId = UUID.randomUUID();
        this.built = built;
        this.movable = movable;
        direction = FACING_WEST;
        this.passable = passable;
        this.location = location;
    }

    //getter and setter for uuid
    public void setUUID(UUID agentId) {
        this.agentId = agentId;
    }

    public UUID getUUID() {
        return agentId;
    }

    public void setBuilt(boolean built) {

        this.built = built;
    }

    public boolean getBuilt() {

        return built;
    }

    public void setPassable(boolean passable) {

        this.passable = passable;
    }

    public boolean getPassable() {

        return passable;
    }

    public void setMovable(boolean movable) {

        this.movable = movable;
    }

    public boolean getMovable() {

        return movable;
    }

    public void setDirection(int direction) {

        this.direction = direction;
    }

    public int getDirection() {

        return direction;
    }

    public void setLocation(Location location) {

        this.location = location;
    }

    public Location getLocation() {

        return location;
    }

    public boolean goForward(Environment environment) {

        //Check current direction
        int shiftXValue = 0;
        int shiftYValue = 0;

        //Get shift value based on truck's direction

        switch(getDirection()) {

            case FACING_SOUTH : shiftXValue = MOVING_SOUTH; break;
            case FACING_WEST : shiftYValue = MOVING_WEST; break;
            case MOVING_NORTH : shiftXValue = MOVING_NORTH; break;
            default: shiftYValue = MOVING_EAST;
        }

        //Old location of truck
        int oldX = getLocation().getX();
        int oldY = getLocation().getY();

        //Change location of truck
        int i = getLocation().getX() + shiftXValue;
        int j = getLocation().getY() + shiftYValue;

        //Check if the truck would be moving outside the grid
        if(i < 0 || i >= environment.getAgents().length || j < 0 || j >= environment.getAgents().length)
            return false;

        //Go forward based on shift value
        if(!environment.getAgents()[i][j].getBuilt() && environment.getAgents()[i][j].getPassable()
                && !environment.getCells()[i][j].getVisited()) {

            //Add truck to new location within cells
            environment.getAgents()[i][j] = this;
            environment.getAgents()[oldX][oldY].setPassable(true);
            environment.getAgents()[oldX][oldY].setBuilt(false);
            //Remove truck from old view
            environment.getChildren().remove(this);
            //Move truck to new location
            Platform.runLater(() -> {
                environment.getCells()[i][j].getChildren().add(this);
            });

            environment.getCells()[oldX][oldY].setVisited(true);
            //Set new location of truck
            getLocation().setX(i);
            getLocation().setY(j);
            return true;
        }

        return false;
    }

    public void turnRight() {

        setDirection(Math.abs((this.getDirection() + 1) % 4));
        this.setRotationAxis(Rotate.Z_AXIS);
        this.setRotate(getRotate() + 90);
    }

    public void turnLeft() {

        setDirection(Math.abs((this.getDirection() - 1 + 4) % 4));
        this.setRotationAxis(Rotate.Z_AXIS);
        this.setRotate(getRotate() - 90);
    }

    public void flipTruck() {

        this.setRotationAxis(Rotate.Y_AXIS);
        this.setRotate(180);
        setDirection(FACING_EAST);
    }

    public TruckAgent refresh(double width, double height, Location location) {

        return this.build(width, height, location);
    }

    public void clear() {

        this.getChildren().clear();
        this.setBuilt(false);
        this.setPassable(true);
        this.setMovable(false);
    }

    //Test when moving to make sure it is the correct truck
    public String toString() {

        return "Agent ID: " + getUUID() + ", x = " + getLocation().getX() + ", y = " + getLocation().getY();
    }
}
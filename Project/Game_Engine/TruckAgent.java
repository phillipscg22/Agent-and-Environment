package Project.Game_Engine;

import java.util.UUID;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;

public class TruckAgent extends Group implements Cloneable, Constants {

    private UUID agentId;
    private boolean movable;
    private int direction;
    private Location location;

    public TruckAgent() {

//        agentId = UUID.randomUUID();
        movable = false;
        direction = FACING_WEST;
        location = new Location();
    }

//    public TruckAgent(boolean movable, Location location) {
//
//        this.agentId = UUID.randomUUID();
//        this.movable = movable;
//        direction = FACING_WEST;
//        this.location = location;
//    }

    //getter and setter for uuid
    public void setUUID(UUID agentId) {
        this.agentId = agentId;
    }

    public UUID getUUID() {
        return agentId;
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

    public void flipTruck() {

        this.setRotationAxis(Rotate.Y_AXIS);
        this.setRotate(180);
        setDirection(FACING_EAST);
    }

//    public TruckAgent refresh(double width, double height, Location location) {
//
//        return this.build(width, height, location);
//    }

//    public void clear() {
//
//        this.getChildren().clear();
//        this.setMovable(false);
//    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        //do a shallow copy
        TruckAgent truckAgentClone = (TruckAgent) super.clone();

        //now do a deep copy
        truckAgentClone.location = (Location) location.clone();

        return truckAgentClone;
    }

    //Test when moving to make sure it is the correct truck
    @Override
    public String toString() {

        return "Agent ID: " + getUUID() + ", x = " + getLocation().getX() + ", y = " + getLocation().getY();
    }
}
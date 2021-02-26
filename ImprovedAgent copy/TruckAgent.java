package ImprovedAgent;

import java.util.UUID;
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

        //agentId =
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

    /**
     * Implement go forward method
     */

    public void goForward() {

        //Check current direction
        //Go forward
        //Update location
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
}
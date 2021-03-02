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

    public void goForward(Environment environment) {

        //Check current direction
        int shiftXValue = 0;
        int shiftYValue = 0;

        //Get shift value based on truck's direction
        if(this.getDirection() == FACING_SOUTH)
            shiftXValue = MOVING_SOUTH;
        else if(this.getDirection() == FACING_WEST)
            shiftYValue = MOVING_WEST;
        else if(this.getDirection() == FACING_NORTH)
            shiftXValue = MOVING_NORTH;
        else
            shiftYValue = MOVING_EAST;

        //Change location of truck
        int i = this.getLocation().getX() + shiftXValue;
        int j = this.getLocation().getY() + shiftYValue;

        //Check if the truck would be moving outside the grid
        if(i < 0 || i >= environment.getCells().length || j < 0 || j >= environment.getCells().length)
            return;

        //Go forward based on shift value
        if((environment.getCells()[i][j] == null) || (!environment.getCells()[i][j].getBuilt() && environment.getCells()[i][j].getPassable())) {

            //Move truck within cells array
            environment.getCells()[this.getLocation().getX()][this.getLocation().getY()] =
                    new TruckAgent(false, true, false,
                            new Location(this.getLocation().getX(), this.getLocation().getY()));
            //Clear new location of truck
            environment.getCells()[i][j].clear();
            //Add truck to new location within cells
            environment.getCells()[i][j] = this;
            //Remove truck from view
            environment.getChildren().remove(this);
            //Move truck to new location
            environment.add(this, j , i);
            //Set new location of truck
            this.getLocation().setX(i);
            this.getLocation().setY(j);
        }
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
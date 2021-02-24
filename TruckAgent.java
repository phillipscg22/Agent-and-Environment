package ImprovedAgent;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;

public class TruckAgent extends Group implements Constants {


    private boolean built;
    private boolean passable;
    private boolean movable;
    private double width;
    private double height;
    private int direction;

    public TruckAgent() {

        built = false;
        movable = false;
        width = 0;
        height = 0;
        direction = FACING_WEST;
        passable = true;
    }

    public int getDirection() {return direction;}
    public void setDirection(int direction) {this.direction = direction;}

    public void setPassable(boolean passable) {

        this.passable = passable;
    }

    public boolean getPassable() {

        return passable;
    }

    public void setBuilt(boolean built) {

        this.built = built;
    }

    public boolean getBuilt() {

        return built;
    }

    public void setMovable(boolean movable) {

        this.movable = movable;
    }

    public boolean getMovable() {

        return movable;
    }

    public void turnRight() {

        setDirection(Math.abs((this.getDirection() + 1) % 4));
        this.setRotate(90);
    }

    public void turnLeft() {

        setDirection(Math.abs((this.getDirection() - 1 + 4) % 4));
        this.setRotate(270);
    }

    public void flipTruck() {

        this.setRotationAxis(Rotate.Y_AXIS);
        this.setRotate(180);
        setDirection(FACING_EAST);
    }

    public TruckAgent refresh(double width, double height) {

        return this.build(width, height);
    }

    public void clear() {

        this.getChildren().clear();
        this.setBuilt(false);
        this.setPassable(true);
        this.setMovable(false);
    }

    public int [] getTruckPosition(TruckAgent [][] trucks) {

        int [] position = new int[2];

        for(int i = 0; i < trucks.length; i++)
            for(int j = 0; j < trucks.length; j++)
                if(trucks[i][j].getBuilt()) {
                    position[0] = i;
                    position[1] = j;
                }

        return position;
    }
}
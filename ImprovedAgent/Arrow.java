package ImprovedAgent;

import javafx.scene.shape.Polyline;

public class Arrow extends Polyline implements Constants {

    public Arrow(int direction) {

        buildArrow(direction);
    }

    public void buildArrow(int direction) {

        getPoints().addAll(37.5, 25.0, 25.0, 37.5, 37.5, 50.0);
        if(direction == FACING_NORTH)
            setRotate(90);
        else if(direction == FACING_EAST)
            setRotate(180);
        else if(direction == FACING_SOUTH)
            setRotate(-90);
    }
}

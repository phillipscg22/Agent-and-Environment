package ImprovedAgent;

import javafx.scene.Group;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;

public class TruckAgent extends Group {

    private final int FACING_SOUTH = 0;
    private final int FACING_WEST = 1;
    private final int FACING_NORTH = 2;
    private final int FACING_EAST = 3;

    private boolean built;
    private boolean passable;
    private boolean movable;
    private double width;
    private double height;
    private int direction;

    public double x = .46 * width;
    public double y = .5 * height;

    public TruckAgent() {

        built = false;
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

        setDirection(Math.abs((getDirection() + 1) % 4));
        this.getTransforms().add(new Rotate(90, width / 2, height / 2));
    }

    public void turnLeft() {

        setDirection(Math.abs((getDirection() - 1 + 4) % 4));
        this.getTransforms().add(new Rotate(270, width / 2, height / 2));
    }

    public void flipTruckAgent() {

        this.setRotationAxis(Rotate.Y_AXIS);
        this.setRotate(180);
        setDirection(FACING_EAST);
    }

    public void refresh(double width, double height) {

        this.build(width, height);
    }

    public void build(double width, double height) {

        direction = 1;
        built = true;
        passable = false;
        movable = true;
        this.width = width;
        this.height = height;
        this.getChildren().clear();

        //Shape of Truck
        Path truckPath = new Path();

        MoveTo start = new MoveTo();
        start.setX(.7 * width);
        start.setY(.65 * height);

        LineTo line1 = new LineTo();
        line1.setX(.9 * width);
        line1.setY(.65 * height);

        LineTo line2 = new LineTo();
        line2.setX(.9 * width);
        line2.setY(.5 * height);

        LineTo line3 = new LineTo();
        line3.setX(.7 * width);
        line3.setY(.5 * height);

        QuadCurveTo firstCurve = new QuadCurveTo();
        firstCurve.setX(.65 * width);
        firstCurve.setY(.45 * height);
        firstCurve.setControlX(0.65 * width);
        firstCurve.setControlY(.48 * height);

        LineTo line4 = new LineTo();
        line4.setX(.65 * width);
        line4.setY(.4 * height);

        QuadCurveTo secondCurve = new QuadCurveTo();
        secondCurve.setX(.6 * width);
        secondCurve.setY(.35 * height);
        secondCurve.setControlX(.64 * width);
        secondCurve.setControlY(.36 * height);

        LineTo line5 = new LineTo();
        line5.setX(.45 * width);
        line5.setY(.35 * height);

        QuadCurveTo thirdCurve = new QuadCurveTo();
        thirdCurve.setX(.3 * width);
        thirdCurve.setY(.48 * height);
        thirdCurve.setControlX(.37 * width);
        thirdCurve.setControlY(.36 * height);

        LineTo line6 = new LineTo();
        line6.setX(.2 * width);
        line6.setY(.48 * height);

        QuadCurveTo fourthCurve = new QuadCurveTo();
        fourthCurve.setX(.15 * width);
        fourthCurve.setY(.56 * height);
        fourthCurve.setControlX(.15 * width);
        fourthCurve.setControlY(.5 * height);

        QuadCurveTo fifthCurve = new QuadCurveTo();
        fifthCurve.setX(.2 * width);
        fifthCurve.setY(.65 * height);
        fifthCurve.setControlX(.15 * width);
        fifthCurve.setControlY(.64 * height);

        LineTo line7 = new LineTo();
        line7.setX(.7 * width);
        line7.setY(.65 * height);

        truckPath.getElements().addAll(start, line1, line2, line3, firstCurve, line4,
                secondCurve, line5, thirdCurve, line6, fourthCurve, fifthCurve, line7);

        //Color of truck
        LinearGradient truckPaint = new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
                new Stop(0f, Color.SANDYBROWN), new Stop(0.05f, Color.RED),
                new Stop(0.69f, Color.RED), new Stop(0.95f, Color.SANDYBROWN));
        truckPath.setFill(truckPaint);

        //Tires
        Circle tire1 = new Circle(.335 * width, .65 * height, .05 * width);
        tire1.setFill(Color.BLACK);

        Circle rim1 = new Circle(.335 * width, .65 * height, .04 * width);
        rim1.setFill(Color.YELLOW);

        Circle hub1 = new Circle(.335 * width, .65 * height, .01 * width);
        hub1.setFill(Color.BLACK);

        Circle tire2 = new Circle(.65 * width, .65 * height, .05 * width);
        tire2.setFill(Color.BLACK);

        Circle rim2 = new Circle(.65 * width, .65 * height, .04 * width);
        rim2.setFill(Color.YELLOW);

        Circle hub2 = new Circle(.65 * width, .65 * height, .01 * width);
        hub2.setFill(Color.BLACK);

        //Doors
        Line carDoorLine = new Line(.5 * width, .36 * height, .5 * width, .6 * height);
        carDoorLine.setStrokeWidth(.004 * width);

        Path frontDoorPath = new Path();

        MoveTo frontDoorStart = new MoveTo();
        frontDoorStart.setX(.5 * width);
        frontDoorStart.setY(.6 * height);

        LineTo frontDoorLine1 = new LineTo();
        frontDoorLine1.setX(.38 * width);
        frontDoorLine1.setY(.6 * height);

        QuadCurveTo frontDoorCurve = new QuadCurveTo();
        frontDoorCurve.setX(.335 * width);
        frontDoorCurve.setY(.475 * height);
        frontDoorCurve.setControlX(.32 * width);
        frontDoorCurve.setControlY(.575 * height);

        frontDoorPath.getElements().addAll(frontDoorStart, frontDoorLine1, frontDoorCurve);

        Path backDoorPath = new Path();

        MoveTo backDoorStart = new MoveTo();
        backDoorStart.setX(.5 * width);
        backDoorStart.setY(.6 * height);

        LineTo backDoorLine1 = new LineTo();
        backDoorLine1.setX(.6 * width);
        backDoorLine1.setY(.6 * height);

        LineTo backDoorLine2 = new LineTo();
        backDoorLine2.setX(.6 * width);
        backDoorLine2.setY(.475 * height);

        backDoorPath.getElements().addAll(backDoorStart, backDoorLine1, backDoorLine2);

        //Door handles
        Rectangle frontHandle = new Rectangle(.46 * width, .5 * height, .03 * width, .02 * height);

        Rectangle backHandle = new Rectangle(.55 * width, .5 * height, .03 * width, .02 * height);

        //Windows
        Path frontWindowPath = new Path();

        MoveTo windowStart = new MoveTo();
        windowStart.setX(.5 * width);
        windowStart.setY(.36 * height);

        LineTo windowLine1 = new LineTo();
        windowLine1.setX(.5 * width);
        windowLine1.setY(.36 * height);

        LineTo windowLine2 = new LineTo();
        windowLine2.setX(.5 * width);
        windowLine2.setY(.475 * height);

        LineTo windowLine3 = new LineTo();
        windowLine3.setX(.335 * width);
        windowLine3.setY(.475 * height);

        QuadCurveTo window1Curve = new QuadCurveTo();
        window1Curve.setX(.5 * width);
        window1Curve.setY(.36 * height);
        window1Curve.setControlX(.39 * width);
        window1Curve.setControlY(.35 * height);

        LinearGradient windowColor = new LinearGradient(0.5f, 0f, 0.5f, 1.0f, true, CycleMethod.NO_CYCLE,
                new Stop(0f, Color.SKYBLUE), new Stop(0.05f, Color.WHITE), new Stop(0.5f, Color.SKYBLUE), new Stop(0.9f, Color.WHITE));

        frontWindowPath.getElements().addAll(windowStart, windowLine1, windowLine2, windowLine3, window1Curve);
        frontWindowPath.setFill(windowColor);

        Rectangle backWindow = new Rectangle(.5 * width, .36 * height, .1 * width, .115 * height);
        backWindow.setStroke(Color.BLACK);
        backWindow.setFill(windowColor);

        this.getChildren().addAll(truckPath, frontWindowPath, frontDoorPath,
                backDoorPath, backWindow, tire1, rim1, hub1, tire2, rim2, hub2,
                carDoorLine, frontHandle, backHandle);
    }

    public void clear() {

        this.getChildren().clear();
        this.setBuilt(false);
        this.setPassable(true);
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

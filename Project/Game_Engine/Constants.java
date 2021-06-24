package Project.Game_Engine;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.UUID;

public interface Constants {

    int FACING_SOUTH = 0;
    int FACING_WEST = 1;
    int FACING_NORTH = 2;
    int FACING_EAST = 3;

    int MOVING_SOUTH = 1;
    int MOVING_WEST = -1;
    int MOVING_NORTH = -1;
    int MOVING_EAST = 1;

    String NON_PASSABLE = "****";
    String GOAL_CELL = "GLITTER";
    String BORDER = "BUMP";
    String BLANK = "null";

    String BREADTH_FIRST_SEARCH = "breadthFirstSearch";
    String DEPTH_FIRST_SEARCH = "depthFirstSearch";
    String DEPTH_LIMITED_SEARCH = "depthLimitedSearch";
    String ITERATIVE_DEEPENING_DEPTH_FIRST_SEARCH = "iterativeDeepeningDepthFirstSearch";
    String UNIFORMED_COST_SEARCH = "UniformedCostSearch";

    String GREEDY_BEST_FIRST_SEARCH = "GreedyBestFirstSearch";
    String A_STAR_SEARCH = "AStarSearch";

    default TruckAgent build(double width, double height, Location location, TruckAgent truck, int count) {

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

        //Labels for truck
        Text label1 = new Text(count + "");
        label1.setX(.20 * width);
        label1.setY(.6 * height);
        label1.setFont(new Font(.15 * width));
        Text label2 = new Text(count + "");
        label2.setX(.8 * width);
        label2.setY(.6 * height);
        label2.setFont(new Font(.15 * width));

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

        //UUID updated
        truck.setMovable(true);
        truck.setLocation(location);
        truck.setUUID(UUID.randomUUID());

        truck.getChildren().addAll(truckPath, frontWindowPath, frontDoorPath,
                backDoorPath, backWindow, tire1, rim1, hub1, tire2, rim2, hub2,
                carDoorLine, frontHandle, backHandle, label1, label2);

        return truck;
    }
}

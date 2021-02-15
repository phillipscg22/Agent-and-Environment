package ImprovedAgent;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class TruckAgent extends Application {

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();

        //Shape of Truck
        Path truckPath = new Path();

        MoveTo start = new MoveTo();
        start.setX(350);
        start.setY(325);

        LineTo line1 = new LineTo();
        line1.setX(350);
        line1.setY(325);

        LineTo line2 = new LineTo();
        line2.setX(450);
        line2.setY(325);

        LineTo line3 = new LineTo();
        line3.setX(450);
        line3.setY(250);

        LineTo line4 = new LineTo();
        line4.setX(350);
        line4.setY(250);

        QuadCurveTo firstCurve = new QuadCurveTo();
        firstCurve.setX(325);
        firstCurve.setY(225);
        firstCurve.setControlX(325);
        firstCurve.setControlY(240);

        LineTo line5 = new LineTo();
        line5.setX(325);
        line5.setY(200);

        QuadCurveTo secondCurve = new QuadCurveTo();
        secondCurve.setX(300);
        secondCurve.setY(175);
        secondCurve.setControlX(320);
        secondCurve.setControlY(180);

        LineTo line6 = new LineTo();
        line6.setX(225);
        line6.setY(175);

        QuadCurveTo thirdCurve = new QuadCurveTo();
        thirdCurve.setX(150);
        thirdCurve.setY(240);
        thirdCurve.setControlX(185);
        thirdCurve.setControlY(180);

        LineTo line7 = new LineTo();
        line7.setX(100);
        line7.setY(240);

        QuadCurveTo fourthCurve = new QuadCurveTo();
        fourthCurve.setX(75);
        fourthCurve.setY(280);
        fourthCurve.setControlX(75);
        fourthCurve.setControlY(250);

        QuadCurveTo fifthCurve = new QuadCurveTo();
        fifthCurve.setX(100);
        fifthCurve.setY(325);
        fifthCurve.setControlX(75);
        fifthCurve.setControlY(320);

        LineTo line8 = new LineTo();
        line8.setX(350);
        line8.setY(325);

        truckPath.getElements().addAll(start, line1, line2, line3, line4, firstCurve, line5,
                secondCurve, line6, thirdCurve, line7, fourthCurve, fifthCurve, line8);

        //Color of truck
        LinearGradient truckPaint = new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
                        new Stop(0f, Color.SANDYBROWN), new Stop(0.05f, Color.RED),
                        new Stop(0.69f, Color.RED), new Stop(0.95f, Color.SANDYBROWN));
        truckPath.setFill(truckPaint);

        //Tires
        Circle tire1 = new Circle(167.5, 325, 25);
        tire1.setFill(Color.BLACK);

        Circle rim1 = new Circle(167.5, 325, 20);
        rim1.setFill(Color.YELLOW);

        Circle hub1 = new Circle(167.5, 325, 5);
        hub1.setFill(Color.BLACK);

        Circle tire2 = new Circle(325, 325, 25);
        tire2.setFill(Color.BLACK);

        Circle rim2 = new Circle(325, 325, 20);
        rim2.setFill(Color.YELLOW);

        Circle hub2 = new Circle(325, 325, 5);
        hub2.setFill(Color.BLACK);

        //Doors
        Line carDoorLine = new Line(250, 180, 250, 300);
        carDoorLine.setStrokeWidth(2);

        Path frontDoorPath = new Path();

        MoveTo frontDoorStart = new MoveTo();
        frontDoorStart.setX(250);
        frontDoorStart.setY(300);

        LineTo frontDoorLine1 = new LineTo();
        frontDoorLine1.setX(190);
        frontDoorLine1.setY(300);

        QuadCurveTo frontDoorCurve = new QuadCurveTo();
        frontDoorCurve.setX(167.5);
        frontDoorCurve.setY(237.5);
        frontDoorCurve.setControlX(160);
        frontDoorCurve.setControlY(287.5);

        frontDoorPath.getElements().addAll(frontDoorStart, frontDoorLine1, frontDoorCurve);

        Path backDoorPath = new Path();

        MoveTo backDoorStart = new MoveTo();
        backDoorStart.setX(250);
        backDoorStart.setY(300);

        LineTo backDoorLine1 = new LineTo();
        backDoorLine1.setX(300);
        backDoorLine1.setY(300);

        LineTo backDoorLine2 = new LineTo();
        backDoorLine2.setX(300);
        backDoorLine2.setY(237.5);

        backDoorPath.getElements().addAll(backDoorStart, backDoorLine1, backDoorLine2);

        //Door handles
        Rectangle frontHandle = new Rectangle(230, 250, 15, 10);

        Rectangle backHandle = new Rectangle(275, 250, 15, 10);

        //Windows
        Path frontWindowPath = new Path();

        MoveTo windowStart = new MoveTo();
        windowStart.setX(250);
        windowStart.setY(180);

        LineTo windowLine1 = new LineTo();
        windowLine1.setX(250);
        windowLine1.setY(180);

        LineTo windowLine2 = new LineTo();
        windowLine2.setX(250);
        windowLine2.setY(237.5);

        LineTo windowLine3 = new LineTo();
        windowLine3.setX(167.5);
        windowLine3.setY(237.5);

        QuadCurveTo window1Curve = new QuadCurveTo();
        window1Curve.setX(250);
        window1Curve.setY(180);
        window1Curve.setControlX(195);
        window1Curve.setControlY(175);

        LinearGradient windowColor = new LinearGradient(0.5f, 0f, 0.5f, 1.0f, true, CycleMethod.NO_CYCLE,
                new Stop(0f, Color.SKYBLUE), new Stop(0.05f, Color.WHITE), new Stop(0.5f, Color.SKYBLUE), new Stop(0.9f, Color.WHITE));

        frontWindowPath.getElements().addAll(windowStart, windowLine1, windowLine2, windowLine3, window1Curve);
        frontWindowPath.setFill(windowColor);

        Rectangle backWindow = new Rectangle(250, 180, 50, 57.5);
        backWindow.setStroke(Color.BLACK);
        backWindow.setFill(windowColor);

        root.getChildren().addAll(truckPath, frontWindowPath, frontDoorPath,
                backDoorPath, backWindow, tire1, rim1, hub1, tire2, rim2, hub2,
                carDoorLine, frontHandle, backHandle);

        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String [] args) {

        Application.launch(args);
    }
}

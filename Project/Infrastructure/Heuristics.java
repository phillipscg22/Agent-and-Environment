package Project.Infrastructure;

import Project.Game_Engine.Cell;

public class Heuristics {

    public double getFValue(int x1, int y1, Cell goal, double pathCost){

        double fValue = getHValue(x1,y1,goal) + pathCost;

        return fValue;
    }
    public double getHValue(int x1, int y1, Cell goal) {

        int x2 = goal.getLocation().getX();
        int y2 = goal.getLocation().getY();

        double hValue = Math.sqrt((Math.pow(x1 - x2,2)) + (Math.pow(y1 - y2, 2))); // h(n)

        return hValue;
    }

}

package Project.Infrastructure;

import Project.Game_Engine.Cell;

public class Heuristics {

    public Heuristics(){

    }
    public double getFValue(int x, int y, double pathCost){

        double fValue = getHValue(x,y) + pathCost;

        return fValue;
    }
    public double getHValue(int x1, int y1) {

       // int x2 = ; Need to acquire coordinates for the goal cell, confused about how to do that correctly
       // int y2 = ;

        double hValue = Math.sqrt((Math.pow(x1 - x2,2)) + (Math.pow(y1 - y2, 2))); // h(n)

        return hValue;
    }
}

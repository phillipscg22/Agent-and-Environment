package Project.Infrastructure;

import Project.Game_Engine.Cell;

public class Heuristics {

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public Heuristics(){

          this.x1 = 0;
          this.y1 = 0;
          this.x2 = 0;
          this.y2 = 0;

    }
    public Heuristics(int x1, int y1){

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = 0;
        this.y2 = 0;

    }
    public Heuristics(int x1, int y1, int x2, int y2){

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

    }

    public double getFValue(double pathCost){

        double fValue = getHValue() + pathCost;

        return fValue;
    }
    public double getHValue() {

       // int x2 = ; Need to acquire coordinates for the goal cell, confused about how to do that correctly
       // int y2 = ;

        double hValue = Math.sqrt((Math.pow(x1 - x2,2)) + (Math.pow(y1 - y2, 2))); // h(n)

        return hValue;
    }
}

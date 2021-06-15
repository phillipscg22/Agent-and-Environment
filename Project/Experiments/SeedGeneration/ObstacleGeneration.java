package Project.Experiments.SeedGeneration;

import Project.Game_Engine.Cell;
import Project.Game_Engine.Constants;
import Project.Game_Engine.Environment;
import Project.Game_Engine.Location;

import java.util.ArrayList;

public class ObstacleGeneration implements Constants {

    private int i = 0;
    private int j = 0;
    private ArrayList<Location> locations;

    /**
     * 1-20 single agent
     * 21-40 multiple agents
     */
    public void buildSeed0(Environment environment) {

    }
    public void buildSeed1(Environment environment) {

        int gridSize = environment.getGridSize();
        locations = new ArrayList<>();

        if(gridSize == 1);
        else if(gridSize == 2);
        else if(gridSize == 3) {
            locations.add(new Location(i, j + 2));
            locations.add(new Location(i + 1, j + 1));
        }
        else if(gridSize == 4) {
            locations.add(new Location(i + 1, j + 3));
            locations.add(new Location(i + 2, j + 2));
            locations.add(new Location(i + 3, j));
        }
        else if(gridSize == 5) {

            locations.add(new Location(i + 2, j + 4));
            locations.add(new Location(i + 2, j + 1));
            locations.add(new Location(i + 3, j + 1));
            locations.add(new Location(i + 3, j + 3));
            locations.add(new Location(i + 4, j));
        }
        else if(gridSize < 8) {

            seed1Helper(locations, gridSize);
        }
        else if(gridSize <= 10) {

            seed1Helper(locations, gridSize);
            seed1Helper2(locations, gridSize);
        }
        //else if (gridSize <= 15) {
        else {
            seed1Helper(locations, gridSize);
            seed1Helper2(locations, gridSize);
            if(gridSize != 11 && gridSize != 12) {
                locations.add(new Location(gridSize - 5, gridSize * 2 / 3));
                locations.add(new Location(gridSize / 2, gridSize * 3 / 4));
                locations.add(new Location(gridSize / 2 + 3, gridSize / 2 + 1));
                if(gridSize != 13)
                    locations.add(new Location(gridSize - 3, gridSize - 3));
                else
                    locations.add(new Location(gridSize - 3, gridSize - 2));
            }
            locations.add(new Location(gridSize * 2 / 3 - 1, gridSize * 2 / 3 - 1));
            locations.add(new Location(7, 4));
            locations.add(new Location(6, 3));
            locations.add(new Location(gridSize * 2 / 3, gridSize - 1));
            locations.add(new Location(gridSize * 2 / 3 + 1, gridSize - 2));
            locations.add(new Location(gridSize / 2 + (gridSize / 4), gridSize / 2));
            locations.add(new Location(8, 0));
        }

        if(locations.size() > 0)
            buildNonPassables(locations, environment);
    }

    public void buildSeed2(Environment environment) {

        int gridSize = environment.getGridSize();

        if(gridSize == 1);
        else if(gridSize % 2 == 0) {

            for(int i = 0; i < gridSize; i+= 2)
                for(int y = 0; y < gridSize; y += 2)
                    buildSingleNonPassable(i, y, environment);

            for(int i = 3; i < gridSize; i += 4) {

                if(i < gridSize - 2)
                    buildSingleNonPassable(i, i, environment);
            }
        }
        else if(gridSize % 2 == 1) {

            for(int i = 0; i < gridSize; i+= 2)
                for(int y = 0; y < gridSize; y += 2)
                    buildSingleNonPassable(i, y, environment);
            for(int i = 3; i < gridSize; i += 4) {

                if(i < gridSize - 3)
                    buildSingleNonPassable(i, i, environment);
            }
            for(int i = 3, j = gridSize - 4; i < gridSize; i += 4, j -= 4) {

                if(i < gridSize - 3)
                    buildSingleNonPassable(i, j, environment);
            }
        }

    }

    public void buildSeed3(Environment environment) {

        int gridSize = environment.getGridSize();


    }

    public void buildSeed4(Environment environment) { // creates arrow after gridSize > 4, otherwise its a line or dot in the middle

        int gridSize = environment.getGridSize();

        if(gridSize == 1);
        else if(gridSize == 2 || gridSize == 3) {

            buildSingleNonPassable(i + 1, j + 1, environment);
        }
        else {

            int arrow = 1;

            for( ; arrow < gridSize-1; arrow++){
                if(arrow >= (gridSize / 2)){
                    buildSingleNonPassable(gridSize-2, arrow, environment);
                    buildSingleNonPassable(arrow, gridSize-2, environment);
                }
                buildSingleNonPassable(arrow, arrow, environment);

            }

        }
    }

    public void buildSeed5(Environment environment) { // creates arrow after gridSize > 4, otherwise its a line or dot in the middle

        int gridSize = environment.getGridSize();

        if(gridSize == 1);
        else if(gridSize == 2 || gridSize == 3) {

            buildSingleNonPassable(i + 1, j+1, environment);

        }
        else if(gridSize == 4) {

            buildSingleNonPassable(i + 1, j+1, environment);

        }
        else if(gridSize == 5) {

            buildSingleNonPassable(i + 1, j+1, environment);
            buildSingleNonPassable(i + 3, j+1, environment);
            buildSingleNonPassable(i + 1, j+3, environment);
            buildSingleNonPassable(i +3, j+3, environment);
            buildSingleNonPassable(i + 4, j+2, environment);

        }

        else {

            int diagonal = 0;

            buildSingleNonPassable(((gridSize /2) -2), (gridSize /2 + (gridSize / 5)), environment);
            buildSingleNonPassable(((gridSize /2) -2), (gridSize /2 - (gridSize /5)), environment);

            buildSingleNonPassable(((gridSize /2) +2), (gridSize /2 + (gridSize / 5)), environment);
            buildSingleNonPassable(((gridSize /2) +2), (gridSize /2 - (gridSize /5)), environment);


            for(int n = (gridSize /2 - (gridSize / 5))+1; n < (gridSize /2 + (gridSize / 5)); n++){

                try {
                    buildSingleNonPassable(((gridSize / 2) + 3), n, environment);
                }
                catch(Exception ignored){}

            }
        }
    }

    public void buildSeed6(Environment environment) {

        int gridSize = environment.getGridSize();

        double shiftValue = 1.6;

        if(gridSize != 2)
            for(int i = 0; i < gridSize; i++) {

                double jValue = 0;
                double iValue = 0;
                if(i % 2 == 0)
                    for(int j = 0; j < gridSize; j++) {

                        if(jValue > gridSize)
                            jValue /= 2;

                        jValue += shiftValue;

                        buildSingleNonPassable(i, (int) jValue, environment);
                    }
                else
                    for(int j = 0; j < gridSize; j += 2) {

                        if(iValue > gridSize)
                            iValue /= 2;

                        iValue += shiftValue;

                        buildSingleNonPassable((int) iValue , j, environment);
                    }

                if(i % 6 == 0)
                    for(int j = 0; j < gridSize; j += 2)
                        buildSingleNonPassable(i, j, environment);
            }
    }

    public void buildSeed7(Environment environment) {

        int gridSize = environment.getGridSize();

        double shiftValue = 1.5;

        if(gridSize > 3) {
            for (int i = 0; i < gridSize; i += 2) {

                if (i % 3 == 0) {

                    for (int j = 0; j < gridSize; j += 3)
                        buildSingleNonPassable(i, j, environment);
                } else {

                    for (double j = 0; j < gridSize; j += shiftValue)
                        buildSingleNonPassable(i, (int) Math.floor(j), environment);
                }
            }

            for (int i = 0; i < gridSize; i += 3) {

                for (int j = 4; j < gridSize; j += 6)
                    buildSingleNonPassable(i, j, environment);
            }

            for (int i = 1; i < gridSize; i += 4) {

                for (int j = 0; j < gridSize; j += 6) {

                    buildSingleNonPassable(i, j, environment);
                }
            }

            for (int i = 0; i < gridSize; i++) {

                if (i % 2 != 0)
                    buildSingleNonPassable(i, i, environment);
            }
        }

        if(gridSize != 2) {
            for (int i = 0; i < gridSize; i++) {

                buildSingleNonPassable(gridSize - 1, i, environment);
                buildSingleNonPassable(i, gridSize - 1, environment);
            }
        }
    }

    public void buildSeed8(Environment environment) {

        int gridSize = environment.getGridSize();

        double shiftValue = 2.6;

        for(int i = 0; i < gridSize; i++) {

            double jValue = 0;
            double iValue = 0;
            if(i % 2 == 0)
                for(int j = 0; j < gridSize; j++) {

                    if(jValue > gridSize)
                        jValue /= 2;

                    jValue += shiftValue;

                    buildSingleNonPassable(i, (int) jValue, environment);
                }
            else
                for(int j = 0; j < gridSize; j += 2) {

                    if(iValue > gridSize)
                        iValue /= 2;

                    iValue += shiftValue;

                    buildSingleNonPassable((int) iValue , j, environment);
                }

            if(i % 6 == 0)
                for(int j = 0; j < gridSize; j += 2)
                    buildSingleNonPassable(i, j, environment);
        }

        for(int i = 1; i < gridSize; i += 10) {

            for(int j = 3; j < gridSize; j += 8) {

                buildSingleNonPassable(i, j, environment);
            }
        }
    }

    public void buildSeed9(Environment environment) {

        int gridSize = environment.getGridSize();

        double shiftValue = 3.3;

        for(int i = 0; i < gridSize; i++) {

            double jValue = 0;
            double iValue = 0;
            if(i % 2 == 0)
                for(int j = 0; j < gridSize; j++) {

                    if(jValue > gridSize)
                        jValue /= 2;

                    jValue += shiftValue;

                    buildSingleNonPassable(i, (int) jValue, environment);
                }
            else
                for(int j = 0; j < gridSize; j += 2) {

                    if(iValue > gridSize)
                        iValue /= 2;

                    iValue += shiftValue;

                    buildSingleNonPassable((int) iValue , j, environment);
                }

            if(i % 6 == 0)
                for(int j = 0; j < gridSize; j += 2)
                    buildSingleNonPassable(i, j, environment);
        }

        for(int i = 1; i < gridSize; i += 10) {

            for(int j = 3; j < gridSize; j += 8) {

                buildSingleNonPassable(i, j, environment);
            }
        }
    }

    public void buildSeed10(Environment environment) {

        int gridSize = environment.getGridSize();

        for(int i = 0; i < gridSize; i += 2) {

            for(int j = 0; j < gridSize; j += 4) {

                buildSingleNonPassable(i, j, environment);
            }
        }

        for(int i = 1; i < gridSize; i += 2) {

            for(int j = 1; j < gridSize; j += 3) {

                buildSingleNonPassable(i, j, environment);
            }
        }

        for(int i = gridSize - 1, j = 0; i > 0; i--, j++) {

            buildSingleNonPassable(i, j, environment);
        }
    }

    public void buildSeed11(Environment environment) {

        int gridSize = environment.getGridSize();

        if (gridSize == 1) ;
        else
            for (i = 0; i < gridSize + 1; i++) {
                for (j = 0; j < gridSize + 1; j++) {

                    if (i % 2 == 0 && j % 5 == 0 || i % 3 == 0 && j % 2 == 0 || j % 5 == 0 && i % 3 == 0) {
                        buildSingleNonPassable(i, j, environment);
                    }
                }
            }
    }
    public void buildSeed12(Environment environment) {


        int gridSize = environment.getGridSize();

        if(gridSize == 1);
        else
            for(i = 0; i < gridSize+1; i++){
                for(j = 0; j < gridSize +1; j++){

                    if( i % 4 == 0 && j % 5 == 0 || i % 7 == 0 && j % 4 == 0 || j % 3 ==0 && i % 3 == 0){
                        buildSingleNonPassable(i,j, environment);
                    }
                }
            }

    }
    public void buildSeed13(Environment environment) {


        int gridSize = environment.getGridSize();

        if(gridSize == 1);
        else
            for(i = 0; i < gridSize+1 ; i++){
                for(j = 0; j < gridSize+1 ; j++){

                    if( i % 4 == 0 && j % 3 == 0 || j % 5 ==0 && i % 3 == 0 ||
                            j % 2 == 0 && i % 9 == 0 || i % 2 == 0 && j % 7 == 0 || i % 9 == 0 && j % 7 == 0){
                        buildSingleNonPassable(i,j, environment);
                    }
                }
            }

    }
    public void buildSeed14(Environment environment) {

        int gridSize = environment.getGridSize();

        if(gridSize == 1);
        else
            for(i = 0; i < gridSize+1 ; i++){
                for(j = 0; j < gridSize+1 ; j++){

                    if( i % 4 == 0 && j % 3 == 0 ||  i % 2 == 0 && j % 7 == 0 || i % 9 == 0 && j % 5 == 0
                            || i % 5 == 0 && j % 7 == 0|| i % 2 == 0 && j % 3 == 0){
                        buildSingleNonPassable(i,j, environment);
                    }
                }
            }
        for(int k = 0; k < gridSize+1 ; k++){
            if(k % 2 == 0){
                buildSingleNonPassable(k,k, environment);
            }
        }
        for(int f = gridSize+1 ; f > 0 ; f--){
            if(f % 3 == 0){
                buildSingleNonPassable(f,f, environment);
            }
        }
    }
    public void buildSeed15(Environment environment) {

        int gridSize = environment.getGridSize();

        if(gridSize == 1);
        else
            for(i = 0; i < gridSize+1 ; i++){
                for(j = 0; j < gridSize+1 ; j++){

                    if( i % 2 == 0 && j % 3 == 0 ||  i % 8 == 0 && j % 2 == 0 || i % 7 == 0 && j % 5 == 0
                            || i % 6 == 0 && j % 4 == 0){
                        buildSingleNonPassable(i,j, environment);
                    }
                }
            }
        for(int k = 0; k < gridSize+1 ; k++){
            if(k % 2 == 0 && k % 3 != 0){
                buildSingleNonPassable(k,k, environment);
            }
        }
        for(int f = gridSize+1 ; f > 0 ; f--){
            if(f % 3 == 0){
                buildSingleNonPassable(f,f, environment);
            }
        }
    }


    public void buildSeed20(Environment environment) {

        int gridSize = environment.getGridSize();

        if(gridSize <= 2)
            return;

        if(gridSize % 2 == 0) {
            for (int y = 0, xUpper = gridSize / 2 - 1, xLower = gridSize / 2; y < gridSize / 2; xLower++, xUpper--, y++) {

                buildSingleNonPassable(xUpper, y, environment);
                buildSingleNonPassable(xLower, y, environment);
            }
            for (int y = gridSize - 1, xUpper = gridSize / 2 - 1, xLower = gridSize / 2; y >= gridSize / 2; xLower++, xUpper--, y--) {

                buildSingleNonPassable(xUpper, y, environment);
                buildSingleNonPassable(xLower, y, environment);
            }
            for(int x = gridSize / 2, y = 0; y < gridSize; y++) {
                buildSingleNonPassable(x - 1, y, environment);
                buildSingleNonPassable(y, x - 1, environment);
                buildSingleNonPassable(x, y, environment);
                buildSingleNonPassable(y, x, environment);
            }
        }
        else {

            for(int xUpper = gridSize / 2, xLower = gridSize / 2, y = 0; y <= gridSize / 2; xUpper--, xLower++, y++) {

                buildSingleNonPassable(xUpper, y, environment);
                buildSingleNonPassable(xLower, y, environment);
            }
            for (int y = gridSize - 1, xUpper = gridSize / 2, xLower = gridSize / 2; y >= gridSize / 2; xLower++, xUpper--, y--) {

                buildSingleNonPassable(xUpper, y, environment);
                buildSingleNonPassable(xLower, y, environment);
            }
            for(int x = gridSize / 2, y = 0; y < gridSize; y++) {
                buildSingleNonPassable(x, y, environment);
                buildSingleNonPassable(y, x, environment);
            }
        }
    }

    private void buildSingleNonPassable(int i, int j, Environment environment) {

        try {
            if (environment.getCells()[i][j].getPassable()) {

                Cell cell = new Cell(new Location(i, j), environment.getWidthOfCell(), environment.getHeightOfCell(), NON_PASSABLE, Constants.FACING_WEST);
                cell.setPassable(false);
                cell.fillCell();
                environment.add(cell, j, i);
                environment.getCells()[i][j] = cell;
            }
        }
        catch(Exception ignored) {}
    }

    private void buildNonPassables(ArrayList<Location> locations, Environment environment) {

        int x;
        int y;

        for(int m = 0; m < locations.size(); m++) {

            x = locations.get(m).getX();
            y = locations.get(m).getY();

            try {
                buildSingleNonPassable(x, y, environment);
            }
            catch(Exception ignored) {}
        }
    }

    private void seed1Helper(ArrayList<Location> locations, int gridSize) {

        locations.add(new Location(gridSize / 2, gridSize - 1));
        locations.add(new Location(gridSize / 2 + 1, gridSize - 2));
        locations.add(new Location(gridSize / 3, gridSize - 3));
        locations.add(new Location(gridSize - 1, j + 1));
        locations.add(new Location(gridSize - 2, j + 2));
        locations.add(new Location(gridSize - 3, j));
        locations.add(new Location(gridSize - 4, j + 2));
        locations.add(new Location(gridSize - 1, j + 6));
        if(gridSize == 7)
            locations.add(new Location(gridSize - 2, gridSize / 2));
    }

    private void seed1Helper2(ArrayList<Location> locations, int gridSize) {

        seed1Helper(locations, gridSize);
        locations.add(new Location(gridSize - 2, gridSize / 2));
        locations.add(new Location(gridSize / 2, gridSize / 2));
        locations.add(new Location(gridSize / 2 - 1, gridSize / 2 - 1));
        if(gridSize != 8)
            locations.add(new Location((int)(gridSize * .7), (int)(gridSize * .7)));
        locations.add(new Location(gridSize / 5, gridSize / 2));
        locations.add(new Location(2, 5));
        if(gridSize == 10) {
            locations.add(new Location(3, 6));
            locations.add(new Location(gridSize / 2, gridSize / 2 + 1));
        }
    }
}

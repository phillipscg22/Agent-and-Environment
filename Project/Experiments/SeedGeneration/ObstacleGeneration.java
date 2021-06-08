package Project.Experiments.SeedGeneration;

import Project.Game_Engine.Cell;
import Project.Game_Engine.Constants;
import Project.Game_Engine.Environment;
import Project.Game_Engine.Location;

public class ObstacleGeneration implements Constants {

    public void buildSeed1(Environment environment) {

        int i = 8;
        int j = 8;

        buildSingleNonPassable(i, j, environment);

        i = 9;
        j = 9;

        buildSingleNonPassable(i, j, environment);

        i = 6;
        j = 9;

        buildSingleNonPassable(i, j, environment);
    }

    public void buildSeed3(Environment environment) {

        int gridSize = environment.getGridSize();

        int i = 0;
        int j = 0;

        if(gridSize == 1);
        else if(gridSize == 2) {

            buildSingleNonPassable(i + 1, j, environment);
            buildSingleNonPassable(i, j + 1, environment);
        }
        else if(gridSize == 3 || gridSize == 4) {

            j = gridSize - 1;
            for( ; i < gridSize; i++, j--)
                buildSingleNonPassable(i, j, environment);
        }
        else {

            j = gridSize / 2;
            for( ; j >= 0; i++, j--)
                buildSingleNonPassable(i, j, environment);
        }
    }







    private static void buildSingleNonPassable(int i, int j, Environment environment) {

        if(environment.getCells()[i][j].getPassable()) {

            Cell cell = new Cell(new Location(i, j), environment.getWidthOfCell(), environment.getHeightOfCell(), NON_PASSABLE, Constants.FACING_WEST);
            cell.setPassable(false);
            cell.fillCell();
            environment.add(cell, j, i);
            environment.getCells()[i][j] = cell;
        }
    }
}

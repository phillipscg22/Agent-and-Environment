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
            seed2Helper(locations, gridSize);
        }
        else if (gridSize <= 15) {

            seed1Helper(locations, gridSize);
            seed2Helper(locations, gridSize);
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

    private void seed2Helper(ArrayList<Location> locations, int gridSize) {

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

    public void buildSeed3(Environment environment) {

        int gridSize = environment.getGridSize();
        locations = new ArrayList<>();

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







    private void buildSingleNonPassable(int i, int j, Environment environment) {

        if(environment.getCells()[i][j].getPassable()) {

            Cell cell = new Cell(new Location(i, j), environment.getWidthOfCell(), environment.getHeightOfCell(), NON_PASSABLE, Constants.FACING_WEST);
            cell.setPassable(false);
            cell.fillCell();
            environment.add(cell, j, i);
            environment.getCells()[i][j] = cell;
        }
    }

    private void buildNonPassables(ArrayList<Location> locations, Environment environment) {

        int x = 0;
        int y = 0;

        for(int m = 0; m < locations.size(); m++) {

            x = locations.get(m).getX();
            y = locations.get(m).getY();

            try {
                buildSingleNonPassable(x, y, environment);
            }
            catch(Exception ignored) {}
        }
    }
}

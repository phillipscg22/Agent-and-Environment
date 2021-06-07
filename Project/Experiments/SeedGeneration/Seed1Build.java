package Project.Experiments.SeedGeneration;

import Project.Game_Engine.*;

public interface Seed1Build extends Constants {

    static TruckAgent[] fillCellsSituation(Environment environment) {

        for(int i = 0; i < environment.getGridSize(); i++) {

            for(int j = 0; j < environment.getGridSize(); j++) {

                Cell cell = new Cell(new Location(i, j), environment.getWidthOfCell(), environment.getHeightOfCell(), BLANK, Constants.FACING_WEST);
                environment.add(cell, j, i);
                environment.getCells()[i][j] = cell;
            }
        }

        createObstaclesSituation(environment);

        return createAgentsSituation(environment);
    }

    private static TruckAgent [] createAgentsSituation(Environment environment) {

        TruckAgent [] trucks = new TruckAgent[1];

        int count = 0;

        int i = 1;
        int j = 1;

        if(environment.getCells()[i][j].getPassable()){
            TruckAgent truck = new TruckAgent();
            truck = truck.build(environment.getWidthOfCell(), environment.getHeightOfCell(), new Location(i, j), truck, count);
            environment.getAgents()[i][j] = truck;
            environment.getCells()[i][j].getChildren().add(truck);
            environment.getCells()[i][j].setVisited(true);
            environment.getCells()[i][j].setAgent(truck);
            trucks[count] = truck;
            environment.getCells()[i][j].setPassable(false);
        }

        return trucks;
    }

    private static void createObstaclesSituation(Environment environment) {

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

    static void createGoalsSituation(Environment environment) {

        environment.setGoals(new Cell[1]);

        int j = 9;
        int i = 8;

        if(environment.getCells()[i][j].getPassable() && !environment.getCells()[i][j].getEndState()) {

            Location location = new Location(i, j);
            Cell cell = new Cell(location, environment.getWidthOfCell(), environment.getHeightOfCell(), GOAL_CELL, Constants.FACING_WEST);
            cell.setEndState(true);
            environment.add(cell, j, i);
            environment.getCells()[i][j] = cell;
            environment.getCells()[i][j].setPassable(true);
            environment.getGoals()[0] = environment.getCells()[i][j];
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

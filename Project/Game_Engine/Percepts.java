package Project.Game_Engine;

public interface Percepts {

    String NON_PASSABLE = "****";
    String GOAL_CELL = "GLITTER";
    String BORDER = "BUMP";
    String BLANK = "null";

    //Used to get percept to agent based on what cell's percept is
//    default String receivePercept(Cell cell) {
//
//        return cell.getPercept();
//    }
}
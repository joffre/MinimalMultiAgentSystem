package hunter;

import core.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by escobedo on 24/01/17.
 */
public class Dijkstra {
    int[][] values;

    List<Position> visited;
    List<Position> unvisited;

    List<Position> positionsToRemove;

    private int maxValue;

    private boolean opposite = false;

    public Dijkstra(int sizeX, int sizeY){
        maxValue = sizeX * sizeY;
        values = new int[sizeX][sizeY];
        visited = new ArrayList<>();
        unvisited = new ArrayList<>();
        positionsToRemove = new ArrayList<>();
        opposite = false;
    }

    public void setExcludedPositions(List<Position> excluded){
        positionsToRemove = excluded;
    }

    public void compute(Position base, boolean opposite){
        this.opposite = opposite;
        visited.clear();
        unvisited.clear();


        visited.addAll(positionsToRemove);


        for(int x = 0; x < values.length; x++){
            for(int y = 0; y < values[x].length; y++){

                values[x][y] = maxValue;
            }
        }

        Position currentPos = new Position(base.getPosX(), base.getPosY());

        values[currentPos.getPosX()][currentPos.getPosY()] = 0;

        valueNeighborship(currentPos);

        while(unvisited.size() > 0){
            int lowestValue = maxValue;

            for(Position uPos : unvisited){
                int uPosValue = values[uPos.getPosX()][uPos.getPosY()];
                if(uPosValue < lowestValue){
                    currentPos = uPos;
                    lowestValue = uPosValue;
                }
            }

            if(lowestValue == maxValue){ break; }

            valueNeighborship(currentPos);
        }
    }

    public void valueNeighborship(Position currentPos){
        int CPValue = values[currentPos.getPosX()][currentPos.getPosY()];
        for(int diffX = -1; diffX<=1; diffX++){
            for(int diffY = -1; diffY <= 1; diffY++){
                if(diffX != 0 || diffY != 0) {
                    int x = currentPos.getPosX() + diffX;
                    int y = currentPos.getPosY() + diffY;
                    Position neighborPos = new Position(x, y);
                    if (!visited.contains(neighborPos)) {
                        if (x >= 0 && x < values.length && y>= 0 && y < values[0].length) {
                            if(!unvisited.contains(neighborPos))unvisited.add(neighborPos);
                            if(values[x][y] > CPValue + 1) values[x][y] = CPValue +1;
                        }
                    }
                }
            }
        }

        unvisited.remove(currentPos);
        visited.add(currentPos);
    }

    public Position getNextShortestPosition(Position pos){
        int posValue = values[pos.getPosX()][pos.getPosY()];
        if(opposite) posValue = 0;

        Position nextPosition = new Position(0,0);
        for(int diffX = -1; diffX<=1; diffX++){
            for(int diffY = -1; diffY <= 1; diffY++) {
                if (diffX != 0 || diffY != 0) {
                    int x = pos.getPosX() + diffX;
                    int y = pos.getPosY() + diffY;
                    if (x >= 0 && x < values.length && y >= 0 && y < values[0].length) {
                        int nextValue = values[x][y];
                        if (opposite) {
                            if (nextValue >= posValue && nextValue != values.length * values[x].length) {
                                nextPosition = new Position(x, y);
                                posValue = nextValue;
                            }
                        } else {
                            if (nextValue < posValue) {
                                nextPosition = new Position(x, y);
                                posValue = nextValue;
                            }
                        }
                    }
                }
            }
        }

        return nextPosition;
    }
}

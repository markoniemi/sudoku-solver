package com.tieto.sudoku.generator;

import java.util.Random;

import com.tieto.sudoku.Location;

public class RandomLocationGenerator implements LocationGenerator {
    private Random random;

    RandomLocationGenerator() {
        random = new Random(System.currentTimeMillis());
    }

    @Override
    // TODO return a location which is empty
    public Location nextLocation(/* Board board */) {
        Location location = new Location();
        location.setColumn(random.nextInt(8));
        location.setRow(random.nextInt(8));
        // if (board.getCell(location.getRow(),
        // location.getColumn()).intValue()==Cell.EMPTY) {
        //
        // }
        return location;
    }
}

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
        var row = random.nextInt(8);
        var column = random.nextInt(8);
        // if (board.getCell(location.row(),
        // location.column()).intValue()==Cell.EMPTY) {
        //
        // }
        return new Location(row, column);
    }
}

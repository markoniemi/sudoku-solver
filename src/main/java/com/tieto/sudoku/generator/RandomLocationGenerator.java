package com.tieto.sudoku.generator;

import java.util.Random;

import com.tieto.sudoku.Location;

public class RandomLocationGenerator implements LocationGenerator {
    private Random random;

    RandomLocationGenerator() {
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public Location nextLocation() {
        var row = random.nextInt(9);
        var column = random.nextInt(9);
        return new Location(row, column);
    }
}

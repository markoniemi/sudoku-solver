package com.example.sudoku.generator;

import com.example.sudoku.Board;
import com.example.sudoku.Location;

public class OrderedLocationGenerator implements LocationGenerator {
    private int index = 0;
    private int step = 4;

    public OrderedLocationGenerator(int step) {
        this.step = step;
    }

    @Override
    public Location nextLocation() {
        Location location = Location.ofIndex(index);
        index += step;
        if (index >= Board.CELL_COUNT) {
            index = index % Board.CELL_COUNT;
        }
        return location;
    }
}

package com.example.sudoku.generator;

import com.example.sudoku.Location;

/**
 * Strategy for generating locations on a sudoku board.
 */
public interface LocationGenerator {
    /**
     * Get the next location to fill.
     * @return the next location (row, column) on the board
     */
    Location nextLocation();
}

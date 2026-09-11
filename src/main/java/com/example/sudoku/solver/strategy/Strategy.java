package com.example.sudoku.solver.strategy;

import com.example.sudoku.Board;

/**
 * Sudoku solving strategy interface. Implementations apply logical deduction
 * rules to solve sudoku puzzles by eliminating candidates and determining cell values.
 */
public interface Strategy {
    /**
     * Apply this strategy to the board, eliminating candidates or finding values.
     * @param board the sudoku board to solve
     * @return the number of changes made by this strategy application
     */
    int apply(Board board);

    /**
     * @return the name of this strategy
     */
    String getName();

    /**
     * Calculate valid candidates for each cell based on sudoku rules.
     * @param board the sudoku board to analyze
     */
    void calculateCandidates(Board board);
}

package com.example.sudoku.solver;

import com.example.sudoku.Board;

/**
 * Interface for sudoku puzzle solvers.
 */
public interface Solver {
    /**
     * Solve the given sudoku board.
     * @param board the sudoku board to solve, modified in place
     * @return true if the board was fully solved, false if unsolvable
     */
    boolean solve(Board board);
}

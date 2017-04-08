package com.tieto.sudoku.solver.strategy;

import com.tieto.sudoku.Board;

public interface Strategy {
    int apply(Board board);

    String getName();

    void calculateCandidates(Board board);
}

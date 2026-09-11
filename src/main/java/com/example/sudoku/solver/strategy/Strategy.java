package com.example.sudoku.solver.strategy;

import com.example.sudoku.Board;

public interface Strategy {
    int apply(Board board);

    String getName();

    void calculateCandidates(Board board);
}

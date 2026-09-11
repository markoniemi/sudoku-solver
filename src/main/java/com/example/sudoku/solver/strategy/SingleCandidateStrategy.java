package com.example.sudoku.solver.strategy;

import com.example.sudoku.Box;
import com.example.sudoku.Cell;
import com.example.sudoku.Line;

public final class SingleCandidateStrategy extends AbstractStrategy {

    public int applyToLine(Line line) {
        int changeCount = 0;
        for (var location = 0; location < Line.LENGTH; location++) {
            int candidateCount = 0;
            int foundCandidate = Cell.EMPTY;
            Cell cell = line.getCell(location);
            for (var candidate = 1; candidate <= Line.LENGTH; candidate++) {
                if (cell.intValue() == Cell.EMPTY
                        && cell.isCandidate(candidate)) {
                    candidateCount++;
                    foundCandidate = candidate;
                }
            }
            if (candidateCount == 1) {
                line.setValue(location, foundCandidate);
                changeCount++;
            }
        }
        return changeCount;
    }

    public int applyToBox(Box box) {
        return applyToLine(box.asLine());
    }

    @Override
    public String getName() {
        return "SingleCandidateStrategy";
    }
}

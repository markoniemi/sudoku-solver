package com.tieto.sudoku.solver.strategy;

import com.tieto.sudoku.Box;
import com.tieto.sudoku.Cell;
import com.tieto.sudoku.Line;

public class SingleCandidateStrategy extends AbstractStrategy {

    public int applyToLine(Line line) {
        int changeCount = 0;
        for (int location = 0; location < Line.LENGTH; location++) {
            int candidateCount = 0;
            int foundCandidate = Cell.EMPTY;
            Cell cell = line.getCell(location);
            for (int candidate = 1; candidate < 10; candidate++) {
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

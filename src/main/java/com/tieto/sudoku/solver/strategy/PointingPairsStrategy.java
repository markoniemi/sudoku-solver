package com.tieto.sudoku.solver.strategy;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.tieto.sudoku.Board;
import com.tieto.sudoku.Box;
import com.tieto.sudoku.Line;

// TODO refactor apply to use applyToLine
public class PointingPairsStrategy extends AbstractStrategy {

    @Override
    public int apply(Board board) {
        calculateCandidates(board);
        int changeCount = 0;
        // go through all boxes
        for (int boxNumber = 0; boxNumber < Board.BOX_COUNT; boxNumber++) {
            // remove candidates in horizontal lines
            changeCount += removeCandidatesInLines(board, boxNumber,
                    Direction.HORIZONTAL);
            // remove candidates in vertical lines
            changeCount += removeCandidatesInLines(board, boxNumber,
                    Direction.VERTICAL);
        }
        return changeCount;
    }

    private int removeCandidatesInLines(Board board, int boxNumber,
            Direction direction) {
        Box box = board.getBox(boxNumber);
        int row = boxNumber / Box.SIZE;
        int column = boxNumber % Box.SIZE;
        int changeCount = 0;
        // get candidates in a line
        List<CandidateLine> candidatesInLine = getCandidatesInLine(box,
                direction);
        for (CandidateLine candidateLine : candidatesInLine) {
            Line line = null;
            if (direction == Direction.HORIZONTAL) {
                line = board.getRow((row * Box.SIZE) + candidateLine.getLineNumber());
            } else {
                line = board.getColumn((row * Box.SIZE)
                        + candidateLine.getLineNumber());
            }
            // remove candidates on this line, except the ones that are in
            // the same box as the candidateInLine
            boolean otherCandidatesFound = false;
            for (int location = 0; location < Line.LENGTH; location++) {
                // skip values that are in the same box as the
                // candidateInLine
                if (direction == Direction.HORIZONTAL) {
                    if (location / Box.SIZE == column) {
                        continue;
                    }
                } else {
                    if (location / Box.SIZE == row) {
                        continue;
                    }
                }
                if (line.getCell(location).isCandidate(
                        candidateLine.getCandidate())) {
                    otherCandidatesFound = true;
                }
                line.getCell(location).removeCandidate(
                        candidateLine.getCandidate());
            }
            if (otherCandidatesFound) {
                changeCount++;
            }
        }
        return changeCount;
    }

    @Override
    public String getName() {
        return "PointingPairsStrategy";
    }

    /**
     * Returns a list of CandidateRows, each representing a candidate in a row.
     * 
     * @param box
     * @param direction
     *            controls whether vertical or horizontal row are checked.
     * @return
     */
    protected List<CandidateLine> getCandidatesInLine(Box box,
            Direction direction) {
        List<CandidateLine> candidateLines = new ArrayList<CandidateLine>();
        for (int candidate = 1; candidate < 10; candidate++) {
            for (int row = 0; row < Box.SIZE; row++) {
                if (lineHasCandidate(box, row, candidate, direction)) {
                    boolean isCandidatesInLine = true;
                    // If a row has candidate, the other two rows must not.
                    for (int j = 0; j < Box.SIZE; j++) {
                        if (row == j) {
                            continue;
                        } else {
                            if (lineHasCandidate(box, j, candidate, direction)) {
                                isCandidatesInLine = false;
                            }
                        }
                    }
                    if (isCandidatesInLine) {
                        CandidateLine candidateLine = new CandidateLine();
                        candidateLine.setCandidate(candidate);
                        candidateLine.setLineNumber(row);
                        candidateLines.add(candidateLine);
                    }
                }
            }
        }
        return candidateLines;
    }

    private boolean lineHasCandidate(Box box, int lineNumber, int candidate,
            Direction direction) {
        if (direction == Direction.HORIZONTAL) {
            for (int column = 0; column < Box.SIZE; column++) {
                if (box.getCell(lineNumber, column).isCandidate(candidate)) {
                    return true;
                }
            }
            return false;
        } else {
            for (int row = 0; row < Box.SIZE; row++) {
                if (box.getCell(row, lineNumber).isCandidate(candidate)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Data
    protected class CandidateLine {
        private int candidate;
        private int lineNumber;
    }

    protected enum Direction {
        VERTICAL, HORIZONTAL;
    }

    /**
     * strategy overrides apply, therefore applyToLine is not needed.
     */
    @Override
    public int applyToLine(Line line) {
        return 0;
    }
}

package com.tieto.sudoku.solver.strategy;

import com.tieto.sudoku.Board;
import com.tieto.sudoku.Box;
import com.tieto.sudoku.Line;

public sealed abstract class AbstractStrategy implements Strategy permits
    HiddenPairStrategy,
    NakedGroupStrategy,
    NakedPairStrategy,
    PointingPairsStrategy,
    SingleCandidateStrategy,
    SingleLocationStrategy,
    DummyStrategy {
    public abstract int applyToLine(Line line);
    public abstract String getName();
    public int apply(Board board) {
        calculateCandidates(board);
        // go through all lines and boxes
        int changeCount = 0;
        for (var rowNumber = 0; rowNumber < Line.LENGTH; rowNumber++) {
            int changeCountInLine = applyToLine(board.getRow(rowNumber));
            if (changeCountInLine > 0) {
                changeCount = changeCount + changeCountInLine;
                calculateCandidates(board);
            }
        }
        for (var columnNumber = 0; columnNumber < Line.LENGTH; columnNumber++) {
            int changeCountInLine = applyToLine(board.getColumn(columnNumber));
            if (changeCountInLine > 0) {
                changeCount = changeCount + changeCountInLine;
                calculateCandidates(board);
            }
        }
        for (var boxNumber = 0; boxNumber < Board.BOX_COUNT; boxNumber++) {
            int changeCountInBox = applyToLine(board.getBox(boxNumber).asLine());
            if (changeCountInBox > 0) {
                changeCount = changeCount + changeCountInBox;
                calculateCandidates(board);
            }
        }
        return changeCount;
    }

	public void calculateCandidates(Board board) {
		for (var rowNumber = 0; rowNumber < Line.LENGTH; rowNumber++) {
			calculateLineCandidates(board.getRow(rowNumber));
		}
		for (var columnNumber = 0; columnNumber < Line.LENGTH; columnNumber++) {
		    calculateLineCandidates(board.getColumn(columnNumber));
		}
		for (var boxNumber = 0; boxNumber < Board.BOX_COUNT; boxNumber++) {
			calculateBoxCandidates(board.getBox(boxNumber));
		}
	}

	public void calculateLineCandidates(Line line) {
		for (var candidate = 1; candidate < 10; candidate++) {
			for (var location = 0; location < Line.LENGTH; location++) {
				if (line.getCell(location).intValue() == candidate) {
					for (var k = 0; k < Line.LENGTH; k++) {
						line.getCell(k).removeCandidate(candidate);
					}
				}
			}
		}
	}

	public void calculateBoxCandidates(Box box) {
		for (var candidate = 1; candidate < 10; candidate++) {
			for (var row = 0; row < Box.SIZE; row++) {
				for (var column = 0; column < Box.SIZE; column++) {
					if (box.getCell(row, column).intValue() == candidate) {
						for (var k = 0; k < Box.SIZE; k++) {
							for (var l = 0; l < Box.SIZE; l++) {
								box.getCell(k, l).removeCandidate(candidate);
							}
						}
					}
				}
			}
		}
	}
}

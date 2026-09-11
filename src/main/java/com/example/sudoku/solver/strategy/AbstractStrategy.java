package com.example.sudoku.solver.strategy;

import com.example.sudoku.Board;
import com.example.sudoku.Box;
import com.example.sudoku.Line;

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
        int changeCount = 0;
        changeCount += applyToAllLines(board, LineSupplier.ROWS);
        changeCount += applyToAllLines(board, LineSupplier.COLUMNS);
        changeCount += applyToAllLines(board, LineSupplier.BOXES);
        return changeCount;
    }

    private int applyToAllLines(Board board, LineSupplier supplier) {
        int totalChanges = 0;
        for (var index = 0; index < supplier.getCount(board); index++) {
            Line line = supplier.getLine(board, index);
            int changes = applyToLine(line);
            if (changes > 0) {
                totalChanges += changes;
                calculateCandidates(board);
            }
        }
        return totalChanges;
    }

    private interface LineSupplier {
        Line getLine(Board board, int index);
        int getCount(Board board);

        LineSupplier ROWS = new LineSupplier() {
            public Line getLine(Board board, int index) { return board.getRow(index); }
            public int getCount(Board board) { return Line.LENGTH; }
        };

        LineSupplier COLUMNS = new LineSupplier() {
            public Line getLine(Board board, int index) { return board.getColumn(index); }
            public int getCount(Board board) { return Line.LENGTH; }
        };

        LineSupplier BOXES = new LineSupplier() {
            public Line getLine(Board board, int index) { return board.getBox(index).asLine(); }
            public int getCount(Board board) { return Board.BOX_COUNT; }
        };
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
		for (var candidate = 1; candidate <= Line.LENGTH; candidate++) {
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
		for (var candidate = 1; candidate <= Line.LENGTH; candidate++) {
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

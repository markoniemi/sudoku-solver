package com.tieto.sudoku.solver.strategy;

import com.tieto.sudoku.Box;
import com.tieto.sudoku.Cell;
import com.tieto.sudoku.Line;

public final class SingleLocationStrategy extends AbstractStrategy{

	public int applyToLine(Line line) {
		int changeCount = 0;
		for (var candidate = 1; candidate < 10; candidate++) {
			int locationCount = 0;
			int foundLocation = 0;
			for (var location = 0; location < Line.LENGTH; location++) {
				Cell cell = line.getCell(location);
				if (cell.intValue() == Cell.EMPTY
						&& cell.isCandidate(candidate)) {
					locationCount++;
					foundLocation = location;
				}
			}
			if (locationCount == 1) {
				line.setValue(foundLocation, candidate);
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
		return "SingleLocationStrategy";
	}
}

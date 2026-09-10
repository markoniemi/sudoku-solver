package com.tieto.sudoku.solver.strategy;

import com.tieto.sudoku.Board;
import com.tieto.sudoku.Line;

public final class DummyStrategy extends AbstractStrategy {

	@Override
	public String getName() {
		return "DummyStrategy";
	}

	@Override
	public int apply(Board board) {
		return 0;
	}

	@Override
	public int applyToLine(Line line) {
		return 0;
	}

}

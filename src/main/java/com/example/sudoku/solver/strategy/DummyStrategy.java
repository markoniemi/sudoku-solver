package com.example.sudoku.solver.strategy;

import com.example.sudoku.Board;
import com.example.sudoku.Line;

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

package com.tieto.sudoku.solver.strategy;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.tieto.sudoku.Board;
import com.tieto.sudoku.Box;
import com.tieto.sudoku.Line;

public class StrategyTest {

	@Test
	public void testCalculateLineCandidates() {
		Line line = new Line();
		line.setValue(0, 1);
		DummyStrategy dummyStrategy = new DummyStrategy();
		dummyStrategy.calculateLineCandidates(line);
		for (int location = 1; location < 9; location++) {
			Assert.assertFalse(line.getCell(location).isCandidate(1));
			Assert.assertTrue(line.getCell(location).isCandidate(2));
		}
	}

	@Test
	public void testCalculateBoxCandidates() {
		Box box = new Box();
		box.setValue(0, 0, 1);
		DummyStrategy dummyStrategy = new DummyStrategy();
		dummyStrategy.calculateBoxCandidates(box);
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				if (!(row == 0 && column == 0)) {
					Assert
							.assertFalse(box.getCell(row, column).isCandidate(
									1));
					Assert.assertTrue(box.getCell(row, column).isCandidate(2));
				}
			}
		}
	}

	@Ignore
	@Test
	public void testCalculateCandidates() {
		Board board = new Board();
		board.setValue(0, 0, 1);
		DummyStrategy dummyStrategy = new DummyStrategy();
		dummyStrategy.calculateCandidates(board);
		Line line = board.getColumn(0);
		for (int location = 1; location < 9; location++) {
			Assert.assertFalse(line.getCell(location).isCandidate(1));
			Assert.assertTrue(line.getCell(location).isCandidate(2));
		}
		line = board.getRow(9);
		for (int location = 1; location < 9; location++) {
			Assert.assertFalse(line.getCell(location).isCandidate(1));
			Assert.assertTrue(line.getCell(location).isCandidate(2));
		}
		Box box = board.getBox(0);
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				if (!(row == 0 && column == 0)) {
					Assert
							.assertFalse(box.getCell(row, column).isCandidate(
									1));
					Assert.assertTrue(box.getCell(row, column).isCandidate(2));
				}
			}
		}
	}

	private class DummyStrategy extends AbstractStrategy {

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
            // TODO Auto-generated method stub
            return 0;
        }

	}
}

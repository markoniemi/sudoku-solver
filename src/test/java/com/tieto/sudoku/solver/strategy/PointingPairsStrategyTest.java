package com.tieto.sudoku.solver.strategy;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.tieto.sudoku.Board;
import com.tieto.sudoku.Box;
import com.tieto.sudoku.solver.strategy.PointingPairsStrategy.CandidateLine;
import com.tieto.sudoku.solver.strategy.PointingPairsStrategy.Direction;

public class PointingPairsStrategyTest {

	@Test
	public void testGetCandidatesInRow() {
		Box box = new Box();
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				box.getCell(row, column).cleanCandidates();
			}
		}
		// 1 is not a candidate in line
		box.getCell(0, 0).setCandidate(1);
		box.getCell(1, 0).setCandidate(1);
		box.getCell(0, 1).setCandidate(1);
		// 2 is a candidate in horizontal line
		box.getCell(0, 0).setCandidate(2);
		box.getCell(0, 1).setCandidate(2);
		// 3 is a candidate in vertical line
		box.getCell(1, 1).setCandidate(3);
		box.getCell(2, 1).setCandidate(3);
		// 4 is a single location candidate, but should be calculated as both
		// vertical and horizontal line candidate
		box.getCell(1, 1).setCandidate(4);
		// 5 is a candidate in horizontal line
		box.getCell(2, 0).setCandidate(5);
		box.getCell(2, 2).setCandidate(5);
		// 6 is a candidate in vertical line
		box.getCell(0, 2).setCandidate(6);
		box.getCell(1, 2).setCandidate(6);
		box.getCell(2, 2).setCandidate(6);
		// 7 is not a candidate in line
		box.getCell(0, 2).setCandidate(7);
		box.getCell(1, 2).setCandidate(7);
		box.getCell(2, 1).setCandidate(7);
		// 8 is not a candidate in line
		box.getCell(0, 0).setCandidate(8);
		box.getCell(0, 1).setCandidate(8);
		box.getCell(1, 0).setCandidate(8);
		box.getCell(1, 1).setCandidate(8);
		PointingPairsStrategy pointingPairsStrategy = new PointingPairsStrategy();
		List<CandidateLine> candidatesInRow = pointingPairsStrategy
				.getCandidatesInLine(box, Direction.HORIZONTAL);
		Assert.assertEquals(3, candidatesInRow.size());
		Assert.assertEquals(2, candidatesInRow.get(0).getCandidate());
		Assert.assertEquals(4, candidatesInRow.get(1).getCandidate());
		Assert.assertEquals(5, candidatesInRow.get(2).getCandidate());
		candidatesInRow = pointingPairsStrategy.getCandidatesInLine(box, Direction.VERTICAL);
		Assert.assertEquals(3, candidatesInRow.size());
		Assert.assertEquals(3, candidatesInRow.get(0).getCandidate());
		Assert.assertEquals(4, candidatesInRow.get(1).getCandidate());
		Assert.assertEquals(6, candidatesInRow.get(2).getCandidate());
	}

	@Test
	public void testApply() {
		Board board = new Board();
		// clean all candidates and set 7 and 8 to as candidate to whole board
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				board.getCell(row, column).cleanCandidates();
				board.getCell(row, column).setCandidate(2);
				board.getCell(row, column).setCandidate(3);
				board.getCell(row, column).setCandidate(4);
				board.getCell(row, column).setCandidate(5);
				board.getCell(row, column).setCandidate(6);
			}
		}
		// but clean them from the first box
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				board.getCell(row, column).removeCandidate(2);
				board.getCell(row, column).removeCandidate(3);
				board.getCell(row, column).removeCandidate(4);
				board.getCell(row, column).removeCandidate(5);
				board.getCell(row, column).removeCandidate(6);
			}
		}
		// set candidates to first box
		// 1 is not a candidate in line
		board.getCell(0, 0).setCandidate(1);
		board.getCell(1, 0).setCandidate(1);
		board.getCell(0, 1).setCandidate(1);
		// 2 is a candidate in horizontal line
		board.getCell(0, 0).setCandidate(2);
		board.getCell(0, 1).setCandidate(2);
		// 3 is a candidate in vertical line
		board.getCell(1, 1).setCandidate(3);
		board.getCell(2, 1).setCandidate(3);
		// 4 is a single location candidate, but should be calculated as both
		// vertical and horizontal line candidate
		board.getCell(1, 1).setCandidate(4);
		// 5 is a candidate in horizontal line
		board.getCell(2, 0).setCandidate(5);
		board.getCell(2, 2).setCandidate(5);
		// 6 is a candidate in vertical line
		board.getCell(0, 2).setCandidate(6);
		board.getCell(1, 2).setCandidate(6);
		board.getCell(2, 2).setCandidate(6);
		// 7 is not a candidate in line
		board.getCell(0, 2).setCandidate(7);
		board.getCell(1, 2).setCandidate(7);
		board.getCell(2, 1).setCandidate(7);
		// 8 is not a candidate in line
		board.getCell(0, 0).setCandidate(8);
		board.getCell(0, 1).setCandidate(8);
		board.getCell(1, 0).setCandidate(8);
		board.getCell(1, 1).setCandidate(8);
		PointingPairsStrategy pointingPairsStrategy = new PointingPairsStrategy();
		Assert.assertEquals(6, pointingPairsStrategy.apply(board));
		// horizontal lines
		Assert.assertFalse(board.getCell(0, 4).isCandidate(2));
		Assert.assertFalse(board.getCell(1, 4).isCandidate(4));
		Assert.assertFalse(board.getCell(2, 4).isCandidate(5));
		// vertical lines
		Assert.assertFalse(board.getCell(4, 1).isCandidate(3));
		Assert.assertFalse(board.getCell(4, 1).isCandidate(4));
		Assert.assertFalse(board.getCell(4, 2).isCandidate(6));
		// another call should yield nothing
		Assert.assertEquals(0, pointingPairsStrategy.apply(board));
	}
}

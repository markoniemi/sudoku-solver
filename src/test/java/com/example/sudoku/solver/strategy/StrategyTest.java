package com.example.sudoku.solver.strategy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.example.sudoku.Board;
import com.example.sudoku.Box;
import com.example.sudoku.Line;

public class StrategyTest {

	@Test
	public void testCalculateLineCandidates() {
		Line line = new Line();
		line.setValue(0, 1);
		DummyStrategy dummyStrategy = new DummyStrategy();
		dummyStrategy.calculateLineCandidates(line);
		for (int location = 1; location < 9; location++) {
			assertFalse(line.getCell(location).isCandidate(1));
			assertTrue(line.getCell(location).isCandidate(2));
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
					assertFalse(box.getCell(row, column).isCandidate(1));
					assertTrue(box.getCell(row, column).isCandidate(2));
				}
			}
		}
	}

	@Test
	public void testCalculateCandidates() {
		Board board = new Board();
		board.setValue(0, 0, 1);
		DummyStrategy dummyStrategy = new DummyStrategy();
		dummyStrategy.calculateCandidates(board);
		Line line = board.getColumn(0);
		for (int location = 1; location < Line.LENGTH; location++) {
			assertFalse(line.getCell(location).isCandidate(1));
			assertTrue(line.getCell(location).isCandidate(2));
		}
		line = board.getRow(0);
		for (int location = 1; location < Line.LENGTH; location++) {
			assertFalse(line.getCell(location).isCandidate(1));
			assertTrue(line.getCell(location).isCandidate(2));
		}
		Box box = board.getBox(0);
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				if (!(row == 0 && column == 0)) {
					assertFalse(box.getCell(row, column).isCandidate(1));
					assertTrue(box.getCell(row, column).isCandidate(2));
				}
			}
		}
	}

}

package com.example.sudoku.solver.strategy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.sudoku.Box;
import com.example.sudoku.Line;
import com.example.sudoku.Cell;

public class HiddenPairStrategyTest {
	@Test
	public void testFindPairsInLine() {
		Line line = new Line();
		for (int location = 0; location < 9; location++) {
			Cell cell = line.getCell(location);
			cell.cleanCandidates();
			cell.setCandidates(1, 2, 9);
		}
		line.getCell(0).setCandidate(3);
		line.getCell(1).setCandidates(3, 4);
		line.getCell(2).setCandidate(4);
		line.setValue(3, 5);
		line.setValue(4, 6);
		line.getCell(5).setCandidates(7, 8);
		line.getCell(6).setCandidates(7, 8);
		HiddenPairStrategy strategy = new HiddenPairStrategy();
		List<com.example.sudoku.solver.strategy.HiddenPairStrategy.LinePair> pairs = strategy
				.findPairs(line);
		assertEquals(4, pairs.size());
		assertEquals(3, pairs.get(0).getValue());
		assertEquals(4, pairs.get(1).getValue());
		assertEquals(7, pairs.get(2).getValue());
		assertEquals(8, pairs.get(3).getValue());
	}

	@Test
	public void testFindPairs() {
		Box box = new Box();
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				Cell cell = box.getCell(row, column);
				cell.cleanCandidates();
				cell.setCandidates(1, 2, 9);
			}
		}
		box.getCell(0, 0).setCandidate(3);
		box.getCell(0, 1).setCandidates(3, 4);
		box.getCell(0, 2).setCandidate(4);
		box.setValue(1, 0, 5);
		box.setValue(1, 1, 6);
		box.getCell(1, 2).setCandidates(7, 8);
		box.getCell(2, 0).setCandidates(7, 8);
		HiddenPairStrategy strategy = new HiddenPairStrategy();
		Line line = box.asLine();
		List<com.example.sudoku.solver.strategy.HiddenPairStrategy.LinePair> pairs = strategy
				.findPairs(line);
		assertEquals(4, pairs.size());
		assertEquals(3, pairs.get(0).getValue());
		assertEquals(4, pairs.get(1).getValue());
		assertEquals(7, pairs.get(2).getValue());
		assertEquals(8, pairs.get(3).getValue());
	}

	@Test
	public void testHiddenPairInLine() {
		Line line = new Line();
		for (int location = 0; location < 9; location++) {
			Cell cell = line.getCell(location);
			cell.cleanCandidates();
			cell.setCandidates(1, 2, 9);
		}
		line.getCell(0).setCandidate(3);
		line.getCell(1).setCandidates(3, 4);
		line.getCell(2).setCandidate(4);
		line.setValue(3, 5);
		line.setValue(4, 6);
		line.getCell(5).setCandidates(7, 8);
		line.getCell(6).setCandidates(7, 8);
		HiddenPairStrategy strategy = new HiddenPairStrategy();
		assertEquals(1, strategy.applyToLine(line));
		assertFalse(line.getCell(5).isCandidate(1));
		assertFalse(line.getCell(5).isCandidate(2));
		assertFalse(line.getCell(5).isCandidate(3));
		assertFalse(line.getCell(5).isCandidate(4));
		assertFalse(line.getCell(5).isCandidate(5));
		assertFalse(line.getCell(5).isCandidate(6));
		assertTrue(line.getCell(5).isCandidate(7));
		assertTrue(line.getCell(5).isCandidate(8));
		assertFalse(line.getCell(5).isCandidate(9));
		assertFalse(line.getCell(6).isCandidate(1));
		assertFalse(line.getCell(6).isCandidate(2));
		assertFalse(line.getCell(6).isCandidate(3));
		assertFalse(line.getCell(6).isCandidate(4));
		assertFalse(line.getCell(6).isCandidate(5));
		assertFalse(line.getCell(6).isCandidate(6));
		assertTrue(line.getCell(6).isCandidate(7));
		assertTrue(line.getCell(6).isCandidate(8));
		assertFalse(line.getCell(6).isCandidate(9));
		// run again to see if the method returns changeCount == 0
		assertEquals(0, strategy.applyToLine(line));
	}

	@Test
	public void testHiddenPairInBox() {
		Box box = new Box();
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				Cell cell = box.getCell(row, column);
				cell.cleanCandidates();
				cell.setCandidates(1, 2, 9);
			}
		}
		box.getCell(0, 0).setCandidate(3);
		box.getCell(0, 1).setCandidates(3, 4);
		box.getCell(0, 2).setCandidate(4);
		box.setValue(1, 0, 5);
		box.setValue(1, 1, 6);
		box.getCell(1, 2).setCandidates(7, 8);
		box.getCell(2, 0).setCandidates(7, 8);
		HiddenPairStrategy strategy = new HiddenPairStrategy();
		assertEquals(1, strategy.applyToLine(box.asLine()));
		assertFalse(box.getCell(1, 2).isCandidate(1));
		assertFalse(box.getCell(1, 2).isCandidate(2));
		assertFalse(box.getCell(1, 2).isCandidate(3));
		assertFalse(box.getCell(1, 2).isCandidate(4));
		assertFalse(box.getCell(1, 2).isCandidate(5));
		assertFalse(box.getCell(1, 2).isCandidate(6));
		assertTrue(box.getCell(1, 2).isCandidate(7));
		assertTrue(box.getCell(1, 2).isCandidate(8));
		assertFalse(box.getCell(1, 2).isCandidate(9));
		assertFalse(box.getCell(2, 0).isCandidate(1));
		assertFalse(box.getCell(2, 0).isCandidate(2));
		assertFalse(box.getCell(2, 0).isCandidate(3));
		assertFalse(box.getCell(2, 0).isCandidate(4));
		assertFalse(box.getCell(2, 0).isCandidate(5));
		assertFalse(box.getCell(2, 0).isCandidate(6));
		assertTrue(box.getCell(2, 0).isCandidate(7));
		assertTrue(box.getCell(2, 0).isCandidate(8));
		assertFalse(box.getCell(2, 0).isCandidate(9));
		// run again to see if the method returns changeCount == 0
		assertEquals(0, strategy.applyToLine(box.asLine()));
	}
}

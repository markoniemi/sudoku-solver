package com.tieto.sudoku.solver.strategy;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.tieto.sudoku.Box;
import com.tieto.sudoku.Line;
import com.tieto.sudoku.Cell;

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
		List<com.tieto.sudoku.solver.strategy.HiddenPairStrategy.LinePair> pairs = strategy
				.findPairs(line);
		Assert.assertEquals(4, pairs.size());
		Assert.assertEquals(3, pairs.get(0).getValue());
		Assert.assertEquals(4, pairs.get(1).getValue());
		Assert.assertEquals(7, pairs.get(2).getValue());
		Assert.assertEquals(8, pairs.get(3).getValue());
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
		List<com.tieto.sudoku.solver.strategy.HiddenPairStrategy.LinePair> pairs = strategy
				.findPairs(line);
		Assert.assertEquals(4, pairs.size());
		Assert.assertEquals(3, pairs.get(0).getValue());
		Assert.assertEquals(4, pairs.get(1).getValue());
		Assert.assertEquals(7, pairs.get(2).getValue());
		Assert.assertEquals(8, pairs.get(3).getValue());
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
		Assert.assertEquals(1, strategy.applyToLine(line));
		Assert.assertFalse(line.getCell(5).isCandidate(1));
		Assert.assertFalse(line.getCell(5).isCandidate(2));
		Assert.assertFalse(line.getCell(5).isCandidate(3));
		Assert.assertFalse(line.getCell(5).isCandidate(4));
		Assert.assertFalse(line.getCell(5).isCandidate(5));
		Assert.assertFalse(line.getCell(5).isCandidate(6));
		Assert.assertTrue(line.getCell(5).isCandidate(7));
		Assert.assertTrue(line.getCell(5).isCandidate(8));
		Assert.assertFalse(line.getCell(5).isCandidate(9));
		Assert.assertFalse(line.getCell(6).isCandidate(1));
		Assert.assertFalse(line.getCell(6).isCandidate(2));
		Assert.assertFalse(line.getCell(6).isCandidate(3));
		Assert.assertFalse(line.getCell(6).isCandidate(4));
		Assert.assertFalse(line.getCell(6).isCandidate(5));
		Assert.assertFalse(line.getCell(6).isCandidate(6));
		Assert.assertTrue(line.getCell(6).isCandidate(7));
		Assert.assertTrue(line.getCell(6).isCandidate(8));
		Assert.assertFalse(line.getCell(6).isCandidate(9));
		// run again to see if the method returns changeCount == 0
		Assert.assertEquals(0, strategy.applyToLine(line));
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
		Assert.assertEquals(1, strategy.applyToLine(box.asLine()));
		Assert.assertFalse(box.getCell(1, 2).isCandidate(1));
		Assert.assertFalse(box.getCell(1, 2).isCandidate(2));
		Assert.assertFalse(box.getCell(1, 2).isCandidate(3));
		Assert.assertFalse(box.getCell(1, 2).isCandidate(4));
		Assert.assertFalse(box.getCell(1, 2).isCandidate(5));
		Assert.assertFalse(box.getCell(1, 2).isCandidate(6));
		Assert.assertTrue(box.getCell(1, 2).isCandidate(7));
		Assert.assertTrue(box.getCell(1, 2).isCandidate(8));
		Assert.assertFalse(box.getCell(1, 2).isCandidate(9));
		Assert.assertFalse(box.getCell(2, 0).isCandidate(1));
		Assert.assertFalse(box.getCell(2, 0).isCandidate(2));
		Assert.assertFalse(box.getCell(2, 0).isCandidate(3));
		Assert.assertFalse(box.getCell(2, 0).isCandidate(4));
		Assert.assertFalse(box.getCell(2, 0).isCandidate(5));
		Assert.assertFalse(box.getCell(2, 0).isCandidate(6));
		Assert.assertTrue(box.getCell(2, 0).isCandidate(7));
		Assert.assertTrue(box.getCell(2, 0).isCandidate(8));
		Assert.assertFalse(box.getCell(2, 0).isCandidate(9));
		// run again to see if the method returns changeCount == 0
		Assert.assertEquals(0, strategy.applyToLine(box.asLine()));
	}
}

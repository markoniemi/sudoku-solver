package com.tieto.sudoku.solver.strategy;

import java.util.List;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import com.tieto.sudoku.Box;
import com.tieto.sudoku.Line;
import com.tieto.sudoku.solver.strategy.NakedPairStrategy.CandidatePair;

public class NakedPairStrategyTest {
	@Test
	public void testFindPairs() {
		Line line = new Line();
		for (int location = 0; location < 9; location++) {
			line.getCell(location).cleanCandidates();
		}
		line.getCell(0).setCandidate(3);
		line.getCell(1).setCandidates(3,4);
		line.getCell(2).setCandidates(1,4,7);
		line.getCell(3).setCandidates(1,2,3);
		line.getCell(5).setCandidates(7,8);
		line.getCell(6).setCandidates(7,8);
		line.getCell(7).setCandidates(1,8,9);
		NakedPairStrategy nakedPairStrategy = new NakedPairStrategy();
		List<CandidatePair> pairs = nakedPairStrategy.findCandidatePairs(line);
		Assertions.assertEquals(3, pairs.size());
		Assertions.assertEquals(1, pairs.get(0).getLocation());
		Assertions.assertEquals(5, pairs.get(1).getLocation());
		Assertions.assertEquals(6, pairs.get(2).getLocation());
	}

	@Test
	public void testApplyToLine() {
		Line line = new Line();
		for (int location = 0; location < 9; location++) {
			line.getCell(location).cleanCandidates();
		}
		line.getCell(0).setCandidate(3);
		line.getCell(1).setCandidates(3,4);
		line.getCell(2).setCandidates(4,7);
		line.getCell(3).setCandidates(1,2,3);
		line.getCell(5).setCandidates(7,8);
		line.getCell(6).setCandidates(7,8);
		line.getCell(7).setCandidates(1,8,9);
		NakedPairStrategy nakedPairStrategy = new NakedPairStrategy();
		Assertions.assertEquals(2, nakedPairStrategy.applyToLine(line));
		Assertions.assertFalse(line.getCell(2).isCandidate(7));
		Assertions.assertFalse(line.getCell(7).isCandidate(8));
	}
	@Test
	public void testApplyToLine2() {
	    Line line = new Line();
	    for (int location = 0; location < 9; location++) {
	        line.getCell(location).cleanCandidates();
	    }
	    line.getCell(0).setCandidates(3,4);
	    line.getCell(1).setCandidates(3,4);
	    line.getCell(2).setCandidates(4,7);
	    line.getCell(3).setCandidates(1,2,3);
	    line.getCell(5).setCandidates(7,8);
	    line.getCell(6).setCandidates(7,8);
	    line.getCell(7).setCandidates(1,8,9);
	    NakedPairStrategy nakedPairStrategy = new NakedPairStrategy();
	    Assertions.assertEquals(4, nakedPairStrategy.applyToLine(line));
	    Assertions.assertFalse(line.getCell(2).isCandidate(7));
	    Assertions.assertFalse(line.getCell(7).isCandidate(8));
	    Assertions.assertFalse(line.getCell(2).isCandidate(4));
	    Assertions.assertFalse(line.getCell(3).isCandidate(3));
	}

	@Test
	public void testApplyToBox() {
		Box box = new Box();
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				box.getCell(row, column).cleanCandidates();
			}
		}
		box.getCell(0, 0).setCandidate(3);
		box.getCell(0, 1).setCandidates(3,4);
		box.getCell(0, 2).setCandidates(4,7);
		box.getCell(1, 0).setCandidates(1,2,3);
		box.getCell(1, 2).setCandidates(7,8);
		box.getCell(2, 0).setCandidates(7,8);
		box.getCell(2, 1).setCandidates(1,8,9);
		NakedPairStrategy nakedPairStrategy = new NakedPairStrategy();
		Assertions.assertEquals(2, nakedPairStrategy.applyToLine(box.asLine()));
		Assertions.assertFalse(box.getCell(0, 2).isCandidate(7));
		Assertions.assertFalse(box.getCell(2, 1).isCandidate(8));
	}
}

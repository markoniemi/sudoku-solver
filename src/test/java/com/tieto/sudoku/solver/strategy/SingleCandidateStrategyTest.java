package com.tieto.sudoku.solver.strategy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.tieto.sudoku.Board;
import com.tieto.sudoku.Box;
import com.tieto.sudoku.Cell;
import com.tieto.sudoku.Line;
import com.tieto.sudoku.reader.SDKReader;

public class SingleCandidateStrategyTest {
	@Test
	public void testSingleCandidateInLine() {
		Line line = new Line();
		for (int location = 0; location < 9; location++) {
			line.setValue(location, location + 1);
		}
		// fill all but one
		line.setValue(8, Cell.EMPTY);
		SingleCandidateStrategy strategy = new SingleCandidateStrategy();
		strategy.calculateLineCandidates(line);
		Assert.assertEquals(1, strategy.applyToLine(line));
		Assert.assertEquals(9, line.getCell(8).intValue());
	}

	@Test
	public void testSingleCandidateInBox() {
		Box box = new Box();
		// fill all but one
		int value = 1;
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				box.setValue(row, column, value);
				value++;
			}
		}
		box.setValue(2, 2, Cell.EMPTY);
		SingleCandidateStrategy strategy = new SingleCandidateStrategy();
		strategy.calculateBoxCandidates(box);
		Assert.assertEquals(1, strategy.applyToBox(box));
		Assert.assertEquals(9, box.getCell(2, 2).intValue());
	}

	@Test
	public void testSingleCandidate() throws FileNotFoundException, IOException {
	    Board board = new SDKReader().read(new FileInputStream("src/test/resources/testData1.sdk"));
		board.setValue(0, 0, Cell.EMPTY);
		SingleCandidateStrategy strategy = new SingleCandidateStrategy();
		strategy.calculateCandidates(board);
		Assert.assertEquals(1, strategy.apply(board));
		Assert.assertEquals(9, board.getCell(0, 0).intValue());
	}
}

package com.tieto.sudoku.solver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;
import lombok.extern.log4j.Log4j;

import org.junit.Ignore;
import org.junit.Test;

import com.tieto.sudoku.Board;
import com.tieto.sudoku.Cell;
import com.tieto.sudoku.reader.SDKReader;

@Log4j
public class BruteForceSolverTest {

	@Test
	public void solve() throws FileNotFoundException, IOException {
		// test with first two values as empty
	    Board board = new SDKReader().read(new FileInputStream("src/test/resources/testData1.sdk"));
		board.setValue(0, 0, Cell.EMPTY);
		board.getCell(0, 0).setClue(false);
		board.setValue(0, 1, Cell.EMPTY);
		board.getCell(0, 1).setClue(false);
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		Assert.assertTrue(bruteForceSolver.solve(board));
		Assert.assertEquals(9, board.getCell(0, 0).intValue());
		Assert.assertEquals(4, board.getCell(0, 1).intValue());
		// test with last two values as empty
		board = new SDKReader().read(new FileInputStream("src/test/resources/testData1.sdk"));
		board.setValue(8, 7, Cell.EMPTY);
		board.getCell(8, 7).setClue(false);
		board.setValue(8, 8, Cell.EMPTY);
		board.getCell(8, 8).setClue(false);
		bruteForceSolver = new BruteForceSolver();
		Assert.assertTrue(bruteForceSolver.solve(board));
		Assert.assertEquals(6, board.getCell(8, 7).intValue());
		Assert.assertEquals(1, board.getCell(8, 8).intValue());
	}

	@Test
//	@Ignore
	public void solveWithEmptyBoard() throws FileNotFoundException, IOException {
	    Board board = new SDKReader().read(new FileInputStream("src/test/resources/testData1.sdk"));
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		boolean solved = bruteForceSolver.solve(board);
		if (!solved) {
			log.debug("empty" + board.toString());
		}
		Assert.assertTrue(solved);
		Assert.assertTrue(board.isLegal());
	}

	@Test
	public void solveWithTestData3() throws FileNotFoundException, IOException {
		Board board = new SDKReader().read(new FileInputStream("src/test/resources/testData3.sdk"));
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		boolean solved = bruteForceSolver.solve(board);
		if (!solved) {
			log.debug("testData3" + board.toString());
		}
		Assert.assertEquals(9, board.getCell(0, 8).getValue());
		Assert.assertTrue(solved);
		Assert.assertTrue(board.isLegal());
	}

	@Test
	public void solveWithEasiest() throws FileNotFoundException, IOException {
		Board board = new SDKReader().read(new FileInputStream("src/test/resources/easiest.sdk"));
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		boolean solved = bruteForceSolver.solve(board);
		if (!solved) {
			log.debug("easiest" + board.toString());
		}
		Assert.assertEquals(8, board.getCell(0, 8).getValue());
		Assert.assertTrue(solved);
		Assert.assertTrue(board.isLegal());
	}

	@Test
	public void solveWithGentle() throws FileNotFoundException, IOException {
		Board board = new SDKReader().read(new FileInputStream("src/test/resources/gentle.sdk"));
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		boolean solved = bruteForceSolver.solve(board);
		if (!solved) {
			log.debug("gentle" + board.toString());
		}
		Assert.assertEquals(7, board.getCell(0, 0).getValue());
		Assert.assertTrue(solved);
		Assert.assertTrue(board.isLegal());
	}

	@Test
	public void solveWithModerate() throws FileNotFoundException, IOException {
		Board board = new SDKReader().read(new FileInputStream("src/test/resources/moderate.sdk"));
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		boolean solved = bruteForceSolver.solve(board);
		if (!solved) {
			log.debug("moderate" + board.toString());
		}
		Assert.assertEquals(2, board.getCell(0, 8).getValue());
		Assert.assertTrue(solved);
		Assert.assertTrue(board.isLegal());
	}

	@Test
	@Ignore
	public void solveWithDaily() throws FileNotFoundException, IOException {
	    Board board = new SDKReader().read(new FileInputStream("src/test/resources/daily.sdk"));
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		boolean solved = bruteForceSolver.solve(board);
		if (!solved) {
			log.debug("daily" + board.toString());
		}
		Assert.assertTrue(solved);
		Assert.assertTrue(board.isLegal());
	}

	@Test
	public void findAllSolutions() throws FileNotFoundException, IOException {
	    Board board = new SDKReader().read(new FileInputStream("src/test/resources/testData1.sdk"));
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		List<Board> solutions = bruteForceSolver.findAllSolutions(board);
		Assert.assertEquals(1, solutions.size());
		Assert.assertTrue(board.isLegal());
	}
}

package com.example.sudoku.solver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import lombok.extern.log4j.Log4j;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.example.sudoku.Board;
import com.example.sudoku.Cell;
import com.example.sudoku.reader.SDKReader;

@Log4j
public class BruteForceSolverTest {

	@Test
	public void solve() throws IOException {
		// test with first two values as empty
	    Board board = new SDKReader().read(new FileInputStream("src/test/resources/testData1.sdk"));
		board.setValue(0, 0, Cell.EMPTY);
		board.getCell(0, 0).setClue(false);
		board.setValue(0, 1, Cell.EMPTY);
		board.getCell(0, 1).setClue(false);
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		assertTrue(bruteForceSolver.solve(board));
		assertEquals(9, board.getCell(0, 0).intValue());
		assertEquals(4, board.getCell(0, 1).intValue());
		// test with last two values as empty
		board = new SDKReader().read(new FileInputStream("src/test/resources/testData1.sdk"));
		board.setValue(8, 7, Cell.EMPTY);
		board.getCell(8, 7).setClue(false);
		board.setValue(8, 8, Cell.EMPTY);
		board.getCell(8, 8).setClue(false);
		bruteForceSolver = new BruteForceSolver();
		assertTrue(bruteForceSolver.solve(board));
		assertEquals(6, board.getCell(8, 7).intValue());
		assertEquals(1, board.getCell(8, 8).intValue());
	}

	@Test
//	@Disabled
	public void solveWithEmptyBoard() throws IOException {
	    Board board = new SDKReader().read(new FileInputStream("src/test/resources/testData1.sdk"));
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		boolean solved = bruteForceSolver.solve(board);
		if (!solved) {
			log.debug("empty" + board.toString());
		}
		assertTrue(solved);
		assertTrue(board.isLegal());
	}

	@Test
	public void solveWithTestData3() throws IOException {
		Board board = new SDKReader().read(new FileInputStream("src/test/resources/testData3.sdk"));
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		boolean solved = bruteForceSolver.solve(board);
		if (!solved) {
			log.debug("testData3" + board.toString());
		}
		assertEquals(9, board.getCell(0, 8).getValue());
		assertTrue(solved);
		assertTrue(board.isLegal());
	}

	@Test
	public void solveWithEasiest() throws IOException {
		Board board = new SDKReader().read(new FileInputStream("src/test/resources/easiest.sdk"));
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		boolean solved = bruteForceSolver.solve(board);
		if (!solved) {
			log.debug("easiest" + board.toString());
		}
		assertEquals(8, board.getCell(0, 8).getValue());
		assertTrue(solved);
		assertTrue(board.isLegal());
	}

	@Test
	public void solveWithGentle() throws IOException {
		Board board = new SDKReader().read(new FileInputStream("src/test/resources/gentle.sdk"));
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		boolean solved = bruteForceSolver.solve(board);
		if (!solved) {
			log.debug("gentle" + board.toString());
		}
		assertEquals(7, board.getCell(0, 0).getValue());
		assertTrue(solved);
		assertTrue(board.isLegal());
	}

	@Test
	public void solveWithModerate() throws IOException {
		Board board = new SDKReader().read(new FileInputStream("src/test/resources/moderate.sdk"));
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		boolean solved = bruteForceSolver.solve(board);
		if (!solved) {
			log.debug("moderate" + board.toString());
		}
		assertEquals(2, board.getCell(0, 8).getValue());
		assertTrue(solved);
		assertTrue(board.isLegal());
	}

	@Test
	@Disabled("Performance test - takes too long")
	public void solveWithDaily() throws IOException {
	    Board board = new SDKReader().read(new FileInputStream("src/test/resources/daily.sdk"));
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		boolean solved = bruteForceSolver.solve(board);
		if (!solved) {
			log.debug("daily" + board.toString());
		}
		assertTrue(solved);
		assertTrue(board.isLegal());
	}

	@Test
	public void findAllSolutions() throws IOException {
	    Board board = new SDKReader().read(new FileInputStream("src/test/resources/testData1.sdk"));
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		List<Board> solutions = bruteForceSolver.findAllSolutions(board);
		assertEquals(1, solutions.size());
		assertTrue(board.isLegal());
	}
}

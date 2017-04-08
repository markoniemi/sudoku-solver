package com.tieto.sudoku.solver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.Assert;
import lombok.extern.log4j.Log4j;

import org.junit.Ignore;
import org.junit.Test;

import com.tieto.sudoku.Board;
import com.tieto.sudoku.BoardToTextConverter;
import com.tieto.sudoku.reader.SDKReader;

@Log4j
public class StrategySolverTest {

	@Test
	public void testSolveEasiest() throws FileNotFoundException, IOException {
		// solve easiest board, which also has a solved version in test data
        Board board = new SDKReader().read(new FileInputStream("src/test/resources/easiest.sdk"));
		StrategySolver strategySolver = new StrategySolver();
		Assert.assertTrue(strategySolver.solve(board));
		Board solvedBoard = new SDKReader().read(new FileInputStream("src/test/resources/easiestSolved.sdk"));
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				Assert.assertEquals(solvedBoard.getCell(row,column).getValue(), board
						.getCell(row, column).intValue());
			}
		}
	}

	@Test
	public void testSolveGentle() throws FileNotFoundException, IOException {
		// solve gentle board, use BruteForceSolver to obtain solved board
	    Board board = new SDKReader().read(new FileInputStream("src/test/resources/gentle.sdk"));
		// log.debug("gentle before solving\n" +
		// board.printBoardWithCandidates());
		StrategySolver strategySolver = new StrategySolver();
		boolean solved = strategySolver.solve(board);
		if (!solved) {
		    BoardToTextConverter toTextConverter = new BoardToTextConverter();
			log.debug("gentle" + toTextConverter.convert(board));
		}
		Assert.assertTrue(solved);
        Board solvedBoard = new SDKReader().read(new FileInputStream("src/test/resources/gentleSolved.sdk"));
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                Assert.assertEquals(solvedBoard.getCell(row,column).getValue(), board
                        .getCell(row, column).intValue());
            }
        }
	}

	@Test
	@Ignore
	public void testSolveModerate() throws FileNotFoundException, IOException {
		// solve moderate board, use BruteForceSolver to obtain solved board
	    Board board = new SDKReader().read(new FileInputStream("src/test/resources/moderate.sdk"));
		StrategySolver strategySolver = new StrategySolver();
		boolean solved = strategySolver.solve(board);
		if (!solved) {
            BoardToTextConverter toTextConverter = new BoardToTextConverter();
            log.debug("moderate" + toTextConverter.convert(board));
		}
		Assert.assertTrue(solved);
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		Board solvedBoard = new SDKReader().read(new FileInputStream("src/test/resources/moderate.sdk"));
		bruteForceSolver.solve(solvedBoard);
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				Assert.assertEquals(solvedBoard.getCell(row, column)
						.intValue(), board.getCell(row, column).intValue());
			}
		}
	}

	@Test
	public void testSolveMild() throws FileNotFoundException, IOException {
		// solve mild board, use BruteForceSolver to obtain solved board
	    Board board = new SDKReader().read(new FileInputStream("src/test/resources/mild.sdk"));
		StrategySolver strategySolver = new StrategySolver();
		boolean solved = strategySolver.solve(board);
		if (!solved) {
            BoardToTextConverter toTextConverter = new BoardToTextConverter();
            log.debug("mild" + toTextConverter.convert(board));
		}
		Assert.assertTrue(solved);
		BruteForceSolver bruteForceSolver = new BruteForceSolver();
		Board solvedBoard = new SDKReader().read(new FileInputStream("src/test/resources/mild.sdk"));
		bruteForceSolver.solve(solvedBoard);
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				Assert.assertEquals(solvedBoard.getCell(row, column)
						.intValue(), board.getCell(row, column).intValue());
			}
		}
	}
}

package com.tieto.sudoku.generator;

import junit.framework.Assert;
import lombok.extern.log4j.Log4j;

import org.junit.Ignore;
import org.junit.Test;

import com.tieto.sudoku.Board;
import com.tieto.sudoku.BoardToTextConverter;
import com.tieto.sudoku.Cell;
import com.tieto.sudoku.solver.BruteForceSolver;
import com.tieto.sudoku.solver.StrategySolver;

@Log4j
public class SudokuGeneratorTest {

    @Test
    public void testGenerateWithOrderedLocationGenerator() {
        generateWithOrderedLocationGenerator(2);
    }

    @Test
    public void testGenerateWithOrderedLocationGenerator2() {
        generateWithOrderedLocationGenerator(4);
    }
    @Test
    public void testGenerateWithOrderedLocationGenerator3() {
        generateWithOrderedLocationGenerator(5);
    }
    @Test
    public void testGenerateWithOrderedLocationGenerator4() {
        generateWithOrderedLocationGenerator(7);
    }

    private Board generateWithOrderedLocationGenerator(int step) {
        SudokuGenerator sudokuGenerator = new SudokuGenerator();
        sudokuGenerator
                .setLocationGenerator(new OrderedLocationGenerator(step));
        Board board = sudokuGenerator.generate();
        log.debug(board.toString());
        Assert.assertTrue(board.isLegal());
        BruteForceSolver bruteForceSolver = new BruteForceSolver();
        Assert.assertTrue(bruteForceSolver.solve(board.copy()));
        StrategySolver strategySolver = new StrategySolver();
        Assert.assertEquals(1, bruteForceSolver.findAllSolutions(board.copy())
                .size());
        boolean solved = strategySolver.solve(board.copy());
        log.debug("after strategySolver:");
        log.debug(board.toString());
        if (!solved) {
            BoardToTextConverter toTextConverter = new BoardToTextConverter();
            log.debug("generator" + toTextConverter.convert(board));
        }
        Assert.assertTrue(solved);
        return board;
    }

    @Test
    public void testGenerateWithRandomLocationGenerator() {
        SudokuGenerator sudokuGenerator = new SudokuGenerator();
        sudokuGenerator.setLocationGenerator(new RandomLocationGenerator());
        Board board = sudokuGenerator.generate();
        log.debug(board.toString());
        Assert.assertTrue(board.isLegal());
        BruteForceSolver bruteForceSolver = new BruteForceSolver();
        Assert.assertTrue(bruteForceSolver.solve(board.copy()));
        StrategySolver strategySolver = new StrategySolver();
        Assert.assertEquals(1, bruteForceSolver.findAllSolutions(board.copy())
                .size());
        boolean solved = strategySolver.solve(board.copy());
        log.debug("after strategySolver:");
        log.debug(board.toString());
        if (!solved) {
            BoardToTextConverter toTextConverter = new BoardToTextConverter();
            log.debug("generator" + toTextConverter.convert(board));
        }
        Assert.assertTrue(solved);
    }

    private int countValues(Board board) {
        int valueCount = 0;
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (board.getCell(row, column).intValue() != Cell.EMPTY) {
                    valueCount++;
                }
            }
        }
        return valueCount;
    }
}

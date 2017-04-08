package com.tieto.sudoku;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.tieto.sudoku.reader.SDKReader;

public class BoardTest {

    @Test
    public void getLine() {
        Board board = new Board();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.setValue(i, j, j + 1);
            }
        }
        for (int i = 0; i < 9; i++) {
            Line line = board.getColumn(i);
            for (int j = 0; j < 9; j++) {
                Assert.assertEquals(i + 1, line.getCell(j).getValue());
            }
        }
        for (int i = 0; i < 9; i++) {
            Line line = board.getRow(i);
            for (int j = 0; j < 9; j++) {
                Assert.assertEquals(j + 1, line.getCell(j).getValue());
            }
        }
    }

    @Test
    public void getBox() {
        Board board = new Board();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.setValue(i, j, j + 1);
            }
        }
        Box box = board.getBox(0);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Assert.assertEquals(j + 1, box.getCell(i, j).getValue());
            }
        }
    }

    @Test
    public void copy() throws FileNotFoundException, IOException {
        Board board = new SDKReader().read(new FileInputStream(
                "src/test/resources/testData1.sdk"));
        Board copyOfBoard = board.copy();
        for (int row = 0; row < Line.LENGTH; row++) {
            for (int column = 0; column < Line.LENGTH; column++) {
                Cell cell = board.getCell(row, column);
                Cell copyOfCell = copyOfBoard.getCell(row, column);
                Assert.assertEquals(cell.getValue(), copyOfCell.getValue());
                Assert.assertEquals(cell.isClue(), copyOfCell.isClue());
//                Assert.assertTrue(ArrayUtils.isEquals(cell.getCandidates(),
//                        copyOfCell.getCandidates()));
            }
        }
    }

    @Test
    public void isLegal() throws FileNotFoundException, IOException {
        Board board = new SDKReader().read(new FileInputStream(
                "src/test/resources/testData1.sdk"));
        Assert.assertTrue(board.isLegal());
        board.setValue(1, 1, Cell.EMPTY);
        Assert.assertTrue(board.isLegal());
        board = new SDKReader().read(new FileInputStream(
                "src/test/resources/testData1.sdk"));
        Assert.assertTrue(board.isLegal());
        board.setValue(1, 1, Cell.EMPTY);
        Assert.assertTrue(board.isLegal());
        board = new Board();
        int value = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.setValue(i, j, value);
                value++;
            }
        }
        Assert.assertTrue(board.isLegal());
    }

    @Test
    public void isLegalWithIllegalValues() throws FileNotFoundException,
            IOException {
        Board board = new Board();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.setValue(i, j, j + 1);
            }
        }
        Assert.assertFalse(board.isLegal());
        board = new SDKReader().read(new FileInputStream(
                "src/test/resources/testData1.sdk"));
        board.setValue(0, 0, 4);
        board.setValue(0, 1, 9);
        Assert.assertFalse(board.isLegal());
        board = new SDKReader().read(new FileInputStream(
                "src/test/resources/testData2.sdk"));
        board.setValue(0, 0, 3);
        board.setValue(0, 1, 2);
        Assert.assertFalse(board.isLegal());
    }

    @Test
    public void isLegalWithIllegalRow() {
        Board board = new Board();
        for (int i = 0; i < 9; i++) {
            board.setCell(0, i, new Cell(i + 1));
        }
        board.setCell(0, 1, new Cell(1));
        Assert.assertFalse(board.isLegal());
    }

    @Test
    public void isLegalWithIllegalColumn() {
        Board board = new Board();
        for (int i = 0; i < 9; i++) {
            board.setCell(i, 0, new Cell(i + 1));
        }
        board.setCell(1, 0, new Cell(1));
        Assert.assertFalse(board.isLegal());
    }

    @Test
    public void isLegalWithIllegalBox() {
        Board board = new Board();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.setCell(j, i, new Cell((i * 3) + j + 1));
            }
        }
        board.setCell(1, 1, new Cell(1));
        Assert.assertFalse(board.isLegal());
    }

    @Test
    public void isLegalWithEmptyBoard() {
        Board board = new Board();
        Assert.assertTrue(board.isLegal());
    }
}

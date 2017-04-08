package com.tieto.sudoku.solver.strategy;

import junit.framework.Assert;

import org.junit.Test;

import com.tieto.sudoku.Board;
import com.tieto.sudoku.Box;
import com.tieto.sudoku.Line;

public class SingleLocationStrategyTest {
    @Test
    public void testSingleLocationInLine() {
        // test with line that has only one candidate
        Line line = new Line();
        for (int location = 0; location < 9; location++) {
            for (int candidate = 1; candidate < 10; candidate++) {
                line.getCell(location).removeCandidate(candidate);
            }
        }
        line.getCell(0).setCandidate(1);
        SingleLocationStrategy strategy = new SingleLocationStrategy();
        Assert.assertEquals(1, strategy.applyToLine(line));
        Assert.assertEquals(1, line.getCell(0).intValue());
        // test with line that has all candidates, except 1 in only single
        // location
        line = new Line();
        for (int location = 0; location < 9; location++) {
            for (int candidate = 1; candidate < 10; candidate++) {
                line.getCell(location).setCandidate(candidate);
            }
        }
        for (int location = 0; location < 9; location++) {
            line.getCell(location).removeCandidate(1);
        }
        line.getCell(0).setCandidate(1);
        strategy = new SingleLocationStrategy();
        Assert.assertEquals(1, strategy.applyToLine(line));
        Assert.assertEquals(1, line.getCell(0).intValue());
    }

    @Test
    public void testSingleLocationInBox() {
        // test with box that has only one candidate
        Box box = new Box();
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                for (int candidate = 1; candidate < 10; candidate++) {
                    box.getCell(row, column).removeCandidate(candidate);
                }
            }
        }
        // test with box that has all candidates, except 1 in only single
        // location
        box.getCell(0, 0).setCandidate(1);
        SingleLocationStrategy strategy = new SingleLocationStrategy();
        Assert.assertEquals(1, strategy.applyToBox(box));
        Assert.assertEquals(1, box.getCell(0, 0).intValue());
    }

    @Test
    public void testSingleLocationInBoard() {
        Board board = new Board();
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                for (int candidate = 1; candidate < 10; candidate++) {
                    board.getCell(row, column).removeCandidate(candidate);
                }
            }
        }
        board.getCell(0, 0).setCandidate(1);
        SingleLocationStrategy strategy = new SingleLocationStrategy();
        Assert.assertEquals(1, strategy.apply(board));
        Assert.assertEquals(1, board.getCell(0, 0).intValue());
    }
}

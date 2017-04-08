package com.tieto.sudoku.solver;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j;

import com.tieto.sudoku.Board;
import com.tieto.sudoku.Cell;
import com.tieto.sudoku.Line;

@Log4j
public class BruteForceSolver implements Solver {
    private Board board;
    private List<Board> solutions = null;

    public boolean solve(Board board) {
        this.board = board;
        return solve(0);
    }

    public List<Board> findAllSolutions(Board board) {
        this.board = board;
        this.solutions = new ArrayList<Board>();
        findAllSolutions(0);
        return solutions;
    }

    /**
     * Recursive method for solving sudoku.
     * 
     * @param board
     * @param currentIndex
     *            index of cell which is being solved. Index is 0..81.
     * @return
     */
    private boolean solve(int currentIndex) {
        if (currentIndex >= Board.CELL_COUNT) {
            // board is already solved
            return true;
        }
        int row = currentIndex / Line.LENGTH;
        int column = currentIndex % Line.LENGTH;
        int nextIndex = currentIndex + 1;
        // if value is clue, move to next
        if (board.getCell(row, column).isClue()) {
            return solve(nextIndex);
        } else {
            for (int value = 1; value < 10; value++) {
                board.setValue(row, column, value);
                if (board.isLegal()) {
                    boolean solved = solve(nextIndex);
                    if (solved) {
                        return true;
                    }
                }
            }
            board.setValue(row, column, Cell.EMPTY);
        }
        return false;
    }

    private void findAllSolutions(int currentIndex) {
        int row = currentIndex / Line.LENGTH;
        int column = currentIndex % Line.LENGTH;
        if (row > 8) {
//            log.debug("SOLUTION FOUND");
            this.solutions.add(board);
            return;
        }
        int nextIndex = currentIndex + 1;
        // if value is clue, move to next
        if (board.getCell(row, column).isClue()) {
            findAllSolutions(nextIndex);
        } else {
            for (int value = 1; value < 10; value++) {
                board.setValue(row, column, value);
                if (board.isLegal()) {
                    findAllSolutions(nextIndex);
                }
            }
            board.setValue(row, column, Cell.EMPTY);
        }
    }
}

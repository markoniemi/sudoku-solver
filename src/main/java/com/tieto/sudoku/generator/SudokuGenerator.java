package com.tieto.sudoku.generator;

import java.util.List;

import lombok.extern.log4j.Log4j;

import com.google.common.base.Preconditions;
import com.tieto.sudoku.Board;
import com.tieto.sudoku.Cell;
import com.tieto.sudoku.Location;
import com.tieto.sudoku.solver.BruteForceSolver;
import com.tieto.sudoku.solver.StrategySolver;

@Log4j
public class SudokuGenerator {
    public static final int CLUE_COUNT = 40;
    public static final int SEED_COUNT = 10;
    LocationGenerator locationGenerator = new RandomLocationGenerator();

    @SuppressWarnings("PMD.SystemPrintln")
    public static void main(String args[]) {
        SudokuGenerator generator = new SudokuGenerator();
        Board board = generator.generate();
        log.debug(board);
    }

    public Board generate() {
        return generate(CLUE_COUNT, SEED_COUNT);
    }

    /**
     * Generate a sudoku board. Strategy for generating: Generate a seed board,
     * solve it with BruteForceSolver and remove numbers and check that there is
     * only one solution.
     * 
     * @param clueCount
     * @param seedCount
     * @return
     */
    public Board generate(int clueCount, int seedCount) {
        Preconditions.checkArgument(clueCount > 0,
                "clueCount must be greater than zero");
        Preconditions.checkArgument(seedCount > 0,
                "seedCount must be greater than zero");
        Board board = new Board();
        // generate random seed while checking it is legal and solvable
        generateSeed(board, seedCount);
        BruteForceSolver bruteForceSolver = new BruteForceSolver();
        bruteForceSolver.solve(board);
        removeRandomNumbers(board, clueCount);
        return board;
    }

    /**
     * Recursive method that adds random clues to board while making sure the
     * board remains legal.
     * 
     * @param board
     * @param numberCount
     *            number or clues to add.
     */
    private void generateSeed(Board board, int numberCount) {
        int count = 0;
        generateSeedRecursive(board, numberCount, count);
    }

    private boolean generateSeedRecursive(Board board, int numberCount,
            int count) {
        if (count >= numberCount) {
            log.debug("generateSeedRecursive done");
            return true;
        }
        log.debug("generateSeedRecursive.count: " + count);
        BruteForceSolver bruteForceSolver = new BruteForceSolver();
        Location location = locationGenerator.nextLocation();
        int row = location.getRow();
        int column = location.getColumn();
        for (int value = 1; value < 10; value++) {
            board.setValue(row, column, value);
            board.getCell(row, column).setClue(true);
            if (board.isLegal()) {
                if (bruteForceSolver.solve(board.copy())) {
                    if (generateSeedRecursive(board, numberCount, ++count)) {
                        return true;
                    }
                }
            }
        }
        board.setValue(row, column, Cell.EMPTY);
        board.getCell(row, column).setClue(false);
        return false;
    }

    // TODO add removeRandomNumbersRecursive, so that the removing returns to
    // the last valid solution
    private void removeRandomNumbers(Board board, int clueCount) {
        BruteForceSolver bruteForceSolver = new BruteForceSolver();
        StrategySolver strategySolver = new StrategySolver();
        // List<Board> solutions;
        // do {
        boolean ready = false;
        int count = Board.CELL_COUNT;
        do {
            log.debug("count:" + count);
            int value = 0;
            int row;
            int column;
            do {
                Location location = locationGenerator.nextLocation();
                row = location.getRow();
                column = location.getColumn();
                value = board.getCell(row, column).intValue();
            } while (value == Cell.EMPTY);
            // remove a number of values
            board.setValue(row, column, Cell.EMPTY);
            board.getCell(row, column).setClue(false);
            board.getCell(row, column).resetCandidates();
            // try to solve it with strategy solver
            boolean solved = strategySolver.solve(board.copy());
            // check if board has multiple solutions
            List<Board> solutions = bruteForceSolver.findAllSolutions(board
                    .copy());
            // cannot solve or has multiple solutions, set back the last value
            if (!solved || solutions.size() > 1) {
                board.setValue(row, column, value);
                board.getCell(row, column).setClue(true);
                board.getCell(row, column).cleanCandidates();
                if (count < clueCount) {
                    ready = true;
                }
            } else {
                count--;
            }
        } while (!ready);
        // solutions = bruteForceSolver.findAllSolutions(board.copy());
        // logger.debug("solutions:" + solutions.size());
        // } while (solutions.size() == 1);
    }

    public LocationGenerator getLocationGenerator() {
        return locationGenerator;
    }

    public void setLocationGenerator(LocationGenerator locationGenerator) {
        this.locationGenerator = locationGenerator;
    }
}

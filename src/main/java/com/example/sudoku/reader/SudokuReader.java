package com.example.sudoku.reader;

import java.io.IOException;
import java.io.InputStream;

import com.example.sudoku.Board;

/**
 * Interface for sudoku file readers.
 */
public interface SudokuReader {
    /**
     * Read Sudoku form a file.
     * @param inputStream
     * @return
     * @throws IOException if unable to read from inputStream.
     */
    public abstract Board read(InputStream inputStream) throws IOException;
}
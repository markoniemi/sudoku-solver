package com.example.sudoku.reader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import com.example.sudoku.Board;
import com.example.sudoku.Line;

/**
 * Reads sudoku in simple string format: 81-character string where 0 = empty cell.
 * Example: "060027100090803200000400030209060070800000095000004020140000000003009000080200006"
 */
public class SimpleReader implements SudokuReader {
	private static final int PUZZLE_LENGTH = Line.LENGTH * Line.LENGTH;

	@Override
	public Board read(InputStream inputStream) throws IOException {
		String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8).trim();

		if (content.length() < PUZZLE_LENGTH) {
			throw new IOException("Puzzle string too short. Expected " + PUZZLE_LENGTH + " characters, got " + content.length());
		}

		int[][] data = new int[Line.LENGTH][Line.LENGTH];
		int index = 0;

		for (int row = 0; row < Line.LENGTH; row++) {
			for (int col = 0; col < Line.LENGTH; col++) {
				char digit = content.charAt(index++);
				if (!Character.isDigit(digit)) {
					throw new IOException("Invalid character at position " + (index - 1) + ": '" + digit + "'. Expected digit 0-9.");
				}
				data[row][col] = digit - '0';
			}
		}

		return new Board(data);
	}
}

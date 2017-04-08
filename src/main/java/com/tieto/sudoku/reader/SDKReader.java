package com.tieto.sudoku.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.tieto.sudoku.Board;
import com.tieto.sudoku.Line;

/**
 * Reads Sudoku Puzzle (.sdk) file into Board.
 * 
 * @see src/site/xhtml/sdkFile.xhtml
 * @see <a href="http://www.sudocue.net/fileformats.php">Sudo file formats</a>
 */
public class SDKReader implements SudokuReader {
	/**
	 * @throws NumberFormatException
	 *             if data contains characters other than '0'..'9' or '.'.
	 * @throws StreamCorruptedException
	 *             if data line is shorter than 9 characters.
	 */
	@Override
	public Board read(InputStream inputStream) throws IOException {
		@SuppressWarnings("unchecked")
		List<String> textLines = IOUtils.readLines(inputStream);
		int[][] data = new int[Line.LENGTH][Line.LENGTH];
		int row = 0;
		for (String textLine : textLines) {
			if (isCommentLine(textLine)) {
				continue;
			}
			if (textLine.length() < Line.LENGTH) {
				throw new StreamCorruptedException("line is too short:"
						+ textLine);
			}
			for (int column = 0; column < 9; column++) {
				data[row][column] = readValueFromLine(textLine, column);
			}
			row++;
		}
		return new Board(data);
	}

	/**
	 * returns true if line contains '#'
	 */
	private boolean isCommentLine(String textLine) {
		return textLine.contains("#");
	}

	private int readValueFromLine(String textLine, int location) {
		int cellValue = 0;
		char charAt = textLine.charAt(location);
		if (charAt != '.') {
			cellValue = Integer.parseInt("" + charAt);
		}
		return cellValue;
	}
}

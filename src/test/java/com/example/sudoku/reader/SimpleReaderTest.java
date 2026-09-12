package com.example.sudoku.reader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.example.sudoku.Board;

class SimpleReaderTest {

	@Test
	void testReadSimpleFormat() throws IOException {
		SimpleReader reader = new SimpleReader();
		Board board = reader.read(new FileInputStream("src/test/resources/simple.txt"));

		assertNotNull(board);
		assertEquals(6, board.getCell(0, 1).intValue());
		assertEquals(2, board.getCell(0, 4).intValue());
		assertEquals(7, board.getCell(0, 5).intValue());
		assertEquals(1, board.getCell(0, 6).intValue());
		assertEquals(0, board.getCell(0, 0).intValue());
	}

	@Test
	void testReadWithTrailingWhitespace() throws IOException {
		SimpleReader reader = new SimpleReader();
		Board board = reader.read(new FileInputStream("src/test/resources/simple.txt"));
		assertNotNull(board);
		assertTrue(board.isLegal());
	}
}

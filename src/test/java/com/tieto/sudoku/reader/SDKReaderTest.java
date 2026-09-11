package com.tieto.sudoku.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SDKReaderTest {
    @Test
    public void read() throws IOException {
        SudokuReader sdkReader = new SDKReader();
        InputStream inputStream = new FileInputStream("src/test/resources/test.sdk");
        sdkReader.read(inputStream);
    }

    @Test
    public void readInvalid1() throws Exception {
        assertThrows(StreamCorruptedException.class, () -> {
            SudokuReader sdkReader = new SDKReader();
            InputStream inputStream = new FileInputStream("src/test/resources/invalid1.sdk");
            sdkReader.read(inputStream);
        });
    }

    @Test
    public void readInvalid2() throws Exception {
        assertThrows(NumberFormatException.class, () -> {
            SudokuReader sdkReader = new SDKReader();
            InputStream inputStream = new FileInputStream("src/test/resources/invalid2.sdk");
            sdkReader.read(inputStream);
        });
    }
}

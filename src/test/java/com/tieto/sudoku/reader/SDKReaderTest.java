package com.tieto.sudoku.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;

import org.junit.Test;

public class SDKReaderTest {
    @Test
    public void read() throws FileNotFoundException, IOException {
        SudokuReader sdkReader = new SDKReader();
        InputStream inputStream = new FileInputStream("src/test/resources/test.sdk");
        sdkReader.read(inputStream);
    }
    @Test(expected=StreamCorruptedException.class)
    public void readInvalid1() throws FileNotFoundException, IOException {
        SudokuReader sdkReader = new SDKReader();
        InputStream inputStream = new FileInputStream("src/test/resources/invalid1.sdk");
        sdkReader.read(inputStream);
    }
    @Test(expected=NumberFormatException.class)
    public void readInvalid2() throws FileNotFoundException, IOException {
        SudokuReader sdkReader = new SDKReader();
        InputStream inputStream = new FileInputStream("src/test/resources/invalid2.sdk");
        sdkReader.read(inputStream);
    }
}

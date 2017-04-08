package com.tieto.sudoku;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import lombok.extern.log4j.Log4j;

import org.junit.Assert;
import org.junit.Test;

import com.tieto.sudoku.reader.SDKReader;
import com.tieto.sudoku.solver.strategy.SingleCandidateStrategy;
@Log4j
public class BoardToTextConverterTest {

    /**
     * This test is more suited for mk.1 eyeball testing.
     */
    @Test
    public void convert() throws FileNotFoundException, IOException {
        Board board = new SDKReader().read(new FileInputStream(
                "src/test/resources/mild.sdk"));
        // use a strategy class to calculate candidates
        SingleCandidateStrategy strategy = new SingleCandidateStrategy();
        strategy.calculateCandidates(board);
        BoardToTextConverter toTextConverter = new BoardToTextConverter();
        String boardAsText = toTextConverter.convert(board);
        Assert.assertTrue(boardAsText.contains("|   |1  |   |1  |   |1  |   |   |1  |"));
        log.debug(boardAsText);
    }
}

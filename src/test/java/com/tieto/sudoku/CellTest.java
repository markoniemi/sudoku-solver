package com.tieto.sudoku;

import org.junit.Assert;
import org.junit.Test;

public class CellTest {

    @Test
    public void countCandidates() {
        Cell cell = new Cell();
        Assert.assertEquals(9, cell.countCandidates());
        Assert.assertEquals(9, cell.getCandidatesAsList().size());
        cell.cleanCandidates();
        Assert.assertEquals(0, cell.countCandidates());
        Assert.assertEquals(0, cell.getCandidatesAsList().size());
        cell.resetCandidates();
        Assert.assertEquals(9, cell.countCandidates());
        Assert.assertEquals(9, cell.getCandidatesAsList().size());
        cell.removeCandidate(9);
        Assert.assertEquals(8, cell.countCandidates());
        Assert.assertEquals(8, cell.getCandidatesAsList().size());
        cell.setCandidate(8);
        Assert.assertEquals(8, cell.countCandidates());
        Assert.assertEquals(8, cell.getCandidatesAsList().size());
        cell.setCandidate(9);
        Assert.assertEquals(9, cell.countCandidates());
        Assert.assertEquals(9, cell.getCandidatesAsList().size());
    }
}

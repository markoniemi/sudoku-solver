package com.tieto.sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CellTest {

    @Test
    public void countCandidates() {
        Cell cell = new Cell();
        Assertions.assertEquals(9, cell.countCandidates());
        Assertions.assertEquals(9, cell.getCandidatesAsList().size());
        cell.cleanCandidates();
        Assertions.assertEquals(0, cell.countCandidates());
        Assertions.assertEquals(0, cell.getCandidatesAsList().size());
        cell.resetCandidates();
        Assertions.assertEquals(9, cell.countCandidates());
        Assertions.assertEquals(9, cell.getCandidatesAsList().size());
        cell.removeCandidate(9);
        Assertions.assertEquals(8, cell.countCandidates());
        Assertions.assertEquals(8, cell.getCandidatesAsList().size());
        cell.setCandidate(8);
        Assertions.assertEquals(8, cell.countCandidates());
        Assertions.assertEquals(8, cell.getCandidatesAsList().size());
        cell.setCandidate(9);
        Assertions.assertEquals(9, cell.countCandidates());
        Assertions.assertEquals(9, cell.getCandidatesAsList().size());
    }
}

package com.tieto.sudoku;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CellTest {

    @Test
    public void countCandidates() {
        Cell cell = new Cell();
        assertEquals(9, cell.countCandidates());
        assertEquals(9, cell.getCandidatesAsList().size());
        cell.cleanCandidates();
        assertEquals(0, cell.countCandidates());
        assertEquals(0, cell.getCandidatesAsList().size());
        cell.resetCandidates();
        assertEquals(9, cell.countCandidates());
        assertEquals(9, cell.getCandidatesAsList().size());
        cell.removeCandidate(9);
        assertEquals(8, cell.countCandidates());
        assertEquals(8, cell.getCandidatesAsList().size());
        cell.setCandidate(8);
        assertEquals(8, cell.countCandidates());
        assertEquals(8, cell.getCandidatesAsList().size());
        cell.setCandidate(9);
        assertEquals(9, cell.countCandidates());
        assertEquals(9, cell.getCandidatesAsList().size());
    }
}

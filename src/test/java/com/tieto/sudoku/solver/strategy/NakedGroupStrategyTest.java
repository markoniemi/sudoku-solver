package com.tieto.sudoku.solver.strategy;

import java.util.List;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.tieto.sudoku.Box;
import com.tieto.sudoku.Line;

@Disabled
public class NakedGroupStrategyTest {
        
    @Test
    public void findCandidateGroups() {
        Line line = new Line();
        for (int location = 0; location < 9; location++) {
            line.getCell(location).cleanCandidates();
        }
        line.getCell(0).setCandidate(3);
        line.getCell(1).setCandidate(3);
        line.getCell(1).setCandidate(4);
        line.getCell(2).setCandidate(4);
        line.getCell(2).setCandidate(7);
        line.getCell(3).setCandidate(1);
        line.getCell(3).setCandidate(2);
        line.getCell(3).setCandidate(3);
        line.getCell(5).setCandidate(7);
        line.getCell(5).setCandidate(8);
        line.getCell(6).setCandidate(7);
        line.getCell(6).setCandidate(8);
        line.getCell(7).setCandidate(8);
        line.getCell(7).setCandidate(1);
        line.getCell(7).setCandidate(9);
        NakedGroupStrategy nakedGroupStrategy = new NakedGroupStrategy();
        List<CandidateGroup> candidateGroups = nakedGroupStrategy.findCandidateGroups(line);
        Assertions.assertEquals(0, candidateGroups.get(0).getLocation());
        Assertions.assertEquals(1, candidateGroups.get(1).getLocation());
        Assertions.assertEquals(2, candidateGroups.get(2).getLocation());
        Assertions.assertEquals(5, candidateGroups.get(3).getLocation());
        Assertions.assertEquals(6, candidateGroups.get(4).getLocation());
    }
    @Test
    public void testApplyToLine() {
        Line line = new Line();
        for (int location = 0; location < 9; location++) {
            line.getCell(location).cleanCandidates();
        }
        line.getCell(0).setCandidate(3);
        line.getCell(1).setCandidate(3);
        line.getCell(1).setCandidate(4);
        line.getCell(2).setCandidate(4);
        line.getCell(2).setCandidate(7);
        line.getCell(3).setCandidate(1);
        line.getCell(3).setCandidate(2);
        line.getCell(3).setCandidate(3);
        line.getCell(5).setCandidate(7);
        line.getCell(5).setCandidate(8);
        line.getCell(6).setCandidate(7);
        line.getCell(6).setCandidate(8);
        line.getCell(7).setCandidate(8);
        line.getCell(7).setCandidate(1);
        line.getCell(7).setCandidate(9);
        NakedGroupStrategy nakedGroupStrategy = new NakedGroupStrategy();
        nakedGroupStrategy.applyToLine(line);
//        Assertions.assertEquals(2, nakedGroupStrategy.applyToLine(line));
        Assertions.assertFalse(line.getCell(2).isCandidate(7));
        Assertions.assertFalse(line.getCell(7).isCandidate(8));
    }
    @Test
    public void testApplyToLine2() {
        Line line = new Line();
        for (int location = 0; location < 9; location++) {
            line.getCell(location).cleanCandidates();
        }
        line.getCell(0).setCandidate(3);
        line.getCell(0).setCandidate(4);
        line.getCell(1).setCandidate(3);
        line.getCell(1).setCandidate(4);
        line.getCell(2).setCandidate(4);
        line.getCell(2).setCandidate(7);
        line.getCell(3).setCandidate(1);
        line.getCell(3).setCandidate(2);
        line.getCell(3).setCandidate(3);
        line.getCell(5).setCandidate(7);
        line.getCell(5).setCandidate(8);
        line.getCell(6).setCandidate(7);
        line.getCell(6).setCandidate(8);
        line.getCell(7).setCandidate(8);
        line.getCell(7).setCandidate(1);
        line.getCell(7).setCandidate(9);
        NakedGroupStrategy nakedGroupStrategy = new NakedGroupStrategy();
        nakedGroupStrategy.applyToLine(line);
//        Assertions.assertEquals(4, nakedGroupStrategy.applyToLine(line));
        Assertions.assertFalse(line.getCell(2).isCandidate(7));
        Assertions.assertFalse(line.getCell(7).isCandidate(8));
        Assertions.assertFalse(line.getCell(2).isCandidate(4));
        Assertions.assertFalse(line.getCell(3).isCandidate(3));
    }

    @Test
    public void testApplyToBox() {
        Box box = new Box();
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                box.getCell(row, column).cleanCandidates();
            }
        }
        box.getCell(0, 0).setCandidate(3);
        box.getCell(0, 1).setCandidate(3);
        box.getCell(0, 1).setCandidate(4);
        box.getCell(0, 2).setCandidate(4);
        box.getCell(0, 2).setCandidate(7);
        box.getCell(1, 0).setCandidate(1);
        box.getCell(1, 0).setCandidate(2);
        box.getCell(1, 0).setCandidate(3);
        box.getCell(1, 2).setCandidate(7);
        box.getCell(1, 2).setCandidate(8);
        box.getCell(2, 0).setCandidate(7);
        box.getCell(2, 0).setCandidate(8);
        box.getCell(2, 1).setCandidate(8);
        box.getCell(2, 1).setCandidate(1);
        box.getCell(2, 1).setCandidate(9);
        NakedGroupStrategy nakedGroupStrategy = new NakedGroupStrategy();
        nakedGroupStrategy.applyToLine(box.asLine());
//        Assertions.assertEquals(2, nakedGroupStrategy.applyToLine(box.asLine()));
        Assertions.assertFalse(box.getCell(0, 2).isCandidate(7));
        Assertions.assertFalse(box.getCell(2, 1).isCandidate(8));
    }

}

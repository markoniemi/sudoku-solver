package com.tieto.sudoku.solver.strategy;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.tieto.sudoku.Cell;
import com.tieto.sudoku.Line;

public class HiddenPairStrategy extends AbstractStrategy {

    public int applyToLine(Line line) {
        // find all pairs in line
        List<LinePair> pairs = findPairs(line);
        int changeCount = 0;
        // check if two pairs have same locations
        for (LinePair linePair1 : pairs) {
            for (LinePair linePair2 : pairs) {
                if (linePair1 == linePair2) {
                    continue;
                } else {
                    changeCount += makeHiddenPairNakedInLine(line, linePair1,
                            linePair2);
                }
            }
        }
        return changeCount;
    }

    private int makeHiddenPairNakedInLine(Line line, LinePair linePair1,
            LinePair linePair2) {
        int changeCount = 0;
        // locations should be in order
        if (linePair1.getLocation1() == linePair2.getLocation1()
                && linePair1.getLocation2() == linePair2.getLocation2()) {
            Cell value1 = line.getCell(linePair1.getLocation1());
            Cell value2 = line.getCell(linePair1.getLocation2());
            // check that this is not an old hidden pair that is
            // already found on last call
            boolean otherCandidatesFound = false;
            for (int candidate = 1; candidate < 10; candidate++) {
                if (candidate == linePair1.getValue()
                        || candidate == linePair2.getValue()) {
                    continue;
                } else if (value1.isCandidate(candidate)
                        || value2.isCandidate(candidate)) {
                    otherCandidatesFound = true;
                }
            }
            if (otherCandidatesFound) {
                changeCount++;
                makeHiddenPairNakedPair(linePair1, linePair2, value1, value2);
            }
        }
        return changeCount;
    }

    /**
     * Makes a hidden pair to naked pair by removing all other candidates from
     * both locations.
     */
    private void makeHiddenPairNakedPair(LinePair linePair1,
            LinePair linePair2, Cell value1, Cell value2) {
        value1.cleanCandidates();
        value1.setCandidate(linePair1.getValue());
        value1.setCandidate(linePair2.getValue());
        value2.cleanCandidates();
        value2.setCandidate(linePair1.getValue());
        value2.setCandidate(linePair2.getValue());
    }

    @Override
    public String getName() {
        return "HiddenPairStrategy";
    }

    public List<LinePair> findPairs(Line line) {
        List<LinePair> pairs = new ArrayList<LinePair>();
        LinePair pair = new LinePair();
        for (int candidate = 1; candidate < 10; candidate++) {
            int candidateCount = 0;
            for (int location = 0; location < Line.LENGTH; location++) {
                if (line.getCell(location).isCandidate(candidate)) {
                    pair.setValue(candidate);
                    candidateCount++;
                    if (candidateCount == 1) {
                        pair.setLocation1(location);
                    } else if (candidateCount == 2) {
                        pair.setLocation2(location);
                    } else {
                        continue;
                    }
                }
            }
            if (candidateCount == 2) {
                pairs.add(pair);
                pair = new LinePair();
            }
        }
        return pairs;
    }

    @Data
    @SuppressWarnings("PMD.UnusedPrivateField")
    protected class LinePair {
        private int value;
        private int location1;
        private int location2;
    }
}

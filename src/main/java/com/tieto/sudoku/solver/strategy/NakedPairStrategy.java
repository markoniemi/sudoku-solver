package com.tieto.sudoku.solver.strategy;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.tieto.sudoku.Line;

public class NakedPairStrategy extends AbstractStrategy {

    @Override
    public int applyToLine(Line line) {
        List<CandidatePair> candidatePairs = findCandidatePairs(line);
        int changeCount = 0;
        // go through all possible pairs
        for (CandidatePair candidatePair1 : candidatePairs) {
            for (CandidatePair candidatePair2 : candidatePairs) {
                // skip if location is same
                if (candidatePair1 == candidatePair2) {
                    continue;
                } else {
                    // remove candidates in line
                    changeCount += removeCandidatesInLine(line, candidatePair1,
                            candidatePair2);
                }
            }
        }
        return changeCount;
    }

    /**
     * Remove candidates from a line that contains a naked pair.
     * 
     * @param line
     * @param candidatePair1
     *            1st location
     * @param candidatePair2
     *            2nd location
     * @return number of changes made
     */
    private int removeCandidatesInLine(Line line, CandidatePair candidatePair1,
            CandidatePair candidatePair2) {
        int changeCount = 0;
        // values should be in order
        if (candidatePair1.getCandidate1() == candidatePair2.getCandidate1()
                && candidatePair1.getCandidate2() == candidatePair2
                        .getCandidate2()) {
            // check that this is not an old hidden pair that is
            // already found on last call
            boolean otherCandidatesFound = false;
            for (int location = 0; location < Line.LENGTH; location++) {
                if (location == candidatePair1.getLocation()
                        || location == candidatePair2.getLocation()) {
                    continue;
                } else if (line.getCell(location).isCandidate(
                        candidatePair1.getCandidate1())
                        || line.getCell(location).isCandidate(
                                candidatePair1.getCandidate2())) {
                    otherCandidatesFound = true;
                }
            }
            if (otherCandidatesFound) {
                changeCount += removeAllOtherCandidates(line, candidatePair1,
                        candidatePair2);
            }
        }
        return changeCount;
    }

    /**
     * Remove candidates from all other locations except the ones marked by
     * candidatePairs.
     * 
     * @return
     */
    private int removeAllOtherCandidates(Line line,
            CandidatePair candidatePair1, CandidatePair candidatePair2) {
        // remove two candidates from all other locations.
        int changeCount = 0;
        for (int location = 0; location < Line.LENGTH; location++) {
            if (location == candidatePair1.getLocation()
                    || location == candidatePair2.getLocation()) {
                continue;
            } else {
                if (line.getCell(location).isCandidate(
                        candidatePair1.candidate1)) {
                    line.getCell(location).removeCandidate(
                            candidatePair1.getCandidate1());
                    changeCount++;
                }
                if (line.getCell(location).isCandidate(
                        candidatePair1.candidate2)) {
                    line.getCell(location).removeCandidate(
                            candidatePair1.getCandidate2());
                    changeCount++;
                }
            }
        }
        return changeCount;
    }

    protected List<CandidatePair> findCandidatePairs(Line line) {
        List<CandidatePair> pairs = new ArrayList<CandidatePair>();
        CandidatePair pair = new CandidatePair();
        for (int location = 0; location < Line.LENGTH; location++) {
            int candidateCount = 0;
            for (int candidate = 1; candidate < 10; candidate++) {
                if (line.getCell(location).isCandidate(candidate)) {
                    pair.setLocation(location);
                    candidateCount++;
                    if (candidateCount == 1) {
                        pair.setCandidate1(candidate);
                    } else if (candidateCount == 2) {
                        pair.setCandidate2(candidate);
                    } else {
                        continue;
                    }
                }
            }
            if (candidateCount == 2) {
                pairs.add(pair);
                pair = new CandidatePair();
            }
        }
        return pairs;
    }

    @Override
    public String getName() {
        return "NakedPairStrategy";
    }

    @Data
    protected class CandidatePair {
        private int candidate1;
        private int candidate2;
        private int location;
    }
}

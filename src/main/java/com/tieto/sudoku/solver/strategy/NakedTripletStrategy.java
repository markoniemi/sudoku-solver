package com.tieto.sudoku.solver.strategy;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.apache.commons.lang.ArrayUtils;
//
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//import com.google.common.base.Preconditions;
//import com.tieto.sudoku.Line;
//import com.tieto.sudoku.solver.strategy.NakedPairStrategy.CandidatePair;
//
//public class NakedTripletStrategy extends AbstractStrategy {
//
//    @Override
//    public String getName() {
//        return "NakedTripletStrategy";
//    }
//
//    @Override
//    public int applyToLine(Line line) {
//        return 0;
//    }
//
//    /**
//     * Remove candidates from a line that contains a naked pair.
//     * 
//     * @param line
//     * @param candidatePair1
//     *            1st location
//     * @param candidatePair2
//     *            2nd location
//     * @return number of changes made
//     */
//    private int removeCandidatesInLine(Line line,
//            List<CandidateGroup> candidateGroups) {
//        int changeCount = 0;
//        // values should be in order
//        if (hasSameCandidates(candidateGroups)) {
//            // if (candidateTriplets.get(0).getCandidate1() ==
//            // candidatePair2.getCandidate1()
//            // && candidatePair1.getCandidate2() == candidatePair2
//            // .getCandidate2()) {
//            // check that this is not an old hidden pair that is
//            // already found on last call
//            boolean otherCandidatesFound = false;
//            for (int location = 0; location < Line.LENGTH; location++) {
//                if (hasSameLocations()
//                        location == candidatePair1.getLocation()
//                        || location == candidatePair2.getLocation()) {
//                    continue;
//                } else if (line.getCell(location).isCandidate(
//                        candidatePair1.getCandidate1())
//                        || line.getCell(location).isCandidate(
//                                candidatePair1.getCandidate2())) {
//                    otherCandidatesFound = true;
//                }
//            }
//            if (otherCandidatesFound) {
//                changeCount++;
//                // removeAllOtherCandidates(line, candidatePair1,
//                // candidatePair2);
//            }
//        }
//        return changeCount;
//    }
//
//    boolean hasSameCandidates(List<CandidateGroup> candidateGroups) {
//        if (candidateGroups.get(0).contains(
//                candidateGroups.get(1).candidates)
//                && candidateGroups.get(1).contains(
//                        candidateGroups.get(2).candidates)) {
//            return true;
//        }
//        return false;
//    }
//
//    protected List<CandidateGroup> findCandidateGroup(Line line) {
//        List<CandidateGroup> candidateGroups = new ArrayList<CandidateGroup>();
//        CandidateGroup candidateGroup = new CandidateGroup();
//        for (int location = 0; location < Line.LENGTH; location++) {
//            int candidateCount = 0;
//            for (int candidate = 1; candidate < 10; candidate++) {
//                if (line.getCell(location).isCandidate(candidate)) {
//                    candidateGroup.setLocation(location);
//                    candidateGroup.setCandidate(candidate);
//                    candidateCount++;
//                    if (candidateCount > 3) {
//                        continue;
//                    }
//                }
//            }
//            if (candidateCount == 3) {
//                candidateGroups.add(candidateGroup);
//                candidateGroup = new CandidateGroup();
//            }
//        }
//        return candidateGroups;
//    }
//
//    @Data
//    class CandidateGroup {
//        private List<Integer> candidates = new ArrayList<Integer>();
//        private int location;
//
//        public void setCandidate(int candidate) {
//            Preconditions.checkArgument(candidate >= 1);
//            Preconditions.checkArgument(candidate <= Line.LENGTH);
//            candidates.add(candidate);
//        }
//
//        boolean contains(List<Integer> candidates) {
//            return this.contains(candidates);
//        }
//    }
//}

package com.tieto.sudoku.solver.strategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import com.tieto.sudoku.Cell;
import com.tieto.sudoku.Line;

@Slf4j
public class NakedGroupStrategy extends AbstractStrategy {
	private int groupSize = 2;

	public NakedGroupStrategy() {
	}

	public NakedGroupStrategy(int groupSize) {
		this.groupSize = groupSize;
	}

	@Override
	public String getName() {
		return "NakedGroupStrategy";
	}

	@Override
	public int applyToLine(Line line) {
		List<CandidateGroup> candidateGroups = findCandidateGroups(line);
		return removeCandidatesInOtherLocations(line, candidateGroups);
	}

	/**
	 * Find candidateGroups. Go through locations. If cell contains less or
	 * equal amount of candidates than the groupSize, it is a potential
	 * candidate. Then remove extra candidates.
	 */
	List<CandidateGroup> findCandidateGroups(Line line) {
		List<CandidateGroup> candidateGroups = new ArrayList<CandidateGroup>();
		for (int location = 0; location < Line.LENGTH; location++) {
			Cell cell = line.getCell(location);
			if (!cell.isClue() && cell.countCandidates() <= groupSize
					&& cell.countCandidates() > 0) {
				CandidateGroup candidateGroup = new CandidateGroup();
				candidateGroup.setLocation(location);
				candidateGroup.setCandidates(cell.getCandidatesAsList());
				candidateGroups.add(candidateGroup);
			}
		}
		log.debug(""+candidateGroups);
		removeExtraCandidates(candidateGroups);
		return candidateGroups;
	}

	private List<CandidateGroup> removeExtraCandidates(
			List<CandidateGroup> candidateGroups) {
		Set<CandidateGroup> preparedCandidateGroups = new HashSet<CandidateGroup>();
		if (candidateGroups.size() < groupSize) {
			// not possible to have naked group
			// FIXME return empty set
			return null;
		}
		for (CandidateGroup candidateGroup1 : candidateGroups) {
			for (CandidateGroup candidateGroup2 : candidateGroups) {
				// skip same locations
				if (candidateGroup1.getLocation() != candidateGroup2
						.getLocation()) {
					if (candidateGroup1.getCandidates().containsAll(
							candidateGroup2.getCandidates())) {
						// // candidateGroups may have unequal number of
						// candidates, hence
						// // it is necessary to test contains both ways
						// if
						// (candidateGroup1.contains(candidateGroup2.getCandidates())
						// || candidateGroup2.contains(candidateGroup1
						// .getCandidates())) {
						log.debug("{}, {}",candidateGroup1,candidateGroup2);
						preparedCandidateGroups.add(candidateGroup1);
						preparedCandidateGroups.add(candidateGroup2);
					}
				}
			}
		}
		return new ArrayList<CandidateGroup>(preparedCandidateGroups);
	}

	/**
	 * Remove candidates from locations that are not in nakedGroups. Collect a
	 * list of all candidates in nakedGroups. Remove those candidates from
	 * locations that are not in nakedGroups.
	 * 
	 * @param candidateGroups
	 * @return
	 */
	private int removeCandidatesInOtherLocations(Line line,
			List<CandidateGroup> candidateGroups) {
		List<Integer> allCandidates = getAllCandidatesFromList(candidateGroups);
		List<Integer> nakedGroupLocations = getAllLocationsFromList(candidateGroups);
		return removeNonNakedGroupCandidates(allCandidates,
				nakedGroupLocations, line);
	}

	// TODO calculate the count of changes
	private int removeNonNakedGroupCandidates(List<Integer> allCandidates,
			List<Integer> nakedGroupLocations, Line line) {
		for (int location = 0; location < Line.LENGTH; location++) {
			Cell cell = line.getCell(location);
			if (!nakedGroupLocations.contains(location)) {
				cell.cleanCandidates();
				for (Integer candidate : allCandidates) {
					cell.setCandidate(candidate);
				}
			}
		}
		return 0;
	}

	private List<Integer> getAllCandidatesFromList(
			List<CandidateGroup> candidateGroups) {
		Set<Integer> allCandidates = new HashSet<Integer>();
		for (CandidateGroup candidateGroup : candidateGroups) {
			allCandidates.addAll(candidateGroup.getCandidates());
		}
		return new ArrayList<Integer>(allCandidates);
	}

	private List<Integer> getAllLocationsFromList(
			List<CandidateGroup> candidateGroups) {
		Set<Integer> allLocations = new HashSet<Integer>();
		for (CandidateGroup candidateGroup : candidateGroups) {
			allLocations.add(candidateGroup.getLocation());
		}
		return new ArrayList<Integer>(allLocations);
	}
}

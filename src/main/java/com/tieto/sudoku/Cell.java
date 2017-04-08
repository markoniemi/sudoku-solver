package com.tieto.sudoku;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(of = "value")
public class Cell {
	public static final int EMPTY = 0;
	@Getter
	@Setter
	private int value = EMPTY;
	@Getter
	@Setter
	@SuppressWarnings("PMD.UnusedPrivateField")
	private boolean isClue = false;
	Set<Integer> candidates = new HashSet<Integer>(Line.LENGTH);

	public Cell() {
		this(EMPTY);
	}

	public Cell(int value) {
		this.value = value;
		if (value != EMPTY) {
			isClue = true;
		}
		resetCandidates();
	}

	public void setCandidate(int candidate) {
		candidates.add(candidate);
	}

	public void setCandidates(Integer... integers) {
		for (Integer integer : integers) {
			candidates.add(integer);
		}
	}

	public void removeCandidate(int candidate) {
		candidates.remove(candidate);
	}

	public boolean isCandidate(int candidate) {
		if (value != EMPTY) {
			return false;
		}
		return candidates.contains(candidate);
	}

	public void resetCandidates() {
		for (int candidate = 0; candidate < Line.LENGTH; candidate++) {
			candidates.add(candidate + 1);
		}
	}

	public void cleanCandidates() {
		for (int candidate = 0; candidate < Line.LENGTH; candidate++) {
			removeCandidate(candidate + 1);
		}
	}

	public int intValue() {
		return value;
	}

	public int countCandidates() {
		int candidateCount = 0;
		for (int candidate = 1; candidate < 10; candidate++) {
			if (isCandidate(candidate)) {
				candidateCount++;
			}
		}
		return candidateCount;
	}

	public List<Integer> getCandidatesAsList() {
		return new ArrayList<Integer>(candidates);
	}
}

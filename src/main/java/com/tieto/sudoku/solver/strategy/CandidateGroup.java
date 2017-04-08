package com.tieto.sudoku.solver.strategy;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.common.base.Preconditions;
import com.tieto.sudoku.Line;

@Data
@EqualsAndHashCode(of = "location")
class CandidateGroup {
	private List<Integer> candidates = new ArrayList<Integer>();
	private int location;

	public void setCandidate(int candidate) {
		Preconditions.checkArgument(candidate >= 1);
		Preconditions.checkArgument(candidate <= Line.LENGTH);
		candidates.add(candidate);
	}

	boolean contains(List<Integer> candidates) {
		return this.candidates.contains(candidates);
	}

	@Override
	public String toString() {
		return "[location=" + location + "]";
	}
}

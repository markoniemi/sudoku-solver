package com.example.sudoku.solver;

import lombok.extern.slf4j.Slf4j;

import com.example.sudoku.Board;
import com.example.sudoku.solver.strategy.HiddenPairStrategy;
import com.example.sudoku.solver.strategy.NakedPairStrategy;
import com.example.sudoku.solver.strategy.PointingPairsStrategy;
import com.example.sudoku.solver.strategy.SingleCandidateStrategy;
import com.example.sudoku.solver.strategy.SingleLocationStrategy;
import com.example.sudoku.solver.strategy.Strategy;

@Slf4j
public class StrategySolver implements Solver {

	public boolean solve(Board board) {
		Strategy[] strategies = { new SingleCandidateStrategy(),
				new SingleLocationStrategy(), new HiddenPairStrategy(),
				new PointingPairsStrategy(), new NakedPairStrategy()};

		int changesDuringStrategyRound = 1;
		while (!board.isSolved() && changesDuringStrategyRound != 0) {
		    changesDuringStrategyRound = 0;
		    for (Strategy strategy : strategies) {
			    int changesByStrategy = 0;
//			    strategy.calculateCandidates(board);
				changesByStrategy = strategy.apply(board);
				if (changesByStrategy > 0) {
					log.debug("{}:{}", strategy.getName(), changesByStrategy);
					changesDuringStrategyRound += changesByStrategy;
					break;
				}
			}
		}
		return board.isSolved();
	}

}

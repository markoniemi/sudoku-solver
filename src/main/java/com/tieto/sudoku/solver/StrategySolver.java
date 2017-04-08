package com.tieto.sudoku.solver;

import lombok.extern.log4j.Log4j;

import com.tieto.sudoku.Board;
import com.tieto.sudoku.solver.strategy.HiddenPairStrategy;
import com.tieto.sudoku.solver.strategy.NakedPairStrategy;
import com.tieto.sudoku.solver.strategy.PointingPairsStrategy;
import com.tieto.sudoku.solver.strategy.SingleCandidateStrategy;
import com.tieto.sudoku.solver.strategy.SingleLocationStrategy;
import com.tieto.sudoku.solver.strategy.Strategy;

@Log4j
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
					log.debug(strategy.getName() + ":" + changesByStrategy);
					changesDuringStrategyRound += changesByStrategy;
					break;
				}
			}
		}
		return board.isSolved();
	}

}

package com.tieto.sudoku.solver.strategy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { 
    SingleCandidateStrategyTest.class, 
    SingleLocationStrategyTest.class,
    HiddenPairStrategyTest.class, 
    NakedPairStrategyTest.class,
    PointingPairsStrategyTest.class
    })
public class StrategyTestSuite {

}

package com.tieto.sudoku;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.tieto.sudoku.generator.SudokuGeneratorTest;
import com.tieto.sudoku.reader.SDKReaderTest;
import com.tieto.sudoku.solver.BruteForceSolverTest;
import com.tieto.sudoku.solver.StrategySolverTest;
import com.tieto.sudoku.solver.strategy.StrategyTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { 
    CellTest.class,
    BoxTest.class, 
    BoardTest.class,
    BoardToTextConverterTest.class,
    StrategyTestSuite.class,
	BruteForceSolverTest.class, 
	StrategySolverTest.class,
	SDKReaderTest.class,
	SudokuGeneratorTest.class
	})
public class UnitTestSuite {

}

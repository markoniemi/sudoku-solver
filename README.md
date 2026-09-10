# Sudoku Solver

A Java-based Sudoku puzzle solver featuring both strategic and brute-force solving algorithms, with support for generating new Sudoku puzzles.

## Features

- **Multiple Solving Strategies**: Implements intelligent solving techniques including:
  - Single Candidate
  - Single Location
  - Hidden Pair
  - Naked Pair
  - Naked Group
  - Pointing Pairs
  
- **Brute Force Solver**: For puzzles that require exhaustive search

- **Strategy Solver**: Uses constraint propagation and heuristics for efficient solving

- **Sudoku Generator**: Creates valid Sudoku puzzles with unique solutions

- **Text-based Interface**: Convert boards to/from text format for easy I/O

## Requirements

- Java 21+
- Maven 3.x

## Building

```bash
mvn clean install
```

## Testing

Run the full test suite:

```bash
mvn test
```

All 56 tests should pass.

## Usage

### Solving a Sudoku

```java
Board board = new Board();
// ... populate board with clues ...

StrategySolver strategySolver = new StrategySolver();
boolean solved = strategySolver.solve(board);

if (solved) {
    System.out.println(board);
}
```

### Generating a Sudoku

```java
SudokuGenerator generator = new SudokuGenerator();
Board puzzle = generator.generate();
```

### Converting to/from Text

```java
String boardText = new BoardToTextConverter().toText(board);
Board board = new BoardToTextConverter().toBoard(boardText);
```

## Project Structure

```
src/main/java/com/tieto/sudoku/
├── Board.java                 # Core board representation
├── Cell.java                  # Individual cell with value and candidates
├── Location.java              # Row/column position
├── Box.java                   # 3x3 box constraint
├── Line.java                  # Row or column constraint
├── BoardToTextConverter.java  # Text serialization
├── generator/
│   ├── SudokuGenerator.java   # Puzzle generation
│   ├── RandomLocationGenerator.java
│   └── LocationGenerator.java
├── reader/
│   └── SDKReader.java         # File/format parsing
└── solver/
    ├── StrategySolver.java    # Strategy-based solving
    ├── BruteForceSolver.java  # Exhaustive search
    └── strategy/
        ├── AbstractStrategy.java
        ├── SingleCandidateStrategy.java
        ├── SingleLocationStrategy.java
        ├── HiddenPairStrategy.java
        ├── NakedPairStrategy.java
        ├── NakedGroupStrategy.java
        └── PointingPairsStrategy.java
```

## Dependencies

- **Spring Framework 5.3.33**: Dependency injection
- **Lombok 1.18.30**: Annotation processing for boilerplate reduction
- **Apache Commons Lang 3.14.0**: Utility functions
- **SLF4J 1.7.36 + Log4j 1.2.17**: Logging
- **Google Guava**: Collections and utilities
- **JUnit 4.13.2**: Testing

## Recent Updates

The project has been modernized to Java 21 with:
- Updated to JDK 21 language features (records, sealed classes)
- Removed deprecated APIs and code
- Applied Java 21 best practices
- Cleaned up dead code and unused implementations
- All tests passing with zero failures

## CI/CD

Automated testing runs on:
- GitHub Actions (JDK 21)
- Azure Pipelines (JDK 21)

## License

[Add license information]

## Contributing

[Add contribution guidelines]

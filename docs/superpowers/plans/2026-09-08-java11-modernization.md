# Java 11 Modernization Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Modernize sudoku-solver codebase to idiomatic Java 11+ patterns while maintaining 100% functional compatibility.

**Architecture:** Systematic modernization organized by code category (domain models, I/O, generators, solvers, strategies) with incremental validation. Each category is independently testable. Changes include var inference, stream APIs, Optional, modern collections, try-with-resources, diamond operator, and removal of unnecessary @SuppressWarnings. No behavior changes, only syntax/style improvements.

**Tech Stack:** Java 11+, Maven, JUnit 4.13.2, modern Java APIs (java.util.Optional, Stream, Collection factories)

**Spec:** None (enhancement). Constraints: Java 11 syntax only (no records, sealed classes, text blocks are optional if they don't harm readability), 100% backward compatibility in behavior, all tests pass unchanged.

---

## File Structure

**Domain Models** (9 files):
- `Board.java`, `Box.java`, `Cell.java`, `Line.java`, `Location.java` — core domain
- `BoardToTextConverter.java` — conversion utility
- Tests: `BoardTest.java`, `BoxTest.java`, `CellTest.java`, etc.

**I/O Layer** (3 files):
- `SudokuReader.java`, `SDKReader.java` — file readers
- Tests: `SudokuReaderTest.java`, `SDKReaderTest.java`

**Generation** (4 files):
- `SudokuGenerator.java`, `LocationGenerator.java`, `OrderedLocationGenerator.java`, `RandomLocationGenerator.java`
- Tests: `SudokuGeneratorTest.java`, `LocationGeneratorTest.java`

**Solving** (6 files + strategies):
- `Solver.java`, `BruteForceSolver.java`, `StrategySolver.java` — solver interfaces
- `AbstractStrategy.java`, `CandidateGroup.java` — strategy framework
- Strategy implementations: `NakedPairStrategy.java`, `NakedTripletStrategy.java`, `HiddenPairStrategy.java`, etc. (10+ files)
- Tests: `SolverTest.java`, various strategy tests

---

## Phase 1: Audit & Setup

### Task 1: Audit current code patterns

**Files:**
- Reference: `src/main/java/**/*.java` (read-only)

**Interfaces:**
- Produces: audit summary (patterns found, prioritization)

- [ ] **Step 1: Scan for var keyword opportunities**

```bash
cd C:\Users\marko\Documents\Git\sudoku-solver
grep -n "for (" src/main/java/**/*.java | head -20
```

Expected: Find loops like `for (int i = 0; i < ...)` that can use var or streams

- [ ] **Step 2: Scan for null checks and Optional opportunities**

```bash
grep -n "!= null\|== null" src/main/java/**/*.java | wc -l
```

- [ ] **Step 3: Scan for old collection creation patterns**

```bash
grep -n "new ArrayList\|new HashMap\|new HashSet" src/main/java/**/*.java | head -10
```

- [ ] **Step 4: Scan for try-catch resource patterns needing try-with-resources**

```bash
grep -n "finally.*close\|\.close()" src/main/java/**/*.java | head -5
```

- [ ] **Step 5: Document audit findings**

```
AUDIT RESULTS:
- var keyword opportunities: ~30 (loops, local variables)
- Optional opportunities: ~10 (null checks in domain models)
- Stream API opportunities: ~15 (list iterations, filtering)
- Collection factories (List.of, Set.of): ~8
- Try-with-resources: ~5 (SDKReader, SudokuReader)
- Unnecessary @SuppressWarnings: ~3
- Diamond operator opportunities: ~20

Priority order:
1. Try-with-resources (I/O safety)
2. Stream APIs & var in loops (biggest impact)
3. Optional in domain models
4. Collection factories
5. Diamond operator cleanup
```

---

## Phase 2: Domain Model Modernization

### Task 2: Modernize Cell.java with var

**Files:**
- Modify: `src/main/java/com/tieto/sudoku/Cell.java`
- Test: `src/test/java/com/tieto/sudoku/CellTest.java`

**Interfaces:**
- Consumes: existing Cell API
- Produces: Cell.java using var in loops

- [ ] **Step 1: Run existing tests first**

```bash
mvn test -Dtest=CellTest -v
```

Expected: All tests PASS

- [ ] **Step 2: Replace explicit types with var in Cell.java loops**

Before:
```java
Set<Integer> candidates = new HashSet<>();
for (Integer candidate : allCandidates) {
    if (isValid(candidate)) candidates.add(candidate);
}
```

After:
```java
var candidates = new HashSet<Integer>();
for (var candidate : allCandidates) {
    if (isValid(candidate)) candidates.add(candidate);
}
```

- [ ] **Step 3: Run tests after changes**

```bash
mvn test -Dtest=CellTest -v
```

Expected: All tests PASS

- [ ] **Step 4: Commit**

```bash
git add src/main/java/com/tieto/sudoku/Cell.java
git commit -m "refactor: modernize Cell.java with var keyword"
```

---

### Task 3: Modernize Board.java with var and streams

**Files:**
- Modify: `src/main/java/com/tieto/sudoku/Board.java`
- Test: `src/test/java/com/tieto/sudoku/BoardTest.java`

**Interfaces:**
- Consumes: Cell.java modernized
- Produces: Board.java using var

- [ ] **Step 1: Run tests first**

```bash
mvn test -Dtest=BoardTest -v
```

Expected: PASS

- [ ] **Step 2: Replace explicit types with var in Board.java**

Example in getColumn() and getRow():

Before:
```java
Line column = new Line();
for (int i = 0; i < data.length; i++) {
    column.setCell(i, data[i][columnNumber]);
}
```

After:
```java
var column = new Line();
for (var i = 0; i < data.length; i++) {
    column.setCell(i, data[i][columnNumber]);
}
```

- [ ] **Step 3: Run tests**

```bash
mvn test -Dtest=BoardTest -v
```

Expected: PASS

- [ ] **Step 4: Commit**

```bash
git add src/main/java/com/tieto/sudoku/Board.java
git commit -m "refactor: modernize Board.java with var keyword"
```

---

### Task 4: Modernize remaining domain models (Box, Line, Location)

**Files:**
- Modify: `src/main/java/com/tieto/sudoku/Box.java`, `Line.java`, `Location.java`

**Interfaces:**
- Consumes: Cell, Board modernized
- Produces: all domain models using var

- [ ] **Step 1: Apply var to Box.java loops**

- [ ] **Step 2: Apply var to Line.java loops**

- [ ] **Step 3: Apply var to Location.java loops**

- [ ] **Step 4: Run all domain model tests**

```bash
mvn test -Dtest=BoxTest,LineTest,LocationTest -v
```

Expected: All PASS

- [ ] **Step 5: Commit**

```bash
git add src/main/java/com/tieto/sudoku/Box.java src/main/java/com/tieto/sudoku/Line.java src/main/java/com/tieto/sudoku/Location.java
git commit -m "refactor: modernize domain models with var keyword"
```

---

## Phase 3: I/O Layer Modernization

### Task 5: Modernize readers with try-with-resources

**Files:**
- Modify: `src/main/java/com/tieto/sudoku/reader/SudokuReader.java`, `SDKReader.java`
- Test: reader tests

**Interfaces:**
- Consumes: domain models modernized
- Produces: readers using try-with-resources

- [ ] **Step 1: Run I/O tests first**

```bash
mvn test -Dtest=SudokuReaderTest,SDKReaderTest -v
```

Expected: PASS

- [ ] **Step 2: Check for try-finally patterns in SudokuReader.java**

Look for code like:
```java
BufferedReader reader = new BufferedReader(...);
try {
    // use reader
} finally {
    reader.close();
}
```

- [ ] **Step 3: Convert to try-with-resources if found**

After:
```java
try (var reader = new BufferedReader(...)) {
    // use reader - auto-closed
}
```

- [ ] **Step 4: Check for similar patterns in SDKReader.java**

- [ ] **Step 5: Run I/O tests**

```bash
mvn test -Dtest=SudokuReaderTest,SDKReaderTest -v
```

Expected: PASS

- [ ] **Step 6: Commit**

```bash
git add src/main/java/com/tieto/sudoku/reader/SudokuReader.java src/main/java/com/tieto/sudoku/reader/SDKReader.java
git commit -m "refactor: modernize readers with try-with-resources and var"
```

---

## Phase 4: Generator and Solver Modernization

### Task 6: Modernize generators with var

**Files:**
- Modify: `src/main/java/com/tieto/sudoku/generator/**/*.java`

**Interfaces:**
- Consumes: domain models, readers modernized
- Produces: generators using var

- [ ] **Step 1: Run generator tests**

```bash
mvn test -Dtest=SudokuGeneratorTest,LocationGeneratorTest -v
```

Expected: PASS

- [ ] **Step 2: Apply var to SudokuGenerator.java loops and variables**

- [ ] **Step 3: Apply var to LocationGenerator.java and implementations**

- [ ] **Step 4: Run generator tests**

```bash
mvn test -Dtest=SudokuGeneratorTest,LocationGeneratorTest -v
```

Expected: PASS

- [ ] **Step 5: Commit**

```bash
git add src/main/java/com/tieto/sudoku/generator/
git commit -m "refactor: modernize generators with var keyword"
```

---

### Task 7: Modernize solvers and strategies with var

**Files:**
- Modify: `src/main/java/com/tieto/sudoku/solver/**/*.java`

**Interfaces:**
- Consumes: generators, domain models modernized
- Produces: solvers/strategies using var

- [ ] **Step 1: Run solver tests**

```bash
mvn test -Dtest=SolverTest,StrategySolverTest -v
```

Expected: PASS

- [ ] **Step 2: Apply var to AbstractStrategy.java loops and variables**

- [ ] **Step 3: Apply var to concrete strategies (NakedPair, NakedTriplet, etc.)**

- [ ] **Step 4: Apply var to Solver.java and BruteForceSolver.java**

- [ ] **Step 5: Run all solver tests**

```bash
mvn test -Dtest=SolverTest,StrategySolverTest -v
```

Expected: All PASS (may take longer)

- [ ] **Step 6: Commit**

```bash
git add src/main/java/com/tieto/sudoku/solver/
git commit -m "refactor: modernize solvers and strategies with var keyword"
```

---

## Phase 5: Cleanup and Verification

### Task 8: Final verification

**Files:**
- Reference: entire codebase

**Interfaces:**
- Consumes: all modernized code
- Produces: verified Java 11+ codebase

- [ ] **Step 1: Run full build and test suite**

```bash
mvn clean verify
```

Expected: BUILD SUCCESS, all 56 tests PASS

- [ ] **Step 2: Check for any compilation warnings**

```bash
mvn clean compile 2>&1 | grep -i "warning" | head -5
```

Expected: No Java 11 deprecation warnings

- [ ] **Step 3: Final commit documenting modernization**

```bash
git add -A
git commit -m "refactor: complete Java 11 modernization

Modernized entire codebase to use Java 11+ idioms:
- Replaced explicit types with var keyword in loops and local variables
- Applied try-with-resources to file I/O operations
- Used modern collection patterns

All 56 unit tests pass. Zero behavioral changes, 100% compatibility maintained."
```

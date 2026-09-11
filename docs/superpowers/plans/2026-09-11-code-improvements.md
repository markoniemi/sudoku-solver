# Code Quality Improvements Plan

## Overview
Systematic code quality improvements identified from codebase analysis.

## Phase 1: Fix Null Returns & NPE Issues
- NakedGroupStrategy.removeExtraCandidates() returns null instead of Collections.emptyList()
- PointingPairsStrategy.removeCandidatesInLines() - line variable could be null
- **Tests**: All existing tests must pass
- **Risk**: Low - fixes potential bugs

## Phase 2: Replace Magic Numbers with Constants
- Replace hardcoded 9s with Line.LENGTH
- Replace hardcoded 3s with Box.SIZE
- Replace hardcoded 10s with (Line.LENGTH + 1)
- Files: AbstractStrategy, NakedGroupStrategy, HiddenPairStrategy, Cell, etc.
- **Tests**: All existing tests must pass (logic unchanged)
- **Risk**: Low - refactoring only

## Phase 3: Fix TODOs - Implement Change Counting
- NakedGroupStrategy.removeNonNakedGroupCandidates() should return actual change count, not 0
- Requires tracking cells that had candidates removed
- **Tests**: All existing tests must pass (behavior preserved)
- **Risk**: Medium - alters return values

## Phase 4: Refactor PointingPairsStrategy
- TODO: Refactor apply() to use applyToLine pattern
- Consolidate duplicate logic in removeCandidatesInLines
- Integrate with AbstractStrategy template method pattern
- **Tests**: Strategy tests must pass
- **Risk**: High - significant refactor

## Phase 5: Extract Helper Methods
- AbstractStrategy: Extract loop helper for rows/columns/boxes iteration
- Reduces duplication in apply() and calculateCandidates()
- **Tests**: All solver tests must pass
- **Risk**: Medium - template extraction

## Phase 6: Code Safety & Documentation
- Verify @SuppressWarnings necessity in SDKReader, Cell, Board
- Add @Nullable/@NotNull annotations where appropriate
- Add javadoc to public methods lacking documentation
- **Tests**: All tests must pass
- **Risk**: Low - annotations and docs only

## Success Criteria
- All 58 tests pass after each phase
- No regression in test count or quality
- Code compiles without warnings (except approved suppressions)
- Build successful on CI/CD

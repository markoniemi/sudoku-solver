package com.tieto.sudoku;

import org.springframework.core.convert.converter.Converter;

/**
 * Convert Board to String. For a cell that has no value, prints out the candidates.
 */
public class BoardToTextConverter implements Converter<Board, String> {
    public static final String LINE_BREAK = System
            .getProperty("line.separator");
    private static final String HORIZONTAL_LINE = "------------------------------------" + LINE_BREAK;

    /**
     * Convert Board to String. For a cell that has no value, prints out the candidates.
     */
    @Override
    public String convert(Board board) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LINE_BREAK);
        stringBuilder.append(HORIZONTAL_LINE);
        for (int row = 0; row < Line.LENGTH; row++) {
            appendLine(board, stringBuilder, board.getRow(row));
        }
        return stringBuilder.toString();
    }

    private void appendLine(Board board, StringBuilder stringBuilder, Line line) {
        StringBuilder line1 = new StringBuilder();
        StringBuilder line2 = new StringBuilder();
        StringBuilder line3 = new StringBuilder();
        for (int column = 0; column < Line.LENGTH; column++) {
            Cell cell = line.getCell(column);
            line1.append("|");
            line2.append("|");
            line3.append("|");
            if (cell.intValue() != Cell.EMPTY) {
                appendValue(line1, line2, line3, cell);
            } else {
                appendCandidates(line1, line2, line3, cell);
            }
        }
        line1.append("|");
        line2.append("|");
        line3.append("|");
        line1.append(LINE_BREAK);
        line2.append(LINE_BREAK);
        line3.append(LINE_BREAK);
        stringBuilder.append(line1);
        stringBuilder.append(line2);
        stringBuilder.append(line3);
        stringBuilder.append(HORIZONTAL_LINE);
    }

    private void appendValue(StringBuilder line1, StringBuilder line2,
            StringBuilder line3, Cell cell) {
        line1.append("   ");
        line2.append(" ");
        line2.append(cell.intValue());
        line2.append(" ");
        line3.append("   ");
    }

    private void appendCandidates(StringBuilder line1, StringBuilder line2,
            StringBuilder line3, Cell cell) {
        StringBuilder currentLine = null;
        for (int candidate = 1; candidate < 10; candidate++) {
            if (candidate < 4) {
                currentLine = line1;
            } else if (candidate < 7) {
                currentLine = line2;
            } else {
                currentLine = line3;
            }
            if (cell.isCandidate(candidate)) {
                currentLine.append(candidate);
            } else {
                currentLine.append(" ");
            }
        }
    }
}

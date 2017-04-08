package com.tieto.sudoku;


public class Board {
    /**
     * Number of cell in board.
     */
    public static final int CELL_COUNT = Line.LENGTH * Line.LENGTH;
    /**
     * Number of 3x3 boxes in the board.
     */
    public static final int BOX_COUNT = 9;
    private Cell data[][];

    public Board() {
        data = new Cell[Line.LENGTH][Line.LENGTH];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = new Cell();
            }
        }
    }

    @SuppressWarnings("PMD.ArrayIsStoredDirectly")
    public Board(int[][] data) {
        this.data = new Cell[Line.LENGTH][Line.LENGTH];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                this.data[i][j] = new Cell(data[i][j]);
            }
        }
    }

    public Line getColumn(int columnNumber) {
        Line column = new Line();
        for (int i = 0; i < data.length; i++) {
            column.setCell(i, data[i][columnNumber]);
        }
        return column;
    }

    public Line getRow(int rowNumber) {
        Line row = new Line();
        for (int i = 0; i < data[rowNumber].length; i++) {
            row.setCell(i, data[rowNumber][i]);
        }
        return row;
    }

    /**
     * Returns a 3x3 box from board. Boxes are numbered 0..9, starting from
     * upper left.
     */
    public Box getBox(int boxNumber) {
        int row = boxNumber / Box.SIZE;
        int column = boxNumber % Box.SIZE;
        Box box = new Box();
        for (int i = 0; i < Box.SIZE; i++) {
            for (int j = 0; j < Box.SIZE; j++) {
                box.setCell(i, j,
                        data[(row * Box.SIZE) + i][(column * Box.SIZE) + j]);
            }
        }
        return box;
    }

    /**
     * Returns a 3x3 box from board.
     * 
     * @param boxNumber
     * @return
     */
    public Box getBox(int row, int column) {
        Box box = new Box();
        for (int i = 0; i < Box.SIZE; i++) {
            for (int j = 0; j < Box.SIZE; j++) {
                box.setCell(i, j,
                        data[(row * Box.SIZE) + i][(column * Box.SIZE) + j]);
            }
        }
        return box;
    }

    public boolean isLegal() {
        for (int rowNumber = 0; rowNumber < Line.LENGTH; rowNumber++) {
            if (!isRowLegal(rowNumber)) {
                return false;
            }
        }
        for (int columnNumber = 0; columnNumber < Line.LENGTH; columnNumber++) {
            if (!isColumnLegal(columnNumber)) {
                return false;
            }
        }
        for (int boxNumber = 0; boxNumber < Board.BOX_COUNT; boxNumber++) {
            if (!isBoxLegal(boxNumber)) {
                return false;
            }
        }
        return true;
    }

    private boolean isRowLegal(int rowNumber) {
        for (int value = 1; value <= Line.LENGTH; value++) {
            boolean valueExists = false;
            for (int location = 0; location < data.length; location++) {
                if (data[location][rowNumber].getValue() == value
                        && valueExists) {
                    return false;
                } else if (data[location][rowNumber].getValue() == value) {
                    valueExists = true;
                }
            }
        }
        return true;
    }

    private boolean isColumnLegal(int rowNumber) {
        for (int value = 1; value <= Line.LENGTH; value++) {
            boolean valueExists = false;
            for (int location = 0; location < data.length; location++) {
                if (data[rowNumber][location].getValue() == value
                        && valueExists) {
                    return false;
                } else if (data[rowNumber][location].getValue() == value) {
                    valueExists = true;
                }
            }
        }
        return true;
    }

    private boolean isBoxLegal(int boxNumber) {
        int row = boxNumber / Box.SIZE;
        int column = boxNumber % Box.SIZE;
        for (int value = 1; value <= Line.LENGTH; value++) {
            boolean valueExists = false;
            for (int i = 0; i < Box.SIZE; i++) {
                for (int j = 0; j < Box.SIZE; j++) {
                    if (data[(row * Box.SIZE) + i][(column * Box.SIZE) + j]
                            .getValue() == value && valueExists) {
                        return false;
                    } else if (data[(row * Box.SIZE) + i][(column * Box.SIZE)
                            + j].getValue() == value) {
                        valueExists = true;
                    }
                }
            }
        }
        return true;
    }

    public boolean isSolved() {
        for (int row = 0; row < Line.LENGTH; row++) {
            for (int column = 0; column < Line.LENGTH; column++) {
                if (getCell(row, column).intValue() == Cell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setValue(int row, int column, int value) {
        data[row][column].setValue(value);
    }

    public void setCell(int row, int column, Cell cell) {
        data[row][column] = cell;
    }

    public Cell getCell(int row, int column) {
        return data[row][column];
    }

    public void setData(int[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                this.data[i][j] = new Cell(data[i][j]);
            }
        }
    }

    public Board copy() {
        Board copy = new Board();
        for (int row = 0; row < Line.LENGTH; row++) {
            for (int column = 0; column < Line.LENGTH; column++) {
                copy.setCell(row, column, new Cell(getCell(row, column)
                        .intValue()));
            }
        }
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(BoardToTextConverter.LINE_BREAK);
        for (int row = 0; row < Line.LENGTH; row++) {
            Line line = getRow(row);
            for (int column = 0; column < Line.LENGTH; column++) {
                int value = line.getCell(column).intValue();
                if (value == Cell.EMPTY) {
                    stringBuilder.append(".");
                } else {
                    stringBuilder.append(value);
                }
            }
            stringBuilder.append(BoardToTextConverter.LINE_BREAK);
        }
        return stringBuilder.toString();
    }
}

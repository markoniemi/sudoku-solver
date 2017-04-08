package com.tieto.sudoku;

import lombok.Getter;

public class Box {
    public static final int SIZE = 3;
    @Getter
    private Cell data[][];

    public Box() {
        data = new Cell[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                data[i][j] = new Cell();
            }
        }
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

    public Line asLine() {
        Line line = new Line();
        for (int location = 0; location < Line.LENGTH; location++) {
            int row = location / SIZE;
            int column = location % SIZE;
            line.setCell(location, getCell(row, column));
        }
        return line;
    }
}

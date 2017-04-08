package com.tieto.sudoku;


public class Line {
    public static final int LENGTH = 9;
	private Cell data[] = new Cell[LENGTH];

	public Line() {
		for (int i = 0; i < data.length; i++) {
			data[i] = new Cell();
		}
	}

	public void setValue(int location, int value) {
		this.data[location].setValue(value);
	}

	public void setCell(int location, Cell cell) {
		this.data[location] = cell;
	}

	public Cell getCell(int location) {
		return this.data[location];
	}
}

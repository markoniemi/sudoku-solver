package com.example.sudoku;

public record Location(int row, int column) {
    public static Location ofIndex(int index) {
        return new Location(index / Line.LENGTH, index % Line.LENGTH);
    }
}

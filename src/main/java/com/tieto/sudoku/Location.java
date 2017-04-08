package com.tieto.sudoku;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Location {
    private int row;
    private int column;
    public static Location ofIndex(int index) {
        Location location = new Location();
        location.row=index / Line.LENGTH;
        location.column=index % Line.LENGTH;
        return location;
    }
}

package com.tieto.sudoku;

import junit.framework.Assert;

import org.junit.Test;

public class BoxTest {
    @Test
    public void asLine() {
        Box box = new Box();
        box.setData(new int[3][3]);
        int value = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                box.getData()[i][j].setValue(value);
                value++;
            }
        }
        Line line = box.asLine();
        for (int i = 0; i < 9; i++) {
            Assert.assertEquals(i + 1, line.getCell(i).getValue());
        }
    }
}

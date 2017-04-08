package com.tieto.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DfCollectionStub {
    int index = -1;
    List<Map<String, ?>> data = new ArrayList<Map<String, ?>>();

    public void addRow(Map<String, ?> row) {
        data.add(row);
    }

    public String getStringValue(String key, int index) {
        List<String> values = getValues(key);
        if (index <= values.size()) {
            return values.get(index);
        }
        return null;
    }

    public List<String> getValues(String key) {
        List<String> values = new ArrayList<String>();
        for (Map<String, ?> row : data) {
            values.add(row.get(key).toString());
        }
        return values;
    }

    public int size() {
        return data.size();
    }

    public boolean next() {
        return ++index < data.size();
    }

    public String getValue(String key) {
        Map<String, ?> row = getRow(index);
        return row.get(key).toString();
    }

    public Map<String, ?> getRow(int index) {
        if (index <= data.size()) {
            return data.get(index);
        }
        return null;
    }
}

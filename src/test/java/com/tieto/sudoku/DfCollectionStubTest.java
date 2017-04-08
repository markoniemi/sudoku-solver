package com.tieto.sudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class DfCollectionStubTest {

    @Test
    public void addRow() {
        DfCollectionStub collection = createCollection();

        Assert.assertEquals("value0", collection.getStringValue("key", 0));
        Assert.assertEquals("value1", collection.getStringValue("key", 1));
        Assert.assertEquals("value2", collection.getStringValue("key", 2));
    }

    @Test
    public void getValuesAsList() {
        DfCollectionStub collection = createCollection();

        List<String> values = new ArrayList<String>();
        for (int i = 0; i < collection.size(); i++) {
            values.add(collection.getStringValue("key", i));
        }
        Assert.assertEquals(3, values.size());
        Assert.assertEquals("value0", values.get(0));
        Assert.assertEquals("value1", values.get(1));
        Assert.assertEquals("value2", values.get(2));
    }

    @Test
    public void getValue() {
        DfCollectionStub collection = createCollection();
        
        List<String> values = new ArrayList<String>();
        while (collection.next()) {
            values.add(collection.getValue("key"));
        }
        Assert.assertEquals(3, values.size());
        Assert.assertEquals("value0", values.get(0));
        Assert.assertEquals("value1", values.get(1));
        Assert.assertEquals("value2", values.get(2));
    }

    private DfCollectionStub createCollection() {
        DfCollectionStub collection = new DfCollectionStub();
        addRow(collection, "0");
        addRow(collection, "1");
        addRow(collection, "2");
        return collection;
    }

    private void addRow(DfCollectionStub collection, String index) {
        Map<String, String> row = new HashMap<String, String>();
        row.put("key", "value" + index);
        collection.addRow(row);
    }
}

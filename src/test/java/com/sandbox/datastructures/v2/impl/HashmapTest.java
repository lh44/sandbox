package com.sandbox.datastructures.v2.impl;

import com.sandbox.datastructures.v2.Map;
import org.junit.Assert;
import org.junit.Test;


public class HashmapTest {

    @Test
    public void mapTest() {
        Map<String, Integer> data = new Hashmap<>();
        data.put("HAM", 44);
        data.put("RAI", 7);
        data.put("SAI", 55);
        data.put("RUS", 63);

        Assert.assertEquals(44, (int) data.get("HAM"));
        Assert.assertEquals(7, (int) data.get("RAI"));
        Assert.assertEquals(55, (int) data.get("SAI"));
        Assert.assertEquals(63, (int) data.get("RUS"));
    }

}
package com.sandbox.datastructures.v2.impl;

import com.sandbox.datastructures.v2.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayListTest {

    @Test
    public void testAddRemoveGet() {
        List<Integer> list = new ArrayList<>();

        //add
        list.add(1); list.add(2); list.add(3);

        //get
        assertEquals(1, (int) list.get(0));
        assertEquals(2, (int) list.get(1));
        assertEquals(3, (int) list.get(2));

        //remove
        assertFalse(list.remove(new Integer(44)));
        list.add(44);
        assertTrue(list.remove(new Integer(44)));

        //remove at index
        assertEquals(3, list.size());
        list.remove(2);
        assertEquals(2, list.size());
        list.remove(1);
        assertEquals(1, list.size());
        list.remove(0);

        //empty
        assertTrue(list.isEmpty());
    }

    @Test
    public void expandAndShrink() {
        List<Integer> list = new ArrayList<>();
        int elementCount = 20;
        for (int i = 0; i < elementCount; i++) {
            list.add(i);
        }
        assertEquals(elementCount, list.size());

        for (int i = 0; i < elementCount; i++) {
            assertEquals(i, (int) list.get(i));
        }

        for (int i = elementCount-1 ; i >= 0; i--) {
            list.remove(i);
        }

        list.add(2);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getElementFailure() {
        List<Integer> list = new ArrayList<>();
        list.get(0);
    }

    @Test
    public void testIndexedAddRemoveGet() {
        List<Integer> list = new ArrayList<>();
        //add
        list.add(0, 44); list.add(1, 33); list.add(2, 55);

        //get
        assertEquals(44, (int) list.get(0));
        assertEquals(33, (int) list.get(1));
        assertEquals(55, (int) list.get(2));

        //set
        assertEquals(44, (int) list.set(0, 7));
        assertEquals(33, (int) list.set(1, 3));
        assertEquals(55, (int) list.set(2, 4));
        assertEquals(7, (int) list.get(0));
        assertEquals(3, (int) list.get(1));
        assertEquals(4, (int) list.get(2));

        //empty
        assertEquals(3, list.size());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void setElementFailure() {
        List<Integer> list = new ArrayList<>();
        list.set(0, 14);
    }
}
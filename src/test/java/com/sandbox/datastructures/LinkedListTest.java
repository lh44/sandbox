package com.sandbox.datastructures;

import com.sandbox.datastructures.v1.LinkedList;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class LinkedListTest {

    @Test
    public void testLinkedList() {
        final int size = 5;
        LinkedList<Integer> linkedList = new LinkedList<>();

        for (int i=0; i<size; i++) linkedList.add(i);

        assertEquals(size, linkedList.size());

        for (int i=0; i<size; i++) assertEquals((int)i, (int)linkedList.getAt(i));
        try {
            linkedList.getAt(445);
            fail("Does not throw any exception when an element is not found.");
        } catch (NoSuchElementException e) {
            assert true;
        }

        try {
            linkedList.getAt(-1);
            fail("Does not throw any exception when an element is not found.");
        } catch (NoSuchElementException e) {
            assert true;
        }

        for (int i=0; i<size; i++) linkedList.remove();
        assertEquals(true, linkedList.isEmpty());

        for (int i=0; i<size; i++) linkedList.add(i);
        linkedList.addAt(344, 3);
        assertEquals((int)344, (int)linkedList.getAt(3));
        assertEquals(size+1, linkedList.size());

        linkedList.addAt(444, 0);
        assertEquals((int)444, (int)linkedList.getAt(0));
        assertEquals(size+2, linkedList.size());

        linkedList.addAt(544, 7);
        assertEquals((int)544, (int)linkedList.getAt(7));
        assertEquals(size+3, linkedList.size());

        linkedList.addAt(565, 4);
        assertEquals((int)565, (int)linkedList.get(565));
        assertEquals(size+4, linkedList.size());
        linkedList.remove(565);
        assertEquals(size+3, linkedList.size());

        try {
            assertEquals((int)565, (int)linkedList.get(565));
            fail("Does not throw any exception when an element is not found.");
        } catch (NoSuchElementException e) {
            assert true;
        }


        linkedList.addFirst(100);
        assertEquals((int)100, (int)linkedList.getAt(0));
        assertEquals(size+4, linkedList.size());

        linkedList.removeFirst();
        assertNotEquals((int)100, (int)linkedList.getAt(0));
        assertEquals(size+3, linkedList.size());
    }

}
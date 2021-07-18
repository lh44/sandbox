package com.sandbox.datastructures.v2.impl;

import com.sandbox.datastructures.v2.List;
import com.sandbox.datastructures.v2.Stack;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SingleLinkedListTest {

    @Test
    public void testAddRemoveGet() {
        List<Integer> list = new SingleLinkedList<>();

        //add
        list.add(1); list.add(2); list.add(3);

        //get
        assertEquals(3, (int) list.get(0));
        assertEquals(2, (int) list.get(1));
        assertEquals(1, (int) list.get(2));

        //remove
        assertFalse(list.remove(new Integer(44)));
        list.add(44);
        assertTrue(list.remove(new Integer(44)));

        //remove at index
        assertEquals(3, list.size());
        list.remove(new Integer(3));
        assertEquals(2, list.size());
        list.remove(new Integer(2));
        assertEquals(1, list.size());
        list.remove(new Integer(1));

        //empty
        assertTrue(list.isEmpty());
    }

    @Test
    public void expandAndShrink() {
        List<Integer> list = new SingleLinkedList<>();
        int elementCount = 20;
        for (int i = 0; i < elementCount; i++) {
            list.add(i);
        }
        assertEquals(elementCount, list.size());

        for (int i = 0; i < elementCount; i++) {
            assertEquals(elementCount-1-i, (int) list.get(i));
        }
    }

    @Test
    public void stackTests() {
        Stack<Integer> stack = new SingleLinkedList<>();

        stack.push(44);
        stack.push(3);
        stack.push(55);
        stack.push(63);
        stack.push(4);

        assertEquals(5, stack.size());

        assertEquals(1, stack.search(4));
        assertEquals(2, stack.search(63));
        assertEquals(3, stack.search(55));
        assertEquals(4, stack.search(3));
        assertEquals(5, stack.search(44));

        assertEquals(4, (int) stack.peek());
        assertEquals(4, (int) stack.pop());

        assertEquals(63, (int) stack.peek());
        assertEquals(63, (int) stack.pop());

        assertEquals(55, (int) stack.peek());
        assertEquals(55, (int) stack.pop());

        assertEquals(3, (int) stack.peek());
        assertEquals(3, (int) stack.pop());

        assertEquals(44, (int) stack.peek());
        assertEquals(44, (int) stack.pop());

        assertEquals(0, stack.size());

        assertEquals(-1, stack.search(4));
        assertEquals(-1, stack.search(63));
        assertEquals(-1, stack.search(55));
        assertEquals(-1, stack.search(3));
        assertEquals(-1, stack.search(44));
    }

}

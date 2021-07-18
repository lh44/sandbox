package com.sandbox.datastructures;

import com.sandbox.datastructures.v1.Stack;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StackTest {

    private static final int CAPACITY = 5;

    @Test
    public void testStack() {
        Stack<Integer> stack = new Stack<>();
        for (int i=1; i<=CAPACITY; i++) {
            stack.push(i);
        }
        assertEquals(true, stack.isFull());

        try {
            stack.push(44);
            fail("Stack overflow error is not thrown");
        } catch (StackOverflowError e) {
            assert true;
        }

        assertEquals(Integer.valueOf(CAPACITY), stack.peek());
        assertEquals(CAPACITY, stack.size());
        assertEquals(false, stack.isEmpty());

        for (Integer i=1; i<=CAPACITY; i++) {
            assertEquals(CAPACITY-i+1, stack.size());
            assertEquals(Integer.valueOf(CAPACITY-i+1), stack.pop());
        }
        assertEquals(true, stack.isEmpty());
        assertEquals(null, stack.pop());
        assertEquals(null, stack.peek());

    }


}
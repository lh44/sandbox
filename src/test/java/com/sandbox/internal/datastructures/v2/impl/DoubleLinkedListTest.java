package com.sandbox.internal.datastructures.v2.impl;

import com.sandbox.internal.datastructures.v2.Queue;
import com.sandbox.internal.datastructures.v2.Stack;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DoubleLinkedListTest {

    @Test
    public void stack() {
        Stack<Integer> stack = new DoubleLinkedList<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertEquals(3, stack.size());
        assertTrue(stack.pop() == 3);

        assertEquals(2, stack.size());
        assertTrue(stack.pop() == 2);

        assertEquals(1, stack.size());
        assertTrue(stack.pop() == 1);
    }

    @Test
    public void queue() {
        Queue<Integer> queue = new DoubleLinkedList<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);

        assertTrue(queue.poll() == 1);

        assertTrue(queue.poll() == 2);

        assertTrue(queue.poll() == 3);

    }
}

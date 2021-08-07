package com.sandbox.internal.datastructures.heap;

import org.junit.Test;

public class HeapTest {

    @Test
    public void maxHeap() {
        Heap maxHeap = new MaxHeap();
        int[] arr = new int[]{2, 8, 6, 1, 5, 9};
        for (int e : arr) {
            maxHeap.add(e);
        }
        maxHeap.print();
        for (int i=0; i<arr.length; i++) {
            System.out.println(maxHeap.extract());
            maxHeap.print();
        }
    }

}
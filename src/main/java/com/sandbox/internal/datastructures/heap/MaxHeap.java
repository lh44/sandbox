package com.sandbox.internal.datastructures.heap;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class MaxHeap implements Heap {

    private int[] contents = new int[0];
    private int size = 0;

    @Override
    public void add(Integer e) {
        expand();
        contents[size] = e;
        size++;
        heapify();
    }

    private void expand() {
        contents = Arrays.copyOf(contents, size+1);
    }

    private void shrink() {
        contents = Arrays.copyOf(contents, size);
    }

    @Override
    public void print() {
        System.out.println(Arrays.toString(contents));
    }

    @Override
    public int extract() {
        if (size > 0) {
            int res = contents[0];
            contents[0] = contents[size - 1];
            heapifyUp();
            size--;
            shrink();
            return res;
        }
        throw new NoSuchElementException();
    }

    private void heapifyUp() {
        heapifyUp(0);
    }

    private void heapifyUp(int i) {
        int largest = i;
        int l = 2*i+1;
        int r = l+1;

        if (l < size && r < size && contents[i] < Math.max(contents[l], contents[r])) {
            largest = contents[l] > contents[r] ? l : r;
        } else if (l < size && contents[l] > contents[i]) {
            largest = l;
        }else if (r < size && contents[r] > contents[i]) {
            largest = r;
        }
        if (largest != i) {
            swap(largest, i);
            heapifyUp(largest);
        }
    }

    private void swap(int i, int j) {
        int temp = contents[i];
        contents[i] = contents[j];
        contents[j] = temp;
    }

    private void heapify() {
        heapify(size-1);
    }

    private void heapify(int i) {
        if (contents[i] > contents[(i-1)/2]) {
            swap(contents[i], contents[(i-1)/2]);
            heapify((i-1)/2);
        }
    }
}

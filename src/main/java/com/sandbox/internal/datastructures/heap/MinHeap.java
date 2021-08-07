package com.sandbox.internal.datastructures.heap;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class MinHeap {

    private int[] contents = new int[0];
    private int size = 0;

    public void add(int e) {
        expand();//before adding
        contents[size] = e;
        size++;
        heapify();
    }

    public void delete(int e) {
        deleteValue(e);
        shrink();//always after deleting
    }

    public void print() {
        if (size > 0) {
            System.out.println(contents[0]);
        }
    }

    private void deleteValue(int e) {
        int index = search(e);
        if (index == -1) {
            throw new NoSuchElementException();
        }
        contents[index] = contents[size-1];
        size--;
        heapifyDown();
    }

    private int search(int e) {
        for (int i=0; i<size; i++) {
            if (contents[i] == e) {
                return i;
            }
        }
        return -1;
    }

    private void swap(int i, int j) {
        int temp = contents[i];
        contents[i] = contents[j];
        contents[j] = temp;
    }

    private void heapifyDown() {
        heapify(0);
    }

    private void heapifyDown(int i) {
        int smallest = i;
        int l = 2*i+1;
        int r = l+1;

        if (l < size && r < size && contents[i] > Math.min(contents[l], contents[r])) {
            smallest = contents[l] < contents[r] ? l : r;
        } else if (l < size && contents[l] < contents[i]) {
            smallest = l;
        }else if (r < size && contents[r] < contents[i]) {
            smallest = r;
        }
        if (smallest != i) {
            swap(smallest, i);
            heapifyDown(smallest);
        }
    }

    private void heapify() {
        heapify(size-1);
    }

    private void heapify(int i) {
        if (contents[i] < contents[(i-1)/2]) {
            swap(i, (i-1)/2);
            heapify((i-1)/2);
        }
    }

    private void expand() {
        contents = Arrays.copyOf(contents, size+1);
    }

    private void shrink() {
        contents = Arrays.copyOf(contents, size);
    }
}

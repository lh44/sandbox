package com.sandbox.internal.datastructures.v2.impl;

import com.sandbox.internal.datastructures.v2.List;

import java.util.Arrays;

public class ArrayList<E> implements List<E> {
    private static final int INITIAL_CAPACITY = 10;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private int size = 0;
    private Object[] a = new Object[INITIAL_CAPACITY];

    @Override
    public int size() {
        return size;
    }

    /**
     * O(1)
     * @param e
     * @return
     */
    @Override
    public boolean add(E e) {
        resize();
        a[size] = e;
        size++;
        return true;
    }

    /**
     * O(n)
     * @param o
     * @return
     */
    @Override
    public boolean remove(Object o) {
        compress();
        for (int i = 0; i < size; i++) {
            if (o.equals(a[i])) {
                leftShift(i);
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * O(1)
     * @param index
     * @return
     */
    @Override
    public E get(int index) {
        sizeCheck(index);
        return (E) a[index];
    }

    /**
     * O(1)
     * @param index
     * @param element
     * @return
     */
    @Override
    public E set(int index, E element) {
        sizeCheck(index);
        E e = (E) a[index];
        a[index] = element;
        return e;
    }

    /**
     * O(n)
     * @param index
     * @param element
     */
    @Override
    public void add(int index, E element) {
        if (index > size) throw new ArrayIndexOutOfBoundsException();
        resize();
        size++;
        rightShift(index);
        a[index] = element;
    }

    /**
     * O(n)
     * @param index
     * @return
     */
    @Override
    public E remove(int index) {
        compress();
        sizeCheck(index);
        E e = (E) a[index];
        leftShift(index);
        size--;
        return e;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void leftShift(int i) {
        for (int j = i; j < size-1; j++) {
            a[j] = a[j+1];
        }
    }

    private void rightShift(int i) {
        for (int j = size - 1; j > i; j--) {
            a[j] = a[j-1];
        }
    }

    private void resize() {
        if (size >= (a.length * DEFAULT_LOAD_FACTOR)) {
            int s = 2 * a.length;
            a = Arrays.copyOf(a, s);
        }
    }

    private void compress() {
        if (size <= (a.length / 2)) {
            int s = a.length / 2;
            Object[] tmp = new Object[s];
            for (int i=0; i<size; i++) {
                tmp[i] = a[i];
            }
            a = tmp;
        }
    }


    private void sizeCheck(int index) {
        if (index >= size) throw new ArrayIndexOutOfBoundsException();
    }
}

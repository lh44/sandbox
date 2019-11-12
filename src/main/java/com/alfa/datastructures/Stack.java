package com.alfa.datastructures;

import java.util.Iterator;

public class Stack<T> implements Iterable<T> {

    private static final int CAPACITY = 5;
    private Object[] datastore = new Object[CAPACITY];
    private int size = 0;

    public void push(T t) {
        if (!isFull()) {
            datastore[size] = t;
            size++;
        } else {
            throw new StackOverflowError();
        }
    }

    public T pop() {
        if (!isEmpty()) {
            T t = (T) datastore[size-1];
            size--;
            return t;
        }
        return null;
    }

    public T peek() {
        if (!isEmpty()) {
            return (T) datastore[size-1];
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isFull() {
        return size == CAPACITY;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return !isEmpty();
            }

            @Override
            public T next() {
                return pop();
            }
        };
    }
}

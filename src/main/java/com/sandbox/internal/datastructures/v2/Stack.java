package com.sandbox.internal.datastructures.v2;

public interface Stack<E> {

    E push(E item);

    E pop();

    E peek();

    default boolean empty() {
        return size() == 0;
    }

    int size();

    int search(Object o);

}

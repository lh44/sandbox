package com.sandbox.internal.datastructures.v2;

public interface List<E> {

    int size();

    boolean add(E e);

    boolean remove(Object o);

    E get(int index);

    E set(int index, E element);

    void add(int index, E element);

    E remove(int index);

    boolean isEmpty();

}

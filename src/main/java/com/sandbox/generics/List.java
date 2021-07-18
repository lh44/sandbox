package com.sandbox.generics;

public interface List<E> {

    void add(E e);

    E get(int i);

    Iterator<E> iterator();
}

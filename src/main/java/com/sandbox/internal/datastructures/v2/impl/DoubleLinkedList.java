package com.sandbox.internal.datastructures.v2.impl;

import com.sandbox.internal.datastructures.v2.Queue;
import com.sandbox.internal.datastructures.v2.Stack;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class DoubleLinkedList<E> implements Stack<E>, Queue<E>, Iterable<E> {

    private int size = 0;
    private Node<E> first;
    private Node<E> last;

    @Override
    public E push(E item) {
        if (add(item)) {
            return item;
        }
        return null;
    }

    @Override
    public E pop() {
        return removeLast();
    }

    @Override
    public boolean add(E e) {
        Node<E> f = first;
        first = new Node<>(e, f, null);
        if (last == null) {
            last = f;
        }
        if (f != null) {
            f.prev = first;
        }
        size++;
        return true;
    }

    public boolean addLast(E e) {
        Node<E> l = last;
        last = new Node<>(e, null, l);
        if (first == null) {
            first = l;
        }
        if (l != null) {
            l.next = last;
        }
        size++;
        return true;
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E remove() {
        if (size > 0) {
            Node<E> f = first;
            if (size == 1) {
                first = null;
                last = null;
                return f.e;
            }
            first = first.next;
            first.prev = null;
            size--;
            return f.e;
        }
        return null;
    }

    public E removeLast() {
        if (size > 0) {
            Node<E> l = last;
            if (size == 1) {
                first = null;
                last = null;
                return l.e;
            }
            last = last.prev;
            last.next = null;
            size--;
            return l.e;
        }
        return null;
    }

    @Override
    public E poll() {
        return removeLast();
    }

    @Override
    public E element() {
        return peek();
    }

    @Override
    public E peek() {
        if (size > 0) {
            return last.e;
        }
        return null;
    }

    @Override
    public boolean empty() {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int search(Object o) {
        return 0;
    }

    public E remove(E e) {
        return null;
    }

    public E get(int i) {
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super E> action) {
    }

    @Override
    public Spliterator<E> spliterator() {
        return null;
    }

    private static class Node<E> {
        public E e;
        public Node<E> next;
        public Node<E> prev;

        private Node(E e, Node<E> next, Node<E> prev) {
            this.e = e;
            this.next = next;
            this.prev = prev;
        }
    }
}

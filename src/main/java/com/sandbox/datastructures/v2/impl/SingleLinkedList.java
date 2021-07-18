package com.sandbox.datastructures.v2.impl;

import com.sandbox.datastructures.v2.List;
import com.sandbox.datastructures.v2.Stack;

public class SingleLinkedList<E> implements List<E>, Stack<E> {

    private int size;
    private Node<E> first;

    @Override
    public E push(E item) {
        if (add(item)) {
            return item;
        }
        return null;
    }

    @Override
    public E pop() {
        E e = peek();
        remove(e);
        return e;
    }

    @Override
    public E peek() {
        if (size > 0) {
            return first.e;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int search(Object o) {
        return lastIndexOf((E) o);
    }

    @Override
    public boolean add(E e) {
        if (first == null) {
            first = new Node<>(e, null);
            size++;
            return true;
        }
        first = new Node<>(e, first);
        size++;
        return true;
    }

    private int lastIndexOf(E e) {
        int i = 1;
        if (size > 0) {
            Node<E> el = first;
            while (el != null) {
                if (el.e.equals(e)) {
                    return i;
                }
                el = el.next;
                i++;
            }
        }
        return -1;
    }

    @Override
    public boolean remove(Object o) {
        if (first == null || o == null) {
            return false;
        } else if (first.e.equals(o)) {
            first = first.next;
            size--;
            return true;
        }

        Node<E> el = first;
        while (el.next != null) {
            if (el.next.e.equals(o)) {
                el.next = el.next.next;
                size--;
                return true;
            }
            el = el.next;
        }
        return false;
    }

    @Override
    public E get(int index) {
        sizeCheck(index);
        int i = 0;
        Node<E> e = first;
        while (i < size) {
            if (i == index) {
                return e.e;
            }
            e = e.next;
            i++;
        }
        return null;
    }

    @Override
    public E set(int index, E element) {
        sizeCheck(index);
        if (index == 0) {
            first = new Node<>(element, first.next);
        }
        int i = 0;
        Node<E> el = first;
        while (i < index) {
            if (index == i+1) {
                el.next = el.next.next;
                return el.next.e;
            }
            el = el.next;
            i++;
        }
        return null;
    }

    @Override
    public void add(int index, E element) {
        sizeCheck(index);
        if (index == 0) {
            first = new Node<>(element, first.next);
            size++;
            return;
        }

        int i = 0;
        Node<E> el = first;
        while (i < index) {
            if (index == i+1) {
                Node<E> tmp = el.next;
                el.next = new Node<>(element, tmp);
                size++;
            }
            el = el.next;
            i++;
        }
    }

    @Override
    public E remove(int index) {
        sizeCheck(index);
        if (index == 0) {
            Node<E> tmp = first;
            first = first.next;
            size--;
            return tmp.e;
        }
        int i = 1;
        Node<E> e = first;
        while (i < size) {
            if (index == i+1) {
                Node<E> tmp = e.next;
                e.next = e.next.next;
                size--;
                return tmp.e;
            }
            e = e.next;
            i++;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    private static class Node<E> {
        public E e;
        public Node<E> next;

        private Node(E e, Node<E> next) {
            this.e = e;
            this.next = next;
        }
    }

    private void sizeCheck(int index) {
        if (index >= size) throw new ArrayIndexOutOfBoundsException();
    }
}

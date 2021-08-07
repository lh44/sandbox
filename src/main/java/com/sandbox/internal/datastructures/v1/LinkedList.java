package com.sandbox.internal.datastructures.v1;

import java.util.NoSuchElementException;

public class LinkedList<E> {

    private Node<E> last;
    private Node<E> first;
    private int size;

    //Adds an element to the last
    public void add(E e) {
        Node l = last;
        Node node = new Node(e);
        last = node;
        if (l == null) first = node;
        else {
            l.next = node;
            node.prev = l;
        }
        size++;
    }

    //removes an element at the end
    public void remove() {
        if (size > 0) {
            Node l = last;
            last = null;
            last = l.prev;
            size--;
        }
    }

    public void addAt(E el, int index) {
        if (index > size) {
            return;
        }
        Node node = new Node(el);
        if (first == null) {
            first = node;
            last = node;
        } else if (index == 0) {
            Node f = first;
            first = node;
            first.next = f;
            f.prev = first;
        } else if (index == size) {
            last.next = node;
            node.prev = last;
            last = node;
        } else {
            int count = 0;
            Node current = first;
            while (current.next != null) {
                if (count == index-1) {
                    Node next = current.next;
                    current.next = node;
                    node.prev  = current;
                    node.next = next;
                    next.prev = node;
                    break;
                }
                current = current.next;
                count++;
            }
        }
        size++;
    }

    public E getAt(int index) {
        if (size == 0 || index < 0) {
            throw new NoSuchElementException();
        }

        int count = 0;
        Node current = first;
        while (current != null) {
            if (count == index) return (E) current.data;
            current = current.next;
            count++;
        }
        throw new NoSuchElementException();
    }

    public E get(E el) {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        Node current = first;
        while (current != null) {
            if (el.equals(current.data)) return (E) current.data;
            current = current.next;
        }
        throw new NoSuchElementException();
    }

    public void remove(E e) {
        if (size > 0) {
            Node current = first;
            while (current != null) {
                if (current.data.equals(e)) {
                    Node c = current;
                    current = null;
                    if (c.prev != null) {
                        c.prev.next = c.next;
                    }
                    if (c.next != null) {
                        c.next.prev = c.prev;
                    }
                    size--;
                    return;
                }
                current = current.next;
            }
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(E e) {
        Node node = new Node(e);
        if (size == 0) {
            first = node;
            last = node;
        } else {
            Node f = first;
            node.next = f;
            f.prev = node;
            first = node;
        }
        size++;
    }

    public void removeFirst() {
        if (size > 0) {
            Node f = first;
            first = null;
            first = f.next;
            size--;
        }
    }

    static class Node<T> {
        T data;
        Node next;
        Node prev;
        Node(T data) {
            this.data = data;
        }
    }

    public void print() {
        Node current = first;
        int count = 0;
        while (current.next != null) {
            System.out.printf(String.format("%1$"+count+"s", "") + "{}:{} -> {}\n", count, current.data, current.next.data);
            count++;
            current = current.next;
        }
    }
}

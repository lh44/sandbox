package com.sandbox.internal.datastructures.heap;

public class Node<E extends Comparable<E>> {
    E e;
    Node<E> left;
    Node<E> right;

    public Node(E e, Node<E> left, Node<E> right) {
        this.e = e;
        this.left = left;
        this.right = right;
    }
}

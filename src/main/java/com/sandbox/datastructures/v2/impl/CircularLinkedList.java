package com.sandbox.datastructures.v2.impl;

public class CircularLinkedList<E> {

    private int size = 0;

    private static class Node<E> {
        public E e;
        public Node<E> next;

        private Node(E e, Node<E> next) {
            this.e = e;
            this.next = next;
        }
    }
}

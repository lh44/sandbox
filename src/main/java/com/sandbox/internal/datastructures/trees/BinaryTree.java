package com.sandbox.internal.datastructures.trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BinaryTree {

    Node root;

    Node addRecursive(Node current, int value) {
        if (current == null) {
            //We've reached a leaf node, add new node
            return new Node(value);
        }

        if (value < current.value) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value);
        } else {
            //Node already present
            return current;
        }
        return current;
    }

    public void add(int value) {
        root = addRecursive(root, value);
    }

    public void populateTree() {
        Random r = new Random();
        for (int i = 0; i < 99998; i++) {
            add(r.nextInt((99999 - 1) + 1) + 1);
        }
    }

    public boolean search(int value) {
        return search(root, value);
    }

    private boolean search(Node current, int value) {
        if (current == null)
            return false;
        if (current.value == value)
            return true;
        return (value < current.value) ? search(current.left, value):
                search(current.right, value);
    }

    public void delete(int value) {
        delete(root, value);
    }

    /**
     1. a node has no children – this is the simplest case; we just need to replace this node with null in its parent node
     2. a node has exactly one child – in the parent node, we replace this node with its only child.
     3. a node has two children – this is the most complex case because it requires a tree reorganization
     */
    private Node delete(Node current, int value) {
        if (current == null) {
            return null;
        }
        if (current.value == value) {
            //Node to delete
            if (current.left == null && current.right == null)
                return null;
            if (current.left == null)
                return current.right;
            if (current.right == null)
                return current.left;
            int smallestValue = findSmallestValue(current.right);
            current.value = smallestValue;
            current.right = delete(current.right, smallestValue);
            return current;
        }
        if (value > current.value)
            current.left = delete(current.left, value);
        current.right = delete(current.right, value);
        return current;
    }

    private int findSmallestValue(Node root) {
        return root.left == null ? root.value : findSmallestValue(root.left);
    }

    //Traversing the Tree
    public void traverse() {
        System.out.println("DFS");
        System.out.println("In Order");
        traverseInOrder(root);
    }

    //Depth-First search
    //DFS goes as deep as possible in every child before exploring the next search

    /**
     * The in-order traversal consists of visiting the left subtree, then the root node, and finally the right subtree
     * @param node
     */
    private void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.print(" " + node.value);
            traverseInOrder(node.right);
        }

    }

    /**
     * The pre-order traversal visits the root node first, then the left subtree, and finally the right subtree
     * @param node
     */
    private void traversePreOrder(Node node) {
        if (node != null) {
            System.out.print(" " + node.value);
            traverseInOrder(node.left);
            traverseInOrder(node.right);
        }

    }

    /**
     * The post-order traversal consists of visiting the left subtree, then the right subtree, and finally the root node
     * @param node
     */
    private void traversePostOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            traverseInOrder(node.right);
            System.out.print(" " + node.value);
        }
    }

    //Breadth-First search
    //BFS also called level-order, visits all levels starting from the root, from left to right

    /**
     * For the implementation, we’ll use a Queue to hold the nodes from each level in order.
     * We’ll extract each node from the list, print its values, then add its children to the queue
     */
    private void traverseLeverOrder() {
        if (root == null) {
            return;
        }
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);
        while (nodes.isEmpty()) {
            Node node = nodes.remove();
            System.out.println(node.value);

            if (node.left != null)
                nodes.add(node.left);

            if (node.right != null)
                nodes.add(node.right);
        }
    }

}

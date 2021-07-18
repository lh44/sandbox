package com.sandbox.datastructures.v2.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

public class BinarySearchTree<E extends Comparable<E>> {
    private Node<E> root;
    private int size = 0;

    /**
     * Adds an element into a Binary search tree
     * @param e
     */
    public void add(E e) {
        root = add(e, root);
        size++;
    }

    private Node<E> add(E e, Node<E> current) {
        if (current == null) {
            return new Node<>(null, null, e);
        }
        if (e.compareTo(current.value) > 0) {
            current.right = add(e, current.right);
        } else if (e.compareTo(current.value) < 0) {
            current.left = add(e, current.left);
        }
        return current;
    }

    /**
     * Search recursively looks up the BST for the given element
     * @param e
     * @return
     */
    public boolean search(E e) {
        return search(e, root);
    }

    private boolean search(E e, Node<E> current) {
        if (current == null) {
            return false;
        } else if (e.compareTo(current.value) > 0) {
            return search(e, current.right);
        } else if (e.compareTo(current.value) < 0) {
            return search(e, current.left);
        }
        return true;
    }

    /**
     * Search looks up the BST for a given element in a loop
     * @param e
     * @return
     */
    public boolean searchInLoop(E e) {
        Node<E> node = root;
        while (node != null) {
            if (node.value.equals(e)) {
                return true;
            } else if (e.compareTo(node.value) > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return false;
    }

    public void delete(E e) {
        delete(e, root);
        size--;
    }

    private Node<E> delete(E e, Node<E> current) {
        if (current == null) return null;
        if (e.equals(current.value)) {
            //Node to delete
            //has no children
            if (current.left == null && current.right == null) {
                return null;
            } else if (current.right == null) {
                return current.left;
            } else if (current.left == null) {
                return current.right;
            } else {
                E smallest = findSmallest(current.right);
                current.value = smallest;
                current.right = delete(smallest, current.right);
                return current;
            }
        } else if (e.compareTo(current.value) < 0) {
            current.left = delete(e, current.left);
        }
        current.right = delete(e, current.right);
        return current;
    }

    private E findSmallest(Node<E> node) {
        return node.left == null ? node.value : findSmallest(node.left);
    }

    public void traverseLevelOrder() {
        if (root == null) return;
        Queue<Node<E>> nodes = new LinkedList<>();
        nodes.add(root);
        while (!nodes.isEmpty()) {
            Node<E> node = nodes.remove();
            System.out.println(node.value);
            if (node.left != null) {
                nodes.add(node.left);
            }
            if (node.right != null) {
                nodes.add(node.right);
            }
        }
    }

    public void traverseLevelOrderZigZag() {
        if (root == null) return;
        Stack<Node<E>> currentLevel = new Stack<>();
        Stack<Node<E>> nextLevel = new Stack<>();
        currentLevel.add(root);
        boolean invert = false;
        while (!currentLevel.isEmpty()) {
            Node<E> node = currentLevel.pop();
            System.out.println(node.value);

            if (invert) {
                if (node.right != null) {
                    nextLevel.add(node.right);
                }
                if (node.left != null) {
                    nextLevel.add(node.left);
                }
            } else {
                if (node.left != null) {
                    nextLevel.add(node.left);
                }
                if (node.right != null) {
                    nextLevel.add(node.right);
                }
            }

            if (currentLevel.isEmpty()) {
                invert = !invert;
                Stack<Node<E>> temp = currentLevel;
                currentLevel = nextLevel;
                nextLevel = temp;
            }
        }
    }

    public void balance() {
        Object[] arr = traverseInOrder();
        this.root = null;
        balancedInsert(arr, 0, arr.length-1);
    }

    private void balancedInsert(Object[] arr, int l, int r) {
        if (l < r) {
            int mid = l + (r - l) / 2;
            add((E) arr[mid]);

            balancedInsert(arr, l, mid);
            balancedInsert(arr, mid+1, r);
        }
    }

    public Object[] traverseInOrder() {
        Stack<E> s = new Stack<>();
        traverseInOrder(root, s);
        return s.toArray();
    }

    public void traverseInOrder(Node<E> node, Stack<E> s) {
        if (node != null) {
            traverseInOrder(node.left, s);
            s.push(node.value);
            traverseInOrder(node.right, s);
        }
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node<E> node) {
        if (node == null) return true;
        int lh, rh = 0;
        lh = height(node.left);
        rh = height(node.right);

        if (Math.abs(lh-rh) <= 1 &&
                isBalanced(node.left) &&
                isBalanced(node.right)) {
            return true;
        }
        return false;
    }

    private int height(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    static class Node<E extends Comparable<E>> {
        Node<E> left;
        Node<E> right;
        E value;

        public Node(Node<E> left, Node<E> right, E value) {
            this.left = left;
            this.right = right;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3,3,3,3,5,5,5,2,2,7};
        System.out.println(minSetSize(arr));
    }

    public static int minSetSize(int[] arr) {
        Map<Integer, Integer> d = new HashMap<>();

        for (int e : arr) {
            Integer val = d.get(e) == null ? 0 : d.get(e);
            d.put(e, 1);
            if (val > 0) {
                d.put(e, val+1);
            }
        }

        List<Integer> values = d.values().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        int req = arr.length/2;
        int temp = 0;
        int i = 0;
        for (int e : values) {
            temp+=e;
            i++;
            if (temp >= req) {
                break;
            }
        }
        return i;
    }

}

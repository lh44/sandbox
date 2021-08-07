package com.sandbox.internal.datastructures.v2.impl;

import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BinarySearchTreeTest {

    @Test
    public void testBST() {
        BinarySearchTree<Integer> integerBST = new BinarySearchTree<>();

        //Integer
        integerBST.add(2132323);
        integerBST.add(2132321);
        integerBST.add(23355454);
        integerBST.add(2132324);
        integerBST.add(2132322);
        integerBST.add(23355453);
        integerBST.add(90008808);

        assertTrue(integerBST.search(2132323));
        assertTrue(integerBST.searchInLoop(2132323));

        assertTrue(integerBST.search(23355454));
        assertTrue(integerBST.searchInLoop(23355454));

        assertTrue(integerBST.search(90008808));
        assertTrue(integerBST.searchInLoop(90008808));

        assertFalse(integerBST.searchInLoop(900088081));
        assertFalse(integerBST.search(900088081));
        //integerBST.traverseLevelOrder();
        
        //String
        BinarySearchTree<String> stringBST = new BinarySearchTree<>();
        stringBST.add("Aston Martin");
        stringBST.add("Bently");
        stringBST.add("Dodge");

        assertTrue(stringBST.search("Aston Martin"));
        assertTrue(stringBST.searchInLoop("Aston Martin"));

        assertTrue(stringBST.search("Bently"));
        assertTrue(stringBST.searchInLoop("Bently"));

        assertTrue(stringBST.search("Dodge"));
        assertTrue(stringBST.searchInLoop("Dodge"));

        assertFalse(stringBST.searchInLoop("Ferrari"));
        assertFalse(stringBST.search("Ferrari"));
        //stringBST.traverseLevelOrder();

        BinarySearchTree<String> bst = new BinarySearchTree<>();
        bst.add("Monkey");
        bst.add("Donkey");
        bst.add("Varun");
        bst.add("Ball");
        bst.add("Eagle");
        bst.add("Priyanka");
        bst.add("Zoom");
        //bst.traverseLevelOrderZigZag();
    }

    @Test
    public void delete() {
        BinarySearchTree<Integer> bst2 = new BinarySearchTree<>();
        bst2.add(50);
        bst2.add(30);
        bst2.add(20);
        bst2.add(40);
        bst2.add(70);
        bst2.add(60);
        bst2.add(80);
        assertTrue(bst2.search(20));
        bst2.traverseLevelOrder();

        bst2.delete(20);
        assertFalse(bst2.search(20));
        System.out.println("After deletion of 20");
        bst2.traverseLevelOrder();

        bst2.delete(30);
        assertFalse(bst2.search(30));
        System.out.println("After deletion of 30");
        bst2.traverseLevelOrder();
    }

    @Test
    public void balancedBST() {
        BinarySearchTree<Integer> bst2 = new BinarySearchTree<>();
        bst2.add(4);
        bst2.add(3);
        bst2.add(2);
        bst2.add(1);
        bst2.add(5);
        bst2.add(6);
        bst2.add(7);
        assertFalse(bst2.isBalanced());
        bst2.traverseLevelOrder();

        bst2.balance();
        System.out.println("After");
        bst2.traverseLevelOrder();
        assertTrue(bst2.isBalanced());
    }

    @Test
    public void test() {
        Function<Integer, Boolean> f = i -> false;
    }

}
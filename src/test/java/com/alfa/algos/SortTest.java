package com.alfa.algos;

import com.alfa.util.Utils;
import org.junit.Assert;
import org.junit.Test;

import javax.rmi.CORBA.Util;
import java.util.Arrays;

public class SortTest {

    @Test
    public void bubbleSort() {
        int[] arr = Utils.getArray();
        long t1 = System.currentTimeMillis();
        Sort.bubbleSort(arr);
        long t2 = System.currentTimeMillis();
        System.out.println("Bubble sort:  | " + (t2-t1));
        Assert.assertTrue(Utils.isSorted(arr, 0));
    }

    @Test
    public void insertionSort() {
        int[] arr = Utils.getArray();
        long t1 = System.currentTimeMillis();
        Sort.insertionSort(arr);
        long t2 = System.currentTimeMillis();
        System.out.println("Insertion sort:  | " + (t2-t1));
        Assert.assertTrue(Utils.isSorted(arr, 0));
    }

    @Test
    public void selectionSort() {
        int[] arr = Utils.getArray();
        long t1 = System.currentTimeMillis();
        Sort.selectionSort(arr);
        long t2 = System.currentTimeMillis();
        System.out.println("Selection sort:  | " + (t2-t1));
        Assert.assertTrue(Utils.isSorted(arr, 0));
    }

    @Test
    public void mergeSort() {
        int[] arr = Utils.getArray();
        long t1 = System.currentTimeMillis();
        Sort.mergeSort(arr);
        long t2 = System.currentTimeMillis();
        System.out.println("Merge sort:  | " + (t2-t1));
        Assert.assertTrue(Utils.isSorted(arr, 0));
    }


    @Test
    public void quickSort() {
        int[] arr = Utils.getArray();
        long t1 = System.currentTimeMillis();
        Sort.quickSort(arr);
        long t2 = System.currentTimeMillis();
        System.out.println("Quick sort:  | " + (t2-t1));
        Assert.assertTrue(Utils.isSorted(arr, 0));
    }
}
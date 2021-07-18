package com.sandbox.algos;

import com.sandbox.util.Utils;
import org.junit.Assert;
import org.junit.Test;

public class TestSearch {

    @Test
    public void linearSearch() {
        int[] arr = Utils.getArray();
        int el = arr[arr.length-12];
        long t1 = System.currentTimeMillis();
        Assert.assertEquals(true, Search.linearSearch(arr, el));
        long t2 = System.currentTimeMillis();
        System.out.println(" Linear Search " + " | " + (t2-t1));
    }

    @Test
    public void binarySearch() {
        int[] arr = Utils.getArray();
        arr = Sort.mergeSort(arr);
        int el = arr[arr.length-12];
        long t1 = System.currentTimeMillis();
        Assert.assertEquals(true, Search.binarySearch(arr, el));
        long t2 = System.currentTimeMillis();
        System.out.println(" Binary Search " + " | " + (t2-t1));
    }

    @Test
    public void binarySearchRecursion() {
        int[] arr = Utils.getArray();
        arr = Sort.mergeSort(arr);
        int el = arr[arr.length-12];
        long t1 = System.currentTimeMillis();
        Assert.assertEquals(true, Search.binarySearchRecursion(arr, 0, arr.length, el));
        long t2 = System.currentTimeMillis();
        System.out.println(" Binary Search Recursion" + " | " + (t2-t1));
    }

}

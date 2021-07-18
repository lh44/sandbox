package com.sandbox.algos;

public class Search {

    public static boolean linearSearch(int[] arr, int el) {
        for (int i=0; i<arr.length; i++) {
            if (arr[i] == el) {
                return true;
            }
        }
        return false;
    }

    //Needs a sorted array to perform the search
    public static boolean binarySearch(int[] arr, int el) {
        int l =0 , m, r = arr.length-1;
        while (l <= r) {
            m = l + (r - l) / 2;
            if (arr[m] == el) {
                return true;
            } else if (el < arr[m]) {
                r = m-1;
            } else {
                l = m+1;
            }
        }
        return false;
    }

    //Needs a sorted array to perform the search
    public static boolean binarySearchRecursion(int[] arr, int l, int r, int el) {
        if (l > r) {
            return false;
        }
        int m = l + (r - l) / 2;
        if (el == arr[m]) {
            return true;
        } else if (el < arr[m]) {
            return binarySearchRecursion(arr, l, m-1, el);
        }
        return binarySearchRecursion(arr, m + 1, r, el);
    }
}

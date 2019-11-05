package com.alfa.util;

import java.util.Random;

public class Utils {

    private static final int ARRAY_SIZE = 25770;

    public static int[] getArray() {
        Random r = new Random();
        int[] arr = new int[ARRAY_SIZE];
        for (int i = 0; i < arr.length-1; i++) {
            arr[i] = r.nextInt((ARRAY_SIZE - 1) + 1) + 1;
        }
        return arr;
    }

    /**
     * @param arr input array to be checked for sorting
     * @param order 0 - ASC; 1 - DESC;
     * @return
     */
    public static boolean isSorted(int[] arr, int order) {
        if (order == 1)
            reverse(arr);
        for (int i =0; i < arr.length - 2; i++) {
            if (arr[i] > arr[i+1])
                return false;
        }
        return true;
    }

    private static void reverse(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length/2; i++) {
            int temp = arr[i];
            arr[i] = arr[length-1-i];
            arr[length-1-i] = temp;
        }
    }

/*    private static void print(int[] arr) {
        for (int el : arr){
            System.out.printf("%d, ", el);
        }
        System.out.println();
    }*/
}

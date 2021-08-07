package com.sandbox.external.interview.hpe;

public class Application {

    public static void main(String[] args) {

        /*int[] arr = new int[]{-5, -7, 10, 12, -15, 13};
        int l = 0;
        int r = arr.length-1;

        while (l < r) {
            if (arr[l] >= 0 && arr[r] < 0) {
                swap(arr, l ,r);
            }
            if (arr[l] < 0) {
                l++;
            }
            if (arr[r] >= 0) {
                r--;
            }
        }
        System.out.println(Arrays.toString(arr));*/
        int k = 6;
        int[] arr = new int[]{2, 1, 3, 6, 5};
        //sum 1-6-4
        //6




        //sorting n long n

        // O(n), O(n)

    }

    static void swap(int[] arr, int l, int r) {
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }

}

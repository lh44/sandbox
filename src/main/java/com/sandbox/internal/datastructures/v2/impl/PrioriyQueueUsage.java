package com.sandbox.internal.datastructures.v2.impl;

import java.util.Arrays;

public class PrioriyQueueUsage {

    public static void main(String[] args) {
        int[] factorCount = new int[101];
        for (int i=1; i<=100; i++) {
            for (int j=i; j>0; j--) {
                if (i % j == 0) {
                    factorCount[i]++;
                }
            }
        }

        int count = 0;
        for (int bulb : factorCount) {
            if (bulb % 2 != 0) {
                count++;
            }
        }
        System.out.println(count);
        System.out.println(Arrays.toString(factorCount));
    }
}

package com.sandbox.hashing;

/**
 Uses hashing to compute the frequency of a character
 */

public class Hashing {

    private int[] frequency = new int[26];

    private int hashFunc(Character c) {
        return (int)c - (int)'a';
    }

    public void countFrequency(String string) {
        for (int i=0; i<string.length(); i++) {
            int index = hashFunc(string.charAt(i));
            frequency[index]++;
        }
        print(frequency);
    }

    void print(int[] a) {
        int j=0;
        for (char i='a'; i<='z'; i++) {
            System.out.println(i + " " + frequency[j]);
            j++;
        }
    }

}
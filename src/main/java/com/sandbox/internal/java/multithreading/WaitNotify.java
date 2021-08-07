package com.sandbox.internal.java.multithreading;

public class WaitNotify {

    public static void main(String[] args) {
        try {
            System.out.println(doStuff(args));
        } catch (Exception e) {
            System.out.println("");
        }
        doStuff(args);
    }

    static int doStuff(String[] args) {
        return Integer.parseInt(args[0]);
    }
}

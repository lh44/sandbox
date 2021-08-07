package com.sandbox.internal.design.patterns.v1;

public class Singleton {

    private static Singleton singleton;

    private Singleton() {}

    public static Singleton newInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

}

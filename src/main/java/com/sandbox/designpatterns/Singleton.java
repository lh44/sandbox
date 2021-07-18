package com.sandbox.designpatterns;

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

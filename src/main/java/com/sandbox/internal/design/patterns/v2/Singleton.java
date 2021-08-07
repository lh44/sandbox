package com.sandbox.internal.design.patterns.v2;

/**
 * One and only one instance of a particular class will ever be created per classloader
 * The scope of a Spring singleton is described as "per container per bean",
 * It is the scope of bean definition to a single object instance per Spring IoC container
 */
public class Singleton {

    private static Singleton singleton;

    private Singleton() {}

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}

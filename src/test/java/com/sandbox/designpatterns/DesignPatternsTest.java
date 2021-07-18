package com.sandbox.designpatterns;

import org.junit.Test;

import static org.junit.Assert.*;

public class DesignPatternsTest {

    @Test
    public void singleton() {
        Singleton s = Singleton.newInstance();
    }

}
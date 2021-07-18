package com.sandbox.algos.dynamic;

import org.junit.Test;

import static org.junit.Assert.*;

public class DP1Test {

    private DP1 dp = new DP1();

    @Test
    public void factorial() {
        long start = System.currentTimeMillis();
        long actual = dp.factorialD(44);
        System.out.println("Took " + (System.currentTimeMillis()-start) + " ms");
        assertEquals(2673996885588443136l, actual);
    }

    @Test
    public void fib() {
        assertEquals(dp.fibTopBottom(24), dp.fibBottomUp(24));
    }
}
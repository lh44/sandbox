package com.sandbox.internal.design.patterns.v1;

import java.util.Arrays;
import java.util.List;

public class Structural {

    static class Adapter {

        public void some() {
            //Here, Arrays#asList is helping us adapt an Array to a List.
            List<String> musketeers = Arrays.asList("Athos", "Aramis", "Porthos");
        }
    }
}

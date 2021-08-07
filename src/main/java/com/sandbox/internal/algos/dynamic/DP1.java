package com.sandbox.internal.algos.dynamic;

public class DP1 {

    public long factorial(int n) {
        if (n < 2) {
            return 1;
        }
        return n * factorial(n-1);
    }

    public long factorialD(int n) {
        long[] data = new long[n+1];
        data[0] = 1;
        for (int i=1; i<=n; i++) {
            data[i] = i * data[i-1];
        }
        return data[n];
    }

    public long fibBottomUp(int n) {
        long[] data = new long[n+1];
        data[0] = 0;
        data[1] = 1;

        for (int i=2; i<=n; i++) {
            data[i] = data[i-1] + data[i-2];
        }
        return data[n];
    }

    public long fibTopBottom(int n) {
        long[] data = new long[n+1];
        return fibTopBottom(n, data);
    }

    private  long fibTopBottom(int n, long[] data) {
        if (n <= 2) {
            return 1;
        } else if (data[n] != 0) {
            return data[n];
        } else {
            data[n] = fibTopBottom(n - 1, data) + fibTopBottom(n - 2, data);
        }
        return data[n];
    }

}

package com.sandbox.external.interview.hpe;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Hpe {

    public static void main(String[] args) {
        System.out.println(isValid("aabbccddeefghi"));
    }

    public static String isValid(String s) {
        int[] chars = new int[26];
        for (char c : s.toCharArray()) {
            chars[c - 'a']++;
        }

        int count = 0;
        boolean found = false;
        for (int i : chars) {
            if (i > 0) {
                if (count == 0) {
                    count = i;
                } else if (found && Math.abs(count - i) != 0) {
                    System.out.println(i);
                    return "NO";
                } else if (!found && Math.abs(count - i) == 1) {
                    found = true;
                }
            }
        }
        return "YES";
    }

    static class ProducerConsumer {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Queue<Long> queue = new PriorityQueue<>();

        public ProducerConsumer() {
            produce();
            consume();
        }

        public void produce() {
            executorService.submit(() -> {
                while (true) {
                    Thread.sleep(1000);
                    if (queue.isEmpty()) {
                        queue.add(System.currentTimeMillis());
                    }
                }
            });
        }

        public void consume() {
            executorService.submit(() -> {
                while (true) {
                    Thread.sleep(1000);
                    if (!queue.isEmpty()) {
                        System.out.println(queue.remove());
                    }
                }
            });
        }
    }

    public void threads() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<?> future1 = executorService.submit(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Future<?> future2 = executorService.submit(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        while (!future1.isDone() && !future2.isDone()) {}
        System.out.println("Done");
    }
}


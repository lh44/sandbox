package com.sandbox.internal.others.random;

import com.sandbox.internal.datastructures.heap.MinHeap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Some {

    public static void main(String[] args) throws IOException {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            int size = Integer.parseInt(bufferedReader.readLine().trim());
            List<int[]> operations = new ArrayList<>();
            for (int i=0; i<size; i++) {
                String op = bufferedReader.readLine().trim();
                String[] arr = op.split("\\s");
                if (arr.length > 1) {
                    operations.add(new int[]{Integer.parseInt(arr[0]), Integer.parseInt(arr[1])});
                } else {
                    operations.add(new int[]{Integer.parseInt(arr[0]), 0});
                }
            }
            performOperations(operations);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void performOperations(List<int[]> operations) {
        MinHeap heap = new MinHeap();
        for (int[] operation : operations) {
            switch (operation[0]) {
                case 1:
                    heap.add(operation[1]);
                    break;
                case 2:
                    heap.delete(operation[1]);
                    break;
                case 3:
                    heap.print();
                    break;
            }
        }
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, Integer> courses = new HashMap<>();
        for (int[] e : prerequisites) {
            courses.put(e[0], e[1]);
        }

        int possible = 0;
        for (int course : courses.keySet()) {
            int dependency = courses.get(course);
            if (courses.get(dependency) == null || courses.get(dependency) != course) {
                possible++;
                if (possible == numCourses) {
                    return true;
                }
            }
        }
        return false;
    }


}

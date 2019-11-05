package com.alfa.algos;

public class Sort {
    /**
     * These are the most common sorting algorithms with some recommendations for when to use them.
     * Internalize these! They are used everywhere!
     *
     * Heap sort: When you don’t need a stable sort and you care more about worst case performance than
     * average case performance. It’s guaranteed to be O(N log N), and uses O(1) auxiliary space,
     * meaning that you won’t unexpectedly run out of heap or stack space on very large inputs.
     *
     * Introsort: This is a quick sort that switches to a heap sort after a certain recursion depth to
     * get around quick sort’s O(N²) worst case. It’s almost always better than a plain old quick sort,
     * since you get the average case of a quick sort, with guaranteed O(N log N) performance.
     * Probably the only reason to use a heap sort instead of this is in severely memory constrained
     * systems where O(log N) stack space is practically significant.
     *
     * Insertion sort: When N is guaranteed to be small, including as the base case of a quick sort or merge sort.
     * While this is O(N²), it has a very small constant and is a stable sort.
     *
     *
     *
     * Quick sort: When you don’t need a stable sort and average case performance matters more than worst case performance.
     * A quick sort is O(N log N) on average, O(N²) in the worst case. A good implementation uses O(log N) auxiliary
     * storage in the form of stack space for recursion.
     *
     * Merge sort: When you need a stable, O(N log N) sort, this is about your only option. The only downsides
     * to it are that it uses O(N) auxiliary space and has a slightly larger constant than a quick sort.
     * There are some in-place merge sorts, but AFAIK they are all either not stable or worse than O(N log N).
     * Even the O(N log N) in place sorts have so much larger a constant than the plain old merge sort that they’re
     * more theoretical curiosities than useful algorithms.
     *
     * Non-comparison sorts: Under some fairly limited conditions it’s possible to break the O(N log N) barrier
     * and sort in O(N)! Here are some cases where that’s worth a try:
     *
     * Counting sort: When you are sorting integers with a limited range.
     *
     * Radix sort: When log(N) is significantly larger than K, where K is the number of radix digits.
     *
     * Bucket sort: When you can guarantee that your input is approximately uniformly distributed.
     */


    /**
     * Bubble sort: O(n2)
     * Traverses through the array swapping the elements until no swapping is needed
     * BubbleSort can be practical if the input is in mostly sorted order with some out-of-order elements nearly in position
     * is named for the way smaller or larger elements "bubble" to the top of the list
     * Bubble sort has a worst-case and average complexity of О(n2)
     * When the list is already sorted (best-case), the complexity of bubble sort is only O(n)
     * @return
     */
    public static int[] bubbleSort(int[] arr) {
        for (int i=0; i<arr.length; i++) {
            boolean swapped = false;
            for (int j=i; j<arr.length-1; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        return arr;
    }

    // InsertionSort efficient for data sets that are already substantially sorted:
    // the time complexity is O(kn) when each element in the input is no more than k places away from its sorted position
    //
    // does not change the relative order of elements with equal keys
    // The resulting array after k iterations has the property where the first k + 1 entries are sorted
    // The best case input is an array that is already sorted. In this case insertion sort has a linear running time (i.e., O(n))
    public static int[] insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j-1]) {
                    int temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                }
            }
        }
        return arr;
    }

    // SelectionSort average time complexity as O(n2)
    public static int[] selectionSort(int[] arr) {
        for (int i = 0; i < arr.length-1; i++) {
            int min = i;
            for (int j=i+1; j<arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            if (arr[i] != arr[min]) {
                int temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
        }
        return arr;
    }

    public static int[] mergeSort(int[] arr) {
        int length = arr.length;
        if (length == 1) {
            return arr;
        }
        return doMergeSort(arr, 0, arr.length-1);
    }

    private static int[] doMergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = l+(r-l)/2;
            doMergeSort(arr, l, m);
            doMergeSort(arr, m+1, r);
            merge(arr, l, m, r);
        }
        return arr;
    }

    private static void merge(int[] arr, int l, int m, int r) {
        //Calc the size of the array
        int i, j, k;
        int n1 = m - l + 1;
        int n2 = r - m;

        //Create two temp arrays
        int[] left = new int[n1];
        int[] right = new int[n2];
        //Copy the elements into the arrays
        for (i=0; i<n1; i++)
            left[i] = arr[l+i];
        for (j=0; j<n2; j++)
            right[j] = arr[m+1+j];
        //print(left);
        //print(right);
        //Merge the temp arrays back into the previous array
        i = 0;
        j = 0;
        k = l;

        while (i < n1 && j < n2) {
            if (left[i] < right[j]) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }
        //print(arr);
        //System.out.println();
        //Copy the remaining elements into the array
        //Since the individual arrays are already sorted, they can be directly copied
        while (i < n1) {
            arr[k] = left[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = right[j];
            j++;
            k++;
        }
    }


    /**
     * O(log N) basically means time goes up linearly while the n goes up exponentially. So if it takes 1 second to
     * compute 10 elements, it will take 2 seconds to compute 100 elements, 3 seconds to compute 1000 elements, and so on.
     *
     * ​It is O(log n) when we do divide and conquer type of algorithms e.g binary search. Another example
     * is quick sort where each time we divide the array into two parts and each time it takes O(N) time to find
     * pivot element. Hence it  N O(log N)
     * The main function that implements QuickSort()
     *       arr[] --> Array to be sorted,
     *       low  --> Starting index,
     *       high  --> Ending index *
     * @return
     */
    public static int[] quickSort(int[] arr) {
        return qSort(arr, 0, arr.length - 1);
    }

    private static int[] qSort(int[] arr, int low, int high) {
        if (low < high) {
            //pi is the partitioning index, arr[pi] is now at the right place
            int pi = partition(arr, low, high);

            //Recursively  sort the elements before and after partition
            qSort(arr, low, pi-1);
            qSort(arr, pi+1, high);
        }
        return arr;
    }

    /**
     This function takes last element as pivot,
     places the pivot element at its correct
     position in sorted array, and places all
     smaller (smaller than pivot) to left of
     pivot and all greater elements to right
     of pivot
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);//Index of the smallest element
        for (int j=low; j<high; j++) {
            //If the current element is smaller or equal to the pivot
            if (arr[j] <= pivot) {
                i++;

                //Swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        //Swap arr[i+1] and pivot
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

}

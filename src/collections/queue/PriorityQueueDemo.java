package collections.queue;

import java.util.*;

/**
 * PriorityQueueDemo
 *
 * INTERNAL WORKING:
 * - Binary Heap (min-heap by default)
 * - Stored as array
 * - NOT FIFO
 */
public class PriorityQueueDemo {

    public static void main(String[] args) {

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // add(E)
        // Heapify-up
        // O(log n)
        pq.add(30);
        pq.add(10);
        pq.add(20);

        // peek()
        // Returns smallest element
        // O(1)
        pq.peek();

        // poll()
        // Removes root, heapify-down
        // O(log n)
        pq.poll();

        // remove(Object)
        // Searches + heapify
        pq.remove(20);

        // contains()
        pq.contains(10);
    }
}

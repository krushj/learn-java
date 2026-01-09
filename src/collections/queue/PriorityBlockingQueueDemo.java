package collections.queue;

import java.util.concurrent.*;

/**
 * PriorityBlockingQueueDemo
 *
 * INTERNAL:
 * - Thread-safe PriorityQueue
 * - Binary heap
 * - NO FIFO ordering
 */
public class PriorityBlockingQueueDemo {

    public static void main(String[] args) throws Exception {

        BlockingQueue<Integer> queue = new PriorityBlockingQueue<>();

        queue.put(30);
        queue.put(10);
        queue.put(20);

        queue.take(); // returns smallest
    }
}

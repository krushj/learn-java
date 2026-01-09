package collections.queue;

import java.util.concurrent.*;

/**
 * ArrayBlockingQueueDemo
 *
 * INTERNAL:
 * - Fixed-size array
 * - Uses locks
 * - Thread-safe
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) throws Exception {

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);

        // put()
        // Blocks if queue is full
        queue.put(1);
        queue.put(2);

        // take()
        // Blocks if queue is empty
        queue.take();
    }
}

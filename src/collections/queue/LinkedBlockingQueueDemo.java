package collections.queue;

import java.util.concurrent.*;

/**
 * LinkedBlockingQueueDemo
 *
 * INTERNAL:
 * - Linked nodes
 * - Separate locks for put & take
 * - Optional capacity
 */
public class LinkedBlockingQueueDemo {

    public static void main(String[] args) throws Exception {

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        queue.put("A");
        queue.put("B");

        queue.take();
    }
}

package collections.queue;

import java.util.concurrent.*;
import java.util.*;

/**
 * ArrayBlockingQueueDemo
 *
 * Demonstrates ArrayBlockingQueue - a bounded blocking queue backed by array.
 * Used extensively in Producer-Consumer pattern.
 *
 * COMPARISON:
 * +----------------------+-------------+-------------+------------+
 * | BlockingQueue        | Bounded     | Structure   | Ordering   |
 * +----------------------+-------------+-------------+------------+
 * | ArrayBlockingQueue   | Yes (fixed) | Array       | FIFO       |
 * | LinkedBlockingQueue  | Optional    | Linked list | FIFO       |
 * | PriorityBlockingQueue| No          | Heap        | Priority   |
 * | DelayQueue           | No          | Heap        | Delay time |
 * | SynchronousQueue     | 0 capacity  | None        | Direct     |
 * +----------------------+-------------+-------------+------------+
 *
 * WHEN TO USE:
 * - ArrayBlockingQueue: Fixed capacity needed, memory predictable
 * - LinkedBlockingQueue: High throughput, unbounded or large capacity
 * - PriorityBlockingQueue: Need priority ordering in concurrent context
 *
 * INTERNAL WORKING:
 * - Fixed-size circular array
 * - Uses ReentrantLock with two Conditions (notEmpty, notFull)
 * - Head and tail pointers wrap around
 * - Thread-safe with blocking operations
 *
 * STRUCTURE:
 * ┌───────────────────────────────────────────────────┐
 * │ ArrayBlockingQueue (capacity=5)                   │
 * │                                                   │
 * │  ┌────┬────┬────┬────┬────┐                      │
 * │  │ A  │ B  │ C  │    │    │  ← Array             │
 * │  └────┴────┴────┴────┴────┘                      │
 * │    ↑              ↑                              │
 * │   head          tail                             │
 * │                                                   │
 * │  Lock: ReentrantLock                             │
 * │  Conditions: notEmpty, notFull                   │
 * └───────────────────────────────────────────────────┘
 *
 * KEY CHARACTERISTICS:
 * - Bounded (fixed capacity)
 * - FIFO ordering
 * - Thread-safe
 * - Blocking operations (put/take)
 * - Fair ordering option
 */
public class ArrayBlockingQueueDemo {

    public static void main(String[] args) throws Exception {

        // ===== CREATION =====

        System.out.println("===== CREATION =====");

        // Create with capacity (required)
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

        // Create with capacity and fair ordering
        // Fair = threads acquire lock in FIFO order (slower but prevents starvation)
        BlockingQueue<String> fairQueue = new ArrayBlockingQueue<>(5, true);

        // Create with initial elements
        BlockingQueue<String> prefilledQueue = new ArrayBlockingQueue<>(5, false,
                Arrays.asList("A", "B", "C"));

        System.out.println("Created queue with capacity 5");
        System.out.println("Prefilled queue: " + prefilledQueue);

        // ===== ADD OPERATIONS =====

        System.out.println("\n===== ADD OPERATIONS =====");

        /*
         * Four ways to add elements:
         *
         * | Method        | Full Queue Behavior           | Return    |
         * |---------------|-------------------------------|-----------|
         * | add(e)        | Throws IllegalStateException  | boolean   |
         * | offer(e)      | Returns false                 | boolean   |
         * | offer(e,t,u)  | Waits for timeout             | boolean   |
         * | put(e)        | Blocks until space available  | void      |
         */

        // add() - throws exception if full
        queue.add("Item1");
        queue.add("Item2");
        System.out.println("After add(): " + queue);

        // offer() - returns false if full (non-blocking)
        boolean added = queue.offer("Item3");
        System.out.println("offer() returned: " + added);

        // offer() with timeout - waits up to specified time
        added = queue.offer("Item4", 100, TimeUnit.MILLISECONDS);
        System.out.println("offer(timeout) returned: " + added);

        // put() - blocks until space available
        queue.put("Item5");
        System.out.println("After put(): " + queue);

        // Queue is now full (5 items)
        System.out.println("Queue full, size: " + queue.size());

        // offer() on full queue - returns immediately
        added = queue.offer("Item6");
        System.out.println("offer() on full queue: " + added);  // false

        // ===== REMOVE OPERATIONS =====

        System.out.println("\n===== REMOVE OPERATIONS =====");

        /*
         * Four ways to remove elements:
         *
         * | Method        | Empty Queue Behavior          | Return    |
         * |---------------|-------------------------------|-----------|
         * | remove()      | Throws NoSuchElementException | E         |
         * | poll()        | Returns null                  | E         |
         * | poll(t,u)     | Waits for timeout             | E         |
         * | take()        | Blocks until element available| E         |
         */

        // poll() - returns null if empty (non-blocking)
        String item = queue.poll();
        System.out.println("poll() returned: " + item);

        // poll() with timeout
        item = queue.poll(100, TimeUnit.MILLISECONDS);
        System.out.println("poll(timeout) returned: " + item);

        // take() - blocks until element available
        item = queue.take();
        System.out.println("take() returned: " + item);

        // remove() - throws exception if empty
        item = queue.remove();
        System.out.println("remove() returned: " + item);

        System.out.println("After removals: " + queue);

        // ===== EXAMINE OPERATIONS =====

        System.out.println("\n===== EXAMINE OPERATIONS =====");

        /*
         * | Method   | Empty Queue Behavior          |
         * |----------|-------------------------------|
         * | element()| Throws NoSuchElementException |
         * | peek()   | Returns null                  |
         */

        // peek() - view without removing
        String head = queue.peek();
        System.out.println("peek(): " + head);

        // element() - view without removing (throws if empty)
        head = queue.element();
        System.out.println("element(): " + head);

        // ===== BULK OPERATIONS =====

        System.out.println("\n===== BULK OPERATIONS =====");

        // drainTo() - removes all elements and adds to collection
        List<String> drained = new ArrayList<>();
        int count = queue.drainTo(drained);
        System.out.println("Drained " + count + " elements: " + drained);
        System.out.println("Queue after drain: " + queue);

        // Refill for more demos
        queue.addAll(Arrays.asList("X", "Y", "Z"));

        // drainTo() with max elements
        drained.clear();
        count = queue.drainTo(drained, 2);
        System.out.println("Drained max 2: " + drained);

        // ===== CAPACITY & STATE =====

        System.out.println("\n===== CAPACITY & STATE =====");

        // remainingCapacity() - space left
        System.out.println("Remaining capacity: " + queue.remainingCapacity());

        // size() - current elements
        System.out.println("Size: " + queue.size());

        // isEmpty()
        System.out.println("Is empty: " + queue.isEmpty());

        // contains()
        System.out.println("Contains 'Z': " + queue.contains("Z"));

        // ===== PRODUCER-CONSUMER PATTERN =====

        System.out.println("\n===== PRODUCER-CONSUMER PATTERN =====");

        BlockingQueue<Integer> taskQueue = new ArrayBlockingQueue<>(3);

        // Producer thread
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    System.out.println("Producing: " + i);
                    taskQueue.put(i);  // Blocks if full
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        // Consumer thread
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    Integer task = taskQueue.take();  // Blocks if empty
                    System.out.println("Consuming: " + task);
                    Thread.sleep(200);  // Slower consumer
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        // ===== ITERATION =====

        System.out.println("\n===== ITERATION =====");

        BlockingQueue<String> iterQueue = new ArrayBlockingQueue<>(5);
        iterQueue.addAll(Arrays.asList("A", "B", "C"));

        // Iterator (weakly consistent)
        System.out.print("Iterator: ");
        for (String s : iterQueue) {
            System.out.print(s + " ");
        }
        System.out.println();

        // toArray()
        Object[] arr = iterQueue.toArray();
        System.out.println("toArray: " + Arrays.toString(arr));

        System.out.println("\n===== Demo Complete =====");
        System.out.println("See class Javadoc for comparison table and when-to-use guide.");
    }
}

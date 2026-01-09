package collections.queue;

import java.util.*;

/**
 * PriorityQueueDemo
 *
 * Demonstrates PriorityQueue - a priority heap-based queue.
 * Elements are ordered by priority, not FIFO.
 *
 * COMPARISON:
 * +----------------------+----------+------------+---------------+
 * | Queue Type           | Ordering | Thread-safe| Null Allowed  |
 * +----------------------+----------+------------+---------------+
 * | PriorityQueue        | Priority | No         | No            |
 * | LinkedList (Queue)   | FIFO     | No         | Yes           |
 * | ArrayDeque           | FIFO/LIFO| No         | No            |
 * | PriorityBlockingQueue| Priority | Yes        | No            |
 * | LinkedBlockingQueue  | FIFO     | Yes        | No            |
 * +----------------------+----------+------------+---------------+
 *
 * WHEN TO USE:
 * - PriorityQueue: Need elements processed by priority
 * - ArrayDeque: Need fast FIFO queue or LIFO stack
 * - BlockingQueue variants: Multi-threaded producer-consumer
 *
 * INTERNAL WORKING:
 * - Binary Heap (Min-Heap by default)
 * - Stored as array (complete binary tree property)
 * - Parent at index i: children at 2i+1 and 2i+2
 * - Child at index i: parent at (i-1)/2
 * - NOT thread-safe (use PriorityBlockingQueue)
 *
 * HEAP STRUCTURE:
 * ┌───────────────────────────────────────────────────────────────────┐
 * │ PriorityQueue (Min-Heap)                                          │
 * │                                                                   │
 * │  Logical Tree View:           Array Representation:              │
 * │         10                    [10, 20, 15, 30, 40, 25]           │
 * │        /  \                     0   1   2   3   4   5            │
 * │       20   15                                                     │
 * │      / \   /                  Parent of i: (i-1)/2               │
 * │     30 40 25                  Left child: 2i+1                   │
 * │                               Right child: 2i+2                  │
 * │                                                                   │
 * │  Min element always at root (index 0)                            │
 * └───────────────────────────────────────────────────────────────────┘
 *
 * TIME COMPLEXITY:
 * - offer/add: O(log n) - heapify up
 * - poll/remove: O(log n) - heapify down
 * - peek: O(1) - just return root
 * - contains: O(n) - linear search
 * - remove(Object): O(n) - search + heapify
 */
public class PriorityQueueDemo {

    public static void main(String[] args) {

        // ===== CREATION =====

        System.out.println("===== CREATION =====");

        // Default: Min-Heap with natural ordering
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // With initial capacity
        PriorityQueue<Integer> withCapacity = new PriorityQueue<>(20);

        // Max-Heap using Comparator
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

        // From collection
        PriorityQueue<Integer> fromList = new PriorityQueue<>(
            Arrays.asList(30, 10, 20, 40, 50)
        );
        System.out.println("From list [30,10,20,40,50]: " + fromList);
        System.out.println("Peek (min): " + fromList.peek());

        // With custom comparator
        PriorityQueue<String> byLength = new PriorityQueue<>(
            Comparator.comparingInt(String::length)
        );

        // ===== ADD OPERATIONS =====

        System.out.println("\n===== ADD OPERATIONS =====");

        // offer() - preferred, returns false if capacity restricted
        // Time: O(log n)
        minHeap.offer(50);
        minHeap.offer(20);
        minHeap.offer(40);
        minHeap.offer(10);
        minHeap.offer(30);

        System.out.println("After offers: " + minHeap);
        System.out.println("Note: Array may not be sorted, but heap property maintained");
        System.out.println("Peek (min): " + minHeap.peek());

        // add() - same as offer() for unbounded queue
        // Throws IllegalStateException if capacity restricted
        minHeap.add(5);
        System.out.println("After add(5), peek: " + minHeap.peek());

        // addAll()
        minHeap.addAll(Arrays.asList(100, 200));
        System.out.println("After addAll: " + minHeap);

        // ===== REMOVE OPERATIONS =====

        System.out.println("\n===== REMOVE OPERATIONS =====");

        // poll() - removes and returns head (smallest)
        // Time: O(log n)
        System.out.println("Polling elements in priority order:");
        PriorityQueue<Integer> pollDemo = new PriorityQueue<>(
            Arrays.asList(50, 20, 40, 10, 30)
        );

        while (!pollDemo.isEmpty()) {
            System.out.print(pollDemo.poll() + " ");  // 10 20 30 40 50
        }
        System.out.println();

        // remove() - same as poll() but throws if empty
        minHeap = new PriorityQueue<>(Arrays.asList(30, 10, 20));
        System.out.println("remove(): " + minHeap.remove());

        // remove(Object) - removes specific element
        // Time: O(n) - must search
        boolean removed = minHeap.remove(30);
        System.out.println("remove(30): " + removed);

        // ===== EXAMINE OPERATIONS =====

        System.out.println("\n===== EXAMINE OPERATIONS =====");

        PriorityQueue<Integer> examineQ = new PriorityQueue<>(
            Arrays.asList(50, 20, 40, 10, 30)
        );

        // peek() - returns head without removing
        // Time: O(1)
        System.out.println("peek(): " + examineQ.peek());  // 10
        System.out.println("Queue unchanged: " + examineQ);

        // element() - same but throws if empty
        System.out.println("element(): " + examineQ.element());

        // ===== MAX HEAP =====

        System.out.println("\n===== MAX HEAP =====");

        PriorityQueue<Integer> maxQ = new PriorityQueue<>(Comparator.reverseOrder());
        maxQ.addAll(Arrays.asList(50, 20, 40, 10, 30));

        System.out.println("Max heap peek: " + maxQ.peek());  // 50

        System.out.println("Polling from max heap:");
        while (!maxQ.isEmpty()) {
            System.out.print(maxQ.poll() + " ");  // 50 40 30 20 10
        }
        System.out.println();

        // ===== CUSTOM OBJECTS =====

        System.out.println("\n===== CUSTOM OBJECTS =====");

        // Task with priority
        PriorityQueue<Task> taskQueue = new PriorityQueue<>();

        taskQueue.offer(new Task("Low priority task", 3));
        taskQueue.offer(new Task("High priority task", 1));
        taskQueue.offer(new Task("Medium priority task", 2));
        taskQueue.offer(new Task("Critical task", 0));

        System.out.println("Processing tasks by priority:");
        while (!taskQueue.isEmpty()) {
            Task task = taskQueue.poll();
            System.out.println("  [" + task.priority + "] " + task.name);
        }

        // With external comparator
        PriorityQueue<Task> byNameLength = new PriorityQueue<>(
            Comparator.comparingInt(t -> t.name.length())
        );

        byNameLength.offer(new Task("Short", 1));
        byNameLength.offer(new Task("Very long task name", 2));
        byNameLength.offer(new Task("Medium task", 3));

        System.out.println("\nBy name length:");
        while (!byNameLength.isEmpty()) {
            System.out.println("  " + byNameLength.poll().name);
        }

        // ===== K LARGEST/SMALLEST ELEMENTS =====

        System.out.println("\n===== TOP K ELEMENTS =====");

        int[] numbers = {7, 10, 4, 3, 20, 15, 8, 1, 9, 2};
        int k = 3;

        // Find K largest using min-heap of size K
        PriorityQueue<Integer> kLargest = new PriorityQueue<>(k);
        for (int num : numbers) {
            kLargest.offer(num);
            if (kLargest.size() > k) {
                kLargest.poll();  // Remove smallest
            }
        }
        System.out.println("Top " + k + " largest: " + kLargest);

        // Find K smallest using max-heap of size K
        PriorityQueue<Integer> kSmallest = new PriorityQueue<>(k, Comparator.reverseOrder());
        for (int num : numbers) {
            kSmallest.offer(num);
            if (kSmallest.size() > k) {
                kSmallest.poll();  // Remove largest
            }
        }
        System.out.println("Top " + k + " smallest: " + kSmallest);

        // ===== ITERATION =====

        System.out.println("\n===== ITERATION =====");

        PriorityQueue<Integer> iterQ = new PriorityQueue<>(
            Arrays.asList(50, 20, 40, 10, 30)
        );

        // WARNING: Iterator does NOT return elements in priority order!
        System.out.println("Iterator (NOT sorted): ");
        for (Integer i : iterQ) {
            System.out.print(i + " ");
        }
        System.out.println();

        // For sorted iteration, use poll() in loop (destructive)
        // Or copy to list and sort
        List<Integer> sorted = new ArrayList<>(iterQ);
        Collections.sort(sorted);
        System.out.println("Sorted copy: " + sorted);

        // ===== SIZE & STATE =====

        System.out.println("\n===== SIZE & STATE =====");

        PriorityQueue<Integer> stateQ = new PriorityQueue<>(Arrays.asList(1, 2, 3));

        System.out.println("Size: " + stateQ.size());
        System.out.println("isEmpty: " + stateQ.isEmpty());
        System.out.println("contains(2): " + stateQ.contains(2));

        // toArray()
        Object[] arr = stateQ.toArray();
        System.out.println("toArray: " + Arrays.toString(arr));

        // clear()
        stateQ.clear();
        System.out.println("After clear, isEmpty: " + stateQ.isEmpty());

        // ===== NULL HANDLING =====

        System.out.println("\n===== NULL HANDLING =====");

        System.out.println("PriorityQueue does NOT allow null elements");
        System.out.println("Adding null throws NullPointerException");
        // pq.offer(null);  // NullPointerException!

        System.out.println("\n===== Demo Complete =====");
        System.out.println("See class Javadoc for comparison table and when-to-use guide.");
    }
}

/**
 * Task class implementing Comparable for priority ordering
 */
class Task implements Comparable<Task> {
    String name;
    int priority;  // Lower = higher priority

    Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString() {
        return name + "(p=" + priority + ")";
    }
}

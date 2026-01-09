package collections.list;

import java.util.*;

/**
 * QueueDemo
 *
 * QUEUE PRINCIPLE:
 * - FIFO (First In First Out)
 * - Head = next element to be removed
 *
 * METHOD GROUPS:
 * - add / remove → throws exception
 * - offer / poll → returns special value
 */
public class QueueDemo {

    public static void main(String[] args) {

        Queue<Integer> queue = new LinkedList<>();

        // add(E)
        // Inserts element, throws exception if full
        queue.add(10);

        // offer(E)
        // Inserts element, returns false if full
        queue.offer(20);

        // peek()
        // Returns head without removing (null if empty)
        queue.peek();

        // element()
        // Same as peek but throws exception if empty
        queue.element();

        // poll()
        // Removes and returns head (null if empty)
        queue.poll();

        // remove()
        // Removes head, throws exception if empty
        queue.remove();
    }
}

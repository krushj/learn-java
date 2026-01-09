package collections.queue;

import java.util.*;

/**
 * ArrayDequeDemo
 *
 * INTERNAL WORKING:
 * - Resizable circular array
 * - Faster than Stack & LinkedList
 * - NO null elements allowed
 */
public class ArrayDequeDemo {

    public static void main(String[] args) {

        ArrayDeque<String> deque = new ArrayDeque<>();

        // add()
        deque.add("A");

        // addFirst(), addLast()
        deque.addFirst("FIRST");
        deque.addLast("LAST");

        // removeFirst(), removeLast()
        deque.removeFirst();
        deque.removeLast();

        // peek()
        deque.peek();

        // poll()
        deque.poll();
    }
}

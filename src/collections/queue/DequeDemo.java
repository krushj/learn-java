package collections.queue;

import java.util.*;

/**
 * DequeDemo
 *
 * DEQUE:
 * - Insert & remove from both ends
 * - Can act as Queue or Stack
 */
public class DequeDemo {

    public static void main(String[] args) {

        Deque<Integer> deque = new ArrayDeque<>();

        // addFirst(), addLast()
        deque.addFirst(10);
        deque.addLast(20);

        // offerFirst(), offerLast()
        deque.offerFirst(5);
        deque.offerLast(25);

        // peekFirst(), peekLast()
        deque.peekFirst();
        deque.peekLast();

        // pollFirst(), pollLast()
        deque.pollFirst();
        deque.pollLast();

        // push() → stack behavior
        deque.push(100);

        // pop() → stack behavior
        deque.pop();
    }
}

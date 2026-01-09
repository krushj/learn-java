package collections.list;

import java.util.*;

/**
 * LinkedListDemo
 *
 * INTERNAL WORKING:
 * - Doubly linked list
 * - Each node = prev + item + next
 * - No shifting, no resizing
 * - Slower random access, faster insert/delete
 */
public class LinkedListDemo {

    public static void main(String[] args) {

        LinkedList<String> list = new LinkedList<>();

        // ===== ADD METHODS =====

        // add(E) → adds at tail, O(1)
        list.add("A");
        list.add("B");

        // addFirst(E) → adjusts head pointer, O(1)
        list.addFirst("FIRST");

        // addLast(E) → adjusts tail pointer, O(1)
        list.addLast("LAST");

        // add(index, E) → traversal + insert, O(n)
        list.add(2, "MIDDLE");

        // addAll(Collection) → sequential node creation, O(n)
        list.addAll(List.of("C", "D"));

        // ===== ACCESS METHODS =====

        // getFirst(), getLast() → direct head/tail, O(1)
        list.getFirst();
        list.getLast();

        // get(index) → traversal from head/tail, O(n)
        list.get(2);

        // ===== REMOVE METHODS =====

        // remove() → removes head, O(1)
        list.remove();

        // removeFirst(), removeLast() → O(1)
        list.removeFirst();
        list.removeLast();

        // remove(Object) → traversal + unlink, O(n)
        list.remove("B");

        // ===== QUEUE / DEQUE =====

        // peek() → returns head without removal, O(1)
        list.peek();

        // poll() → removes head, O(1)
        list.poll();

        // offer(E) → same as addLast, O(1)
        list.offer("X");

        // ===== STACK BEHAVIOR =====

        // push(E) → addFirst, O(1)
        list.push("STACK");

        // pop() → removeFirst, O(1)
        list.pop();

        // ===== ITERATION =====

        // iterator() → fail-fast
        Iterator<String> it = list.iterator();
        while (it.hasNext()) it.next();

        // descendingIterator() → reverse traversal
        Iterator<String> dit = list.descendingIterator();
        while (dit.hasNext()) dit.next();
    }
}

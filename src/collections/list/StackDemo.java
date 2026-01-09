package collections.list;

import java.util.Stack;

/**
 * StackDemo
 *
 * LEGACY
 * - Extends Vector
 * - LIFO
 */
public class StackDemo {

    public static void main(String[] args) {

        Stack<String> stack = new Stack<>();

        // push(E) → add at top, O(1)
        stack.push("A");
        stack.push("B");

        // peek() → view top, O(1)
        stack.peek();

        // pop() → remove top, O(1)
        stack.pop();

        // search(Object) → 1-based index from top, O(n)
        stack.search("A");

        // empty() → check empty
        stack.empty();
    }
}

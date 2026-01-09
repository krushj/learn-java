package collections.list;

import java.util.*;

/**
 * LinkedListDemo
 *
 * Demonstrates LinkedList - doubly-linked list implementation.
 * Implements both List and Deque interfaces.
 *
 * COMPARISON: ArrayList vs LinkedList
 * +-------------------+-----------------+-----------------+
 * | Operation         | ArrayList       | LinkedList      |
 * +-------------------+-----------------+-----------------+
 * | get(index)        | O(1)            | O(n)            |
 * | add(end)          | O(1) amortized  | O(1)            |
 * | add(index)        | O(n)            | O(n)            |
 * | add(0) - front    | O(n)            | O(1)            |
 * | remove(index)     | O(n)            | O(n)            |
 * | remove(0) - front | O(n)            | O(1)            |
 * | Memory            | Less (array)    | More (nodes)    |
 * +-------------------+-----------------+-----------------+
 *
 * WHEN TO USE:
 * - ArrayList: Random access frequent, modifications mostly at end
 * - LinkedList: Frequent add/remove at beginning, need Queue/Deque
 *
 * INTERNAL WORKING:
 * - Doubly-linked list (each node has prev and next pointers)
 * - No random access (must traverse from head or tail)
 * - Efficient insertions/deletions at both ends
 * - Implements List, Deque, Queue interfaces
 *
 * STRUCTURE:
 * ┌───────────────────────────────────────────────────────────────────┐
 * │ LinkedList                                                        │
 * │                                                                   │
 * │  head                                              tail           │
 * │   ↓                                                 ↓             │
 * │  ┌──────────┐   ┌──────────┐   ┌──────────┐   ┌──────────┐      │
 * │  │   Node   │ ⇄ │   Node   │ ⇄ │   Node   │ ⇄ │   Node   │      │
 * │  │ prev=null│   │          │   │          │   │ next=null│      │
 * │  │ item="A" │   │ item="B" │   │ item="C" │   │ item="D" │      │
 * │  │ next ────│─→ │ next ────│─→ │ next ────│─→ │          │      │
 * │  └──────────┘   └──────────┘   └──────────┘   └──────────┘      │
 * │       ↑              ↑              ↑              ↑             │
 * │       └──────────────┴──────────────┴──────────────┘             │
 * │                       (prev pointers)                            │
 * └───────────────────────────────────────────────────────────────────┘
 *
 * TIME COMPLEXITY:
 * - get(index): O(n) - must traverse (optimizes: starts from head or tail)
 * - add(index, e): O(n) search + O(1) insert
 * - addFirst/addLast: O(1)
 * - removeFirst/removeLast: O(1)
 * - contains: O(n)
 *
 * USE CASES:
 * - Frequent insertions/deletions at ends
 * - Implementing Queue/Deque
 * - When you don't need random access
 */
public class LinkedListDemo {

    public static void main(String[] args) {

        // ===== CREATION =====

        System.out.println("===== CREATION =====");

        // Empty list
        LinkedList<String> list = new LinkedList<>();

        // From collection
        LinkedList<String> fromList = new LinkedList<>(
            Arrays.asList("A", "B", "C")
        );
        System.out.println("From collection: " + fromList);

        // ===== LIST OPERATIONS =====

        System.out.println("\n===== LIST OPERATIONS =====");

        // add(E) - adds at end
        // Time: O(1)
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        System.out.println("After add(): " + list);

        // add(index, E) - adds at position
        // Time: O(n) for traversal + O(1) for insert
        list.add(1, "Apricot");
        System.out.println("After add(1, 'Apricot'): " + list);

        // get(index) - access by position
        // Time: O(n) - traverses from head or tail (whichever is closer)
        String item = list.get(2);
        System.out.println("get(2): " + item);

        // set(index, E) - replace element
        // Time: O(n)
        list.set(0, "Avocado");
        System.out.println("After set(0, 'Avocado'): " + list);

        // remove(index) - remove by position
        // Time: O(n)
        String removed = list.remove(1);
        System.out.println("remove(1) returned: " + removed);

        // remove(Object) - remove first occurrence
        // Time: O(n)
        boolean success = list.remove("Cherry");
        System.out.println("remove('Cherry'): " + success);
        System.out.println("After removes: " + list);

        // indexOf / lastIndexOf
        list.addAll(Arrays.asList("Banana", "Avocado", "Banana"));
        System.out.println("List: " + list);
        System.out.println("indexOf('Banana'): " + list.indexOf("Banana"));
        System.out.println("lastIndexOf('Banana'): " + list.lastIndexOf("Banana"));

        // ===== DEQUE OPERATIONS (Double-Ended Queue) =====

        System.out.println("\n===== DEQUE OPERATIONS =====");

        LinkedList<String> deque = new LinkedList<>();

        // Add to front - O(1)
        deque.addFirst("First");
        deque.offerFirst("NewFirst");
        deque.push("Pushed");  // Same as addFirst

        // Add to back - O(1)
        deque.addLast("Last");
        deque.offerLast("NewLast");

        System.out.println("Deque: " + deque);

        // Examine front - O(1)
        System.out.println("getFirst(): " + deque.getFirst());
        System.out.println("peekFirst(): " + deque.peekFirst());
        System.out.println("peek(): " + deque.peek());

        // Examine back - O(1)
        System.out.println("getLast(): " + deque.getLast());
        System.out.println("peekLast(): " + deque.peekLast());

        // Remove from front - O(1)
        System.out.println("removeFirst(): " + deque.removeFirst());
        System.out.println("pollFirst(): " + deque.pollFirst());
        System.out.println("pop(): " + deque.pop());  // Same as removeFirst

        // Remove from back - O(1)
        System.out.println("removeLast(): " + deque.removeLast());

        System.out.println("After removals: " + deque);

        // ===== QUEUE OPERATIONS =====

        System.out.println("\n===== QUEUE OPERATIONS (FIFO) =====");

        LinkedList<String> queue = new LinkedList<>();

        // Enqueue (add to tail)
        queue.offer("Task1");
        queue.offer("Task2");
        queue.offer("Task3");
        System.out.println("Queue: " + queue);

        // Dequeue (remove from head)
        System.out.println("poll(): " + queue.poll());
        System.out.println("poll(): " + queue.poll());
        System.out.println("Queue after polls: " + queue);

        // Peek (view head without removing)
        System.out.println("peek(): " + queue.peek());

        // ===== STACK OPERATIONS =====

        System.out.println("\n===== STACK OPERATIONS (LIFO) =====");

        LinkedList<String> stack = new LinkedList<>();

        // Push (add to front)
        stack.push("Bottom");
        stack.push("Middle");
        stack.push("Top");
        System.out.println("Stack: " + stack);

        // Pop (remove from front)
        System.out.println("pop(): " + stack.pop());
        System.out.println("pop(): " + stack.pop());
        System.out.println("Stack after pops: " + stack);

        // Peek
        System.out.println("peek(): " + stack.peek());

        // ===== SPECIAL METHODS =====

        System.out.println("\n===== SPECIAL METHODS =====");

        LinkedList<Integer> numbers = new LinkedList<>(
            Arrays.asList(1, 2, 3, 2, 4, 2, 5)
        );
        System.out.println("Numbers: " + numbers);

        // removeFirstOccurrence
        numbers.removeFirstOccurrence(2);
        System.out.println("After removeFirstOccurrence(2): " + numbers);

        // removeLastOccurrence
        numbers.removeLastOccurrence(2);
        System.out.println("After removeLastOccurrence(2): " + numbers);

        // ===== ITERATION =====

        System.out.println("\n===== ITERATION =====");

        LinkedList<String> iterList = new LinkedList<>(
            Arrays.asList("A", "B", "C", "D")
        );

        // Forward iteration
        System.out.print("Forward: ");
        for (String s : iterList) {
            System.out.print(s + " ");
        }
        System.out.println();

        // Reverse iteration using descendingIterator()
        System.out.print("Reverse: ");
        Iterator<String> descIter = iterList.descendingIterator();
        while (descIter.hasNext()) {
            System.out.print(descIter.next() + " ");
        }
        System.out.println();

        // ListIterator - bidirectional
        System.out.print("ListIterator bidirectional: ");
        ListIterator<String> listIter = iterList.listIterator();
        while (listIter.hasNext()) {
            System.out.print(listIter.next() + " ");
        }
        System.out.print("| ");
        while (listIter.hasPrevious()) {
            System.out.print(listIter.previous() + " ");
        }
        System.out.println();

        // ===== SIZE & STATE =====

        System.out.println("\n===== SIZE & STATE =====");

        System.out.println("Size: " + iterList.size());
        System.out.println("isEmpty: " + iterList.isEmpty());
        System.out.println("contains('B'): " + iterList.contains("B"));

        // clear()
        LinkedList<String> temp = new LinkedList<>(iterList);
        temp.clear();
        System.out.println("After clear, isEmpty: " + temp.isEmpty());

        // ===== CONVERSION =====

        System.out.println("\n===== CONVERSION =====");

        // To array
        Object[] objArr = iterList.toArray();
        String[] strArr = iterList.toArray(new String[0]);
        System.out.println("toArray: " + Arrays.toString(strArr));

        // To ArrayList (for random access)
        ArrayList<String> arrayList = new ArrayList<>(iterList);
        System.out.println("To ArrayList: " + arrayList);

        System.out.println("\n===== Demo Complete =====");
        System.out.println("See class Javadoc for comparison table and when-to-use guide.");
    }
}

package collections.list;

import java.util.*;

/**
 * ArrayListDemo
 *
 * Demonstrates almost ALL important ArrayList methods,
 * including inherited Collection & List methods.
 *
 * INTERNAL WORKING:
 * - Backed by Object[] array
 * - Default capacity = 10
 * - Grows by 1.5x when full
 * - Random access is fast
 * - Insert/Delete in middle is costly
 */
public class ArrayListDemo {

    public static void main(String[] args) {

        // ===== CREATION =====
        // Creates empty ArrayList with default capacity
        ArrayList<Integer> list = new ArrayList<>();

        // Creates ArrayList with initial capacity
        ArrayList<Integer> capacityList = new ArrayList<>(20);

        // Creates ArrayList from another collection
        ArrayList<Integer> copyList = new ArrayList<>(List.of(1, 2, 3));

        // ===== ADD METHODS =====

        // add(E e)
        // Adds element at end
        // Internal: elementData[size++] = e
        // Time: O(1) amortized
        list.add(10);
        list.add(20);
        list.add(30);

        // add(int index, E e)
        // Shifts elements using System.arraycopy
        // Time: O(n)
        list.add(1, 15);

        // addAll(Collection c)
        // Copies all elements into internal array
        // Time: O(n)
        list.addAll(List.of(40, 50));

        // addAll(int index, Collection c)
        // Shifts + bulk copy
        // Time: O(n)
        list.addAll(2, List.of(16, 17));

        // ===== ACCESS METHODS =====

        // get(int index)
        // Direct array access
        // Time: O(1)
        list.get(0);

        // set(int index, E e)
        // Replaces element at index
        // Time: O(1)
        list.set(0, 5);

        // indexOf(Object o)
        // Linear search using equals()
        // Time: O(n)
        list.indexOf(20);

        // lastIndexOf(Object o)
        // Searches from end
        // Time: O(n)
        list.lastIndexOf(20);

        // contains(Object o)
        // Uses equals() for comparison
        // Time: O(n)
        list.contains(30);

        // ===== REMOVE METHODS =====

        // remove(int index)
        // Shifts elements left
        // Time: O(n)
        list.remove(1);

        // remove(Object o)
        // Removes first matching element
        // Time: O(n)
        list.remove(Integer.valueOf(30));

        // removeIf(Predicate)
        // Uses iterator internally
        // Time: O(n)
        list.removeIf(x -> x > 40);

        // clear()
        // Sets all array slots to null (GC friendly)
        // Time: O(n)
        list.clear();

        // ===== SIZE / STATE =====

        // size()
        // Returns internal size field
        // Time: O(1)
        list.size();

        // isEmpty()
        // size == 0 check
        // Time: O(1)
        list.isEmpty();

        // ===== BULK OPERATIONS =====

        list.addAll(List.of(1, 2, 3, 4, 5));

        // retainAll(Collection c)
        // Keeps only matching elements (intersection)
        // Time: O(n)
        list.retainAll(List.of(2, 4));

        // removeAll(Collection c)
        // Removes all matching elements
        // Time: O(n)
        list.removeAll(List.of(4));

        // ===== ITERATION =====

        // iterator()
        // Fail-fast iterator (throws ConcurrentModificationException)
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }

        // listIterator()
        // Supports bidirectional traversal
        ListIterator<Integer> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            listIterator.next();
        }

        // forEach()
        // Uses internal iterator
        list.forEach(System.out::println);

        // ===== ARRAY CONVERSION =====

        // toArray()
        // Creates new Object[]
        Object[] objArray = list.toArray();

        // toArray(T[] a)
        // Creates typed array
        Integer[] intArray = list.toArray(new Integer[0]);

        // ===== SUBLIST =====

        // subList(from, to)
        // Returns VIEW backed by original list
        // Structural changes affect parent list
        List<Integer> subList = list.subList(0, list.size());

        // ===== SORTING =====

        // sort(Comparator)
        // Uses TimSort (merge + insertion)
        // Time: O(n log n)
        list.sort(Comparator.naturalOrder());

        list.sort(Comparator.reverseOrder());

        // ===== EQUALITY =====

        // equals(Object o)
        // Order + elements must match
        list.equals(subList);

        // hashCode()
        // Based on elements
        list.hashCode();

        // ===== CLONING (SHALLOW COPY) =====

        // clone()
        // Creates shallow copy
        // Elements are NOT cloned
        ArrayList<Integer> cloned = (ArrayList<Integer>) list.clone();
    }
}

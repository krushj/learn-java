package collections.set;

import java.util.TreeSet;

/**
 * TreeSetDemo
 *
 * INTERNAL:
 * - Red-Black Tree
 * - Sorted order
 */
public class TreeSetDemo {

    public static void main(String[] args) {

        TreeSet<Integer> set = new TreeSet<>();

        // add(E) → O(log n)
        set.add(30);
        set.add(10);
        set.add(20);

        // first(), last()
        set.first();
        set.last();

        // higher(), lower()
        set.higher(10);
        set.lower(30);

        // subset views
        set.headSet(20);
        set.tailSet(20);
    }
}

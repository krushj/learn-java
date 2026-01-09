package collections.set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * HashSetDemo
 *
 * INTERNAL:
 * - Backed by HashMap
 * - Elements stored as keys
 * - No order, no duplicates
 */
public class HashSetDemo {

    public static void main(String[] args) {

        HashSet<String> set = new HashSet<>();

        // add(E) → hashCode + equals, O(1) avg
        set.add("A");
        set.add("B");
        set.add("A"); // ignored

        // contains(Object) → O(1)
        set.contains("B");

        // remove(Object) → O(1)
        set.remove("A");

        // addAll(Collection) → O(n)
        set.addAll(Set.of("X", "Y"));

        // retainAll(Collection) → intersection
        set.retainAll(Set.of("X"));

        // removeAll(Collection)
        set.removeAll(Set.of("X"));

        // iterator() → fail-fast
        Iterator<String> it = set.iterator();
        while (it.hasNext()) it.next();
    }
}

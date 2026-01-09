package collections.set;

import java.util.LinkedHashSet;

/**
 * LinkedHashSetDemo
 *
 * INTERNAL:
 * - HashSet + Doubly Linked List
 * - Maintains insertion order
 */
public class LinkedHashSetDemo {

    public static void main(String[] args) {

        LinkedHashSet<String> set = new LinkedHashSet<>();

        set.add("Laptop");
        set.add("Mobile");
        set.add("Tablet");
        set.add("Mobile"); // ignored

        // Order preserved
        set.forEach(System.out::println);
    }
}

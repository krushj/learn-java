package collections.map;

import java.util.*;

/**
 * LinkedHashMapDemo
 *
 * INTERNAL:
 * - HashMap + Doubly Linked List
 * - Maintains insertion order
 * - Can maintain access order (LRU cache)
 */
public class LinkedHashMapDemo {

    public static void main(String[] args) {

        LinkedHashMap<Integer, String> map =
                new LinkedHashMap<>(16, 0.75f, true); // accessOrder = true

        map.put(1, "A");
        map.put(2, "B");
        map.put(3, "C");

        // Access changes order
        map.get(1);

        // Iteration follows access order
        map.forEach((k, v) -> System.out.println(k + " " + v));
    }
}

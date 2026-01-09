package collections.map;

import java.util.*;

/**
 * TreeMapDemo
 *
 * INTERNAL:
 * - Red-Black Tree
 * - Sorted by key
 * - NavigableMap
 */
public class TreeMapDemo {

    public static void main(String[] args) {

        TreeMap<Integer, String> map = new TreeMap<>();

        // put() → O(log n)
        map.put(3, "C");
        map.put(1, "A");
        map.put(2, "B");

        // firstKey(), lastKey()
        map.firstKey();
        map.lastKey();

        // higherKey(), lowerKey()
        map.higherKey(1);
        map.lowerKey(3);

        // subMap views
        map.headMap(2);
        map.tailMap(2);

        // entrySet()
        map.forEach((k, v) -> System.out.println(k + " " + v));
    }
}

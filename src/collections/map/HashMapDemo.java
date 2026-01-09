package collections.map;

import java.util.*;

/**
 * HashMapDemo
 *
 * INTERNAL WORKING:
 * - Array of buckets
 * - Each bucket = LinkedList or Red-Black Tree (Java 8+)
 * - Uses hashCode() → index
 * - equals() → key comparison
 * - Load factor = 0.75
 * - NOT thread-safe
 */
public class HashMapDemo {

    public static void main(String[] args) {

        HashMap<Integer, String> map = new HashMap<>();

        // put(K, V)
        // Avg O(1), Worst O(log n)
        map.put(1, "A");
        map.put(2, "B");

        // putIfAbsent()
        map.putIfAbsent(2, "C");

        // get(K)
        map.get(1);

        // getOrDefault()
        map.getOrDefault(3, "DEFAULT");

        // containsKey(), containsValue()
        map.containsKey(2);
        map.containsValue("A");

        // remove(K)
        map.remove(1);

        // replace(K, V)
        map.replace(2, "NEW");

        // compute()
        map.compute(2, (k, v) -> v + "_UPDATED");

        // keySet()
        Set<Integer> keys = map.keySet();

        // values()
        Collection<String> values = map.values();

        // entrySet()
        for (Map.Entry<Integer, String> e : map.entrySet()) {
            e.getKey();
            e.getValue();
        }

        // clear()
        map.clear();
    }
}

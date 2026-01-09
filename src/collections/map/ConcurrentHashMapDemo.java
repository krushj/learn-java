package collections.map;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMapDemo
 *
 * INTERNAL:
 * - Segment locking (Java 7)
 * - CAS + bucket locking (Java 8+)
 * - Thread-safe & high performance
 * - No null keys/values
 */
public class ConcurrentHashMapDemo {

    public static void main(String[] args) {

        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

        map.put(1, "A");
        map.putIfAbsent(2, "B");

        map.get(1);

        // Atomic operations
        map.compute(1, (k, v) -> v + "_UPDATED");

        map.forEach((k, v) -> System.out.println(k + " " + v));
    }
}

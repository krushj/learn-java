package collections.map;

import java.util.Hashtable;
import java.util.Enumeration;

/**
 * HashtableDemo
 *
 * LEGACY (Java 1.0)
 * - Thread-safe (synchronized)
 * - No null keys or values
 * - Slower than ConcurrentHashMap
 */
public class HashtableDemo {

    public static void main(String[] args) {

        Hashtable<Integer, String> table = new Hashtable<>();

        table.put(1, "A");
        table.put(2, "B");

        table.get(1);
        table.containsKey(2);
        table.contains("A"); // legacy containsValue

        table.remove(1);

        // Enumeration (legacy cursor)
        Enumeration<Integer> e = table.keys();
        while (e.hasMoreElements()) {
            e.nextElement();
        }
    }
}

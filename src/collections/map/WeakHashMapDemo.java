package collections.map;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * WeakHashMapDemo
 *
 * INTERNAL WORKING:
 * - Keys are stored as WEAK REFERENCES
 * - Values are stored as STRONG REFERENCES
 * - When a key is no longer strongly referenced elsewhere,
 *   it becomes eligible for Garbage Collection
 * - Once GC runs, the corresponding entry is automatically removed
 *
 * USE CASES:
 * - Caching
 * - Metadata storage
 * - Avoiding memory leaks
 *
 * IMPORTANT:
 * - Not thread-safe
 * - Allows null keys and values
 */
public class WeakHashMapDemo {

    public static void main(String[] args) throws InterruptedException {

        /*
         * Normal HashMap:
         * - Strong reference to key
         * - Entry NOT removed even if key reference is lost
         *
         * WeakHashMap:
         * - Weak reference to key
         * - Entry removed automatically after GC
         */

        // Creating WeakHashMap
        Map<Key, String> weakMap = new WeakHashMap<>();

        // Creating key object
        Key key1 = new Key("user-1");

        // put(K, V)
        // Stores key as weak reference
        weakMap.put(key1, "User Data");

        System.out.println("Before GC: " + weakMap);

        // Removing strong reference to key
        key1 = null;

        // Requesting Garbage Collection
        System.gc();

        // Giving GC some time (not guaranteed, but usually works for demo)
        Thread.sleep(1000);

        /*
         * After GC:
         * - Key object is collected
         * - WeakHashMap automatically removes entry
         */
        System.out.println("After GC: " + weakMap);

        // ===== OTHER METHODS =====

        // size()
        weakMap.size();

        // isEmpty()
        weakMap.isEmpty();

        // containsKey()
        weakMap.containsKey(new Key("user-1"));

        // clear()
        weakMap.clear();
    }

    /**
     * Custom Key class
     *
     * IMPORTANT:
     * - hashCode() and equals() must be implemented correctly
     * - Otherwise map behavior will be incorrect
     */
    static class Key {

        String id;

        Key(String id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Key)) return false;
            Key other = (Key) obj;
            return id.equals(other.id);
        }

        @Override
        public String toString() {
            return id;
        }
    }
}

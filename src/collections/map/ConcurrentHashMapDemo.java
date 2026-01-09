package collections.map;

import java.util.concurrent.*;
import java.util.*;

/**
 * ConcurrentHashMapDemo
 *
 * Demonstrates ConcurrentHashMap - thread-safe, highly concurrent map.
 * Best choice for concurrent access in multi-threaded applications.
 *
 * COMPARISON:
 * +------------------------+------------------+------------------+
 * | Approach               | Pros             | Cons             |
 * +------------------------+------------------+------------------+
 * | ConcurrentHashMap      | High concurrency | No null keys/vals|
 * | synchronized HashMap   | Simple           | Low concurrency  |
 * | Collections.synchMap() | Simple           | Full locking     |
 * | Hashtable (legacy)     | Thread-safe      | Full locking     |
 * +------------------------+------------------+------------------+
 *
 * WHEN TO USE:
 * - ConcurrentHashMap: ALWAYS for concurrent access (best performance)
 * - synchronized HashMap: Never (use ConcurrentHashMap instead)
 *
 * INTERNAL WORKING (Java 8+):
 * - Array of Nodes (buckets)
 * - Lock-free reads (volatile reads)
 * - Fine-grained locking for writes (per-bucket CAS + synchronized)
 * - No full map locking (unlike synchronized HashMap)
 * - Segments replaced with Node-level synchronization in Java 8
 *
 * STRUCTURE:
 * ┌───────────────────────────────────────────────────────────────────┐
 * │ ConcurrentHashMap (Java 8+)                                       │
 * │                                                                   │
 * │  table[] (Node array)                                            │
 * │  ┌──────┬──────┬──────┬──────┬──────┬──────┬──────┬──────┐       │
 * │  │  0   │  1   │  2   │  3   │  4   │  5   │  6   │ ...  │       │
 * │  └──┬───┴──────┴──┬───┴──────┴──────┴──┬───┴──────┴──────┘       │
 * │     │             │                    │                          │
 * │     ▼             ▼                    ▼                          │
 * │  ┌─────┐       ┌─────┐             ┌─────┐                       │
 * │  │Node │       │Node │             │Node │                       │
 * │  │CAS  │       │sync │             │Tree │ (if > TREEIFY)        │
 * │  └─────┘       └─────┘             └─────┘                       │
 * │                                                                   │
 * │  Read: No locking (volatile)                                     │
 * │  Write: CAS for empty bucket, synchronized for non-empty         │
 * │  Tree: Converted when bucket size > 8                            │
 * └───────────────────────────────────────────────────────────────────┘
 *
 * KEY CHARACTERISTICS:
 * - Thread-safe without full synchronization
 * - NO null keys or values allowed
 * - Weakly consistent iterators (no ConcurrentModificationException)
 * - High concurrency for reads and writes
 *
 * TIME COMPLEXITY:
 * - put/get/remove: O(1) average, O(log n) worst
 * - size(): O(n) - approximate count
 */
public class ConcurrentHashMapDemo {

    public static void main(String[] args) throws Exception {

        // ===== CREATION =====

        System.out.println("===== CREATION =====");

        // Default constructor
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        // With initial capacity
        ConcurrentHashMap<String, Integer> mapWithCap = new ConcurrentHashMap<>(32);

        // With capacity and load factor
        ConcurrentHashMap<String, Integer> mapWithLF = new ConcurrentHashMap<>(32, 0.75f);

        // With concurrency level (hint for internal sizing)
        ConcurrentHashMap<String, Integer> mapWithConc = new ConcurrentHashMap<>(32, 0.75f, 16);

        // From another map
        Map<String, Integer> source = Map.of("A", 1, "B", 2, "C", 3);
        ConcurrentHashMap<String, Integer> fromMap = new ConcurrentHashMap<>(source);
        System.out.println("From Map: " + fromMap);

        // ===== BASIC OPERATIONS =====

        System.out.println("\n===== BASIC OPERATIONS =====");

        map.put("Alice", 25);
        map.put("Bob", 30);
        map.put("Charlie", 35);

        System.out.println("After puts: " + map);
        System.out.println("get('Alice'): " + map.get("Alice"));
        System.out.println("size(): " + map.size());

        // ===== ATOMIC OPERATIONS =====

        System.out.println("\n===== ATOMIC OPERATIONS =====");

        // putIfAbsent - atomic check-then-put
        Integer prev = map.putIfAbsent("Alice", 100);  // Won't update
        System.out.println("putIfAbsent('Alice', 100): " + prev);  // 25

        prev = map.putIfAbsent("Diana", 28);  // Will add
        System.out.println("putIfAbsent('Diana', 28): " + prev);  // null
        System.out.println("Map: " + map);

        // replace(key, value) - only if key exists
        Integer old = map.replace("Bob", 31);
        System.out.println("replace('Bob', 31): " + old);  // 30

        // replace(key, oldValue, newValue) - only if current value matches
        boolean success = map.replace("Bob", 31, 32);
        System.out.println("replace('Bob', 31, 32): " + success);  // true

        success = map.replace("Bob", 99, 100);  // Won't match
        System.out.println("replace('Bob', 99, 100): " + success);  // false

        // remove(key, value) - only if value matches
        success = map.remove("Charlie", 35);
        System.out.println("remove('Charlie', 35): " + success);  // true

        // ===== COMPUTE OPERATIONS =====

        System.out.println("\n===== COMPUTE OPERATIONS =====");

        map.put("Eve", 20);

        // compute - atomically compute new value
        map.compute("Eve", (k, v) -> v + 1);
        System.out.println("After compute (Eve+1): " + map.get("Eve"));

        // computeIfAbsent - compute only if absent
        map.computeIfAbsent("Frank", k -> k.length() * 10);
        System.out.println("computeIfAbsent('Frank'): " + map.get("Frank"));

        // computeIfPresent - compute only if present
        map.computeIfPresent("Frank", (k, v) -> v + 5);
        System.out.println("computeIfPresent('Frank'): " + map.get("Frank"));

        // merge - combine old and new values
        map.merge("Eve", 10, Integer::sum);  // Add 10
        System.out.println("After merge (Eve+10): " + map.get("Eve"));

        // ===== BULK OPERATIONS (Java 8+) =====

        System.out.println("\n===== BULK OPERATIONS =====");

        ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>();
        scores.put("Alice", 85);
        scores.put("Bob", 92);
        scores.put("Charlie", 78);
        scores.put("Diana", 95);
        scores.put("Eve", 88);

        // forEach - parallel iteration
        System.out.println("forEach:");
        scores.forEach(2, (k, v) ->
            System.out.println("  " + Thread.currentThread().getName() + ": " + k + "=" + v)
        );

        // search - parallel search, returns first match
        String highScorer = scores.search(2, (k, v) -> v > 90 ? k : null);
        System.out.println("First scorer > 90: " + highScorer);

        // reduce - parallel reduction
        Integer maxScore = scores.reduce(2, (k, v) -> v, Integer::max);
        System.out.println("Max score: " + maxScore);

        Integer totalScore = scores.reduce(2, (k, v) -> v, Integer::sum);
        System.out.println("Total score: " + totalScore);

        // reduceKeys / reduceValues
        String allNames = scores.reduceKeys(2, (a, b) -> a + ", " + b);
        System.out.println("All names: " + allNames);

        // ===== THREAD-SAFE DEMO =====

        System.out.println("\n===== THREAD-SAFE DEMO =====");

        ConcurrentHashMap<String, Integer> counter = new ConcurrentHashMap<>();
        counter.put("count", 0);

        // Multiple threads incrementing same key
        int numThreads = 10;
        int incrementsPerThread = 1000;
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    // Atomic increment using compute
                    counter.compute("count", (k, v) -> v + 1);
                }
            });
            threads[i].start();
        }

        // Wait for all threads
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Expected: " + (numThreads * incrementsPerThread));
        System.out.println("Actual: " + counter.get("count"));
        System.out.println("Thread-safe: " + (counter.get("count") == numThreads * incrementsPerThread));

        // ===== ITERATION (Weakly Consistent) =====

        System.out.println("\n===== ITERATION =====");

        ConcurrentHashMap<String, Integer> iterMap = new ConcurrentHashMap<>(scores);

        // Safe to iterate and modify concurrently
        System.out.println("Iterating (no ConcurrentModificationException):");
        for (Map.Entry<String, Integer> e : iterMap.entrySet()) {
            System.out.println("  " + e.getKey() + " = " + e.getValue());
            // Safe to modify during iteration
            if (e.getValue() < 80) {
                iterMap.remove(e.getKey());
            }
        }
        System.out.println("After removal during iteration: " + iterMap);

        // ===== KEY SET AS SET =====

        System.out.println("\n===== KEY SET AS SET =====");

        // newKeySet() - creates a concurrent Set
        Set<String> concurrentSet = ConcurrentHashMap.newKeySet();
        concurrentSet.add("X");
        concurrentSet.add("Y");
        concurrentSet.add("Z");
        System.out.println("Concurrent Set: " + concurrentSet);

        // keySet(defaultValue) - backed by map
        Set<String> keySetBacked = iterMap.keySet(100);  // Default value for new entries
        keySetBacked.add("NewKey");  // Adds to map with value 100
        System.out.println("After keySet().add(): " + iterMap);

        // ===== NULL HANDLING =====

        System.out.println("\n===== NULL HANDLING =====");

        System.out.println("ConcurrentHashMap does NOT allow null keys or values");
        System.out.println("Both throw NullPointerException");
        // map.put(null, 1);     // NullPointerException
        // map.put("key", null); // NullPointerException

        // ===== SIZE & MAPPINGS COUNT =====

        System.out.println("\n===== SIZE =====");

        System.out.println("size(): " + iterMap.size());
        System.out.println("mappingCount(): " + iterMap.mappingCount());  // Long, more accurate for large maps
        System.out.println("isEmpty(): " + iterMap.isEmpty());

        System.out.println("\n===== Demo Complete =====");
        System.out.println("See class Javadoc for comparison table and when-to-use guide.");
    }
}

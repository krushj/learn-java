package collections.map;

import java.util.*;

/**
 * HashMapDemo
 *
 * Demonstrates HashMap - the most commonly used Map implementation.
 *
 * COMPARISON:
 * +------------------+----------+----------+-------------+------------+
 * | Map Type         | Ordered  | Sorted   | Performance | Null Keys  |
 * +------------------+----------+----------+-------------+------------+
 * | HashMap          | No       | No       | O(1)        | 1 allowed  |
 * | LinkedHashMap    | Insert   | No       | O(1)        | 1 allowed  |
 * | TreeMap          | Sorted   | Yes      | O(log n)    | Not allowed|
 * | ConcurrentHashMap| No       | No       | O(1)        | Not allowed|
 * | Hashtable        | No       | No       | O(1)        | Not allowed|
 * +------------------+----------+----------+-------------+------------+
 *
 * WHEN TO USE:
 * - HashMap: Default choice, fast operations, order doesn't matter
 * - LinkedHashMap: Need insertion/access order preserved
 * - TreeMap: Need sorted keys, range queries
 * - ConcurrentHashMap: Multi-threaded concurrent access
 *
 * INTERNAL WORKING:
 * - Array of buckets (Node[] table)
 * - Each bucket: LinkedList → Red-Black Tree (when size > 8)
 * - Hash: (key.hashCode()) ^ (h >>> 16) - spreads higher bits
 * - Index: hash & (capacity - 1) - capacity always power of 2
 * - Load factor: 0.75 (default) - threshold for resizing
 * - Resize: doubles capacity, rehashes all entries
 *
 * STRUCTURE (Java 8+):
 * ┌───────────────────────────────────────────────────────────────────┐
 * │ HashMap                                                           │
 * │                                                                   │
 * │  table[] (Node array, capacity = 16 default)                     │
 * │  ┌──────┬──────┬──────┬──────┬──────┬──────┬──────┬──────┐       │
 * │  │  0   │  1   │  2   │  3   │  4   │  5   │  6   │ ...  │       │
 * │  └──┬───┴──────┴──┬───┴──────┴──────┴──┬───┴──────┴──────┘       │
 * │     │             │                    │                          │
 * │     ▼             ▼                    ▼                          │
 * │  ┌─────┐       ┌─────┐             ┌─────┐                       │
 * │  │K1,V1│       │K2,V2│             │K4,V4│                       │
 * │  └──┬──┘       └──┬──┘             └──┬──┘                       │
 * │     │             │                   │                          │
 * │     ▼             ▼                   ▼                          │
 * │  ┌─────┐       ┌─────┐           Red-Black                       │
 * │  │K3,V3│       │null │           Tree (if >8)                    │
 * │  └─────┘       └─────┘                                           │
 * │                                                                   │
 * │  Linked List        Tree (TREEIFY_THRESHOLD = 8)                 │
 * └───────────────────────────────────────────────────────────────────┘
 *
 * TIME COMPLEXITY:
 * - put(): O(1) average, O(log n) worst (tree), O(n) worst (list)
 * - get(): O(1) average, O(log n) worst (tree)
 * - remove(): O(1) average
 * - containsKey(): O(1) average
 * - containsValue(): O(n) - must scan all values
 *
 * NOT THREAD-SAFE: Use ConcurrentHashMap for concurrent access
 */
public class HashMapDemo {

    public static void main(String[] args) {

        // ===== CREATION =====

        System.out.println("===== CREATION =====");

        // Default constructor: capacity=16, loadFactor=0.75
        HashMap<String, Integer> map = new HashMap<>();

        // With initial capacity
        HashMap<String, Integer> mapWithCapacity = new HashMap<>(32);

        // With capacity and load factor
        HashMap<String, Integer> mapWithLF = new HashMap<>(32, 0.5f);

        // From another map
        Map<String, Integer> source = Map.of("A", 1, "B", 2);
        HashMap<String, Integer> mapFromMap = new HashMap<>(source);

        System.out.println("Created maps with different constructors");
        System.out.println("From Map.of(): " + mapFromMap);

        // ===== PUT OPERATIONS =====

        System.out.println("\n===== PUT OPERATIONS =====");

        // put(K, V) - returns previous value or null
        // Time: O(1) average
        Integer prev = map.put("Alice", 25);
        System.out.println("put('Alice', 25) returned: " + prev);  // null

        map.put("Bob", 30);
        map.put("Charlie", 35);

        // put() with existing key - overwrites
        prev = map.put("Alice", 26);
        System.out.println("put('Alice', 26) returned: " + prev);  // 25

        System.out.println("Map after puts: " + map);

        // putIfAbsent() - only if key not present
        Integer result = map.putIfAbsent("Alice", 100);  // Won't update
        System.out.println("putIfAbsent('Alice', 100): " + result);  // 26

        result = map.putIfAbsent("Diana", 28);  // Will add
        System.out.println("putIfAbsent('Diana', 28): " + result);  // null

        // putAll() - bulk put
        map.putAll(Map.of("Eve", 22, "Frank", 40));
        System.out.println("After putAll: " + map);

        // ===== GET OPERATIONS =====

        System.out.println("\n===== GET OPERATIONS =====");

        // get(K) - returns value or null
        // Time: O(1) average
        Integer age = map.get("Alice");
        System.out.println("get('Alice'): " + age);

        age = map.get("Unknown");
        System.out.println("get('Unknown'): " + age);  // null

        // getOrDefault() - returns default if not found
        age = map.getOrDefault("Unknown", -1);
        System.out.println("getOrDefault('Unknown', -1): " + age);

        // ===== CONTAINS OPERATIONS =====

        System.out.println("\n===== CONTAINS OPERATIONS =====");

        // containsKey() - O(1) average
        System.out.println("containsKey('Bob'): " + map.containsKey("Bob"));
        System.out.println("containsKey('Zack'): " + map.containsKey("Zack"));

        // containsValue() - O(n) - must scan all values!
        System.out.println("containsValue(30): " + map.containsValue(30));
        System.out.println("containsValue(99): " + map.containsValue(99));

        // ===== REMOVE OPERATIONS =====

        System.out.println("\n===== REMOVE OPERATIONS =====");

        // remove(K) - returns removed value
        Integer removed = map.remove("Frank");
        System.out.println("remove('Frank'): " + removed);

        // remove(K, V) - removes only if value matches
        boolean success = map.remove("Eve", 99);  // Won't remove (value mismatch)
        System.out.println("remove('Eve', 99): " + success);

        success = map.remove("Eve", 22);  // Will remove
        System.out.println("remove('Eve', 22): " + success);

        System.out.println("After removes: " + map);

        // ===== REPLACE OPERATIONS =====

        System.out.println("\n===== REPLACE OPERATIONS =====");

        // replace(K, V) - only if key exists
        Integer oldVal = map.replace("Bob", 31);
        System.out.println("replace('Bob', 31): " + oldVal);

        oldVal = map.replace("Unknown", 99);  // Does nothing
        System.out.println("replace('Unknown', 99): " + oldVal);  // null

        // replace(K, oldV, newV) - only if current value matches
        success = map.replace("Alice", 26, 27);
        System.out.println("replace('Alice', 26, 27): " + success);

        System.out.println("After replaces: " + map);

        // ===== COMPUTE OPERATIONS =====

        System.out.println("\n===== COMPUTE OPERATIONS =====");

        // compute() - compute new value from key and current value
        map.compute("Bob", (k, v) -> v + 1);  // Increment
        System.out.println("After compute (Bob+1): " + map.get("Bob"));

        // computeIfAbsent() - compute only if absent
        map.computeIfAbsent("Grace", k -> k.length() * 10);
        System.out.println("computeIfAbsent('Grace'): " + map.get("Grace"));

        // computeIfPresent() - compute only if present
        map.computeIfPresent("Grace", (k, v) -> v + 5);
        System.out.println("computeIfPresent('Grace'): " + map.get("Grace"));

        // ===== MERGE OPERATION =====

        System.out.println("\n===== MERGE OPERATION =====");

        // merge() - combine old and new values
        map.merge("Bob", 10, Integer::sum);  // Add 10 to Bob
        System.out.println("merge('Bob', 10, sum): " + map.get("Bob"));

        map.merge("NewPerson", 50, Integer::sum);  // Creates if absent
        System.out.println("merge('NewPerson', 50, sum): " + map.get("NewPerson"));

        // ===== VIEW COLLECTIONS =====

        System.out.println("\n===== VIEW COLLECTIONS =====");

        // keySet() - view of keys
        Set<String> keys = map.keySet();
        System.out.println("Keys: " + keys);

        // values() - view of values
        Collection<Integer> values = map.values();
        System.out.println("Values: " + values);

        // entrySet() - view of entries
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        System.out.println("Entries: " + entries);

        // Note: Views are backed by map - changes reflect in map!
        // keys.remove("Alice");  // Would remove from map too

        // ===== ITERATION =====

        System.out.println("\n===== ITERATION =====");

        // Method 1: entrySet() - most efficient
        System.out.println("Using entrySet():");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("  " + entry.getKey() + " = " + entry.getValue());
        }

        // Method 2: keySet() + get() - less efficient (extra lookup)
        System.out.println("Using keySet():");
        for (String key : map.keySet()) {
            System.out.println("  " + key + " = " + map.get(key));
        }

        // Method 3: forEach() - Java 8+
        System.out.println("Using forEach():");
        map.forEach((k, v) -> System.out.println("  " + k + " = " + v));

        // Method 4: Iterator (for removal during iteration)
        Iterator<Map.Entry<String, Integer>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Integer> e = iter.next();
            if (e.getValue() > 50) {
                // iter.remove();  // Safe removal during iteration
            }
        }

        // ===== SIZE & STATE =====

        System.out.println("\n===== SIZE & STATE =====");

        System.out.println("Size: " + map.size());
        System.out.println("isEmpty: " + map.isEmpty());

        // clear() - removes all
        HashMap<String, Integer> tempMap = new HashMap<>(map);
        tempMap.clear();
        System.out.println("After clear, isEmpty: " + tempMap.isEmpty());

        // ===== NULL HANDLING =====

        System.out.println("\n===== NULL HANDLING =====");

        // HashMap allows ONE null key and multiple null values
        HashMap<String, Integer> nullMap = new HashMap<>();
        nullMap.put(null, 100);  // null key allowed
        nullMap.put("nullValue", null);  // null value allowed

        System.out.println("get(null): " + nullMap.get(null));
        System.out.println("get('nullValue'): " + nullMap.get("nullValue"));

        // ===== HASHCODE & EQUALS CONTRACT =====

        System.out.println("\n===== HASHCODE & EQUALS =====");

        System.out.println(
            "For HashMap to work correctly with custom objects:\n" +
            "\n" +
            "1. equals() CONTRACT:\n" +
            "   - Reflexive: a.equals(a) = true\n" +
            "   - Symmetric: a.equals(b) = b.equals(a)\n" +
            "   - Transitive: a=b, b=c => a=c\n" +
            "   - Consistent: same result on multiple calls\n" +
            "   - a.equals(null) = false\n" +
            "\n" +
            "2. hashCode() CONTRACT:\n" +
            "   - Same object = same hashCode (consistent)\n" +
            "   - a.equals(b) => a.hashCode() == b.hashCode()\n" +
            "   - Different hashCodes => !equals (contrapositive)\n" +
            "   - Equal objects MUST have equal hashCodes\n" +
            "   - Different objects CAN have same hashCode (collision)"
        );

        // Demo with custom key class
        HashMap<Person, String> personMap = new HashMap<>();
        Person p1 = new Person("John", 30);
        Person p2 = new Person("John", 30);

        personMap.put(p1, "Engineer");

        // p2.equals(p1) is true, and hashCodes are same
        System.out.println("\np1.equals(p2): " + p1.equals(p2));
        System.out.println("p1.hashCode(): " + p1.hashCode());
        System.out.println("p2.hashCode(): " + p2.hashCode());
        System.out.println("personMap.get(p2): " + personMap.get(p2));

        System.out.println("\n===== Demo Complete =====");
        System.out.println("See class Javadoc for comparison table and when-to-use guide.");
    }
}

/**
 * Example class with proper equals() and hashCode()
 */
class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}

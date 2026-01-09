package collections.map;

import java.util.*;

/**
 * TreeMapDemo
 *
 * Demonstrates TreeMap - a NavigableMap backed by Red-Black Tree.
 * Keys are sorted and unique.
 *
 * COMPARISON:
 * +------------------+----------+----------+-------------+------------+
 * | Map Type         | Ordered  | Sorted   | Performance | Null Keys  |
 * +------------------+----------+----------+-------------+------------+
 * | HashMap          | No       | No       | O(1)        | 1 allowed  |
 * | LinkedHashMap    | Insert   | No       | O(1)        | 1 allowed  |
 * | TreeMap          | Sorted   | Yes      | O(log n)    | Not allowed|
 * | ConcurrentHashMap| No       | No       | O(1)        | Not allowed|
 * +------------------+----------+----------+-------------+------------+
 *
 * WHEN TO USE:
 * - TreeMap: Need sorted keys, range queries (subMap, headMap, tailMap)
 * - HashMap: Order doesn't matter, need fastest put/get/remove
 * - LinkedHashMap: Need insertion order preserved
 *
 * INTERNAL WORKING:
 * - Red-Black Tree (self-balancing BST)
 * - Keys sorted by natural order or Comparator
 * - No null keys (throws NullPointerException)
 * - O(log n) for most operations
 *
 * STRUCTURE:
 * ┌───────────────────────────────────────────────────────────────────┐
 * │ TreeMap (Red-Black Tree)                                          │
 * │                                                                   │
 * │              ┌─────────────────────┐                             │
 * │              │  K:50, V:"Fifty"   │ ← Root                       │
 * │              └──────────┬──────────┘                             │
 * │           ┌─────────────┴─────────────┐                          │
 * │      ┌────▼────────┐          ┌───────▼───────┐                  │
 * │      │ K:30, V:... │          │ K:70, V:...   │                  │
 * │      └──────┬──────┘          └───────┬───────┘                  │
 * │        ┌────┴────┐              ┌─────┴─────┐                    │
 * │     ┌──▼──┐   ┌──▼──┐        ┌──▼──┐    ┌───▼──┐                │
 * │     │ 20  │   │ 40  │        │ 60  │    │  80  │                │
 * │     └─────┘   └─────┘        └─────┘    └──────┘                │
 * │                                                                   │
 * │  Sorted by keys (natural order or Comparator)                    │
 * └───────────────────────────────────────────────────────────────────┘
 *
 * TIME COMPLEXITY:
 * - put(): O(log n)
 * - get(): O(log n)
 * - remove(): O(log n)
 * - containsKey(): O(log n)
 * - firstKey/lastKey(): O(log n)
 */
public class TreeMapDemo {

    public static void main(String[] args) {

        // ===== CREATION =====

        System.out.println("===== CREATION =====");

        // Natural ordering
        TreeMap<Integer, String> map = new TreeMap<>();

        // Reverse order
        TreeMap<Integer, String> descMap = new TreeMap<>(Comparator.reverseOrder());

        // From another map
        Map<Integer, String> source = Map.of(3, "Three", 1, "One", 2, "Two");
        TreeMap<Integer, String> fromMap = new TreeMap<>(source);
        System.out.println("From Map (sorted): " + fromMap);

        // ===== PUT OPERATIONS =====

        System.out.println("\n===== PUT OPERATIONS =====");

        map.put(50, "Fifty");
        map.put(30, "Thirty");
        map.put(70, "Seventy");
        map.put(20, "Twenty");
        map.put(40, "Forty");
        map.put(60, "Sixty");
        map.put(80, "Eighty");

        System.out.println("TreeMap (sorted by keys): " + map);

        // putIfAbsent()
        map.putIfAbsent(50, "New Fifty");  // Won't update
        map.putIfAbsent(90, "Ninety");     // Will add
        System.out.println("After putIfAbsent: " + map);

        // ===== GET OPERATIONS =====

        System.out.println("\n===== GET OPERATIONS =====");

        System.out.println("get(50): " + map.get(50));
        System.out.println("get(100): " + map.get(100));  // null
        System.out.println("getOrDefault(100, 'N/A'): " + map.getOrDefault(100, "N/A"));

        // ===== NAVIGATION - KEYS =====

        System.out.println("\n===== NAVIGATION - KEYS =====");

        System.out.println("Map: " + map);

        // firstKey() / lastKey()
        System.out.println("firstKey(): " + map.firstKey());  // 20
        System.out.println("lastKey(): " + map.lastKey());    // 90

        // floorKey(k) - greatest key <= k
        System.out.println("floorKey(45): " + map.floorKey(45));  // 40
        System.out.println("floorKey(40): " + map.floorKey(40));  // 40

        // ceilingKey(k) - smallest key >= k
        System.out.println("ceilingKey(45): " + map.ceilingKey(45));  // 50

        // lowerKey(k) - greatest key < k (strictly less)
        System.out.println("lowerKey(50): " + map.lowerKey(50));  // 40

        // higherKey(k) - smallest key > k (strictly greater)
        System.out.println("higherKey(50): " + map.higherKey(50));  // 60

        // ===== NAVIGATION - ENTRIES =====

        System.out.println("\n===== NAVIGATION - ENTRIES =====");

        // firstEntry() / lastEntry()
        System.out.println("firstEntry(): " + map.firstEntry());
        System.out.println("lastEntry(): " + map.lastEntry());

        // floorEntry() / ceilingEntry()
        System.out.println("floorEntry(45): " + map.floorEntry(45));
        System.out.println("ceilingEntry(45): " + map.ceilingEntry(45));

        // lowerEntry() / higherEntry()
        System.out.println("lowerEntry(50): " + map.lowerEntry(50));
        System.out.println("higherEntry(50): " + map.higherEntry(50));

        // ===== POLL OPERATIONS =====

        System.out.println("\n===== POLL OPERATIONS =====");

        TreeMap<Integer, String> pollMap = new TreeMap<>(map);

        // pollFirstEntry() - removes and returns smallest
        System.out.println("pollFirstEntry(): " + pollMap.pollFirstEntry());

        // pollLastEntry() - removes and returns largest
        System.out.println("pollLastEntry(): " + pollMap.pollLastEntry());

        System.out.println("After polls: " + pollMap);

        // ===== RANGE VIEWS =====

        System.out.println("\n===== RANGE VIEWS =====");

        System.out.println("Original: " + map);

        // headMap(toKey) - keys < toKey
        SortedMap<Integer, String> head = map.headMap(50);
        System.out.println("headMap(50): " + head);

        // headMap(toKey, inclusive)
        NavigableMap<Integer, String> headInc = map.headMap(50, true);
        System.out.println("headMap(50, true): " + headInc);

        // tailMap(fromKey) - keys >= fromKey
        SortedMap<Integer, String> tail = map.tailMap(50);
        System.out.println("tailMap(50): " + tail);

        // tailMap(fromKey, inclusive)
        NavigableMap<Integer, String> tailExc = map.tailMap(50, false);
        System.out.println("tailMap(50, false): " + tailExc);

        // subMap(from, to) - from <= key < to
        SortedMap<Integer, String> sub = map.subMap(30, 70);
        System.out.println("subMap(30, 70): " + sub);

        // subMap with inclusivity
        NavigableMap<Integer, String> subInc = map.subMap(30, true, 70, true);
        System.out.println("subMap(30, true, 70, true): " + subInc);

        // ===== DESCENDING VIEWS =====

        System.out.println("\n===== DESCENDING VIEWS =====");

        // descendingMap()
        NavigableMap<Integer, String> desc = map.descendingMap();
        System.out.println("descendingMap(): " + desc);

        // descendingKeySet()
        NavigableSet<Integer> descKeys = map.descendingKeySet();
        System.out.println("descendingKeySet(): " + descKeys);

        // ===== KEY/VALUE/ENTRY VIEWS =====

        System.out.println("\n===== VIEWS =====");

        // keySet() - sorted!
        System.out.println("keySet(): " + map.keySet());

        // values() - in key order
        System.out.println("values(): " + map.values());

        // entrySet()
        System.out.println("entrySet(): " + map.entrySet());

        // navigableKeySet()
        NavigableSet<Integer> navKeys = map.navigableKeySet();
        System.out.println("navigableKeySet(): " + navKeys);

        // ===== CUSTOM OBJECTS AS KEYS =====

        System.out.println("\n===== CUSTOM OBJECTS AS KEYS =====");

        // Using Comparable
        TreeMap<Employee, String> empMap = new TreeMap<>();
        empMap.put(new Employee(3, "Charlie"), "Engineering");
        empMap.put(new Employee(1, "Alice"), "HR");
        empMap.put(new Employee(2, "Bob"), "Sales");

        System.out.println("Employees by ID:");
        empMap.forEach((emp, dept) ->
            System.out.println("  " + emp + " -> " + dept));

        // Using Comparator (by name)
        TreeMap<Employee, String> byName = new TreeMap<>(
            Comparator.comparing(e -> e.name)
        );
        byName.putAll(empMap);
        System.out.println("\nEmployees by Name:");
        byName.forEach((emp, dept) ->
            System.out.println("  " + emp + " -> " + dept));

        // ===== REMOVE OPERATIONS =====

        System.out.println("\n===== REMOVE OPERATIONS =====");

        TreeMap<Integer, String> removeMap = new TreeMap<>(map);

        // remove(key)
        String removed = removeMap.remove(50);
        System.out.println("remove(50): " + removed);

        // remove(key, value) - conditional
        boolean success = removeMap.remove(60, "Wrong");  // Won't remove
        System.out.println("remove(60, 'Wrong'): " + success);

        success = removeMap.remove(60, "Sixty");  // Will remove
        System.out.println("remove(60, 'Sixty'): " + success);

        System.out.println("After removes: " + removeMap);

        // ===== NULL HANDLING =====

        System.out.println("\n===== NULL HANDLING =====");

        System.out.println("TreeMap does NOT allow null keys");
        System.out.println("Null values ARE allowed");

        TreeMap<Integer, String> nullValMap = new TreeMap<>();
        nullValMap.put(1, null);  // OK
        System.out.println("Map with null value: " + nullValMap);

        // ===== ITERATION =====

        System.out.println("\n===== ITERATION =====");

        // entrySet (recommended)
        System.out.println("Forward:");
        for (Map.Entry<Integer, String> e : map.entrySet()) {
            System.out.println("  " + e.getKey() + " = " + e.getValue());
        }

        // Reverse using descendingMap
        System.out.println("Reverse:");
        for (Map.Entry<Integer, String> e : map.descendingMap().entrySet()) {
            System.out.println("  " + e.getKey() + " = " + e.getValue());
        }

        System.out.println("\n===== Demo Complete =====");
        System.out.println("See class Javadoc for comparison table and when-to-use guide.");
    }
}

/**
 * Employee class implementing Comparable
 */
class Employee implements Comparable<Employee> {
    int id;
    String name;

    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "'}";
    }
}

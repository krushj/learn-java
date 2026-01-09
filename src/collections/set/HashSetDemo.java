package collections.set;

import java.util.*;

/**
 * HashSetDemo
 *
 * Demonstrates HashSet - Set implementation backed by HashMap.
 *
 * COMPARISON:
 * +----------------+----------+----------+-------------+------------+
 * | Set Type       | Ordered  | Sorted   | Performance | Null       |
 * +----------------+----------+----------+-------------+------------+
 * | HashSet        | No       | No       | O(1)        | 1 allowed  |
 * | LinkedHashSet  | Insert   | No       | O(1)        | 1 allowed  |
 * | TreeSet        | Sorted   | Yes      | O(log n)    | Not allowed|
 * +----------------+----------+----------+-------------+------------+
 *
 * WHEN TO USE:
 * - HashSet: Order doesn't matter, need fastest operations
 * - LinkedHashSet: Need insertion order preserved
 * - TreeSet: Need sorted elements, range queries
 *
 * INTERNAL WORKING:
 * - Backed by HashMap internally
 * - Elements stored as keys, dummy value (PRESENT) as values
 * - Uses hashCode() for bucket index
 * - Uses equals() for equality check
 * - NO ordering guarantee
 * - NO duplicates allowed
 *
 * STRUCTURE:
 * ┌───────────────────────────────────────────────────────────────────┐
 * │ HashSet                                                           │
 * │                                                                   │
 * │  Internally uses HashMap<E, Object>                              │
 * │                                                                   │
 * │  ┌─────────────────────────────────────────────────────────┐     │
 * │  │ HashMap                                                  │     │
 * │  │  Key (Element)  →  Value (PRESENT - dummy object)       │     │
 * │  │  "Apple"        →  PRESENT                              │     │
 * │  │  "Banana"       →  PRESENT                              │     │
 * │  │  "Cherry"       →  PRESENT                              │     │
 * │  └─────────────────────────────────────────────────────────┘     │
 * │                                                                   │
 * │  add("Apple") → map.put("Apple", PRESENT)                        │
 * │  contains("Apple") → map.containsKey("Apple")                    │
 * │  remove("Apple") → map.remove("Apple")                           │
 * └───────────────────────────────────────────────────────────────────┘
 *
 * TIME COMPLEXITY:
 * - add(): O(1) average
 * - remove(): O(1) average
 * - contains(): O(1) average
 * - size(): O(1)
 *
 * Allows ONE null element
 * NOT thread-safe (use Collections.synchronizedSet or ConcurrentHashMap.newKeySet())
 */
public class HashSetDemo {

    public static void main(String[] args) {

        // ===== CREATION =====

        System.out.println("===== CREATION =====");

        // Default constructor
        HashSet<String> set = new HashSet<>();

        // With initial capacity
        HashSet<String> setWithCapacity = new HashSet<>(32);

        // With capacity and load factor
        HashSet<String> setWithLF = new HashSet<>(32, 0.5f);

        // From collection
        HashSet<String> setFromList = new HashSet<>(Arrays.asList("A", "B", "C", "A"));
        System.out.println("From list with duplicate: " + setFromList);  // [A, B, C]

        // Using Set.of() - immutable (Java 9+)
        Set<String> immutableSet = Set.of("X", "Y", "Z");
        System.out.println("Immutable set: " + immutableSet);

        // ===== ADD OPERATIONS =====

        System.out.println("\n===== ADD OPERATIONS =====");

        // add(E) - returns true if added, false if already exists
        // Time: O(1) average
        boolean added = set.add("Apple");
        System.out.println("add('Apple'): " + added);  // true

        added = set.add("Banana");
        System.out.println("add('Banana'): " + added);  // true

        added = set.add("Apple");  // Duplicate
        System.out.println("add('Apple') again: " + added);  // false

        set.add("Cherry");
        set.add("Date");
        System.out.println("Set after adds: " + set);

        // addAll() - bulk add, returns true if set changed
        // Time: O(n)
        boolean changed = set.addAll(Arrays.asList("Elderberry", "Fig", "Apple"));
        System.out.println("addAll() changed set: " + changed);
        System.out.println("Set after addAll: " + set);

        // ===== CONTAINS OPERATIONS =====

        System.out.println("\n===== CONTAINS OPERATIONS =====");

        // contains() - O(1) average
        System.out.println("contains('Apple'): " + set.contains("Apple"));
        System.out.println("contains('Grape'): " + set.contains("Grape"));

        // containsAll() - check if all elements exist
        boolean hasAll = set.containsAll(Arrays.asList("Apple", "Banana"));
        System.out.println("containsAll(['Apple', 'Banana']): " + hasAll);

        hasAll = set.containsAll(Arrays.asList("Apple", "Grape"));
        System.out.println("containsAll(['Apple', 'Grape']): " + hasAll);

        // ===== REMOVE OPERATIONS =====

        System.out.println("\n===== REMOVE OPERATIONS =====");

        // remove() - returns true if removed
        // Time: O(1) average
        boolean removed = set.remove("Date");
        System.out.println("remove('Date'): " + removed);

        removed = set.remove("Grape");  // Not in set
        System.out.println("remove('Grape'): " + removed);

        System.out.println("Set after removes: " + set);

        // removeAll() - remove all elements in collection
        set.removeAll(Arrays.asList("Elderberry", "NonExistent"));
        System.out.println("After removeAll: " + set);

        // removeIf() - remove matching predicate
        set.add("Apricot");
        set.add("Avocado");
        set.removeIf(s -> s.startsWith("A"));
        System.out.println("After removeIf(starts with A): " + set);

        // ===== SET OPERATIONS =====

        System.out.println("\n===== SET OPERATIONS =====");

        HashSet<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        HashSet<Integer> set2 = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));

        System.out.println("Set1: " + set1);
        System.out.println("Set2: " + set2);

        // UNION: addAll() - modifies set1!
        HashSet<Integer> union = new HashSet<>(set1);
        union.addAll(set2);
        System.out.println("Union (set1 ∪ set2): " + union);

        // INTERSECTION: retainAll() - modifies set!
        HashSet<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println("Intersection (set1 ∩ set2): " + intersection);

        // DIFFERENCE: removeAll() - modifies set!
        HashSet<Integer> difference = new HashSet<>(set1);
        difference.removeAll(set2);
        System.out.println("Difference (set1 - set2): " + difference);

        // SYMMETRIC DIFFERENCE: (A ∪ B) - (A ∩ B)
        HashSet<Integer> symDiff = new HashSet<>(set1);
        symDiff.addAll(set2);
        HashSet<Integer> intersect = new HashSet<>(set1);
        intersect.retainAll(set2);
        symDiff.removeAll(intersect);
        System.out.println("Symmetric Difference: " + symDiff);

        // ===== ITERATION =====

        System.out.println("\n===== ITERATION =====");

        HashSet<String> fruits = new HashSet<>(
            Arrays.asList("Apple", "Banana", "Cherry", "Date")
        );

        // Method 1: Enhanced for loop
        System.out.print("For-each: ");
        for (String fruit : fruits) {
            System.out.print(fruit + " ");
        }
        System.out.println();

        // Method 2: Iterator
        System.out.print("Iterator: ");
        Iterator<String> iter = fruits.iterator();
        while (iter.hasNext()) {
            System.out.print(iter.next() + " ");
        }
        System.out.println();

        // Method 3: forEach() with lambda
        System.out.print("forEach(): ");
        fruits.forEach(f -> System.out.print(f + " "));
        System.out.println();

        // Method 4: Stream
        System.out.print("Stream: ");
        fruits.stream().forEach(f -> System.out.print(f + " "));
        System.out.println();

        // Safe removal during iteration
        Iterator<String> removalIter = fruits.iterator();
        while (removalIter.hasNext()) {
            if (removalIter.next().equals("Date")) {
                removalIter.remove();  // Safe removal
            }
        }
        System.out.println("After iterator removal: " + fruits);

        // ===== SIZE & STATE =====

        System.out.println("\n===== SIZE & STATE =====");

        System.out.println("Size: " + fruits.size());
        System.out.println("isEmpty: " + fruits.isEmpty());

        // clear()
        HashSet<String> temp = new HashSet<>(fruits);
        temp.clear();
        System.out.println("After clear, isEmpty: " + temp.isEmpty());

        // ===== NULL HANDLING =====

        System.out.println("\n===== NULL HANDLING =====");

        HashSet<String> nullSet = new HashSet<>();
        nullSet.add(null);  // One null allowed
        nullSet.add("Value");
        nullSet.add(null);  // Duplicate null ignored

        System.out.println("Set with null: " + nullSet);
        System.out.println("contains(null): " + nullSet.contains(null));

        // ===== CONVERSION =====

        System.out.println("\n===== CONVERSION =====");

        HashSet<String> convSet = new HashSet<>(Arrays.asList("A", "B", "C"));

        // To array
        Object[] objArray = convSet.toArray();
        System.out.println("toArray(): " + Arrays.toString(objArray));

        String[] strArray = convSet.toArray(new String[0]);
        System.out.println("toArray(String[]): " + Arrays.toString(strArray));

        // To List
        List<String> list = new ArrayList<>(convSet);
        System.out.println("To List: " + list);

        // From array
        String[] arr = {"X", "Y", "Z"};
        HashSet<String> fromArray = new HashSet<>(Arrays.asList(arr));
        System.out.println("From array: " + fromArray);

        // ===== COMPARISON =====

        System.out.println("\n===== EQUALS COMPARISON =====");

        HashSet<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3));
        HashSet<Integer> b = new HashSet<>(Arrays.asList(3, 2, 1));  // Same elements
        HashSet<Integer> c = new HashSet<>(Arrays.asList(1, 2, 4));

        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("c: " + c);
        System.out.println("a.equals(b): " + a.equals(b));  // true - order doesn't matter
        System.out.println("a.equals(c): " + a.equals(c));  // false

        System.out.println("\n===== Demo Complete =====");
        System.out.println("See class Javadoc for comparison table and when-to-use guide.");
    }
}

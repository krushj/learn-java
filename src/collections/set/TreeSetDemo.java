package collections.set;

import java.util.*;

/**
 * TreeSetDemo
 *
 * Demonstrates TreeSet - a NavigableSet backed by TreeMap.
 * Elements are sorted and unique.
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
 * - TreeSet: Need sorted elements, range queries, floor/ceiling operations
 * - HashSet: Order doesn't matter, need fastest add/remove/contains
 * - LinkedHashSet: Need insertion order preserved
 *
 * INTERNAL WORKING:
 * - Backed by TreeMap (Red-Black Tree)
 * - Self-balancing BST
 * - Elements sorted by natural order or Comparator
 * - No duplicates, NO null allowed
 *
 * STRUCTURE:
 * ┌───────────────────────────────────────────────────────────────────┐
 * │ TreeSet (Red-Black Tree)                                          │
 * │                                                                   │
 * │              ┌───────────────────┐                               │
 * │              │    50 (BLACK)     │ ← Root                        │
 * │              └─────────┬─────────┘                               │
 * │           ┌────────────┴────────────┐                            │
 * │      ┌────▼────┐              ┌─────▼────┐                       │
 * │      │30 (RED) │              │ 70 (RED) │                       │
 * │      └────┬────┘              └────┬─────┘                       │
 * │      ┌────┴────┐              ┌────┴─────┐                       │
 * │   ┌──▼──┐   ┌──▼──┐        ┌──▼──┐   ┌───▼──┐                   │
 * │   │ 20  │   │ 40  │        │ 60  │   │  80  │                   │
 * │   └─────┘   └─────┘        └─────┘   └──────┘                   │
 * │                                                                   │
 * │  Red-Black Tree Properties:                                      │
 * │  1. Every node is RED or BLACK                                   │
 * │  2. Root is BLACK                                                │
 * │  3. Leaves (NIL) are BLACK                                       │
 * │  4. RED node's children are BLACK                                │
 * │  5. All paths have same BLACK node count                         │
 * └───────────────────────────────────────────────────────────────────┘
 *
 * TIME COMPLEXITY:
 * - add(): O(log n)
 * - remove(): O(log n)
 * - contains(): O(log n)
 * - first/last(): O(log n)
 * - floor/ceiling/higher/lower(): O(log n)
 */
public class TreeSetDemo {

    public static void main(String[] args) {

        // ===== CREATION =====

        System.out.println("===== CREATION =====");

        // Natural ordering (Comparable)
        TreeSet<Integer> set = new TreeSet<>();

        // With custom Comparator
        TreeSet<Integer> descSet = new TreeSet<>(Comparator.reverseOrder());

        // From collection (will be sorted)
        TreeSet<Integer> fromList = new TreeSet<>(
            Arrays.asList(50, 20, 80, 10, 30, 60, 70)
        );
        System.out.println("From unsorted list: " + fromList);

        // ===== ADD OPERATIONS =====

        System.out.println("\n===== ADD OPERATIONS =====");

        // add() - O(log n)
        set.add(50);
        set.add(30);
        set.add(70);
        set.add(20);
        set.add(40);
        set.add(60);
        set.add(80);

        System.out.println("TreeSet (sorted): " + set);

        // Duplicate ignored
        boolean added = set.add(50);
        System.out.println("add(50) duplicate: " + added);

        // addAll()
        set.addAll(Arrays.asList(15, 25, 35));
        System.out.println("After addAll: " + set);

        // ===== NAVIGATION OPERATIONS =====

        System.out.println("\n===== NAVIGATION OPERATIONS =====");

        System.out.println("Set: " + set);

        // first() / last() - O(log n)
        System.out.println("first(): " + set.first());  // 15 (smallest)
        System.out.println("last(): " + set.last());    // 80 (largest)

        // floor(e) - greatest element <= e
        System.out.println("floor(45): " + set.floor(45));  // 40
        System.out.println("floor(40): " + set.floor(40));  // 40

        // ceiling(e) - smallest element >= e
        System.out.println("ceiling(45): " + set.ceiling(45));  // 50
        System.out.println("ceiling(50): " + set.ceiling(50));  // 50

        // lower(e) - greatest element < e (strictly less)
        System.out.println("lower(50): " + set.lower(50));  // 40

        // higher(e) - smallest element > e (strictly greater)
        System.out.println("higher(50): " + set.higher(50));  // 60

        // ===== POLL OPERATIONS =====

        System.out.println("\n===== POLL OPERATIONS =====");

        TreeSet<Integer> pollSet = new TreeSet<>(set);

        // pollFirst() - removes and returns smallest
        System.out.println("pollFirst(): " + pollSet.pollFirst());
        System.out.println("After pollFirst: " + pollSet);

        // pollLast() - removes and returns largest
        System.out.println("pollLast(): " + pollSet.pollLast());
        System.out.println("After pollLast: " + pollSet);

        // ===== RANGE VIEWS =====

        System.out.println("\n===== RANGE VIEWS =====");

        System.out.println("Original set: " + set);

        // headSet(e) - elements < e
        SortedSet<Integer> head = set.headSet(50);
        System.out.println("headSet(50): " + head);  // [15, 20, 25, 30, 35, 40]

        // headSet(e, inclusive)
        NavigableSet<Integer> headInc = set.headSet(50, true);
        System.out.println("headSet(50, true): " + headInc);  // includes 50

        // tailSet(e) - elements >= e
        SortedSet<Integer> tail = set.tailSet(50);
        System.out.println("tailSet(50): " + tail);  // [50, 60, 70, 80]

        // tailSet(e, inclusive)
        NavigableSet<Integer> tailExc = set.tailSet(50, false);
        System.out.println("tailSet(50, false): " + tailExc);  // excludes 50

        // subSet(from, to) - elements from <= x < to
        SortedSet<Integer> sub = set.subSet(30, 70);
        System.out.println("subSet(30, 70): " + sub);

        // subSet with inclusivity
        NavigableSet<Integer> subInc = set.subSet(30, true, 70, true);
        System.out.println("subSet(30, true, 70, true): " + subInc);

        // Note: Views are backed by original set!

        // ===== DESCENDING VIEWS =====

        System.out.println("\n===== DESCENDING VIEWS =====");

        // descendingSet() - reverse order view
        NavigableSet<Integer> desc = set.descendingSet();
        System.out.println("descendingSet(): " + desc);

        // descendingIterator()
        System.out.print("Descending iterator: ");
        Iterator<Integer> descIter = set.descendingIterator();
        while (descIter.hasNext()) {
            System.out.print(descIter.next() + " ");
        }
        System.out.println();

        // ===== CUSTOM OBJECTS =====

        System.out.println("\n===== CUSTOM OBJECTS =====");

        // Using Comparable
        TreeSet<Student> students = new TreeSet<>();
        students.add(new Student("Alice", 85));
        students.add(new Student("Bob", 92));
        students.add(new Student("Charlie", 78));
        students.add(new Student("Diana", 92));  // Same score as Bob

        System.out.println("Students by score:");
        for (Student s : students) {
            System.out.println("  " + s);
        }

        // Using Comparator (by name)
        TreeSet<Student> byName = new TreeSet<>(
            Comparator.comparing(s -> s.name)
        );
        byName.addAll(students);
        System.out.println("\nStudents by name:");
        for (Student s : byName) {
            System.out.println("  " + s);
        }

        // ===== REMOVE OPERATIONS =====

        System.out.println("\n===== REMOVE OPERATIONS =====");

        TreeSet<Integer> removeSet = new TreeSet<>(set);

        // remove(Object)
        boolean removed = removeSet.remove(50);
        System.out.println("remove(50): " + removed);

        // removeIf()
        removeSet.removeIf(n -> n < 30);
        System.out.println("After removeIf(< 30): " + removeSet);

        // clear()
        removeSet.clear();
        System.out.println("After clear, isEmpty: " + removeSet.isEmpty());

        // ===== NULL HANDLING =====

        System.out.println("\n===== NULL HANDLING =====");

        System.out.println("TreeSet does NOT allow null elements");
        System.out.println("Adding null throws NullPointerException");
        System.out.println("(Cannot compare null with compareTo/compare)");

        // ===== ITERATION =====

        System.out.println("\n===== ITERATION =====");

        System.out.print("Forward: ");
        for (Integer n : set) {
            System.out.print(n + " ");
        }
        System.out.println();

        System.out.print("Reverse: ");
        for (Integer n : set.descendingSet()) {
            System.out.print(n + " ");
        }
        System.out.println();

        System.out.println("\n===== Demo Complete =====");
        System.out.println("See class Javadoc for comparison table and when-to-use guide.");
    }
}

/**
 * Student class implementing Comparable
 */
class Student implements Comparable<Student> {
    String name;
    int score;

    Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Student other) {
        // Primary: by score (descending)
        int scoreCompare = Integer.compare(other.score, this.score);
        if (scoreCompare != 0) return scoreCompare;
        // Secondary: by name (ascending) - to allow same scores
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name + " (score=" + score + ")";
    }
}

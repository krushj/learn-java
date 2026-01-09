package core.generics;

import java.util.*;

/**
 * GenericsDemo
 *
 * Demonstrates ALL Generics concepts in Java:
 * - Generic classes and methods
 * - Type parameters and bounds
 * - Wildcards (?, extends, super)
 * - Type erasure
 * - Generic interfaces
 *
 * WHY GENERICS?
 * 1. Type safety at compile time
 * 2. Elimination of casts
 * 3. Generic algorithms (work with any type)
 *
 * INTERNAL WORKING:
 * - Type erasure: Generic types removed at compile time
 * - Replaced with Object (or bound type)
 * - Compiler inserts casts as needed
 * - Bridge methods generated for polymorphism
 */
public class GenericsDemo {

    public static void main(String[] args) {

        // ===== WHY GENERICS? =====

        System.out.println("===== WHY GENERICS? =====");

        // Without generics (pre-Java 5)
        List oldList = new ArrayList();
        oldList.add("Hello");
        oldList.add(123);  // No compile-time check!
        // String s = (String) oldList.get(1);  // ClassCastException at runtime!

        // With generics - type safety
        List<String> newList = new ArrayList<>();
        newList.add("Hello");
        // newList.add(123);  // COMPILE ERROR - type mismatch!
        String str = newList.get(0);  // No cast needed
        System.out.println("Type-safe: " + str);

        // ===== GENERIC CLASSES =====

        System.out.println("\n===== GENERIC CLASSES =====");

        // Using generic class with different types
        Box<String> stringBox = new Box<>("Hello");
        System.out.println("String box: " + stringBox.getValue());

        Box<Integer> intBox = new Box<>(42);
        System.out.println("Integer box: " + intBox.getValue());

        Box<List<String>> listBox = new Box<>(Arrays.asList("a", "b", "c"));
        System.out.println("List box: " + listBox.getValue());

        // Multiple type parameters
        Pair<String, Integer> pair = new Pair<>("Age", 25);
        System.out.println("Pair: " + pair.getFirst() + " = " + pair.getSecond());

        // ===== GENERIC METHODS =====

        System.out.println("\n===== GENERIC METHODS =====");

        // Generic method with type inference
        String[] strArr = {"apple", "banana", "cherry"};
        List<String> strList = arrayToList(strArr);
        System.out.println("Array to list: " + strList);

        Integer[] intArr = {1, 2, 3, 4, 5};
        List<Integer> intList = arrayToList(intArr);
        System.out.println("Integer list: " + intList);

        // Generic method with explicit type
        List<String> explicitList = GenericsDemo.<String>arrayToList(strArr);
        System.out.println("Explicit type: " + explicitList);

        // Multiple type parameters in method
        Pair<String, Double> created = createPair("Price", 29.99);
        System.out.println("Created pair: " + created.getFirst() + " = " + created.getSecond());

        // ===== BOUNDED TYPE PARAMETERS =====

        System.out.println("\n===== BOUNDED TYPE PARAMETERS =====");

        // Upper bound: T extends Number
        System.out.println("Sum of integers: " + sum(Arrays.asList(1, 2, 3, 4, 5)));
        System.out.println("Sum of doubles: " + sum(Arrays.asList(1.5, 2.5, 3.5)));
        // sum(Arrays.asList("a", "b"));  // COMPILE ERROR - String not a Number

        // Multiple bounds: T extends A & B & C
        MultiBounded<MultiClass> mb = new MultiBounded<>();
        mb.process(new MultiClass());

        // ===== WILDCARDS =====

        System.out.println("\n===== WILDCARDS =====");

        // Unbounded wildcard: ?
        List<String> strings = Arrays.asList("a", "b", "c");
        List<Integer> integers = Arrays.asList(1, 2, 3);

        printList(strings);
        printList(integers);

        // Upper bounded wildcard: ? extends Type
        // Can READ (as Type), cannot WRITE
        List<Integer> integerList = Arrays.asList(10, 20, 30);
        List<Double> doubleList = Arrays.asList(1.1, 2.2, 3.3);

        System.out.println("Sum integers: " + sumWildcard(integerList));
        System.out.println("Sum doubles: " + sumWildcard(doubleList));

        // Lower bounded wildcard: ? super Type
        // Can WRITE, limited READ (as Object)
        List<Object> objectList = new ArrayList<>();
        List<Number> numberList = new ArrayList<>();

        addNumbers(objectList);  // Object is super of Integer
        addNumbers(numberList);  // Number is super of Integer
        System.out.println("Objects: " + objectList);
        System.out.println("Numbers: " + numberList);

        // ===== PECS PRINCIPLE =====

        System.out.println("\n===== PECS PRINCIPLE =====");

        /*
         * PECS = Producer Extends, Consumer Super
         *
         * If you need to READ from a structure, use "extends"
         * If you need to WRITE to a structure, use "super"
         * If you need both, don't use wildcard
         *
         * Example: Collections.copy(dest, src)
         * void copy(List<? super T> dest, List<? extends T> src)
         *   - src is producer (read from) - extends
         *   - dest is consumer (write to) - super
         */

        List<Number> dest = new ArrayList<>();
        List<Integer> src = Arrays.asList(1, 2, 3);
        copyElements(dest, src);
        System.out.println("Copied: " + dest);

        // ===== GENERIC INTERFACES =====

        System.out.println("\n===== GENERIC INTERFACES =====");

        // Implementing generic interface
        Repository<String> stringRepo = new StringRepository();
        stringRepo.save("Hello");
        System.out.println("Loaded: " + stringRepo.load(0));

        // Anonymous implementation
        Repository<Integer> intRepo = new Repository<Integer>() {
            private List<Integer> data = new ArrayList<>();

            @Override
            public void save(Integer item) {
                data.add(item);
            }

            @Override
            public Integer load(int id) {
                return data.get(id);
            }
        };

        intRepo.save(42);
        System.out.println("Integer repo: " + intRepo.load(0));

        // ===== TYPE ERASURE =====

        System.out.println("\n===== TYPE ERASURE =====");

        /*
         * At compile time:
         * Box<String> box = new Box<>("Hello");
         * String s = box.getValue();
         *
         * After type erasure:
         * Box box = new Box("Hello");
         * String s = (String) box.getValue();
         *
         * Consequences:
         * - Cannot use instanceof with generics
         * - Cannot create generic arrays
         * - Cannot use primitives as type parameters
         */

        Box<String> box1 = new Box<>("a");
        Box<Integer> box2 = new Box<>(1);

        // Both have same class at runtime
        System.out.println("Same class? " +
                          (box1.getClass() == box2.getClass()));  // true

        // Cannot do: if (box1 instanceof Box<String>)
        // Can do:
        if (box1 instanceof Box) {
            System.out.println("box1 is a Box");
        }

        // ===== GENERIC ARRAYS =====

        System.out.println("\n===== GENERIC ARRAYS =====");

        // Cannot create: new T[]
        // Cannot create: new ArrayList<String>[]

        // Workaround 1: Array of raw type with cast
        @SuppressWarnings("unchecked")
        Box<String>[] boxArray = (Box<String>[]) new Box<?>[10];
        boxArray[0] = new Box<>("First");
        System.out.println("Box array[0]: " + boxArray[0].getValue());

        // Workaround 2: Use List instead
        List<Box<String>> boxList = new ArrayList<>();
        boxList.add(new Box<>("Second"));
        System.out.println("Box list[0]: " + boxList.get(0).getValue());

        // ===== RECURSIVE TYPE BOUNDS =====

        System.out.println("\n===== RECURSIVE TYPE BOUNDS =====");

        // Comparable pattern
        List<Person> people = Arrays.asList(
            new Person("Alice", 30),
            new Person("Bob", 25),
            new Person("Charlie", 35)
        );

        Person youngest = findMin(people);
        System.out.println("Youngest: " + youngest.getName());

        // ===== GENERIC CONSTRUCTORS =====

        System.out.println("\n===== GENERIC CONSTRUCTORS =====");

        // Constructor can have its own type parameter
        GenericConstructor gc = new GenericConstructor(Arrays.asList("a", "b"));
        System.out.println("Generic constructor: " + gc.getSize());

        // ===== RAW TYPES =====

        System.out.println("\n===== RAW TYPES =====");

        // Raw type - generic class without type parameter
        // For backward compatibility only - avoid!
        @SuppressWarnings("rawtypes")
        Box rawBox = new Box("Hello");

        @SuppressWarnings("unchecked")
        String rawValue = (String) rawBox.getValue();
        System.out.println("Raw type: " + rawValue);

        // ===== GENERIC BEST PRACTICES =====

        System.out.println("\n===== BEST PRACTICES =====");

        /*
         * 1. Always use parameterized types
         *    Bad:  List list = new ArrayList();
         *    Good: List<String> list = new ArrayList<>();
         *
         * 2. Use diamond operator (<>) for type inference
         *    Map<String, List<Integer>> map = new HashMap<>();
         *
         * 3. Prefer List over arrays for generic collections
         *
         * 4. Use bounded wildcards for API flexibility
         *    void process(List<? extends Number> list)
         *
         * 5. Don't use raw types except for class literals
         *    List.class not List<String>.class
         *
         * 6. Eliminate unchecked warnings when possible
         *    Use @SuppressWarnings("unchecked") only when safe
         */

        System.out.println("See comments for best practices");

        System.out.println("\n===== Demo Complete =====");
    }

    // ===== GENERIC METHODS =====

    // Generic method with type inference
    public static <T> List<T> arrayToList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    // Multiple type parameters
    public static <K, V> Pair<K, V> createPair(K key, V value) {
        return new Pair<>(key, value);
    }

    // Bounded type parameter
    public static <T extends Number> double sum(List<T> list) {
        double sum = 0;
        for (T num : list) {
            sum += num.doubleValue();
        }
        return sum;
    }

    // ===== WILDCARD METHODS =====

    // Unbounded wildcard - read-only
    public static void printList(List<?> list) {
        System.out.print("List: ");
        for (Object obj : list) {
            System.out.print(obj + " ");
        }
        System.out.println();
    }

    // Upper bounded wildcard - producer
    public static double sumWildcard(List<? extends Number> list) {
        double sum = 0;
        for (Number num : list) {  // Can read as Number
            sum += num.doubleValue();
        }
        return sum;
    }

    // Lower bounded wildcard - consumer
    public static void addNumbers(List<? super Integer> list) {
        list.add(1);   // Can add Integer
        list.add(2);
        list.add(3);
        // Integer i = list.get(0);  // Cannot read as Integer (only Object)
    }

    // PECS example
    public static <T> void copyElements(List<? super T> dest, List<? extends T> src) {
        for (T item : src) {
            dest.add(item);
        }
    }

    // Recursive type bound
    public static <T extends Comparable<T>> T findMin(List<T> list) {
        T min = list.get(0);
        for (T item : list) {
            if (item.compareTo(min) < 0) {
                min = item;
            }
        }
        return min;
    }
}

// ===== GENERIC CLASSES =====

// Single type parameter
class Box<T> {
    private T value;

    public Box(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}

// Multiple type parameters
class Pair<K, V> {
    private K first;
    private V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public K getFirst() { return first; }
    public V getSecond() { return second; }
}

// Bounded type parameter
class NumberBox<T extends Number> {
    private T value;

    public NumberBox(T value) {
        this.value = value;
    }

    public double doubleValue() {
        return value.doubleValue();  // Can call Number methods
    }
}

// Multiple bounds
interface Printable {
    void print();
}

class MultiClass extends Number implements Printable, Comparable<MultiClass> {
    @Override
    public void print() {
        System.out.println("Printing MultiClass");
    }

    @Override
    public int compareTo(MultiClass o) {
        return 0;
    }

    @Override public int intValue() { return 0; }
    @Override public long longValue() { return 0; }
    @Override public float floatValue() { return 0; }
    @Override public double doubleValue() { return 0; }
}

class MultiBounded<T extends Number & Printable & Comparable<T>> {
    public void process(T item) {
        System.out.println("Processing with multiple bounds");
        double d = item.doubleValue();  // Number method
        item.print();                    // Printable method
    }
}

// ===== GENERIC INTERFACE =====

interface Repository<T> {
    void save(T item);
    T load(int id);
}

class StringRepository implements Repository<String> {
    private List<String> data = new ArrayList<>();

    @Override
    public void save(String item) {
        data.add(item);
    }

    @Override
    public String load(int id) {
        return data.get(id);
    }
}

// ===== COMPARABLE PATTERN =====

class Person implements Comparable<Person> {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public int compareTo(Person other) {
        return Integer.compare(this.age, other.age);
    }
}

// ===== GENERIC CONSTRUCTOR =====

class GenericConstructor {
    private int size;

    public <T> GenericConstructor(List<T> list) {
        this.size = list.size();
    }

    public int getSize() {
        return size;
    }
}

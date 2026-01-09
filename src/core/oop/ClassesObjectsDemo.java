package core.oop;

/**
 * ClassesObjectsDemo
 *
 * Demonstrates Classes and Objects in Java:
 * - Class definition and components
 * - Object creation and lifecycle
 * - Instance vs Class members
 * - Access modifiers
 * - this keyword
 *
 * INTERNAL WORKING:
 * - Class loaded into Method Area when first referenced
 * - Object created on Heap via 'new' keyword
 * - Reference stored on Stack (for local vars)
 * - Garbage collected when no references remain
 *
 * CLASS STRUCTURE:
 * ┌─────────────────────────────────────┐
 * │            CLASS                    │
 * ├─────────────────────────────────────┤
 * │  Fields (Instance & Static)         │
 * │  Constructors                       │
 * │  Methods (Instance & Static)        │
 * │  Nested Classes                     │
 * │  Initializer Blocks                 │
 * └─────────────────────────────────────┘
 */
public class ClassesObjectsDemo {

    public static void main(String[] args) {

        // ===== OBJECT CREATION =====

        System.out.println("===== OBJECT CREATION =====");

        // Create objects using 'new' keyword
        // new allocates memory on heap
        // Constructor initializes the object
        Person person1 = new Person();
        Person person2 = new Person("Alice", 25);
        Person person3 = new Person("Bob", 30, "bob@email.com");

        System.out.println("Created 3 Person objects");

        // ===== ACCESSING MEMBERS =====

        System.out.println("\n===== ACCESSING MEMBERS =====");

        // Access instance variables (through object reference)
        System.out.println("person2 name: " + person2.getName());
        System.out.println("person2 age: " + person2.getAge());

        // Modify instance variables
        person2.setAge(26);
        System.out.println("person2 age after update: " + person2.getAge());

        // Access static variables (through class name - recommended)
        System.out.println("Total persons: " + Person.getPopulation());

        // Can also access static through object (not recommended)
        System.out.println("Via object: " + person1.getPopulation());

        // ===== OBJECT REFERENCES =====

        System.out.println("\n===== OBJECT REFERENCES =====");

        // Multiple references to same object
        Person ref1 = person1;
        Person ref2 = person1;

        System.out.println("ref1 == ref2: " + (ref1 == ref2));  // true
        System.out.println("ref1 == person1: " + (ref1 == person1));  // true

        // Modifying through one reference affects all
        ref1.setName("Charlie");
        System.out.println("person1 name: " + person1.getName());  // Charlie

        // Null reference
        Person nullRef = null;
        // nullRef.getName();  // NullPointerException!

        // ===== THIS KEYWORD =====

        System.out.println("\n===== THIS KEYWORD =====");

        // 'this' refers to current object
        Person thisPerson = new Person("Diana", 28);
        thisPerson.printDetails();

        // Method chaining using 'this'
        Person chainPerson = new Person();
        chainPerson.setName("Eve").setAge(22).setEmail("eve@email.com");
        chainPerson.printDetails();

        // ===== OBJECT COMPARISON =====

        System.out.println("\n===== OBJECT COMPARISON =====");

        Person p1 = new Person("Frank", 35);
        Person p2 = new Person("Frank", 35);
        Person p3 = p1;

        // == compares references (memory addresses)
        System.out.println("p1 == p2: " + (p1 == p2));  // false (different objects)
        System.out.println("p1 == p3: " + (p1 == p3));  // true (same reference)

        // equals() compares content (if overridden properly)
        System.out.println("p1.equals(p2): " + p1.equals(p2));  // true (same content)

        // hashCode() for equal objects
        System.out.println("p1.hashCode(): " + p1.hashCode());
        System.out.println("p2.hashCode(): " + p2.hashCode());

        // ===== OBJECT METHODS =====

        System.out.println("\n===== OBJECT METHODS =====");

        Person objPerson = new Person("Grace", 40);

        // toString() - string representation
        System.out.println("toString(): " + objPerson.toString());
        System.out.println("Implicit toString(): " + objPerson);

        // getClass() - runtime class info
        System.out.println("getClass(): " + objPerson.getClass());
        System.out.println("Class name: " + objPerson.getClass().getName());
        System.out.println("Simple name: " + objPerson.getClass().getSimpleName());

        // ===== STATIC CONTEXT =====

        System.out.println("\n===== STATIC CONTEXT =====");

        // Static methods called via class name
        Person.printPopulation();

        // Static variable shared across all instances
        System.out.println("Population before: " + Person.getPopulation());
        new Person("Temp1", 20);
        new Person("Temp2", 21);
        System.out.println("Population after: " + Person.getPopulation());

        // Static initializer runs once when class loads
        System.out.println("Static constant: " + Person.SPECIES);

        // ===== INSTANCE INITIALIZER BLOCKS =====

        System.out.println("\n===== INITIALIZER BLOCKS =====");

        // Instance initializer runs for each object creation
        InitializerDemo initDemo = new InitializerDemo();
        System.out.println("Check console for initialization order");

        // ===== OBJECT LIFECYCLE =====

        System.out.println("\n===== OBJECT LIFECYCLE =====");

        /*
         * Object Lifecycle:
         * 1. Class Loading - Class loaded into Method Area
         * 2. Memory Allocation - 'new' allocates memory on Heap
         * 3. Initialization - Constructor runs
         * 4. Usage - Object is used via references
         * 5. Dereferencing - No more references point to object
         * 6. Garbage Collection - JVM reclaims memory
         */

        Person temp = new Person("Temporary", 1);
        temp.printDetails();
        temp = null;  // Object eligible for garbage collection
        System.out.println("Object dereferenced - eligible for GC");

        // Request garbage collection (not guaranteed to run immediately)
        System.gc();

        // ===== NESTED CLASSES DEMO =====

        System.out.println("\n===== NESTED CLASSES =====");

        // Inner class (non-static) - needs outer class instance
        OuterClass outer = new OuterClass();
        OuterClass.InnerClass inner = outer.new InnerClass();
        inner.display();

        // Static nested class - doesn't need outer instance
        OuterClass.StaticNestedClass staticNested = new OuterClass.StaticNestedClass();
        staticNested.display();

        // Local class (defined in method) and Anonymous class
        outer.demonstrateLocalAndAnonymous();

        System.out.println("\n===== Demo Complete =====");
    }
}

/**
 * Person class demonstrating class components
 */
class Person {

    // ===== STATIC VARIABLES (Class Variables) =====
    // Shared across all instances
    // Stored in Method Area

    private static int population = 0;
    public static final String SPECIES = "Homo Sapiens";

    // ===== INSTANCE VARIABLES (Fields) =====
    // Each object has its own copy
    // Stored on Heap with object

    private String name;
    private int age;
    private String email;

    // ===== STATIC INITIALIZER =====
    // Runs once when class is loaded
    static {
        System.out.println("[Static Block] Person class loaded");
    }

    // ===== INSTANCE INITIALIZER =====
    // Runs before each constructor
    {
        System.out.println("[Instance Block] Creating new Person");
    }

    // ===== CONSTRUCTORS =====

    // No-arg constructor
    public Person() {
        this.name = "Unknown";
        this.age = 0;
        this.email = "";
        population++;
    }

    // Parameterized constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        this.email = "";
        population++;
    }

    // Constructor with all fields
    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
        population++;
    }

    // ===== GETTER METHODS =====

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    // ===== SETTER METHODS (with chaining) =====

    public Person setName(String name) {
        this.name = name;
        return this;  // Enable method chaining
    }

    public Person setAge(int age) {
        if (age >= 0) {
            this.age = age;
        }
        return this;
    }

    public Person setEmail(String email) {
        this.email = email;
        return this;
    }

    // ===== STATIC METHODS =====

    public static int getPopulation() {
        return population;
    }

    public static void printPopulation() {
        System.out.println("Total population: " + population);
        // Cannot access instance variables here
        // System.out.println(name);  // COMPILE ERROR
    }

    // ===== INSTANCE METHODS =====

    public void printDetails() {
        System.out.println("Person: name=" + this.name +
                          ", age=" + this.age +
                          ", email=" + this.email);
    }

    // ===== OBJECT CLASS OVERRIDES =====

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", email='" + email + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return age == person.age &&
               name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + age;
    }
}

/**
 * Demonstrates initialization order
 */
class InitializerDemo {

    // Static variable
    private static int staticVar;

    // Instance variable
    private int instanceVar;

    // Static initializer (runs once when class loads)
    static {
        staticVar = 10;
        System.out.println("1. Static initializer: staticVar = " + staticVar);
    }

    // Instance initializer (runs for each object)
    {
        instanceVar = 20;
        System.out.println("2. Instance initializer: instanceVar = " + instanceVar);
    }

    // Constructor
    public InitializerDemo() {
        System.out.println("3. Constructor: instanceVar = " + instanceVar);
        instanceVar = 30;
        System.out.println("   After constructor: instanceVar = " + instanceVar);
    }
}

/**
 * Demonstrates nested classes
 */
class OuterClass {

    private int outerField = 100;
    private static int staticOuterField = 200;

    // Inner class (non-static nested class)
    // Has access to outer class instance members
    class InnerClass {
        public void display() {
            System.out.println("Inner class - outerField: " + outerField);
            System.out.println("Inner class - staticOuterField: " + staticOuterField);
        }
    }

    // Static nested class
    // Only has access to outer class static members
    static class StaticNestedClass {
        public void display() {
            // System.out.println(outerField);  // COMPILE ERROR
            System.out.println("Static nested - staticOuterField: " + staticOuterField);
        }
    }

    // Method demonstrating local and anonymous classes
    public void demonstrateLocalAndAnonymous() {

        // Local class (defined inside method)
        class LocalClass {
            public void display() {
                System.out.println("Local class - outerField: " + outerField);
            }
        }

        LocalClass local = new LocalClass();
        local.display();

        // Anonymous class (implementing interface inline)
        Runnable anonymous = new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous class - outerField: " + outerField);
            }
        };
        anonymous.run();
    }
}

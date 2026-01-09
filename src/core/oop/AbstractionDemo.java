package core.oop;

/**
 * AbstractionDemo
 *
 * Demonstrates Abstraction in Java:
 * - Abstract Classes
 * - Interfaces
 * - When to use which
 * - Default and Static methods in interfaces (Java 8+)
 * - Multiple inheritance through interfaces
 *
 * ABSTRACTION = Hiding implementation details, showing only functionality
 *
 * INTERNAL WORKING:
 * - Abstract classes can have constructors (for child initialization)
 * - Abstract methods have no body, must be overridden
 * - Interfaces create contracts for implementing classes
 * - Default methods provide backward compatibility
 */
public class AbstractionDemo {

    public static void main(String[] args) {

        // ===== ABSTRACT CLASSES =====

        System.out.println("===== ABSTRACT CLASSES =====");

        // Cannot instantiate abstract class
        // Employee emp = new Employee("John");  // COMPILE ERROR

        // Must use concrete implementation
        Employee manager = new Manager("Alice", 50000, "IT");
        Employee developer = new Developer("Bob", 45000, "Java");

        manager.work();
        manager.displayInfo();
        System.out.println("Manager bonus: " + manager.calculateBonus());

        System.out.println();

        developer.work();
        developer.displayInfo();
        System.out.println("Developer bonus: " + developer.calculateBonus());

        // ===== INTERFACES =====

        System.out.println("\n===== INTERFACES =====");

        // Interface reference, implementation object
        Playable guitar = new Guitar("Fender");
        Playable piano = new Piano("Yamaha");

        guitar.play();
        guitar.stop();
        System.out.println();

        piano.play();
        piano.stop();

        // ===== MULTIPLE INTERFACE IMPLEMENTATION =====

        System.out.println("\n===== MULTIPLE INTERFACES =====");

        // A class can implement multiple interfaces
        SmartPhone phone = new SmartPhone("iPhone");

        // As Callable
        phone.call("123-456-7890");
        phone.receiveCall();

        // As Messageable
        phone.sendMessage("Hello!");
        phone.receiveMessage();

        // As Browsable
        phone.openBrowser();
        phone.search("Java tutorials");

        // ===== DEFAULT METHODS (Java 8+) =====

        System.out.println("\n===== DEFAULT METHODS =====");

        // Default methods provide implementation in interface
        Vehicle2 car = new Car2();
        car.start();
        car.stop();
        car.honk();  // Default method from interface

        // ===== STATIC METHODS IN INTERFACES =====

        System.out.println("\n===== STATIC METHODS IN INTERFACES =====");

        // Static methods called on interface name
        Vehicle2.printVehicleInfo();
        Playable.getInstrumentCount();

        // ===== FUNCTIONAL INTERFACES =====

        System.out.println("\n===== FUNCTIONAL INTERFACES =====");

        // Interface with single abstract method
        // Can be used with lambda expressions

        Calculator2 add = (a, b) -> a + b;
        Calculator2 subtract = (a, b) -> a - b;
        Calculator2 multiply = (a, b) -> a * b;

        System.out.println("10 + 5 = " + add.calculate(10, 5));
        System.out.println("10 - 5 = " + subtract.calculate(10, 5));
        System.out.println("10 * 5 = " + multiply.calculate(10, 5));

        // ===== ABSTRACT CLASS VS INTERFACE =====

        System.out.println("\n===== ABSTRACT CLASS VS INTERFACE =====");

        printComparison();

        // ===== MARKER INTERFACES =====

        System.out.println("\n===== MARKER INTERFACES =====");

        /*
         * Marker interfaces have no methods
         * Used to provide runtime type information
         * Examples: Serializable, Cloneable, Remote
         */

        SerializableEntity entity = new SerializableEntity();
        if (entity instanceof java.io.Serializable) {
            System.out.println("Entity is serializable");
        }

        // ===== INTERFACE INHERITANCE =====

        System.out.println("\n===== INTERFACE INHERITANCE =====");

        // Interfaces can extend other interfaces
        AdvancedVehicle av = new ElectricCar();
        av.start();
        av.stop();
        av.charge();
        av.enableAutoPilot();

        // ===== DIAMOND PROBLEM SOLUTION =====

        System.out.println("\n===== DIAMOND PROBLEM =====");

        // Java handles diamond problem through interfaces
        DiamondDemo dd = new DiamondDemo();
        dd.commonMethod();  // Must override if conflict exists

        System.out.println("\n===== Demo Complete =====");
    }

    static void printComparison() {
        String comparison =
            "┌───────────────────────────────────────────────────────────────────┐\n" +
            "│           ABSTRACT CLASS vs INTERFACE                            │\n" +
            "├─────────────────────────────┬─────────────────────────────────────┤\n" +
            "│       ABSTRACT CLASS        │           INTERFACE                 │\n" +
            "├─────────────────────────────┼─────────────────────────────────────┤\n" +
            "│ Can have constructors       │ Cannot have constructors           │\n" +
            "│ Can have instance variables │ Only constants (public static final)│\n" +
            "│ Can have any access modifier│ Methods public by default          │\n" +
            "│ Single inheritance          │ Multiple inheritance               │\n" +
            "│ Can have concrete methods   │ All methods abstract (pre-Java 8)  │\n" +
            "│ extends keyword             │ implements keyword                  │\n" +
            "│ IS-A relationship           │ CAN-DO relationship                │\n" +
            "│ Partial abstraction         │ Full abstraction (pre-Java 8)     │\n" +
            "├─────────────────────────────┴─────────────────────────────────────┤\n" +
            "│ WHEN TO USE:                                                      │\n" +
            "│ Abstract class: Share code among related classes                 │\n" +
            "│ Interface: Define contract for unrelated classes                 │\n" +
            "└───────────────────────────────────────────────────────────────────┘";
        System.out.println(comparison);
    }
}

// ===== ABSTRACT CLASS =====

abstract class Employee {
    protected String name;
    protected double salary;

    // Abstract class CAN have constructor
    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    // Abstract method - no body, must be overridden
    public abstract void work();

    // Abstract method with return type
    public abstract double calculateBonus();

    // Concrete method - has implementation
    public void displayInfo() {
        System.out.println("Name: " + name + ", Salary: $" + salary);
    }

    // Getters
    public String getName() { return name; }
    public double getSalary() { return salary; }
}

class Manager extends Employee {
    private String department;

    public Manager(String name, double salary, String department) {
        super(name, salary);  // Call abstract class constructor
        this.department = department;
    }

    @Override
    public void work() {
        System.out.println(name + " is managing the " + department + " team");
    }

    @Override
    public double calculateBonus() {
        return salary * 0.20;  // 20% bonus
    }
}

class Developer extends Employee {
    private String language;

    public Developer(String name, double salary, String language) {
        super(name, salary);
        this.language = language;
    }

    @Override
    public void work() {
        System.out.println(name + " is coding in " + language);
    }

    @Override
    public double calculateBonus() {
        return salary * 0.15;  // 15% bonus
    }
}

// ===== INTERFACES =====

interface Playable {
    // Constants (implicitly public static final)
    int MAX_VOLUME = 100;

    // Abstract methods (implicitly public abstract)
    void play();
    void stop();

    // Default method (Java 8+)
    default void pause() {
        System.out.println("Pausing...");
    }

    // Static method (Java 8+)
    static void getInstrumentCount() {
        System.out.println("Instrument count: varies by implementation");
    }
}

class Guitar implements Playable {
    private String brand;

    public Guitar(String brand) {
        this.brand = brand;
    }

    @Override
    public void play() {
        System.out.println("Playing " + brand + " guitar: 🎸");
    }

    @Override
    public void stop() {
        System.out.println("Guitar stopped");
    }
}

class Piano implements Playable {
    private String brand;

    public Piano(String brand) {
        this.brand = brand;
    }

    @Override
    public void play() {
        System.out.println("Playing " + brand + " piano: 🎹");
    }

    @Override
    public void stop() {
        System.out.println("Piano stopped");
    }
}

// ===== MULTIPLE INTERFACE IMPLEMENTATION =====

interface Callable {
    void call(String number);
    void receiveCall();
}

interface Messageable {
    void sendMessage(String msg);
    void receiveMessage();
}

interface Browsable {
    void openBrowser();
    void search(String query);
}

class SmartPhone implements Callable, Messageable, Browsable {
    private String model;

    public SmartPhone(String model) {
        this.model = model;
    }

    @Override
    public void call(String number) {
        System.out.println(model + " calling " + number);
    }

    @Override
    public void receiveCall() {
        System.out.println(model + " receiving call");
    }

    @Override
    public void sendMessage(String msg) {
        System.out.println(model + " sending: " + msg);
    }

    @Override
    public void receiveMessage() {
        System.out.println(model + " received message");
    }

    @Override
    public void openBrowser() {
        System.out.println(model + " opening browser");
    }

    @Override
    public void search(String query) {
        System.out.println(model + " searching: " + query);
    }
}

// ===== DEFAULT METHODS =====

interface Vehicle2 {
    void start();
    void stop();

    // Default method - provides default implementation
    default void honk() {
        System.out.println("Beep! Beep!");
    }

    // Static method
    static void printVehicleInfo() {
        System.out.println("Vehicle interface - defines vehicle behavior");
    }
}

class Car2 implements Vehicle2 {
    @Override
    public void start() {
        System.out.println("Car starting with key");
    }

    @Override
    public void stop() {
        System.out.println("Car stopping");
    }

    // Can override default method if needed
    // @Override
    // public void honk() {
    //     System.out.println("Car horn: HONK!");
    // }
}

// ===== FUNCTIONAL INTERFACE =====

@FunctionalInterface
interface Calculator2 {
    int calculate(int a, int b);

    // Can have default methods
    default void printResult(int result) {
        System.out.println("Result: " + result);
    }

    // Can have static methods
    static void printInfo() {
        System.out.println("Calculator functional interface");
    }
}

// ===== MARKER INTERFACE =====

class SerializableEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String data;

    public SerializableEntity() {
        this.data = "Sample data";
    }
}

// ===== INTERFACE INHERITANCE =====

interface Chargeable {
    void charge();
}

interface AutoPilot {
    void enableAutoPilot();
}

// Interface extending multiple interfaces
interface AdvancedVehicle extends Vehicle2, Chargeable, AutoPilot {
    // Inherits all methods from parent interfaces
}

class ElectricCar implements AdvancedVehicle {
    @Override
    public void start() {
        System.out.println("Electric car starting silently");
    }

    @Override
    public void stop() {
        System.out.println("Electric car stopping with regenerative braking");
    }

    @Override
    public void charge() {
        System.out.println("Charging battery...");
    }

    @Override
    public void enableAutoPilot() {
        System.out.println("Auto-pilot enabled");
    }
}

// ===== DIAMOND PROBLEM =====

interface InterfaceA {
    default void commonMethod() {
        System.out.println("InterfaceA commonMethod");
    }
}

interface InterfaceB {
    default void commonMethod() {
        System.out.println("InterfaceB commonMethod");
    }
}

// Must override to resolve diamond problem
class DiamondDemo implements InterfaceA, InterfaceB {
    @Override
    public void commonMethod() {
        // Can call specific interface's method
        InterfaceA.super.commonMethod();
        // Or provide own implementation
        System.out.println("DiamondDemo's resolution of diamond problem");
    }
}

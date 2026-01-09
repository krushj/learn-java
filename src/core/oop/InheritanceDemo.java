package core.oop;

/**
 * InheritanceDemo
 *
 * Demonstrates Inheritance concepts in Java:
 * - Single inheritance (extends)
 * - Method overriding
 * - super keyword
 * - Constructor chaining
 * - IS-A relationship
 * - Types of inheritance
 *
 * INTERNAL WORKING:
 * - Child class inherits all non-private members from parent
 * - Method dispatch uses vtable (virtual method table)
 * - super() calls parent constructor (first statement)
 * - Object class is root of all inheritance
 *
 * TYPES OF INHERITANCE:
 * 1. Single       - One parent, one child
 * 2. Multilevel   - A → B → C (chain)
 * 3. Hierarchical - One parent, multiple children
 * 4. Multiple     - NOT supported with classes (use interfaces)
 * 5. Hybrid       - Combination (achieved via interfaces)
 */
public class InheritanceDemo {

    public static void main(String[] args) {

        // ===== SINGLE INHERITANCE =====

        System.out.println("===== SINGLE INHERITANCE =====");

        // Child extends Parent
        Dog dog = new Dog("Buddy", 3, "Golden Retriever");
        dog.eat();       // Inherited from Animal
        dog.sleep();     // Inherited from Animal
        dog.bark();      // Dog's own method
        dog.displayInfo();

        System.out.println();

        Cat cat = new Cat("Whiskers", 2, true);
        cat.eat();       // Overridden method
        cat.sleep();     // Inherited from Animal
        cat.meow();      // Cat's own method

        // ===== IS-A RELATIONSHIP =====

        System.out.println("\n===== IS-A RELATIONSHIP =====");

        // Dog IS-A Animal
        Animal animal1 = new Dog("Rex", 4, "German Shepherd");
        Animal animal2 = new Cat("Mittens", 1, false);

        // Reference type determines available methods
        animal1.eat();   // OK - Animal method
        // animal1.bark();  // COMPILE ERROR - Animal reference

        // Cast to access child-specific methods
        if (animal1 instanceof Dog) {
            Dog d = (Dog) animal1;
            d.bark();
        }

        // instanceof check
        System.out.println("animal1 instanceof Dog: " + (animal1 instanceof Dog));
        System.out.println("animal1 instanceof Animal: " + (animal1 instanceof Animal));
        System.out.println("animal1 instanceof Object: " + (animal1 instanceof Object));

        // ===== METHOD OVERRIDING =====

        System.out.println("\n===== METHOD OVERRIDING =====");

        Animal genericAnimal = new Animal("Generic", 1);
        Animal dogAsAnimal = new Dog("Bruno", 5, "Labrador");
        Animal catAsAnimal = new Cat("Luna", 3, true);

        // Runtime polymorphism - actual method depends on object type
        System.out.print("Generic animal: ");
        genericAnimal.eat();

        System.out.print("Dog as Animal: ");
        dogAsAnimal.eat();  // Calls Dog's overridden eat()

        System.out.print("Cat as Animal: ");
        catAsAnimal.eat();  // Calls Cat's overridden eat()

        // ===== SUPER KEYWORD =====

        System.out.println("\n===== SUPER KEYWORD =====");

        SuperDemo child = new SuperDemo("ChildName", 100);
        child.display();
        child.callParentMethod();

        // ===== CONSTRUCTOR CHAINING =====

        System.out.println("\n===== CONSTRUCTOR CHAINING =====");

        System.out.println("Creating GrandChild...");
        GrandChild gc = new GrandChild("GC Name");
        System.out.println("GrandChild created with name: " + gc.getName());

        // ===== MULTILEVEL INHERITANCE =====

        System.out.println("\n===== MULTILEVEL INHERITANCE =====");

        // GrandParent → Parent → Child
        GrandChild grandChild = new GrandChild("Junior");
        grandChild.grandParentMethod();  // From GrandParent
        grandChild.parentMethod();       // From ParentClass
        grandChild.childMethod();        // Own method

        // ===== HIERARCHICAL INHERITANCE =====

        System.out.println("\n===== HIERARCHICAL INHERITANCE =====");

        // One parent, multiple children
        Vehicle car = new Car("Toyota", 4);
        Vehicle motorcycle = new Motorcycle("Honda", true);

        car.start();
        car.displayInfo();

        motorcycle.start();
        motorcycle.displayInfo();

        // ===== WHAT'S NOT INHERITED =====

        System.out.println("\n===== WHAT'S NOT INHERITED =====");

        /*
         * NOT inherited by child class:
         * 1. Private members (accessible via public methods)
         * 2. Constructors (but can be called via super())
         * 3. Static members (belong to class, not inherited but accessible)
         *
         * INHERITED:
         * 1. Public members
         * 2. Protected members
         * 3. Default (package-private) if same package
         */

        InheritanceRules rules = new InheritanceRules();
        rules.demonstrateAccess();

        // ===== FINAL KEYWORD IN INHERITANCE =====

        System.out.println("\n===== FINAL IN INHERITANCE =====");

        /*
         * final class - Cannot be extended
         * final method - Cannot be overridden
         * final variable - Cannot be reassigned
         */

        FinalDemo finalDemo = new FinalDemo();
        finalDemo.printConstant();
        // class CannotExtend extends FinalClass {} // COMPILE ERROR

        // ===== OBJECT CLASS =====

        System.out.println("\n===== OBJECT CLASS (Root) =====");

        /*
         * Every class implicitly extends Object
         * Object provides:
         * - toString()
         * - equals()
         * - hashCode()
         * - getClass()
         * - clone() (protected)
         * - finalize() (deprecated)
         * - wait(), notify(), notifyAll()
         */

        Dog d = new Dog("Max", 2, "Beagle");
        System.out.println("toString: " + d.toString());
        System.out.println("hashCode: " + d.hashCode());
        System.out.println("getClass: " + d.getClass().getName());

        System.out.println("\n===== Demo Complete =====");
    }
}

// ===== BASE CLASS =====

class Animal {
    protected String name;
    protected int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("Animal constructor called");
    }

    public void eat() {
        System.out.println(name + " is eating");
    }

    public void sleep() {
        System.out.println(name + " is sleeping");
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void displayInfo() {
        System.out.println("Animal: " + name + ", Age: " + age);
    }
}

// ===== CHILD CLASS =====

class Dog extends Animal {
    private String breed;

    public Dog(String name, int age, String breed) {
        super(name, age);  // Call parent constructor
        this.breed = breed;
        System.out.println("Dog constructor called");
    }

    // Dog-specific method
    public void bark() {
        System.out.println(name + " is barking: Woof! Woof!");
    }

    // Override parent method
    @Override
    public void eat() {
        System.out.println(name + " the dog is eating dog food");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();  // Call parent method
        System.out.println("Breed: " + breed);
    }

    public String getBreed() {
        return breed;
    }
}

class Cat extends Animal {
    private boolean isIndoor;

    public Cat(String name, int age, boolean isIndoor) {
        super(name, age);
        this.isIndoor = isIndoor;
    }

    public void meow() {
        System.out.println(name + " is meowing: Meow!");
    }

    @Override
    public void eat() {
        System.out.println(name + " the cat is eating cat food");
    }

    public boolean isIndoor() {
        return isIndoor;
    }
}

// ===== SUPER KEYWORD DEMO =====

class SuperParent {
    protected int value = 10;

    public SuperParent() {
        System.out.println("SuperParent no-arg constructor");
    }

    public SuperParent(int value) {
        this.value = value;
        System.out.println("SuperParent parameterized constructor");
    }

    public void display() {
        System.out.println("SuperParent value: " + value);
    }
}

class SuperDemo extends SuperParent {
    private int value = 20;  // Shadows parent's value

    public SuperDemo(String name, int value) {
        super(value);  // Call parent constructor
        System.out.println("SuperDemo constructor");
    }

    @Override
    public void display() {
        System.out.println("Child value: " + value);        // 20
        System.out.println("Parent value: " + super.value); // From constructor
    }

    public void callParentMethod() {
        super.display();  // Call parent's display()
    }
}

// ===== MULTILEVEL INHERITANCE =====

class GrandParent {
    protected String name;

    public GrandParent() {
        System.out.println("GrandParent constructor");
    }

    public void grandParentMethod() {
        System.out.println("Method from GrandParent");
    }
}

class ParentClass extends GrandParent {

    public ParentClass() {
        super();  // Calls GrandParent constructor
        System.out.println("ParentClass constructor");
    }

    public void parentMethod() {
        System.out.println("Method from ParentClass");
    }
}

class GrandChild extends ParentClass {

    public GrandChild(String name) {
        super();  // Calls ParentClass constructor
        this.name = name;
        System.out.println("GrandChild constructor");
    }

    public void childMethod() {
        System.out.println("Method from GrandChild");
    }

    public String getName() {
        return name;
    }
}

// ===== HIERARCHICAL INHERITANCE =====

class Vehicle {
    protected String brand;

    public Vehicle(String brand) {
        this.brand = brand;
    }

    public void start() {
        System.out.println(brand + " vehicle starting...");
    }

    public void displayInfo() {
        System.out.println("Brand: " + brand);
    }
}

class Car extends Vehicle {
    private int doors;

    public Car(String brand, int doors) {
        super(brand);
        this.doors = doors;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Type: Car, Doors: " + doors);
    }
}

class Motorcycle extends Vehicle {
    private boolean hasSidecar;

    public Motorcycle(String brand, boolean hasSidecar) {
        super(brand);
        this.hasSidecar = hasSidecar;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Type: Motorcycle, Sidecar: " + hasSidecar);
    }
}

// ===== INHERITANCE RULES =====

class BaseClass {
    public int publicVar = 1;
    protected int protectedVar = 2;
    int defaultVar = 3;        // Package-private
    private int privateVar = 4;

    public int getPrivateVar() {
        return privateVar;
    }
}

class InheritanceRules extends BaseClass {

    public void demonstrateAccess() {
        System.out.println("publicVar: " + publicVar);       // ✓ Inherited
        System.out.println("protectedVar: " + protectedVar); // ✓ Inherited
        System.out.println("defaultVar: " + defaultVar);     // ✓ Same package
        // System.out.println(privateVar);                   // ✗ Not inherited
        System.out.println("privateVar via method: " + getPrivateVar()); // ✓ Via public method
    }
}

// ===== FINAL CLASS =====

final class FinalClass {
    // This class cannot be extended
    public void method() {
        System.out.println("Final class method");
    }
}

class FinalDemo {
    public static final double PI = 3.14159;  // Constant

    public final void cannotOverride() {
        System.out.println("This method cannot be overridden");
    }

    public void printConstant() {
        System.out.println("PI = " + PI);
    }
}

// Cannot extend FinalClass
// class InvalidChild extends FinalClass {} // COMPILE ERROR

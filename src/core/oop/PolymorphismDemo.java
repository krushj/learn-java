package core.oop;

/**
 * PolymorphismDemo
 *
 * Demonstrates Polymorphism in Java:
 * - Compile-time (Static) Polymorphism - Method Overloading
 * - Runtime (Dynamic) Polymorphism - Method Overriding
 * - Upcasting and Downcasting
 * - Virtual method invocation
 *
 * INTERNAL WORKING:
 * - Overloading: Resolved at compile-time by method signature
 * - Overriding: Resolved at runtime using vtable (virtual method table)
 * - Each class has a vtable with method addresses
 * - JVM looks up actual object's vtable at runtime
 *
 * POLYMORPHISM = "Many Forms"
 * Same interface/method, different implementations
 */
public class PolymorphismDemo {

    public static void main(String[] args) {

        // ===== COMPILE-TIME POLYMORPHISM (OVERLOADING) =====

        System.out.println("===== COMPILE-TIME POLYMORPHISM =====");
        System.out.println("(Method Overloading)\n");

        Calculator calc = new Calculator();

        // Same method name, different parameters
        // Compiler decides which method to call based on arguments
        System.out.println("add(5, 10) = " + calc.add(5, 10));
        System.out.println("add(5, 10, 15) = " + calc.add(5, 10, 15));
        System.out.println("add(5.5, 10.5) = " + calc.add(5.5, 10.5));
        System.out.println("add(\"Hello\", \"World\") = " + calc.add("Hello", "World"));

        // Overloading with different parameter order
        calc.display(10, "Ten");
        calc.display("Ten", 10);

        // ===== RUNTIME POLYMORPHISM (OVERRIDING) =====

        System.out.println("\n===== RUNTIME POLYMORPHISM =====");
        System.out.println("(Method Overriding)\n");

        // Parent reference, child object
        Shape shape1 = new Circle(5);
        Shape shape2 = new Rectangle(4, 6);
        Shape shape3 = new Triangle(3, 4);

        // Same method call, different behavior
        // JVM determines actual method at runtime
        System.out.println("Circle area: " + shape1.calculateArea());
        System.out.println("Rectangle area: " + shape2.calculateArea());
        System.out.println("Triangle area: " + shape3.calculateArea());

        System.out.println();

        shape1.draw();  // Calls Circle's draw()
        shape2.draw();  // Calls Rectangle's draw()
        shape3.draw();  // Calls Triangle's draw()

        // ===== POLYMORPHISM WITH ARRAYS/COLLECTIONS =====

        System.out.println("\n===== POLYMORPHISM WITH ARRAYS =====");

        Shape[] shapes = {
            new Circle(3),
            new Rectangle(5, 3),
            new Triangle(4, 5),
            new Circle(7)
        };

        // Same code handles different types
        double totalArea = 0;
        for (Shape shape : shapes) {
            System.out.println(shape.getClass().getSimpleName() +
                             " - Area: " + shape.calculateArea());
            totalArea += shape.calculateArea();
        }
        System.out.println("Total area: " + totalArea);

        // ===== UPCASTING =====

        System.out.println("\n===== UPCASTING =====");

        // Child to Parent reference (implicit, always safe)
        Circle circle = new Circle(10);
        Shape shapeRef = circle;  // Upcasting - automatic

        System.out.println("Circle upcasted to Shape");
        System.out.println("Can call Shape methods: area = " + shapeRef.calculateArea());
        // shapeRef.getRadius();  // COMPILE ERROR - Shape doesn't know about radius

        // ===== DOWNCASTING =====

        System.out.println("\n===== DOWNCASTING =====");

        // Parent to Child reference (explicit, can fail)
        Shape shapeVar = new Circle(8);  // Actually a Circle

        // Must cast explicitly
        if (shapeVar instanceof Circle) {
            Circle circleVar = (Circle) shapeVar;  // Downcasting
            System.out.println("Downcast successful - radius: " + circleVar.getRadius());
        }

        // ClassCastException if wrong type
        Shape rectShape = new Rectangle(3, 4);
        try {
            Circle wrongCast = (Circle) rectShape;  // Will fail!
            System.out.println(wrongCast);
        } catch (ClassCastException e) {
            System.out.println("ClassCastException: Cannot cast Rectangle to Circle");
        }

        // ===== VIRTUAL METHOD INVOCATION =====

        System.out.println("\n===== VIRTUAL METHOD INVOCATION =====");

        /*
         * How JVM resolves overridden methods:
         * 1. Check actual object type (not reference type)
         * 2. Look up method in object's vtable
         * 3. Invoke the resolved method
         *
         * This is why child's method is called even with parent reference
         */

        AnimalBase animal = new Dog2();  // Reference: AnimalBase, Object: Dog2
        animal.makeSound();              // Calls Dog2's makeSound() - NOT AnimalBase's!

        AnimalBase animal2 = new Cat2();
        animal2.makeSound();             // Calls Cat2's makeSound()

        // ===== COVARIANT RETURN TYPES =====

        System.out.println("\n===== COVARIANT RETURN TYPES =====");

        // Child can return subtype of parent's return type

        AnimalFactory factory = new DogFactory();
        AnimalBase created = factory.createAnimal();
        System.out.println("Created: " + created.getClass().getSimpleName());

        DogFactory dogFactory = new DogFactory();
        Dog2 createdDog = dogFactory.createAnimal();  // Returns Dog2, not just AnimalBase
        System.out.println("Created dog: " + createdDog.getClass().getSimpleName());

        // ===== POLYMORPHISM WITH INTERFACES =====

        System.out.println("\n===== POLYMORPHISM WITH INTERFACES =====");

        // Interface reference, implementing class object
        Drawable drawable1 = new Circle(5);
        Drawable drawable2 = new Rectangle(3, 4);

        drawable1.draw();
        drawable2.draw();

        // Method accepting interface type
        renderDrawable(drawable1);
        renderDrawable(drawable2);

        // ===== METHOD HIDING (Static Methods) =====

        System.out.println("\n===== METHOD HIDING (Static Methods) =====");

        /*
         * Static methods are NOT overridden, they are HIDDEN
         * Method called depends on REFERENCE type, not object type
         */

        ParentStatic parent = new ChildStatic();
        parent.staticMethod();  // Calls ParentStatic.staticMethod()

        ChildStatic child = new ChildStatic();
        child.staticMethod();   // Calls ChildStatic.staticMethod()

        // Better to call static methods via class name
        ParentStatic.staticMethod();
        ChildStatic.staticMethod();

        // ===== OVERLOADING VS OVERRIDING COMPARISON =====

        System.out.println("\n===== OVERLOADING VS OVERRIDING =====");

        /*
         * OVERLOADING:
         * - Same class (or inherited)
         * - Same method name
         * - Different parameters
         * - Return type can differ
         * - Compile-time binding
         * - Also called static polymorphism
         *
         * OVERRIDING:
         * - Parent-child relationship
         * - Same method signature
         * - Same or covariant return type
         * - Cannot be more restrictive access
         * - Runtime binding
         * - Also called dynamic polymorphism
         */

        ComparisonDemo demo = new ComparisonDemo();
        demo.showDifferences();

        System.out.println("\n===== Demo Complete =====");
    }

    // Method accepting interface type - enables polymorphism
    static void renderDrawable(Drawable d) {
        System.out.println("Rendering: ");
        d.draw();
    }
}

// ===== COMPILE-TIME POLYMORPHISM (OVERLOADING) =====

class Calculator {

    // Overloaded methods - same name, different signatures

    public int add(int a, int b) {
        return a + b;
    }

    public int add(int a, int b, int c) {
        return a + b + c;
    }

    public double add(double a, double b) {
        return a + b;
    }

    public String add(String a, String b) {
        return a + b;
    }

    // Different parameter order
    public void display(int num, String text) {
        System.out.println("int, String: " + num + ", " + text);
    }

    public void display(String text, int num) {
        System.out.println("String, int: " + text + ", " + num);
    }
}

// ===== RUNTIME POLYMORPHISM (OVERRIDING) =====

interface Drawable {
    void draw();
}

abstract class Shape implements Drawable {
    protected String color;

    public Shape() {
        this.color = "Black";
    }

    // Abstract method - must be overridden
    public abstract double calculateArea();

    // Concrete method - can be overridden
    @Override
    public void draw() {
        System.out.println("Drawing a shape");
    }

    public String getColor() {
        return color;
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing Circle with radius " + radius);
    }

    public double getRadius() {
        return radius;
    }
}

class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing Rectangle " + width + "x" + height);
    }
}

class Triangle extends Shape {
    private double base;
    private double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing Triangle with base " + base + ", height " + height);
    }
}

// ===== VIRTUAL METHOD INVOCATION =====

class AnimalBase {
    public void makeSound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog2 extends AnimalBase {
    @Override
    public void makeSound() {
        System.out.println("Dog barks: Woof!");
    }
}

class Cat2 extends AnimalBase {
    @Override
    public void makeSound() {
        System.out.println("Cat meows: Meow!");
    }
}

// ===== COVARIANT RETURN TYPES =====

class AnimalFactory {
    public AnimalBase createAnimal() {
        return new AnimalBase();
    }
}

class DogFactory extends AnimalFactory {
    @Override
    public Dog2 createAnimal() {  // Returns Dog2, subtype of AnimalBase
        return new Dog2();
    }
}

// ===== METHOD HIDING (Static Methods) =====

class ParentStatic {
    public static void staticMethod() {
        System.out.println("ParentStatic.staticMethod()");
    }

    public void instanceMethod() {
        System.out.println("ParentStatic.instanceMethod()");
    }
}

class ChildStatic extends ParentStatic {
    // This HIDES parent's static method, not overrides
    public static void staticMethod() {
        System.out.println("ChildStatic.staticMethod()");
    }

    @Override
    public void instanceMethod() {
        System.out.println("ChildStatic.instanceMethod()");
    }
}

// ===== COMPARISON DEMO =====

class ComparisonDemo {

    public void showDifferences() {
        String comparison =
            "┌─────────────────────────────────────────────────────────────┐\n" +
            "│        OVERLOADING vs OVERRIDING                           │\n" +
            "├─────────────────────────┬───────────────────────────────────┤\n" +
            "│      OVERLOADING        │         OVERRIDING               │\n" +
            "├─────────────────────────┼───────────────────────────────────┤\n" +
            "│ Same class              │ Parent-child classes              │\n" +
            "│ Same name               │ Same name                         │\n" +
            "│ Different parameters    │ Same parameters                   │\n" +
            "│ Return type can differ  │ Same/covariant return             │\n" +
            "│ Access can differ       │ Cannot be more restrictive        │\n" +
            "│ Compile-time binding    │ Runtime binding                   │\n" +
            "│ Static polymorphism     │ Dynamic polymorphism              │\n" +
            "│ Also: ad-hoc poly.      │ Also: subtype poly.              │\n" +
            "└─────────────────────────┴───────────────────────────────────┘";
        System.out.println(comparison);
    }
}

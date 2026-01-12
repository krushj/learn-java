package advanced.solid;

/**
 * ============================================================================
 * LISKOV SUBSTITUTION PRINCIPLE (LSP)
 * ============================================================================
 * 
 * DEFINITION: Objects of a superclass should be replaceable with objects
 * of a subclass without breaking the application.
 * 
 * FORMAL DEFINITION: If S is a subtype of T, then objects of type T may be
 * replaced with objects of type S without altering program correctness.
 * 
 * WHY IT MATTERS:
 * - Ensures proper inheritance hierarchies
 * - Prevents unexpected behavior
 * - Maintains code contracts
 * - Enables polymorphism safely
 * 
 * RULES TO FOLLOW:
 * - Subclass should not strengthen preconditions
 * - Subclass should not weaken postconditions
 * - Subclass should maintain invariants
 * - No new exceptions in subclass methods
 * 
 * COMMON VIOLATIONS:
 * - Subclass throws new exceptions
 * - Subclass has stricter input requirements
 * - Subclass weakens output guarantees
 * - Classic example: Square/Rectangle problem
 */

// ============================================================================
// BAD EXAMPLE 1: Bird/Penguin Problem
// ============================================================================

/**
 * BAD: All birds assumed to fly
 */
class BirdBad {
    public void fly() {
        System.out.println("Bird is flying");
    }
}

/**
 * BAD: Penguins can't fly - throws exception, violating LSP!
 */
class PenguinBad extends BirdBad {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguins cannot fly!");
    }
}

/**
 * BAD: This code breaks when using PenguinBad
 */
class BirdWatcherBad {
    public void makeBirdFly(BirdBad bird) {
        bird.fly(); // FAILS if bird is a Penguin!
    }
}

// ============================================================================
// GOOD EXAMPLE 1: Proper Bird Hierarchy
// ============================================================================

/**
 * GOOD: Base class for all birds - only common behaviors
 */
abstract class Bird {
    protected String name;
    
    public Bird(String name) {
        this.name = name;
    }
    
    // All birds can eat
    public void eat() {
        System.out.println(name + " is eating");
    }
    
    // All birds can make sounds
    public abstract void makeSound();
    
    public String getName() {
        return name;
    }
}

/**
 * GOOD: Separate hierarchy for flying birds
 */
abstract class FlyingBird extends Bird {
    public FlyingBird(String name) {
        super(name);
    }
    
    // Only flying birds have these methods
    public void fly() {
        System.out.println(name + " is flying");
    }
    
    public void takeOff() {
        System.out.println(name + " is taking off");
    }
    
    public void land() {
        System.out.println(name + " is landing");
    }
}

/**
 * Sparrow is a flying bird
 */
class Sparrow extends FlyingBird {
    public Sparrow() {
        super("Sparrow");
    }
    
    @Override
    public void makeSound() {
        System.out.println("Chirp chirp!");
    }
}

/**
 * Eagle is a flying bird
 */
class Eagle extends FlyingBird {
    public Eagle() {
        super("Eagle");
    }
    
    @Override
    public void makeSound() {
        System.out.println("Screech!");
    }
}

/**
 * GOOD: Penguin is a Bird but NOT a FlyingBird
 */
class Penguin extends Bird {
    public Penguin() {
        super("Penguin");
    }
    
    @Override
    public void makeSound() {
        System.out.println("Squawk!");
    }
    
    // Penguins have their own special ability
    public void swim() {
        System.out.println(name + " is swimming");
    }
}

/**
 * Ostrich is also a non-flying bird
 */
class Ostrich extends Bird {
    public Ostrich() {
        super("Ostrich");
    }
    
    @Override
    public void makeSound() {
        System.out.println("Boom boom!");
    }
    
    public void run() {
        System.out.println(name + " is running fast");
    }
}

/**
 * GOOD: Now we can safely work with different bird types
 */
class BirdSanctuary {
    // Works with ANY bird
    public void feedBird(Bird bird) {
        System.out.println("Feeding " + bird.getName());
        bird.eat();
        bird.makeSound();
    }
    
    // Works specifically with flying birds
    public void releaseFlyingBird(FlyingBird bird) {
        System.out.println("Releasing " + bird.getName());
        bird.takeOff();
        bird.fly();
        bird.land();
    }
}

// ============================================================================
// BAD EXAMPLE 2: Rectangle/Square Problem
// ============================================================================

/**
 * BAD: Mutable Rectangle
 */
class RectangleBad {
    protected int width;
    protected int height;
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getArea() {
        return width * height;
    }
}

/**
 * BAD: Square violates LSP - changes both dimensions
 */
class SquareBad extends RectangleBad {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width; // Violates LSP!
    }
    
    @Override
    public void setHeight(int height) {
        this.width = height; // Violates LSP!
        this.height = height;
    }
}

/**
 * BAD: This test breaks with Square
 */
class RectangleTesterBad {
    public void testRectangle(RectangleBad rect) {
        rect.setWidth(5);
        rect.setHeight(4);
        
        int expectedArea = 20; // 5 * 4
        int actualArea = rect.getArea();
        
        System.out.println("Expected area: " + expectedArea);
        System.out.println("Actual area: " + actualArea);
        System.out.println("Test " + (expectedArea == actualArea ? "PASSED ✓" : "FAILED ✗"));
    }
}

// ============================================================================
// GOOD EXAMPLE 2: Immutable Shape Hierarchy
// ============================================================================

/**
 * GOOD: Shape interface
 */
interface ShapeLSP {
    int getArea();
    int getPerimeter();
}

/**
 * GOOD: Immutable Rectangle
 */
class RectangleLSP implements ShapeLSP {
    private final int width;
    private final int height;
    
    public RectangleLSP(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public int getArea() {
        return width * height;
    }
    
    @Override
    public int getPerimeter() {
        return 2 * (width + height);
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    // Create new rectangle with different dimensions
    public RectangleLSP withWidth(int newWidth) {
        return new RectangleLSP(newWidth, this.height);
    }
    
    public RectangleLSP withHeight(int newHeight) {
        return new RectangleLSP(this.width, newHeight);
    }
}

/**
 * GOOD: Square as separate implementation (not subclass of Rectangle)
 */
class SquareLSP implements ShapeLSP {
    private final int side;
    
    public SquareLSP(int side) {
        this.side = side;
    }
    
    @Override
    public int getArea() {
        return side * side;
    }
    
    @Override
    public int getPerimeter() {
        return 4 * side;
    }
    
    public int getSide() { return side; }
    
    public SquareLSP withSide(int newSide) {
        return new SquareLSP(newSide);
    }
}

/**
 * GOOD: Works with any shape
 */
class ShapeTester {
    public void testShape(ShapeLSP shape) {
        System.out.println("Area: " + shape.getArea());
        System.out.println("Perimeter: " + shape.getPerimeter());
    }
}

// ============================================================================
// USAGE DEMONSTRATION
// ============================================================================

public class LSPDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║     LISKOV SUBSTITUTION PRINCIPLE DEMONSTRATION          ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Bird Problem
        System.out.println("━━━ BAD Example: Bird/Penguin Problem ━━━\n");
        demonstrateBadBirdExample();
        
        System.out.println("\n━━━ GOOD Example: Proper Bird Hierarchy ━━━\n");
        demonstrateGoodBirdExample();
        
        // Demo 2: Rectangle Problem
        System.out.println("\n━━━ BAD Example: Rectangle/Square Problem ━━━\n");
        demonstrateBadRectangleExample();
        
        System.out.println("\n━━━ GOOD Example: Immutable Shapes ━━━\n");
        demonstrateGoodShapeExample();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║               LSP DEMO COMPLETED!                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void demonstrateBadBirdExample() {
        System.out.println("Problem: All birds assumed to fly");
        System.out.println();
        
        BirdWatcherBad watcher = new BirdWatcherBad();
        
        System.out.println("Testing with BirdBad:");
        BirdBad normalBird = new BirdBad();
        watcher.makeBirdFly(normalBird);
        
        System.out.println("\nTesting with PenguinBad:");
        try {
            BirdBad penguin = new PenguinBad();
            watcher.makeBirdFly(penguin); // This will throw!
        } catch (UnsupportedOperationException e) {
            System.out.println("⚠️  EXCEPTION: " + e.getMessage());
            System.out.println("⚠️  LSP VIOLATED: Penguin cannot substitute Bird!");
        }
    }
    
    private static void demonstrateGoodBirdExample() {
        BirdSanctuary sanctuary = new BirdSanctuary();
        
        System.out.println("All birds can be fed (common behavior):\n");
        
        // All birds can be fed
        Bird sparrow = new Sparrow();
        Bird penguin = new Penguin();
        Bird eagle = new Eagle();
        Bird ostrich = new Ostrich();
        
        sanctuary.feedBird(sparrow);
        System.out.println();
        sanctuary.feedBird(penguin);
        System.out.println();
        
        System.out.println("\n--- Only FlyingBirds can be released to fly ---\n");
        
        // Only flying birds can be released
        sanctuary.releaseFlyingBird((FlyingBird) sparrow);
        System.out.println();
        sanctuary.releaseFlyingBird((FlyingBird) eagle);
        
        System.out.println("\n--- Non-flying birds have special abilities ---\n");
        
        ((Penguin) penguin).swim();
        ((Ostrich) ostrich).run();
        
        System.out.println("\n✓ LSP maintained: Each subtype properly substitutes its parent");
    }
    
    private static void demonstrateBadRectangleExample() {
        RectangleTesterBad tester = new RectangleTesterBad();
        
        System.out.println("Testing with Rectangle:");
        RectangleBad rectangle = new RectangleBad();
        tester.testRectangle(rectangle);
        
        System.out.println("\nTesting with Square (subclass of Rectangle):");
        RectangleBad square = new SquareBad();
        tester.testRectangle(square);
        
        System.out.println("\n⚠️  Square failed because setWidth also changed height!");
        System.out.println("⚠️  LSP VIOLATED: Square cannot substitute Rectangle!");
    }
    
    private static void demonstrateGoodShapeExample() {
        ShapeTester tester = new ShapeTester();
        
        System.out.println("Rectangle (5x4):");
        RectangleLSP rectangle = new RectangleLSP(5, 4);
        tester.testShape(rectangle);
        
        System.out.println("\nSquare (5x5):");
        SquareLSP square = new SquareLSP(5);
        tester.testShape(square);
        
        System.out.println("\n--- Immutability ensures correctness ---\n");
        
        System.out.println("Creating new rectangle from existing:");
        RectangleLSP newRect = rectangle.withWidth(10);
        System.out.println("Original: " + rectangle.getWidth() + "x" + rectangle.getHeight());
        System.out.println("New: " + newRect.getWidth() + "x" + newRect.getHeight());
        
        System.out.println("\n✓ LSP maintained: Both shapes work through ShapeLSP interface");
        System.out.println("✓ Immutability prevents unexpected state changes");
    }
}

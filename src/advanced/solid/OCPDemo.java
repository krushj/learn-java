package advanced.solid;

import java.util.*;

/**
 * ============================================================================
 * OPEN/CLOSED PRINCIPLE (OCP)
 * ============================================================================
 * 
 * DEFINITION: Software entities should be OPEN for extension but
 * CLOSED for modification.
 * 
 * MEANING:
 * - Open for Extension: Can add new functionality
 * - Closed for Modification: Don't change existing code
 * 
 * WHY IT MATTERS:
 * - Reduces risk of breaking existing functionality
 * - Makes code more maintainable
 * - Supports polymorphism
 * - Enables plugin architectures
 * 
 * HOW TO ACHIEVE:
 * - Use abstraction (interfaces/abstract classes)
 * - Rely on polymorphism
 * - Strategy pattern, Template Method pattern
 * 
 * WHEN TO APPLY:
 * - When you anticipate changes in requirements
 * - When similar functionality might be added
 * - When building frameworks/libraries
 */

// ============================================================================
// BAD EXAMPLE - Violates OCP
// ============================================================================

/**
 * BAD: Need to modify this method every time we add a new shape
 */
class AreaCalculatorBad {
    public double calculateArea(Object shape) {
        double area = 0;
        
        // Need to add new else-if for each shape type
        if (shape instanceof CircleBad) {
            CircleBad circle = (CircleBad) shape;
            area = Math.PI * circle.radius * circle.radius;
        } else if (shape instanceof RectangleBad) {
            RectangleBad rect = (RectangleBad) shape;
            area = rect.width * rect.height;
        } else if (shape instanceof TriangleBad) {
            TriangleBad tri = (TriangleBad) shape;
            area = 0.5 * tri.base * tri.height;
        }
        // Adding new shape requires MODIFYING this class!
        
        return area;
    }
}

class CircleBad {
    double radius;
    CircleBad(double r) { this.radius = r; }
}

class RectangleBad {
    double width, height;
    RectangleBad(double w, double h) { this.width = w; this.height = h; }
}

class TriangleBad {
    double base, height;
    TriangleBad(double b, double h) { this.base = b; this.height = h; }
}

// ============================================================================
// GOOD EXAMPLE - Follows OCP
// ============================================================================

/**
 * GOOD: Abstract shape interface - defines contract
 */
interface Shape {
    double calculateArea();
    double calculatePerimeter();
    String getName();
}

/**
 * Circle implementation - can be extended without modifying existing code
 */
class Circle implements Shape {
    private double radius;
    
    public Circle(double radius) {
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
    
    @Override
    public String getName() {
        return "Circle (radius=" + radius + ")";
    }
    
    public double getRadius() {
        return radius;
    }
}

/**
 * Rectangle implementation
 */
class Rectangle implements Shape {
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
    public double calculatePerimeter() {
        return 2 * (width + height);
    }
    
    @Override
    public String getName() {
        return "Rectangle (" + width + "x" + height + ")";
    }
}

/**
 * Triangle implementation - NEW shape added without modifying existing code
 */
class Triangle implements Shape {
    private double base;
    private double height;
    private double side1, side2, side3;
    
    public Triangle(double base, double height, double side1, double side2, double side3) {
        this.base = base;
        this.height = height;
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }
    
    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }
    
    @Override
    public double calculatePerimeter() {
        return side1 + side2 + side3;
    }
    
    @Override
    public String getName() {
        return "Triangle (base=" + base + ", height=" + height + ")";
    }
}

/**
 * Pentagon - Another new shape, NO modification needed to existing classes
 */
class Pentagon implements Shape {
    private double side;
    
    public Pentagon(double side) {
        this.side = side;
    }
    
    @Override
    public double calculateArea() {
        // Area of regular pentagon = (1/4) * sqrt(5(5 + 2*sqrt(5))) * s^2
        return 0.25 * Math.sqrt(5 * (5 + 2 * Math.sqrt(5))) * side * side;
    }
    
    @Override
    public double calculatePerimeter() {
        return 5 * side;
    }
    
    @Override
    public String getName() {
        return "Pentagon (side=" + side + ")";
    }
}

/**
 * GOOD: Area calculator - CLOSED for modification, OPEN for extension
 * Can handle ANY shape that implements Shape interface
 */
class AreaCalculator {
    
    public double calculateTotalArea(List<Shape> shapes) {
        double totalArea = 0;
        for (Shape shape : shapes) {
            totalArea += shape.calculateArea();
        }
        return totalArea;
    }
    
    public double calculateTotalPerimeter(List<Shape> shapes) {
        double totalPerimeter = 0;
        for (Shape shape : shapes) {
            totalPerimeter += shape.calculatePerimeter();
        }
        return totalPerimeter;
    }
    
    public Shape findLargestShape(List<Shape> shapes) {
        if (shapes.isEmpty()) return null;
        
        Shape largest = shapes.get(0);
        for (Shape shape : shapes) {
            if (shape.calculateArea() > largest.calculateArea()) {
                largest = shape;
            }
        }
        return largest;
    }
    
    public Shape findSmallestShape(List<Shape> shapes) {
        if (shapes.isEmpty()) return null;
        
        Shape smallest = shapes.get(0);
        for (Shape shape : shapes) {
            if (shape.calculateArea() < smallest.calculateArea()) {
                smallest = shape;
            }
        }
        return smallest;
    }
    
    public void printShapeDetails(List<Shape> shapes) {
        for (Shape shape : shapes) {
            System.out.println("  " + shape.getName());
            System.out.println("    Area: " + String.format("%.2f", shape.calculateArea()));
            System.out.println("    Perimeter: " + String.format("%.2f", shape.calculatePerimeter()));
        }
    }
}

// ============================================================================
// ANOTHER OCP EXAMPLE: Discount System
// ============================================================================

/**
 * Discount Strategy interface
 */
interface DiscountStrategy {
    double applyDiscount(double price);
    String getDescription();
}

/**
 * No discount
 */
class NoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double price) {
        return price;
    }
    
    @Override
    public String getDescription() {
        return "No discount";
    }
}

/**
 * Percentage discount
 */
class PercentageDiscount implements DiscountStrategy {
    private double percentage;
    
    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }
    
    @Override
    public double applyDiscount(double price) {
        return price * (1 - percentage / 100);
    }
    
    @Override
    public String getDescription() {
        return percentage + "% off";
    }
}

/**
 * Fixed amount discount
 */
class FixedDiscount implements DiscountStrategy {
    private double amount;
    
    public FixedDiscount(double amount) {
        this.amount = amount;
    }
    
    @Override
    public double applyDiscount(double price) {
        return Math.max(0, price - amount);
    }
    
    @Override
    public String getDescription() {
        return "$" + amount + " off";
    }
}

/**
 * Buy one get one free (BOGO)
 */
class BogoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double price) {
        return price / 2; // 50% off effectively
    }
    
    @Override
    public String getDescription() {
        return "Buy One Get One Free";
    }
}

/**
 * PriceCalculator - CLOSED for modification, OPEN for new discount types
 */
class PriceCalculator {
    
    public double calculateFinalPrice(double originalPrice, DiscountStrategy discount) {
        return discount.applyDiscount(originalPrice);
    }
    
    public void printPriceBreakdown(double originalPrice, DiscountStrategy discount) {
        double finalPrice = discount.applyDiscount(originalPrice);
        double savings = originalPrice - finalPrice;
        
        System.out.println("  Original Price: $" + String.format("%.2f", originalPrice));
        System.out.println("  Discount: " + discount.getDescription());
        System.out.println("  Final Price: $" + String.format("%.2f", finalPrice));
        System.out.println("  You Save: $" + String.format("%.2f", savings));
    }
}

// ============================================================================
// USAGE DEMONSTRATION
// ============================================================================

public class OCPDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║        OPEN/CLOSED PRINCIPLE DEMONSTRATION               ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Bad Example
        System.out.println("━━━ BAD Example: Violates OCP ━━━\n");
        demonstrateBadExample();
        
        System.out.println("\n━━━ GOOD Example: Follows OCP (Shapes) ━━━\n");
        demonstrateGoodShapeExample();
        
        System.out.println("\n━━━ GOOD Example: Follows OCP (Discounts) ━━━\n");
        demonstrateDiscountExample();
        
        System.out.println("\n━━━ Adding New Functionality ━━━\n");
        demonstrateExtension();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║               OCP DEMO COMPLETED!                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void demonstrateBadExample() {
        System.out.println("AreaCalculatorBad has problems:");
        System.out.println("  ⚠️  Uses instanceof checks for each shape type");
        System.out.println("  ⚠️  Adding a Pentagon requires MODIFYING the calculator");
        System.out.println("  ⚠️  Growing if-else chain is hard to maintain");
        System.out.println("  ⚠️  Risk of breaking existing functionality");
        
        AreaCalculatorBad badCalc = new AreaCalculatorBad();
        System.out.println("\nCalculating areas (bad way):");
        System.out.println("  Circle area: " + badCalc.calculateArea(new CircleBad(5)));
        System.out.println("  Rectangle area: " + badCalc.calculateArea(new RectangleBad(4, 6)));
    }
    
    private static void demonstrateGoodShapeExample() {
        AreaCalculator calculator = new AreaCalculator();
        
        // Create various shapes
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Circle(5));
        shapes.add(new Rectangle(4, 6));
        shapes.add(new Triangle(3, 4, 3, 4, 5));
        shapes.add(new Pentagon(3));
        
        System.out.println("Shape details:");
        calculator.printShapeDetails(shapes);
        
        System.out.println("\nSummary:");
        System.out.println("  Total Area: " + String.format("%.2f", calculator.calculateTotalArea(shapes)));
        System.out.println("  Total Perimeter: " + String.format("%.2f", calculator.calculateTotalPerimeter(shapes)));
        
        Shape largest = calculator.findLargestShape(shapes);
        System.out.println("  Largest Shape: " + largest.getName() + 
            " (area=" + String.format("%.2f", largest.calculateArea()) + ")");
        
        System.out.println("\n✓ Adding Pentagon required NO changes to AreaCalculator!");
    }
    
    private static void demonstrateDiscountExample() {
        PriceCalculator priceCalc = new PriceCalculator();
        double originalPrice = 100.0;
        
        System.out.println("Original price: $" + originalPrice);
        System.out.println();
        
        // Different discount strategies
        DiscountStrategy[] discounts = {
            new NoDiscount(),
            new PercentageDiscount(20),
            new FixedDiscount(15),
            new BogoDiscount()
        };
        
        for (DiscountStrategy discount : discounts) {
            System.out.println(discount.getDescription() + ":");
            priceCalc.printPriceBreakdown(originalPrice, discount);
            System.out.println();
        }
        
        System.out.println("✓ Adding new discount types requires NO changes to PriceCalculator!");
    }
    
    private static void demonstrateExtension() {
        System.out.println("Extending the system with new functionality:\n");
        
        // Add a new shape - Hexagon
        class Hexagon implements Shape {
            private double side;
            
            Hexagon(double side) { this.side = side; }
            
            @Override
            public double calculateArea() {
                return (3 * Math.sqrt(3) / 2) * side * side;
            }
            
            @Override
            public double calculatePerimeter() {
                return 6 * side;
            }
            
            @Override
            public String getName() {
                return "Hexagon (side=" + side + ")";
            }
        }
        
        // Add a new discount - Seasonal
        class SeasonalDiscount implements DiscountStrategy {
            private String season;
            private double percentage;
            
            SeasonalDiscount(String season, double percentage) {
                this.season = season;
                this.percentage = percentage;
            }
            
            @Override
            public double applyDiscount(double price) {
                return price * (1 - percentage / 100);
            }
            
            @Override
            public String getDescription() {
                return season + " Sale: " + percentage + "% off";
            }
        }
        
        // Use new shape
        AreaCalculator calculator = new AreaCalculator();
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Hexagon(5));
        shapes.add(new Circle(3));
        
        System.out.println("New Hexagon shape added:");
        calculator.printShapeDetails(shapes);
        
        // Use new discount
        PriceCalculator priceCalc = new PriceCalculator();
        System.out.println("\nNew Seasonal Discount added:");
        priceCalc.printPriceBreakdown(200.0, new SeasonalDiscount("Summer", 30));
        
        System.out.println("\n✓ Extended system without modifying existing classes!");
        System.out.println("✓ This is the power of Open/Closed Principle!");
    }
}

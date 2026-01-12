package advanced.patterns.creational;

/**
 * ============================================================================
 * FACTORY PATTERN
 * ============================================================================
 * 
 * PURPOSE: Creates objects without specifying the exact class to create.
 * 
 * TYPES:
 * 1. Simple Factory (not an official pattern)
 * 2. Factory Method
 * 3. Abstract Factory
 * 
 * WHEN TO USE:
 * - Object creation logic is complex
 * - Need to decouple object creation from usage
 * - Creation depends on configuration/input
 * - Want to hide concrete implementations
 * 
 * PROS:
 * - Loose coupling
 * - Single Responsibility (creation logic in one place)
 * - Open/Closed Principle (easy to add new types)
 * 
 * CONS:
 * - Code complexity increases
 * - Many classes needed
 */

// ============================================================================
// SIMPLE FACTORY
// ============================================================================

/**
 * Product interface - what factory creates
 */
interface Vehicle {
    void drive();
    void park();
    String getType();
    double getFuelEfficiency();
}

/**
 * Concrete product - Car
 */
class Car implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Driving a car on the road");
    }
    
    @Override
    public void park() {
        System.out.println("Parking car in garage");
    }
    
    @Override
    public String getType() {
        return "Car";
    }
    
    @Override
    public double getFuelEfficiency() {
        return 30.0; // MPG
    }
}

/**
 * Concrete product - Bike
 */
class Bike implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Riding a bike on the trail");
    }
    
    @Override
    public void park() {
        System.out.println("Parking bike at rack");
    }
    
    @Override
    public String getType() {
        return "Bike";
    }
    
    @Override
    public double getFuelEfficiency() {
        return 60.0; // MPG
    }
}

/**
 * Concrete product - Truck
 */
class Truck implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Driving a heavy truck on highway");
    }
    
    @Override
    public void park() {
        System.out.println("Parking truck at loading bay");
    }
    
    @Override
    public String getType() {
        return "Truck";
    }
    
    @Override
    public double getFuelEfficiency() {
        return 15.0; // MPG
    }
}

/**
 * Concrete product - Electric Car
 */
class ElectricCar implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Driving electric car silently");
    }
    
    @Override
    public void park() {
        System.out.println("Parking at charging station");
    }
    
    @Override
    public String getType() {
        return "Electric Car";
    }
    
    @Override
    public double getFuelEfficiency() {
        return 100.0; // MPGe
    }
}

/**
 * SIMPLE FACTORY - Creates objects based on input
 */
class VehicleFactory {
    
    public static Vehicle createVehicle(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null");
        }
        
        switch (type.toLowerCase()) {
            case "car":
                return new Car();
            case "bike":
                return new Bike();
            case "truck":
                return new Truck();
            case "electric":
            case "electriccar":
                return new ElectricCar();
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
    
    // Overloaded factory method with additional configuration
    public static Vehicle createVehicle(String type, String color) {
        Vehicle vehicle = createVehicle(type);
        System.out.println("Created " + color + " " + vehicle.getType());
        return vehicle;
    }
}

// ============================================================================
// FACTORY METHOD PATTERN
// ============================================================================

/**
 * FACTORY METHOD PATTERN
 * 
 * DIFFERENCE FROM SIMPLE FACTORY:
 * - Uses inheritance and polymorphism
 * - Each factory creates specific product
 * - More extensible
 */

/**
 * Abstract creator - defines factory method
 */
abstract class VehicleFactoryMethod {
    
    /**
     * Factory method - subclasses override to create specific vehicles
     */
    public abstract Vehicle createVehicle();
    
    /**
     * Template method using factory method
     */
    public Vehicle orderVehicle() {
        Vehicle vehicle = createVehicle();
        
        System.out.println("Ordering " + vehicle.getType());
        prepareVehicle(vehicle);
        qualityCheck(vehicle);
        deliver(vehicle);
        
        return vehicle;
    }
    
    private void prepareVehicle(Vehicle vehicle) {
        System.out.println("  Preparing " + vehicle.getType() + "...");
    }
    
    private void qualityCheck(Vehicle vehicle) {
        System.out.println("  Quality check passed for " + vehicle.getType());
    }
    
    private void deliver(Vehicle vehicle) {
        System.out.println("  " + vehicle.getType() + " delivered!");
    }
}

/**
 * Concrete creator - Car Factory
 */
class CarFactory extends VehicleFactoryMethod {
    @Override
    public Vehicle createVehicle() {
        return new Car();
    }
}

/**
 * Concrete creator - Bike Factory
 */
class BikeFactory extends VehicleFactoryMethod {
    @Override
    public Vehicle createVehicle() {
        return new Bike();
    }
}

/**
 * Concrete creator - Truck Factory
 */
class TruckFactory extends VehicleFactoryMethod {
    @Override
    public Vehicle createVehicle() {
        return new Truck();
    }
}

/**
 * Concrete creator - Electric Car Factory
 */
class ElectricCarFactory extends VehicleFactoryMethod {
    @Override
    public Vehicle createVehicle() {
        return new ElectricCar();
    }
}

// ============================================================================
// ABSTRACT FACTORY PATTERN
// ============================================================================

/**
 * ABSTRACT FACTORY
 * 
 * Creates families of related objects without specifying concrete classes.
 * Here: Different vehicle families (Economy, Luxury)
 */

// Additional vehicle interfaces for families
interface Sedan {
    void drive();
    String getComfortLevel();
}

interface SUV {
    void drive();
    String getTerrainCapability();
}

// Economy family
class EconomySedan implements Sedan {
    @Override
    public void drive() {
        System.out.println("Driving economy sedan - fuel efficient");
    }
    
    @Override
    public String getComfortLevel() {
        return "Basic";
    }
}

class EconomySUV implements SUV {
    @Override
    public void drive() {
        System.out.println("Driving economy SUV - practical");
    }
    
    @Override
    public String getTerrainCapability() {
        return "City roads";
    }
}

// Luxury family
class LuxurySedan implements Sedan {
    @Override
    public void drive() {
        System.out.println("Driving luxury sedan - smooth and quiet");
    }
    
    @Override
    public String getComfortLevel() {
        return "Premium leather, massage seats";
    }
}

class LuxurySUV implements SUV {
    @Override
    public void drive() {
        System.out.println("Driving luxury SUV - powerful and comfortable");
    }
    
    @Override
    public String getTerrainCapability() {
        return "All-terrain with air suspension";
    }
}

/**
 * Abstract Factory interface
 */
interface VehicleFamilyFactory {
    Sedan createSedan();
    SUV createSUV();
    String getFactoryName();
}

/**
 * Concrete factory - Economy vehicles
 */
class EconomyVehicleFactory implements VehicleFamilyFactory {
    @Override
    public Sedan createSedan() {
        return new EconomySedan();
    }
    
    @Override
    public SUV createSUV() {
        return new EconomySUV();
    }
    
    @Override
    public String getFactoryName() {
        return "Economy Vehicle Factory";
    }
}

/**
 * Concrete factory - Luxury vehicles
 */
class LuxuryVehicleFactory implements VehicleFamilyFactory {
    @Override
    public Sedan createSedan() {
        return new LuxurySedan();
    }
    
    @Override
    public SUV createSUV() {
        return new LuxurySUV();
    }
    
    @Override
    public String getFactoryName() {
        return "Luxury Vehicle Factory";
    }
}

/**
 * Client that uses Abstract Factory
 */
class VehicleShowroom {
    private Sedan sedan;
    private SUV suv;
    private String showroomType;
    
    public VehicleShowroom(VehicleFamilyFactory factory) {
        this.sedan = factory.createSedan();
        this.suv = factory.createSUV();
        this.showroomType = factory.getFactoryName();
    }
    
    public void showVehicles() {
        System.out.println("=== " + showroomType + " Showroom ===");
        System.out.println("\nSedan:");
        sedan.drive();
        System.out.println("Comfort: " + sedan.getComfortLevel());
        
        System.out.println("\nSUV:");
        suv.drive();
        System.out.println("Terrain: " + suv.getTerrainCapability());
    }
}

// ============================================================================
// USAGE DEMONSTRATION
// ============================================================================

public class FactoryDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║            FACTORY PATTERN DEMONSTRATION                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Simple Factory
        System.out.println("━━━ Simple Factory ━━━\n");
        demonstrateSimpleFactory();
        
        // Demo 2: Factory Method
        System.out.println("\n━━━ Factory Method Pattern ━━━\n");
        demonstrateFactoryMethod();
        
        // Demo 3: Abstract Factory
        System.out.println("\n━━━ Abstract Factory Pattern ━━━\n");
        demonstrateAbstractFactory();
        
        // Demo 4: Real-world scenario
        System.out.println("\n━━━ Real-World Scenario ━━━\n");
        demonstrateRealWorldScenario();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║            FACTORY DEMO COMPLETED!                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void demonstrateSimpleFactory() {
        System.out.println("Creating vehicles using Simple Factory:\n");
        
        // Create different vehicles using factory
        Vehicle car = VehicleFactory.createVehicle("car");
        Vehicle bike = VehicleFactory.createVehicle("bike");
        Vehicle truck = VehicleFactory.createVehicle("truck");
        Vehicle electric = VehicleFactory.createVehicle("electric");
        
        // Use the vehicles
        System.out.println("Using created vehicles:\n");
        
        for (Vehicle v : new Vehicle[]{car, bike, truck, electric}) {
            System.out.println(v.getType() + ":");
            v.drive();
            v.park();
            System.out.println("Fuel efficiency: " + v.getFuelEfficiency() + " MPG\n");
        }
        
        System.out.println("✓ Client doesn't know concrete classes!");
        System.out.println("✓ Easy to add new vehicle types to factory!");
    }
    
    private static void demonstrateFactoryMethod() {
        System.out.println("Creating vehicles using Factory Method:\n");
        
        // Each factory creates its specific vehicle
        VehicleFactoryMethod carFactory = new CarFactory();
        VehicleFactoryMethod bikeFactory = new BikeFactory();
        VehicleFactoryMethod electricFactory = new ElectricCarFactory();
        
        System.out.println("--- Car Factory ---");
        Vehicle car = carFactory.orderVehicle();
        
        System.out.println("\n--- Bike Factory ---");
        Vehicle bike = bikeFactory.orderVehicle();
        
        System.out.println("\n--- Electric Car Factory ---");
        Vehicle electric = electricFactory.orderVehicle();
        
        System.out.println("\n✓ Each factory handles its own vehicle type!");
        System.out.println("✓ Template method provides common workflow!");
    }
    
    private static void demonstrateAbstractFactory() {
        System.out.println("Creating vehicle families using Abstract Factory:\n");
        
        // Economy showroom
        VehicleFamilyFactory economyFactory = new EconomyVehicleFactory();
        VehicleShowroom economyShowroom = new VehicleShowroom(economyFactory);
        economyShowroom.showVehicles();
        
        System.out.println();
        
        // Luxury showroom
        VehicleFamilyFactory luxuryFactory = new LuxuryVehicleFactory();
        VehicleShowroom luxuryShowroom = new VehicleShowroom(luxuryFactory);
        luxuryShowroom.showVehicles();
        
        System.out.println("\n✓ Abstract Factory creates families of related objects!");
        System.out.println("✓ Ensures products are compatible (all economy or all luxury)!");
    }
    
    private static void demonstrateRealWorldScenario() {
        System.out.println("Fleet Management System:\n");
        
        // Simulate fleet management
        String[] requiredVehicles = {"car", "truck", "bike", "electric", "car", "truck"};
        
        System.out.println("Building fleet from requirements...\n");
        
        java.util.List<Vehicle> fleet = new java.util.ArrayList<>();
        
        for (String type : requiredVehicles) {
            Vehicle v = VehicleFactory.createVehicle(type);
            fleet.add(v);
            System.out.println("Added " + v.getType() + " to fleet");
        }
        
        System.out.println("\n--- Fleet Summary ---");
        System.out.println("Total vehicles: " + fleet.size());
        
        double totalEfficiency = 0;
        for (Vehicle v : fleet) {
            totalEfficiency += v.getFuelEfficiency();
        }
        System.out.println("Average fuel efficiency: " + 
            String.format("%.1f", totalEfficiency / fleet.size()) + " MPG");
        
        System.out.println("\n✓ Factory pattern makes fleet construction flexible!");
        System.out.println("✓ Vehicle types can be read from config/database!");
    }
}

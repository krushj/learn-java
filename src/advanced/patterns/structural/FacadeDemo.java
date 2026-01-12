package advanced.patterns.structural;

/**
 * ============================================================================
 * FACADE PATTERN
 * ============================================================================
 * 
 * PURPOSE: Provides unified interface to a set of interfaces in a subsystem.
 * Defines higher-level interface that makes subsystem easier to use.
 * 
 * WHEN TO USE:
 * - Want simple interface to complex subsystem
 * - Many dependencies between clients and implementation classes
 * - Want to layer subsystems
 * 
 * COMPONENTS:
 * 1. Facade - knows which subsystem classes handle requests
 * 2. Subsystem classes - implement subsystem functionality
 * 3. Client - uses Facade instead of subsystem directly
 * 
 * PROS:
 * - Simplifies interface
 * - Decouples client from subsystem
 * - Reduces learning curve
 * - Promotes weak coupling
 * 
 * CONS:
 * - Can become god object
 * - May limit functionality
 * 
 * REAL-WORLD EXAMPLES:
 * - JDBC (hides database complexity)
 * - SLF4J (hides logging complexity)
 * - Spring Framework (hides configuration complexity)
 */

// ============================================================================
// EXAMPLE 1: Home Theater System
// ============================================================================

/**
 * Subsystem class - DVD Player
 */
class DVDPlayer {
    private String movie;
    
    public void on() {
        System.out.println("DVD Player: Turning ON");
    }
    
    public void off() {
        System.out.println("DVD Player: Turning OFF");
    }
    
    public void play(String movie) {
        this.movie = movie;
        System.out.println("DVD Player: Playing \"" + movie + "\"");
    }
    
    public void pause() {
        System.out.println("DVD Player: Pausing \"" + movie + "\"");
    }
    
    public void stop() {
        System.out.println("DVD Player: Stopping \"" + movie + "\"");
    }
    
    public void eject() {
        System.out.println("DVD Player: Ejecting disc");
    }
}

/**
 * Subsystem class - Amplifier
 */
class Amplifier {
    public void on() {
        System.out.println("Amplifier: Turning ON");
    }
    
    public void off() {
        System.out.println("Amplifier: Turning OFF");
    }
    
    public void setDvdPlayer(DVDPlayer dvd) {
        System.out.println("Amplifier: Setting DVD player as input");
    }
    
    public void setSurroundSound() {
        System.out.println("Amplifier: Setting surround sound mode");
    }
    
    public void setVolume(int level) {
        System.out.println("Amplifier: Setting volume to " + level);
    }
}

/**
 * Subsystem class - Projector
 */
class Projector {
    public void on() {
        System.out.println("Projector: Turning ON");
    }
    
    public void off() {
        System.out.println("Projector: Turning OFF");
    }
    
    public void wideScreenMode() {
        System.out.println("Projector: Setting widescreen mode (16:9)");
    }
    
    public void tvMode() {
        System.out.println("Projector: Setting TV mode (4:3)");
    }
}

/**
 * Subsystem class - Theater Lights
 */
class TheaterLights {
    public void on() {
        System.out.println("Theater Lights: Turning ON");
    }
    
    public void off() {
        System.out.println("Theater Lights: Turning OFF");
    }
    
    public void dim(int level) {
        System.out.println("Theater Lights: Dimming to " + level + "%");
    }
}

/**
 * Subsystem class - Screen
 */
class Screen {
    public void down() {
        System.out.println("Screen: Lowering");
    }
    
    public void up() {
        System.out.println("Screen: Raising");
    }
}

/**
 * Subsystem class - Popcorn Popper
 */
class PopcornPopper {
    public void on() {
        System.out.println("Popcorn Popper: Turning ON");
    }
    
    public void off() {
        System.out.println("Popcorn Popper: Turning OFF");
    }
    
    public void pop() {
        System.out.println("Popcorn Popper: Popping popcorn!");
    }
}

/**
 * FACADE - Simplifies home theater operation
 */
class HomeTheaterFacade {
    private Amplifier amp;
    private DVDPlayer dvd;
    private Projector projector;
    private TheaterLights lights;
    private Screen screen;
    private PopcornPopper popper;
    
    public HomeTheaterFacade(Amplifier amp, DVDPlayer dvd, Projector projector,
                            TheaterLights lights, Screen screen, PopcornPopper popper) {
        this.amp = amp;
        this.dvd = dvd;
        this.projector = projector;
        this.lights = lights;
        this.screen = screen;
        this.popper = popper;
    }
    
    /**
     * Single method replaces complex sequence of operations
     */
    public void watchMovie(String movie) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║    GET READY TO WATCH: " + movie);
        System.out.println("╚════════════════════════════════════════╝\n");
        
        popper.on();
        popper.pop();
        lights.dim(10);
        screen.down();
        projector.on();
        projector.wideScreenMode();
        amp.on();
        amp.setDvdPlayer(dvd);
        amp.setSurroundSound();
        amp.setVolume(7);
        dvd.on();
        dvd.play(movie);
        
        System.out.println("\n✓ Enjoy your movie!\n");
    }
    
    /**
     * Single method to pause movie
     */
    public void pauseMovie() {
        System.out.println("\n--- Pausing Movie ---");
        dvd.pause();
        lights.dim(50);
    }
    
    /**
     * Single method to resume movie
     */
    public void resumeMovie() {
        System.out.println("\n--- Resuming Movie ---");
        lights.dim(10);
        dvd.play(""); // Continue playing
    }
    
    /**
     * Single method to end movie
     */
    public void endMovie() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         SHUTTING DOWN THEATER          ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        popper.off();
        dvd.stop();
        dvd.eject();
        dvd.off();
        amp.off();
        projector.off();
        screen.up();
        lights.on();
        
        System.out.println("\n✓ Theater shutdown complete\n");
    }
}

// ============================================================================
// EXAMPLE 2: Computer Startup Facade
// ============================================================================

/**
 * Subsystem classes
 */
class CPU {
    public void freeze() {
        System.out.println("CPU: Freezing processor");
    }
    
    public void jump(long position) {
        System.out.println("CPU: Jumping to position " + position);
    }
    
    public void execute() {
        System.out.println("CPU: Executing instructions");
    }
}

class Memory {
    public void load(long position, byte[] data) {
        System.out.println("Memory: Loading data at position " + position);
    }
}

class HardDrive {
    public byte[] read(long sector, int size) {
        System.out.println("Hard Drive: Reading sector " + sector + ", size " + size);
        return new byte[size];
    }
}

class GraphicsCard {
    public void initialize() {
        System.out.println("Graphics Card: Initializing");
    }
    
    public void displayBootScreen() {
        System.out.println("Graphics Card: Displaying boot screen");
    }
}

/**
 * FACADE - Simplifies computer startup
 */
class ComputerFacade {
    private CPU cpu;
    private Memory memory;
    private HardDrive hardDrive;
    private GraphicsCard graphics;
    
    private static final long BOOT_ADDRESS = 0x7C00;
    private static final long BOOT_SECTOR = 0;
    private static final int SECTOR_SIZE = 512;
    
    public ComputerFacade() {
        this.cpu = new CPU();
        this.memory = new Memory();
        this.hardDrive = new HardDrive();
        this.graphics = new GraphicsCard();
    }
    
    public void startComputer() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║         STARTING COMPUTER              ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        graphics.initialize();
        graphics.displayBootScreen();
        cpu.freeze();
        byte[] bootData = hardDrive.read(BOOT_SECTOR, SECTOR_SIZE);
        memory.load(BOOT_ADDRESS, bootData);
        cpu.jump(BOOT_ADDRESS);
        cpu.execute();
        
        System.out.println("\n✓ Computer started successfully!\n");
    }
    
    public void shutdownComputer() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║       SHUTTING DOWN COMPUTER           ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        System.out.println("Saving state...");
        System.out.println("Closing applications...");
        cpu.freeze();
        System.out.println("Powering off...");
        
        System.out.println("\n✓ Computer shutdown complete\n");
    }
}

// ============================================================================
// EXAMPLE 3: Order Processing Facade
// ============================================================================

/**
 * Subsystem classes
 */
class InventoryService {
    public boolean checkStock(String productId) {
        System.out.println("Inventory: Checking stock for " + productId);
        return true;
    }
    
    public void reserveItem(String productId) {
        System.out.println("Inventory: Reserving " + productId);
    }
    
    public void releaseItem(String productId) {
        System.out.println("Inventory: Releasing reservation for " + productId);
    }
}

class PaymentService {
    public boolean processPayment(String cardNumber, double amount) {
        System.out.println("Payment: Processing $" + amount + " on card ***" + 
            cardNumber.substring(cardNumber.length() - 4));
        return true;
    }
    
    public void refundPayment(String transactionId, double amount) {
        System.out.println("Payment: Refunding $" + amount + " for TX " + transactionId);
    }
}

class ShippingService {
    public String createShipment(String address) {
        String trackingNumber = "TRK" + System.currentTimeMillis();
        System.out.println("Shipping: Creating shipment to " + address);
        System.out.println("Shipping: Tracking number: " + trackingNumber);
        return trackingNumber;
    }
    
    public void cancelShipment(String trackingNumber) {
        System.out.println("Shipping: Canceling shipment " + trackingNumber);
    }
}

class NotificationService {
    public void sendOrderConfirmation(String email, String orderId) {
        System.out.println("Notification: Sending order confirmation to " + email);
    }
    
    public void sendShippingNotification(String email, String trackingNumber) {
        System.out.println("Notification: Sending shipping notification to " + email);
    }
}

/**
 * FACADE - Simplifies order processing
 */
class OrderFacade {
    private InventoryService inventory;
    private PaymentService payment;
    private ShippingService shipping;
    private NotificationService notification;
    
    public OrderFacade() {
        this.inventory = new InventoryService();
        this.payment = new PaymentService();
        this.shipping = new ShippingService();
        this.notification = new NotificationService();
    }
    
    /**
     * Single method to place order - handles all subsystems
     */
    public String placeOrder(String productId, String cardNumber, double amount,
                            String shippingAddress, String email) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║          PLACING ORDER                 ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        // Check inventory
        if (!inventory.checkStock(productId)) {
            System.out.println("❌ Order failed: Out of stock");
            return null;
        }
        
        // Reserve item
        inventory.reserveItem(productId);
        
        // Process payment
        if (!payment.processPayment(cardNumber, amount)) {
            inventory.releaseItem(productId);
            System.out.println("❌ Order failed: Payment declined");
            return null;
        }
        
        // Create shipment
        String trackingNumber = shipping.createShipment(shippingAddress);
        
        // Generate order ID
        String orderId = "ORD" + System.currentTimeMillis();
        
        // Send notifications
        notification.sendOrderConfirmation(email, orderId);
        notification.sendShippingNotification(email, trackingNumber);
        
        System.out.println("\n✓ Order placed successfully!");
        System.out.println("  Order ID: " + orderId);
        System.out.println("  Tracking: " + trackingNumber);
        
        return orderId;
    }
    
    /**
     * Single method to cancel order
     */
    public void cancelOrder(String orderId, String productId, String trackingNumber,
                           String transactionId, double amount, String email) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         CANCELING ORDER                ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        shipping.cancelShipment(trackingNumber);
        payment.refundPayment(transactionId, amount);
        inventory.releaseItem(productId);
        
        System.out.println("\n✓ Order " + orderId + " canceled successfully");
    }
}

// ============================================================================
// USAGE DEMONSTRATION
// ============================================================================

public class FacadeDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║            FACADE PATTERN DEMONSTRATION                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Home Theater
        System.out.println("━━━ Home Theater Facade ━━━\n");
        demonstrateHomeTheater();
        
        // Demo 2: Computer Startup
        System.out.println("\n━━━ Computer Startup Facade ━━━\n");
        demonstrateComputerStartup();
        
        // Demo 3: Order Processing
        System.out.println("\n━━━ Order Processing Facade ━━━\n");
        demonstrateOrderProcessing();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║           FACADE DEMO COMPLETED!                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void demonstrateHomeTheater() {
        // Create subsystem components
        Amplifier amp = new Amplifier();
        DVDPlayer dvd = new DVDPlayer();
        Projector projector = new Projector();
        TheaterLights lights = new TheaterLights();
        Screen screen = new Screen();
        PopcornPopper popper = new PopcornPopper();
        
        // Create facade
        HomeTheaterFacade homeTheater = new HomeTheaterFacade(
            amp, dvd, projector, lights, screen, popper
        );
        
        // Simple interface for complex operations
        homeTheater.watchMovie("Inception");
        
        // Simulate watching for a bit
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        
        homeTheater.pauseMovie();
        
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        
        homeTheater.endMovie();
        
        System.out.println("✓ One method call instead of 12+ individual calls!");
    }
    
    private static void demonstrateComputerStartup() {
        ComputerFacade computer = new ComputerFacade();
        
        // Simple startup - hides complexity
        computer.startComputer();
        
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        
        computer.shutdownComputer();
        
        System.out.println("✓ Complex boot sequence simplified to one method!");
    }
    
    private static void demonstrateOrderProcessing() {
        OrderFacade orderSystem = new OrderFacade();
        
        // Place order - single method handles everything
        String orderId = orderSystem.placeOrder(
            "PROD-123",
            "4111111111111111",
            99.99,
            "123 Main St, City, State 12345",
            "customer@example.com"
        );
        
        System.out.println("\n✓ Complex order process simplified to one method!");
        System.out.println("✓ Facade coordinates inventory, payment, shipping, and notifications!");
    }
}

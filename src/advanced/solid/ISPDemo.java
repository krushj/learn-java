package advanced.solid;

/**
 * ============================================================================
 * INTERFACE SEGREGATION PRINCIPLE (ISP)
 * ============================================================================
 * 
 * DEFINITION: Clients should not be forced to depend on interfaces
 * they do not use. Many specific interfaces are better than one
 * general-purpose interface.
 * 
 * WHY IT MATTERS:
 * - Reduces coupling
 * - Increases cohesion
 * - Makes code more flexible
 * - Easier to understand and implement
 * - Prevents "fat" interfaces
 * 
 * HOW TO APPLY:
 * - Break large interfaces into smaller, specific ones
 * - Classes implement only what they need
 * - Role-based interfaces
 * 
 * SIGNS OF VIOLATION:
 * - Empty/dummy implementations
 * - Throwing UnsupportedOperationException
 * - Implementing methods with no-op
 */

// ============================================================================
// BAD EXAMPLE 1: Fat Worker Interface
// ============================================================================

/**
 * BAD: Fat interface - not all workers can do all these things
 */
interface WorkerBad {
    void work();
    void eat();
    void sleep();
    void getSalary();
    void attendMeeting();
}

/**
 * Human worker can do everything - okay
 */
class HumanWorkerBad implements WorkerBad {
    @Override
    public void work() {
        System.out.println("Human working");
    }
    
    @Override
    public void eat() {
        System.out.println("Human eating");
    }
    
    @Override
    public void sleep() {
        System.out.println("Human sleeping");
    }
    
    @Override
    public void getSalary() {
        System.out.println("Human getting salary");
    }
    
    @Override
    public void attendMeeting() {
        System.out.println("Human attending meeting");
    }
}

/**
 * BAD: Robot worker forced to implement methods it can't use!
 */
class RobotWorkerBad implements WorkerBad {
    @Override
    public void work() {
        System.out.println("Robot working");
    }
    
    // Robots don't eat, sleep, get salary, or attend meetings!
    @Override
    public void eat() {
        throw new UnsupportedOperationException("Robots don't eat");
    }
    
    @Override
    public void sleep() {
        throw new UnsupportedOperationException("Robots don't sleep");
    }
    
    @Override
    public void getSalary() {
        throw new UnsupportedOperationException("Robots don't get salary");
    }
    
    @Override
    public void attendMeeting() {
        throw new UnsupportedOperationException("Robots don't attend meetings");
    }
}

// ============================================================================
// GOOD EXAMPLE 1: Segregated Worker Interfaces
// ============================================================================

/**
 * GOOD: Core working interface - minimal and focused
 */
interface Workable {
    void work();
}

/**
 * GOOD: Biological needs interface
 */
interface Eatable {
    void eat();
}

interface Sleepable {
    void sleep();
}

/**
 * GOOD: Employment-related interface
 */
interface Payable {
    void getSalary();
}

/**
 * GOOD: Communication interface
 */
interface Attendable {
    void attendMeeting();
}

/**
 * GOOD: Human worker implements all relevant interfaces
 */
class HumanWorker implements Workable, Eatable, Sleepable, Payable, Attendable {
    private String name;
    
    public HumanWorker(String name) {
        this.name = name;
    }
    
    @Override
    public void work() {
        System.out.println(name + " is working");
    }
    
    @Override
    public void eat() {
        System.out.println(name + " is eating lunch");
    }
    
    @Override
    public void sleep() {
        System.out.println(name + " is sleeping");
    }
    
    @Override
    public void getSalary() {
        System.out.println(name + " received salary");
    }
    
    @Override
    public void attendMeeting() {
        System.out.println(name + " is attending meeting");
    }
}

/**
 * GOOD: Robot worker implements ONLY what it can do
 */
class RobotWorker implements Workable {
    private String model;
    
    public RobotWorker(String model) {
        this.model = model;
    }
    
    @Override
    public void work() {
        System.out.println("Robot " + model + " is working");
    }
    
    // Robots can have their own specific methods
    public void recharge() {
        System.out.println("Robot " + model + " is recharging");
    }
    
    public void runDiagnostics() {
        System.out.println("Robot " + model + " running diagnostics");
    }
}

/**
 * GOOD: Contractor implements only work and payment
 */
class Contractor implements Workable, Payable {
    private String name;
    
    public Contractor(String name) {
        this.name = name;
    }
    
    @Override
    public void work() {
        System.out.println(name + " (contractor) is working");
    }
    
    @Override
    public void getSalary() {
        System.out.println(name + " received contractor payment");
    }
}

/**
 * GOOD: Manager for different types of workers
 */
class WorkManager {
    public void manageWork(Workable worker) {
        worker.work();
    }
    
    public void processPayment(Payable employee) {
        employee.getSalary();
    }
    
    public void scheduleMeeting(Attendable participant) {
        participant.attendMeeting();
    }
    
    public void scheduleLunch(Eatable eater) {
        eater.eat();
    }
}

// ============================================================================
// BAD EXAMPLE 2: Fat Printer Interface
// ============================================================================

/**
 * BAD: Fat interface for multi-function devices
 */
interface MultiFunctionDeviceBad {
    void print(String document);
    void scan(String document);
    void fax(String document);
    void photocopy(String document);
}

/**
 * BAD: Simple printer forced to implement all methods
 */
class SimplePrinterBad implements MultiFunctionDeviceBad {
    @Override
    public void print(String document) {
        System.out.println("Printing: " + document);
    }
    
    // Simple printer doesn't have these features!
    @Override
    public void scan(String document) {
        throw new UnsupportedOperationException("Scan not supported");
    }
    
    @Override
    public void fax(String document) {
        throw new UnsupportedOperationException("Fax not supported");
    }
    
    @Override
    public void photocopy(String document) {
        throw new UnsupportedOperationException("Photocopy not supported");
    }
}

// ============================================================================
// GOOD EXAMPLE 2: Segregated Printer Interfaces
// ============================================================================

/**
 * GOOD: Segregated interfaces
 */
interface Printer {
    void print(String document);
}

interface Scanner {
    void scan(String document);
}

interface Fax {
    void fax(String document);
}

interface Photocopier {
    void photocopy(String document);
}

/**
 * GOOD: Simple printer implements only printing
 */
class SimplePrinter implements Printer {
    @Override
    public void print(String document) {
        System.out.println("SimplePrinter: Printing " + document);
    }
}

/**
 * GOOD: Scan-and-print device implements two interfaces
 */
class ScanPrintDevice implements Printer, Scanner {
    @Override
    public void print(String document) {
        System.out.println("ScanPrint: Printing " + document);
    }
    
    @Override
    public void scan(String document) {
        System.out.println("ScanPrint: Scanning " + document);
    }
}

/**
 * GOOD: All-in-one device implements all interfaces
 */
class AllInOneDevice implements Printer, Scanner, Fax, Photocopier {
    @Override
    public void print(String document) {
        System.out.println("All-in-One: Printing " + document);
    }
    
    @Override
    public void scan(String document) {
        System.out.println("All-in-One: Scanning " + document);
    }
    
    @Override
    public void fax(String document) {
        System.out.println("All-in-One: Faxing " + document);
    }
    
    @Override
    public void photocopy(String document) {
        System.out.println("All-in-One: Photocopying " + document);
    }
}

/**
 * Document processor that uses segregated interfaces
 */
class DocumentProcessor {
    public void printDocument(Printer printer, String doc) {
        printer.print(doc);
    }
    
    public void scanDocument(Scanner scanner, String doc) {
        scanner.scan(doc);
    }
    
    public void faxDocument(Fax fax, String doc) {
        fax.fax(doc);
    }
}

// ============================================================================
// USAGE DEMONSTRATION
// ============================================================================

public class ISPDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║     INTERFACE SEGREGATION PRINCIPLE DEMONSTRATION        ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Bad Worker Example
        System.out.println("━━━ BAD Example: Fat Worker Interface ━━━\n");
        demonstrateBadWorkerExample();
        
        System.out.println("\n━━━ GOOD Example: Segregated Worker Interfaces ━━━\n");
        demonstrateGoodWorkerExample();
        
        // Demo 2: Printer Example
        System.out.println("\n━━━ BAD Example: Fat Printer Interface ━━━\n");
        demonstrateBadPrinterExample();
        
        System.out.println("\n━━━ GOOD Example: Segregated Printer Interfaces ━━━\n");
        demonstrateGoodPrinterExample();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║               ISP DEMO COMPLETED!                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void demonstrateBadWorkerExample() {
        System.out.println("Human worker - works fine:");
        WorkerBad human = new HumanWorkerBad();
        human.work();
        human.eat();
        
        System.out.println("\nRobot worker - forced to implement useless methods:");
        WorkerBad robot = new RobotWorkerBad();
        robot.work();
        
        try {
            robot.eat();
        } catch (UnsupportedOperationException e) {
            System.out.println("⚠️  EXCEPTION: " + e.getMessage());
        }
        
        try {
            robot.getSalary();
        } catch (UnsupportedOperationException e) {
            System.out.println("⚠️  EXCEPTION: " + e.getMessage());
        }
        
        System.out.println("\n⚠️  ISP VIOLATED: Robot forced to implement methods it can't use!");
    }
    
    private static void demonstrateGoodWorkerExample() {
        WorkManager manager = new WorkManager();
        
        // Human worker
        HumanWorker alice = new HumanWorker("Alice");
        System.out.println("Human Worker (Alice):");
        manager.manageWork(alice);
        manager.scheduleLunch(alice);
        manager.processPayment(alice);
        manager.scheduleMeeting(alice);
        
        // Robot worker - only implements Workable
        RobotWorker robot = new RobotWorker("R2D2");
        System.out.println("\nRobot Worker (R2D2):");
        manager.manageWork(robot);
        robot.recharge();
        robot.runDiagnostics();
        
        // Contractor - implements Workable and Payable
        Contractor bob = new Contractor("Bob");
        System.out.println("\nContractor (Bob):");
        manager.manageWork(bob);
        manager.processPayment(bob);
        
        System.out.println("\n✓ Each type implements only relevant interfaces!");
    }
    
    private static void demonstrateBadPrinterExample() {
        System.out.println("Simple printer forced to implement all methods:");
        MultiFunctionDeviceBad printer = new SimplePrinterBad();
        printer.print("document.pdf");
        
        try {
            printer.scan("document.pdf");
        } catch (UnsupportedOperationException e) {
            System.out.println("⚠️  EXCEPTION: " + e.getMessage());
        }
        
        try {
            printer.fax("document.pdf");
        } catch (UnsupportedOperationException e) {
            System.out.println("⚠️  EXCEPTION: " + e.getMessage());
        }
        
        System.out.println("\n⚠️  ISP VIOLATED: Simple printer forced to have scan/fax/copy!");
    }
    
    private static void demonstrateGoodPrinterExample() {
        DocumentProcessor processor = new DocumentProcessor();
        
        // Simple printer - only prints
        System.out.println("Simple Printer:");
        SimplePrinter simple = new SimplePrinter();
        processor.printDocument(simple, "report.pdf");
        
        // Scan-print device - prints and scans
        System.out.println("\nScan-Print Device:");
        ScanPrintDevice scanPrint = new ScanPrintDevice();
        processor.printDocument(scanPrint, "invoice.pdf");
        processor.scanDocument(scanPrint, "receipt.pdf");
        
        // All-in-one - does everything
        System.out.println("\nAll-in-One Device:");
        AllInOneDevice allInOne = new AllInOneDevice();
        processor.printDocument(allInOne, "contract.pdf");
        processor.scanDocument(allInOne, "id-card.pdf");
        processor.faxDocument(allInOne, "urgent.pdf");
        allInOne.photocopy("document.pdf");
        
        System.out.println("\n✓ Each device type implements only its capabilities!");
        System.out.println("✓ No UnsupportedOperationException needed!");
    }
}

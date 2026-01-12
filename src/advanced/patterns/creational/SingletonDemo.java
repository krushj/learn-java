package advanced.patterns.creational;

import java.sql.Connection;

/**
 * ============================================================================
 * SINGLETON PATTERN
 * ============================================================================
 * 
 * PURPOSE: Ensures only ONE instance of a class exists and provides
 * global access to it.
 * 
 * WHEN TO USE:
 * - Database connections
 * - Configuration managers
 * - Logger instances
 * - Thread pools
 * - Caching
 * 
 * PROS:
 * - Controlled access to sole instance
 * - Reduced memory footprint
 * - Global access point
 * 
 * CONS:
 * - Difficult to unit test
 * - Can hide dependencies
 * - Violates Single Responsibility Principle
 * - Synchronization overhead in multithreading
 * 
 * IMPLEMENTATION APPROACHES:
 * 1. Eager Initialization
 * 2. Lazy Initialization
 * 3. Thread-Safe Lazy Initialization
 * 4. Double-Checked Locking
 * 5. Bill Pugh (Inner Static Helper) - RECOMMENDED
 * 6. Enum Singleton - BEST APPROACH
 */

// ============================================================================
// APPROACH 1: EAGER INITIALIZATION
// ============================================================================

/**
 * PROS:
 * - Simple and thread-safe
 * - No synchronization overhead
 * 
 * CONS:
 * - Instance created even if never used
 * - Cannot handle exceptions in constructor
 */
class DatabaseConnectionEager {
    // Instance created at class loading time
    private static final DatabaseConnectionEager instance = new DatabaseConnectionEager();
    
    private String connectionString = "jdbc:mysql://localhost:3306/db";
    
    // Private constructor prevents instantiation
    private DatabaseConnectionEager() {
        System.out.println("DatabaseConnectionEager: Instance created (Eager)");
    }
    
    public static DatabaseConnectionEager getInstance() {
        return instance;
    }
    
    public void query(String sql) {
        System.out.println("Executing query: " + sql);
    }
    
    public String getConnectionString() {
        return connectionString;
    }
}

// ============================================================================
// APPROACH 2: LAZY INITIALIZATION (Not Thread-Safe)
// ============================================================================

/**
 * PROS:
 * - Instance created only when needed
 * - Good for single-threaded environments
 * 
 * CONS:
 * - NOT thread-safe
 * - Multiple instances can be created in multithreading
 */
class DatabaseConnectionLazy {
    private static DatabaseConnectionLazy instance;
    
    private DatabaseConnectionLazy() {
        System.out.println("DatabaseConnectionLazy: Instance created (Lazy)");
    }
    
    // NOT THREAD-SAFE!
    public static DatabaseConnectionLazy getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionLazy();
        }
        return instance;
    }
    
    public void query(String sql) {
        System.out.println("Executing query: " + sql);
    }
}

// ============================================================================
// APPROACH 3: THREAD-SAFE LAZY INITIALIZATION
// ============================================================================

/**
 * PROS:
 * - Thread-safe
 * - Lazy initialization
 * 
 * CONS:
 * - Synchronized method reduces performance
 * - Synchronization needed only during first creation
 */
class DatabaseConnectionThreadSafe {
    private static DatabaseConnectionThreadSafe instance;
    
    private DatabaseConnectionThreadSafe() {
        System.out.println("DatabaseConnectionThreadSafe: Instance created (Thread-Safe)");
    }
    
    // Synchronized entire method - performance overhead
    public static synchronized DatabaseConnectionThreadSafe getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionThreadSafe();
        }
        return instance;
    }
    
    public void query(String sql) {
        System.out.println("Executing query: " + sql);
    }
}

// ============================================================================
// APPROACH 4: DOUBLE-CHECKED LOCKING
// ============================================================================

/**
 * PROS:
 * - Thread-safe
 * - Lazy initialization
 * - Better performance (synchronization only on first creation)
 * 
 * CONS:
 * - More complex code
 * - Requires volatile keyword
 */
class DatabaseConnectionDoubleCheck {
    // volatile ensures visibility across threads
    private static volatile DatabaseConnectionDoubleCheck instance;
    
    private DatabaseConnectionDoubleCheck() {
        System.out.println("DatabaseConnectionDoubleCheck: Instance created (Double-Check)");
    }
    
    public static DatabaseConnectionDoubleCheck getInstance() {
        // First check without locking (fast path)
        if (instance == null) {
            synchronized (DatabaseConnectionDoubleCheck.class) {
                // Second check with locking (thread-safe)
                if (instance == null) {
                    instance = new DatabaseConnectionDoubleCheck();
                }
            }
        }
        return instance;
    }
    
    public void query(String sql) {
        System.out.println("Executing query: " + sql);
    }
}

// ============================================================================
// APPROACH 5: BILL PUGH (Inner Static Helper) - RECOMMENDED
// ============================================================================

/**
 * PROS:
 * - Thread-safe without synchronization
 * - Lazy initialization
 * - Simple and clean
 * - Best performance
 * 
 * HOW IT WORKS:
 * - Inner class not loaded until getInstance() is called
 * - Class loading is thread-safe by JVM
 */
class DatabaseConnectionBillPugh {
    
    private DatabaseConnectionBillPugh() {
        System.out.println("DatabaseConnectionBillPugh: Instance created (Bill Pugh)");
    }
    
    // Static inner class - loaded only when referenced
    private static class SingletonHelper {
        private static final DatabaseConnectionBillPugh INSTANCE = new DatabaseConnectionBillPugh();
    }
    
    public static DatabaseConnectionBillPugh getInstance() {
        return SingletonHelper.INSTANCE;
    }
    
    public void query(String sql) {
        System.out.println("Executing: " + sql);
    }
    
    public void connect() {
        System.out.println("Connected to database");
    }
    
    public void disconnect() {
        System.out.println("Disconnected from database");
    }
}

// ============================================================================
// APPROACH 6: ENUM SINGLETON - BEST APPROACH
// ============================================================================

/**
 * PROS:
 * - Thread-safe by default
 * - Prevents reflection attacks
 * - Prevents serialization issues
 * - Simplest implementation
 * 
 * CONS:
 * - Cannot extend a class (can implement interfaces)
 * - Not lazy loaded
 */
enum DatabaseConnectionEnum {
    INSTANCE;
    
    // Can have fields
    private String connectionString;
    private boolean connected = false;
    
    // Constructor called once
    DatabaseConnectionEnum() {
        System.out.println("DatabaseConnectionEnum: Instance created (Enum)");
        this.connectionString = "jdbc:mysql://localhost:3306/db";
    }
    
    public void connect() {
        connected = true;
        System.out.println("Enum Singleton: Connected to database");
    }
    
    public void disconnect() {
        connected = false;
        System.out.println("Enum Singleton: Disconnected from database");
    }
    
    public void query(String sql) {
        if (!connected) {
            connect();
        }
        System.out.println("Enum Singleton: Executing - " + sql);
    }
    
    public boolean isConnected() {
        return connected;
    }
}

// ============================================================================
// REAL-WORLD EXAMPLE: Configuration Manager
// ============================================================================

class ConfigurationManager {
    private static class Holder {
        private static final ConfigurationManager INSTANCE = new ConfigurationManager();
    }
    
    private java.util.Properties properties;
    
    private ConfigurationManager() {
        properties = new java.util.Properties();
        loadDefaultConfig();
        System.out.println("ConfigurationManager: Initialized");
    }
    
    public static ConfigurationManager getInstance() {
        return Holder.INSTANCE;
    }
    
    private void loadDefaultConfig() {
        properties.setProperty("app.name", "MyApplication");
        properties.setProperty("app.version", "1.0.0");
        properties.setProperty("db.host", "localhost");
        properties.setProperty("db.port", "3306");
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
    
    public void printAllProperties() {
        System.out.println("Configuration Properties:");
        properties.forEach((k, v) -> System.out.println("  " + k + " = " + v));
    }
}

// ============================================================================
// REAL-WORLD EXAMPLE: Logger
// ============================================================================

class Logger {
    private static class Holder {
        private static final Logger INSTANCE = new Logger();
    }
    
    public enum Level { DEBUG, INFO, WARN, ERROR }
    
    private Level minLevel = Level.INFO;
    
    private Logger() {
        System.out.println("Logger: Initialized");
    }
    
    public static Logger getInstance() {
        return Holder.INSTANCE;
    }
    
    public void setMinLevel(Level level) {
        this.minLevel = level;
    }
    
    public void debug(String message) {
        if (minLevel.ordinal() <= Level.DEBUG.ordinal()) {
            log(Level.DEBUG, message);
        }
    }
    
    public void info(String message) {
        if (minLevel.ordinal() <= Level.INFO.ordinal()) {
            log(Level.INFO, message);
        }
    }
    
    public void warn(String message) {
        if (minLevel.ordinal() <= Level.WARN.ordinal()) {
            log(Level.WARN, message);
        }
    }
    
    public void error(String message) {
        log(Level.ERROR, message);
    }
    
    private void log(Level level, String message) {
        String timestamp = java.time.LocalDateTime.now().toString();
        System.out.println(String.format("[%s] [%s] %s", timestamp, level, message));
    }
}

// ============================================================================
// USAGE DEMONSTRATION
// ============================================================================

public class SingletonDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║            SINGLETON PATTERN DEMONSTRATION               ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Different approaches
        System.out.println("━━━ Singleton Approaches ━━━\n");
        demonstrateSingletonApproaches();
        
        // Demo 2: Verify single instance
        System.out.println("\n━━━ Verifying Single Instance ━━━\n");
        verifySingleInstance();
        
        // Demo 3: Real-world examples
        System.out.println("\n━━━ Real-World Examples ━━━\n");
        demonstrateRealWorldExamples();
        
        // Demo 4: Thread safety test
        System.out.println("\n━━━ Thread Safety Test ━━━\n");
        demonstrateThreadSafety();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║           SINGLETON DEMO COMPLETED!                       ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void demonstrateSingletonApproaches() {
        System.out.println("1. Eager Initialization:");
        DatabaseConnectionEager eager = DatabaseConnectionEager.getInstance();
        eager.query("SELECT * FROM users");
        
        System.out.println("\n2. Lazy Initialization (not thread-safe):");
        DatabaseConnectionLazy lazy = DatabaseConnectionLazy.getInstance();
        lazy.query("SELECT * FROM products");
        
        System.out.println("\n3. Thread-Safe (synchronized method):");
        DatabaseConnectionThreadSafe threadSafe = DatabaseConnectionThreadSafe.getInstance();
        threadSafe.query("SELECT * FROM orders");
        
        System.out.println("\n4. Double-Checked Locking:");
        DatabaseConnectionDoubleCheck doubleCheck = DatabaseConnectionDoubleCheck.getInstance();
        doubleCheck.query("SELECT * FROM inventory");
        
        System.out.println("\n5. Bill Pugh (Recommended):");
        DatabaseConnectionBillPugh billPugh = DatabaseConnectionBillPugh.getInstance();
        billPugh.connect();
        billPugh.query("SELECT * FROM customers");
        billPugh.disconnect();
        
        System.out.println("\n6. Enum Singleton (Best):");
        DatabaseConnectionEnum.INSTANCE.query("SELECT * FROM sales");
    }
    
    private static void verifySingleInstance() {
        // Get instance multiple times
        DatabaseConnectionBillPugh instance1 = DatabaseConnectionBillPugh.getInstance();
        DatabaseConnectionBillPugh instance2 = DatabaseConnectionBillPugh.getInstance();
        DatabaseConnectionBillPugh instance3 = DatabaseConnectionBillPugh.getInstance();
        
        System.out.println("instance1 == instance2: " + (instance1 == instance2));
        System.out.println("instance2 == instance3: " + (instance2 == instance3));
        System.out.println("All same hashCode: " + 
            (instance1.hashCode() == instance2.hashCode() && 
             instance2.hashCode() == instance3.hashCode()));
        
        System.out.println("\n✓ All instances point to the same object!");
        
        // Enum singleton
        System.out.println("\nEnum Singleton verification:");
        DatabaseConnectionEnum enum1 = DatabaseConnectionEnum.INSTANCE;
        DatabaseConnectionEnum enum2 = DatabaseConnectionEnum.INSTANCE;
        System.out.println("enum1 == enum2: " + (enum1 == enum2));
    }
    
    private static void demonstrateRealWorldExamples() {
        System.out.println("Configuration Manager:");
        ConfigurationManager config = ConfigurationManager.getInstance();
        config.printAllProperties();
        
        config.setProperty("app.mode", "production");
        System.out.println("\nAfter adding new property:");
        config.printAllProperties();
        
        // Verify same instance
        ConfigurationManager config2 = ConfigurationManager.getInstance();
        System.out.println("\nSame instance? " + (config == config2));
        
        System.out.println("\n--- Logger Example ---\n");
        
        Logger logger = Logger.getInstance();
        logger.setMinLevel(Logger.Level.DEBUG);
        
        logger.debug("This is a debug message");
        logger.info("Application started");
        logger.warn("Low memory warning");
        logger.error("Connection failed");
    }
    
    private static void demonstrateThreadSafety() {
        System.out.println("Creating 5 threads, each getting singleton instance:\n");
        
        Thread[] threads = new Thread[5];
        final DatabaseConnectionBillPugh[] instances = new DatabaseConnectionBillPugh[5];
        
        for (int i = 0; i < 5; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                instances[index] = DatabaseConnectionBillPugh.getInstance();
                System.out.println("Thread-" + index + " got instance: " + 
                    instances[index].hashCode());
            }, "Thread-" + i);
        }
        
        // Start all threads
        for (Thread t : threads) {
            t.start();
        }
        
        // Wait for all threads
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Verify all got same instance
        System.out.println("\nVerifying all threads got same instance:");
        boolean allSame = true;
        for (int i = 1; i < 5; i++) {
            if (instances[0] != instances[i]) {
                allSame = false;
                break;
            }
        }
        System.out.println("All instances same? " + allSame);
        System.out.println("✓ Thread-safe singleton working correctly!");
    }
}

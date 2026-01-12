package advanced.solid;

import java.util.*;

/**
 * ============================================================================
 * DEPENDENCY INVERSION PRINCIPLE (DIP)
 * ============================================================================
 * 
 * DEFINITION: 
 * A. High-level modules should not depend on low-level modules.
 *    Both should depend on abstractions.
 * B. Abstractions should not depend on details.
 *    Details should depend on abstractions.
 * 
 * KEY CONCEPTS:
 * - High-level: Business logic, policies
 * - Low-level: Implementation details (database, file system, etc.)
 * - Abstractions: Interfaces, abstract classes
 * - Details: Concrete implementations
 * 
 * WHY IT MATTERS:
 * - Reduces coupling
 * - Increases flexibility
 * - Makes code testable (can mock dependencies)
 * - Supports plugin architectures
 * - Easier to change implementations
 * 
 * HOW TO ACHIEVE:
 * - Depend on interfaces, not concrete classes
 * - Use dependency injection
 * - Inversion of Control (IoC) containers
 * 
 * DEPENDENCY INJECTION METHODS:
 * 1. Constructor Injection (recommended)
 * 2. Setter Injection
 * 3. Interface Injection
 */

// ============================================================================
// BAD EXAMPLE - Direct dependency on low-level modules
// ============================================================================

/**
 * BAD: Concrete database class
 */
class MySQLDatabaseBad {
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
    
    public String retrieve(String id) {
        return "MySQL data for " + id;
    }
}

/**
 * BAD: High-level module directly depends on low-level module
 */
class UserServiceBad {
    // Direct dependency on concrete class!
    private MySQLDatabaseBad database = new MySQLDatabaseBad();
    
    public void saveUser(String userData) {
        // Tightly coupled to MySQL
        database.save(userData);
    }
}

// Problems:
// 1. Can't switch to PostgreSQL without changing UserServiceBad
// 2. Hard to test (can't mock database)
// 3. UserServiceBad knows too much about database implementation

// ============================================================================
// GOOD EXAMPLE - Depend on abstractions
// ============================================================================

/**
 * GOOD: Abstraction - defines contract for data storage
 */
interface Database {
    void connect();
    void disconnect();
    void save(String data);
    String retrieve(String id);
    void delete(String id);
}

/**
 * Low-level module - MySQL implementation
 */
class MySQLDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("MySQL: Connecting to database");
    }
    
    @Override
    public void disconnect() {
        System.out.println("MySQL: Disconnecting from database");
    }
    
    @Override
    public void save(String data) {
        System.out.println("MySQL: Saving data - " + data);
    }
    
    @Override
    public String retrieve(String id) {
        System.out.println("MySQL: Retrieving data for ID: " + id);
        return "MySQL data for " + id;
    }
    
    @Override
    public void delete(String id) {
        System.out.println("MySQL: Deleting data for ID: " + id);
    }
}

/**
 * Low-level module - PostgreSQL implementation
 */
class PostgreSQLDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("PostgreSQL: Connecting to database");
    }
    
    @Override
    public void disconnect() {
        System.out.println("PostgreSQL: Disconnecting from database");
    }
    
    @Override
    public void save(String data) {
        System.out.println("PostgreSQL: Saving data - " + data);
    }
    
    @Override
    public String retrieve(String id) {
        System.out.println("PostgreSQL: Retrieving data for ID: " + id);
        return "PostgreSQL data for " + id;
    }
    
    @Override
    public void delete(String id) {
        System.out.println("PostgreSQL: Deleting data for ID: " + id);
    }
}

/**
 * Low-level module - MongoDB implementation
 */
class MongoDB implements Database {
    @Override
    public void connect() {
        System.out.println("MongoDB: Connecting to database");
    }
    
    @Override
    public void disconnect() {
        System.out.println("MongoDB: Disconnecting from database");
    }
    
    @Override
    public void save(String data) {
        System.out.println("MongoDB: Saving document - " + data);
    }
    
    @Override
    public String retrieve(String id) {
        System.out.println("MongoDB: Retrieving document for ID: " + id);
        return "MongoDB document for " + id;
    }
    
    @Override
    public void delete(String id) {
        System.out.println("MongoDB: Deleting document for ID: " + id);
    }
}

/**
 * In-Memory database for testing
 */
class InMemoryDatabase implements Database {
    private Map<String, String> storage = new HashMap<>();
    private int idCounter = 0;
    
    @Override
    public void connect() {
        System.out.println("InMemory: Database ready");
    }
    
    @Override
    public void disconnect() {
        System.out.println("InMemory: Database cleared");
        storage.clear();
    }
    
    @Override
    public void save(String data) {
        String id = "id-" + (++idCounter);
        storage.put(id, data);
        System.out.println("InMemory: Saved with ID: " + id);
    }
    
    @Override
    public String retrieve(String id) {
        return storage.get(id);
    }
    
    @Override
    public void delete(String id) {
        storage.remove(id);
        System.out.println("InMemory: Deleted ID: " + id);
    }
}

/**
 * GOOD: High-level module depends on abstraction (Database interface)
 */
class UserServiceDIP {
    private Database database;
    
    /**
     * CONSTRUCTOR INJECTION (Recommended)
     * - Dependencies are immutable
     * - Dependencies are required (compile-time safety)
     * - Easy to test
     */
    public UserServiceDIP(Database database) {
        this.database = database;
    }
    
    /**
     * SETTER INJECTION
     * - Optional dependencies
     * - Can change dependency at runtime
     */
    public void setDatabase(Database database) {
        this.database = database;
    }
    
    public void saveUser(String userData) {
        database.connect();
        database.save(userData);
        database.disconnect();
    }
    
    public String getUser(String id) {
        database.connect();
        String user = database.retrieve(id);
        database.disconnect();
        return user;
    }
    
    public void deleteUser(String id) {
        database.connect();
        database.delete(id);
        database.disconnect();
    }
}

// ============================================================================
// ANOTHER EXAMPLE: Notification System
// ============================================================================

/**
 * Abstraction for message sending
 */
interface MessageSender {
    void sendMessage(String recipient, String message);
    boolean isAvailable();
    String getType();
}

/**
 * Email sender implementation
 */
class EmailSender implements MessageSender {
    @Override
    public void sendMessage(String recipient, String message) {
        System.out.println("EMAIL to " + recipient + ": " + message);
    }
    
    @Override
    public boolean isAvailable() {
        return true; // Would check SMTP server
    }
    
    @Override
    public String getType() {
        return "Email";
    }
}

/**
 * SMS sender implementation
 */
class SmsSender implements MessageSender {
    @Override
    public void sendMessage(String recipient, String message) {
        System.out.println("SMS to " + recipient + ": " + message);
    }
    
    @Override
    public boolean isAvailable() {
        return true; // Would check SMS gateway
    }
    
    @Override
    public String getType() {
        return "SMS";
    }
}

/**
 * Push notification sender implementation
 */
class PushNotificationSender implements MessageSender {
    @Override
    public void sendMessage(String recipient, String message) {
        System.out.println("PUSH to " + recipient + ": " + message);
    }
    
    @Override
    public boolean isAvailable() {
        return true; // Would check push service
    }
    
    @Override
    public String getType() {
        return "Push Notification";
    }
}

/**
 * Slack sender implementation
 */
class SlackSender implements MessageSender {
    @Override
    public void sendMessage(String recipient, String message) {
        System.out.println("SLACK to " + recipient + ": " + message);
    }
    
    @Override
    public boolean isAvailable() {
        return true;
    }
    
    @Override
    public String getType() {
        return "Slack";
    }
}

/**
 * High-level notification service - depends on abstraction
 */
class NotificationService {
    private List<MessageSender> senders;
    
    /**
     * Constructor injection with multiple dependencies
     */
    public NotificationService(List<MessageSender> senders) {
        this.senders = new ArrayList<>(senders);
    }
    
    public void addSender(MessageSender sender) {
        senders.add(sender);
    }
    
    public void removeSender(MessageSender sender) {
        senders.remove(sender);
    }
    
    /**
     * Send notification using all available senders
     */
    public void notifyAll(String recipient, String message) {
        System.out.println("Notifying " + recipient + " via all channels:");
        for (MessageSender sender : senders) {
            if (sender.isAvailable()) {
                sender.sendMessage(recipient, message);
            }
        }
    }
    
    /**
     * Send notification using specific sender type
     */
    public void notify(String recipient, String message, Class<? extends MessageSender> senderType) {
        for (MessageSender sender : senders) {
            if (senderType.isInstance(sender) && sender.isAvailable()) {
                sender.sendMessage(recipient, message);
                return;
            }
        }
        System.out.println("No available sender of type: " + senderType.getSimpleName());
    }
}

// ============================================================================
// USAGE DEMONSTRATION
// ============================================================================

public class DIPDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║     DEPENDENCY INVERSION PRINCIPLE DEMONSTRATION         ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Bad Example
        System.out.println("━━━ BAD Example: Direct Dependency ━━━\n");
        demonstrateBadExample();
        
        System.out.println("\n━━━ GOOD Example: Dependency Injection ━━━\n");
        demonstrateGoodExample();
        
        System.out.println("\n━━━ Switching Implementations ━━━\n");
        demonstrateSwitchingImplementations();
        
        System.out.println("\n━━━ Notification System Example ━━━\n");
        demonstrateNotificationSystem();
        
        System.out.println("\n━━━ Testing with Mock ━━━\n");
        demonstrateTesting();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║               DIP DEMO COMPLETED!                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void demonstrateBadExample() {
        System.out.println("UserServiceBad has problems:");
        System.out.println("  ⚠️  Creates MySQLDatabaseBad internally");
        System.out.println("  ⚠️  Cannot switch to PostgreSQL without code changes");
        System.out.println("  ⚠️  Cannot mock database for testing");
        System.out.println("  ⚠️  Tightly coupled to specific implementation");
        
        System.out.println("\nUsing bad service:");
        UserServiceBad badService = new UserServiceBad();
        badService.saveUser("John Doe");
    }
    
    private static void demonstrateGoodExample() {
        System.out.println("UserServiceDIP depends on Database interface:\n");
        
        // Using MySQL
        Database mysql = new MySQLDatabase();
        UserServiceDIP service = new UserServiceDIP(mysql);
        
        System.out.println("Using MySQL:");
        service.saveUser("Alice");
        
        System.out.println("\n✓ Service doesn't know concrete database type!");
        System.out.println("✓ Can switch implementations without changing service code!");
    }
    
    private static void demonstrateSwitchingImplementations() {
        UserServiceDIP service;
        
        // MySQL
        System.out.println("Configuration: MySQL");
        service = new UserServiceDIP(new MySQLDatabase());
        service.saveUser("User1");
        
        // Switch to PostgreSQL
        System.out.println("\nConfiguration: PostgreSQL");
        service = new UserServiceDIP(new PostgreSQLDatabase());
        service.saveUser("User2");
        
        // Switch to MongoDB
        System.out.println("\nConfiguration: MongoDB");
        service = new UserServiceDIP(new MongoDB());
        service.saveUser("User3");
        
        // Using setter injection to change at runtime
        System.out.println("\n--- Runtime switching using setter injection ---\n");
        service.setDatabase(new MySQLDatabase());
        service.saveUser("User4");
        
        service.setDatabase(new PostgreSQLDatabase());
        service.saveUser("User5");
        
        System.out.println("\n✓ Same UserServiceDIP code works with any database!");
    }
    
    private static void demonstrateNotificationSystem() {
        // Create senders
        List<MessageSender> senders = Arrays.asList(
            new EmailSender(),
            new SmsSender(),
            new PushNotificationSender()
        );
        
        // Create notification service
        NotificationService notifier = new NotificationService(senders);
        
        // Notify via all channels
        System.out.println("Sending to all channels:\n");
        notifier.notifyAll("user@example.com", "Your order has shipped!");
        
        // Add new sender at runtime
        System.out.println("\n--- Adding Slack sender at runtime ---\n");
        notifier.addSender(new SlackSender());
        notifier.notifyAll("user@example.com", "Welcome to our platform!");
        
        // Send via specific channel
        System.out.println("\n--- Sending via specific channel ---\n");
        notifier.notify("user@example.com", "Urgent: Password reset", EmailSender.class);
        
        System.out.println("\n✓ NotificationService works with any MessageSender!");
        System.out.println("✓ New sender types can be added without modifying service!");
    }
    
    private static void demonstrateTesting() {
        System.out.println("Using InMemoryDatabase for testing:\n");
        
        // Use in-memory database for tests
        Database testDb = new InMemoryDatabase();
        UserServiceDIP service = new UserServiceDIP(testDb);
        
        // Run tests
        System.out.println("Test 1: Save user");
        service.saveUser("TestUser1");
        
        System.out.println("\nTest 2: Save another user");
        service.saveUser("TestUser2");
        
        System.out.println("\n✓ Tests run fast with in-memory database!");
        System.out.println("✓ No need for real database connection!");
        System.out.println("✓ DIP enables easy mocking/stubbing for tests!");
    }
}

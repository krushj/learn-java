package advanced.solid;

import java.util.*;

/**
 * ============================================================================
 * SINGLE RESPONSIBILITY PRINCIPLE (SRP)
 * ============================================================================
 * 
 * DEFINITION: A class should have only ONE reason to change.
 * In other words, a class should have only ONE job or responsibility.
 * 
 * WHY IT MATTERS:
 * - Easier to understand and maintain
 * - Reduces coupling between functionalities
 * - Changes in one responsibility don't affect others
 * - Better testability (test one thing at a time)
 * 
 * HOW TO IDENTIFY VIOLATIONS:
 * - Class has multiple unrelated methods
 * - Class description uses "and" (does X AND Y)
 * - Changes in one area require modifying the class
 * 
 * REFACTORING APPROACH:
 * - Identify different responsibilities
 * - Create separate classes for each responsibility
 * - Use composition to combine when needed
 */

// ============================================================================
// BAD EXAMPLE - Multiple Responsibilities
// ============================================================================

/**
 * BAD: This class has MULTIPLE responsibilities:
 * 1. User data management
 * 2. Database operations
 * 3. Email notifications
 * 4. Logging
 * 5. Validation
 */
class UserManagerBad {
    // Responsibility 1: User data management
    public void createUser(String name, String email) {
        System.out.println("Creating user: " + name);
    }
    
    // Responsibility 2: Database operations
    public void saveToDatabase(Object user) {
        System.out.println("Saving to database...");
    }
    
    // Responsibility 3: Email notifications
    public void sendWelcomeEmail(Object user) {
        System.out.println("Sending welcome email...");
    }
    
    // Responsibility 4: Logging
    public void logUserCreation(Object user) {
        System.out.println("Logging user creation...");
    }
    
    // Responsibility 5: Validation
    public boolean validateEmail(String email) {
        return email != null && email.contains("@");
    }
}

// ============================================================================
// GOOD EXAMPLE - Single Responsibility
// ============================================================================

/**
 * GOOD: Represents a User entity - only manages user data
 */
class User {
    private String id;
    private String name;
    private String email;
    private String createdAt;
    
    public User(String name, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.createdAt = new Date().toString();
    }
    
    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCreatedAt() { return createdAt; }
    
    // Setters with basic validation
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "User{id='" + id + "', name='" + name + "', email='" + email + "'}";
    }
}

/**
 * GOOD: Handles ONLY user persistence operations
 */
class UserRepository {
    private Map<String, User> database = new HashMap<>();
    
    public boolean save(User user) {
        System.out.println("UserRepository: Saving user to database: " + user.getName());
        database.put(user.getId(), user);
        return true;
    }
    
    public User findById(String id) {
        System.out.println("UserRepository: Finding user by ID: " + id);
        return database.get(id);
    }
    
    public User findByEmail(String email) {
        System.out.println("UserRepository: Finding user by email: " + email);
        return database.values().stream()
            .filter(u -> u.getEmail().equals(email))
            .findFirst()
            .orElse(null);
    }
    
    public boolean update(User user) {
        System.out.println("UserRepository: Updating user: " + user.getId());
        if (database.containsKey(user.getId())) {
            database.put(user.getId(), user);
            return true;
        }
        return false;
    }
    
    public boolean delete(String id) {
        System.out.println("UserRepository: Deleting user: " + id);
        return database.remove(id) != null;
    }
    
    public List<User> findAll() {
        return new ArrayList<>(database.values());
    }
}

/**
 * GOOD: Handles ONLY email-related operations
 */
class EmailService {
    private String smtpServer;
    private int smtpPort;
    
    public EmailService(String smtpServer, int smtpPort) {
        this.smtpServer = smtpServer;
        this.smtpPort = smtpPort;
    }
    
    public void sendWelcomeEmail(User user) {
        String subject = "Welcome to Our Platform!";
        String body = "Hello " + user.getName() + ", welcome aboard!";
        sendEmail(user.getEmail(), subject, body);
    }
    
    public void sendPasswordResetEmail(User user, String resetLink) {
        String subject = "Password Reset Request";
        String body = "Hello " + user.getName() + ", click here to reset: " + resetLink;
        sendEmail(user.getEmail(), subject, body);
    }
    
    private void sendEmail(String to, String subject, String body) {
        System.out.println("EmailService: Sending email to: " + to);
        System.out.println("  Subject: " + subject);
        System.out.println("  Body: " + body);
        // SMTP logic would go here
    }
}

/**
 * GOOD: Handles ONLY logging operations
 */
class UserLogger {
    public void logUserCreation(User user) {
        String logMessage = String.format("[%s] USER_CREATED: ID=%s, Name=%s, Email=%s",
            new Date(), user.getId(), user.getName(), user.getEmail());
        writeLog("INFO", logMessage);
    }
    
    public void logUserUpdate(User user) {
        String logMessage = String.format("[%s] USER_UPDATED: ID=%s",
            new Date(), user.getId());
        writeLog("INFO", logMessage);
    }
    
    public void logUserDeletion(String userId) {
        String logMessage = String.format("[%s] USER_DELETED: ID=%s",
            new Date(), userId);
        writeLog("INFO", logMessage);
    }
    
    public void logError(String message, Exception e) {
        String logMessage = String.format("[%s] ERROR: %s - %s",
            new Date(), message, e.getMessage());
        writeLog("ERROR", logMessage);
    }
    
    private void writeLog(String level, String message) {
        System.out.println("UserLogger [" + level + "]: " + message);
    }
}

/**
 * GOOD: Handles ONLY validation logic
 */
class UserValidator {
    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    public boolean isValidName(String name) {
        return name != null && name.trim().length() >= 2;
    }
    
    public ValidationResult validateUser(User user) {
        ValidationResult result = new ValidationResult();
        
        if (!isValidName(user.getName())) {
            result.addError("Name must be at least 2 characters");
        }
        
        if (!isValidEmail(user.getEmail())) {
            result.addError("Invalid email format");
        }
        
        return result;
    }
    
    public ValidationResult validateNewUser(String name, String email) {
        ValidationResult result = new ValidationResult();
        
        if (!isValidName(name)) {
            result.addError("Name must be at least 2 characters");
        }
        
        if (!isValidEmail(email)) {
            result.addError("Invalid email format");
        }
        
        return result;
    }
}

/**
 * Validation result holder
 */
class ValidationResult {
    private List<String> errors = new ArrayList<>();
    
    public void addError(String error) {
        errors.add(error);
    }
    
    public boolean isValid() {
        return errors.isEmpty();
    }
    
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }
    
    @Override
    public String toString() {
        return isValid() ? "Valid" : "Invalid: " + errors;
    }
}

/**
 * GOOD: Orchestrates user-related operations (Facade pattern)
 * Single responsibility: Coordinate user operations
 */
class UserService {
    private UserRepository repository;
    private EmailService emailService;
    private UserLogger logger;
    private UserValidator validator;
    
    public UserService(UserRepository repository, EmailService emailService,
                      UserLogger logger, UserValidator validator) {
        this.repository = repository;
        this.emailService = emailService;
        this.logger = logger;
        this.validator = validator;
    }
    
    public User createUser(String name, String email) {
        // Validate
        ValidationResult validation = validator.validateNewUser(name, email);
        if (!validation.isValid()) {
            System.out.println("UserService: Validation failed - " + validation.getErrors());
            return null;
        }
        
        // Create user
        User user = new User(name, email);
        
        // Save
        if (repository.save(user)) {
            // Send welcome email
            emailService.sendWelcomeEmail(user);
            
            // Log creation
            logger.logUserCreation(user);
            
            return user;
        }
        
        return null;
    }
    
    public User getUser(String id) {
        return repository.findById(id);
    }
    
    public boolean updateUser(User user) {
        ValidationResult validation = validator.validateUser(user);
        if (!validation.isValid()) {
            System.out.println("UserService: Validation failed - " + validation.getErrors());
            return false;
        }
        
        if (repository.update(user)) {
            logger.logUserUpdate(user);
            return true;
        }
        return false;
    }
    
    public boolean deleteUser(String id) {
        if (repository.delete(id)) {
            logger.logUserDeletion(id);
            return true;
        }
        return false;
    }
}

// ============================================================================
// USAGE DEMONSTRATION
// ============================================================================

public class SRPDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║     SINGLE RESPONSIBILITY PRINCIPLE DEMONSTRATION        ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Bad Example
        System.out.println("━━━ BAD Example: Multiple Responsibilities ━━━\n");
        demonstrateBadExample();
        
        System.out.println("\n━━━ GOOD Example: Single Responsibility ━━━\n");
        demonstrateGoodExample();
        
        System.out.println("\n━━━ Benefits Demonstration ━━━\n");
        demonstrateBenefits();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║               SRP DEMO COMPLETED!                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void demonstrateBadExample() {
        System.out.println("UserManagerBad class has 5 different responsibilities:");
        System.out.println("  1. User data management");
        System.out.println("  2. Database operations");
        System.out.println("  3. Email notifications");
        System.out.println("  4. Logging");
        System.out.println("  5. Validation");
        System.out.println("\n⚠️  Problems:");
        System.out.println("  - Changes to email logic require modifying this class");
        System.out.println("  - Changes to database logic require modifying this class");
        System.out.println("  - Hard to test individual responsibilities");
        System.out.println("  - Violates SRP!");
    }
    
    private static void demonstrateGoodExample() {
        // Create individual components (each with single responsibility)
        UserRepository repository = new UserRepository();
        EmailService emailService = new EmailService("smtp.example.com", 587);
        UserLogger logger = new UserLogger();
        UserValidator validator = new UserValidator();
        
        // UserService coordinates these components
        UserService userService = new UserService(repository, emailService, logger, validator);
        
        System.out.println("Creating a new user:\n");
        
        // Create user
        User user = userService.createUser("John Doe", "john@example.com");
        
        if (user != null) {
            System.out.println("\n✓ User created successfully: " + user);
        }
        
        System.out.println("\n--- Testing validation (invalid email) ---\n");
        User invalidUser = userService.createUser("Jane", "invalid-email");
        if (invalidUser == null) {
            System.out.println("✓ Validation properly rejected invalid input");
        }
    }
    
    private static void demonstrateBenefits() {
        System.out.println("✓ Benefits of SRP:");
        System.out.println();
        System.out.println("1. TESTABILITY:");
        System.out.println("   - UserValidator can be tested independently");
        System.out.println("   - EmailService can be mocked for UserService tests");
        System.out.println();
        System.out.println("2. MAINTAINABILITY:");
        System.out.println("   - Change email provider? Only modify EmailService");
        System.out.println("   - Change database? Only modify UserRepository");
        System.out.println();
        System.out.println("3. REUSABILITY:");
        System.out.println("   - EmailService can be used by other services");
        System.out.println("   - UserValidator can validate users anywhere");
        System.out.println();
        System.out.println("4. READABILITY:");
        System.out.println("   - Each class has a clear purpose");
        System.out.println("   - Easy to understand what each class does");
        
        // Demonstrate reusability
        System.out.println("\n--- Demonstrating Reusability ---\n");
        
        UserValidator validator = new UserValidator();
        
        // Validator can be used anywhere
        System.out.println("Email validation:");
        System.out.println("  'test@example.com' valid? " + validator.isValidEmail("test@example.com"));
        System.out.println("  'invalid' valid? " + validator.isValidEmail("invalid"));
        System.out.println("  '' valid? " + validator.isValidEmail(""));
        
        System.out.println("\nName validation:");
        System.out.println("  'John' valid? " + validator.isValidName("John"));
        System.out.println("  'J' valid? " + validator.isValidName("J"));
    }
}

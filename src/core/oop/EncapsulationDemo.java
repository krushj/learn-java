package core.oop;

/**
 * EncapsulationDemo
 *
 * Demonstrates Encapsulation in Java:
 * - Data hiding using access modifiers
 * - Getters and Setters
 * - Immutable classes
 * - JavaBean conventions
 * - Benefits of encapsulation
 *
 * ENCAPSULATION = Wrapping data (variables) and code (methods) together
 *                 Restricting direct access to internal state
 *
 * BENEFITS:
 * - Data protection (validation in setters)
 * - Flexibility (change internal implementation)
 * - Maintainability (single point of access)
 * - Testability (controlled behavior)
 */
public class EncapsulationDemo {

    public static void main(String[] args) {

        // ===== BASIC ENCAPSULATION =====

        System.out.println("===== BASIC ENCAPSULATION =====");

        BankAccount account = new BankAccount("John Doe", 1000.0);

        // Cannot access private fields directly
        // account.balance = 5000;  // COMPILE ERROR

        // Must use methods (controlled access)
        System.out.println("Account holder: " + account.getAccountHolder());
        System.out.println("Balance: $" + account.getBalance());

        // Deposit with validation
        account.deposit(500);
        System.out.println("After deposit: $" + account.getBalance());

        // Withdraw with validation
        account.withdraw(200);
        System.out.println("After withdrawal: $" + account.getBalance());

        // Validation prevents invalid operations
        account.deposit(-100);  // Rejected
        account.withdraw(10000);  // Rejected

        // ===== ACCESS MODIFIERS =====

        System.out.println("\n===== ACCESS MODIFIERS =====");

        AccessModifierDemo accessDemo = new AccessModifierDemo();
        accessDemo.demonstrateAccess();

        // From outside package/class:
        // accessDemo.publicVar     ✓
        // accessDemo.protectedVar  ✓ (same package)
        // accessDemo.defaultVar    ✓ (same package)
        // accessDemo.privateVar    ✗ COMPILE ERROR

        // ===== GETTERS AND SETTERS =====

        System.out.println("\n===== GETTERS AND SETTERS =====");

        Person2 person = new Person2();

        // Using setters (with validation)
        person.setName("Alice");
        person.setAge(25);
        person.setEmail("alice@example.com");

        // Using getters
        System.out.println("Name: " + person.getName());
        System.out.println("Age: " + person.getAge());
        System.out.println("Email: " + person.getEmail());

        // Validation in action
        person.setAge(-5);  // Rejected
        person.setEmail("invalid");  // Rejected
        System.out.println("Age after invalid set: " + person.getAge());

        // ===== READ-ONLY AND WRITE-ONLY =====

        System.out.println("\n===== READ-ONLY AND WRITE-ONLY =====");

        // Read-only: only getter, no setter
        ReadOnlyExample ro = new ReadOnlyExample("readonly-value");
        System.out.println("Read-only value: " + ro.getValue());
        // ro.setValue("new");  // No such method

        // Write-only: only setter, no getter (rare)
        WriteOnlyExample wo = new WriteOnlyExample();
        wo.setPassword("secret123");
        // wo.getPassword();  // No such method - security!

        // ===== IMMUTABLE CLASSES =====

        System.out.println("\n===== IMMUTABLE CLASSES =====");

        /*
         * Immutable class rules:
         * 1. Declare class as final
         * 2. Make all fields private and final
         * 3. No setters
         * 4. Initialize all fields via constructor
         * 5. Return copies of mutable objects
         */

        ImmutablePerson immutable = new ImmutablePerson("Bob", 30);
        System.out.println("Immutable person: " + immutable.getName() + ", " + immutable.getAge());

        // Cannot modify after creation
        // immutable.setName("Alice");  // No such method

        // New object for any "change"
        ImmutablePerson updated = immutable.withAge(31);
        System.out.println("Original age: " + immutable.getAge());
        System.out.println("Updated age: " + updated.getAge());

        // ===== JAVABEAN CONVENTIONS =====

        System.out.println("\n===== JAVABEAN CONVENTIONS =====");

        /*
         * JavaBean rules:
         * 1. Public no-arg constructor
         * 2. Private instance variables
         * 3. Public getters/setters following naming convention
         * 4. Implements Serializable (optional but common)
         *
         * Naming convention:
         * - getXxx() for getters
         * - setXxx() for setters
         * - isXxx() for boolean getters
         */

        StudentBean student = new StudentBean();
        student.setId(1001);
        student.setName("Charlie");
        student.setGrade(3.8);
        student.setActive(true);

        System.out.println("Student ID: " + student.getId());
        System.out.println("Student Name: " + student.getName());
        System.out.println("Student Grade: " + student.getGrade());
        System.out.println("Is Active: " + student.isActive());

        // ===== ENCAPSULATION BENEFITS =====

        System.out.println("\n===== ENCAPSULATION BENEFITS =====");

        // 1. Data Validation
        Temperature temp = new Temperature();
        temp.setCelsius(25);
        System.out.println("Celsius: " + temp.getCelsius());
        System.out.println("Fahrenheit: " + temp.getFahrenheit());

        temp.setCelsius(-300);  // Below absolute zero - rejected

        // 2. Computed Properties
        Rectangle2 rect = new Rectangle2(5, 3);
        System.out.println("Area: " + rect.getArea());  // Computed, not stored
        System.out.println("Perimeter: " + rect.getPerimeter());

        // 3. Implementation Flexibility
        Counter counter = new Counter();
        counter.increment();
        counter.increment();
        counter.increment();
        System.out.println("Count: " + counter.getCount());
        // Internal implementation can change without affecting API

        // ===== PROTECTED ACCESS =====

        System.out.println("\n===== PROTECTED ACCESS =====");

        ChildAccess childAccess = new ChildAccess();
        childAccess.showProtectedAccess();

        // ===== PACKAGE-PRIVATE (DEFAULT) =====

        System.out.println("\n===== PACKAGE-PRIVATE ACCESS =====");

        PackagePrivateDemo ppDemo = new PackagePrivateDemo();
        System.out.println("Package-private field: " + ppDemo.packageField);
        ppDemo.packageMethod();

        System.out.println("\n===== Demo Complete =====");
    }
}

// ===== BASIC ENCAPSULATION =====

class BankAccount {
    // Private fields - hidden from outside
    private String accountHolder;
    private double balance;
    private String accountNumber;

    // Constructor
    public BankAccount(String accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.accountNumber = generateAccountNumber();
    }

    // Getter - read access
    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    // No setter for accountNumber - read-only after creation
    public String getAccountNumber() {
        return accountNumber;
    }

    // Controlled modification through methods
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds");
        }
    }

    // Private helper method - internal implementation detail
    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis();
    }
}

// ===== ACCESS MODIFIERS =====

class AccessModifierDemo {
    public int publicVar = 1;        // Accessible everywhere
    protected int protectedVar = 2;   // Same package + subclasses
    int defaultVar = 3;               // Package-private (same package only)
    private int privateVar = 4;       // This class only

    public void demonstrateAccess() {
        // All accessible within same class
        System.out.println("public: " + publicVar);
        System.out.println("protected: " + protectedVar);
        System.out.println("default: " + defaultVar);
        System.out.println("private: " + privateVar);
    }

    // Private method - only callable within this class
    private void privateMethod() {
        System.out.println("Private method");
    }
}

// ===== GETTERS AND SETTERS WITH VALIDATION =====

class Person2 {
    private String name;
    private int age;
    private String email;

    // Getter
    public String getName() {
        return name;
    }

    // Setter with validation
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            System.out.println("Invalid name");
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 0 && age <= 150) {
            this.age = age;
        } else {
            System.out.println("Invalid age: " + age);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        } else {
            System.out.println("Invalid email format");
        }
    }
}

// ===== READ-ONLY CLASS =====

class ReadOnlyExample {
    private final String value;

    public ReadOnlyExample(String value) {
        this.value = value;
    }

    // Only getter, no setter
    public String getValue() {
        return value;
    }
}

// ===== WRITE-ONLY CLASS (for security) =====

class WriteOnlyExample {
    private String password;

    // Only setter, no getter (security)
    public void setPassword(String password) {
        // Hash and store password
        this.password = hashPassword(password);
        System.out.println("Password set securely");
    }

    private String hashPassword(String password) {
        // Simplified - real implementation would use proper hashing
        return "hashed_" + password.hashCode();
    }

    // Validate without exposing password
    public boolean validatePassword(String input) {
        return hashPassword(input).equals(this.password);
    }
}

// ===== IMMUTABLE CLASS =====

final class ImmutablePerson {
    private final String name;
    private final int age;

    public ImmutablePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Only getters, no setters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // "Setter" returns new object
    public ImmutablePerson withName(String newName) {
        return new ImmutablePerson(newName, this.age);
    }

    public ImmutablePerson withAge(int newAge) {
        return new ImmutablePerson(this.name, newAge);
    }
}

// ===== JAVABEAN =====

class StudentBean implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    // Private fields
    private int id;
    private String name;
    private double grade;
    private boolean active;

    // Public no-arg constructor
    public StudentBean() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    // Boolean uses isXxx() naming
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

// ===== COMPUTED PROPERTIES =====

class Temperature {
    private double celsius;

    public double getCelsius() {
        return celsius;
    }

    public void setCelsius(double celsius) {
        if (celsius >= -273.15) {  // Absolute zero
            this.celsius = celsius;
        } else {
            System.out.println("Temperature cannot be below absolute zero");
        }
    }

    // Computed property - derived from celsius
    public double getFahrenheit() {
        return celsius * 9 / 5 + 32;
    }

    public void setFahrenheit(double fahrenheit) {
        setCelsius((fahrenheit - 32) * 5 / 9);
    }
}

class Rectangle2 {
    private double width;
    private double height;

    public Rectangle2(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() { return width; }
    public double getHeight() { return height; }

    // Computed properties
    public double getArea() {
        return width * height;
    }

    public double getPerimeter() {
        return 2 * (width + height);
    }
}

// ===== IMPLEMENTATION FLEXIBILITY =====

class Counter {
    // Internal implementation can change
    // without affecting external API
    private int count = 0;

    public void increment() {
        count++;
    }

    public void decrement() {
        if (count > 0) count--;
    }

    public int getCount() {
        return count;
    }

    public void reset() {
        count = 0;
    }
}

// ===== PROTECTED ACCESS DEMO =====

class ParentAccess {
    protected String protectedField = "Protected value";

    protected void protectedMethod() {
        System.out.println("Protected method called");
    }
}

class ChildAccess extends ParentAccess {
    public void showProtectedAccess() {
        // Can access protected members from parent
        System.out.println("Accessing protected: " + protectedField);
        protectedMethod();
    }
}

// ===== PACKAGE-PRIVATE DEMO =====

class PackagePrivateDemo {
    String packageField = "Package-private field";  // No modifier = package-private

    void packageMethod() {
        System.out.println("Package-private method called");
    }
}

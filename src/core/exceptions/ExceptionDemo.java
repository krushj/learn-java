package core.exceptions;

import java.io.*;

/**
 * ExceptionDemo
 *
 * Demonstrates ALL Exception Handling concepts in Java:
 * - Exception hierarchy
 * - try-catch-finally
 * - throw and throws
 * - Checked vs Unchecked exceptions
 * - Custom exceptions
 * - try-with-resources
 * - Multi-catch
 *
 * EXCEPTION HIERARCHY:
 * ┌──────────────────────────────────────────────────────────────┐
 * │                       Throwable                              │
 * │            ┌───────────────┴───────────────┐                 │
 * │          Error                          Exception            │
 * │     (Don't catch)              ┌───────────┴───────────┐    │
 * │                        RuntimeException            Checked   │
 * │                         (Unchecked)               Exceptions │
 * └──────────────────────────────────────────────────────────────┘
 *
 * INTERNAL WORKING:
 * - Exception objects carry stack trace information
 * - JVM unwinds stack looking for handler
 * - If no handler found, thread terminates
 * - finally block guaranteed to execute
 */
public class ExceptionDemo {

    public static void main(String[] args) {

        // ===== TRY-CATCH BASICS =====

        System.out.println("===== TRY-CATCH BASICS =====");

        // Basic try-catch
        try {
            int result = 10 / 0;  // ArithmeticException
            System.out.println("Result: " + result);  // Won't execute
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println("Execution continues after catch\n");

        // ===== MULTIPLE CATCH BLOCKS =====

        System.out.println("===== MULTIPLE CATCH BLOCKS =====");

        // Order matters: specific exceptions before general
        try {
            String[] arr = {"1", "2", "abc"};
            int value = Integer.parseInt(arr[5]);  // ArrayIndexOutOfBoundsException
            System.out.println("Value: " + value);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array index error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Number format error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("General error: " + e.getMessage());
        }

        // ===== MULTI-CATCH (Java 7+) =====

        System.out.println("\n===== MULTI-CATCH =====");

        // Single catch for multiple exception types
        try {
            processData(null);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("Caught: " + e.getClass().getSimpleName() +
                             " - " + e.getMessage());
        }

        // ===== TRY-CATCH-FINALLY =====

        System.out.println("\n===== TRY-CATCH-FINALLY =====");

        // finally always executes (except System.exit())
        try {
            System.out.println("In try block");
            int result = 10 / 2;
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.out.println("In catch block");
        } finally {
            System.out.println("In finally block - always executes");
        }

        // finally with return
        System.out.println("\nfinally with return: " + finallyWithReturn());

        // finally with exception
        System.out.println("\nfinally with exception:");
        try {
            finallyWithException();
        } catch (Exception e) {
            System.out.println("Caught in main: " + e.getMessage());
        }

        // ===== THROW KEYWORD =====

        System.out.println("\n===== THROW KEYWORD =====");

        // throw - explicitly throw exception
        try {
            validateAge(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // Re-throwing exception
        try {
            try {
                throw new RuntimeException("Original exception");
            } catch (RuntimeException e) {
                System.out.println("Caught, re-throwing...");
                throw e;  // Re-throw same exception
            }
        } catch (RuntimeException e) {
            System.out.println("Caught re-thrown: " + e.getMessage());
        }

        // ===== THROWS KEYWORD =====

        System.out.println("\n===== THROWS KEYWORD =====");

        // throws - declare exception in method signature
        // Caller must handle or declare
        try {
            readFile("nonexistent.txt");
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        // ===== CHECKED VS UNCHECKED EXCEPTIONS =====

        System.out.println("\n===== CHECKED VS UNCHECKED =====");

        /*
         * CHECKED EXCEPTIONS:
         * - Must be caught or declared
         * - Extend Exception (but not RuntimeException)
         * - Examples: IOException, SQLException, ClassNotFoundException
         *
         * UNCHECKED EXCEPTIONS:
         * - Optional to catch
         * - Extend RuntimeException or Error
         * - Examples: NullPointerException, ArrayIndexOutOfBoundsException
         */

        // Checked - must handle
        try {
            Thread.sleep(100);  // Throws checked InterruptedException
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Unchecked - optional to handle
        String str = null;
        // str.length();  // Would throw NullPointerException

        // ===== COMMON EXCEPTIONS =====

        System.out.println("\n===== COMMON EXCEPTIONS =====");

        demonstrateCommonExceptions();

        // ===== TRY-WITH-RESOURCES (Java 7+) =====

        System.out.println("\n===== TRY-WITH-RESOURCES =====");

        // Automatic resource management
        // Resources implementing AutoCloseable are auto-closed
        try (BufferedReader reader = new BufferedReader(
                new StringReader("Hello\nWorld"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Read: " + line);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Multiple resources
        try (
            StringReader sr = new StringReader("Test data");
            BufferedReader br = new BufferedReader(sr)
        ) {
            System.out.println("Multi-resource: " + br.readLine());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Custom AutoCloseable
        try (MyResource resource = new MyResource()) {
            resource.doWork();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // ===== EXCEPTION INFORMATION =====

        System.out.println("\n===== EXCEPTION INFORMATION =====");

        try {
            throw new RuntimeException("Test exception");
        } catch (RuntimeException e) {
            System.out.println("getMessage(): " + e.getMessage());
            System.out.println("toString(): " + e.toString());
            System.out.println("getClass(): " + e.getClass().getName());

            System.out.println("\nStack trace:");
            e.printStackTrace(System.out);  // Print to stdout for demo

            // Get stack trace elements
            StackTraceElement[] stackTrace = e.getStackTrace();
            System.out.println("\nTop stack element: " + stackTrace[0]);
        }

        // ===== EXCEPTION CHAINING =====

        System.out.println("\n===== EXCEPTION CHAINING =====");

        try {
            performOperation();
        } catch (Exception e) {
            System.out.println("Caught: " + e.getMessage());
            System.out.println("Cause: " + e.getCause());
        }

        // ===== CUSTOM EXCEPTIONS =====

        System.out.println("\n===== CUSTOM EXCEPTIONS =====");

        // Using custom checked exception
        try {
            validateUser("", "password");
        } catch (ValidationException e) {
            System.out.println("Validation failed: " + e.getMessage());
            System.out.println("Field: " + e.getFieldName());
        }

        // Using custom unchecked exception
        try {
            processPayment(-100);
        } catch (PaymentException e) {
            System.out.println("Payment failed: " + e.getMessage());
            System.out.println("Amount: " + e.getAmount());
        }

        // ===== BEST PRACTICES =====

        System.out.println("\n===== BEST PRACTICES =====");

        /*
         * DO:
         * 1. Catch specific exceptions, not Exception
         * 2. Log exception with full stack trace
         * 3. Clean up resources in finally or try-with-resources
         * 4. Throw early, catch late
         * 5. Use custom exceptions for business logic
         * 6. Include meaningful messages
         *
         * DON'T:
         * 1. Catch and ignore (empty catch block)
         * 2. Catch Throwable or Error
         * 3. Use exceptions for flow control
         * 4. Throw Exception or RuntimeException directly
         * 5. Log and rethrow the same exception
         */

        System.out.println("See comments in code for best practices");

        // ===== ASSERTIONS =====

        System.out.println("\n===== ASSERTIONS =====");

        // Enable with: java -ea ExceptionDemo
        int value = 10;
        assert value > 0 : "Value must be positive";
        System.out.println("Assertion passed (run with -ea to enable)");

        // Assertions are disabled by default
        // Use for development/testing, not production validation

        System.out.println("\n===== Demo Complete =====");
    }

    // ===== HELPER METHODS =====

    static void processData(String data) {
        if (data == null) {
            throw new NullPointerException("Data cannot be null");
        }
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Data cannot be empty");
        }
    }

    static String finallyWithReturn() {
        try {
            return "from try";
        } finally {
            System.out.println("finally executed before return");
            // return "from finally";  // DON'T do this - overrides try's return
        }
    }

    static void finallyWithException() {
        try {
            throw new RuntimeException("Exception in try");
        } finally {
            System.out.println("finally executes even with exception");
            // throw new RuntimeException("Exception in finally");  // DON'T - hides original
        }
    }

    static void validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative: " + age);
        }
    }

    static void readFile(String filename) throws IOException {
        throw new IOException("File not found: " + filename);
    }

    static void demonstrateCommonExceptions() {
        // NullPointerException
        try {
            String s = null;
            s.length();
        } catch (NullPointerException e) {
            System.out.println("NullPointerException caught");
        }

        // ArrayIndexOutOfBoundsException
        try {
            int[] arr = new int[3];
            int val = arr[5];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ArrayIndexOutOfBoundsException caught");
        }

        // NumberFormatException
        try {
            int num = Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException caught");
        }

        // ClassCastException
        try {
            Object obj = "String";
            Integer num = (Integer) obj;
        } catch (ClassCastException e) {
            System.out.println("ClassCastException caught");
        }

        // IllegalArgumentException
        try {
            Thread.sleep(-1);
        } catch (IllegalArgumentException | InterruptedException e) {
            System.out.println(e.getClass().getSimpleName() + " caught");
        }
    }

    static void performOperation() {
        try {
            // Simulating lower-level exception
            throw new IOException("Low-level I/O error");
        } catch (IOException e) {
            // Wrap in higher-level exception
            throw new RuntimeException("Operation failed", e);  // e is the cause
        }
    }

    static void validateUser(String username, String password) throws ValidationException {
        if (username == null || username.isEmpty()) {
            throw new ValidationException("Username is required", "username");
        }
        if (password == null || password.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters", "password");
        }
    }

    static void processPayment(double amount) {
        if (amount <= 0) {
            throw new PaymentException("Invalid payment amount", amount);
        }
    }
}

// ===== CUSTOM RESOURCE =====

class MyResource implements AutoCloseable {
    public MyResource() {
        System.out.println("Resource opened");
    }

    public void doWork() {
        System.out.println("Resource working...");
    }

    @Override
    public void close() {
        System.out.println("Resource closed automatically");
    }
}

// ===== CUSTOM CHECKED EXCEPTION =====

class ValidationException extends Exception {
    private String fieldName;

    public ValidationException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}

// ===== CUSTOM UNCHECKED EXCEPTION =====

class PaymentException extends RuntimeException {
    private double amount;

    public PaymentException(String message, double amount) {
        super(message);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

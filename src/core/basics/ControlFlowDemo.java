package core.basics;

/**
 * ControlFlowDemo
 *
 * Demonstrates ALL Java control flow statements:
 * - Decision Making: if, if-else, if-else-if, switch
 * - Loops: for, while, do-while, for-each
 * - Jump Statements: break, continue, return, labeled
 *
 * INTERNAL WORKING:
 * - JVM uses conditional branching bytecode instructions
 * - switch uses tableswitch or lookupswitch bytecode
 * - Loops compile to conditional jumps
 * - break/continue use goto bytecode internally
 */
public class ControlFlowDemo {

    public static void main(String[] args) {

        // ===== IF STATEMENT =====

        System.out.println("===== IF STATEMENT =====");

        int age = 20;

        // Simple if
        // Executes block if condition is true
        if (age >= 18) {
            System.out.println("You are an adult");
        }

        // Single statement without braces (not recommended)
        if (age >= 18)
            System.out.println("Adult (single line)");

        // ===== IF-ELSE STATEMENT =====

        System.out.println("\n===== IF-ELSE STATEMENT =====");

        int number = 15;

        // if-else: Two branches
        if (number % 2 == 0) {
            System.out.println(number + " is even");
        } else {
            System.out.println(number + " is odd");
        }

        // ===== IF-ELSE-IF LADDER =====

        System.out.println("\n===== IF-ELSE-IF LADDER =====");

        int score = 75;

        // Multiple conditions evaluated in order
        // First true condition executes, rest skipped
        if (score >= 90) {
            System.out.println("Grade: A");
        } else if (score >= 80) {
            System.out.println("Grade: B");
        } else if (score >= 70) {
            System.out.println("Grade: C");
        } else if (score >= 60) {
            System.out.println("Grade: D");
        } else {
            System.out.println("Grade: F");
        }

        // ===== NESTED IF =====

        System.out.println("\n===== NESTED IF =====");

        int x = 10, y = 20;

        if (x > 0) {
            if (y > 0) {
                System.out.println("Both x and y are positive");
            } else {
                System.out.println("x is positive, y is not");
            }
        }

        // Better approach using logical operators
        if (x > 0 && y > 0) {
            System.out.println("Both positive (using &&)");
        }

        // ===== SWITCH STATEMENT (Traditional) =====

        System.out.println("\n===== SWITCH STATEMENT (Traditional) =====");

        int day = 3;

        // Traditional switch with break
        // Without break, execution "falls through" to next case
        switch (day) {
            case 1:
                System.out.println("Monday");
                break;
            case 2:
                System.out.println("Tuesday");
                break;
            case 3:
                System.out.println("Wednesday");
                break;
            case 4:
                System.out.println("Thursday");
                break;
            case 5:
                System.out.println("Friday");
                break;
            case 6:
            case 7:
                System.out.println("Weekend");  // Fall-through grouping
                break;
            default:
                System.out.println("Invalid day");
        }

        // Switch with String (Java 7+)
        String fruit = "apple";
        switch (fruit) {
            case "apple":
                System.out.println("It's an apple");
                break;
            case "banana":
                System.out.println("It's a banana");
                break;
            default:
                System.out.println("Unknown fruit");
        }

        // ===== SWITCH EXPRESSION (Java 14+) =====

        System.out.println("\n===== SWITCH EXPRESSION (Java 14+) =====");

        int dayNum = 5;

        // Arrow syntax (Java 14+) - no break needed, no fall-through
        // String dayName = switch (dayNum) {
        //     case 1 -> "Monday";
        //     case 2 -> "Tuesday";
        //     case 3 -> "Wednesday";
        //     case 4 -> "Thursday";
        //     case 5 -> "Friday";
        //     case 6, 7 -> "Weekend";  // Multiple cases
        //     default -> "Invalid";
        // };

        // Pre-Java 14 version:
        String dayName;
        switch (dayNum) {
            case 1: dayName = "Monday"; break;
            case 2: dayName = "Tuesday"; break;
            case 3: dayName = "Wednesday"; break;
            case 4: dayName = "Thursday"; break;
            case 5: dayName = "Friday"; break;
            case 6:
            case 7: dayName = "Weekend"; break;
            default: dayName = "Invalid";
        }
        System.out.println("Day " + dayNum + " is " + dayName);

        // Switch expression with yield (Java 14+)
        // String dayType = switch (dayNum) {
        //     case 1, 2, 3, 4, 5 -> {
        //         System.out.println("Processing weekday...");
        //         yield "Weekday";
        //     }
        //     case 6, 7 -> {
        //         System.out.println("Processing weekend...");
        //         yield "Weekend";
        //     }
        //     default -> "Invalid";
        // };

        // Pre-Java 14 version:
        String dayType;
        switch (dayNum) {
            case 1: case 2: case 3: case 4: case 5:
                System.out.println("Processing weekday...");
                dayType = "Weekday";
                break;
            case 6: case 7:
                System.out.println("Processing weekend...");
                dayType = "Weekend";
                break;
            default:
                dayType = "Invalid";
        }
        System.out.println("Day type: " + dayType);

        // ===== FOR LOOP =====

        System.out.println("\n===== FOR LOOP =====");

        // Basic for loop
        // for (initialization; condition; update)
        System.out.print("Basic for: ");
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Countdown
        System.out.print("Countdown: ");
        for (int i = 5; i >= 1; i--) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Step by 2
        System.out.print("Step by 2: ");
        for (int i = 0; i <= 10; i += 2) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Multiple variables
        System.out.print("Two variables: ");
        for (int i = 0, j = 10; i < j; i++, j--) {
            System.out.print("(" + i + "," + j + ") ");
        }
        System.out.println();

        // Infinite loop (commented out)
        // for (;;) { } // Runs forever

        // ===== WHILE LOOP =====

        System.out.println("\n===== WHILE LOOP =====");

        // Basic while loop
        // Condition checked BEFORE each iteration
        System.out.print("While loop: ");
        int count = 1;
        while (count <= 5) {
            System.out.print(count + " ");
            count++;
        }
        System.out.println();

        // May not execute at all if condition is false
        int zero = 0;
        while (zero > 0) {
            System.out.println("This won't print");
            zero--;
        }

        // ===== DO-WHILE LOOP =====

        System.out.println("\n===== DO-WHILE LOOP =====");

        // Condition checked AFTER each iteration
        // Executes at least once
        System.out.print("Do-while loop: ");
        int num = 1;
        do {
            System.out.print(num + " ");
            num++;
        } while (num <= 5);
        System.out.println();

        // Executes once even with false condition
        System.out.print("Runs once: ");
        int once = 10;
        do {
            System.out.print(once + " ");
            once++;
        } while (once < 10);
        System.out.println();

        // ===== FOR-EACH (Enhanced For) LOOP =====

        System.out.println("\n===== FOR-EACH LOOP =====");

        // Iterates over arrays and collections
        // Cannot modify index, no access to index

        // Array iteration
        int[] numbers = {10, 20, 30, 40, 50};
        System.out.print("Array for-each: ");
        for (int n : numbers) {
            System.out.print(n + " ");
        }
        System.out.println();

        // String array
        String[] names = {"Alice", "Bob", "Charlie"};
        System.out.print("String array: ");
        for (String name : names) {
            System.out.print(name + " ");
        }
        System.out.println();

        // 2D array
        int[][] matrix = {{1, 2}, {3, 4}, {5, 6}};
        System.out.println("2D array:");
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }

        // ===== NESTED LOOPS =====

        System.out.println("\n===== NESTED LOOPS =====");

        // Multiplication table
        System.out.println("Multiplication table (partial):");
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.print(i * j + "\t");
            }
            System.out.println();
        }

        // Triangle pattern
        System.out.println("\nTriangle pattern:");
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }

        // ===== BREAK STATEMENT =====

        System.out.println("\n===== BREAK STATEMENT =====");

        // Exits the innermost loop immediately
        System.out.print("Break at 5: ");
        for (int i = 1; i <= 10; i++) {
            if (i == 5) {
                break;  // Exit loop
            }
            System.out.print(i + " ");
        }
        System.out.println();

        // Break in nested loops (exits inner only)
        System.out.println("Break in nested loop:");
        for (int i = 1; i <= 3; i++) {
            System.out.print("i=" + i + ": ");
            for (int j = 1; j <= 5; j++) {
                if (j == 3) {
                    break;  // Only exits inner loop
                }
                System.out.print(j + " ");
            }
            System.out.println();
        }

        // ===== CONTINUE STATEMENT =====

        System.out.println("\n===== CONTINUE STATEMENT =====");

        // Skips current iteration, continues with next
        System.out.print("Skip 5: ");
        for (int i = 1; i <= 10; i++) {
            if (i == 5) {
                continue;  // Skip this iteration
            }
            System.out.print(i + " ");
        }
        System.out.println();

        // Skip even numbers
        System.out.print("Odd only: ");
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue;
            }
            System.out.print(i + " ");
        }
        System.out.println();

        // ===== LABELED BREAK & CONTINUE =====

        System.out.println("\n===== LABELED BREAK & CONTINUE =====");

        // Labeled break - exits the labeled loop
        System.out.println("Labeled break:");
        outer: for (int i = 1; i <= 3; i++) {
            inner: for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 2) {
                    System.out.println("Breaking outer at i=" + i + ", j=" + j);
                    break outer;  // Exits outer loop
                }
                System.out.println("i=" + i + ", j=" + j);
            }
        }

        // Labeled continue - continues outer loop
        System.out.println("\nLabeled continue:");
        outer: for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (j == 2) {
                    continue outer;  // Skip to next outer iteration
                }
                System.out.println("i=" + i + ", j=" + j);
            }
        }

        // ===== RETURN STATEMENT =====

        System.out.println("\n===== RETURN STATEMENT =====");

        // Exits the current method
        demonstrateReturn();
        int result = calculateSum(5, 10);
        System.out.println("Sum returned: " + result);

        // ===== PRACTICAL EXAMPLES =====

        System.out.println("\n===== PRACTICAL EXAMPLES =====");

        // Find first prime in range
        System.out.print("First prime after 10: ");
        for (int i = 11; i <= 100; i++) {
            if (isPrime(i)) {
                System.out.println(i);
                break;
            }
        }

        // Fizz Buzz (classic interview problem)
        System.out.print("FizzBuzz (1-15): ");
        for (int i = 1; i <= 15; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.print("FizzBuzz ");
            } else if (i % 3 == 0) {
                System.out.print("Fizz ");
            } else if (i % 5 == 0) {
                System.out.print("Buzz ");
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();

        // Menu loop pattern
        System.out.println("\nMenu loop pattern (simulated):");
        int choice = 1;
        int iterations = 0;
        while (true) {
            if (iterations++ >= 3) {  // Simulate 3 menu selections
                choice = 0;  // Exit choice
            }

            switch (choice) {
                case 1:
                    System.out.println("Processing option 1");
                    break;
                case 2:
                    System.out.println("Processing option 2");
                    break;
                case 0:
                    System.out.println("Exiting menu");
                    break;
                default:
                    System.out.println("Invalid option");
            }

            if (choice == 0) break;
        }

        System.out.println("\n===== Demo Complete =====");
    }

    // Helper method for return demonstration
    static void demonstrateReturn() {
        System.out.println("Before return");
        if (true) {
            return;  // Exits method immediately
        }
        System.out.println("This won't print");  // Unreachable
    }

    // Method with return value
    static int calculateSum(int a, int b) {
        return a + b;  // Returns value and exits
    }

    // Helper method to check prime
    static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }
}

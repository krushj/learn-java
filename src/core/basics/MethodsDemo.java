package core.basics;

import java.util.Arrays;

/**
 * MethodsDemo
 *
 * Demonstrates ALL method concepts in Java:
 * - Method declaration and calling
 * - Parameters and return types
 * - Method overloading
 * - Varargs (variable arguments)
 * - Pass by value vs Pass by reference
 * - Recursion
 * - Static vs Instance methods
 *
 * INTERNAL WORKING:
 * - Methods stored in Method Area of JVM
 * - Each call creates new stack frame
 * - Local variables stored in stack frame
 * - Stack frame destroyed on method return
 *
 * METHOD SIGNATURE:
 * [access] [modifiers] returnType methodName(parameters) [throws exceptions]
 */
public class MethodsDemo {

    // ===== INSTANCE VARIABLES =====
    private String instanceName = "Instance";
    private int counter = 0;

    // ===== STATIC VARIABLES =====
    private static int staticCounter = 0;

    public static void main(String[] args) {

        MethodsDemo demo = new MethodsDemo();

        // ===== BASIC METHOD CALLS =====

        System.out.println("===== BASIC METHOD CALLS =====");

        // Calling static methods (no object needed)
        sayHello();
        greet("Krushna");

        // Calling instance methods (need object)
        demo.instanceMethod();

        // ===== METHOD RETURN TYPES =====

        System.out.println("\n===== METHOD RETURN TYPES =====");

        // void - no return value
        printLine();

        // Primitive return
        int sum = add(10, 20);
        System.out.println("add(10, 20) = " + sum);

        double area = calculateCircleArea(5.0);
        System.out.println("Circle area (r=5): " + area);

        boolean isPositive = isPositive(10);
        System.out.println("isPositive(10): " + isPositive);

        // Object return
        String message = getMessage();
        System.out.println("getMessage(): " + message);

        int[] numbers = getNumbers();
        System.out.println("getNumbers(): " + Arrays.toString(numbers));

        // ===== METHOD PARAMETERS =====

        System.out.println("\n===== METHOD PARAMETERS =====");

        // No parameters
        noParams();

        // Single parameter
        singleParam(42);

        // Multiple parameters
        multipleParams("Java", 25, true);

        // Array parameter
        int[] arr = {1, 2, 3, 4, 5};
        printArray(arr);

        // Object parameter
        String str = "Hello";
        printStringLength(str);

        // ===== METHOD OVERLOADING =====

        System.out.println("\n===== METHOD OVERLOADING =====");

        // Same method name, different parameters
        // Resolved at compile time (compile-time polymorphism)

        System.out.println("add(int, int): " + add(5, 10));
        System.out.println("add(double, double): " + add(5.5, 10.5));
        System.out.println("add(int, int, int): " + add(5, 10, 15));
        System.out.println("add(String, String): " + add("Hello ", "World"));

        // Overloading with different parameter types
        display(10);           // int version
        display(10.5);         // double version
        display("Java");       // String version

        // Overloading with different parameter order
        printValues(10, "Ten");
        printValues("Ten", 10);

        // ===== VARARGS (Variable Arguments) =====

        System.out.println("\n===== VARARGS =====");

        // Varargs allows passing any number of arguments
        // Internally treated as array
        // Must be last parameter

        System.out.println("sum() = " + sum());
        System.out.println("sum(1) = " + sum(1));
        System.out.println("sum(1,2,3) = " + sum(1, 2, 3));
        System.out.println("sum(1,2,3,4,5) = " + sum(1, 2, 3, 4, 5));

        // Varargs with other parameters
        printAll("Numbers:", 1, 2, 3);

        // Passing array to varargs
        int[] numsArr = {10, 20, 30};
        System.out.println("sum(array) = " + sum(numsArr));

        // ===== PASS BY VALUE =====

        System.out.println("\n===== PASS BY VALUE =====");

        // Java is ALWAYS pass by value
        // Primitives: copy of value is passed
        // Objects: copy of reference is passed

        // Primitive - value is copied
        int x = 10;
        System.out.println("Before modifyPrimitive: x = " + x);
        modifyPrimitive(x);
        System.out.println("After modifyPrimitive: x = " + x);  // Still 10

        // Object - reference copy is passed
        int[] arrayRef = {1, 2, 3};
        System.out.println("Before modifyArray: " + Arrays.toString(arrayRef));
        modifyArray(arrayRef);
        System.out.println("After modifyArray: " + Arrays.toString(arrayRef));  // Modified!

        // String is immutable - appears like pass by value
        String strRef = "Hello";
        System.out.println("Before modifyString: " + strRef);
        modifyString(strRef);
        System.out.println("After modifyString: " + strRef);  // Still "Hello"

        // Reassigning reference doesn't affect original
        int[] arrayRef2 = {1, 2, 3};
        System.out.println("Before reassignArray: " + Arrays.toString(arrayRef2));
        reassignArray(arrayRef2);
        System.out.println("After reassignArray: " + Arrays.toString(arrayRef2));  // Unchanged

        // ===== STATIC VS INSTANCE METHODS =====

        System.out.println("\n===== STATIC VS INSTANCE METHODS =====");

        // Static method - belongs to class
        // Called using ClassName.methodName()
        // Cannot access instance variables directly
        staticMethod();
        MethodsDemo.staticMethod();  // Explicit class name

        // Instance method - belongs to object
        // Called using objectReference.methodName()
        // Can access both static and instance variables
        demo.instanceMethod();

        // Static context cannot access instance directly
        // instanceMethod();  // COMPILE ERROR in static main!

        // ===== METHOD CHAINING =====

        System.out.println("\n===== METHOD CHAINING =====");

        // Methods returning 'this' enable chaining
        demo.increment().increment().increment().printCounter();

        // ===== RECURSION =====

        System.out.println("\n===== RECURSION =====");

        // Method calling itself
        // Must have base case to prevent infinite recursion

        // Factorial: n! = n * (n-1)!
        System.out.println("5! = " + factorial(5));

        // Fibonacci: fib(n) = fib(n-1) + fib(n-2)
        System.out.print("Fibonacci: ");
        for (int i = 0; i < 10; i++) {
            System.out.print(fibonacci(i) + " ");
        }
        System.out.println();

        // Sum of digits
        System.out.println("Sum of digits (12345): " + sumOfDigits(12345));

        // ===== COVARIANT RETURN TYPES =====

        System.out.println("\n===== COVARIANT RETURN TYPES =====");

        // Child class can return subtype of parent's return type
        // Demonstrated in OOP section with inheritance

        System.out.println("Covariant returns allow subtype in overriding methods");

        // ===== METHOD LOCAL VARIABLES =====

        System.out.println("\n===== METHOD LOCAL VARIABLES =====");

        // Local variables must be initialized before use
        // No default values (unlike instance variables)
        // Scope limited to method block

        demonstrateLocalVariables();

        // ===== FINAL PARAMETERS =====

        System.out.println("\n===== FINAL PARAMETERS =====");

        // Final parameters cannot be reassigned within method
        finalParameter(10);

        System.out.println("\n===== Demo Complete =====");
    }

    // ===== BASIC METHODS =====

    // No parameters, no return
    static void sayHello() {
        System.out.println("Hello!");
    }

    // Single parameter, no return
    static void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }

    // No parameters, returns value
    static String getMessage() {
        return "This is a message";
    }

    // Returns array
    static int[] getNumbers() {
        return new int[]{1, 2, 3, 4, 5};
    }

    // Void method with side effect
    static void printLine() {
        System.out.println("-------------------");
    }

    // ===== RETURN TYPE METHODS =====

    // Returns int
    static int add(int a, int b) {
        return a + b;
    }

    // Returns double
    static double calculateCircleArea(double radius) {
        return Math.PI * radius * radius;
    }

    // Returns boolean
    static boolean isPositive(int num) {
        return num > 0;
    }

    // ===== PARAMETER METHODS =====

    static void noParams() {
        System.out.println("Method with no parameters");
    }

    static void singleParam(int value) {
        System.out.println("Single param: " + value);
    }

    static void multipleParams(String name, int age, boolean active) {
        System.out.println("Multiple params: name=" + name + ", age=" + age + ", active=" + active);
    }

    static void printArray(int[] arr) {
        System.out.println("Array param: " + Arrays.toString(arr));
    }

    static void printStringLength(String str) {
        System.out.println("String '" + str + "' has length: " + str.length());
    }

    // ===== OVERLOADED METHODS =====

    // Overload 1: two ints
    // Already defined above: static int add(int a, int b)

    // Overload 2: two doubles
    static double add(double a, double b) {
        return a + b;
    }

    // Overload 3: three ints
    static int add(int a, int b, int c) {
        return a + b + c;
    }

    // Overload 4: two Strings
    static String add(String a, String b) {
        return a + b;
    }

    // Different parameter types
    static void display(int value) {
        System.out.println("display(int): " + value);
    }

    static void display(double value) {
        System.out.println("display(double): " + value);
    }

    static void display(String value) {
        System.out.println("display(String): " + value);
    }

    // Different parameter order
    static void printValues(int num, String text) {
        System.out.println("printValues(int, String): " + num + ", " + text);
    }

    static void printValues(String text, int num) {
        System.out.println("printValues(String, int): " + text + ", " + num);
    }

    // ===== VARARGS METHODS =====

    // Variable number of arguments
    static int sum(int... numbers) {
        int total = 0;
        for (int n : numbers) {
            total += n;
        }
        return total;
    }

    // Varargs with other parameters (varargs must be last)
    static void printAll(String prefix, int... values) {
        System.out.print(prefix + " ");
        for (int v : values) {
            System.out.print(v + " ");
        }
        System.out.println();
    }

    // ===== PASS BY VALUE DEMONSTRATION =====

    static void modifyPrimitive(int value) {
        value = 100;  // Only modifies local copy
        System.out.println("Inside modifyPrimitive: value = " + value);
    }

    static void modifyArray(int[] arr) {
        arr[0] = 999;  // Modifies actual array (reference points to same object)
        System.out.println("Inside modifyArray: " + Arrays.toString(arr));
    }

    static void modifyString(String str) {
        str = "World";  // Creates new String, original unchanged
        System.out.println("Inside modifyString: str = " + str);
    }

    static void reassignArray(int[] arr) {
        arr = new int[]{9, 9, 9};  // Points to new array, original unchanged
        System.out.println("Inside reassignArray: " + Arrays.toString(arr));
    }

    // ===== STATIC VS INSTANCE =====

    static void staticMethod() {
        System.out.println("Static method - belongs to class");
        // Cannot access: instanceName (instance variable)
        // Can access: staticCounter (static variable)
        staticCounter++;
    }

    void instanceMethod() {
        System.out.println("Instance method - belongs to object");
        // Can access both instance and static variables
        System.out.println("  instanceName: " + instanceName);
        System.out.println("  staticCounter: " + staticCounter);
    }

    // ===== METHOD CHAINING =====

    MethodsDemo increment() {
        counter++;
        return this;  // Return this object for chaining
    }

    void printCounter() {
        System.out.println("Counter: " + counter);
    }

    // ===== RECURSIVE METHODS =====

    // Factorial: n! = n * (n-1) * ... * 1
    static long factorial(int n) {
        // Base case
        if (n <= 1) {
            return 1;
        }
        // Recursive case
        return n * factorial(n - 1);
    }

    // Fibonacci: 0, 1, 1, 2, 3, 5, 8, 13, ...
    static int fibonacci(int n) {
        // Base cases
        if (n <= 0) return 0;
        if (n == 1) return 1;
        // Recursive case
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // Sum of digits
    static int sumOfDigits(int n) {
        if (n == 0) return 0;
        return n % 10 + sumOfDigits(n / 10);
    }

    // ===== LOCAL VARIABLES =====

    static void demonstrateLocalVariables() {
        // Local variable - must initialize before use
        int localVar;
        // System.out.println(localVar);  // COMPILE ERROR - not initialized

        localVar = 10;  // Now initialized
        System.out.println("Local variable: " + localVar);

        // Block scope
        {
            int blockVar = 20;
            System.out.println("Block variable: " + blockVar);
        }
        // System.out.println(blockVar);  // COMPILE ERROR - out of scope

        // Loop variable scope
        for (int i = 0; i < 3; i++) {
            System.out.println("Loop variable i: " + i);
        }
        // System.out.println(i);  // COMPILE ERROR - out of scope
    }

    // ===== FINAL PARAMETERS =====

    static void finalParameter(final int value) {
        // value = 20;  // COMPILE ERROR - cannot reassign final
        System.out.println("Final parameter: " + value);
    }
}

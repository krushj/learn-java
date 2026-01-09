package core.basics;

/**
 * DataTypesDemo
 *
 * Demonstrates ALL Java primitive data types, reference types,
 * variables, literals, and type conversions.
 *
 * INTERNAL WORKING:
 * - Primitives stored directly on stack (for local vars)
 * - Reference types store address pointing to heap
 * - Wrapper classes enable primitives in collections
 * - Autoboxing/Unboxing handles automatic conversion
 *
 * MEMORY LAYOUT:
 * - byte:    1 byte  = 8 bits   → -128 to 127
 * - short:   2 bytes = 16 bits  → -32,768 to 32,767
 * - int:     4 bytes = 32 bits  → -2³¹ to 2³¹-1
 * - long:    8 bytes = 64 bits  → -2⁶³ to 2⁶³-1
 * - float:   4 bytes = 32 bits  → ~±3.4E38
 * - double:  8 bytes = 64 bits  → ~±1.7E308
 * - char:    2 bytes = 16 bits  → 0 to 65,535 (Unicode)
 * - boolean: 1 bit (JVM dependent)
 */
public class DataTypesDemo {

    // ===== INSTANCE VARIABLES (Default Values) =====
    // Stored on heap as part of object
    // Automatically initialized to default values

    byte instanceByte;       // default: 0
    short instanceShort;     // default: 0
    int instanceInt;         // default: 0
    long instanceLong;       // default: 0L
    float instanceFloat;     // default: 0.0f
    double instanceDouble;   // default: 0.0d
    char instanceChar;       // default: '\u0000' (null character)
    boolean instanceBoolean; // default: false
    String instanceString;   // default: null

    // ===== STATIC VARIABLES (Class Variables) =====
    // Stored in Method Area (shared across all instances)
    // Also get default values

    static int staticCounter = 0;

    // ===== CONSTANTS (Final Variables) =====
    // Must be initialized at declaration or in constructor
    // Cannot be changed after initialization

    static final double PI = 3.14159265359;
    static final int MAX_SIZE = 100;

    public static void main(String[] args) {

        // ===== PRIMITIVE DATA TYPES =====

        // ----- BYTE -----
        // Range: -128 to 127
        // Use case: Save memory in large arrays, file I/O
        byte minByte = -128;
        byte maxByte = 127;
        byte defaultByte = 0;
        System.out.println("Byte range: " + minByte + " to " + maxByte);

        // ----- SHORT -----
        // Range: -32,768 to 32,767
        // Use case: Save memory, rarely used in practice
        short minShort = -32768;
        short maxShort = 32767;
        System.out.println("Short range: " + minShort + " to " + maxShort);

        // ----- INT -----
        // Range: -2,147,483,648 to 2,147,483,647
        // Default choice for whole numbers
        int minInt = Integer.MIN_VALUE;  // -2^31
        int maxInt = Integer.MAX_VALUE;  // 2^31 - 1
        int decimal = 100;
        int binary = 0b1100100;     // Binary literal (Java 7+)
        int octal = 0144;           // Octal literal (prefix 0)
        int hex = 0x64;             // Hexadecimal literal
        int withUnderscore = 1_000_000; // Underscore for readability (Java 7+)
        System.out.println("Int: decimal=" + decimal + ", binary=" + binary + ", hex=" + hex);

        // ----- LONG -----
        // Range: -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807
        // Use case: Large numbers, timestamps
        // MUST use 'L' suffix
        long minLong = Long.MIN_VALUE;
        long maxLong = Long.MAX_VALUE;
        long bigNumber = 9_223_372_036_854_775_807L;
        long timestamp = System.currentTimeMillis();
        System.out.println("Long max: " + maxLong);
        System.out.println("Current timestamp: " + timestamp);

        // ----- FLOAT -----
        // ~6-7 decimal digits precision
        // Use case: Scientific calculations where precision isn't critical
        // MUST use 'f' suffix
        float minFloat = Float.MIN_VALUE;
        float maxFloat = Float.MAX_VALUE;
        float pi = 3.14159f;
        float scientific = 3.14e2f;  // 314.0
        System.out.println("Float pi: " + pi);

        // ----- DOUBLE -----
        // ~15-16 decimal digits precision
        // Default choice for decimal numbers
        double minDouble = Double.MIN_VALUE;
        double maxDouble = Double.MAX_VALUE;
        double precisePI = 3.141592653589793;
        double scientificDouble = 1.23e-4;  // 0.000123
        System.out.println("Double PI: " + precisePI);

        // Special floating-point values
        double positiveInf = Double.POSITIVE_INFINITY;
        double negativeInf = Double.NEGATIVE_INFINITY;
        double notANumber = Double.NaN;
        System.out.println("Special: +∞=" + positiveInf + ", -∞=" + negativeInf + ", NaN=" + notANumber);

        // ----- CHAR -----
        // Unicode character (2 bytes)
        // Range: 0 to 65,535
        char letter = 'A';
        char unicode = '\u0041';     // 'A' in Unicode
        char digit = '9';
        char special = '@';
        char escape = '\n';          // Newline escape character
        char tab = '\t';             // Tab escape character
        int charAsInt = letter;      // char can be treated as int (65 for 'A')
        System.out.println("Char: " + letter + ", Unicode value: " + charAsInt);

        // Common escape characters
        // \n = newline, \t = tab, \\ = backslash
        // \' = single quote, \" = double quote, \r = carriage return

        // ----- BOOLEAN -----
        // Only true or false
        // Cannot convert to/from other types
        boolean isJavaFun = true;
        boolean isEmpty = false;
        boolean result = (5 > 3);    // true
        System.out.println("Is Java fun? " + isJavaFun);

        // ===== TYPE CONVERSION =====

        // ----- WIDENING (Implicit/Automatic) -----
        // Smaller type → Larger type (No data loss)
        // byte → short → int → long → float → double
        // char → int

        byte b = 10;
        short s = b;      // byte to short
        int i = s;        // short to int
        long l = i;       // int to long
        float f = l;      // long to float
        double d = f;     // float to double
        System.out.println("Widening: byte " + b + " → double " + d);

        // ----- NARROWING (Explicit/Casting) -----
        // Larger type → Smaller type (Possible data loss)
        // Must use explicit cast

        double dValue = 9.78;
        int iValue = (int) dValue;    // 9 (truncates decimal)
        System.out.println("Narrowing: double " + dValue + " → int " + iValue);

        long longValue = 1000L;
        byte byteValue = (byte) longValue;  // May overflow!
        System.out.println("Narrowing: long " + longValue + " → byte " + byteValue);

        // Overflow example
        int overflowInt = 130;
        byte overflowByte = (byte) overflowInt;  // -126 (wraps around)
        System.out.println("Overflow: int " + overflowInt + " → byte " + overflowByte);

        // ===== REFERENCE TYPES =====

        // ----- STRING -----
        // Immutable sequence of characters
        // Stored in String Pool (for literals)
        String literal = "Hello";          // String pool
        String object = new String("Hello"); // Heap
        String nullString = null;           // No object reference

        // String comparison
        System.out.println("== : " + (literal == "Hello"));           // true (same pool reference)
        System.out.println("== : " + (literal == object));            // false (different objects)
        System.out.println("equals: " + literal.equals(object));      // true (same content)

        // ----- ARRAYS -----
        // Fixed-size container (reference type)
        int[] intArray = new int[5];        // Default values: all 0
        int[] initialized = {1, 2, 3, 4, 5};
        String[] stringArray = new String[3]; // Default: all null

        // ----- NULL -----
        // Special literal for reference types
        // Cannot be assigned to primitives
        String nullValue = null;
        // int nullInt = null;  // COMPILE ERROR!

        // ===== WRAPPER CLASSES =====

        // Each primitive has a corresponding wrapper class
        // Used for: Collections, null values, utility methods

        Byte wrapperByte = 10;              // Autoboxing
        Short wrapperShort = 100;
        Integer wrapperInt = 1000;
        Long wrapperLong = 10000L;
        Float wrapperFloat = 3.14f;
        Double wrapperDouble = 3.14159;
        Character wrapperChar = 'A';
        Boolean wrapperBool = true;

        // Autoboxing: primitive → wrapper
        Integer autoboxed = 100;            // int → Integer

        // Unboxing: wrapper → primitive
        int unboxed = autoboxed;            // Integer → int

        // Wrapper utility methods
        int parsed = Integer.parseInt("123");
        String intStr = Integer.toString(123);
        int maxOfTwo = Integer.max(10, 20);
        int bitCount = Integer.bitCount(15);  // Count 1s in binary

        System.out.println("Parsed: " + parsed);
        System.out.println("Max: " + maxOfTwo);
        System.out.println("Bit count of 15: " + bitCount);

        // ===== VAR KEYWORD (Java 10+) =====

        // Local Variable Type Inference
        // Compiler infers type from initializer
        var number = 100;           // inferred as int
        var name = "Java";          // inferred as String
        var list = new java.util.ArrayList<String>(); // inferred as ArrayList<String>

        // var restrictions:
        // - Only for local variables
        // - Must be initialized
        // - Cannot be null without cast
        // - Cannot be used for method parameters

        // ===== CONSTANTS & LITERALS =====

        // Integer literals
        int dec = 100;          // Decimal
        int bin = 0b1100100;    // Binary (0b prefix)
        int oct = 0144;         // Octal (0 prefix)
        int hexVal = 0x64;      // Hexadecimal (0x prefix)

        // Floating-point literals
        double d1 = 3.14;       // Standard
        double d2 = 3.14d;      // With suffix
        double d3 = 314e-2;     // Scientific notation
        float f1 = 3.14f;       // Float requires 'f'

        // Character literals
        char c1 = 'A';          // Character
        char c2 = '\u0041';     // Unicode
        char c3 = 65;           // ASCII value

        // String literals
        String s1 = "Hello";
        // Text blocks (Java 15+):
        // String s2 = """
        //             Multi-line
        //             Text Block
        //             """;
        String s2 = "Multi-line\nText Block\n(use text blocks in Java 15+)";

        // Boolean literals
        boolean t = true;
        boolean fal = false;

        // Null literal
        Object obj = null;

        System.out.println("\n===== Demo Complete =====");

        // ===== COMMON PITFALLS =====

        // 1. Integer overflow
        int overflow = Integer.MAX_VALUE + 1;  // Becomes MIN_VALUE
        System.out.println("Overflow: MAX_VALUE + 1 = " + overflow);

        // 2. Floating-point precision
        double precision = 0.1 + 0.2;  // Not exactly 0.3!
        System.out.println("0.1 + 0.2 = " + precision);

        // 3. Integer division
        int intDiv = 5 / 2;  // 2 (not 2.5)
        System.out.println("5 / 2 = " + intDiv);

        // 4. Comparing floating points
        double a = 0.1 + 0.2;
        double bVal = 0.3;
        System.out.println("0.1+0.2 == 0.3: " + (a == bVal));  // false!
        System.out.println("Use threshold: " + (Math.abs(a - bVal) < 0.0001));  // true

        // ===== TYPE SIZE METHODS =====

        System.out.println("\n===== Type Sizes =====");
        System.out.println("Byte.SIZE: " + Byte.SIZE + " bits");
        System.out.println("Short.SIZE: " + Short.SIZE + " bits");
        System.out.println("Integer.SIZE: " + Integer.SIZE + " bits");
        System.out.println("Long.SIZE: " + Long.SIZE + " bits");
        System.out.println("Float.SIZE: " + Float.SIZE + " bits");
        System.out.println("Double.SIZE: " + Double.SIZE + " bits");
        System.out.println("Character.SIZE: " + Character.SIZE + " bits");
    }
}

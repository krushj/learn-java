package core.strings;

/**
 * StringDemo
 *
 * Demonstrates ALL String concepts in Java:
 * - String creation and String Pool
 * - String immutability
 * - Common String methods
 * - String comparison
 * - String vs StringBuilder vs StringBuffer
 *
 * INTERNAL WORKING:
 * - Strings are immutable objects backed by char[] (pre-Java 9)
 * - From Java 9+: byte[] with encoding flag (compact strings)
 * - String Pool in Heap for literal strings
 * - Each modification creates new String object
 *
 * MEMORY LAYOUT:
 * ┌─────────────────────────────────────────────┐
 * │                   HEAP                       │
 * │  ┌─────────────────────────────────────┐    │
 * │  │        STRING POOL                   │    │
 * │  │  "Hello" → [H][e][l][l][o]          │    │
 * │  │  "World" → [W][o][r][l][d]          │    │
 * │  └─────────────────────────────────────┘    │
 * │  ┌─────────────────────────────────────┐    │
 * │  │     REGULAR HEAP (new String())     │    │
 * │  │  "Hello" → [H][e][l][l][o]          │    │
 * │  └─────────────────────────────────────┘    │
 * └─────────────────────────────────────────────┘
 */
public class StringDemo {

    public static void main(String[] args) {

        // ===== STRING CREATION =====

        System.out.println("===== STRING CREATION =====");

        // Method 1: String literal (uses String Pool)
        // If string exists in pool, returns reference to existing
        String literal1 = "Hello";
        String literal2 = "Hello";  // Same reference as literal1

        // Method 2: new keyword (creates on heap, not pool)
        // Always creates new object
        String newStr1 = new String("Hello");
        String newStr2 = new String("Hello");  // Different object

        // Method 3: char array
        char[] chars = {'J', 'a', 'v', 'a'};
        String fromChars = new String(chars);

        // Method 4: byte array
        byte[] bytes = {72, 101, 108, 108, 111};  // "Hello" in ASCII
        String fromBytes = new String(bytes);

        System.out.println("Literal: " + literal1);
        System.out.println("From chars: " + fromChars);
        System.out.println("From bytes: " + fromBytes);

        // ===== STRING POOL =====

        System.out.println("\n===== STRING POOL =====");

        // Literals use same reference from pool
        System.out.println("literal1 == literal2: " + (literal1 == literal2));  // true

        // new String() creates separate object
        System.out.println("literal1 == newStr1: " + (literal1 == newStr1));    // false
        System.out.println("newStr1 == newStr2: " + (newStr1 == newStr2));      // false

        // intern() adds string to pool and returns pool reference
        String interned = newStr1.intern();
        System.out.println("literal1 == interned: " + (literal1 == interned));  // true

        // ===== STRING IMMUTABILITY =====

        System.out.println("\n===== STRING IMMUTABILITY =====");

        String original = "Hello";
        String modified = original.concat(" World");

        System.out.println("Original: " + original);   // "Hello" - unchanged!
        System.out.println("Modified: " + modified);   // "Hello World"
        System.out.println("Same object? " + (original == modified));  // false

        // Each "modification" creates new String
        String s = "a";
        System.out.println("Reference: " + System.identityHashCode(s));
        s = s + "b";
        System.out.println("Reference after concat: " + System.identityHashCode(s));

        /*
         * Why Strings are immutable:
         * 1. Security - prevents modification of sensitive data
         * 2. Thread-safe - can be shared without synchronization
         * 3. Caching - hashCode can be cached
         * 4. String Pool - enables sharing of literals
         */

        // ===== STRING COMPARISON =====

        System.out.println("\n===== STRING COMPARISON =====");

        String s1 = "hello";
        String s2 = "hello";
        String s3 = new String("hello");
        String s4 = "HELLO";

        // == compares references
        System.out.println("s1 == s2: " + (s1 == s2));  // true (pool)
        System.out.println("s1 == s3: " + (s1 == s3));  // false (different objects)

        // equals() compares content
        System.out.println("s1.equals(s3): " + s1.equals(s3));  // true

        // equalsIgnoreCase() - case-insensitive
        System.out.println("s1.equalsIgnoreCase(s4): " + s1.equalsIgnoreCase(s4));  // true

        // compareTo() - lexicographic comparison
        // Returns: negative (less), 0 (equal), positive (greater)
        System.out.println("\"abc\".compareTo(\"abd\"): " + "abc".compareTo("abd"));  // -1
        System.out.println("\"abc\".compareTo(\"abc\"): " + "abc".compareTo("abc"));  // 0
        System.out.println("\"abd\".compareTo(\"abc\"): " + "abd".compareTo("abc"));  // 1

        // compareToIgnoreCase()
        System.out.println("\"ABC\".compareToIgnoreCase(\"abc\"): " +
                          "ABC".compareToIgnoreCase("abc"));  // 0

        // ===== STRING METHODS - LENGTH & ACCESS =====

        System.out.println("\n===== STRING METHODS - LENGTH & ACCESS =====");

        String str = "Hello, World!";

        // length() - number of characters
        System.out.println("Length: " + str.length());

        // charAt(index) - character at position
        System.out.println("charAt(0): " + str.charAt(0));     // H
        System.out.println("charAt(7): " + str.charAt(7));     // W

        // codePointAt(index) - Unicode code point
        System.out.println("codePointAt(0): " + str.codePointAt(0));  // 72

        // toCharArray() - convert to char[]
        char[] charArray = str.toCharArray();
        System.out.println("First 5 chars: " +
                          charArray[0] + charArray[1] + charArray[2] +
                          charArray[3] + charArray[4]);

        // getBytes() - convert to byte[]
        byte[] byteArray = str.getBytes();
        System.out.println("First byte: " + byteArray[0]);  // 72

        // ===== STRING METHODS - SEARCH =====

        System.out.println("\n===== STRING METHODS - SEARCH =====");

        String text = "Hello World, Hello Java";

        // indexOf() - first occurrence
        System.out.println("indexOf('o'): " + text.indexOf('o'));           // 4
        System.out.println("indexOf(\"Hello\"): " + text.indexOf("Hello")); // 0
        System.out.println("indexOf('o', 5): " + text.indexOf('o', 5));     // 7 (start from 5)

        // lastIndexOf() - last occurrence
        System.out.println("lastIndexOf('o'): " + text.lastIndexOf('o'));   // 20
        System.out.println("lastIndexOf(\"Hello\"): " + text.lastIndexOf("Hello")); // 13

        // contains() - check if contains substring
        System.out.println("contains(\"World\"): " + text.contains("World"));  // true
        System.out.println("contains(\"Python\"): " + text.contains("Python")); // false

        // startsWith() / endsWith()
        System.out.println("startsWith(\"Hello\"): " + text.startsWith("Hello"));  // true
        System.out.println("endsWith(\"Java\"): " + text.endsWith("Java"));        // true

        // matches() - regex matching
        System.out.println("\"abc123\".matches(\"[a-z]+[0-9]+\"): " +
                          "abc123".matches("[a-z]+[0-9]+"));  // true

        // ===== STRING METHODS - EXTRACTION =====

        System.out.println("\n===== STRING METHODS - EXTRACTION =====");

        String source = "Hello, World!";

        // substring(beginIndex) - from index to end
        System.out.println("substring(7): " + source.substring(7));  // World!

        // substring(beginIndex, endIndex) - from begin to end-1
        System.out.println("substring(0, 5): " + source.substring(0, 5));  // Hello

        // split() - split by delimiter
        String csv = "apple,banana,cherry";
        String[] fruits = csv.split(",");
        System.out.println("Split CSV:");
        for (String fruit : fruits) {
            System.out.println("  - " + fruit);
        }

        // split with limit
        String[] limited = csv.split(",", 2);
        System.out.println("Split with limit 2: " + limited[0] + " | " + limited[1]);

        // ===== STRING METHODS - TRANSFORMATION =====

        System.out.println("\n===== STRING METHODS - TRANSFORMATION =====");

        String sample = "  Hello World  ";

        // toUpperCase() / toLowerCase()
        System.out.println("toUpperCase: " + sample.toUpperCase());
        System.out.println("toLowerCase: " + sample.toLowerCase());

        // trim() - remove leading/trailing whitespace
        System.out.println("trim: [" + sample.trim() + "]");

        // strip() (Java 11+) - Unicode-aware trim
        System.out.println("strip: [" + sample.strip() + "]");
        System.out.println("stripLeading: [" + sample.stripLeading() + "]");
        System.out.println("stripTrailing: [" + sample.stripTrailing() + "]");

        // replace() - replace characters or strings
        System.out.println("replace('l', 'L'): " + "hello".replace('l', 'L'));
        System.out.println("replace(\"World\", \"Java\"): " +
                          "Hello World".replace("World", "Java"));

        // replaceAll() - with regex
        System.out.println("replaceAll(\"[0-9]\", \"*\"): " +
                          "abc123def456".replaceAll("[0-9]", "*"));

        // replaceFirst() - first occurrence only
        System.out.println("replaceFirst(\"[0-9]\", \"*\"): " +
                          "abc123def456".replaceFirst("[0-9]", "*"));

        // concat() - concatenate strings
        System.out.println("concat: " + "Hello".concat(" ").concat("World"));

        // repeat() (Java 11+)
        System.out.println("repeat(3): " + "ab".repeat(3));  // ababab

        // ===== STRING METHODS - CHECK =====

        System.out.println("\n===== STRING METHODS - CHECK =====");

        // isEmpty() - true if length is 0
        System.out.println("\"\".isEmpty(): " + "".isEmpty());        // true
        System.out.println("\"a\".isEmpty(): " + "a".isEmpty());      // false

        // isBlank() (Java 11+) - true if empty or only whitespace
        System.out.println("\"   \".isBlank(): " + "   ".isBlank());  // true
        System.out.println("\"  a  \".isBlank(): " + "  a  ".isBlank()); // false

        // ===== STRING METHODS - JOIN =====

        System.out.println("\n===== STRING METHODS - JOIN =====");

        // join() - static method to join with delimiter
        String joined = String.join(", ", "apple", "banana", "cherry");
        System.out.println("join: " + joined);  // apple, banana, cherry

        // join with array/list
        String[] arr = {"one", "two", "three"};
        System.out.println("join array: " + String.join("-", arr));

        // ===== STRING FORMATTING =====

        System.out.println("\n===== STRING FORMATTING =====");

        // format() - printf-style formatting
        String formatted = String.format("Name: %s, Age: %d, Score: %.2f",
                                        "Alice", 25, 92.567);
        System.out.println(formatted);

        // Common format specifiers
        // %s - string, %d - integer, %f - float, %n - newline
        // %c - character, %b - boolean, %x - hex, %o - octal
        System.out.println(String.format("Hex: %x, Octal: %o", 255, 255));
        System.out.println(String.format("Padded: [%10s]", "Hi"));     // Right pad
        System.out.println(String.format("Padded: [%-10s]", "Hi"));    // Left pad
        System.out.println(String.format("Zero pad: %05d", 42));       // 00042

        // formatted() (Java 15+) - instance method
        // String result = "Value: %d".formatted(42);

        // ===== STRING CONCATENATION PERFORMANCE =====

        System.out.println("\n===== CONCATENATION PERFORMANCE =====");

        // Inefficient - creates many intermediate strings
        String inefficient = "";
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            inefficient = inefficient + i;  // Creates new String each time!
        }
        long endInefficient = System.currentTimeMillis();
        System.out.println("String concat time: " + (endInefficient - start) + "ms");

        // Efficient - uses StringBuilder
        start = System.currentTimeMillis();
        StringBuilder efficient = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            efficient.append(i);
        }
        String result = efficient.toString();
        long endEfficient = System.currentTimeMillis();
        System.out.println("StringBuilder time: " + (endEfficient - start) + "ms");

        // ===== STRING vs STRINGBUILDER vs STRINGBUFFER =====

        System.out.println("\n===== STRING vs STRINGBUILDER vs STRINGBUFFER =====");

        /*
         * STRING:
         * - Immutable
         * - Thread-safe (because immutable)
         * - Slow for modifications (creates new objects)
         * - Best for: few modifications, constant strings
         *
         * STRINGBUILDER:
         * - Mutable
         * - NOT thread-safe
         * - Fast for modifications
         * - Best for: single-threaded, many modifications
         *
         * STRINGBUFFER:
         * - Mutable
         * - Thread-safe (synchronized methods)
         * - Slower than StringBuilder
         * - Best for: multi-threaded, many modifications
         */

        // StringBuilder usage
        StringBuilder sb = new StringBuilder("Hello");
        sb.append(" World");            // Append
        sb.insert(5, ",");              // Insert at position
        sb.replace(0, 5, "Hi");         // Replace range
        sb.reverse();                   // Reverse
        System.out.println("StringBuilder: " + sb);

        sb.reverse();                   // Reverse back
        sb.delete(2, 3);                // Delete range
        sb.deleteCharAt(sb.length()-1); // Delete last char
        System.out.println("After delete: " + sb);

        // Capacity management
        StringBuilder sb2 = new StringBuilder(100);  // Initial capacity
        System.out.println("Capacity: " + sb2.capacity());
        sb2.append("test");
        System.out.println("Length: " + sb2.length());

        // ===== COMMON STRING OPERATIONS =====

        System.out.println("\n===== COMMON STRING OPERATIONS =====");

        // Reverse a string
        String toReverse = "Hello";
        String reversed = new StringBuilder(toReverse).reverse().toString();
        System.out.println("Reversed: " + reversed);

        // Check palindrome
        String palindrome = "racecar";
        String reversedPalin = new StringBuilder(palindrome).reverse().toString();
        System.out.println(palindrome + " is palindrome: " +
                          palindrome.equals(reversedPalin));

        // Count character occurrences
        String countStr = "hello world";
        char target = 'l';
        long count = countStr.chars().filter(ch -> ch == target).count();
        System.out.println("Count of '" + target + "': " + count);

        // Remove whitespace
        String withSpaces = "H e l l o";
        String noSpaces = withSpaces.replaceAll("\\s", "");
        System.out.println("Without spaces: " + noSpaces);

        // ===== NULL HANDLING =====

        System.out.println("\n===== NULL HANDLING =====");

        String nullStr = null;

        // Safe null check
        if (nullStr != null && nullStr.length() > 0) {
            System.out.println(nullStr);
        }

        // Objects.requireNonNull (throws NullPointerException)
        // String safe = Objects.requireNonNull(nullStr, "String cannot be null");

        // String.valueOf handles null
        System.out.println("valueOf(null): " + String.valueOf(nullStr));  // "null"

        // Optional (Java 8+)
        String value = java.util.Optional.ofNullable(nullStr).orElse("default");
        System.out.println("Optional default: " + value);

        System.out.println("\n===== Demo Complete =====");
    }
}

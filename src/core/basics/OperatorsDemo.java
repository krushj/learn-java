package core.basics;

/**
 * OperatorsDemo
 *
 * Demonstrates ALL Java operators with examples,
 * precedence, and common use cases.
 *
 * OPERATOR CATEGORIES:
 * 1. Arithmetic      → +, -, *, /, %
 * 2. Unary           → ++, --, +, -, !, ~
 * 3. Assignment      → =, +=, -=, *=, /=, %=, etc.
 * 4. Relational      → ==, !=, <, >, <=, >=
 * 5. Logical         → &&, ||, !
 * 6. Bitwise         → &, |, ^, ~, <<, >>, >>>
 * 7. Ternary         → ? :
 * 8. instanceof      → Type checking
 *
 * PRECEDENCE (High to Low):
 * () [] .  →  ++ -- ! ~  →  * / %  →  + -  →  << >> >>>
 * →  < <= > >= instanceof  →  == !=  →  &  →  ^  →  |
 * →  &&  →  ||  →  ?:  →  = += -= etc.
 */
public class OperatorsDemo {

    public static void main(String[] args) {

        // ===== ARITHMETIC OPERATORS =====

        int a = 20, b = 7;

        // Addition (+)
        // Also used for String concatenation
        int sum = a + b;              // 27
        String concat = "Hello" + " " + "World";  // String concatenation

        // Subtraction (-)
        int diff = a - b;             // 13

        // Multiplication (*)
        int product = a * b;          // 140

        // Division (/)
        // Integer division truncates decimal
        int quotient = a / b;         // 2 (not 2.857)
        double preciseQuotient = (double) a / b;  // 2.857...

        // Modulus (%) - Remainder
        // Sign follows dividend
        int remainder = a % b;        // 6
        int negRemainder = -20 % 7;   // -6
        double floatRemainder = 20.5 % 7;  // 6.5

        System.out.println("===== ARITHMETIC OPERATORS =====");
        System.out.println(a + " + " + b + " = " + sum);
        System.out.println(a + " - " + b + " = " + diff);
        System.out.println(a + " * " + b + " = " + product);
        System.out.println(a + " / " + b + " = " + quotient);
        System.out.println(a + " % " + b + " = " + remainder);
        System.out.println("Precise division: " + preciseQuotient);

        // ===== UNARY OPERATORS =====

        System.out.println("\n===== UNARY OPERATORS =====");

        int x = 10;

        // Unary plus (+) - indicates positive (rarely used)
        int positive = +x;            // 10

        // Unary minus (-) - negates value
        int negative = -x;            // -10
        System.out.println("Unary minus: -" + x + " = " + negative);

        // Increment (++)
        // Pre-increment: increment first, then use
        int preInc = ++x;             // x becomes 11, preInc = 11
        System.out.println("Pre-increment: ++x = " + preInc + ", x = " + x);

        // Post-increment: use first, then increment
        int postInc = x++;            // postInc = 11, x becomes 12
        System.out.println("Post-increment: x++ = " + postInc + ", x = " + x);

        // Decrement (--)
        // Pre-decrement
        int preDec = --x;             // x becomes 11, preDec = 11
        System.out.println("Pre-decrement: --x = " + preDec + ", x = " + x);

        // Post-decrement
        int postDec = x--;            // postDec = 11, x becomes 10
        System.out.println("Post-decrement: x-- = " + postDec + ", x = " + x);

        // Logical NOT (!)
        boolean flag = true;
        boolean notFlag = !flag;      // false
        System.out.println("Logical NOT: !" + flag + " = " + notFlag);

        // Bitwise complement (~)
        // Inverts all bits: ~n = -(n+1)
        int num = 5;                  // Binary: 00000101
        int complement = ~num;        // Binary: 11111010 = -6
        System.out.println("Bitwise complement: ~" + num + " = " + complement);

        // ===== ASSIGNMENT OPERATORS =====

        System.out.println("\n===== ASSIGNMENT OPERATORS =====");

        int v = 10;

        // Simple assignment
        int val = 10;

        // Compound assignments
        v += 5;   // v = v + 5  → 15
        System.out.println("v += 5: " + v);

        v -= 3;   // v = v - 3  → 12
        System.out.println("v -= 3: " + v);

        v *= 2;   // v = v * 2  → 24
        System.out.println("v *= 2: " + v);

        v /= 4;   // v = v / 4  → 6
        System.out.println("v /= 4: " + v);

        v %= 4;   // v = v % 4  → 2
        System.out.println("v %= 4: " + v);

        // Bitwise compound assignments
        int bits = 12;
        bits &= 7;   // bits = bits & 7  → 4
        System.out.println("12 &= 7: " + bits);

        bits |= 8;   // bits = bits | 8  → 12
        System.out.println("4 |= 8: " + bits);

        bits ^= 3;   // bits = bits ^ 3  → 15
        System.out.println("12 ^= 3: " + bits);

        bits <<= 2;  // bits = bits << 2 → 60
        System.out.println("15 <<= 2: " + bits);

        bits >>= 1;  // bits = bits >> 1 → 30
        System.out.println("60 >>= 1: " + bits);

        // ===== RELATIONAL OPERATORS =====

        System.out.println("\n===== RELATIONAL OPERATORS =====");

        int p = 10, q = 20;

        // Equal to (==)
        // For primitives: compares values
        // For objects: compares references
        System.out.println(p + " == " + q + ": " + (p == q));  // false

        // Not equal to (!=)
        System.out.println(p + " != " + q + ": " + (p != q));  // true

        // Greater than (>)
        System.out.println(p + " > " + q + ": " + (p > q));    // false

        // Less than (<)
        System.out.println(p + " < " + q + ": " + (p < q));    // true

        // Greater than or equal (>=)
        System.out.println(p + " >= " + q + ": " + (p >= q));  // false

        // Less than or equal (<=)
        System.out.println(p + " <= " + q + ": " + (p <= q));  // true

        // Object reference comparison
        String s1 = new String("Hello");
        String s2 = new String("Hello");
        String s3 = s1;

        System.out.println("\nObject comparison:");
        System.out.println("s1 == s2: " + (s1 == s2));        // false (different objects)
        System.out.println("s1 == s3: " + (s1 == s3));        // true (same reference)
        System.out.println("s1.equals(s2): " + s1.equals(s2)); // true (same content)

        // ===== LOGICAL OPERATORS =====

        System.out.println("\n===== LOGICAL OPERATORS =====");

        boolean t = true, f = false;

        // Logical AND (&&) - Short-circuit
        // Returns true only if both operands are true
        // Skips second operand if first is false
        System.out.println("true && true: " + (t && t));    // true
        System.out.println("true && false: " + (t && f));   // false
        System.out.println("false && true: " + (f && t));   // false
        System.out.println("false && false: " + (f && f));  // false

        // Logical OR (||) - Short-circuit
        // Returns true if at least one operand is true
        // Skips second operand if first is true
        System.out.println("\ntrue || true: " + (t || t));    // true
        System.out.println("true || false: " + (t || f));     // true
        System.out.println("false || true: " + (f || t));     // true
        System.out.println("false || false: " + (f || f));    // false

        // Logical NOT (!)
        System.out.println("\n!true: " + !t);    // false
        System.out.println("!false: " + !f);    // true

        // Short-circuit demonstration
        int counter = 0;
        boolean shortCircuit = false && (++counter > 0);  // counter not incremented
        System.out.println("\nShort-circuit: counter = " + counter);  // 0

        // Non-short-circuit operators: & and |
        // These evaluate both operands always
        boolean nonShort = false & (++counter > 0);  // counter IS incremented
        System.out.println("Non-short-circuit: counter = " + counter);  // 1

        // ===== BITWISE OPERATORS =====

        System.out.println("\n===== BITWISE OPERATORS =====");

        int m = 12;  // Binary: 1100
        int n = 7;   // Binary: 0111

        // Bitwise AND (&)
        // 1 & 1 = 1, otherwise 0
        int andResult = m & n;  // 1100 & 0111 = 0100 = 4
        System.out.println(m + " & " + n + " = " + andResult);

        // Bitwise OR (|)
        // 0 | 0 = 0, otherwise 1
        int orResult = m | n;   // 1100 | 0111 = 1111 = 15
        System.out.println(m + " | " + n + " = " + orResult);

        // Bitwise XOR (^)
        // Different bits = 1, same bits = 0
        int xorResult = m ^ n;  // 1100 ^ 0111 = 1011 = 11
        System.out.println(m + " ^ " + n + " = " + xorResult);

        // Bitwise complement (~)
        int compResult = ~m;    // ~1100 = ...11110011 = -13
        System.out.println("~" + m + " = " + compResult);

        // Left shift (<<)
        // Shifts bits left, fills with 0s
        // Equivalent to multiplying by 2^n
        int leftShift = m << 2;  // 1100 << 2 = 110000 = 48
        System.out.println(m + " << 2 = " + leftShift);

        // Right shift (>>)
        // Shifts bits right, preserves sign bit
        // Equivalent to dividing by 2^n
        int rightShift = m >> 2;  // 1100 >> 2 = 0011 = 3
        System.out.println(m + " >> 2 = " + rightShift);

        // Negative number right shift (preserves sign)
        int negNum = -8;
        int negRightShift = negNum >> 2;  // -2
        System.out.println(negNum + " >> 2 = " + negRightShift);

        // Unsigned right shift (>>>)
        // Shifts bits right, fills with 0s (ignores sign)
        int unsignedShift = negNum >>> 2;
        System.out.println(negNum + " >>> 2 = " + unsignedShift);

        // Common bitwise tricks
        System.out.println("\n----- Bitwise Tricks -----");

        // Check if number is even/odd using AND
        int testNum = 17;
        boolean isEven = (testNum & 1) == 0;
        System.out.println(testNum + " is even: " + isEven);

        // Multiply by 2 using left shift
        int mult = 5 << 1;  // 5 * 2 = 10
        System.out.println("5 * 2 using <<: " + mult);

        // Divide by 2 using right shift
        int div = 20 >> 1;  // 20 / 2 = 10
        System.out.println("20 / 2 using >>: " + div);

        // Swap two numbers using XOR
        int swapA = 5, swapB = 10;
        swapA = swapA ^ swapB;
        swapB = swapA ^ swapB;
        swapA = swapA ^ swapB;
        System.out.println("After XOR swap: a=" + swapA + ", b=" + swapB);

        // ===== TERNARY OPERATOR =====

        System.out.println("\n===== TERNARY OPERATOR =====");

        // condition ? valueIfTrue : valueIfFalse

        int age = 18;
        String status = (age >= 18) ? "Adult" : "Minor";
        System.out.println("Age " + age + ": " + status);

        // Nested ternary (use sparingly for readability)
        int score = 75;
        String grade = (score >= 90) ? "A" :
                       (score >= 80) ? "B" :
                       (score >= 70) ? "C" :
                       (score >= 60) ? "D" : "F";
        System.out.println("Score " + score + ": Grade " + grade);

        // ===== INSTANCEOF OPERATOR =====

        System.out.println("\n===== INSTANCEOF OPERATOR =====");

        // Checks if object is instance of specified type
        // Returns boolean

        Object obj = "Hello";

        System.out.println("obj instanceof String: " + (obj instanceof String));  // true
        System.out.println("obj instanceof Integer: " + (obj instanceof Integer)); // false
        System.out.println("obj instanceof Object: " + (obj instanceof Object));  // true

        // Pattern matching for instanceof (Java 16+)
        // if (obj instanceof String str) {
        //     System.out.println("Pattern match: " + str.toUpperCase());
        // }
        // Pre-Java 16 version:
        if (obj instanceof String) {
            String str = (String) obj;
            System.out.println("Cast result: " + str.toUpperCase());
        }

        // Null check with instanceof
        String nullStr = null;
        System.out.println("null instanceof String: " + (nullStr instanceof String)); // false

        // ===== OPERATOR PRECEDENCE =====

        System.out.println("\n===== OPERATOR PRECEDENCE =====");

        // Without parentheses - follows precedence
        int result1 = 2 + 3 * 4;      // 2 + 12 = 14 (not 20)
        System.out.println("2 + 3 * 4 = " + result1);

        // With parentheses - overrides precedence
        int result2 = (2 + 3) * 4;    // 5 * 4 = 20
        System.out.println("(2 + 3) * 4 = " + result2);

        // Complex expression
        int result3 = 10 + 20 * 30 / 10 - 5;  // 10 + 60 - 5 = 65
        System.out.println("10 + 20 * 30 / 10 - 5 = " + result3);

        // Associativity (left-to-right for most operators)
        int result4 = 100 / 10 / 2;   // (100/10)/2 = 5
        System.out.println("100 / 10 / 2 = " + result4);

        // Assignment is right-to-left associative
        int aa, bb, cc;
        aa = bb = cc = 100;           // cc=100, bb=100, a=100
        System.out.println("a = b = c = 100: a=" + aa + ", b=" + bb + ", c=" + cc);

        // ===== COMMON PITFALLS =====

        System.out.println("\n===== COMMON PITFALLS =====");

        // 1. = vs ==
        int pitfall1 = 5;
        // if (pitfall1 = 10)  // COMPILE ERROR - assignment, not comparison
        if (pitfall1 == 10) {
            System.out.println("Equal");
        }

        // 2. Integer division
        int pitfall2 = 5 / 2;  // 2, not 2.5
        System.out.println("5 / 2 = " + pitfall2);

        // 3. Floating-point comparison
        double d1 = 0.1 + 0.2;
        double d2 = 0.3;
        System.out.println("0.1 + 0.2 == 0.3: " + (d1 == d2));  // false!
        System.out.println("Use epsilon: " + (Math.abs(d1 - d2) < 0.0001));

        // 4. Overflow
        int maxInt = Integer.MAX_VALUE;
        System.out.println("MAX_VALUE + 1 = " + (maxInt + 1));  // Negative!

        // 5. String comparison with ==
        String str1 = new String("test");
        String str2 = new String("test");
        System.out.println("str1 == str2: " + (str1 == str2));  // false
        System.out.println("str1.equals(str2): " + str1.equals(str2));  // true

        System.out.println("\n===== Demo Complete =====");
    }
}

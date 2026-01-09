package core.basics;

import java.util.Arrays;

/**
 * ArraysDemo
 *
 * Demonstrates ALL array concepts in Java:
 * - Declaration, Initialization, Access
 * - Single, Multi-dimensional, Jagged arrays
 * - Arrays utility class methods
 * - Common operations and patterns
 *
 * INTERNAL WORKING:
 * - Arrays are objects stored in heap memory
 * - Elements stored in contiguous memory locations
 * - Fixed size - cannot grow/shrink after creation
 * - Direct index access: O(1) time complexity
 * - Length stored as final instance variable
 *
 * MEMORY LAYOUT:
 * ┌─────┬─────┬─────┬─────┬─────┐
 * │  0  │  1  │  2  │  3  │  4  │  ← Indices
 * ├─────┼─────┼─────┼─────┼─────┤
 * │ 10  │ 20  │ 30  │ 40  │ 50  │  ← Values
 * └─────┴─────┴─────┴─────┴─────┘
 */
public class ArraysDemo {

    public static void main(String[] args) {

        // ===== ARRAY DECLARATION =====

        System.out.println("===== ARRAY DECLARATION =====");

        // Method 1: Declare then initialize
        int[] arr1;                    // Preferred style
        int arr2[];                    // C-style (valid but not preferred)

        // Method 2: Declare and create with size
        // Default values: numeric=0, boolean=false, object=null
        int[] numbers = new int[5];
        System.out.println("Default int[5]: " + Arrays.toString(numbers));

        // Method 3: Declare, create, and initialize
        int[] primes = new int[]{2, 3, 5, 7, 11};
        System.out.println("Primes: " + Arrays.toString(primes));

        // Method 4: Array literal (shorthand)
        int[] digits = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("Digits: " + Arrays.toString(digits));

        // Different types
        String[] names = {"Alice", "Bob", "Charlie"};
        double[] prices = {19.99, 29.99, 39.99};
        boolean[] flags = {true, false, true};
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};

        System.out.println("Strings: " + Arrays.toString(names));
        System.out.println("Doubles: " + Arrays.toString(prices));
        System.out.println("Booleans: " + Arrays.toString(flags));
        System.out.println("Chars: " + Arrays.toString(vowels));

        // ===== ACCESSING ELEMENTS =====

        System.out.println("\n===== ACCESSING ELEMENTS =====");

        int[] values = {10, 20, 30, 40, 50};

        // Access by index (0-based)
        // Time: O(1) - direct memory access
        System.out.println("First element [0]: " + values[0]);
        System.out.println("Third element [2]: " + values[2]);
        System.out.println("Last element [length-1]: " + values[values.length - 1]);

        // Modify element
        values[2] = 300;
        System.out.println("After modification: " + Arrays.toString(values));

        // Array length (property, not method)
        System.out.println("Array length: " + values.length);

        // ===== ARRAY ITERATION =====

        System.out.println("\n===== ARRAY ITERATION =====");

        int[] nums = {1, 2, 3, 4, 5};

        // Method 1: Traditional for loop
        System.out.print("Traditional for: ");
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();

        // Method 2: Enhanced for-each (read-only)
        System.out.print("For-each: ");
        for (int n : nums) {
            System.out.print(n + " ");
        }
        System.out.println();

        // Method 3: While loop
        System.out.print("While loop: ");
        int i = 0;
        while (i < nums.length) {
            System.out.print(nums[i] + " ");
            i++;
        }
        System.out.println();

        // Method 4: Reverse iteration
        System.out.print("Reverse: ");
        for (int j = nums.length - 1; j >= 0; j--) {
            System.out.print(nums[j] + " ");
        }
        System.out.println();

        // ===== DEFAULT VALUES =====

        System.out.println("\n===== DEFAULT VALUES =====");

        int[] defaultInt = new int[3];
        double[] defaultDouble = new double[3];
        boolean[] defaultBool = new boolean[3];
        String[] defaultString = new String[3];
        Object[] defaultObj = new Object[3];

        System.out.println("int default: " + Arrays.toString(defaultInt));       // [0, 0, 0]
        System.out.println("double default: " + Arrays.toString(defaultDouble)); // [0.0, 0.0, 0.0]
        System.out.println("boolean default: " + Arrays.toString(defaultBool));  // [false, false, false]
        System.out.println("String default: " + Arrays.toString(defaultString)); // [null, null, null]
        System.out.println("Object default: " + Arrays.toString(defaultObj));    // [null, null, null]

        // ===== 2D ARRAYS (Multi-dimensional) =====

        System.out.println("\n===== 2D ARRAYS =====");

        // Declaration and initialization
        int[][] matrix = new int[3][4];  // 3 rows, 4 columns

        // Initialize with values
        int[][] grid = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        // Access elements: array[row][col]
        System.out.println("Element at [1][2]: " + grid[1][2]);  // 6

        // Get dimensions
        System.out.println("Rows: " + grid.length);           // 3
        System.out.println("Columns: " + grid[0].length);     // 3

        // Print 2D array
        System.out.println("2D Array:");
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }

        // Using Arrays.deepToString for 2D
        System.out.println("Using deepToString: " + Arrays.deepToString(grid));

        // ===== JAGGED ARRAYS =====

        System.out.println("\n===== JAGGED ARRAYS =====");

        // Arrays with different row lengths
        int[][] jagged = new int[3][];
        jagged[0] = new int[]{1, 2};           // 2 elements
        jagged[1] = new int[]{3, 4, 5, 6};     // 4 elements
        jagged[2] = new int[]{7, 8, 9};        // 3 elements

        System.out.println("Jagged array:");
        for (int[] row : jagged) {
            System.out.println(Arrays.toString(row));
        }

        // Pascal's triangle example
        int[][] pascal = new int[5][];
        for (int n = 0; n < 5; n++) {
            pascal[n] = new int[n + 1];
            pascal[n][0] = pascal[n][n] = 1;
            for (int k = 1; k < n; k++) {
                pascal[n][k] = pascal[n - 1][k - 1] + pascal[n - 1][k];
            }
        }

        System.out.println("\nPascal's Triangle:");
        for (int[] row : pascal) {
            System.out.println(Arrays.toString(row));
        }

        // ===== 3D ARRAYS =====

        System.out.println("\n===== 3D ARRAYS =====");

        int[][][] cube = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}}
        };

        System.out.println("3D array [1][0][1]: " + cube[1][0][1]);  // 6
        System.out.println("3D array: " + Arrays.deepToString(cube));

        // ===== ARRAYS UTILITY CLASS =====

        System.out.println("\n===== ARRAYS UTILITY CLASS =====");

        int[] array = {5, 2, 8, 1, 9, 3, 7, 4, 6};

        // Arrays.toString() - String representation
        System.out.println("toString: " + Arrays.toString(array));

        // Arrays.sort() - Sorts in ascending order
        // Uses Dual-Pivot Quicksort for primitives
        // Time: O(n log n)
        int[] toSort = array.clone();
        Arrays.sort(toSort);
        System.out.println("Sorted: " + Arrays.toString(toSort));

        // Partial sort
        int[] partialSort = {5, 2, 8, 1, 9};
        Arrays.sort(partialSort, 1, 4);  // Sort indices 1-3
        System.out.println("Partial sort [1,4): " + Arrays.toString(partialSort));

        // Arrays.binarySearch() - Find element in sorted array
        // Time: O(log n)
        // Returns index if found, negative value if not
        int[] sorted = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int index = Arrays.binarySearch(sorted, 5);
        System.out.println("binarySearch(5): index = " + index);

        int notFound = Arrays.binarySearch(sorted, 10);
        System.out.println("binarySearch(10): " + notFound + " (negative = not found)");

        // Arrays.fill() - Fill array with value
        int[] filled = new int[5];
        Arrays.fill(filled, 42);
        System.out.println("Filled with 42: " + Arrays.toString(filled));

        // Partial fill
        Arrays.fill(filled, 1, 4, 100);  // Fill indices 1-3
        System.out.println("Partial fill: " + Arrays.toString(filled));

        // Arrays.copyOf() - Create copy with new length
        int[] original = {1, 2, 3};
        int[] copy = Arrays.copyOf(original, 5);  // Pads with 0s
        System.out.println("copyOf(3 -> 5): " + Arrays.toString(copy));

        int[] truncated = Arrays.copyOf(original, 2);  // Truncates
        System.out.println("copyOf(3 -> 2): " + Arrays.toString(truncated));

        // Arrays.copyOfRange() - Copy portion of array
        int[] source = {0, 1, 2, 3, 4, 5};
        int[] range = Arrays.copyOfRange(source, 2, 5);
        System.out.println("copyOfRange(2,5): " + Arrays.toString(range));

        // Arrays.equals() - Compare arrays
        int[] a1 = {1, 2, 3};
        int[] a2 = {1, 2, 3};
        int[] a3 = {1, 2, 4};

        System.out.println("equals([1,2,3], [1,2,3]): " + Arrays.equals(a1, a2));  // true
        System.out.println("equals([1,2,3], [1,2,4]): " + Arrays.equals(a1, a3));  // false

        // Arrays.deepEquals() - For nested arrays
        int[][] m1 = {{1, 2}, {3, 4}};
        int[][] m2 = {{1, 2}, {3, 4}};
        System.out.println("deepEquals: " + Arrays.deepEquals(m1, m2));  // true

        // Arrays.hashCode()
        System.out.println("hashCode: " + Arrays.hashCode(a1));

        // Arrays.mismatch() (Java 9+) - Find first differing index
        int[] arr1Mis = {1, 2, 3, 4, 5};
        int[] arr2Mis = {1, 2, 9, 4, 5};
        System.out.println("mismatch: index " + Arrays.mismatch(arr1Mis, arr2Mis));  // 2

        // ===== COMMON ARRAY OPERATIONS =====

        System.out.println("\n===== COMMON OPERATIONS =====");

        int[] data = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};

        // Find maximum
        int max = data[0];
        for (int val : data) {
            if (val > max) max = val;
        }
        System.out.println("Maximum: " + max);

        // Find minimum
        int min = data[0];
        for (int val : data) {
            if (val < min) min = val;
        }
        System.out.println("Minimum: " + min);

        // Calculate sum
        int sum = 0;
        for (int val : data) {
            sum += val;
        }
        System.out.println("Sum: " + sum);

        // Calculate average
        double avg = (double) sum / data.length;
        System.out.println("Average: " + avg);

        // Count occurrences
        int target = 5;
        int count = 0;
        for (int val : data) {
            if (val == target) count++;
        }
        System.out.println("Count of " + target + ": " + count);

        // Reverse array
        int[] toReverse = {1, 2, 3, 4, 5};
        for (int left = 0, right = toReverse.length - 1; left < right; left++, right--) {
            int temp = toReverse[left];
            toReverse[left] = toReverse[right];
            toReverse[right] = temp;
        }
        System.out.println("Reversed: " + Arrays.toString(toReverse));

        // ===== ARRAY COPY METHODS =====

        System.out.println("\n===== ARRAY COPY METHODS =====");

        int[] srcArr = {1, 2, 3, 4, 5};

        // 1. System.arraycopy() - Low-level, fastest
        int[] dest1 = new int[5];
        System.arraycopy(srcArr, 0, dest1, 0, srcArr.length);
        System.out.println("System.arraycopy: " + Arrays.toString(dest1));

        // 2. Arrays.copyOf() - Creates new array
        int[] dest2 = Arrays.copyOf(srcArr, srcArr.length);
        System.out.println("Arrays.copyOf: " + Arrays.toString(dest2));

        // 3. clone() - Object method
        int[] dest3 = srcArr.clone();
        System.out.println("clone(): " + Arrays.toString(dest3));

        // 4. Manual loop copy
        int[] dest4 = new int[srcArr.length];
        for (int idx = 0; idx < srcArr.length; idx++) {
            dest4[idx] = srcArr[idx];
        }
        System.out.println("Loop copy: " + Arrays.toString(dest4));

        // ===== ARRAYS AND STREAMS (Java 8+) =====

        System.out.println("\n===== ARRAYS AND STREAMS =====");

        int[] streamArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // Convert to stream
        int streamSum = Arrays.stream(streamArr).sum();
        System.out.println("Stream sum: " + streamSum);

        // Filter and collect
        int[] evenNumbers = Arrays.stream(streamArr)
                                   .filter(n -> n % 2 == 0)
                                   .toArray();
        System.out.println("Even numbers: " + Arrays.toString(evenNumbers));

        // Map operation
        int[] squared = Arrays.stream(streamArr)
                              .map(n -> n * n)
                              .toArray();
        System.out.println("Squared: " + Arrays.toString(squared));

        // Statistics
        var stats = Arrays.stream(streamArr).summaryStatistics();
        System.out.println("Stats - Min: " + stats.getMin() +
                          ", Max: " + stats.getMax() +
                          ", Avg: " + stats.getAverage());

        // ===== COMMON PITFALLS =====

        System.out.println("\n===== COMMON PITFALLS =====");

        // 1. ArrayIndexOutOfBoundsException
        int[] small = {1, 2, 3};
        try {
            System.out.println(small[5]);  // Index 5 doesn't exist
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Pitfall 1: " + e.getClass().getSimpleName());
        }

        // 2. NullPointerException
        String[] nullArr = new String[3];  // All elements are null
        try {
            System.out.println(nullArr[0].length());  // NPE!
        } catch (NullPointerException e) {
            System.out.println("Pitfall 2: " + e.getClass().getSimpleName());
        }

        // 3. Shallow copy for object arrays
        String[] origStr = {"Hello", "World"};
        String[] copyStr = origStr.clone();  // Shallow copy!
        copyStr[0] = "Hi";                   // Modifies copy only
        System.out.println("Original: " + Arrays.toString(origStr));  // ["Hello", "World"]
        System.out.println("Copy: " + Arrays.toString(copyStr));      // ["Hi", "World"]

        // 4. == vs Arrays.equals()
        int[] arr1Check = {1, 2, 3};
        int[] arr2Check = {1, 2, 3};
        System.out.println("== comparison: " + (arr1Check == arr2Check));           // false
        System.out.println("Arrays.equals: " + Arrays.equals(arr1Check, arr2Check)); // true

        // 5. Modifying array in for-each doesn't work
        int[] modifyTest = {1, 2, 3};
        for (int n : modifyTest) {
            n = n * 2;  // Doesn't modify array!
        }
        System.out.println("After for-each modify: " + Arrays.toString(modifyTest)); // [1, 2, 3]

        System.out.println("\n===== Demo Complete =====");
    }
}

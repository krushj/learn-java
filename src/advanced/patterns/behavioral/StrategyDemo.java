package advanced.patterns.behavioral;

import java.util.*;

/**
 * ============================================================================
 * STRATEGY PATTERN
 * ============================================================================
 * 
 * PURPOSE: Defines a family of algorithms, encapsulates each one,
 * and makes them interchangeable. Strategy lets the algorithm vary
 * independently from clients that use it.
 * 
 * WHEN TO USE:
 * - Multiple algorithms for specific task
 * - Need to switch algorithms at runtime
 * - Want to hide complex algorithm implementation
 * - Avoid conditional statements for algorithm selection
 * 
 * COMPONENTS:
 * 1. Strategy - interface for algorithm
 * 2. ConcreteStrategy - implements algorithm
 * 3. Context - uses a Strategy
 * 
 * PROS:
 * - Open/Closed Principle
 * - Runtime algorithm switching
 * - Eliminates conditional statements
 * - Isolates algorithm implementation
 * - Composition over inheritance
 * 
 * CONS:
 * - Clients must be aware of different strategies
 * - Increases number of classes
 * - Overkill for few algorithms
 * 
 * REAL-WORLD EXAMPLES:
 * - java.util.Comparator
 * - Payment processing
 * - Sorting algorithms
 * - Compression algorithms
 */

// ============================================================================
// EXAMPLE 1: Payment Strategies
// ============================================================================

/**
 * Strategy interface - Payment method
 */
interface PaymentStrategy {
    void pay(double amount);
    boolean validate();
    String getPaymentType();
}

/**
 * Concrete Strategy - Credit Card Payment
 */
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cvv;
    private String expiryDate;
    private String cardholderName;
    
    public CreditCardPayment(String cardNumber, String cvv, String expiryDate, String name) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.cardholderName = name;
    }
    
    @Override
    public boolean validate() {
        System.out.println("Validating credit card: " + maskCardNumber());
        // Real validation would check card number, expiry, etc.
        return cardNumber != null && cardNumber.length() >= 13;
    }
    
    @Override
    public void pay(double amount) {
        if (validate()) {
            System.out.println("Processing credit card payment...");
            System.out.println("  Card: " + maskCardNumber());
            System.out.println("  Amount: $" + String.format("%.2f", amount));
            System.out.println("  Status: Payment successful ✓");
        } else {
            System.out.println("  Status: Payment failed - Invalid card ✗");
        }
    }
    
    @Override
    public String getPaymentType() {
        return "Credit Card";
    }
    
    private String maskCardNumber() {
        if (cardNumber.length() < 4) return "****";
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
}

/**
 * Concrete Strategy - PayPal Payment
 */
class PayPalPayment implements PaymentStrategy {
    private String email;
    private String password;
    
    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    @Override
    public boolean validate() {
        System.out.println("Authenticating PayPal account: " + email);
        return email != null && email.contains("@");
    }
    
    @Override
    public void pay(double amount) {
        if (validate()) {
            System.out.println("Processing PayPal payment...");
            System.out.println("  Account: " + email);
            System.out.println("  Amount: $" + String.format("%.2f", amount));
            System.out.println("  Status: Payment successful ✓");
        } else {
            System.out.println("  Status: Payment failed - Invalid account ✗");
        }
    }
    
    @Override
    public String getPaymentType() {
        return "PayPal";
    }
}

/**
 * Concrete Strategy - Cryptocurrency Payment
 */
class CryptoPayment implements PaymentStrategy {
    private String walletAddress;
    private String cryptoType;
    
    public CryptoPayment(String walletAddress, String cryptoType) {
        this.walletAddress = walletAddress;
        this.cryptoType = cryptoType;
    }
    
    @Override
    public boolean validate() {
        System.out.println("Validating " + cryptoType + " wallet: " + 
            walletAddress.substring(0, 8) + "...");
        return walletAddress != null && walletAddress.length() > 20;
    }
    
    @Override
    public void pay(double amount) {
        if (validate()) {
            System.out.println("Processing " + cryptoType + " payment...");
            System.out.println("  Wallet: " + walletAddress.substring(0, 8) + "...");
            System.out.println("  Amount: $" + String.format("%.2f", amount) + " in " + cryptoType);
            System.out.println("  Status: Payment successful ✓");
        } else {
            System.out.println("  Status: Payment failed - Invalid wallet ✗");
        }
    }
    
    @Override
    public String getPaymentType() {
        return cryptoType;
    }
}

/**
 * Concrete Strategy - Bank Transfer
 */
class BankTransferPayment implements PaymentStrategy {
    private String accountNumber;
    private String bankCode;
    
    public BankTransferPayment(String accountNumber, String bankCode) {
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
    }
    
    @Override
    public boolean validate() {
        System.out.println("Validating bank account: " + bankCode + " - " + maskAccount());
        return accountNumber != null && accountNumber.length() >= 8;
    }
    
    @Override
    public void pay(double amount) {
        if (validate()) {
            System.out.println("Processing bank transfer...");
            System.out.println("  Account: " + maskAccount());
            System.out.println("  Bank: " + bankCode);
            System.out.println("  Amount: $" + String.format("%.2f", amount));
            System.out.println("  Status: Transfer initiated (2-3 business days) ✓");
        } else {
            System.out.println("  Status: Transfer failed - Invalid account ✗");
        }
    }
    
    @Override
    public String getPaymentType() {
        return "Bank Transfer";
    }
    
    private String maskAccount() {
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }
}

/**
 * Context - Shopping Cart
 */
class ShoppingCart {
    private List<String> items = new ArrayList<>();
    private Map<String, Double> prices = new HashMap<>();
    private PaymentStrategy paymentStrategy;
    
    public void addItem(String item, double price) {
        items.add(item);
        prices.put(item, price);
        System.out.println("Added: " + item + " - $" + String.format("%.2f", price));
    }
    
    public double getTotal() {
        return prices.values().stream().mapToDouble(d -> d).sum();
    }
    
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
        System.out.println("\nPayment method set to: " + strategy.getPaymentType());
    }
    
    public void checkout() {
        if (paymentStrategy == null) {
            System.out.println("Please select a payment method!");
            return;
        }
        
        double total = getTotal();
        
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           CHECKOUT                     ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("\nItems:");
        for (String item : items) {
            System.out.println("  - " + item + ": $" + String.format("%.2f", prices.get(item)));
        }
        System.out.println("\nTotal: $" + String.format("%.2f", total));
        System.out.println("Payment Method: " + paymentStrategy.getPaymentType());
        System.out.println();
        
        paymentStrategy.pay(total);
        
        System.out.println("\n✓ Thank you for your purchase!\n");
    }
}

// ============================================================================
// EXAMPLE 2: Sorting Strategies
// ============================================================================

/**
 * Strategy interface - Sorting algorithm
 */
interface SortStrategy {
    void sort(int[] array);
    String getAlgorithmName();
}

/**
 * Concrete Strategy - Bubble Sort
 */
class BubbleSort implements SortStrategy {
    @Override
    public void sort(int[] array) {
        System.out.println("Using Bubble Sort...");
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
    
    @Override
    public String getAlgorithmName() {
        return "Bubble Sort - O(n²)";
    }
}

/**
 * Concrete Strategy - Quick Sort
 */
class QuickSort implements SortStrategy {
    @Override
    public void sort(int[] array) {
        System.out.println("Using Quick Sort...");
        quickSort(array, 0, array.length - 1);
    }
    
    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
    
    @Override
    public String getAlgorithmName() {
        return "Quick Sort - O(n log n)";
    }
}

/**
 * Concrete Strategy - Merge Sort
 */
class MergeSort implements SortStrategy {
    @Override
    public void sort(int[] array) {
        System.out.println("Using Merge Sort...");
        mergeSort(array, 0, array.length - 1);
    }
    
    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }
    
    private void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] L = new int[n1];
        int[] R = new int[n2];
        
        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);
        
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }
    
    @Override
    public String getAlgorithmName() {
        return "Merge Sort - O(n log n)";
    }
}

/**
 * Context - Sorter
 */
class Sorter {
    private SortStrategy strategy;
    
    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void sortArray(int[] array) {
        if (strategy == null) {
            System.out.println("No sorting strategy set!");
            return;
        }
        
        System.out.println("Algorithm: " + strategy.getAlgorithmName());
        strategy.sort(array);
    }
    
    public static int[] selectBestStrategy(int[] array) {
        // In real scenarios, you'd analyze the data to pick best algorithm
        if (array.length < 10) {
            return array; // Use simple sort for small arrays
        }
        return array;
    }
}

// ============================================================================
// EXAMPLE 3: Compression Strategies
// ============================================================================

interface CompressionStrategy {
    String compress(String data);
    String decompress(String data);
    String getName();
}

class ZipCompression implements CompressionStrategy {
    @Override
    public String compress(String data) {
        System.out.println("Compressing with ZIP algorithm...");
        return "ZIP[" + data.length() + "]:" + data.substring(0, Math.min(10, data.length())) + "...";
    }
    
    @Override
    public String decompress(String data) {
        System.out.println("Decompressing ZIP data...");
        return data;
    }
    
    @Override
    public String getName() {
        return "ZIP Compression";
    }
}

class GzipCompression implements CompressionStrategy {
    @Override
    public String compress(String data) {
        System.out.println("Compressing with GZIP algorithm...");
        return "GZIP[" + data.length() + "]:" + data.substring(0, Math.min(10, data.length())) + "...";
    }
    
    @Override
    public String decompress(String data) {
        System.out.println("Decompressing GZIP data...");
        return data;
    }
    
    @Override
    public String getName() {
        return "GZIP Compression";
    }
}

class FileCompressor {
    private CompressionStrategy strategy;
    
    public void setStrategy(CompressionStrategy strategy) {
        this.strategy = strategy;
        System.out.println("Compression strategy: " + strategy.getName());
    }
    
    public String compressFile(String fileContent) {
        return strategy.compress(fileContent);
    }
}

// ============================================================================
// USAGE DEMONSTRATION
// ============================================================================

public class StrategyDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║           STRATEGY PATTERN DEMONSTRATION                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Payment Strategies
        System.out.println("━━━ Payment Strategy Example ━━━\n");
        demonstratePaymentStrategies();
        
        // Demo 2: Sorting Strategies
        System.out.println("\n━━━ Sorting Strategy Example ━━━\n");
        demonstrateSortingStrategies();
        
        // Demo 3: Compression Strategies
        System.out.println("\n━━━ Compression Strategy Example ━━━\n");
        demonstrateCompressionStrategies();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║          STRATEGY DEMO COMPLETED!                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void demonstratePaymentStrategies() {
        ShoppingCart cart = new ShoppingCart();
        
        // Add items
        cart.addItem("Laptop", 999.99);
        cart.addItem("Mouse", 49.99);
        cart.addItem("Keyboard", 79.99);
        
        // Pay with Credit Card
        cart.setPaymentStrategy(new CreditCardPayment(
            "4111111111111111", "123", "12/25", "John Doe"));
        cart.checkout();
        
        // Same cart, different payment
        cart.setPaymentStrategy(new PayPalPayment(
            "john@example.com", "secret123"));
        cart.checkout();
        
        // Pay with Crypto
        cart.setPaymentStrategy(new CryptoPayment(
            "0x1234567890abcdef1234567890abcdef12345678", "Bitcoin"));
        cart.checkout();
        
        System.out.println("✓ Same cart, different payment strategies!");
    }
    
    private static void demonstrateSortingStrategies() {
        Sorter sorter = new Sorter();
        
        int[] array1 = {64, 34, 25, 12, 22, 11, 90};
        int[] array2 = array1.clone();
        int[] array3 = array1.clone();
        
        System.out.println("Original array: " + Arrays.toString(array1));
        System.out.println();
        
        // Bubble Sort
        sorter.setStrategy(new BubbleSort());
        sorter.sortArray(array1);
        System.out.println("Result: " + Arrays.toString(array1));
        System.out.println();
        
        // Quick Sort
        sorter.setStrategy(new QuickSort());
        sorter.sortArray(array2);
        System.out.println("Result: " + Arrays.toString(array2));
        System.out.println();
        
        // Merge Sort
        sorter.setStrategy(new MergeSort());
        sorter.sortArray(array3);
        System.out.println("Result: " + Arrays.toString(array3));
        
        System.out.println("\n✓ Same interface, different algorithms!");
    }
    
    private static void demonstrateCompressionStrategies() {
        FileCompressor compressor = new FileCompressor();
        String data = "This is some sample data that needs to be compressed for storage or transmission.";
        
        // ZIP compression
        compressor.setStrategy(new ZipCompression());
        String zipResult = compressor.compressFile(data);
        System.out.println("Result: " + zipResult);
        System.out.println();
        
        // GZIP compression
        compressor.setStrategy(new GzipCompression());
        String gzipResult = compressor.compressFile(data);
        System.out.println("Result: " + gzipResult);
        
        System.out.println("\n✓ Compression strategy easily swappable!");
    }
}

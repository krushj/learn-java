package advanced.threads;

/**
 * ============================================================================
 * THREAD SYNCHRONIZATION
 * ============================================================================
 * 
 * RACE CONDITION: When multiple threads access shared data simultaneously
 * and at least one modifies it, leading to unpredictable results.
 * 
 * SOLUTION: Synchronization ensures only one thread accesses critical section
 * at a time using locks/monitors.
 * 
 * SYNCHRONIZATION METHODS:
 * 1. Synchronized methods - entire method is locked
 * 2. Synchronized blocks - only specific code section is locked
 * 3. Explicit locks (ReentrantLock)
 * 
 * KEY CONCEPTS:
 * - Monitor/Lock: Every object in Java has an intrinsic lock
 * - Only one thread can hold the lock at a time
 * - Other threads wait until lock is released
 * - Prevents data inconsistency but may cause performance overhead
 */

/**
 * Bank Account demonstrating synchronization
 */
class BankAccount {
    private double balance;
    private String accountNumber;
    
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    /**
     * Synchronized Method Approach
     * 
     * PROS:
     * - Simple syntax
     * - Automatic lock acquisition and release
     * 
     * CONS:
     * - Locks entire method (may be inefficient)
     * - Cannot interrupt a thread waiting for lock
     */
    public synchronized void deposit(double amount) {
        System.out.println(Thread.currentThread().getName() + " depositing: $" + amount);
        double newBalance = balance + amount;
        
        try {
            Thread.sleep(100); // Simulate processing time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        balance = newBalance;
        System.out.println("New Balance after deposit: $" + balance);
    }
    
    /**
     * Synchronized Block Approach
     * 
     * PROS:
     * - Fine-grained control
     * - Only critical section is locked
     * - Better performance for long methods
     * 
     * CONS:
     * - More verbose
     * - Need to choose correct lock object
     */
    public void withdraw(double amount) {
        // Code before sync block can run concurrently
        System.out.println(Thread.currentThread().getName() + " attempting withdrawal: $" + amount);
        
        synchronized(this) { // 'this' is the lock object
            if (balance >= amount) {
                double newBalance = balance - amount;
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                balance = newBalance;
                System.out.println("Withdrawal successful. Balance: $" + balance);
            } else {
                System.out.println("Insufficient funds. Balance: $" + balance);
            }
        }
    }
    
    public synchronized double getBalance() {
        return balance;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
}

/**
 * Demonstrates race condition without synchronization
 */
class UnsafeBankAccount {
    private double balance;
    
    public UnsafeBankAccount(double initialBalance) {
        this.balance = initialBalance;
    }
    
    // NOT synchronized - race condition!
    public void deposit(double amount) {
        double newBalance = balance + amount;
        try {
            Thread.sleep(10); // Increases chance of race condition
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        balance = newBalance;
    }
    
    public double getBalance() {
        return balance;
    }
}

/**
 * Counter demonstrating synchronization need
 */
class Counter {
    private int count = 0;
    
    // Synchronized increment
    public synchronized void increment() {
        count++;
    }
    
    // Synchronized decrement
    public synchronized void decrement() {
        count--;
    }
    
    public synchronized int getCount() {
        return count;
    }
}

/**
 * Demonstrates static synchronization (class-level lock)
 */
class StaticSyncDemo {
    private static int staticCounter = 0;
    
    // Synchronized on class object (StaticSyncDemo.class)
    public static synchronized void incrementStatic() {
        staticCounter++;
        System.out.println("Static counter: " + staticCounter);
    }
    
    // Alternative: synchronized block on class
    public static void incrementStaticBlock() {
        synchronized(StaticSyncDemo.class) {
            staticCounter++;
            System.out.println("Static counter (block): " + staticCounter);
        }
    }
    
    public static int getStaticCounter() {
        return staticCounter;
    }
}

/**
 * ============================================================================
 * USAGE DEMONSTRATION
 * ============================================================================
 */
public class SynchronizationDemo {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║          SYNCHRONIZATION DEMONSTRATION                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Race Condition (Unsafe)
        System.out.println("━━━ Demo 1: Race Condition (Unsafe Account) ━━━\n");
        demonstrateRaceCondition();
        
        Thread.sleep(500);
        
        // Demo 2: Synchronized Bank Account
        System.out.println("\n━━━ Demo 2: Synchronized Bank Account ━━━\n");
        demonstrateSynchronizedAccount();
        
        Thread.sleep(500);
        
        // Demo 3: Counter with multiple threads
        System.out.println("\n━━━ Demo 3: Synchronized Counter ━━━\n");
        demonstrateSynchronizedCounter();
        
        Thread.sleep(500);
        
        // Demo 4: Static synchronization
        System.out.println("\n━━━ Demo 4: Static Synchronization ━━━\n");
        demonstrateStaticSync();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║         SYNCHRONIZATION DEMO COMPLETED!                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    /**
     * Demonstrates race condition with unsynchronized account
     */
    private static void demonstrateRaceCondition() throws InterruptedException {
        UnsafeBankAccount unsafeAccount = new UnsafeBankAccount(0);
        
        // Create multiple threads depositing $1 each
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    unsafeAccount.deposit(1);
                }
            });
        }
        
        // Start all threads
        for (Thread t : threads) {
            t.start();
        }
        
        // Wait for all threads
        for (Thread t : threads) {
            t.join();
        }
        
        // Expected: 1000, Actual: likely less due to race condition
        System.out.println("Expected balance: $1000");
        System.out.println("Actual balance (unsafe): $" + unsafeAccount.getBalance());
        System.out.println("⚠️  Race condition caused data loss!");
    }
    
    /**
     * Demonstrates synchronized bank account operations
     */
    private static void demonstrateSynchronizedAccount() throws InterruptedException {
        BankAccount account = new BankAccount("ACC-001", 1000);
        System.out.println("Initial Balance: $" + account.getBalance());
        
        // Multiple threads depositing and withdrawing
        Thread depositor1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                account.deposit(100);
            }
        }, "Depositor-1");
        
        Thread depositor2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                account.deposit(50);
            }
        }, "Depositor-2");
        
        Thread withdrawer = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                account.withdraw(75);
            }
        }, "Withdrawer-1");
        
        depositor1.start();
        depositor2.start();
        withdrawer.start();
        
        depositor1.join();
        depositor2.join();
        withdrawer.join();
        
        // Expected: 1000 + 300 + 150 - 225 = 1225
        System.out.println("\nFinal Balance: $" + account.getBalance());
        System.out.println("✓ Synchronization prevented race conditions");
    }
    
    /**
     * Demonstrates synchronized counter
     */
    private static void demonstrateSynchronizedCounter() throws InterruptedException {
        Counter counter = new Counter();
        
        // 50 threads incrementing
        Thread[] incrementors = new Thread[50];
        for (int i = 0; i < 50; i++) {
            incrementors[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    counter.increment();
                }
            });
        }
        
        // 50 threads decrementing
        Thread[] decrementors = new Thread[50];
        for (int i = 0; i < 50; i++) {
            decrementors[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    counter.decrement();
                }
            });
        }
        
        // Start all threads
        for (int i = 0; i < 50; i++) {
            incrementors[i].start();
            decrementors[i].start();
        }
        
        // Wait for all threads
        for (int i = 0; i < 50; i++) {
            incrementors[i].join();
            decrementors[i].join();
        }
        
        // Expected: 0 (5000 increments - 5000 decrements)
        System.out.println("Expected count: 0");
        System.out.println("Actual count: " + counter.getCount());
        System.out.println("✓ Synchronized counter is thread-safe");
    }
    
    /**
     * Demonstrates static synchronization
     */
    private static void demonstrateStaticSync() throws InterruptedException {
        Thread[] threads = new Thread[5];
        
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                StaticSyncDemo.incrementStatic();
            }, "Thread-" + i);
        }
        
        for (Thread t : threads) {
            t.start();
        }
        
        for (Thread t : threads) {
            t.join();
        }
        
        System.out.println("Final static counter: " + StaticSyncDemo.getStaticCounter());
    }
}

package advanced.threads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * ============================================================================
 * REENTRANTLOCK
 * ============================================================================
 * 
 * PROBLEMS WITH SYNCHRONIZED:
 * - Cannot interrupt a thread waiting for lock
 * - No timeout while waiting for lock
 * - Cannot try to acquire lock without blocking
 * - Lock acquisition is all-or-nothing
 * 
 * REENTRANTLOCK ADVANTAGES:
 * - tryLock() - attempt to acquire without blocking
 * - tryLock(timeout) - wait for specified time
 * - lockInterruptibly() - can be interrupted while waiting
 * - Multiple condition variables
 * - Fair/unfair locking policies
 * - Can check if lock is held
 * 
 * IMPORTANT: Always use try-finally to ensure lock is released
 */

/**
 * Advanced Bank Account using ReentrantLock
 */
class AdvancedBankAccount {
    private double balance;
    private final ReentrantLock lock = new ReentrantLock();
    
    // Condition for waiting when balance is insufficient
    private final Condition sufficientBalance = lock.newCondition();
    
    public AdvancedBankAccount(double initialBalance) {
        this.balance = initialBalance;
    }
    
    /**
     * Basic lock usage with try-finally
     */
    public void deposit(double amount) {
        lock.lock(); // Acquire lock
        try {
            balance += amount;
            System.out.println(Thread.currentThread().getName() + 
                " deposited: $" + amount + ", Balance: $" + balance);
            
            // Signal waiting threads that balance increased
            sufficientBalance.signalAll();
        } finally {
            lock.unlock(); // ALWAYS unlock in finally block
        }
    }
    
    /**
     * Demonstrates tryLock() - non-blocking lock attempt
     */
    public boolean tryWithdraw(double amount) {
        if (lock.tryLock()) { // Attempt to acquire lock immediately
            try {
                if (balance >= amount) {
                    balance -= amount;
                    System.out.println(Thread.currentThread().getName() + 
                        " withdrew: $" + amount + ", Balance: $" + balance);
                    return true;
                }
                System.out.println("Insufficient funds for withdrawal");
                return false;
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + 
                " could not acquire lock, skipping withdrawal");
            return false;
        }
    }
    
    /**
     * Demonstrates tryLock(timeout) - timed lock attempt
     */
    public boolean timedWithdraw(double amount, long timeoutMs) {
        try {
            // Wait up to timeoutMs for lock
            if (lock.tryLock(timeoutMs, TimeUnit.MILLISECONDS)) {
                try {
                    if (balance >= amount) {
                        balance -= amount;
                        System.out.println(Thread.currentThread().getName() + 
                            " withdrew (timed): $" + amount + ", Balance: $" + balance);
                        return true;
                    }
                    System.out.println("Insufficient funds");
                    return false;
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + 
                    " timeout while waiting for lock");
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
    
    /**
     * Demonstrates Condition variables - like wait/notify but more flexible
     */
    public void withdrawWhenSufficient(double amount) throws InterruptedException {
        lock.lock();
        try {
            // Wait until balance is sufficient
            while (balance < amount) {
                System.out.println(Thread.currentThread().getName() + 
                    " waiting for sufficient balance...");
                sufficientBalance.await(); // Releases lock and waits
            }
            
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + 
                " withdrew (condition): $" + amount + ", Balance: $" + balance);
        } finally {
            lock.unlock();
        }
    }
    
    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
    
    public boolean isLocked() {
        return lock.isLocked();
    }
    
    public int getQueueLength() {
        return lock.getQueueLength();
    }
}

/**
 * ReadWriteLock demonstration
 * - Multiple readers can access simultaneously
 * - Writers have exclusive access
 */
class SharedResource {
    private String data = "Initial Data";
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();
    
    /**
     * Multiple threads can read simultaneously
     */
    public String read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " reading data");
            Thread.sleep(100); // Simulate read time
            return data;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            readLock.unlock();
        }
    }
    
    /**
     * Only one thread can write at a time
     */
    public void write(String newData) {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " writing data");
            Thread.sleep(200); // Simulate write time
            this.data = newData;
            System.out.println(Thread.currentThread().getName() + " wrote: " + newData);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            writeLock.unlock();
        }
    }
}

/**
 * StampedLock demonstration (Java 8+)
 * - Optimistic reads for better performance
 */
class OptimisticResource {
    private double x, y;
    private final StampedLock sl = new StampedLock();
    
    /**
     * Write with exclusive lock
     */
    public void move(double deltaX, double deltaY) {
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            sl.unlockWrite(stamp);
        }
    }
    
    /**
     * Optimistic read - doesn't block writers
     */
    public double distanceFromOrigin() {
        // Try optimistic read first
        long stamp = sl.tryOptimisticRead();
        double currentX = x;
        double currentY = y;
        
        // Check if data was modified during read
        if (!sl.validate(stamp)) {
            // Fall back to regular read lock
            stamp = sl.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}

/**
 * ============================================================================
 * USAGE DEMONSTRATION
 * ============================================================================
 */
public class LocksDemo {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║               LOCKS DEMONSTRATION                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Basic ReentrantLock
        System.out.println("━━━ Demo 1: Basic ReentrantLock ━━━\n");
        demonstrateBasicLock();
        
        Thread.sleep(500);
        
        // Demo 2: tryLock
        System.out.println("\n━━━ Demo 2: tryLock (Non-blocking) ━━━\n");
        demonstrateTryLock();
        
        Thread.sleep(500);
        
        // Demo 3: Condition Variables
        System.out.println("\n━━━ Demo 3: Condition Variables ━━━\n");
        demonstrateCondition();
        
        Thread.sleep(500);
        
        // Demo 4: ReadWriteLock
        System.out.println("\n━━━ Demo 4: ReadWriteLock ━━━\n");
        demonstrateReadWriteLock();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║              LOCKS DEMO COMPLETED!                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    /**
     * Basic ReentrantLock usage
     */
    private static void demonstrateBasicLock() throws InterruptedException {
        AdvancedBankAccount account = new AdvancedBankAccount(1000);
        
        Thread depositor = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                account.deposit(100);
                try { Thread.sleep(100); } catch (InterruptedException e) { }
            }
        }, "Depositor");
        
        Thread withdrawer = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                account.tryWithdraw(150);
                try { Thread.sleep(100); } catch (InterruptedException e) { }
            }
        }, "Withdrawer");
        
        depositor.start();
        withdrawer.start();
        
        depositor.join();
        withdrawer.join();
        
        System.out.println("Final Balance: $" + account.getBalance());
    }
    
    /**
     * tryLock demonstration
     */
    private static void demonstrateTryLock() throws InterruptedException {
        AdvancedBankAccount account = new AdvancedBankAccount(500);
        
        // Thread that holds lock for a while
        Thread longOperation = new Thread(() -> {
            account.deposit(1000);
            try {
                Thread.sleep(2000); // Hold lock for 2 seconds
            } catch (InterruptedException e) { }
        }, "LongOp");
        
        // Thread that tries to get lock
        Thread quickOperation = new Thread(() -> {
            try {
                Thread.sleep(100); // Let longOperation start first
            } catch (InterruptedException e) { }
            
            // Try to withdraw with timeout
            account.timedWithdraw(100, 500); // 500ms timeout
        }, "QuickOp");
        
        longOperation.start();
        quickOperation.start();
        
        longOperation.join();
        quickOperation.join();
        
        System.out.println("Operations completed");
    }
    
    /**
     * Condition variable demonstration
     */
    private static void demonstrateCondition() throws InterruptedException {
        AdvancedBankAccount account = new AdvancedBankAccount(100);
        
        // Thread waiting for sufficient balance
        Thread withdrawer = new Thread(() -> {
            try {
                account.withdrawWhenSufficient(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "WaitingWithdrawer");
        
        // Thread that will deposit money after delay
        Thread depositor = new Thread(() -> {
            try {
                Thread.sleep(1000); // Wait 1 second
                System.out.println("Depositor adding funds...");
                account.deposit(200);
                Thread.sleep(500);
                account.deposit(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Depositor");
        
        withdrawer.start();
        depositor.start();
        
        withdrawer.join();
        depositor.join();
        
        System.out.println("Final Balance: $" + account.getBalance());
    }
    
    /**
     * ReadWriteLock demonstration
     */
    private static void demonstrateReadWriteLock() throws InterruptedException {
        SharedResource resource = new SharedResource();
        
        // Create multiple readers
        Thread[] readers = new Thread[3];
        for (int i = 0; i < 3; i++) {
            final int id = i;
            readers[i] = new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    String data = resource.read();
                    System.out.println("  Reader-" + id + " got: " + data);
                }
            }, "Reader-" + i);
        }
        
        // Create a writer
        Thread writer = new Thread(() -> {
            try {
                Thread.sleep(150); // Let readers start
            } catch (InterruptedException e) { }
            resource.write("Updated Data");
        }, "Writer");
        
        // Start all threads
        for (Thread reader : readers) {
            reader.start();
        }
        writer.start();
        
        // Wait for all
        for (Thread reader : readers) {
            reader.join();
        }
        writer.join();
        
        System.out.println("Final data: " + resource.read());
    }
}

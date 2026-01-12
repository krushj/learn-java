package advanced.threads;

/**
 * ============================================================================
 * THREAD BASICS
 * ============================================================================
 * 
 * A thread is a lightweight process that allows concurrent execution.
 * Java provides two main ways to create threads:
 * 1. Extending the Thread class
 * 2. Implementing the Runnable interface
 * 
 * KEY POINTS:
 * - Each thread has its own call stack
 * - Threads share the same memory space (heap)
 * - start() method begins thread execution
 * - run() method contains the code to execute
 * - join() method waits for thread completion
 */

/**
 * Method 1: Creating Threads by Extending Thread Class
 * 
 * ADVANTAGES:
 * - Simple and straightforward
 * - Can override other Thread methods if needed
 * 
 * DISADVANTAGES:
 * - Cannot extend another class (Java single inheritance)
 * - Tight coupling with Thread class
 * 
 * WHEN TO USE:
 * - Simple threading requirements
 * - No need to extend another class
 */
class MyThread extends Thread {
    private String threadName;
    private int iterations;
    
    /**
     * Constructor to initialize thread properties
     * @param name - Name identifier for this thread
     * @param iterations - Number of iterations to perform
     */
    public MyThread(String name, int iterations) {
        this.threadName = name;
        this.iterations = iterations;
    }
    
    /**
     * The run() method contains the code that executes in the thread
     * This method is called when start() is invoked
     */
    @Override
    public void run() {
        System.out.println(threadName + " started - Thread ID: " + Thread.currentThread().getId());
        
        for (int i = 0; i < iterations; i++) {
            System.out.println(threadName + " - Iteration: " + i);
            
            try {
                // Sleep pauses the thread for specified milliseconds
                // Other threads can execute during this time
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println(threadName + " was interrupted");
                e.printStackTrace();
            }
        }
        
        System.out.println(threadName + " finished execution");
    }
}

/**
 * Method 2: Creating Threads by Implementing Runnable Interface
 * 
 * ADVANTAGES:
 * - Can extend another class (better flexibility)
 * - Separates task from thread mechanism
 * - Better object-oriented design
 * - Can share same Runnable instance among multiple threads
 * 
 * DISADVANTAGES:
 * - Slightly more verbose
 * 
 * WHEN TO USE:
 * - When you need to extend another class
 * - Better design and maintainability required
 * - Multiple threads executing same task
 */
class MyRunnable implements Runnable {
    private String taskName;
    
    public MyRunnable(String taskName) {
        this.taskName = taskName;
    }
    
    @Override
    public void run() {
        System.out.println(taskName + " executing on: " + Thread.currentThread().getName());
        System.out.println("Thread Priority: " + Thread.currentThread().getPriority());
        System.out.println("Is Daemon: " + Thread.currentThread().isDaemon());
        
        for (int i = 0; i < 3; i++) {
            System.out.println(taskName + " - Step: " + i);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

/**
 * Demonstrates Lambda-based Thread Creation (Java 8+)
 */
class LambdaThreadDemo {
    public void demonstrate() {
        // Using lambda expression
        Thread lambdaThread = new Thread(() -> {
            System.out.println("Lambda thread running: " + Thread.currentThread().getName());
        });
        lambdaThread.start();
    }
}

/**
 * ============================================================================
 * USAGE DEMONSTRATION
 * ============================================================================
 */
public class ThreadBasicsDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║              THREAD BASICS DEMONSTRATION                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Extending Thread class
        System.out.println("━━━ Method 1: Extending Thread Class ━━━\n");
        MyThread thread1 = new MyThread("Worker-1", 3);
        MyThread thread2 = new MyThread("Worker-2", 3);
        
        thread1.start(); // Calls run() in new thread
        thread2.start();
        
        try {
            // Wait for threads to complete
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n━━━ Method 2: Implementing Runnable Interface ━━━\n");
        
        // Demo 2: Implementing Runnable
        MyRunnable task = new MyRunnable("Runnable-Task");
        Thread thread3 = new Thread(task);
        thread3.start();
        
        try {
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n━━━ Method 3: Lambda Expression (Java 8+) ━━━\n");
        
        // Demo 3: Lambda expression
        Thread lambdaThread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("Lambda Thread - Iteration: " + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Lambda-Thread");
        
        lambdaThread.start();
        
        try {
            lambdaThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n━━━ Thread Properties Demo ━━━\n");
        
        // Demo 4: Thread properties
        Thread propsThread = new Thread(() -> {
            Thread current = Thread.currentThread();
            System.out.println("Thread Name: " + current.getName());
            System.out.println("Thread ID: " + current.getId());
            System.out.println("Thread Priority: " + current.getPriority());
            System.out.println("Thread State: " + current.getState());
            System.out.println("Is Alive: " + current.isAlive());
            System.out.println("Is Daemon: " + current.isDaemon());
        }, "Properties-Thread");
        
        propsThread.setPriority(Thread.MAX_PRIORITY);
        propsThread.start();
        
        try {
            propsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n━━━ Daemon Thread Demo ━━━\n");
        
        // Demo 5: Daemon thread
        Thread daemonThread = new Thread(() -> {
            while (true) {
                System.out.println("Daemon thread running in background...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Daemon-Thread");
        
        daemonThread.setDaemon(true); // Must set before starting
        daemonThread.start();
        
        // Main thread sleeps briefly, then exits
        // Daemon thread will be terminated when main exits
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║           THREAD BASICS DEMO COMPLETED!                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
}

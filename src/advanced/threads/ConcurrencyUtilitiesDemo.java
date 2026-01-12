package advanced.threads;

import java.util.concurrent.*;
import java.util.*;

/**
 * ============================================================================
 * CONCURRENCY UTILITIES
 * ============================================================================
 * 
 * This file covers:
 * 1. Producer-Consumer Pattern with BlockingQueue
 * 2. CountDownLatch
 * 3. CyclicBarrier
 * 4. Semaphore
 */

// ============================================================================
// PRODUCER-CONSUMER PATTERN
// ============================================================================

/**
 * PRODUCER-CONSUMER PATTERN
 * -------------------------
 * 
 * CLASSIC CONCURRENCY PROBLEM:
 * - Producers generate data
 * - Consumers process data
 * - Need to coordinate between them
 * 
 * CHALLENGES:
 * - Prevent producer from adding when queue is full
 * - Prevent consumer from removing when queue is empty
 * - Thread-safe queue operations
 * 
 * BLOCKINGQUEUE SOLUTION:
 * - Automatically handles blocking/waiting
 * - Thread-safe by design
 * - Methods:
 *   - put() - blocks if queue is full
 *   - take() - blocks if queue is empty
 *   - offer()/poll() - non-blocking alternatives
 * 
 * COMMON IMPLEMENTATIONS:
 * - ArrayBlockingQueue - bounded, array-based
 * - LinkedBlockingQueue - optionally bounded, linked-list-based
 * - PriorityBlockingQueue - unbounded, priority-ordered
 * - SynchronousQueue - no internal capacity
 */
class ProducerConsumerDemo {
    
    private final BlockingQueue<Task> taskQueue;
    private volatile boolean running = true;
    
    public ProducerConsumerDemo(int queueCapacity) {
        this.taskQueue = new ArrayBlockingQueue<>(queueCapacity);
    }
    
    /**
     * Task object to be passed through queue
     */
    static class Task {
        private int id;
        private String data;
        
        public Task(int id, String data) {
            this.id = id;
            this.data = data;
        }
        
        @Override
        public String toString() {
            return "Task{id=" + id + ", data='" + data + "'}";
        }
    }
    
    /**
     * Producer - Generates tasks
     */
    class Producer implements Runnable {
        private String producerName;
        private int taskCount;
        
        public Producer(String name, int taskCount) {
            this.producerName = name;
            this.taskCount = taskCount;
        }
        
        @Override
        public void run() {
            try {
                for (int i = 1; i <= taskCount && running; i++) {
                    Task task = new Task(i, producerName + "-Data-" + i);
                    
                    // put() blocks if queue is full
                    taskQueue.put(task);
                    System.out.println(producerName + " produced: " + task);
                    
                    Thread.sleep(100); // Simulate work
                }
                System.out.println(producerName + " finished producing");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(producerName + " interrupted");
            }
        }
    }
    
    /**
     * Consumer - Processes tasks
     */
    class Consumer implements Runnable {
        private String consumerName;
        
        public Consumer(String name) {
            this.consumerName = name;
        }
        
        @Override
        public void run() {
            try {
                while (running || !taskQueue.isEmpty()) {
                    // poll with timeout instead of take to allow shutdown
                    Task task = taskQueue.poll(500, TimeUnit.MILLISECONDS);
                    if (task != null) {
                        System.out.println(consumerName + " consumed: " + task);
                        Thread.sleep(200); // Simulate processing
                    }
                }
                System.out.println(consumerName + " finished consuming");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(consumerName + " interrupted");
            }
        }
    }
    
    public void stop() {
        running = false;
    }
    
    public Producer createProducer(String name, int taskCount) {
        return new Producer(name, taskCount);
    }
    
    public Consumer createConsumer(String name) {
        return new Consumer(name);
    }
}

// ============================================================================
// COUNTDOWNLATCH
// ============================================================================

/**
 * COUNTDOWNLATCH
 * --------------
 * 
 * PURPOSE: Make one or more threads wait until a set of operations
 * performed by other threads completes.
 * 
 * HOW IT WORKS:
 * - Initialize with a count
 * - Waiting threads call await()
 * - Other threads call countDown() to decrement count
 * - When count reaches zero, all waiting threads proceed
 * 
 * KEY CHARACTERISTICS:
 * - One-time use (cannot be reset)
 * - Count can only decrease
 * - Thread-safe
 * 
 * USE CASES:
 * - Wait for multiple services to start before proceeding
 * - Ensure all threads reach a certain point before continuing
 * - Parallel task completion tracking
 * - Testing concurrent code
 */
class CountDownLatchDemo {
    
    /**
     * Simulates service startup
     */
    static class Service implements Runnable {
        private String serviceName;
        private CountDownLatch latch;
        private int startupTime;
        
        public Service(String name, CountDownLatch latch, int startupTime) {
            this.serviceName = name;
            this.latch = latch;
            this.startupTime = startupTime;
        }
        
        @Override
        public void run() {
            try {
                System.out.println(serviceName + " starting...");
                Thread.sleep(startupTime);
                System.out.println(serviceName + " started!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown();
                System.out.println("Latch count: " + latch.getCount());
            }
        }
    }
    
    public static void demonstrate() throws InterruptedException {
        int serviceCount = 3;
        CountDownLatch latch = new CountDownLatch(serviceCount);
        
        System.out.println("Starting " + serviceCount + " services...\n");
        
        // Start services
        new Thread(new Service("Database", latch, 1000)).start();
        new Thread(new Service("Cache", latch, 500)).start();
        new Thread(new Service("MessageQueue", latch, 800)).start();
        
        System.out.println("Main thread waiting for services...\n");
        
        // Wait for all services
        latch.await();
        
        System.out.println("\n✓ All services started! Application ready.");
    }
}

// ============================================================================
// CYCLICBARRIER
// ============================================================================

/**
 * CYCLICBARRIER
 * -------------
 * 
 * PURPOSE: Allow a set of threads to wait for each other to reach
 * a common barrier point.
 * 
 * DIFFERENCE FROM COUNTDOWNLATCH:
 * - Can be reused (cyclic)
 * - All threads wait for each other (symmetric)
 * - Optional barrier action when all threads arrive
 * 
 * USE CASES:
 * - Parallel algorithms requiring synchronization points
 * - Iterative computations where threads must sync each iteration
 * - Games where players must wait for each other
 */
class CyclicBarrierDemo {
    
    static class Worker implements Runnable {
        private int workerId;
        private CyclicBarrier barrier;
        private int phases;
        
        public Worker(int id, CyclicBarrier barrier, int phases) {
            this.workerId = id;
            this.barrier = barrier;
            this.phases = phases;
        }
        
        @Override
        public void run() {
            try {
                for (int phase = 1; phase <= phases; phase++) {
                    // Do work
                    System.out.println("Worker-" + workerId + " working on phase " + phase);
                    Thread.sleep((long)(Math.random() * 1000));
                    
                    System.out.println("Worker-" + workerId + " reached barrier for phase " + phase);
                    barrier.await(); // Wait for all workers
                    
                    System.out.println("Worker-" + workerId + " continuing after phase " + phase);
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public static void demonstrate() throws InterruptedException {
        final int NUM_WORKERS = 3;
        final int NUM_PHASES = 2;
        
        // Create barrier with action to execute when all threads arrive
        CyclicBarrier barrier = new CyclicBarrier(NUM_WORKERS, () -> {
            System.out.println("\n=== All workers reached barrier! ===\n");
        });
        
        Thread[] workers = new Thread[NUM_WORKERS];
        for (int i = 0; i < NUM_WORKERS; i++) {
            workers[i] = new Thread(new Worker(i + 1, barrier, NUM_PHASES));
            workers[i].start();
        }
        
        for (Thread worker : workers) {
            worker.join();
        }
        
        System.out.println("All phases completed!");
    }
}

// ============================================================================
// SEMAPHORE
// ============================================================================

/**
 * SEMAPHORE
 * ---------
 * 
 * PURPOSE: Controls access to a resource through permits.
 * 
 * HOW IT WORKS:
 * - Maintains a count of available permits
 * - acquire() - decrements permits (blocks if none available)
 * - release() - increments permits
 * 
 * TYPES:
 * - Binary Semaphore: 1 permit (like a mutex)
 * - Counting Semaphore: N permits
 * 
 * USE CASES:
 * - Limiting concurrent access to resources (connection pools)
 * - Rate limiting
 * - Resource pooling
 */
class SemaphoreDemo {
    
    /**
     * Resource pool with limited connections
     */
    static class ConnectionPool {
        private final Semaphore semaphore;
        private final String[] connections;
        private final boolean[] used;
        
        public ConnectionPool(int size) {
            this.semaphore = new Semaphore(size, true); // fair=true
            this.connections = new String[size];
            this.used = new boolean[size];
            
            for (int i = 0; i < size; i++) {
                connections[i] = "Connection-" + (i + 1);
            }
        }
        
        public String acquireConnection() throws InterruptedException {
            semaphore.acquire();
            return getNextAvailableConnection();
        }
        
        public void releaseConnection(String connection) {
            if (markAsUnused(connection)) {
                semaphore.release();
            }
        }
        
        private synchronized String getNextAvailableConnection() {
            for (int i = 0; i < connections.length; i++) {
                if (!used[i]) {
                    used[i] = true;
                    return connections[i];
                }
            }
            return null;
        }
        
        private synchronized boolean markAsUnused(String connection) {
            for (int i = 0; i < connections.length; i++) {
                if (connections[i].equals(connection)) {
                    used[i] = false;
                    return true;
                }
            }
            return false;
        }
        
        public int availablePermits() {
            return semaphore.availablePermits();
        }
    }
    
    public static void demonstrate() throws InterruptedException {
        ConnectionPool pool = new ConnectionPool(3); // Only 3 connections
        
        // Create 6 threads trying to use connections
        Thread[] clients = new Thread[6];
        for (int i = 0; i < 6; i++) {
            final int clientId = i + 1;
            clients[i] = new Thread(() -> {
                try {
                    System.out.println("Client-" + clientId + " waiting for connection. " +
                        "Available: " + pool.availablePermits());
                    
                    String connection = pool.acquireConnection();
                    System.out.println("Client-" + clientId + " acquired " + connection);
                    
                    // Use connection
                    Thread.sleep(1000);
                    
                    pool.releaseConnection(connection);
                    System.out.println("Client-" + clientId + " released " + connection);
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // Start all clients
        for (Thread client : clients) {
            client.start();
            Thread.sleep(100); // Stagger starts
        }
        
        // Wait for all
        for (Thread client : clients) {
            client.join();
        }
        
        System.out.println("All clients finished. Final available: " + pool.availablePermits());
    }
}

// ============================================================================
// MAIN DEMONSTRATION
// ============================================================================

public class ConcurrencyUtilitiesDemo {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║         CONCURRENCY UTILITIES DEMONSTRATION              ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Producer-Consumer
        System.out.println("━━━ Demo 1: Producer-Consumer Pattern ━━━\n");
        demonstrateProducerConsumer();
        
        Thread.sleep(1000);
        
        // Demo 2: CountDownLatch
        System.out.println("\n━━━ Demo 2: CountDownLatch ━━━\n");
        CountDownLatchDemo.demonstrate();
        
        Thread.sleep(1000);
        
        // Demo 3: CyclicBarrier
        System.out.println("\n━━━ Demo 3: CyclicBarrier ━━━\n");
        CyclicBarrierDemo.demonstrate();
        
        Thread.sleep(1000);
        
        // Demo 4: Semaphore
        System.out.println("\n━━━ Demo 4: Semaphore ━━━\n");
        SemaphoreDemo.demonstrate();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║      CONCURRENCY UTILITIES DEMO COMPLETED!                ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void demonstrateProducerConsumer() throws InterruptedException {
        ProducerConsumerDemo demo = new ProducerConsumerDemo(5);
        
        // Create producers and consumers
        Thread producer1 = new Thread(demo.createProducer("Producer-1", 5));
        Thread producer2 = new Thread(demo.createProducer("Producer-2", 5));
        Thread consumer1 = new Thread(demo.createConsumer("Consumer-1"));
        Thread consumer2 = new Thread(demo.createConsumer("Consumer-2"));
        
        // Start all
        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        
        // Wait for producers
        producer1.join();
        producer2.join();
        
        // Signal consumers to stop
        Thread.sleep(500);
        demo.stop();
        
        // Wait for consumers
        consumer1.join();
        consumer2.join();
        
        System.out.println("Producer-Consumer demo completed");
    }
}

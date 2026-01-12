package advanced.threads;

import java.util.concurrent.*;
import java.util.*;

/**
 * ============================================================================
 * EXECUTOR FRAMEWORK
 * ============================================================================
 * 
 * Problems with manual thread management:
 * - Thread creation is expensive
 * - Too many threads = resource exhaustion
 * - No easy way to manage thread lifecycle
 * 
 * SOLUTION: ExecutorService provides thread pool management
 * 
 * THREAD POOL TYPES:
 * 1. newFixedThreadPool(n) - Fixed number of threads
 * 2. newCachedThreadPool() - Creates threads as needed, reuses idle threads
 * 3. newSingleThreadExecutor() - Single worker thread
 * 4. newScheduledThreadPool(n) - Supports scheduled/periodic execution
 * 
 * BENEFITS:
 * - Thread reuse (better performance)
 * - Controlled resource usage
 * - Built-in task queue
 * - Easy task submission and management
 */

/**
 * CALLABLE AND FUTURE
 * -------------------
 * 
 * RUNNABLE LIMITATIONS:
 * - Cannot return a result
 * - Cannot throw checked exceptions
 * 
 * CALLABLE INTERFACE:
 * - Can return a result (generic type)
 * - Can throw checked exceptions
 * - Method: V call() throws Exception
 * 
 * FUTURE INTERFACE:
 * - Represents result of asynchronous computation
 * - Methods:
 *   - get() - blocks until result is available
 *   - get(timeout) - blocks for max timeout
 *   - isDone() - check if computation is complete
 *   - cancel() - attempt to cancel execution
 * 
 * USE CASES:
 * - Long-running computations
 * - Tasks that need to return results
 * - Parallel processing with result aggregation
 */

/**
 * Result object for Callable tasks
 */
class ComputationResult {
    private int input;
    private int output;
    private String status;
    private String threadName;
    
    public ComputationResult(int input, int output, String status, String threadName) {
        this.input = input;
        this.output = output;
        this.status = status;
        this.threadName = threadName;
    }
    
    @Override
    public String toString() {
        return String.format("Input: %d, Output: %d, Status: %s, Thread: %s", 
            input, output, status, threadName);
    }
}

/**
 * Callable implementation that returns a result
 */
class ComputationTask implements Callable<ComputationResult> {
    private int inputValue;
    
    public ComputationTask(int inputValue) {
        this.inputValue = inputValue;
    }
    
    @Override
    public ComputationResult call() throws Exception {
        String threadName = Thread.currentThread().getName();
        System.out.println("Computing for input: " + inputValue + " on " + threadName);
        
        // Simulate long computation
        Thread.sleep(1000);
        
        // Can throw checked exceptions
        if (inputValue < 0) {
            throw new IllegalArgumentException("Negative values not allowed");
        }
        
        int result = inputValue * inputValue;
        return new ComputationResult(inputValue, result, "Success", threadName);
    }
}

/**
 * ============================================================================
 * USAGE DEMONSTRATION
 * ============================================================================
 */
public class ExecutorServiceDemo {
    
    public static void main(String[] args) throws Exception {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║          EXECUTOR SERVICE DEMONSTRATION                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Fixed Thread Pool
        System.out.println("━━━ Demo 1: Fixed Thread Pool ━━━\n");
        demonstrateFixedThreadPool();
        
        Thread.sleep(500);
        
        // Demo 2: Cached Thread Pool
        System.out.println("\n━━━ Demo 2: Cached Thread Pool ━━━\n");
        demonstrateCachedThreadPool();
        
        Thread.sleep(500);
        
        // Demo 3: Single Thread Executor
        System.out.println("\n━━━ Demo 3: Single Thread Executor ━━━\n");
        demonstrateSingleThreadExecutor();
        
        Thread.sleep(500);
        
        // Demo 4: Callable and Future
        System.out.println("\n━━━ Demo 4: Callable and Future ━━━\n");
        demonstrateCallableAndFuture();
        
        Thread.sleep(500);
        
        // Demo 5: Scheduled Executor
        System.out.println("\n━━━ Demo 5: Scheduled Executor ━━━\n");
        demonstrateScheduledExecutor();
        
        // Demo 6: invokeAll and invokeAny
        System.out.println("\n━━━ Demo 6: invokeAll and invokeAny ━━━\n");
        demonstrateInvokeAllAndAny();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║        EXECUTOR SERVICE DEMO COMPLETED!                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    /**
     * Fixed Thread Pool - Use when you know max concurrent tasks
     */
    private static void demonstrateFixedThreadPool() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        System.out.println("Submitting 6 tasks to pool of 3 threads:");
        
        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " running on " + 
                    Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task " + taskId + " completed");
            });
        }
        
        // Shutdown and wait
        shutdownAndWait(executor, "Fixed Thread Pool");
    }
    
    /**
     * Cached Thread Pool - Creates threads as needed, reuses idle
     */
    private static void demonstrateCachedThreadPool() {
        ExecutorService executor = Executors.newCachedThreadPool();
        
        System.out.println("Submitting tasks to cached pool:");
        
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " on " + 
                    Thread.currentThread().getName());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        shutdownAndWait(executor, "Cached Thread Pool");
    }
    
    /**
     * Single Thread Executor - Sequential execution
     */
    private static void demonstrateSingleThreadExecutor() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        System.out.println("Submitting tasks to single thread executor:");
        
        for (int i = 1; i <= 3; i++) {
            final int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " on " + 
                    Thread.currentThread().getName());
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        shutdownAndWait(executor, "Single Thread Executor");
    }
    
    /**
     * Callable and Future - Tasks that return results
     */
    private static void demonstrateCallableAndFuture() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<ComputationResult>> futures = new ArrayList<>();
        
        // Submit multiple Callable tasks
        for (int i = 1; i <= 5; i++) {
            Future<ComputationResult> future = executor.submit(new ComputationTask(i * 5));
            futures.add(future);
        }
        
        System.out.println("All tasks submitted. Collecting results...\n");
        
        // Collect results
        for (Future<ComputationResult> future : futures) {
            try {
                // get() blocks until result is available
                ComputationResult result = future.get();
                System.out.println("Result: " + result);
            } catch (ExecutionException e) {
                System.err.println("Task failed: " + e.getCause().getMessage());
            }
        }
        
        shutdownAndWait(executor, "Callable/Future Demo");
    }
    
    /**
     * Scheduled Executor - Delayed and periodic execution
     */
    private static void demonstrateScheduledExecutor() throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        
        // Execute once after delay
        System.out.println("Scheduling task with 2 second delay...");
        scheduler.schedule(() -> {
            System.out.println("Delayed task executed after 2 seconds");
        }, 2, TimeUnit.SECONDS);
        
        // Execute periodically (fixed rate)
        System.out.println("Scheduling periodic task (every 1 second)...");
        ScheduledFuture<?> periodicTask = scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Periodic task at " + new Date());
        }, 0, 1, TimeUnit.SECONDS);
        
        // Let it run for 3 seconds
        Thread.sleep(3500);
        
        // Cancel periodic task
        periodicTask.cancel(false);
        System.out.println("Periodic task cancelled");
        
        shutdownAndWait(scheduler, "Scheduled Executor");
    }
    
    /**
     * invokeAll and invokeAny - Batch execution
     */
    private static void demonstrateInvokeAllAndAny() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        List<Callable<String>> tasks = Arrays.asList(
            () -> { Thread.sleep(1000); return "Task 1 result"; },
            () -> { Thread.sleep(500); return "Task 2 result"; },
            () -> { Thread.sleep(1500); return "Task 3 result"; }
        );
        
        // invokeAll - waits for all tasks
        System.out.println("invokeAll - executing all tasks:");
        List<Future<String>> allResults = executor.invokeAll(tasks);
        for (Future<String> result : allResults) {
            System.out.println("  " + result.get());
        }
        
        // invokeAny - returns result of first completed task
        System.out.println("\ninvokeAny - first completed task:");
        String firstResult = executor.invokeAny(tasks);
        System.out.println("  First result: " + firstResult);
        
        shutdownAndWait(executor, "invokeAll/invokeAny Demo");
    }
    
    /**
     * Helper method to shutdown executor properly
     */
    private static void shutdownAndWait(ExecutorService executor, String name) {
        executor.shutdown(); // Graceful shutdown
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // Force shutdown
            }
            System.out.println("✓ " + name + " shutdown complete\n");
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

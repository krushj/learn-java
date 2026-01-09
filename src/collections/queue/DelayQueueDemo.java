package collections.queue;

import java.util.concurrent.*;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueueDemo
 *
 * INTERNAL:
 * - Elements available only after delay expires
 * - Used in scheduling systems
 */
public class DelayQueueDemo {

    static class Task implements Delayed {

        long startTime;

        Task(long delay) {
            this.startTime = System.currentTimeMillis() + delay;
        }

        public long getDelay(TimeUnit unit) {
            return unit.convert(startTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        public int compareTo(Delayed o) {
            return Long.compare(this.startTime, ((Task) o).startTime);
        }
    }

    public static void main(String[] args) throws Exception {

        DelayQueue<Task> queue = new DelayQueue<>();

        queue.put(new Task(1000));
        queue.take(); // waits until delay expires
    }
}

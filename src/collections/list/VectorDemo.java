package collections.list;

import java.util.Vector;

/**
 * VectorDemo
 *
 * LEGACY CLASS (Java 1.0)
 * - Thread-safe (synchronized methods)
 * - Dynamic array
 * - Slower than ArrayList
 */
public class VectorDemo {

    public static void main(String[] args) {

        Vector<Integer> vector = new Vector<>();

        // add(E) → synchronized, O(1)
        vector.add(10);

        // addElement(E) → legacy method
        vector.addElement(20);

        // get(index) → direct array access, O(1)
        vector.get(0);

        // elementAt(index) → legacy access
        vector.elementAt(1);

        // size(), capacity()
        vector.size();
        vector.capacity();

        // remove(index) → shift elements, O(n)
        vector.remove(0);

        // Enumeration (legacy cursor)
        var en = vector.elements();
        while (en.hasMoreElements()) {
            en.nextElement();
        }
    }
}

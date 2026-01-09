package collections;

import java.util.*;
import java.util.stream.*;

/**
 * ArrayListStreamDemo
 *
 * DEMONSTRATES STREAM API USING ONE COMMON COLLECTION (ArrayList)
 *
 * INTERNAL WORKING OF STREAM:
 * - Stream does NOT store data
 * - It is a pipeline over a source (ArrayList)
 * - Operations are LAZY (executed only on terminal operation)
 * - Original collection is NOT modified
 */
public class StreamDemo {

    static class Employee {
        int id;
        String name;
        String dept;
        double salary;

        Employee(int id, String name, String dept, double salary) {
            this.id = id;
            this.name = name;
            this.dept = dept;
            this.salary = salary;
        }

        @Override
        public String toString() {
            return id + " " + name + " " + dept + " " + salary;
        }
    }

    public static void main(String[] args) {

        // ===== SOURCE COLLECTION =====
        ArrayList<Employee> employees = new ArrayList<>();

        employees.add(new Employee(1, "Alice", "IT", 70000));
        employees.add(new Employee(2, "Bob", "HR", 50000));
        employees.add(new Employee(3, "Charlie", "IT", 90000));
        employees.add(new Employee(4, "David", "FIN", 60000));
        employees.add(new Employee(5, "Eve", "IT", 40000));

        /*
         * stream()
         * - Creates sequential stream
         * - Internally uses Spliterator
         */
        Stream<Employee> stream = employees.stream();

        // ===== INTERMEDIATE OPERATIONS =====

        // filter()
        // Keeps elements matching predicate
        // Lazy operation
        Stream<Employee> itEmployees =
                employees.stream()
                         .filter(e -> e.dept.equals("IT"));

        // map()
        // Transforms elements
        Stream<String> names =
                employees.stream()
                         .map(e -> e.name);

        // sorted()
        // Uses TimSort internally
        Stream<Employee> sortedBySalary =
                employees.stream()
                         .sorted(Comparator.comparingDouble(e -> e.salary));

        // distinct()
        // Uses equals() and hashCode()
        Stream<String> distinctDepts =
                employees.stream()
                         .map(e -> e.dept)
                         .distinct();

        // limit() / skip()
        Stream<Employee> top2 =
                employees.stream()
                         .sorted(Comparator.comparingDouble(e -> -e.salary))
                         .limit(2);

        // peek()
        // Mainly for debugging
        employees.stream()
                 .peek(e -> System.out.println("Before filter: " + e))
                 .filter(e -> e.salary > 60000)
                 .peek(e -> System.out.println("After filter: " + e))
                 .collect(Collectors.toList());

        // ===== TERMINAL OPERATIONS =====

        // forEach()
        employees.stream().forEach(System.out::println);

        // collect()
        // Converts stream to collection
        List<Employee> highPaid =
                employees.stream()
                         .filter(e -> e.salary > 60000)
                         .collect(Collectors.toList());

        // toArray()
        Object[] empArray =
                employees.stream().toArray();

        // count()
        long count =
                employees.stream().count();

        // anyMatch / allMatch / noneMatch
        boolean anyHigh =
                employees.stream().anyMatch(e -> e.salary > 80000);

        boolean allHigh =
                employees.stream().allMatch(e -> e.salary > 30000);

        boolean noneLow =
                employees.stream().noneMatch(e -> e.salary < 20000);

        // findFirst() / findAny()
        Optional<Employee> firstIT =
                employees.stream()
                         .filter(e -> e.dept.equals("IT"))
                         .findFirst();

        // reduce()
        // Combines elements
        double totalSalary =
                employees.stream()
                         .map(e -> e.salary)
                         .reduce(0.0, Double::sum);

        // min() / max()
        Optional<Employee> maxSalary =
                employees.stream()
                         .max(Comparator.comparingDouble(e -> e.salary));

        // ===== COLLECTORS =====

        // groupingBy()
        Map<String, List<Employee>> byDept =
                employees.stream()
                         .collect(Collectors.groupingBy(e -> e.dept));

        // partitioningBy()
        Map<Boolean, List<Employee>> salaryPartition =
                employees.stream()
                         .collect(Collectors.partitioningBy(e -> e.salary > 60000));

        // averagingDouble()
        double avgSalary =
                employees.stream()
                         .collect(Collectors.averagingDouble(e -> e.salary));

        // joining()
        String namesJoined =
                employees.stream()
                         .map(e -> e.name)
                         .collect(Collectors.joining(", "));

        // ===== OPTIONAL HANDLING =====

        firstIT.ifPresent(System.out::println);

        // ===== PARALLEL STREAM =====

        /*
         * parallelStream()
         * - Splits data using ForkJoinPool
         * - Uses multiple threads
         * - Order not guaranteed
         */
        employees.parallelStream()
                 .filter(e -> e.salary > 50000)
                 .forEach(System.out::println);
    }
}

# 📚 Learn Java - From Basics to Advanced

A comprehensive, hands-on Java learning repository with **clear documentation**, **consistent patterns**, and **interview-ready examples**.

---

## 📖 Table of Contents

1. [Learning Roadmap](#-learning-roadmap)
2. [Project Structure](#-project-structure)
3. [How to Use](#-how-to-use)
4. [Java Fundamentals](#1%EF%B8%8F⃣-java-fundamentals)
5. [OOP Concepts](#2%EF%B8%8F⃣-oop-concepts)
6. [Exception Handling](#3%EF%B8%8F⃣-exception-handling)
7. [String Handling](#4%EF%B8%8F⃣-string-handling)
8. [Generics](#5%EF%B8%8F⃣-generics)
9. [Collections Framework](#6%EF%B8%8F⃣-collections-framework)
10. [Stream API](#7%EF%B8%8F⃣-stream-api)
11. [Java Internals (JVM, JDK, JRE)](#8%EF%B8%8F⃣-java-internals)
12. [Memory Management & GC](#9%EF%B8%8F⃣-memory-management--garbage-collection)
13. [Interview Cheatsheet](#-interview-cheatsheet)
14. [Practice Projects](#-practice-projects)

> 🚀 **Ready to build?** Check out [PROJECTS.md](./PROJECTS.md) for 5 real-world applications!

---

## 🎯 Learning Roadmap

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                           JAVA MASTERY ROADMAP                                  │
├─────────────────────────────────────────────────────────────────────────────────┤
│                                                                                 │
│   📗 LEVEL 1: FUNDAMENTALS                                                      │
│   ├── Data Types & Variables        ── Primitives, References, Type Conversion │
│   ├── Operators                     ── All Operators with Precedence           │
│   ├── Control Flow                  ── if/else, switch, loops, jump statements │
│   ├── Arrays                        ── 1D, 2D, Jagged, Arrays utility          │
│   └── Methods                       ── Overloading, Varargs, Recursion         │
│                                                                                 │
│   📘 LEVEL 2: OBJECT-ORIENTED PROGRAMMING                                       │
│   ├── Classes & Objects             ── Constructors, this keyword              │
│   ├── Inheritance                   ── extends, super, constructor chaining    │
│   ├── Polymorphism                  ── Overloading vs Overriding               │
│   ├── Abstraction                   ── Abstract classes, Interfaces            │
│   └── Encapsulation                 ── Access modifiers, Getters/Setters       │
│                                                                                 │
│   📙 LEVEL 3: CORE APIS                                                         │
│   ├── String Handling               ── String Pool, Immutability, StringBuilder│
│   ├── Exception Handling            ── try-catch, Custom exceptions            │
│   └── Generics                      ── Wildcards, PECS, Type erasure           │
│                                                                                 │
│   📕 LEVEL 4: COLLECTIONS FRAMEWORK                                             │
│   ├── List                          ── ArrayList, LinkedList, Vector, Stack    │
│   ├── Set                           ── HashSet, LinkedHashSet, TreeSet         │
│   ├── Map                           ── HashMap, TreeMap, ConcurrentHashMap     │
│   ├── Queue & Deque                 ── PriorityQueue, BlockingQueue            │
│   └── Stream API                    ── filter, map, reduce, collect            │
│                                                                                 │
│   📓 LEVEL 5: ADVANCED (Coming Soon)                                            │
│   ├── Multithreading                ── Threads, Executors, Synchronization     │
│   ├── File I/O & NIO                ── Streams, Readers, Path API              │
│   └── Java 8+ Features              ── Lambdas, Optional, Date/Time API        │
│                                                                                 │
└─────────────────────────────────────────────────────────────────────────────────┘
```

---

## 📁 Project Structure

```
core-java/
├── src/
│   ├── core/                         # Core Java concepts
│   │   ├── basics/
│   │   │   ├── DataTypesDemo.java
│   │   │   ├── OperatorsDemo.java
│   │   │   ├── ControlFlowDemo.java
│   │   │   ├── ArraysDemo.java
│   │   │   └── MethodsDemo.java
│   │   ├── oop/
│   │   │   ├── ClassesObjectsDemo.java
│   │   │   ├── InheritanceDemo.java
│   │   │   ├── PolymorphismDemo.java
│   │   │   ├── AbstractionDemo.java
│   │   │   └── EncapsulationDemo.java
│   │   ├── strings/
│   │   │   └── StringDemo.java
│   │   ├── exceptions/
│   │   │   └── ExceptionDemo.java
│   │   └── generics/
│   │       └── GenericsDemo.java
│   │
│   └── collections/                  # Collections Framework
│       ├── list/
│       │   ├── ArrayListDemo.java
│       │   ├── LinkedListDemo.java
│       │   ├── VectorDemo.java
│       │   └── StackDemo.java
│       ├── set/
│       │   ├── HashSetDemo.java
│       │   ├── LinkedHashSetDemo.java
│       │   └── TreeSetDemo.java
│       ├── map/
│       │   ├── HashMapDemo.java
│       │   ├── LinkedHashMapDemo.java
│       │   ├── TreeMapDemo.java
│       │   ├── ConcurrentHashMapDemo.java
│       │   └── WeakHashMapDemo.java
│       ├── queue/
│       │   ├── PriorityQueueDemo.java
│       │   ├── ArrayDequeDemo.java
│       │   ├── ArrayBlockingQueueDemo.java
│       │   └── LinkedBlockingQueueDemo.java
│       └── StreamDemo.java
│
├── bin/                              # Compiled classes
├── lib/                              # Dependencies
└── README.md                         # This file
```

---

## 🚀 How to Use

### 1. Clone and Open
```bash
git clone <repo-url>
cd core-java
```

### 2. Run Any Demo
```bash
# Compile
javac -d bin src/core/basics/DataTypesDemo.java

# Run
java -cp bin core.basics.DataTypesDemo
```

### 3. Learning Approach
1. **Read the documentation** - Each section below explains the concept
2. **Run the demos** - Execute each file to see output
3. **Read the code** - Each file has detailed comments
4. **Experiment** - Modify code and observe changes

### Documentation Pattern
Each demo file follows this consistent pattern:
```java
/**
 * DemoName
 *
 * COMPARISON:
 * (Table comparing similar implementations)
 *
 * WHEN TO USE:
 * (Decision guide)
 *
 * INTERNAL WORKING:
 * (How it works under the hood)
 *
 * TIME COMPLEXITY:
 * (Big-O for common operations)
 */
public class DemoName {
    public static void main(String[] args) {
        // ===== SECTION NAME =====
        // method() - Time: O(n)
        // What this does and when to use
        code();
    }
}
```

---

# 1️⃣ Java Fundamentals

## Primitive Data Types

| Type    | Size    | Default | Range                    | Wrapper   |
|---------|---------|---------|--------------------------|-----------|
| byte    | 1 byte  | 0       | -128 to 127              | Byte      |
| short   | 2 bytes | 0       | -32,768 to 32,767        | Short     |
| int     | 4 bytes | 0       | -2³¹ to 2³¹-1            | Integer   |
| long    | 8 bytes | 0L      | -2⁶³ to 2⁶³-1            | Long      |
| float   | 4 bytes | 0.0f    | ±3.4E38 (6-7 digits)     | Float     |
| double  | 8 bytes | 0.0d    | ±1.7E308 (15-16 digits)  | Double    |
| char    | 2 bytes | \u0000  | 0 to 65,535 (Unicode)    | Character |
| boolean | 1 bit   | false   | true / false             | Boolean   |

## Variable Types

| Type           | Declared In        | Scope           | Default Value    |
|----------------|-------------------|-----------------|------------------|
| Local          | Method/Block      | Within block    | None (must init) |
| Instance       | Class (non-static)| Object lifetime | Type default     |
| Static (Class) | Class (static)    | Class lifetime  | Type default     |

## Operators (Precedence High → Low)

```
1. () [] .                    → Parentheses, array, member
2. ++ -- ! ~ + -              → Unary
3. * / %                      → Multiplicative
4. + -                        → Additive
5. << >> >>>                  → Shift
6. < <= > >= instanceof       → Relational
7. == !=                      → Equality
8. & → ^ → |                  → Bitwise (AND, XOR, OR)
9. && → ||                    → Logical (AND, OR)
10. ?:                        → Ternary
11. = += -= *= /=             → Assignment
```

## Control Flow

| Statement  | Use Case                          |
|------------|-----------------------------------|
| if-else    | Simple conditions                 |
| switch     | Multiple values for one variable  |
| for        | Known number of iterations        |
| while      | Unknown iterations, may skip all  |
| do-while   | Unknown iterations, runs at least once |
| for-each   | Iterate collections/arrays        |

## Arrays

| Type       | Description                   | Declaration                   |
|------------|-------------------------------|-------------------------------|
| 1D Array   | Linear array                  | `int[] arr = new int[5]`      |
| 2D Array   | Array of arrays (matrix)      | `int[][] arr = new int[3][4]` |
| Jagged     | Arrays with different lengths | `int[][] arr = new int[3][]`  |

**Key Points:**
- Fixed size after creation
- 0-based indexing
- `arr.length` is a property (not method)
- Stored in contiguous memory

---

# 2️⃣ OOP Concepts

## Four Pillars

| Pillar        | Description                     | Implementation                |
|---------------|---------------------------------|-------------------------------|
| Encapsulation | Hide data, expose via methods   | private fields + getters/setters |
| Inheritance   | Reuse code from parent class    | `extends` keyword             |
| Polymorphism  | Same interface, diff behavior   | Overloading + Overriding      |
| Abstraction   | Hide complexity, show essence   | abstract class + interface    |

## Method Overloading vs Overriding

| Aspect          | Overloading              | Overriding                    |
|-----------------|--------------------------|-------------------------------|
| Definition      | Same name, diff params   | Same signature in child       |
| Binding         | Compile-time             | Runtime                       |
| Return type     | Can differ               | Must be same/covariant        |
| Access modifier | Can change               | Cannot be more restrictive    |
| Static methods  | Yes                      | No (method hiding)            |
| Private methods | Yes                      | No                            |

## Access Modifiers

| Modifier  | Class | Package | Subclass | World |
|-----------|-------|---------|----------|-------|
| private   | ✅    | ❌      | ❌       | ❌    |
| default   | ✅    | ✅      | ❌       | ❌    |
| protected | ✅    | ✅      | ✅       | ❌    |
| public    | ✅    | ✅      | ✅       | ✅    |

## Abstract Class vs Interface

| Aspect            | Abstract Class          | Interface (Java 8+)          |
|-------------------|-------------------------|------------------------------|
| Methods           | Abstract + Concrete     | Abstract + Default + Static  |
| Variables         | Any type                | public static final only     |
| Constructors      | Yes                     | No                           |
| Multiple inherit  | No                      | Yes                          |
| When to use       | IS-A with shared code   | CAN-DO capability            |

---

# 3️⃣ Exception Handling

## Exception Hierarchy

```
Throwable
├── Error (Don't catch - JVM errors)
│   ├── OutOfMemoryError
│   ├── StackOverflowError
│   └── VirtualMachineError
│
└── Exception
    ├── RuntimeException (Unchecked)
    │   ├── NullPointerException
    │   ├── ArrayIndexOutOfBoundsException
    │   ├── ArithmeticException
    │   └── IllegalArgumentException
    │
    └── Checked Exceptions
        ├── IOException
        ├── SQLException
        └── ClassNotFoundException
```

## Checked vs Unchecked

| Aspect       | Checked                    | Unchecked                   |
|--------------|----------------------------|-----------------------------|
| Compile time | Must handle or declare     | Optional                    |
| Inheritance  | Exception (not Runtime)    | RuntimeException            |
| Examples     | IOException, SQLException  | NullPointer, ArrayIndexOOB  |
| When to use  | Recoverable conditions     | Programming errors          |

---

# 4️⃣ String Handling

## String vs StringBuilder vs StringBuffer

| Aspect       | String          | StringBuilder   | StringBuffer    |
|--------------|-----------------|-----------------|-----------------|
| Mutability   | Immutable       | Mutable         | Mutable         |
| Thread-safe  | Yes (immutable) | No              | Yes (sync)      |
| Performance  | Slow (new obj)  | Fast            | Slower          |
| Memory       | String pool     | Heap            | Heap            |
| Use case     | Few changes     | Single-threaded | Multi-threaded  |

## String Pool

```
┌─────────────────────────────────────────────────────────┐
│                        HEAP                             │
│  ┌───────────────────────────────────────────────────┐  │
│  │                 STRING POOL                       │  │
│  │   "Hello" ──┐                                     │  │
│  │             ├──→ [H][e][l][l][o] (single copy)    │  │
│  │   "Hello" ──┘                                     │  │
│  └───────────────────────────────────────────────────┘  │
│  ┌───────────────────────────────────────────────────┐  │
│  │              REGULAR HEAP                         │  │
│  │   new String("Hello") ──→ [H][e][l][l][o]         │  │
│  │   new String("Hello") ──→ [H][e][l][l][o]         │  │
│  │                          (separate objects)       │  │
│  └───────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────┘
```

---

# 5️⃣ Generics

## Why Generics?

| Without Generics          | With Generics              |
|---------------------------|----------------------------|
| Type casting required     | No casting needed          |
| Runtime ClassCastException| Compile-time type safety   |
| Object type everywhere    | Specific type throughout   |

## Generic Syntax

```java
// Generic Class
class Box<T> { T value; }

// Generic Method
<T> void print(T item) { }

// Bounded Types
<T extends Number> void process(T num) { }

// Wildcards
List<?>                 // Unbounded - read only
List<? extends Number>  // Upper bounded - read only
List<? super Integer>   // Lower bounded - write safe
```

## PECS Principle (Producer Extends, Consumer Super)

| Use Case          | Wildcard          | Can Do              |
|-------------------|-------------------|---------------------|
| Read from list    | `? extends T`     | Get items as T      |
| Write to list     | `? super T`       | Add items of type T |
| Read and Write    | No wildcard       | Full access         |

---

# 6️⃣ Collections Framework

## Collections Hierarchy

```
                          Iterable
                              │
                         Collection
              ┌───────────────┼───────────────┐
             List            Set            Queue
              │               │               │
        ┌─────┴─────┐   ┌─────┴─────┐   ┌─────┴─────┐
    ArrayList  LinkedList HashSet TreeSet  PriorityQueue
                         LinkedHashSet      Deque
                                             │
                                        ArrayDeque


                            Map (separate hierarchy)
              ┌───────────────┼───────────────┐
          HashMap        TreeMap      ConcurrentHashMap
       LinkedHashMap
```

## List Implementations

| Feature          | ArrayList       | LinkedList        | Vector        |
|------------------|-----------------|-------------------|---------------|
| Structure        | Dynamic array   | Doubly linked     | Dynamic array |
| Random access    | O(1) ✅         | O(n) ❌           | O(1) ✅       |
| Add/Remove end   | O(1) amortized  | O(1)              | O(1)          |
| Add/Remove front | O(n) ❌         | O(1) ✅           | O(n) ❌       |
| Thread-safe      | No              | No                | Yes (legacy)  |
| Use when         | Most cases      | Frequent insert   | Avoid         |

## Set Implementations

| Feature          | HashSet  | LinkedHashSet | TreeSet      |
|------------------|----------|---------------|--------------|
| Structure        | HashMap  | HashMap+List  | Red-Black    |
| Order            | None     | Insertion     | Sorted       |
| Performance      | O(1)     | O(1)          | O(log n)     |
| Null allowed     | 1        | 1             | No           |
| Use when         | Fast ops | Need order    | Need sorted  |

## Map Implementations

| Feature          | HashMap  | LinkedHashMap | TreeMap      | ConcurrentHashMap |
|------------------|----------|---------------|--------------|-------------------|
| Structure        | Buckets  | HashMap+List  | Red-Black    | Segments          |
| Order            | None     | Insertion     | Sorted       | None              |
| Performance      | O(1)     | O(1)          | O(log n)     | O(1)              |
| Null keys        | 1        | 1             | No           | No                |
| Thread-safe      | No       | No            | No           | Yes               |

## Queue Implementations

| Type                  | Structure      | Ordering   | Bounded | Thread-safe |
|-----------------------|----------------|------------|---------|-------------|
| PriorityQueue         | Binary Heap    | Priority   | No      | No          |
| ArrayDeque            | Circular array | FIFO/LIFO  | No      | No          |
| ArrayBlockingQueue    | Array          | FIFO       | Yes     | Yes         |
| LinkedBlockingQueue   | Linked list    | FIFO       | Optional| Yes         |
| PriorityBlockingQueue | Heap           | Priority   | No      | Yes         |

## Fail-Fast vs Fail-Safe

| Feature                | Fail-Fast                   | Fail-Safe           |
|------------------------|-----------------------------|--------------------|
| Modification behavior  | Throws ConcurrentModException| No exception       |
| Works on               | Original collection          | Copy/Snapshot      |
| Memory                 | Low                          | Higher             |
| Examples               | ArrayList, HashMap           | CopyOnWriteArrayList|

---

# 7️⃣ Stream API

## Collections vs Streams

| Aspect       | Collections           | Stream API                   |
|--------------|-----------------------|------------------------------|
| Purpose      | Store data            | Process data                 |
| Mutability   | Mutable               | Does not change source       |
| Evaluation   | Eager                 | Lazy                         |
| Reusability  | Reusable              | One-time use                 |
| Parallelism  | Manual                | Built-in `parallelStream()`  |

## Stream Operations

| Type         | Operations                                    | Behavior |
|--------------|-----------------------------------------------|----------|
| Intermediate | filter, map, sorted, distinct, limit, skip    | Lazy     |
| Terminal     | forEach, collect, count, reduce, findFirst    | Triggers |
| Short-circuit| findFirst, findAny, anyMatch, limit           | Stops early|

## Common Patterns

```java
// Filter and collect
list.stream()
    .filter(x -> x > 5)
    .collect(Collectors.toList());

// Map and reduce
list.stream()
    .map(String::length)
    .reduce(0, Integer::sum);

// Group by
list.stream()
    .collect(Collectors.groupingBy(Person::getCity));
```

---

# 8️⃣ Java Internals

## JDK vs JRE vs JVM

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           JDK (Java Development Kit)                        │
│  ┌───────────────────────────────────────────────────────────────────────┐  │
│  │                      Development Tools                                │  │
│  │  javac (compiler) │ java │ javadoc │ jar │ jdb │ jconsole             │  │
│  └───────────────────────────────────────────────────────────────────────┘  │
│  ┌───────────────────────────────────────────────────────────────────────┐  │
│  │                    JRE (Java Runtime Environment)                     │  │
│  │  ┌─────────────────────────────────────────────────────────────────┐  │  │
│  │  │                 Core Libraries (rt.jar)                         │  │  │
│  │  │  java.lang │ java.util │ java.io │ java.net │ java.sql          │  │  │
│  │  └─────────────────────────────────────────────────────────────────┘  │  │
│  │  ┌─────────────────────────────────────────────────────────────────┐  │  │
│  │  │                   JVM (Java Virtual Machine)                    │  │  │
│  │  │  Class Loader │ Runtime Memory │ Execution Engine │ JNI         │  │  │
│  │  └─────────────────────────────────────────────────────────────────┘  │  │
│  └───────────────────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────────────────┘
```

| Component | Full Name                | Purpose                  | Contains         |
|-----------|--------------------------|--------------------------|------------------|
| **JDK**   | Java Development Kit     | Develop + Run Java       | JRE + Dev Tools  |
| **JRE**   | Java Runtime Environment | Run Java programs        | JVM + Libraries  |
| **JVM**   | Java Virtual Machine     | Execute bytecode         | Runtime Engine   |

## JVM Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                            JVM ARCHITECTURE                                 │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌───────────────────────────────────────────────────────────────────────┐  │
│  │                    CLASS LOADER SUBSYSTEM                             │  │
│  │      Loading → Linking (Verify/Prepare/Resolve) → Initialization      │  │
│  └───────────────────────────────────────────────────────────────────────┘  │
│                                  ↓                                          │
│  ┌───────────────────────────────────────────────────────────────────────┐  │
│  │                      RUNTIME DATA AREAS                               │  │
│  │  ┌─────────────────────────────┐  ┌────────────────────────────────┐  │  │
│  │  │     HEAP (Shared)           │  │    METHOD AREA (Shared)        │  │  │
│  │  │  ┌─────────┐ ┌───────────┐  │  │  Class metadata                │  │  │
│  │  │  │Young Gen│ │  Old Gen  │  │  │  Static variables              │  │  │
│  │  │  │Eden+S0+1│ │ (Tenured) │  │  │  Constant pool                 │  │  │
│  │  │  └─────────┘ └───────────┘  │  │                                │  │  │
│  │  └─────────────────────────────┘  └────────────────────────────────┘  │  │
│  │  ┌───────────────┐ ┌─────────────────┐ ┌─────────────────────────┐    │  │
│  │  │ STACK (Thread)│ │  PC Register    │ │ Native Method Stack     │    │  │
│  │  │ Local vars    │ │  (per thread)   │ │ (per thread)            │    │  │
│  │  │ Method calls  │ │                 │ │                         │    │  │
│  │  └───────────────┘ └─────────────────┘ └─────────────────────────┘    │  │
│  └───────────────────────────────────────────────────────────────────────┘  │
│                                  ↓                                          │
│  ┌───────────────────────────────────────────────────────────────────────┐  │
│  │                       EXECUTION ENGINE                                │  │
│  │    Interpreter │ JIT Compiler (C1/C2) │ Garbage Collector             │  │
│  └───────────────────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Class Loader Hierarchy

```
     Bootstrap ClassLoader (Native C++)     ← Core Java classes (java.lang.*)
              ↓
     Extension/Platform ClassLoader         ← Extension classes (lib/ext)
              ↓
     Application ClassLoader                ← Your application classes
              ↓
     Custom ClassLoaders                    ← User-defined loaders
```

**Delegation Model:** Child → Parent → Bootstrap (prevents replacing core classes)

---

# 9️⃣ Memory Management & Garbage Collection

## Memory Areas

| Area          | Stores                          | Thread    | Error              |
|---------------|--------------------------------|-----------|-------------------|
| Heap          | Objects, instance variables     | Shared    | OutOfMemoryError  |
| Method Area   | Class metadata, static vars     | Shared    | OutOfMemoryError  |
| Stack         | Local vars, method calls        | Per thread| StackOverflowError|
| PC Register   | Current instruction address     | Per thread| -                 |
| Native Stack  | Native method info              | Per thread| StackOverflowError|

## Heap Structure

```
┌─────────────────────────────────────────────────────────────────┐
│                            HEAP                                 │
├─────────────────────────────────────────────────────────────────┤
│  ┌────────────────────────┐  ┌────────────────────────────────┐ │
│  │    YOUNG GENERATION    │  │       OLD GENERATION           │ │
│  │  ┌──────┐ ┌────┐┌────┐ │  │        (Tenured)               │ │
│  │  │ Eden │ │ S0 ││ S1 │ │  │                                │ │
│  │  │      │ │    ││    │ │  │   Long-lived objects           │ │
│  │  │ New  │ │Survivor   │ │  │   Promoted from Young          │ │
│  │  │ objs │ │ Spaces   │ │  │                                │ │
│  │  └──────┘ └────┘└────┘ │  │   Major GC (Full GC)           │ │
│  │                        │  │                                │ │
│  │  Minor GC (frequent)   │  │                                │ │
│  └────────────────────────┘  └────────────────────────────────┘ │
│  ┌──────────────────────────────────────────────────────────────┤
│  │  METASPACE (Java 8+) - Class metadata (native memory)       │
│  └──────────────────────────────────────────────────────────────┘
└─────────────────────────────────────────────────────────────────┘
```

## GC Algorithms

| GC Type       | Description                  | Use Case               | Flag                 |
|---------------|------------------------------|------------------------|----------------------|
| Serial        | Single-threaded              | Small apps             | -XX:+UseSerialGC     |
| Parallel      | Multi-threaded               | Throughput priority    | -XX:+UseParallelGC   |
| G1            | Region-based, balanced       | Large heaps (default)  | -XX:+UseG1GC         |
| ZGC           | Low latency, scalable        | Very large heaps       | -XX:+UseZGC          |

## GC Process

```
1. New object → Eden
2. Eden full → Minor GC
3. Survivors → S0/S1 (age++)
4. Age threshold reached → Old Gen
5. Old Gen full → Major GC (Full GC)
```

## Common JVM Flags

| Flag                      | Description                |
|---------------------------|----------------------------|
| -Xms512m                  | Initial heap size          |
| -Xmx2g                    | Maximum heap size          |
| -Xss256k                  | Stack size per thread      |
| -XX:+PrintGCDetails       | Print GC details           |
| -XX:+HeapDumpOnOutOfMemoryError | Dump heap on OOM    |

---

# 🎯 Interview Cheatsheet

## Golden Rules

| Rule | Description |
|------|-------------|
| 1 | **Primitives** on stack, **Objects** on heap |
| 2 | **String** is immutable (security + thread-safety + caching) |
| 3 | **==** compares reference, **equals()** compares content |
| 4 | **ArrayList** for most cases, **LinkedList** for frequent insert/delete at front |
| 5 | **HashMap** unless you need ordering (LinkedHashMap) or sorting (TreeMap) |
| 6 | **ConcurrentHashMap** for thread-safe map (not synchronized HashMap) |
| 7 | **Generics** provide compile-time type safety |
| 8 | **Try-with-resources** for automatic resource management |
| 9 | **Interfaces** for multiple inheritance and loose coupling |
| 10 | **G1** is default GC since Java 9 |

## Quick Comparisons

| Concept | Option A | Option B | Choose A When | Choose B When |
|---------|----------|----------|---------------|---------------|
| List | ArrayList | LinkedList | Random access | Frequent insert/delete |
| Set | HashSet | TreeSet | Fast lookup | Need sorted |
| Map | HashMap | TreeMap | Fast lookup | Need sorted keys |
| String | String | StringBuilder | Few changes | Many changes |
| Exception | Checked | Unchecked | Recoverable | Programming error |
| Class | Abstract | Interface | Shared code | Multiple inherit |
| Iteration | Fail-Fast | Fail-Safe | Single thread | Concurrent |

## Common Pitfalls

| Pitfall | Avoid | Use Instead |
|---------|-------|-------------|
| String concatenation in loop | `s += "x"` | `StringBuilder` |
| Checking null | `str == null \|\| str.equals("")` | `str == null \|\| str.isEmpty()` |
| Boxing in loops | `Integer i++` | `int i++` |
| Synchronized collection | `Collections.synchronizedMap()` | `ConcurrentHashMap` |
| Vector/Hashtable | Vector, Hashtable | ArrayList, HashMap |

## Memory Leaks Common Causes

1. Objects held in static collections
2. Unclosed resources (streams, connections)
3. Inner class holding outer class reference
4. ThreadLocal not removed
5. Listeners not unregistered

---

---

# 🚀 Practice Projects

After mastering the concepts, build real-world applications! See **[PROJECTS.md](./PROJECTS.md)** for 5 comprehensive project plans:

| # | Project | Core Concepts | Difficulty |
|---|---------|---------------|------------|
| 1 | **Library Management** | HashMap, TreeSet, Queue, Exception Handling | ⭐⭐ |
| 2 | **E-Commerce Order System** | PriorityQueue, ConcurrentHashMap, Streams, BigDecimal | ⭐⭐⭐ |
| 3 | **Banking Transaction System** | ReentrantLock, ConcurrentHashMap, Thread Safety | ⭐⭐⭐⭐ |
| 4 | **Task Scheduler** | PriorityQueue, TreeMap, ScheduledExecutor | ⭐⭐⭐ |
| 5 | **Real-Time Chat** | BlockingQueue, LRU Cache, CompletableFuture | ⭐⭐⭐⭐ |

Each project includes:
- ✅ Complete system architecture
- ✅ Data models with all fields
- ✅ Core feature implementations
- ✅ Edge cases & solutions
- ✅ Constraints & validation rules
- ✅ Test scenarios
- ✅ Project structure

---

## 📝 Contributing

Feel free to:
- Add more examples
- Improve documentation
- Fix errors
- Suggest new topics

---

## 📜 License

MIT License - Learn, modify, and share freely!

---

**Happy Learning! 🎉**

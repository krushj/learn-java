# 🚀 Real-World Java Projects - Development Plans

These 5 projects are designed to help you **master Java core concepts** through practical, real-world applications. Each project includes detailed requirements, edge cases, constraints, and implementation guidance.

---

## 📖 Table of Contents

1. [Library Management System](#1️⃣-library-management-system)
2. [E-Commerce Order Processing System](#2️⃣-e-commerce-order-processing-system)
3. [Banking Transaction System](#3️⃣-banking-transaction-system)
4. [Task Scheduler & Reminder App](#4️⃣-task-scheduler--reminder-app)
5. [Real-Time Chat Application](#5️⃣-real-time-chat-application)

---

# 1️⃣ Library Management System

## 📋 Description

A complete library management system that handles book inventory, member management, book borrowing/returning, fine calculation, and search functionality.

## 🎯 Core Java Concepts Used

| Concept | Usage |
|---------|-------|
| **HashMap** | Book catalog (ISBN → Book), Member registry (ID → Member) |
| **ArrayList** | List of borrowed books per member, search results |
| **TreeSet** | Sorted list of books by title/author |
| **LinkedList** | Queue for book reservations |
| **Queue** | Waitlist for popular books |
| **Generics** | Generic search/filter methods |
| **Exception Handling** | Custom exceptions for business rules |
| **Streams** | Filtering, sorting, reporting |
| **Comparable/Comparator** | Multiple sorting strategies |
| **File I/O** | Persist data to files |

## 📊 Data Models

```java
// Book Entity
class Book {
    private String isbn;           // Primary Key
    private String title;
    private String author;
    private String category;
    private int totalCopies;
    private int availableCopies;
    private double finePerDay;
    private LocalDate publishDate;
}

// Member Entity
class Member {
    private String memberId;       // Primary Key
    private String name;
    private String email;
    private String phone;
    private MemberType type;       // STUDENT, FACULTY, GENERAL
    private LocalDate joinDate;
    private List<BorrowRecord> borrowHistory;
    private double outstandingFines;
}

// Borrow Record
class BorrowRecord {
    private String recordId;
    private Book book;
    private Member member;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;  // null if not returned
    private double fineAmount;
    private BorrowStatus status;   // BORROWED, RETURNED, OVERDUE
}

// Reservation
class Reservation {
    private String reservationId;
    private Book book;
    private Member member;
    private LocalDateTime reservedAt;
    private ReservationStatus status;
}
```

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        LIBRARY MANAGEMENT SYSTEM                            │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                         SERVICE LAYER                                │   │
│  │  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐ ┌──────────────┐  │   │
│  │  │BookService  │ │MemberService│ │BorrowService│ │SearchService │  │   │
│  │  └─────────────┘ └─────────────┘ └─────────────┘ └──────────────┘  │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                        │
│  ┌─────────────────────────────────▼───────────────────────────────────┐   │
│  │                       REPOSITORY LAYER                              │   │
│  │  ┌──────────────────────────────────────────────────────────────┐  │   │
│  │  │ HashMap<ISBN, Book> bookCatalog                              │  │   │
│  │  │ HashMap<MemberId, Member> memberRegistry                     │  │   │
│  │  │ HashMap<RecordId, BorrowRecord> activeRecords                │  │   │
│  │  │ TreeSet<Book> booksByTitle (sorted)                          │  │   │
│  │  │ Map<ISBN, Queue<Reservation>> waitlists                      │  │   │
│  │  │ Map<Category, List<Book>> booksByCategory                    │  │   │
│  │  └──────────────────────────────────────────────────────────────┘  │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## ⚙️ Core Features & Implementation

### Feature 1: Book Management

```java
public class BookService {
    private Map<String, Book> bookCatalog = new HashMap<>();
    private TreeSet<Book> booksByTitle = new TreeSet<>(Comparator.comparing(Book::getTitle));
    private Map<String, List<Book>> booksByCategory = new HashMap<>();
    
    // Add book - O(1) for HashMap, O(log n) for TreeSet
    public void addBook(Book book) {
        if (bookCatalog.containsKey(book.getIsbn())) {
            throw new DuplicateBookException("Book with ISBN already exists: " + book.getIsbn());
        }
        bookCatalog.put(book.getIsbn(), book);
        booksByTitle.add(book);
        booksByCategory.computeIfAbsent(book.getCategory(), k -> new ArrayList<>()).add(book);
    }
    
    // Search by title prefix - O(log n) using TreeSet
    public List<Book> searchByTitlePrefix(String prefix) {
        return booksByTitle.stream()
            .filter(b -> b.getTitle().toLowerCase().startsWith(prefix.toLowerCase()))
            .collect(Collectors.toList());
    }
}
```

### Feature 2: Borrowing System with Waitlist

```java
public class BorrowService {
    private Map<String, BorrowRecord> activeRecords = new HashMap<>();
    private Map<String, Queue<Reservation>> waitlists = new HashMap<>();
    
    public BorrowRecord borrowBook(String isbn, String memberId) {
        Book book = bookCatalog.get(isbn);
        Member member = memberRegistry.get(memberId);
        
        // Validations
        validateBorrowing(book, member);
        
        if (book.getAvailableCopies() == 0) {
            // Add to waitlist
            addToWaitlist(isbn, member);
            throw new BookNotAvailableException("Added to waitlist. Position: " + 
                waitlists.get(isbn).size());
        }
        
        // Create borrow record
        BorrowRecord record = new BorrowRecord();
        record.setRecordId(generateRecordId());
        record.setBook(book);
        record.setMember(member);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(calculateDueDate(member.getType()));
        record.setStatus(BorrowStatus.BORROWED);
        
        // Update book availability
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        
        activeRecords.put(record.getRecordId(), record);
        member.getBorrowHistory().add(record);
        
        return record;
    }
    
    private void addToWaitlist(String isbn, Member member) {
        waitlists.computeIfAbsent(isbn, k -> new LinkedList<>())
                 .offer(new Reservation(isbn, member));
    }
}
```

### Feature 3: Fine Calculation

```java
public class FineCalculator {
    
    public double calculateFine(BorrowRecord record) {
        if (record.getReturnDate() == null) {
            record.setReturnDate(LocalDate.now());
        }
        
        long daysOverdue = ChronoUnit.DAYS.between(
            record.getDueDate(), 
            record.getReturnDate()
        );
        
        if (daysOverdue <= 0) {
            return 0.0;
        }
        
        double finePerDay = record.getBook().getFinePerDay();
        double baseFine = daysOverdue * finePerDay;
        
        // Apply member type discount
        double discount = getMemberDiscount(record.getMember().getType());
        
        // Apply cap
        double maxFine = record.getBook().getFinePerDay() * 30; // 30-day cap
        
        return Math.min(baseFine * (1 - discount), maxFine);
    }
}
```

## 🚨 Edge Cases & Handling

| Edge Case | Scenario | Solution |
|-----------|----------|----------|
| **Duplicate ISBN** | Adding book with existing ISBN | Throw `DuplicateBookException`, offer update option |
| **Non-existent book** | Borrow/return unknown ISBN | Throw `BookNotFoundException` |
| **No available copies** | All copies borrowed | Add to waitlist Queue |
| **Member limit reached** | Member has max borrowed books | Throw `BorrowLimitExceededException` |
| **Overdue books** | Trying to borrow with overdues | Block until fines paid or books returned |
| **Concurrent borrowing** | Two members borrow last copy | Synchronize critical section |
| **Waitlist notification** | Book returned with waitlist | Notify first in Queue, auto-reserve for 24h |
| **Invalid return** | Return book not borrowed | Validate against active records |
| **Fine dispute** | Member disputes fine | Log all transactions with timestamps |
| **Book damage** | Book returned damaged | Additional damage fine, update book status |

## 📏 Constraints

```java
public class LibraryConstraints {
    // Borrowing limits by member type
    public static final Map<MemberType, Integer> BORROW_LIMITS = Map.of(
        MemberType.STUDENT, 3,
        MemberType.FACULTY, 10,
        MemberType.GENERAL, 5
    );
    
    // Loan periods (days) by member type
    public static final Map<MemberType, Integer> LOAN_PERIODS = Map.of(
        MemberType.STUDENT, 14,
        MemberType.FACULTY, 30,
        MemberType.GENERAL, 7
    );
    
    // Fine limits
    public static final double MAX_FINE_PER_BOOK = 50.0;
    public static final double MAX_OUTSTANDING_FINE = 100.0;
    
    // Reservation
    public static final int RESERVATION_HOLD_HOURS = 24;
    public static final int MAX_RESERVATIONS_PER_MEMBER = 3;
    
    // Search
    public static final int MAX_SEARCH_RESULTS = 100;
    public static final int MIN_SEARCH_LENGTH = 2;
}
```

## 🧪 Test Scenarios

```java
// Test: Concurrent borrowing of last copy
@Test
void testConcurrentBorrowingLastCopy() {
    Book book = createBookWithOneCopy();
    Member member1 = createMember("M001");
    Member member2 = createMember("M002");
    
    // Simulate concurrent borrowing
    ExecutorService executor = Executors.newFixedThreadPool(2);
    
    Future<BorrowRecord> future1 = executor.submit(() -> borrowService.borrowBook(book.getIsbn(), member1.getId()));
    Future<BorrowRecord> future2 = executor.submit(() -> borrowService.borrowBook(book.getIsbn(), member2.getId()));
    
    // One should succeed, one should be waitlisted
    // Assert exactly one BookNotAvailableException
}

// Test: Fine calculation with grace period
@Test
void testFineCalculationWithGracePeriod() {
    BorrowRecord record = createOverdueRecord(5); // 5 days overdue
    double fine = fineCalculator.calculateFine(record);
    
    // If grace period is 2 days
    assertEquals(3 * FINE_PER_DAY, fine);
}
```

## 📁 Project Structure

```
library-management/
├── src/
│   ├── model/
│   │   ├── Book.java
│   │   ├── Member.java
│   │   ├── BorrowRecord.java
│   │   ├── Reservation.java
│   │   └── enums/
│   │       ├── MemberType.java
│   │       ├── BorrowStatus.java
│   │       └── BookCategory.java
│   ├── service/
│   │   ├── BookService.java
│   │   ├── MemberService.java
│   │   ├── BorrowService.java
│   │   ├── SearchService.java
│   │   ├── FineCalculator.java
│   │   └── NotificationService.java
│   ├── repository/
│   │   ├── BookRepository.java
│   │   ├── MemberRepository.java
│   │   └── BorrowRepository.java
│   ├── exception/
│   │   ├── LibraryException.java
│   │   ├── BookNotFoundException.java
│   │   ├── MemberNotFoundException.java
│   │   ├── DuplicateBookException.java
│   │   ├── BookNotAvailableException.java
│   │   └── BorrowLimitExceededException.java
│   ├── util/
│   │   ├── LibraryConstraints.java
│   │   ├── IdGenerator.java
│   │   └── DateUtils.java
│   └── LibraryApplication.java
├── data/
│   ├── books.json
│   └── members.json
└── test/
    └── (mirror structure)
```

---

# 2️⃣ E-Commerce Order Processing System

## 📋 Description

A complete e-commerce order processing system handling product catalog, shopping cart, inventory management, order processing, and pricing with discounts.

## 🎯 Core Java Concepts Used

| Concept | Usage |
|---------|-------|
| **HashMap** | Product catalog, Shopping cart items |
| **LinkedHashMap** | Order history (maintain insertion order) |
| **TreeMap** | Price-sorted products, Time-sorted orders |
| **PriorityQueue** | Order processing by priority |
| **ConcurrentHashMap** | Thread-safe inventory |
| **Streams** | Cart calculations, filtering, reports |
| **Optional** | Null-safe product lookups |
| **Generics** | Generic repository pattern |
| **Comparator** | Multiple sorting strategies |
| **BigDecimal** | Precise monetary calculations |

## 📊 Data Models

```java
// Product Entity
class Product {
    private String productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private int stockQuantity;
    private ProductStatus status;  // ACTIVE, OUT_OF_STOCK, DISCONTINUED
    private double weight;         // For shipping calculation
}

// Cart Item
class CartItem {
    private Product product;
    private int quantity;
    private BigDecimal priceAtAddition;  // Price when added to cart
}

// Shopping Cart
class ShoppingCart {
    private String cartId;
    private String customerId;
    private Map<String, CartItem> items;  // productId -> CartItem
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
    private CartStatus status;
}

// Order
class Order {
    private String orderId;
    private String customerId;
    private List<OrderItem> items;
    private Address shippingAddress;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal tax;
    private BigDecimal shippingCost;
    private BigDecimal total;
    private OrderStatus status;
    private OrderPriority priority;
    private LocalDateTime orderDate;
    private LocalDateTime estimatedDelivery;
}

// Coupon
class Coupon {
    private String code;
    private CouponType type;        // PERCENTAGE, FIXED_AMOUNT, FREE_SHIPPING
    private BigDecimal value;
    private BigDecimal minOrderValue;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private int maxUsage;
    private int currentUsage;
    private List<String> applicableCategories;  // Empty = all categories
}
```

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      E-COMMERCE ORDER PROCESSING                            │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                         SERVICE LAYER                                │   │
│  │  ┌────────────┐ ┌────────────┐ ┌────────────┐ ┌────────────────┐   │   │
│  │  │ProductSvc  │ │ CartSvc    │ │ OrderSvc   │ │ InventorySvc   │   │   │
│  │  └────────────┘ └────────────┘ └────────────┘ └────────────────┘   │   │
│  │  ┌────────────┐ ┌────────────┐ ┌────────────┐                      │   │
│  │  │PricingSvc  │ │ CouponSvc  │ │ ShippingSvc│                      │   │
│  │  └────────────┘ └────────────┘ └────────────┘                      │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                        │
│  ┌─────────────────────────────────▼───────────────────────────────────┐   │
│  │                       DATA STRUCTURES                               │   │
│  │  ┌──────────────────────────────────────────────────────────────┐  │   │
│  │  │ ConcurrentHashMap<ProductId, Product> inventory              │  │   │
│  │  │ HashMap<CustomerId, ShoppingCart> activeCarts                │  │   │
│  │  │ LinkedHashMap<OrderId, Order> orderHistory                   │  │   │
│  │  │ PriorityQueue<Order> orderProcessingQueue                    │  │   │
│  │  │ TreeMap<BigDecimal, List<Product>> productsByPrice           │  │   │
│  │  │ Map<Category, TreeSet<Product>> productsByCategory           │  │   │
│  │  └──────────────────────────────────────────────────────────────┘  │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## ⚙️ Core Features & Implementation

### Feature 1: Shopping Cart with Price Lock

```java
public class CartService {
    private Map<String, ShoppingCart> activeCarts = new HashMap<>();
    private InventoryService inventoryService;
    
    public CartItem addToCart(String customerId, String productId, int quantity) {
        ShoppingCart cart = activeCarts.computeIfAbsent(customerId, 
            id -> new ShoppingCart(id));
        
        Product product = productService.getProduct(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
        
        // Validate stock
        if (!inventoryService.isAvailable(productId, quantity)) {
            int available = inventoryService.getAvailableQuantity(productId);
            throw new InsufficientStockException(
                String.format("Requested: %d, Available: %d", quantity, available)
            );
        }
        
        // Check if item already in cart
        CartItem existingItem = cart.getItems().get(productId);
        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + quantity;
            if (!inventoryService.isAvailable(productId, newQuantity)) {
                throw new InsufficientStockException("Cannot add more items");
            }
            existingItem.setQuantity(newQuantity);
            return existingItem;
        }
        
        // Create new cart item with current price (price lock)
        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setPriceAtAddition(product.getPrice());  // Lock price at this moment
        
        cart.getItems().put(productId, item);
        cart.setLastModified(LocalDateTime.now());
        
        return item;
    }
    
    public CartSummary getCartSummary(String customerId) {
        ShoppingCart cart = getCart(customerId);
        
        BigDecimal subtotal = cart.getItems().values().stream()
            .map(item -> item.getPriceAtAddition()
                .multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Check for price changes
        List<PriceChangeNotification> priceChanges = cart.getItems().values().stream()
            .filter(item -> !item.getPriceAtAddition()
                .equals(item.getProduct().getPrice()))
            .map(item -> new PriceChangeNotification(
                item.getProduct(),
                item.getPriceAtAddition(),
                item.getProduct().getPrice()
            ))
            .collect(Collectors.toList());
        
        return new CartSummary(subtotal, cart.getItems().size(), priceChanges);
    }
}
```

### Feature 2: Order Processing Queue with Priority

```java
public class OrderService {
    // Priority: EXPRESS > PRIME_MEMBER > STANDARD
    private PriorityQueue<Order> processingQueue = new PriorityQueue<>(
        Comparator.comparing(Order::getPriority)
                  .thenComparing(Order::getOrderDate)
    );
    
    private LinkedHashMap<String, Order> orderHistory = new LinkedHashMap<>();
    
    public Order placeOrder(String customerId, ShoppingCart cart, 
                           Address shippingAddress, String couponCode) {
        
        // Validate cart
        validateCart(cart);
        
        // Reserve inventory (atomic operation)
        Map<String, Integer> reservations = reserveInventory(cart);
        
        try {
            Order order = new Order();
            order.setOrderId(generateOrderId());
            order.setCustomerId(customerId);
            order.setItems(convertCartToOrderItems(cart));
            order.setShippingAddress(shippingAddress);
            order.setOrderDate(LocalDateTime.now());
            order.setStatus(OrderStatus.PENDING);
            order.setPriority(determinePriority(customerId));
            
            // Calculate pricing
            BigDecimal subtotal = calculateSubtotal(order.getItems());
            BigDecimal discount = applyCoupon(couponCode, subtotal, order.getItems());
            BigDecimal tax = calculateTax(subtotal.subtract(discount), shippingAddress);
            BigDecimal shipping = calculateShipping(order.getItems(), shippingAddress);
            
            order.setSubtotal(subtotal);
            order.setDiscount(discount);
            order.setTax(tax);
            order.setShippingCost(shipping);
            order.setTotal(subtotal.subtract(discount).add(tax).add(shipping));
            
            // Add to processing queue
            processingQueue.offer(order);
            orderHistory.put(order.getOrderId(), order);
            
            // Clear cart
            cartService.clearCart(customerId);
            
            return order;
            
        } catch (Exception e) {
            // Rollback inventory reservations
            releaseInventory(reservations);
            throw new OrderProcessingException("Failed to place order", e);
        }
    }
    
    // Process orders in priority order
    public void processNextOrder() {
        Order order = processingQueue.poll();
        if (order == null) return;
        
        try {
            // Confirm inventory deduction
            confirmInventoryDeduction(order);
            order.setStatus(OrderStatus.PROCESSING);
            
            // Calculate estimated delivery
            order.setEstimatedDelivery(calculateEstimatedDelivery(order));
            
            // Trigger notifications
            notificationService.sendOrderConfirmation(order);
            
        } catch (Exception e) {
            order.setStatus(OrderStatus.FAILED);
            // Handle failure...
        }
    }
}
```

### Feature 3: Dynamic Pricing & Coupon System

```java
public class PricingService {
    private Map<String, Coupon> activeCoupons = new HashMap<>();
    
    public PriceBreakdown calculatePrice(List<OrderItem> items, String couponCode) {
        BigDecimal subtotal = items.stream()
            .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal discount = BigDecimal.ZERO;
        
        // Apply quantity discounts
        discount = discount.add(calculateQuantityDiscount(items));
        
        // Apply coupon
        if (couponCode != null && !couponCode.isEmpty()) {
            discount = discount.add(applyCoupon(couponCode, subtotal, items));
        }
        
        // Ensure discount doesn't exceed subtotal
        discount = discount.min(subtotal);
        
        return new PriceBreakdown(subtotal, discount, subtotal.subtract(discount));
    }
    
    private BigDecimal applyCoupon(String code, BigDecimal subtotal, List<OrderItem> items) {
        Coupon coupon = activeCoupons.get(code.toUpperCase());
        
        if (coupon == null) {
            throw new InvalidCouponException("Coupon not found: " + code);
        }
        
        // Validate coupon
        validateCoupon(coupon, subtotal, items);
        
        return switch (coupon.getType()) {
            case PERCENTAGE -> subtotal.multiply(coupon.getValue())
                                       .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
            case FIXED_AMOUNT -> coupon.getValue();
            case FREE_SHIPPING -> BigDecimal.ZERO; // Handled in shipping calculation
        };
    }
    
    private void validateCoupon(Coupon coupon, BigDecimal subtotal, List<OrderItem> items) {
        LocalDate today = LocalDate.now();
        
        if (today.isBefore(coupon.getValidFrom())) {
            throw new InvalidCouponException("Coupon not yet valid");
        }
        if (today.isAfter(coupon.getValidUntil())) {
            throw new InvalidCouponException("Coupon has expired");
        }
        if (subtotal.compareTo(coupon.getMinOrderValue()) < 0) {
            throw new InvalidCouponException(
                "Minimum order value: " + coupon.getMinOrderValue());
        }
        if (coupon.getCurrentUsage() >= coupon.getMaxUsage()) {
            throw new InvalidCouponException("Coupon usage limit reached");
        }
        
        // Check category restrictions
        if (!coupon.getApplicableCategories().isEmpty()) {
            boolean hasApplicableItem = items.stream()
                .anyMatch(item -> coupon.getApplicableCategories()
                    .contains(item.getProduct().getCategory()));
            if (!hasApplicableItem) {
                throw new InvalidCouponException("Coupon not applicable to cart items");
            }
        }
    }
}
```

## 🚨 Edge Cases & Handling

| Edge Case | Scenario | Solution |
|-----------|----------|----------|
| **Race condition on last item** | Two users buy last item simultaneously | Use `ConcurrentHashMap` with atomic operations |
| **Price change during checkout** | Price changes after adding to cart | Lock price at cart addition, notify on changes |
| **Abandoned cart recovery** | Cart inactive for days | Schedule cleanup, send reminder emails |
| **Inventory oversell** | More orders than stock | Reserve inventory at checkout, queue backorders |
| **Coupon abuse** | Same coupon used multiple times | Track usage per customer, implement limits |
| **Payment failure after inventory reserved** | Order fails at payment | Release inventory after timeout |
| **Partial fulfillment** | Some items out of stock | Allow partial orders, refund unavailable items |
| **Currency precision** | Floating-point errors | Use `BigDecimal` for all monetary calculations |
| **Cart size limit** | Extremely large carts | Limit items per cart (e.g., 100 items) |
| **Concurrent cart modifications** | Multiple tabs editing cart | Last-write-wins or optimistic locking |

## 📏 Constraints

```java
public class ECommerceConstraints {
    // Cart constraints
    public static final int MAX_ITEMS_PER_CART = 100;
    public static final int MAX_QUANTITY_PER_ITEM = 10;
    public static final Duration CART_EXPIRY = Duration.ofDays(7);
    public static final Duration CART_PRICE_LOCK = Duration.ofHours(24);
    
    // Order constraints
    public static final BigDecimal MIN_ORDER_VALUE = new BigDecimal("10.00");
    public static final BigDecimal MAX_ORDER_VALUE = new BigDecimal("10000.00");
    public static final int MAX_ORDERS_PER_DAY = 5;
    
    // Inventory
    public static final Duration INVENTORY_RESERVATION_TIMEOUT = Duration.ofMinutes(15);
    public static final int LOW_STOCK_THRESHOLD = 5;
    
    // Coupon
    public static final int MAX_COUPONS_PER_ORDER = 1;
    public static final BigDecimal MAX_DISCOUNT_PERCENTAGE = new BigDecimal("50");
}
```

## 📁 Project Structure

```
ecommerce-order-system/
├── src/
│   ├── model/
│   │   ├── Product.java
│   │   ├── CartItem.java
│   │   ├── ShoppingCart.java
│   │   ├── Order.java
│   │   ├── OrderItem.java
│   │   ├── Coupon.java
│   │   └── enums/
│   ├── service/
│   │   ├── ProductService.java
│   │   ├── CartService.java
│   │   ├── OrderService.java
│   │   ├── InventoryService.java
│   │   ├── PricingService.java
│   │   ├── CouponService.java
│   │   └── ShippingService.java
│   ├── repository/
│   ├── exception/
│   └── util/
└── test/
```

---

# 3️⃣ Banking Transaction System

## 📋 Description

A banking system handling account management, transactions (deposit, withdraw, transfer), transaction history, and concurrent access for ATM/online banking scenarios.

## 🎯 Core Java Concepts Used

| Concept | Usage |
|---------|-------|
| **ConcurrentHashMap** | Thread-safe account storage |
| **LinkedList** | Transaction history per account |
| **TreeMap** | Transactions sorted by date |
| **BlockingQueue** | Transaction processing queue |
| **synchronized/ReentrantLock** | Thread-safe balance operations |
| **AtomicLong** | Thread-safe counters |
| **Streams** | Statement generation, analytics |
| **BigDecimal** | Precise monetary calculations |
| **Exception Handling** | Transaction failures |
| **Optional** | Null-safe account lookups |

## 📊 Data Models

```java
// Account Entity
class Account {
    private String accountNumber;
    private String customerId;
    private AccountType type;         // SAVINGS, CURRENT, FIXED_DEPOSIT
    private BigDecimal balance;
    private BigDecimal minBalance;
    private BigDecimal dailyLimit;
    private AccountStatus status;     // ACTIVE, FROZEN, CLOSED
    private LocalDateTime createdAt;
    private LinkedList<Transaction> recentTransactions;
    
    // For thread-safety
    private final ReentrantLock lock = new ReentrantLock();
}

// Transaction Entity
class Transaction {
    private String transactionId;
    private String accountNumber;
    private TransactionType type;     // DEPOSIT, WITHDRAWAL, TRANSFER_IN, TRANSFER_OUT
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private String description;
    private String referenceNumber;   // For transfers
    private LocalDateTime timestamp;
    private TransactionStatus status; // PENDING, COMPLETED, FAILED, REVERSED
}

// Transfer Request
class TransferRequest {
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String description;
    private TransferType type;        // IMMEDIATE, SCHEDULED, RECURRING
    private LocalDateTime scheduledTime;
}
```

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      BANKING TRANSACTION SYSTEM                             │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                         API LAYER                                    │   │
│  │  ┌─────────────┐ ┌─────────────┐ ┌─────────────┐                   │   │
│  │  │  ATM API    │ │ Online API  │ │ Mobile API  │                   │   │
│  │  └─────────────┘ └─────────────┘ └─────────────┘                   │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                        │
│  ┌─────────────────────────────────▼───────────────────────────────────┐   │
│  │                       SERVICE LAYER                                 │   │
│  │  ┌─────────────┐ ┌─────────────────┐ ┌─────────────────────────┐   │   │
│  │  │AccountSvc   │ │TransactionSvc   │ │TransferSvc              │   │   │
│  │  └─────────────┘ └─────────────────┘ └─────────────────────────┘   │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                        │
│  ┌─────────────────────────────────▼───────────────────────────────────┐   │
│  │                     THREAD-SAFE DATA LAYER                          │   │
│  │  ┌──────────────────────────────────────────────────────────────┐  │   │
│  │  │ ConcurrentHashMap<AccountNumber, Account> accounts           │  │   │
│  │  │ BlockingQueue<TransferRequest> transferQueue                 │  │   │
│  │  │ Map<AccountNumber, TreeMap<DateTime, Transaction>> history   │  │   │
│  │  │ AtomicLong transactionCounter                                │  │   │
│  │  │ Map<AccountNumber, AtomicReference<BigDecimal>> dailyTotals  │  │   │
│  │  └──────────────────────────────────────────────────────────────┘  │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## ⚙️ Core Features & Implementation

### Feature 1: Thread-Safe Account Operations

```java
public class AccountService {
    private final ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();
    private final AtomicLong transactionIdGenerator = new AtomicLong(1000000);
    
    public Transaction deposit(String accountNumber, BigDecimal amount) {
        validateAmount(amount);
        
        Account account = getAccountOrThrow(accountNumber);
        
        // Thread-safe operation using account's lock
        account.getLock().lock();
        try {
            validateAccountStatus(account);
            
            BigDecimal newBalance = account.getBalance().add(amount);
            account.setBalance(newBalance);
            
            Transaction txn = createTransaction(
                account, TransactionType.DEPOSIT, amount, newBalance
            );
            
            account.getRecentTransactions().addFirst(txn);
            trimTransactionHistory(account);
            
            return txn;
            
        } finally {
            account.getLock().unlock();
        }
    }
    
    public Transaction withdraw(String accountNumber, BigDecimal amount) {
        validateAmount(amount);
        
        Account account = getAccountOrThrow(accountNumber);
        
        account.getLock().lock();
        try {
            validateAccountStatus(account);
            validateSufficientBalance(account, amount);
            validateDailyLimit(account, amount);
            
            BigDecimal newBalance = account.getBalance().subtract(amount);
            
            // Check minimum balance
            if (newBalance.compareTo(account.getMinBalance()) < 0) {
                throw new InsufficientBalanceException(
                    "Withdrawal would breach minimum balance requirement"
                );
            }
            
            account.setBalance(newBalance);
            updateDailyWithdrawal(accountNumber, amount);
            
            Transaction txn = createTransaction(
                account, TransactionType.WITHDRAWAL, amount, newBalance
            );
            
            account.getRecentTransactions().addFirst(txn);
            trimTransactionHistory(account);
            
            return txn;
            
        } finally {
            account.getLock().unlock();
        }
    }
}
```

### Feature 2: Deadlock-Free Transfer

```java
public class TransferService {
    private final AccountService accountService;
    
    public TransferResult transfer(TransferRequest request) {
        validateTransferRequest(request);
        
        Account fromAccount = accountService.getAccountOrThrow(request.getFromAccount());
        Account toAccount = accountService.getAccountOrThrow(request.getToAccount());
        
        // CRITICAL: Always lock accounts in consistent order to prevent deadlock
        Account firstLock = fromAccount.getAccountNumber().compareTo(
            toAccount.getAccountNumber()) < 0 ? fromAccount : toAccount;
        Account secondLock = firstLock == fromAccount ? toAccount : fromAccount;
        
        firstLock.getLock().lock();
        try {
            secondLock.getLock().lock();
            try {
                return executeTransfer(fromAccount, toAccount, request.getAmount());
            } finally {
                secondLock.getLock().unlock();
            }
        } finally {
            firstLock.getLock().unlock();
        }
    }
    
    private TransferResult executeTransfer(Account from, Account to, BigDecimal amount) {
        // Validate source account
        validateAccountStatus(from);
        validateSufficientBalance(from, amount);
        validateDailyLimit(from, amount);
        
        // Validate destination account
        validateAccountStatus(to);
        
        String referenceNumber = generateReferenceNumber();
        
        // Debit source
        BigDecimal fromNewBalance = from.getBalance().subtract(amount);
        from.setBalance(fromNewBalance);
        
        Transaction debitTxn = createTransaction(
            from, TransactionType.TRANSFER_OUT, amount, fromNewBalance, referenceNumber
        );
        
        // Credit destination
        BigDecimal toNewBalance = to.getBalance().add(amount);
        to.setBalance(toNewBalance);
        
        Transaction creditTxn = createTransaction(
            to, TransactionType.TRANSFER_IN, amount, toNewBalance, referenceNumber
        );
        
        return new TransferResult(referenceNumber, debitTxn, creditTxn);
    }
}
```

### Feature 3: Transaction History with Efficient Queries

```java
public class TransactionHistoryService {
    // AccountNumber -> (Timestamp -> Transaction) for range queries
    private final Map<String, TreeMap<LocalDateTime, Transaction>> transactionHistory 
        = new ConcurrentHashMap<>();
    
    public void recordTransaction(Transaction txn) {
        transactionHistory.computeIfAbsent(txn.getAccountNumber(), 
            k -> new TreeMap<>())
            .put(txn.getTimestamp(), txn);
    }
    
    // Get transactions in date range - O(log n + k) where k is result size
    public List<Transaction> getTransactions(String accountNumber, 
                                             LocalDateTime from, 
                                             LocalDateTime to) {
        TreeMap<LocalDateTime, Transaction> accountHistory = 
            transactionHistory.get(accountNumber);
        
        if (accountHistory == null) {
            return Collections.emptyList();
        }
        
        return new ArrayList<>(
            accountHistory.subMap(from, true, to, true).values()
        );
    }
    
    // Generate statement with running balance
    public Statement generateStatement(String accountNumber, 
                                       LocalDateTime from, 
                                       LocalDateTime to) {
        List<Transaction> transactions = getTransactions(accountNumber, from, to);
        
        // Calculate opening balance
        BigDecimal openingBalance = calculateOpeningBalance(accountNumber, from);
        
        // Generate statement lines with running balance
        List<StatementLine> lines = new ArrayList<>();
        BigDecimal runningBalance = openingBalance;
        
        for (Transaction txn : transactions) {
            if (txn.getType() == TransactionType.DEPOSIT || 
                txn.getType() == TransactionType.TRANSFER_IN) {
                runningBalance = runningBalance.add(txn.getAmount());
            } else {
                runningBalance = runningBalance.subtract(txn.getAmount());
            }
            
            lines.add(new StatementLine(txn, runningBalance));
        }
        
        return new Statement(accountNumber, from, to, openingBalance, 
                            runningBalance, lines);
    }
    
    // Analytics - Total debits/credits per category
    public Map<TransactionType, BigDecimal> getTransactionSummary(
            String accountNumber, LocalDateTime from, LocalDateTime to) {
        
        return getTransactions(accountNumber, from, to).stream()
            .collect(Collectors.groupingBy(
                Transaction::getType,
                Collectors.reducing(
                    BigDecimal.ZERO,
                    Transaction::getAmount,
                    BigDecimal::add
                )
            ));
    }
}
```

## 🚨 Edge Cases & Handling

| Edge Case | Scenario | Solution |
|-----------|----------|----------|
| **Concurrent withdrawals** | Two ATMs withdraw from same account | ReentrantLock per account |
| **Transfer deadlock** | A→B and B→A simultaneously | Always lock accounts in sorted order |
| **Daily limit exceeded** | Multiple small transactions | Track daily total with AtomicReference |
| **Insufficient balance race** | Balance check vs actual debit | Atomic check-and-debit inside lock |
| **Account frozen mid-transaction** | Status changes during transfer | Re-validate status inside lock |
| **Transaction rollback** | Transfer credit fails after debit | Use compensation transactions |
| **Duplicate transaction** | Network retry sends twice | Idempotency key, check before processing |
| **Overdraft** | Withdrawal exceeds balance | Validate balance > amount + minBalance |
| **Self-transfer** | Transfer to same account | Block or auto-succeed with no change |
| **System crash during transfer** | Server crashes mid-transfer | Transaction log + recovery mechanism |

## 📏 Constraints

```java
public class BankingConstraints {
    // Account constraints
    public static final BigDecimal SAVINGS_MIN_BALANCE = new BigDecimal("1000.00");
    public static final BigDecimal CURRENT_MIN_BALANCE = new BigDecimal("5000.00");
    
    // Transaction limits
    public static final BigDecimal ATM_WITHDRAWAL_LIMIT = new BigDecimal("20000.00");
    public static final BigDecimal DAILY_TRANSFER_LIMIT = new BigDecimal("200000.00");
    public static final BigDecimal SINGLE_TRANSACTION_MAX = new BigDecimal("100000.00");
    
    // Rate limiting
    public static final int MAX_TRANSACTIONS_PER_MINUTE = 10;
    public static final int MAX_FAILED_ATTEMPTS = 3;
    public static final Duration LOCKOUT_DURATION = Duration.ofMinutes(30);
    
    // History retention
    public static final int RECENT_TRANSACTIONS_LIMIT = 100;
    public static final Duration TRANSACTION_HISTORY_RETENTION = Duration.ofDays(365 * 7);
}
```

## 📁 Project Structure

```
banking-system/
├── src/
│   ├── model/
│   │   ├── Account.java
│   │   ├── Transaction.java
│   │   ├── TransferRequest.java
│   │   ├── Statement.java
│   │   └── enums/
│   ├── service/
│   │   ├── AccountService.java
│   │   ├── TransactionService.java
│   │   ├── TransferService.java
│   │   ├── TransactionHistoryService.java
│   │   └── StatementService.java
│   ├── exception/
│   │   ├── BankingException.java
│   │   ├── InsufficientBalanceException.java
│   │   ├── AccountNotFoundException.java
│   │   ├── DailyLimitExceededException.java
│   │   └── AccountFrozenException.java
│   └── util/
└── test/
```

---

# 4️⃣ Task Scheduler & Reminder App

## 📋 Description

A task management application with priority scheduling, reminders, recurring tasks, categories, and deadline tracking.

## 🎯 Core Java Concepts Used

| Concept | Usage |
|---------|-------|
| **PriorityQueue** | Tasks sorted by priority/deadline |
| **TreeMap** | Tasks sorted by date |
| **HashMap** | Task lookup by ID, category mapping |
| **LinkedHashSet** | Maintain task order with no duplicates |
| **ScheduledExecutorService** | Timer for reminders |
| **Comparable/Comparator** | Multiple sorting strategies |
| **Streams** | Filtering, grouping tasks |
| **Optional** | Null-safe operations |
| **Enum** | Task status, priority levels |
| **LocalDateTime API** | Date/time handling |

## 📊 Data Models

```java
// Task Entity
class Task {
    private String taskId;
    private String title;
    private String description;
    private Priority priority;          // URGENT, HIGH, MEDIUM, LOW
    private TaskStatus status;          // TODO, IN_PROGRESS, COMPLETED, CANCELLED
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private LocalDateTime completedAt;
    private Duration estimatedDuration;
    private List<String> tags;
    private RecurrenceRule recurrence;  // null if not recurring
    private List<Reminder> reminders;
}

// Reminder
class Reminder {
    private String reminderId;
    private String taskId;
    private LocalDateTime reminderTime;
    private ReminderType type;          // EMAIL, PUSH, IN_APP
    private boolean sent;
}

// Recurrence Rule
class RecurrenceRule {
    private RecurrenceType type;        // DAILY, WEEKLY, MONTHLY, YEARLY
    private int interval;               // Every X days/weeks/months
    private Set<DayOfWeek> daysOfWeek;  // For weekly
    private int dayOfMonth;             // For monthly
    private LocalDate endDate;          // null = forever
    private int maxOccurrences;         // 0 = unlimited
}

// Task Filter
class TaskFilter {
    private Set<Priority> priorities;
    private Set<TaskStatus> statuses;
    private Set<String> categories;
    private Set<String> tags;
    private LocalDateTime deadlineFrom;
    private LocalDateTime deadlineTo;
    private String searchTerm;
}
```

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        TASK SCHEDULER SYSTEM                                │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                       SCHEDULER ENGINE                              │   │
│  │  ScheduledExecutorService                                           │   │
│  │  ├── Reminder Checker (every minute)                               │   │
│  │  ├── Recurring Task Generator (daily)                              │   │
│  │  └── Overdue Task Notifier (hourly)                                │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                        │
│  ┌─────────────────────────────────▼───────────────────────────────────┐   │
│  │                       SERVICE LAYER                                 │   │
│  │  ┌─────────────┐ ┌─────────────────┐ ┌─────────────────────────┐   │   │
│  │  │ TaskService │ │ ReminderService │ │ RecurrenceService       │   │   │
│  │  └─────────────┘ └─────────────────┘ └─────────────────────────┘   │   │
│  │  ┌─────────────┐ ┌─────────────────┐                               │   │
│  │  │SearchService│ │ ReportService   │                               │   │
│  │  └─────────────┘ └─────────────────┘                               │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                        │
│  ┌─────────────────────────────────▼───────────────────────────────────┐   │
│  │                       DATA STRUCTURES                               │   │
│  │  ┌──────────────────────────────────────────────────────────────┐  │   │
│  │  │ HashMap<TaskId, Task> taskStore                              │  │   │
│  │  │ PriorityQueue<Task> urgentTasks (by deadline)                │  │   │
│  │  │ TreeMap<LocalDate, List<Task>> tasksByDate                   │  │   │
│  │  │ Map<Category, LinkedHashSet<Task>> tasksByCategory           │  │   │
│  │  │ Map<Tag, Set<Task>> tasksByTag                               │  │   │
│  │  │ PriorityQueue<Reminder> pendingReminders (by time)           │  │   │
│  │  └──────────────────────────────────────────────────────────────┘  │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## ⚙️ Core Features & Implementation

### Feature 1: Priority-Based Task Queue

```java
public class TaskService {
    private final Map<String, Task> taskStore = new HashMap<>();
    
    // Priority queue ordered by: Priority (desc) -> Deadline (asc) -> Created (asc)
    private final PriorityQueue<Task> taskQueue = new PriorityQueue<>(
        Comparator.comparing(Task::getPriority)
                  .thenComparing(Task::getDeadline, Comparator.nullsLast(Comparator.naturalOrder()))
                  .thenComparing(Task::getCreatedAt)
    );
    
    private final TreeMap<LocalDate, List<Task>> tasksByDate = new TreeMap<>();
    private final Map<String, LinkedHashSet<Task>> tasksByCategory = new HashMap<>();
    
    public Task createTask(TaskCreateRequest request) {
        Task task = new Task();
        task.setTaskId(generateTaskId());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());
        task.setDeadline(request.getDeadline());
        task.setCategory(request.getCategory());
        task.setTags(request.getTags());
        task.setCreatedAt(LocalDateTime.now());
        task.setStatus(TaskStatus.TODO);
        
        // Store in all data structures
        taskStore.put(task.getTaskId(), task);
        taskQueue.offer(task);
        
        if (task.getDeadline() != null) {
            tasksByDate.computeIfAbsent(task.getDeadline().toLocalDate(), 
                k -> new ArrayList<>()).add(task);
        }
        
        tasksByCategory.computeIfAbsent(task.getCategory(), 
            k -> new LinkedHashSet<>()).add(task);
        
        // Create default reminder if deadline exists
        if (task.getDeadline() != null) {
            createDefaultReminders(task);
        }
        
        // Handle recurrence
        if (request.getRecurrence() != null) {
            task.setRecurrence(request.getRecurrence());
        }
        
        return task;
    }
    
    // Get next most important task
    public Optional<Task> getNextTask() {
        while (!taskQueue.isEmpty()) {
            Task task = taskQueue.peek();
            // Skip completed/cancelled tasks
            if (task.getStatus() == TaskStatus.TODO || 
                task.getStatus() == TaskStatus.IN_PROGRESS) {
                return Optional.of(task);
            }
            taskQueue.poll(); // Remove stale task
        }
        return Optional.empty();
    }
    
    // Get tasks due today
    public List<Task> getTasksDueToday() {
        LocalDate today = LocalDate.now();
        return tasksByDate.getOrDefault(today, Collections.emptyList())
            .stream()
            .filter(t -> t.getStatus() != TaskStatus.COMPLETED)
            .sorted(Comparator.comparing(Task::getPriority)
                             .thenComparing(Task::getDeadline))
            .collect(Collectors.toList());
    }
    
    // Get overdue tasks
    public List<Task> getOverdueTasks() {
        LocalDateTime now = LocalDateTime.now();
        
        // Get all dates before today
        return tasksByDate.headMap(LocalDate.now(), false)
            .values()
            .stream()
            .flatMap(List::stream)
            .filter(t -> t.getStatus() != TaskStatus.COMPLETED && 
                        t.getStatus() != TaskStatus.CANCELLED)
            .sorted(Comparator.comparing(Task::getDeadline))
            .collect(Collectors.toList());
    }
}
```

### Feature 2: Reminder System

```java
public class ReminderService {
    private final PriorityQueue<Reminder> pendingReminders = new PriorityQueue<>(
        Comparator.comparing(Reminder::getReminderTime)
    );
    
    private final ScheduledExecutorService scheduler = 
        Executors.newScheduledThreadPool(2);
    
    public void initialize() {
        // Check for due reminders every minute
        scheduler.scheduleAtFixedRate(
            this::processReminders,
            0, 1, TimeUnit.MINUTES
        );
    }
    
    public Reminder createReminder(String taskId, LocalDateTime reminderTime, 
                                   ReminderType type) {
        Task task = taskService.getTask(taskId)
            .orElseThrow(() -> new TaskNotFoundException(taskId));
        
        // Validate reminder time
        if (reminderTime.isBefore(LocalDateTime.now())) {
            throw new InvalidReminderException("Reminder time must be in future");
        }
        
        if (task.getDeadline() != null && reminderTime.isAfter(task.getDeadline())) {
            throw new InvalidReminderException("Reminder cannot be after deadline");
        }
        
        Reminder reminder = new Reminder();
        reminder.setReminderId(generateReminderId());
        reminder.setTaskId(taskId);
        reminder.setReminderTime(reminderTime);
        reminder.setType(type);
        reminder.setSent(false);
        
        pendingReminders.offer(reminder);
        task.getReminders().add(reminder);
        
        return reminder;
    }
    
    private void processReminders() {
        LocalDateTime now = LocalDateTime.now();
        
        while (!pendingReminders.isEmpty() && 
               !pendingReminders.peek().getReminderTime().isAfter(now)) {
            
            Reminder reminder = pendingReminders.poll();
            
            if (reminder.isSent()) continue;
            
            // Get associated task
            Task task = taskService.getTask(reminder.getTaskId()).orElse(null);
            
            // Skip if task completed or cancelled
            if (task == null || 
                task.getStatus() == TaskStatus.COMPLETED ||
                task.getStatus() == TaskStatus.CANCELLED) {
                continue;
            }
            
            // Send reminder
            sendReminder(reminder, task);
            reminder.setSent(true);
        }
    }
    
    // Create default reminders based on deadline
    public void createDefaultReminders(Task task) {
        if (task.getDeadline() == null) return;
        
        LocalDateTime deadline = task.getDeadline();
        LocalDateTime now = LocalDateTime.now();
        
        // 1 day before
        LocalDateTime dayBefore = deadline.minusDays(1);
        if (dayBefore.isAfter(now)) {
            createReminder(task.getTaskId(), dayBefore, ReminderType.PUSH);
        }
        
        // 1 hour before
        LocalDateTime hourBefore = deadline.minusHours(1);
        if (hourBefore.isAfter(now)) {
            createReminder(task.getTaskId(), hourBefore, ReminderType.PUSH);
        }
        
        // At deadline
        if (deadline.isAfter(now)) {
            createReminder(task.getTaskId(), deadline, ReminderType.PUSH);
        }
    }
}
```

### Feature 3: Advanced Search & Filtering

```java
public class SearchService {
    private final Map<String, Task> taskStore;
    private final Map<String, Set<Task>> tasksByTag = new HashMap<>();
    
    public List<Task> searchTasks(TaskFilter filter) {
        Stream<Task> taskStream = taskStore.values().stream();
        
        // Apply filters
        if (filter.getPriorities() != null && !filter.getPriorities().isEmpty()) {
            taskStream = taskStream.filter(t -> 
                filter.getPriorities().contains(t.getPriority()));
        }
        
        if (filter.getStatuses() != null && !filter.getStatuses().isEmpty()) {
            taskStream = taskStream.filter(t -> 
                filter.getStatuses().contains(t.getStatus()));
        }
        
        if (filter.getCategories() != null && !filter.getCategories().isEmpty()) {
            taskStream = taskStream.filter(t -> 
                filter.getCategories().contains(t.getCategory()));
        }
        
        if (filter.getTags() != null && !filter.getTags().isEmpty()) {
            taskStream = taskStream.filter(t -> 
                t.getTags() != null && 
                !Collections.disjoint(t.getTags(), filter.getTags()));
        }
        
        if (filter.getDeadlineFrom() != null) {
            taskStream = taskStream.filter(t -> 
                t.getDeadline() != null && 
                !t.getDeadline().isBefore(filter.getDeadlineFrom()));
        }
        
        if (filter.getDeadlineTo() != null) {
            taskStream = taskStream.filter(t -> 
                t.getDeadline() != null && 
                !t.getDeadline().isAfter(filter.getDeadlineTo()));
        }
        
        if (filter.getSearchTerm() != null && !filter.getSearchTerm().isBlank()) {
            String searchLower = filter.getSearchTerm().toLowerCase();
            taskStream = taskStream.filter(t -> 
                t.getTitle().toLowerCase().contains(searchLower) ||
                (t.getDescription() != null && 
                 t.getDescription().toLowerCase().contains(searchLower)));
        }
        
        return taskStream.collect(Collectors.toList());
    }
    
    // Group tasks by category with counts
    public Map<String, Long> getTaskCountByCategory() {
        return taskStore.values().stream()
            .filter(t -> t.getStatus() != TaskStatus.COMPLETED)
            .collect(Collectors.groupingBy(
                Task::getCategory,
                Collectors.counting()
            ));
    }
    
    // Get productivity stats
    public ProductivityStats getProductivityStats(LocalDate from, LocalDate to) {
        List<Task> completedTasks = taskStore.values().stream()
            .filter(t -> t.getStatus() == TaskStatus.COMPLETED)
            .filter(t -> {
                LocalDate completedDate = t.getCompletedAt().toLocalDate();
                return !completedDate.isBefore(from) && !completedDate.isAfter(to);
            })
            .collect(Collectors.toList());
        
        long onTime = completedTasks.stream()
            .filter(t -> t.getDeadline() == null || 
                        !t.getCompletedAt().isAfter(t.getDeadline()))
            .count();
        
        Map<Priority, Long> byPriority = completedTasks.stream()
            .collect(Collectors.groupingBy(Task::getPriority, Collectors.counting()));
        
        return new ProductivityStats(
            completedTasks.size(),
            onTime,
            completedTasks.size() - onTime,
            byPriority
        );
    }
}
```

## 🚨 Edge Cases & Handling

| Edge Case | Scenario | Solution |
|-----------|----------|----------|
| **Past deadline** | Creating task with past deadline | Allow with warning, auto-mark overdue |
| **Reminder in past** | Reminder time already passed | Send immediately or skip |
| **Recurring task completed** | Mark recurring task done | Generate next occurrence automatically |
| **Recurring end date** | Recurrence ends | Stop generating new occurrences |
| **Duplicate reminders** | Multiple reminders at same time | Batch and send single notification |
| **Task update after reminder sent** | Deadline extended | Cancel old reminders, create new ones |
| **Orphan reminders** | Task deleted but reminder exists | Clean up reminders when task deleted |
| **Time zone handling** | User in different timezone | Store in UTC, convert for display |
| **Bulk operations** | Mark many tasks complete | Batch updates, update indices efficiently |
| **Empty queue** | No tasks in priority queue | Return Optional.empty() |

## 📏 Constraints

```java
public class TaskConstraints {
    // Task limits
    public static final int MAX_TITLE_LENGTH = 200;
    public static final int MAX_DESCRIPTION_LENGTH = 5000;
    public static final int MAX_TAGS_PER_TASK = 10;
    public static final int MAX_REMINDERS_PER_TASK = 5;
    
    // Reminder constraints
    public static final Duration MIN_REMINDER_ADVANCE = Duration.ofMinutes(5);
    public static final Duration MAX_REMINDER_ADVANCE = Duration.ofDays(30);
    
    // Recurrence limits
    public static final int MAX_RECURRENCE_INTERVAL = 365;
    public static final int MAX_RECURRENCE_OCCURRENCES = 100;
    
    // Search constraints
    public static final int MAX_SEARCH_RESULTS = 500;
    public static final int MIN_SEARCH_TERM_LENGTH = 2;
    
    // Performance
    public static final int MAX_ACTIVE_TASKS = 10000;
}
```

## 📁 Project Structure

```
task-scheduler/
├── src/
│   ├── model/
│   │   ├── Task.java
│   │   ├── Reminder.java
│   │   ├── RecurrenceRule.java
│   │   ├── TaskFilter.java
│   │   └── enums/
│   ├── service/
│   │   ├── TaskService.java
│   │   ├── ReminderService.java
│   │   ├── RecurrenceService.java
│   │   ├── SearchService.java
│   │   └── ReportService.java
│   ├── scheduler/
│   │   └── TaskScheduler.java
│   ├── exception/
│   └── util/
└── test/
```

---

# 5️⃣ Real-Time Chat Application

## 📋 Description

A multi-user chat application with private messaging, group chats, message history, online status tracking, and message delivery status.

## 🎯 Core Java Concepts Used

| Concept | Usage |
|---------|-------|
| **ConcurrentHashMap** | Thread-safe user sessions, chat rooms |
| **CopyOnWriteArrayList** | Thread-safe participant lists |
| **LinkedBlockingQueue** | Message queue per user |
| **TreeMap** | Messages sorted by timestamp |
| **LinkedHashMap** | LRU cache for recent messages |
| **HashSet** | Online users, read receipts |
| **Streams** | Message filtering, search |
| **Observer Pattern** | Message delivery notifications |
| **ExecutorService** | Async message processing |
| **CompletableFuture** | Async operations |

## 📊 Data Models

```java
// User Entity
class User {
    private String odUserId;
    private String username;
    private String displayName;
    private UserStatus status;          // ONLINE, AWAY, OFFLINE
    private LocalDateTime lastSeen;
    private Set<String> blockedUsers;
}

// Message Entity
class Message {
    private String messageId;
    private String senderId;
    private String chatId;              // User ID for DM, Group ID for group
    private MessageType type;           // TEXT, IMAGE, FILE, SYSTEM
    private String content;
    private LocalDateTime sentAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime readAt;
    private MessageStatus status;       // SENT, DELIVERED, READ
    private String replyToMessageId;    // For threaded replies
}

// Chat Room (Group)
class ChatRoom {
    private String roomId;
    private String name;
    private ChatRoomType type;          // PRIVATE (DM), GROUP
    private Set<String> participants;
    private String creatorId;
    private LocalDateTime createdAt;
    private TreeMap<LocalDateTime, Message> messages;
}

// Message Delivery Receipt
class DeliveryReceipt {
    private String messageId;
    private String recipientId;
    private DeliveryStatus status;
    private LocalDateTime timestamp;
}
```

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        CHAT APPLICATION SYSTEM                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                      MESSAGE BROKER                                 │   │
│  │  ┌──────────────────────────────────────────────────────────────┐  │   │
│  │  │ BlockingQueue<Message> incomingMessages                      │  │   │
│  │  │ Map<UserId, BlockingQueue<Message>> userMessageQueues        │  │   │
│  │  │ ExecutorService messageProcessors                            │  │   │
│  │  └──────────────────────────────────────────────────────────────┘  │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                        │
│  ┌─────────────────────────────────▼───────────────────────────────────┐   │
│  │                       SERVICE LAYER                                 │   │
│  │  ┌─────────────┐ ┌─────────────────┐ ┌─────────────────────────┐   │   │
│  │  │ UserService │ │ ChatRoomService │ │ MessageService          │   │   │
│  │  └─────────────┘ └─────────────────┘ └─────────────────────────┘   │   │
│  │  ┌─────────────┐ ┌─────────────────┐                               │   │
│  │  │PresencesSvc │ │ DeliverySvc     │                               │   │
│  │  └─────────────┘ └─────────────────┘                               │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                    │                                        │
│  ┌─────────────────────────────────▼───────────────────────────────────┐   │
│  │                       DATA STRUCTURES                               │   │
│  │  ┌──────────────────────────────────────────────────────────────┐  │   │
│  │  │ ConcurrentHashMap<UserId, User> onlineUsers                  │  │   │
│  │  │ ConcurrentHashMap<RoomId, ChatRoom> chatRooms                │  │   │
│  │  │ Map<UserId, Set<RoomId>> userChatRooms                       │  │   │
│  │  │ LRUCache<RoomId, List<Message>> recentMessages               │  │   │
│  │  │ Map<MessageId, Set<DeliveryReceipt>> deliveryReceipts        │  │   │
│  │  └──────────────────────────────────────────────────────────────┘  │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## ⚙️ Core Features & Implementation

### Feature 1: Thread-Safe Message Delivery

```java
public class MessageService {
    private final ConcurrentHashMap<String, ChatRoom> chatRooms = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, BlockingQueue<Message>> userQueues = 
        new ConcurrentHashMap<>();
    private final BlockingQueue<Message> incomingMessages = new LinkedBlockingQueue<>();
    private final ExecutorService messageProcessors = Executors.newFixedThreadPool(4);
    
    public void initialize() {
        // Start message processor threads
        for (int i = 0; i < 4; i++) {
            messageProcessors.submit(this::processMessages);
        }
    }
    
    public CompletableFuture<Message> sendMessage(String senderId, String chatId, 
                                                   String content) {
        return CompletableFuture.supplyAsync(() -> {
            // Validate sender
            User sender = userService.getUser(senderId)
                .orElseThrow(() -> new UserNotFoundException(senderId));
            
            // Validate chat room
            ChatRoom room = chatRooms.get(chatId);
            if (room == null) {
                throw new ChatRoomNotFoundException(chatId);
            }
            
            // Check if user is participant
            if (!room.getParticipants().contains(senderId)) {
                throw new NotParticipantException(senderId, chatId);
            }
            
            // Create message
            Message message = new Message();
            message.setMessageId(generateMessageId());
            message.setSenderId(senderId);
            message.setChatId(chatId);
            message.setContent(content);
            message.setSentAt(LocalDateTime.now());
            message.setStatus(MessageStatus.SENT);
            
            // Add to incoming queue for processing
            incomingMessages.offer(message);
            
            return message;
        });
    }
    
    private void processMessages() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message message = incomingMessages.take();
                deliverMessage(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    private void deliverMessage(Message message) {
        ChatRoom room = chatRooms.get(message.getChatId());
        
        // Store message in chat room
        room.getMessages().put(message.getSentAt(), message);
        
        // Deliver to all participants except sender
        for (String participantId : room.getParticipants()) {
            if (!participantId.equals(message.getSenderId())) {
                deliverToUser(participantId, message);
            }
        }
    }
    
    private void deliverToUser(String userId, Message message) {
        BlockingQueue<Message> userQueue = userQueues.get(userId);
        
        if (userQueue != null) {
            // User is online - deliver to their queue
            userQueue.offer(message);
            
            // Update delivery status
            message.setDeliveredAt(LocalDateTime.now());
            message.setStatus(MessageStatus.DELIVERED);
            
            // Create delivery receipt
            createDeliveryReceipt(message.getMessageId(), userId, DeliveryStatus.DELIVERED);
        } else {
            // User is offline - message will be delivered when they connect
            // Store pending messages in persistent storage
        }
    }
    
    // Called when user connects
    public BlockingQueue<Message> connectUser(String userId) {
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
        userQueues.put(userId, queue);
        
        // Deliver any pending messages
        deliverPendingMessages(userId);
        
        return queue;
    }
    
    public void disconnectUser(String userId) {
        userQueues.remove(userId);
    }
}
```

### Feature 2: Online Presence System

```java
public class PresenceService {
    private final ConcurrentHashMap<String, UserPresence> onlineUsers = 
        new ConcurrentHashMap<>();
    private final Map<String, Set<String>> subscribers = new ConcurrentHashMap<>();
    private final ScheduledExecutorService heartbeatChecker = 
        Executors.newScheduledThreadPool(1);
    
    private static final Duration HEARTBEAT_TIMEOUT = Duration.ofSeconds(30);
    
    public void initialize() {
        // Check for stale connections every 10 seconds
        heartbeatChecker.scheduleAtFixedRate(
            this::checkHeartbeats,
            10, 10, TimeUnit.SECONDS
        );
    }
    
    public void userConnected(String userId) {
        UserPresence presence = new UserPresence();
        presence.setUserId(userId);
        presence.setStatus(UserStatus.ONLINE);
        presence.setLastHeartbeat(LocalDateTime.now());
        
        onlineUsers.put(userId, presence);
        
        // Notify subscribers
        notifyPresenceChange(userId, UserStatus.ONLINE);
    }
    
    public void userDisconnected(String userId) {
        UserPresence presence = onlineUsers.remove(userId);
        if (presence != null) {
            presence.setStatus(UserStatus.OFFLINE);
            notifyPresenceChange(userId, UserStatus.OFFLINE);
        }
    }
    
    public void heartbeat(String userId) {
        UserPresence presence = onlineUsers.get(userId);
        if (presence != null) {
            presence.setLastHeartbeat(LocalDateTime.now());
        }
    }
    
    private void checkHeartbeats() {
        LocalDateTime threshold = LocalDateTime.now().minus(HEARTBEAT_TIMEOUT);
        
        onlineUsers.entrySet().removeIf(entry -> {
            if (entry.getValue().getLastHeartbeat().isBefore(threshold)) {
                // User timed out
                notifyPresenceChange(entry.getKey(), UserStatus.OFFLINE);
                return true;
            }
            return false;
        });
    }
    
    public void subscribeToPresence(String subscriberId, String targetUserId) {
        subscribers.computeIfAbsent(targetUserId, k -> ConcurrentHashMap.newKeySet())
                  .add(subscriberId);
    }
    
    private void notifyPresenceChange(String userId, UserStatus status) {
        Set<String> subs = subscribers.get(userId);
        if (subs != null) {
            for (String subscriberId : subs) {
                // Send presence update to subscriber
                sendPresenceNotification(subscriberId, userId, status);
            }
        }
    }
    
    public Map<String, UserStatus> getPresenceForUsers(Set<String> userIds) {
        Map<String, UserStatus> result = new HashMap<>();
        for (String userId : userIds) {
            UserPresence presence = onlineUsers.get(userId);
            result.put(userId, presence != null ? presence.getStatus() : UserStatus.OFFLINE);
        }
        return result;
    }
}
```

### Feature 3: Message History with LRU Cache

```java
public class MessageHistoryService {
    // LRU Cache for recent messages - keeps last N accessed chat rooms in memory
    private final LinkedHashMap<String, List<Message>> recentMessagesCache;
    private static final int CACHE_SIZE = 100;
    private static final int MESSAGES_PER_ROOM = 50;
    
    public MessageHistoryService() {
        // Create LRU cache
        this.recentMessagesCache = new LinkedHashMap<>(CACHE_SIZE, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, List<Message>> eldest) {
                return size() > CACHE_SIZE;
            }
        };
    }
    
    public List<Message> getRecentMessages(String chatId, int limit) {
        // Check cache first
        synchronized (recentMessagesCache) {
            List<Message> cached = recentMessagesCache.get(chatId);
            if (cached != null) {
                return cached.stream()
                    .limit(limit)
                    .collect(Collectors.toList());
            }
        }
        
        // Load from storage
        ChatRoom room = chatRoomService.getChatRoom(chatId);
        List<Message> messages = new ArrayList<>(
            room.getMessages().descendingMap().values()
        ).stream()
            .limit(MESSAGES_PER_ROOM)
            .collect(Collectors.toList());
        
        // Update cache
        synchronized (recentMessagesCache) {
            recentMessagesCache.put(chatId, messages);
        }
        
        return messages.stream().limit(limit).collect(Collectors.toList());
    }
    
    // Paginated message history
    public List<Message> getMessageHistory(String chatId, LocalDateTime before, int limit) {
        ChatRoom room = chatRoomService.getChatRoom(chatId);
        TreeMap<LocalDateTime, Message> messages = room.getMessages();
        
        // Get messages before the given timestamp
        return messages.headMap(before, false)
            .descendingMap()
            .values()
            .stream()
            .limit(limit)
            .collect(Collectors.toList());
    }
    
    // Search messages
    public List<Message> searchMessages(String chatId, String query) {
        ChatRoom room = chatRoomService.getChatRoom(chatId);
        String queryLower = query.toLowerCase();
        
        return room.getMessages().values().stream()
            .filter(m -> m.getContent().toLowerCase().contains(queryLower))
            .sorted(Comparator.comparing(Message::getSentAt).reversed())
            .limit(50)
            .collect(Collectors.toList());
    }
    
    public void cacheMessage(String chatId, Message message) {
        synchronized (recentMessagesCache) {
            List<Message> cached = recentMessagesCache.get(chatId);
            if (cached != null) {
                cached.add(0, message);
                // Trim to max size
                if (cached.size() > MESSAGES_PER_ROOM) {
                    cached.remove(cached.size() - 1);
                }
            }
        }
    }
}
```

## 🚨 Edge Cases & Handling

| Edge Case | Scenario | Solution |
|-----------|----------|----------|
| **Message to offline user** | Recipient not connected | Store in pending queue, deliver on connect |
| **User blocks sender** | Blocked user sends message | Filter at delivery, don't notify blocked |
| **Large group message** | Message to 1000+ members | Batch delivery, async processing |
| **Duplicate message** | Network retry | Idempotency key based on messageId |
| **Out-of-order messages** | Messages arrive out of sequence | Sort by timestamp at display |
| **Connection drop mid-send** | User disconnects during send | Acknowledge receipt, retry mechanism |
| **Read receipt for offline** | Mark read while offline | Sync on reconnect |
| **Group participant removed** | User removed from group | Stop delivering new messages |
| **Concurrent read/write** | Multiple messages simultaneously | Thread-safe collections, proper locking |
| **Memory pressure** | Too many messages cached | LRU eviction, pagination |

## 📏 Constraints

```java
public class ChatConstraints {
    // Message limits
    public static final int MAX_MESSAGE_LENGTH = 4000;
    public static final int MAX_MESSAGES_PER_SECOND = 10;
    public static final int MESSAGE_HISTORY_DAYS = 365;
    
    // Group limits
    public static final int MAX_GROUP_SIZE = 500;
    public static final int MAX_GROUPS_PER_USER = 100;
    public static final int MAX_GROUP_NAME_LENGTH = 100;
    
    // Connection limits
    public static final Duration HEARTBEAT_INTERVAL = Duration.ofSeconds(10);
    public static final Duration CONNECTION_TIMEOUT = Duration.ofSeconds(30);
    public static final int MAX_CONCURRENT_CONNECTIONS = 5;
    
    // Cache limits
    public static final int MESSAGE_CACHE_SIZE = 100;
    public static final int MESSAGES_PER_CACHE_ENTRY = 50;
    
    // Rate limiting
    public static final int MAX_MESSAGES_PER_MINUTE = 60;
    public static final int MAX_NEW_GROUPS_PER_DAY = 10;
}
```

## 📁 Project Structure

```
chat-application/
├── src/
│   ├── model/
│   │   ├── User.java
│   │   ├── Message.java
│   │   ├── ChatRoom.java
│   │   ├── DeliveryReceipt.java
│   │   ├── UserPresence.java
│   │   └── enums/
│   ├── service/
│   │   ├── UserService.java
│   │   ├── MessageService.java
│   │   ├── ChatRoomService.java
│   │   ├── PresenceService.java
│   │   ├── DeliveryService.java
│   │   └── MessageHistoryService.java
│   ├── queue/
│   │   └── MessageBroker.java
│   ├── cache/
│   │   └── LRUMessageCache.java
│   ├── exception/
│   └── util/
└── test/
```

---

# 📊 Project Comparison Matrix

| Feature | Library | E-Commerce | Banking | Task Scheduler | Chat |
|---------|---------|------------|---------|----------------|------|
| **Primary DS** | HashMap, TreeSet | PriorityQueue, LinkedHashMap | ConcurrentHashMap | PriorityQueue, TreeMap | BlockingQueue, TreeMap |
| **Thread Safety** | Medium | High | Critical | Low | High |
| **Concurrency** | Locks | CAS operations | ReentrantLock | Scheduled tasks | BlockingQueue |
| **Search** | TreeSet prefix | Stream filters | Date range | Multi-criteria | Full-text |
| **Sorting** | Multiple comparators | Priority-based | Chronological | Priority + deadline | Timestamp |
| **Caching** | None | Price lock | None | None | LRU Cache |
| **Complexity** | Medium | High | High | Medium | High |

---

# 🎓 Learning Progression

**Start with:** Library Management (foundational)
**Then:** Task Scheduler (scheduling concepts)
**Then:** E-Commerce (complex business logic)
**Then:** Banking (concurrency critical)
**Finally:** Chat Application (real-time, async)

Each project builds on previous concepts while introducing new challenges!

---

**Happy Coding! 🚀**

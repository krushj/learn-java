package advanced.patterns.creational;

/**
 * ============================================================================
 * BUILDER PATTERN
 * ============================================================================
 * 
 * PURPOSE: Constructs complex objects step by step. Separates
 * construction from representation.
 * 
 * WHEN TO USE:
 * - Object has many optional parameters
 * - Object creation is complex
 * - Want immutable objects
 * - Telescoping constructor problem
 * 
 * PROS:
 * - More readable code
 * - Can create immutable objects
 * - Different representations of object
 * - Step-by-step construction
 * 
 * CONS:
 * - More code (builder class needed)
 * - Increased complexity for simple objects
 * 
 * VARIATIONS:
 * 1. Static Inner Builder (most common)
 * 2. Separate Builder Class
 * 3. Director with Builder
 */

// ============================================================================
// EXAMPLE 1: Computer Builder (Static Inner Builder)
// ============================================================================

/**
 * Complex object with many parameters
 */
class Computer {
    // Required parameters
    private final String cpu;
    private final String ram;
    
    // Optional parameters
    private final String storage;
    private final String gpu;
    private final String motherboard;
    private final String powerSupply;
    private final String coolingSystem;
    private final boolean wifi;
    private final boolean bluetooth;
    private final String caseType;
    
    /**
     * Private constructor - only accessible by Builder
     */
    private Computer(ComputerBuilder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
        this.motherboard = builder.motherboard;
        this.powerSupply = builder.powerSupply;
        this.coolingSystem = builder.coolingSystem;
        this.wifi = builder.wifi;
        this.bluetooth = builder.bluetooth;
        this.caseType = builder.caseType;
    }
    
    // Getters only - object is immutable
    public String getCpu() { return cpu; }
    public String getRam() { return ram; }
    public String getStorage() { return storage; }
    public String getGpu() { return gpu; }
    public String getMotherboard() { return motherboard; }
    public String getPowerSupply() { return powerSupply; }
    public String getCoolingSystem() { return coolingSystem; }
    public boolean hasWifi() { return wifi; }
    public boolean hasBluetooth() { return bluetooth; }
    public String getCaseType() { return caseType; }
    
    @Override
    public String toString() {
        return "Computer Configuration:\n" +
               "  CPU: " + cpu + "\n" +
               "  RAM: " + ram + "\n" +
               "  Storage: " + storage + "\n" +
               "  GPU: " + gpu + "\n" +
               "  Motherboard: " + motherboard + "\n" +
               "  Power Supply: " + powerSupply + "\n" +
               "  Cooling: " + coolingSystem + "\n" +
               "  WiFi: " + wifi + "\n" +
               "  Bluetooth: " + bluetooth + "\n" +
               "  Case: " + caseType;
    }
    
    /**
     * Static nested Builder class
     */
    public static class ComputerBuilder {
        // Required parameters
        private final String cpu;
        private final String ram;
        
        // Optional parameters - initialized to default values
        private String storage = "256GB SSD";
        private String gpu = "Integrated Graphics";
        private String motherboard = "Standard ATX";
        private String powerSupply = "500W";
        private String coolingSystem = "Air Cooling";
        private boolean wifi = false;
        private boolean bluetooth = false;
        private String caseType = "Mid Tower";
        
        /**
         * Constructor with required parameters
         */
        public ComputerBuilder(String cpu, String ram) {
            this.cpu = cpu;
            this.ram = ram;
        }
        
        /**
         * Builder methods return 'this' for method chaining
         */
        public ComputerBuilder storage(String storage) {
            this.storage = storage;
            return this;
        }
        
        public ComputerBuilder gpu(String gpu) {
            this.gpu = gpu;
            return this;
        }
        
        public ComputerBuilder motherboard(String motherboard) {
            this.motherboard = motherboard;
            return this;
        }
        
        public ComputerBuilder powerSupply(String powerSupply) {
            this.powerSupply = powerSupply;
            return this;
        }
        
        public ComputerBuilder coolingSystem(String coolingSystem) {
            this.coolingSystem = coolingSystem;
            return this;
        }
        
        public ComputerBuilder wifi(boolean wifi) {
            this.wifi = wifi;
            return this;
        }
        
        public ComputerBuilder bluetooth(boolean bluetooth) {
            this.bluetooth = bluetooth;
            return this;
        }
        
        public ComputerBuilder caseType(String caseType) {
            this.caseType = caseType;
            return this;
        }
        
        /**
         * Build method creates and returns Computer instance
         */
        public Computer build() {
            // Validation
            if (cpu == null || cpu.isEmpty()) {
                throw new IllegalStateException("CPU is required");
            }
            if (ram == null || ram.isEmpty()) {
                throw new IllegalStateException("RAM is required");
            }
            
            return new Computer(this);
        }
    }
}

/**
 * Director class - knows how to build specific configurations
 */
class ComputerDirector {
    
    public Computer buildGamingComputer() {
        return new Computer.ComputerBuilder("Intel Core i9-13900K", "32GB DDR5")
                .storage("2TB NVMe SSD")
                .gpu("NVIDIA RTX 4090")
                .motherboard("ASUS ROG Maximus")
                .powerSupply("1000W Platinum")
                .coolingSystem("360mm AIO Liquid Cooling")
                .wifi(true)
                .bluetooth(true)
                .caseType("Full Tower RGB")
                .build();
    }
    
    public Computer buildOfficeComputer() {
        return new Computer.ComputerBuilder("Intel Core i5-13400", "16GB DDR4")
                .storage("512GB SSD")
                .wifi(true)
                .caseType("Small Form Factor")
                .build();
    }
    
    public Computer buildWorkstationComputer() {
        return new Computer.ComputerBuilder("AMD Ryzen 9 7950X", "64GB DDR5")
                .storage("4TB NVMe SSD")
                .gpu("NVIDIA RTX 4080")
                .motherboard("ASUS ProArt Creator")
                .powerSupply("850W Gold")
                .coolingSystem("280mm AIO Liquid Cooling")
                .wifi(true)
                .bluetooth(true)
                .caseType("Professional Tower")
                .build();
    }
    
    public Computer buildBudgetComputer() {
        return new Computer.ComputerBuilder("Intel Core i3-12100", "8GB DDR4")
                .storage("256GB SSD")
                .caseType("Compact Mini Tower")
                .build();
    }
}

// ============================================================================
// EXAMPLE 2: HTTP Request Builder
// ============================================================================

class HttpRequest {
    private final String url;
    private final String method;
    private final java.util.Map<String, String> headers;
    private final String body;
    private final int timeout;
    private final boolean followRedirects;
    
    private HttpRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = java.util.Collections.unmodifiableMap(builder.headers);
        this.body = builder.body;
        this.timeout = builder.timeout;
        this.followRedirects = builder.followRedirects;
    }
    
    public void execute() {
        System.out.println("Executing HTTP Request:");
        System.out.println("  " + method + " " + url);
        System.out.println("  Timeout: " + timeout + "ms");
        System.out.println("  Follow Redirects: " + followRedirects);
        if (!headers.isEmpty()) {
            System.out.println("  Headers: " + headers);
        }
        if (body != null) {
            System.out.println("  Body: " + body);
        }
    }
    
    // Getters
    public String getUrl() { return url; }
    public String getMethod() { return method; }
    
    public static class Builder {
        private final String url;
        private String method = "GET";
        private java.util.Map<String, String> headers = new java.util.HashMap<>();
        private String body;
        private int timeout = 30000;
        private boolean followRedirects = true;
        
        public Builder(String url) {
            this.url = url;
        }
        
        public Builder method(String method) {
            this.method = method;
            return this;
        }
        
        public Builder get() {
            this.method = "GET";
            return this;
        }
        
        public Builder post() {
            this.method = "POST";
            return this;
        }
        
        public Builder put() {
            this.method = "PUT";
            return this;
        }
        
        public Builder delete() {
            this.method = "DELETE";
            return this;
        }
        
        public Builder header(String name, String value) {
            this.headers.put(name, value);
            return this;
        }
        
        public Builder contentType(String contentType) {
            return header("Content-Type", contentType);
        }
        
        public Builder authorization(String token) {
            return header("Authorization", "Bearer " + token);
        }
        
        public Builder body(String body) {
            this.body = body;
            return this;
        }
        
        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }
        
        public Builder followRedirects(boolean follow) {
            this.followRedirects = follow;
            return this;
        }
        
        public HttpRequest build() {
            if (url == null || url.isEmpty()) {
                throw new IllegalStateException("URL is required");
            }
            return new HttpRequest(this);
        }
    }
}

// ============================================================================
// EXAMPLE 3: Pizza Builder (Classic Example)
// ============================================================================

class Pizza {
    private final String size;
    private final String crust;
    private final String sauce;
    private final java.util.List<String> toppings;
    private final boolean extraCheese;
    
    private Pizza(PizzaBuilder builder) {
        this.size = builder.size;
        this.crust = builder.crust;
        this.sauce = builder.sauce;
        this.toppings = java.util.Collections.unmodifiableList(builder.toppings);
        this.extraCheese = builder.extraCheese;
    }
    
    @Override
    public String toString() {
        return "Pizza Order:\n" +
               "  Size: " + size + "\n" +
               "  Crust: " + crust + "\n" +
               "  Sauce: " + sauce + "\n" +
               "  Toppings: " + toppings + "\n" +
               "  Extra Cheese: " + extraCheese;
    }
    
    public static class PizzaBuilder {
        private String size = "Medium";
        private String crust = "Regular";
        private String sauce = "Tomato";
        private java.util.List<String> toppings = new java.util.ArrayList<>();
        private boolean extraCheese = false;
        
        public PizzaBuilder size(String size) {
            this.size = size;
            return this;
        }
        
        public PizzaBuilder small() { return size("Small"); }
        public PizzaBuilder medium() { return size("Medium"); }
        public PizzaBuilder large() { return size("Large"); }
        public PizzaBuilder extraLarge() { return size("Extra Large"); }
        
        public PizzaBuilder crust(String crust) {
            this.crust = crust;
            return this;
        }
        
        public PizzaBuilder thinCrust() { return crust("Thin"); }
        public PizzaBuilder thickCrust() { return crust("Thick"); }
        public PizzaBuilder stuffedCrust() { return crust("Stuffed"); }
        
        public PizzaBuilder sauce(String sauce) {
            this.sauce = sauce;
            return this;
        }
        
        public PizzaBuilder addTopping(String topping) {
            this.toppings.add(topping);
            return this;
        }
        
        public PizzaBuilder pepperoni() { return addTopping("Pepperoni"); }
        public PizzaBuilder mushrooms() { return addTopping("Mushrooms"); }
        public PizzaBuilder onions() { return addTopping("Onions"); }
        public PizzaBuilder peppers() { return addTopping("Peppers"); }
        public PizzaBuilder olives() { return addTopping("Olives"); }
        public PizzaBuilder sausage() { return addTopping("Sausage"); }
        
        public PizzaBuilder extraCheese() {
            this.extraCheese = true;
            return this;
        }
        
        public Pizza build() {
            return new Pizza(this);
        }
    }
}

// ============================================================================
// USAGE DEMONSTRATION
// ============================================================================

public class BuilderDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║            BUILDER PATTERN DEMONSTRATION                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Computer Builder
        System.out.println("━━━ Computer Builder ━━━\n");
        demonstrateComputerBuilder();
        
        // Demo 2: Director Pattern
        System.out.println("\n━━━ Director Pattern ━━━\n");
        demonstrateDirector();
        
        // Demo 3: HTTP Request Builder
        System.out.println("\n━━━ HTTP Request Builder ━━━\n");
        demonstrateHttpRequestBuilder();
        
        // Demo 4: Pizza Builder
        System.out.println("\n━━━ Pizza Builder ━━━\n");
        demonstratePizzaBuilder();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║            BUILDER DEMO COMPLETED!                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void demonstrateComputerBuilder() {
        System.out.println("Building a custom gaming PC:\n");
        
        Computer gamingPC = new Computer.ComputerBuilder("AMD Ryzen 9 7900X", "32GB DDR5")
                .storage("2TB NVMe SSD")
                .gpu("NVIDIA RTX 4080")
                .powerSupply("850W")
                .coolingSystem("Liquid Cooling")
                .wifi(true)
                .bluetooth(true)
                .caseType("Full Tower")
                .build();
        
        System.out.println(gamingPC);
        
        System.out.println("\n--- Building minimal office PC ---\n");
        
        Computer officePC = new Computer.ComputerBuilder("Intel i5", "16GB DDR4")
                .storage("512GB SSD")
                .build();
        
        System.out.println(officePC);
        
        System.out.println("\n✓ Same builder, different configurations!");
        System.out.println("✓ Objects are immutable after creation!");
    }
    
    private static void demonstrateDirector() {
        ComputerDirector director = new ComputerDirector();
        
        System.out.println("Director building pre-configured computers:\n");
        
        System.out.println("=== Gaming Computer ===");
        Computer gaming = director.buildGamingComputer();
        System.out.println(gaming);
        
        System.out.println("\n=== Office Computer ===");
        Computer office = director.buildOfficeComputer();
        System.out.println(office);
        
        System.out.println("\n=== Workstation Computer ===");
        Computer workstation = director.buildWorkstationComputer();
        System.out.println(workstation);
        
        System.out.println("\n✓ Director encapsulates building recipes!");
    }
    
    private static void demonstrateHttpRequestBuilder() {
        System.out.println("Building HTTP requests:\n");
        
        // Simple GET request
        HttpRequest getRequest = new HttpRequest.Builder("https://api.example.com/users")
                .get()
                .header("Accept", "application/json")
                .timeout(5000)
                .build();
        
        getRequest.execute();
        
        System.out.println();
        
        // POST request with body
        HttpRequest postRequest = new HttpRequest.Builder("https://api.example.com/users")
                .post()
                .contentType("application/json")
                .authorization("my-secret-token")
                .body("{\"name\": \"John\", \"email\": \"john@example.com\"}")
                .timeout(10000)
                .followRedirects(false)
                .build();
        
        postRequest.execute();
        
        System.out.println("\n✓ Fluent API makes requests readable!");
    }
    
    private static void demonstratePizzaBuilder() {
        System.out.println("Building pizzas:\n");
        
        // Meat lovers pizza
        Pizza meatLovers = new Pizza.PizzaBuilder()
                .large()
                .thickCrust()
                .pepperoni()
                .sausage()
                .extraCheese()
                .build();
        
        System.out.println("=== Meat Lovers ===");
        System.out.println(meatLovers);
        
        // Vegetarian pizza
        Pizza veggie = new Pizza.PizzaBuilder()
                .medium()
                .thinCrust()
                .mushrooms()
                .onions()
                .peppers()
                .olives()
                .build();
        
        System.out.println("\n=== Vegetarian ===");
        System.out.println(veggie);
        
        // Simple cheese pizza
        Pizza cheese = new Pizza.PizzaBuilder()
                .small()
                .extraCheese()
                .build();
        
        System.out.println("\n=== Simple Cheese ===");
        System.out.println(cheese);
        
        System.out.println("\n✓ Builder pattern handles complex object construction!");
        System.out.println("✓ Method chaining provides readable API!");
    }
}

package advanced.patterns.structural;

/**
 * ============================================================================
 * DECORATOR PATTERN
 * ============================================================================
 * 
 * PURPOSE: Attaches additional responsibilities to an object dynamically.
 * Provides flexible alternative to subclassing for extending functionality.
 * 
 * WHEN TO USE:
 * - Add responsibilities to objects dynamically
 * - Responsibilities can be withdrawn
 * - Extension by subclassing is impractical
 * - Need transparent wrapping
 * 
 * COMPONENTS:
 * 1. Component - interface for objects
 * 2. ConcreteComponent - object to add functionality to
 * 3. Decorator - maintains reference to Component
 * 4. ConcreteDecorator - adds functionality
 * 
 * PROS:
 * - More flexible than inheritance
 * - Avoids feature-laden classes high up in hierarchy
 * - Responsibilities added/removed at runtime
 * - Combines several behaviors
 * 
 * CONS:
 * - Many small objects
 * - Complexity in instantiation
 * 
 * REAL-WORLD EXAMPLES:
 * - Java I/O streams (BufferedInputStream wraps FileInputStream)
 * - GUI components
 * - Coffee shop menu
 */

// ============================================================================
// EXAMPLE 1: Coffee Shop
// ============================================================================

/**
 * Component interface - defines the interface for objects
 */
interface Coffee {
    String getDescription();
    double getCost();
}

/**
 * Concrete Component - Base coffee
 */
class SimpleCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Simple Coffee";
    }
    
    @Override
    public double getCost() {
        return 2.00;
    }
}

/**
 * Another Concrete Component - Espresso
 */
class Espresso implements Coffee {
    @Override
    public String getDescription() {
        return "Espresso";
    }
    
    @Override
    public double getCost() {
        return 2.50;
    }
}

/**
 * Abstract Decorator - maintains reference to Component
 */
abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;
    
    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription();
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost();
    }
}

/**
 * Concrete Decorator - Milk
 */
class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Milk";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.50;
    }
}

/**
 * Concrete Decorator - Sugar
 */
class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Sugar";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.20;
    }
}

/**
 * Concrete Decorator - Whipped Cream
 */
class WhippedCreamDecorator extends CoffeeDecorator {
    public WhippedCreamDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Whipped Cream";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.70;
    }
}

/**
 * Concrete Decorator - Caramel
 */
class CaramelDecorator extends CoffeeDecorator {
    public CaramelDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Caramel";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.60;
    }
}

/**
 * Concrete Decorator - Vanilla
 */
class VanillaDecorator extends CoffeeDecorator {
    public VanillaDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Vanilla";
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 0.50;
    }
}

/**
 * Concrete Decorator - Size modifier
 */
class LargeSizeDecorator extends CoffeeDecorator {
    public LargeSizeDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return "Large " + decoratedCoffee.getDescription();
    }
    
    @Override
    public double getCost() {
        return decoratedCoffee.getCost() + 1.00;
    }
}

// ============================================================================
// EXAMPLE 2: Text Formatting
// ============================================================================

/**
 * Component interface
 */
interface Text {
    String getContent();
}

/**
 * Concrete Component
 */
class PlainText implements Text {
    private String content;
    
    public PlainText(String content) {
        this.content = content;
    }
    
    @Override
    public String getContent() {
        return content;
    }
}

/**
 * Abstract Decorator
 */
abstract class TextDecorator implements Text {
    protected Text decoratedText;
    
    public TextDecorator(Text text) {
        this.decoratedText = text;
    }
}

/**
 * Concrete Decorator - Bold
 */
class BoldDecorator extends TextDecorator {
    public BoldDecorator(Text text) {
        super(text);
    }
    
    @Override
    public String getContent() {
        return "<b>" + decoratedText.getContent() + "</b>";
    }
}

/**
 * Concrete Decorator - Italic
 */
class ItalicDecorator extends TextDecorator {
    public ItalicDecorator(Text text) {
        super(text);
    }
    
    @Override
    public String getContent() {
        return "<i>" + decoratedText.getContent() + "</i>";
    }
}

/**
 * Concrete Decorator - Underline
 */
class UnderlineDecorator extends TextDecorator {
    public UnderlineDecorator(Text text) {
        super(text);
    }
    
    @Override
    public String getContent() {
        return "<u>" + decoratedText.getContent() + "</u>";
    }
}

/**
 * Concrete Decorator - Color
 */
class ColorDecorator extends TextDecorator {
    private String color;
    
    public ColorDecorator(Text text, String color) {
        super(text);
        this.color = color;
    }
    
    @Override
    public String getContent() {
        return "<span style='color:" + color + "'>" + decoratedText.getContent() + "</span>";
    }
}

// ============================================================================
// EXAMPLE 3: Data Processing Pipeline
// ============================================================================

/**
 * Component interface
 */
interface DataSource {
    void writeData(String data);
    String readData();
}

/**
 * Concrete Component - File data source
 */
class FileDataSource implements DataSource {
    private String data = "";
    private String filename;
    
    public FileDataSource(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void writeData(String data) {
        System.out.println("Writing to file: " + filename);
        this.data = data;
    }
    
    @Override
    public String readData() {
        System.out.println("Reading from file: " + filename);
        return data;
    }
}

/**
 * Abstract Decorator
 */
abstract class DataSourceDecorator implements DataSource {
    protected DataSource wrappedSource;
    
    public DataSourceDecorator(DataSource source) {
        this.wrappedSource = source;
    }
    
    @Override
    public void writeData(String data) {
        wrappedSource.writeData(data);
    }
    
    @Override
    public String readData() {
        return wrappedSource.readData();
    }
}

/**
 * Concrete Decorator - Encryption
 */
class EncryptionDecorator extends DataSourceDecorator {
    public EncryptionDecorator(DataSource source) {
        super(source);
    }
    
    @Override
    public void writeData(String data) {
        System.out.println("Encrypting data...");
        String encrypted = encrypt(data);
        super.writeData(encrypted);
    }
    
    @Override
    public String readData() {
        String encrypted = super.readData();
        System.out.println("Decrypting data...");
        return decrypt(encrypted);
    }
    
    private String encrypt(String data) {
        // Simple XOR encryption for demo
        StringBuilder sb = new StringBuilder();
        for (char c : data.toCharArray()) {
            sb.append((char) (c ^ 5));
        }
        return sb.toString();
    }
    
    private String decrypt(String data) {
        // XOR decryption (same as encryption)
        return encrypt(data);
    }
}

/**
 * Concrete Decorator - Compression
 */
class CompressionDecorator extends DataSourceDecorator {
    public CompressionDecorator(DataSource source) {
        super(source);
    }
    
    @Override
    public void writeData(String data) {
        System.out.println("Compressing data...");
        String compressed = compress(data);
        super.writeData(compressed);
    }
    
    @Override
    public String readData() {
        String compressed = super.readData();
        System.out.println("Decompressing data...");
        return decompress(compressed);
    }
    
    private String compress(String data) {
        // Simple "compression" for demo
        return "COMPRESSED[" + data.length() + "]:" + data;
    }
    
    private String decompress(String data) {
        // Simple "decompression" for demo
        int colonIndex = data.indexOf(':');
        if (colonIndex > 0 && data.startsWith("COMPRESSED")) {
            return data.substring(colonIndex + 1);
        }
        return data;
    }
}

// ============================================================================
// USAGE DEMONSTRATION
// ============================================================================

public class DecoratorDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║           DECORATOR PATTERN DEMONSTRATION                ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Coffee Shop
        System.out.println("━━━ Coffee Shop Example ━━━\n");
        demonstrateCoffeeShop();
        
        // Demo 2: Text Formatting
        System.out.println("\n━━━ Text Formatting Example ━━━\n");
        demonstrateTextFormatting();
        
        // Demo 3: Data Processing
        System.out.println("\n━━━ Data Processing Pipeline ━━━\n");
        demonstrateDataProcessing();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║          DECORATOR DEMO COMPLETED!                        ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void demonstrateCoffeeShop() {
        System.out.println("Building coffee orders dynamically:\n");
        
        // Simple coffee
        Coffee coffee1 = new SimpleCoffee();
        printOrder("Order 1", coffee1);
        
        // Coffee with milk
        Coffee coffee2 = new SimpleCoffee();
        coffee2 = new MilkDecorator(coffee2);
        printOrder("Order 2", coffee2);
        
        // Coffee with milk and sugar
        Coffee coffee3 = new SimpleCoffee();
        coffee3 = new MilkDecorator(coffee3);
        coffee3 = new SugarDecorator(coffee3);
        printOrder("Order 3", coffee3);
        
        // Fancy latte
        Coffee latte = new Espresso();
        latte = new MilkDecorator(latte);
        latte = new MilkDecorator(latte); // Double milk
        latte = new VanillaDecorator(latte);
        latte = new WhippedCreamDecorator(latte);
        latte = new LargeSizeDecorator(latte);
        printOrder("Fancy Latte", latte);
        
        // Caramel macchiato
        Coffee macchiato = new Espresso();
        macchiato = new MilkDecorator(macchiato);
        macchiato = new CaramelDecorator(macchiato);
        macchiato = new WhippedCreamDecorator(macchiato);
        printOrder("Caramel Macchiato", macchiato);
        
        System.out.println("\n✓ Decorators can be combined in any order!");
        System.out.println("✓ Same decorators can be applied multiple times!");
    }
    
    private static void printOrder(String name, Coffee coffee) {
        System.out.println(name + ":");
        System.out.println("  " + coffee.getDescription());
        System.out.println("  Price: $" + String.format("%.2f", coffee.getCost()));
        System.out.println();
    }
    
    private static void demonstrateTextFormatting() {
        System.out.println("Formatting text dynamically:\n");
        
        // Plain text
        Text text = new PlainText("Hello World");
        System.out.println("Plain: " + text.getContent());
        
        // Bold text
        text = new PlainText("Hello World");
        text = new BoldDecorator(text);
        System.out.println("Bold: " + text.getContent());
        
        // Bold and italic
        text = new PlainText("Hello World");
        text = new BoldDecorator(text);
        text = new ItalicDecorator(text);
        System.out.println("Bold+Italic: " + text.getContent());
        
        // All formatting
        text = new PlainText("Important Message");
        text = new BoldDecorator(text);
        text = new ItalicDecorator(text);
        text = new UnderlineDecorator(text);
        text = new ColorDecorator(text, "red");
        System.out.println("All formats: " + text.getContent());
        
        System.out.println("\n✓ Decorators wrap each other to add formatting!");
    }
    
    private static void demonstrateDataProcessing() {
        System.out.println("Data processing pipeline:\n");
        
        // Simple file storage
        System.out.println("--- Simple File Storage ---");
        DataSource simpleSource = new FileDataSource("data.txt");
        simpleSource.writeData("Hello World");
        System.out.println("Read: " + simpleSource.readData());
        
        // Encrypted file storage
        System.out.println("\n--- Encrypted File Storage ---");
        DataSource encryptedSource = new EncryptionDecorator(
            new FileDataSource("encrypted.txt")
        );
        encryptedSource.writeData("Secret Data");
        System.out.println("Read: " + encryptedSource.readData());
        
        // Compressed and encrypted storage
        System.out.println("\n--- Compressed + Encrypted Storage ---");
        DataSource fullSource = new CompressionDecorator(
            new EncryptionDecorator(
                new FileDataSource("full.txt")
            )
        );
        fullSource.writeData("Important confidential data");
        System.out.println("Read: " + fullSource.readData());
        
        System.out.println("\n✓ Pipeline processes data through multiple decorators!");
    }
}

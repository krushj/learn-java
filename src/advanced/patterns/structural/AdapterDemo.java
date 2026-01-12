package advanced.patterns.structural;

/**
 * ============================================================================
 * ADAPTER PATTERN
 * ============================================================================
 * 
 * PURPOSE: Converts interface of a class into another interface clients expect.
 * Lets classes work together that couldn't otherwise because of incompatible interfaces.
 * 
 * ALSO KNOWN AS: Wrapper
 * 
 * WHEN TO USE:
 * - Use existing class with incompatible interface
 * - Create reusable class that cooperates with unrelated classes
 * - Need to use several existing subclasses but impractical to adapt by subclassing
 * 
 * TYPES:
 * 1. Class Adapter (uses inheritance) - not possible in Java without multiple inheritance
 * 2. Object Adapter (uses composition) - more flexible, recommended
 * 
 * PROS:
 * - Integrates legacy code
 * - Increases reusability
 * - Single Responsibility (conversion logic separated)
 * - Open/Closed Principle
 * 
 * CONS:
 * - Increased complexity
 * - Sometimes direct class changes are simpler
 * 
 * REAL-WORLD EXAMPLES:
 * - Arrays.asList() adapts array to List
 * - InputStreamReader adapts byte stream to character stream
 * - Power adapters (110V to 220V)
 */

// ============================================================================
// EXAMPLE 1: Media Player Adapter
// ============================================================================

/**
 * Target interface - what client expects
 */
interface MediaPlayer {
    void play(String audioType, String fileName);
}

/**
 * Adaptee - existing incompatible interfaces
 */
class VlcPlayer {
    public void playVlc(String fileName) {
        System.out.println("VLC Player: Playing VLC file - " + fileName);
    }
}

class Mp4Player {
    public void playMp4(String fileName) {
        System.out.println("MP4 Player: Playing MP4 file - " + fileName);
    }
}

class AviPlayer {
    public void playAvi(String fileName) {
        System.out.println("AVI Player: Playing AVI file - " + fileName);
    }
}

/**
 * Adapter - makes incompatible players work with MediaPlayer interface
 */
class MediaAdapter implements MediaPlayer {
    private VlcPlayer vlcPlayer;
    private Mp4Player mp4Player;
    private AviPlayer aviPlayer;
    
    public MediaAdapter() {
        this.vlcPlayer = new VlcPlayer();
        this.mp4Player = new Mp4Player();
        this.aviPlayer = new AviPlayer();
    }
    
    @Override
    public void play(String audioType, String fileName) {
        switch (audioType.toLowerCase()) {
            case "vlc":
                vlcPlayer.playVlc(fileName);
                break;
            case "mp4":
                mp4Player.playMp4(fileName);
                break;
            case "avi":
                aviPlayer.playAvi(fileName);
                break;
            default:
                System.out.println("Format not supported: " + audioType);
        }
    }
}

/**
 * Client class using the adapter
 */
class AudioPlayer implements MediaPlayer {
    private MediaAdapter mediaAdapter;
    
    public AudioPlayer() {
        this.mediaAdapter = new MediaAdapter();
    }
    
    @Override
    public void play(String audioType, String fileName) {
        // Built-in support for mp3
        if (audioType.equalsIgnoreCase("mp3")) {
            System.out.println("Audio Player: Playing MP3 file - " + fileName);
        }
        // Use adapter for other formats
        else {
            mediaAdapter.play(audioType, fileName);
        }
    }
}

// ============================================================================
// EXAMPLE 2: Payment Gateway Adapter
// ============================================================================

/**
 * Target interface - modern payment interface
 */
interface PaymentProcessor {
    void processPayment(double amount, String currency);
    boolean refund(String transactionId, double amount);
    String getProcessorName();
}

/**
 * Adaptee - Legacy payment system
 */
class LegacyPaymentGateway {
    public void makePayment(int amountInCents) {
        System.out.println("Legacy Gateway: Processing " + amountInCents + " cents");
    }
    
    public void cancelTransaction(String txId, int amountInCents) {
        System.out.println("Legacy Gateway: Canceling TX " + txId + " for " + amountInCents + " cents");
    }
    
    public String getGatewayVersion() {
        return "Legacy v1.0";
    }
}

/**
 * Another Adaptee - Third-party payment API
 */
class ThirdPartyPaymentApi {
    public void charge(double amount, String curr) {
        System.out.println("ThirdParty API: Charging " + curr + " " + amount);
    }
    
    public boolean reverseCharge(String reference, double amt) {
        System.out.println("ThirdParty API: Reversing charge " + reference + " for " + amt);
        return true;
    }
}

/**
 * Adapter for Legacy Payment Gateway
 */
class LegacyPaymentAdapter implements PaymentProcessor {
    private LegacyPaymentGateway legacyGateway;
    
    public LegacyPaymentAdapter(LegacyPaymentGateway gateway) {
        this.legacyGateway = gateway;
    }
    
    @Override
    public void processPayment(double amount, String currency) {
        // Convert dollars to cents for legacy system
        int amountInCents = (int) (amount * 100);
        System.out.println("Adapter: Converting $" + amount + " to " + amountInCents + " cents");
        legacyGateway.makePayment(amountInCents);
    }
    
    @Override
    public boolean refund(String transactionId, double amount) {
        int amountInCents = (int) (amount * 100);
        legacyGateway.cancelTransaction(transactionId, amountInCents);
        return true;
    }
    
    @Override
    public String getProcessorName() {
        return "Legacy Adapter (" + legacyGateway.getGatewayVersion() + ")";
    }
}

/**
 * Adapter for Third-Party Payment API
 */
class ThirdPartyPaymentAdapter implements PaymentProcessor {
    private ThirdPartyPaymentApi thirdPartyApi;
    
    public ThirdPartyPaymentAdapter(ThirdPartyPaymentApi api) {
        this.thirdPartyApi = api;
    }
    
    @Override
    public void processPayment(double amount, String currency) {
        thirdPartyApi.charge(amount, currency);
    }
    
    @Override
    public boolean refund(String transactionId, double amount) {
        return thirdPartyApi.reverseCharge(transactionId, amount);
    }
    
    @Override
    public String getProcessorName() {
        return "ThirdParty Adapter";
    }
}

/**
 * Client that uses PaymentProcessor interface
 */
class PaymentService {
    private PaymentProcessor processor;
    
    public PaymentService(PaymentProcessor processor) {
        this.processor = processor;
    }
    
    public void setProcessor(PaymentProcessor processor) {
        this.processor = processor;
    }
    
    public void checkout(double amount, String currency) {
        System.out.println("\nProcessing checkout with " + processor.getProcessorName());
        processor.processPayment(amount, currency);
        System.out.println("Checkout complete!");
    }
    
    public void processRefund(String txId, double amount) {
        System.out.println("\nProcessing refund with " + processor.getProcessorName());
        boolean success = processor.refund(txId, amount);
        System.out.println("Refund " + (success ? "successful" : "failed"));
    }
}

// ============================================================================
// EXAMPLE 3: Temperature Sensor Adapter
// ============================================================================

/**
 * Target interface - expects Celsius
 */
interface TemperatureSensor {
    double getTemperatureCelsius();
    String getSensorName();
}

/**
 * Adaptee - Fahrenheit sensor
 */
class FahrenheitSensor {
    private String name;
    private double temperatureF;
    
    public FahrenheitSensor(String name, double tempF) {
        this.name = name;
        this.temperatureF = tempF;
    }
    
    public double getTemperatureFahrenheit() {
        return temperatureF;
    }
    
    public void setTemperatureFahrenheit(double temp) {
        this.temperatureF = temp;
    }
    
    public String getName() {
        return name;
    }
}

/**
 * Adaptee - Kelvin sensor
 */
class KelvinSensor {
    private String id;
    private double temperatureK;
    
    public KelvinSensor(String id, double tempK) {
        this.id = id;
        this.temperatureK = tempK;
    }
    
    public double readKelvin() {
        return temperatureK;
    }
    
    public String getSensorId() {
        return id;
    }
}

/**
 * Adapter - Fahrenheit to Celsius
 */
class FahrenheitAdapter implements TemperatureSensor {
    private FahrenheitSensor sensor;
    
    public FahrenheitAdapter(FahrenheitSensor sensor) {
        this.sensor = sensor;
    }
    
    @Override
    public double getTemperatureCelsius() {
        // Convert F to C: (F - 32) * 5/9
        double fahrenheit = sensor.getTemperatureFahrenheit();
        return (fahrenheit - 32) * 5.0 / 9.0;
    }
    
    @Override
    public String getSensorName() {
        return sensor.getName() + " (F→C Adapter)";
    }
}

/**
 * Adapter - Kelvin to Celsius
 */
class KelvinAdapter implements TemperatureSensor {
    private KelvinSensor sensor;
    
    public KelvinAdapter(KelvinSensor sensor) {
        this.sensor = sensor;
    }
    
    @Override
    public double getTemperatureCelsius() {
        // Convert K to C: K - 273.15
        return sensor.readKelvin() - 273.15;
    }
    
    @Override
    public String getSensorName() {
        return sensor.getSensorId() + " (K→C Adapter)";
    }
}

/**
 * Temperature monitoring system that uses Celsius
 */
class TemperatureMonitor {
    private java.util.List<TemperatureSensor> sensors = new java.util.ArrayList<>();
    
    public void addSensor(TemperatureSensor sensor) {
        sensors.add(sensor);
    }
    
    public void displayAllTemperatures() {
        System.out.println("Temperature Readings (Celsius):");
        for (TemperatureSensor sensor : sensors) {
            System.out.printf("  %s: %.2f°C%n", 
                sensor.getSensorName(), 
                sensor.getTemperatureCelsius());
        }
    }
    
    public double getAverageTemperature() {
        double sum = 0;
        for (TemperatureSensor sensor : sensors) {
            sum += sensor.getTemperatureCelsius();
        }
        return sum / sensors.size();
    }
}

// ============================================================================
// USAGE DEMONSTRATION
// ============================================================================

public class AdapterDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║            ADAPTER PATTERN DEMONSTRATION                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Media Player
        System.out.println("━━━ Media Player Adapter ━━━\n");
        demonstrateMediaPlayer();
        
        // Demo 2: Payment Gateway
        System.out.println("\n━━━ Payment Gateway Adapter ━━━\n");
        demonstratePaymentGateway();
        
        // Demo 3: Temperature Sensor
        System.out.println("\n━━━ Temperature Sensor Adapter ━━━\n");
        demonstrateTemperatureSensor();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║           ADAPTER DEMO COMPLETED!                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void demonstrateMediaPlayer() {
        AudioPlayer audioPlayer = new AudioPlayer();
        
        System.out.println("Playing various media formats:\n");
        
        audioPlayer.play("mp3", "song.mp3");
        audioPlayer.play("mp4", "video.mp4");
        audioPlayer.play("vlc", "movie.vlc");
        audioPlayer.play("avi", "documentary.avi");
        audioPlayer.play("wmv", "file.wmv");
        
        System.out.println("\n✓ Adapter allows playing incompatible formats!");
    }
    
    private static void demonstratePaymentGateway() {
        // Create legacy gateway and wrap with adapter
        LegacyPaymentGateway legacyGateway = new LegacyPaymentGateway();
        PaymentProcessor legacyAdapter = new LegacyPaymentAdapter(legacyGateway);
        
        // Create third-party API and wrap with adapter
        ThirdPartyPaymentApi thirdPartyApi = new ThirdPartyPaymentApi();
        PaymentProcessor thirdPartyAdapter = new ThirdPartyPaymentAdapter(thirdPartyApi);
        
        // Use payment service with different processors
        PaymentService service = new PaymentService(legacyAdapter);
        
        System.out.println("=== Using Legacy Payment Gateway ===");
        service.checkout(99.99, "USD");
        service.processRefund("TX-12345", 29.99);
        
        // Switch to third-party processor
        service.setProcessor(thirdPartyAdapter);
        
        System.out.println("\n=== Using Third-Party Payment API ===");
        service.checkout(149.99, "USD");
        service.processRefund("TX-67890", 49.99);
        
        System.out.println("\n✓ Same client code works with different payment systems!");
    }
    
    private static void demonstrateTemperatureSensor() {
        TemperatureMonitor monitor = new TemperatureMonitor();
        
        // Create native Celsius sensor
        TemperatureSensor celsiusSensor = new TemperatureSensor() {
            @Override
            public double getTemperatureCelsius() { return 25.0; }
            @Override
            public String getSensorName() { return "Celsius Sensor"; }
        };
        
        // Create Fahrenheit sensor with adapter
        FahrenheitSensor fSensor = new FahrenheitSensor("Room-F", 77.0); // 77°F = 25°C
        TemperatureSensor fahrenheitAdapted = new FahrenheitAdapter(fSensor);
        
        // Create Kelvin sensor with adapter
        KelvinSensor kSensor = new KelvinSensor("Lab-K", 298.15); // 298.15K = 25°C
        TemperatureSensor kelvinAdapted = new KelvinAdapter(kSensor);
        
        // Add all sensors to monitor
        monitor.addSensor(celsiusSensor);
        monitor.addSensor(fahrenheitAdapted);
        monitor.addSensor(kelvinAdapted);
        
        // Display temperatures
        monitor.displayAllTemperatures();
        
        System.out.printf("\nAverage Temperature: %.2f°C%n", monitor.getAverageTemperature());
        
        System.out.println("\n✓ Different sensor types unified through adapters!");
    }
}

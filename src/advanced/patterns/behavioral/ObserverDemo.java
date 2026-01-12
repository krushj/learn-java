package advanced.patterns.behavioral;

import java.util.*;

/**
 * ============================================================================
 * OBSERVER PATTERN
 * ============================================================================
 * 
 * PURPOSE: Defines one-to-many dependency between objects so that when
 * one object changes state, all dependents are notified automatically.
 * 
 * ALSO KNOWN AS: Publish-Subscribe pattern, Event-Subscriber
 * 
 * WHEN TO USE:
 * - Event handling systems
 * - MVC architecture (Model notifies Views)
 * - Real-time data updates
 * - Notification systems
 * 
 * COMPONENTS:
 * 1. Subject (Publisher) - maintains list of observers, notifies them
 * 2. Observer (Subscriber) - interface for receiving updates
 * 3. ConcreteSubject - stores state of interest
 * 4. ConcreteObserver - implements Observer interface
 * 
 * PROS:
 * - Loose coupling between subject and observers
 * - Dynamic relationships (add/remove at runtime)
 * - Broadcast communication
 * - Supports Open/Closed Principle
 * 
 * CONS:
 * - Memory leaks if observers not removed
 * - Unpredictable update order
 * - Can cause performance issues with many observers
 * - Cascading updates can be complex
 * 
 * REAL-WORLD EXAMPLES:
 * - Java Swing event listeners
 * - JavaScript DOM events
 * - Message queues (Kafka, RabbitMQ)
 * - Social media notifications
 */

// ============================================================================
// EXAMPLE 1: News Agency
// ============================================================================

/**
 * Observer interface
 */
interface Observer {
    void update(String news);
}

/**
 * Subject interface
 */
interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}

/**
 * Concrete Subject - News Agency
 */
class NewsAgency implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String latestNews;
    
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
        System.out.println("NewsAgency: Observer attached. Total: " + observers.size());
    }
    
    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
        System.out.println("NewsAgency: Observer detached. Total: " + observers.size());
    }
    
    @Override
    public void notifyObservers() {
        System.out.println("NewsAgency: Notifying " + observers.size() + " observers\n");
        for (Observer observer : observers) {
            observer.update(latestNews);
        }
    }
    
    public void setNews(String news) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║          BREAKING NEWS                 ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println(news + "\n");
        
        this.latestNews = news;
        notifyObservers();
    }
    
    public String getLatestNews() {
        return latestNews;
    }
}

/**
 * Concrete Observer - News Channel
 */
class NewsChannel implements Observer {
    private String channelName;
    
    public NewsChannel(String name) {
        this.channelName = name;
    }
    
    @Override
    public void update(String news) {
        System.out.println(channelName + ":");
        System.out.println("  Received: " + news);
        broadcast(news);
    }
    
    private void broadcast(String news) {
        System.out.println("  Broadcasting live on " + channelName + "...\n");
    }
}

/**
 * Concrete Observer - Mobile App
 */
class NewsApp implements Observer {
    private String appName;
    private List<String> newsHistory = new ArrayList<>();
    
    public NewsApp(String name) {
        this.appName = name;
    }
    
    @Override
    public void update(String news) {
        newsHistory.add(news);
        System.out.println(appName + " App:");
        System.out.println("  Push notification sent!");
        System.out.println("  News stored (total: " + newsHistory.size() + ")\n");
    }
}

/**
 * Concrete Observer - Email Newsletter
 */
class EmailNewsletter implements Observer {
    private String listName;
    private int subscriberCount;
    
    public EmailNewsletter(String name, int subscribers) {
        this.listName = name;
        this.subscriberCount = subscribers;
    }
    
    @Override
    public void update(String news) {
        System.out.println(listName + " Newsletter:");
        System.out.println("  Sending to " + subscriberCount + " subscribers...\n");
    }
}

// ============================================================================
// EXAMPLE 2: Stock Market
// ============================================================================

/**
 * Subject - Stock
 */
class Stock implements Subject {
    private List<Observer> investors = new ArrayList<>();
    private String symbol;
    private double price;
    private double previousPrice;
    
    public Stock(String symbol, double initialPrice) {
        this.symbol = symbol;
        this.price = initialPrice;
        this.previousPrice = initialPrice;
    }
    
    @Override
    public void attach(Observer observer) {
        investors.add(observer);
    }
    
    @Override
    public void detach(Observer observer) {
        investors.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        double change = price - previousPrice;
        double percentChange = (change / previousPrice) * 100;
        String direction = change >= 0 ? "↑" : "↓";
        
        String message = String.format("%s: $%.2f %s %.2f (%.2f%%)", 
            symbol, price, direction, Math.abs(change), Math.abs(percentChange));
        
        for (Observer investor : investors) {
            investor.update(message);
        }
    }
    
    public void setPrice(double newPrice) {
        System.out.println("\n--- Stock Price Update ---");
        this.previousPrice = this.price;
        this.price = newPrice;
        notifyObservers();
    }
    
    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }
}

/**
 * Observer - Investor
 */
class Investor implements Observer {
    private String name;
    private Map<String, Integer> portfolio = new HashMap<>();
    
    public Investor(String name) {
        this.name = name;
    }
    
    public void addToPortfolio(String symbol, int shares) {
        portfolio.put(symbol, shares);
    }
    
    @Override
    public void update(String message) {
        System.out.println("Investor " + name + " notified: " + message);
        analyzeAndAct(message);
    }
    
    private void analyzeAndAct(String message) {
        if (message.contains("↓")) {
            System.out.println("  " + name + " thinking: Maybe time to buy?");
        } else {
            System.out.println("  " + name + " thinking: Looking good!");
        }
    }
}

/**
 * Observer - Trading Bot
 */
class TradingBot implements Observer {
    private String botId;
    private double buyThreshold;
    private double sellThreshold;
    
    public TradingBot(String id, double buyThreshold, double sellThreshold) {
        this.botId = id;
        this.buyThreshold = buyThreshold;
        this.sellThreshold = sellThreshold;
    }
    
    @Override
    public void update(String message) {
        System.out.println("TradingBot " + botId + " analyzing: " + message);
        // Auto-trading logic would go here
    }
}

// ============================================================================
// EXAMPLE 3: Weather Station
// ============================================================================

/**
 * Weather data class
 */
class WeatherData {
    public double temperature;
    public double humidity;
    public double pressure;
    
    public WeatherData(double temp, double humidity, double pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        this.pressure = pressure;
    }
}

/**
 * Weather observer interface with specific data
 */
interface WeatherObserver {
    void update(WeatherData data);
}

/**
 * Weather station subject
 */
class WeatherStation {
    private List<WeatherObserver> observers = new ArrayList<>();
    private WeatherData currentData;
    
    public void addObserver(WeatherObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }
    
    public void setWeatherData(double temp, double humidity, double pressure) {
        System.out.println("\n--- Weather Station Update ---");
        this.currentData = new WeatherData(temp, humidity, pressure);
        notifyObservers();
    }
    
    private void notifyObservers() {
        for (WeatherObserver observer : observers) {
            observer.update(currentData);
        }
    }
}

/**
 * Current conditions display
 */
class CurrentConditionsDisplay implements WeatherObserver {
    @Override
    public void update(WeatherData data) {
        System.out.println("Current Conditions Display:");
        System.out.printf("  Temperature: %.1f°C%n", data.temperature);
        System.out.printf("  Humidity: %.1f%%%n", data.humidity);
        System.out.printf("  Pressure: %.1f hPa%n", data.pressure);
    }
}

/**
 * Statistics display
 */
class StatisticsDisplay implements WeatherObserver {
    private List<Double> temperatures = new ArrayList<>();
    
    @Override
    public void update(WeatherData data) {
        temperatures.add(data.temperature);
        
        double avg = temperatures.stream().mapToDouble(d -> d).average().orElse(0);
        double max = temperatures.stream().mapToDouble(d -> d).max().orElse(0);
        double min = temperatures.stream().mapToDouble(d -> d).min().orElse(0);
        
        System.out.println("Statistics Display:");
        System.out.printf("  Avg Temp: %.1f°C, Max: %.1f°C, Min: %.1f°C%n", avg, max, min);
    }
}

/**
 * Forecast display
 */
class ForecastDisplay implements WeatherObserver {
    private double lastPressure = 0;
    
    @Override
    public void update(WeatherData data) {
        System.out.println("Forecast Display:");
        
        if (lastPressure == 0) {
            System.out.println("  Forecast: Insufficient data");
        } else if (data.pressure > lastPressure) {
            System.out.println("  Forecast: Improving weather on the way!");
        } else if (data.pressure < lastPressure) {
            System.out.println("  Forecast: Watch out for cooler, rainy weather");
        } else {
            System.out.println("  Forecast: More of the same");
        }
        
        lastPressure = data.pressure;
    }
}

// ============================================================================
// USAGE DEMONSTRATION
// ============================================================================

public class ObserverDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║           OBSERVER PATTERN DEMONSTRATION                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: News Agency
        System.out.println("━━━ News Agency Example ━━━\n");
        demonstrateNewsAgency();
        
        // Demo 2: Stock Market
        System.out.println("\n━━━ Stock Market Example ━━━\n");
        demonstrateStockMarket();
        
        // Demo 3: Weather Station
        System.out.println("\n━━━ Weather Station Example ━━━\n");
        demonstrateWeatherStation();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║          OBSERVER DEMO COMPLETED!                         ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void demonstrateNewsAgency() {
        NewsAgency agency = new NewsAgency();
        
        // Create observers
        NewsChannel cnn = new NewsChannel("CNN");
        NewsChannel bbc = new NewsChannel("BBC");
        NewsApp app = new NewsApp("NewsFlash");
        EmailNewsletter newsletter = new EmailNewsletter("Daily Digest", 5000);
        
        // Attach observers
        agency.attach(cnn);
        agency.attach(bbc);
        agency.attach(app);
        agency.attach(newsletter);
        
        // Publish news
        agency.setNews("Major tech company announces breakthrough in AI!");
        
        // Detach one observer
        System.out.println("--- BBC unsubscribes ---\n");
        agency.detach(bbc);
        
        // Publish more news
        agency.setNews("Stock markets reach all-time high!");
        
        System.out.println("✓ News published once, all subscribers notified!");
    }
    
    private static void demonstrateStockMarket() {
        // Create stocks
        Stock apple = new Stock("AAPL", 150.00);
        Stock google = new Stock("GOOGL", 2800.00);
        
        // Create investors
        Investor warren = new Investor("Warren");
        Investor charlie = new Investor("Charlie");
        TradingBot bot = new TradingBot("AlphaBot", 145.00, 160.00);
        
        // Subscribe to stocks
        apple.attach(warren);
        apple.attach(charlie);
        apple.attach(bot);
        
        google.attach(warren);
        
        // Price changes
        apple.setPrice(155.50);
        apple.setPrice(152.00);
        google.setPrice(2850.00);
        
        System.out.println("\n✓ Investors automatically notified of price changes!");
    }
    
    private static void demonstrateWeatherStation() {
        WeatherStation station = new WeatherStation();
        
        // Create displays
        CurrentConditionsDisplay current = new CurrentConditionsDisplay();
        StatisticsDisplay stats = new StatisticsDisplay();
        ForecastDisplay forecast = new ForecastDisplay();
        
        // Register displays
        station.addObserver(current);
        station.addObserver(stats);
        station.addObserver(forecast);
        
        // Weather changes
        station.setWeatherData(25.0, 65.0, 1013.0);
        System.out.println();
        station.setWeatherData(27.5, 70.0, 1015.0);
        System.out.println();
        station.setWeatherData(23.0, 80.0, 1008.0);
        
        System.out.println("\n✓ All displays updated automatically from single data source!");
    }
}

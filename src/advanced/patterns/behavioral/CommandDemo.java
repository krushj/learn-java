package advanced.patterns.behavioral;

import java.util.*;

/**
 * ============================================================================
 * COMMAND PATTERN
 * ============================================================================
 * 
 * PURPOSE: Encapsulates a request as an object, letting you parameterize
 * clients with different requests, queue or log requests, and support
 * undoable operations.
 * 
 * WHEN TO USE:
 * - Parameterize objects with operations
 * - Queue operations
 * - Support undo/redo
 * - Log changes
 * - Support transactions
 * 
 * COMPONENTS:
 * 1. Command - interface for executing operation
 * 2. ConcreteCommand - implements Command
 * 3. Receiver - knows how to perform operation
 * 4. Invoker - asks command to execute request
 * 5. Client - creates ConcreteCommand and sets Receiver
 * 
 * PROS:
 * - Decouples sender from receiver
 * - Easy to add new commands
 * - Can assemble commands into composite
 * - Supports undo/redo
 * 
 * CONS:
 * - Increases number of classes
 * - Can be overkill for simple operations
 * 
 * REAL-WORLD EXAMPLES:
 * - GUI button actions
 * - Transaction systems
 * - Text editor undo/redo
 * - Job schedulers
 */

// ============================================================================
// EXAMPLE 1: Smart Home Remote Control
// ============================================================================

/**
 * Command interface
 */
interface Command {
    void execute();
    void undo();
    String getDescription();
}

/**
 * Receiver - Light
 */
class Light {
    private String location;
    private boolean isOn = false;
    private int brightness = 100;
    
    public Light(String location) {
        this.location = location;
    }
    
    public void on() {
        isOn = true;
        System.out.println(location + " light is ON (brightness: " + brightness + "%)");
    }
    
    public void off() {
        isOn = false;
        System.out.println(location + " light is OFF");
    }
    
    public void dim(int level) {
        brightness = level;
        System.out.println(location + " light dimmed to " + brightness + "%");
    }
    
    public boolean isOn() { return isOn; }
    public String getLocation() { return location; }
}

/**
 * Receiver - Fan
 */
class Fan {
    private String location;
    private int speed = 0; // 0=off, 1=low, 2=medium, 3=high
    
    public Fan(String location) {
        this.location = location;
    }
    
    public void high() {
        speed = 3;
        System.out.println(location + " fan on HIGH");
    }
    
    public void medium() {
        speed = 2;
        System.out.println(location + " fan on MEDIUM");
    }
    
    public void low() {
        speed = 1;
        System.out.println(location + " fan on LOW");
    }
    
    public void off() {
        speed = 0;
        System.out.println(location + " fan is OFF");
    }
    
    public int getSpeed() { return speed; }
}

/**
 * Receiver - Stereo
 */
class Stereo {
    private String location;
    private boolean isOn = false;
    private int volume = 0;
    
    public Stereo(String location) {
        this.location = location;
    }
    
    public void on() {
        isOn = true;
        System.out.println(location + " stereo is ON");
    }
    
    public void off() {
        isOn = false;
        System.out.println(location + " stereo is OFF");
    }
    
    public void setCD() {
        System.out.println(location + " stereo set to CD mode");
    }
    
    public void setVolume(int volume) {
        this.volume = volume;
        System.out.println(location + " stereo volume set to " + volume);
    }
}

/**
 * Concrete Command - Light On
 */
class LightOnCommand implements Command {
    private Light light;
    
    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.on();
    }
    
    @Override
    public void undo() {
        light.off();
    }
    
    @Override
    public String getDescription() {
        return "Turn on " + light.getLocation() + " light";
    }
}

/**
 * Concrete Command - Light Off
 */
class LightOffCommand implements Command {
    private Light light;
    
    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.off();
    }
    
    @Override
    public void undo() {
        light.on();
    }
    
    @Override
    public String getDescription() {
        return "Turn off " + light.getLocation() + " light";
    }
}

/**
 * Concrete Command - Light Dim
 */
class LightDimCommand implements Command {
    private Light light;
    private int previousLevel;
    private int newLevel;
    
    public LightDimCommand(Light light, int level) {
        this.light = light;
        this.newLevel = level;
    }
    
    @Override
    public void execute() {
        previousLevel = 100; // Would get actual previous level
        light.dim(newLevel);
    }
    
    @Override
    public void undo() {
        light.dim(previousLevel);
    }
    
    @Override
    public String getDescription() {
        return "Dim " + light.getLocation() + " light to " + newLevel + "%";
    }
}

/**
 * Concrete Command - Fan High
 */
class FanHighCommand implements Command {
    private Fan fan;
    private int previousSpeed;
    
    public FanHighCommand(Fan fan) {
        this.fan = fan;
    }
    
    @Override
    public void execute() {
        previousSpeed = fan.getSpeed();
        fan.high();
    }
    
    @Override
    public void undo() {
        switch (previousSpeed) {
            case 0: fan.off(); break;
            case 1: fan.low(); break;
            case 2: fan.medium(); break;
            case 3: fan.high(); break;
        }
    }
    
    @Override
    public String getDescription() {
        return "Set fan to high";
    }
}

/**
 * Concrete Command - Fan Off
 */
class FanOffCommand implements Command {
    private Fan fan;
    private int previousSpeed;
    
    public FanOffCommand(Fan fan) {
        this.fan = fan;
    }
    
    @Override
    public void execute() {
        previousSpeed = fan.getSpeed();
        fan.off();
    }
    
    @Override
    public void undo() {
        switch (previousSpeed) {
            case 1: fan.low(); break;
            case 2: fan.medium(); break;
            case 3: fan.high(); break;
        }
    }
    
    @Override
    public String getDescription() {
        return "Turn fan off";
    }
}

/**
 * Concrete Command - Stereo On with CD
 */
class StereoOnWithCDCommand implements Command {
    private Stereo stereo;
    
    public StereoOnWithCDCommand(Stereo stereo) {
        this.stereo = stereo;
    }
    
    @Override
    public void execute() {
        stereo.on();
        stereo.setCD();
        stereo.setVolume(11);
    }
    
    @Override
    public void undo() {
        stereo.off();
    }
    
    @Override
    public String getDescription() {
        return "Turn on stereo with CD";
    }
}

/**
 * No-op Command (Null Object pattern)
 */
class NoCommand implements Command {
    @Override
    public void execute() { }
    
    @Override
    public void undo() { }
    
    @Override
    public String getDescription() {
        return "No command assigned";
    }
}

/**
 * Macro Command - executes multiple commands
 */
class MacroCommand implements Command {
    private Command[] commands;
    private String name;
    
    public MacroCommand(String name, Command[] commands) {
        this.name = name;
        this.commands = commands;
    }
    
    @Override
    public void execute() {
        System.out.println("\n--- Executing Macro: " + name + " ---");
        for (Command command : commands) {
            command.execute();
        }
    }
    
    @Override
    public void undo() {
        System.out.println("\n--- Undoing Macro: " + name + " ---");
        // Undo in reverse order
        for (int i = commands.length - 1; i >= 0; i--) {
            commands[i].undo();
        }
    }
    
    @Override
    public String getDescription() {
        return "Macro: " + name;
    }
}

/**
 * Invoker - Remote Control
 */
class RemoteControl {
    private Command[] onCommands;
    private Command[] offCommands;
    private Stack<Command> undoStack;
    
    private static final int SLOT_COUNT = 7;
    
    public RemoteControl() {
        onCommands = new Command[SLOT_COUNT];
        offCommands = new Command[SLOT_COUNT];
        undoStack = new Stack<>();
        
        Command noCommand = new NoCommand();
        for (int i = 0; i < SLOT_COUNT; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
    }
    
    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }
    
    public void onButtonPressed(int slot) {
        onCommands[slot].execute();
        undoStack.push(onCommands[slot]);
    }
    
    public void offButtonPressed(int slot) {
        offCommands[slot].execute();
        undoStack.push(offCommands[slot]);
    }
    
    public void undoButtonPressed() {
        if (!undoStack.isEmpty()) {
            Command lastCommand = undoStack.pop();
            System.out.println("\n--- Undoing: " + lastCommand.getDescription() + " ---");
            lastCommand.undo();
        } else {
            System.out.println("Nothing to undo");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔════════════════════════════════════════╗\n");
        sb.append("║          REMOTE CONTROL                ║\n");
        sb.append("╚════════════════════════════════════════╝\n");
        
        for (int i = 0; i < SLOT_COUNT; i++) {
            sb.append("[slot ").append(i).append("] ");
            sb.append(onCommands[i].getDescription());
            sb.append(" / ");
            sb.append(offCommands[i].getDescription());
            sb.append("\n");
        }
        return sb.toString();
    }
}

// ============================================================================
// EXAMPLE 2: Text Editor with Undo/Redo
// ============================================================================

/**
 * Receiver - Text Document
 */
class TextDocument {
    private StringBuilder content = new StringBuilder();
    
    public void write(String text) {
        content.append(text);
    }
    
    public void delete(int start, int length) {
        content.delete(start, start + length);
    }
    
    public void insert(int position, String text) {
        content.insert(position, text);
    }
    
    public String getContent() {
        return content.toString();
    }
    
    public int length() {
        return content.length();
    }
}

/**
 * Command - Write Text
 */
class WriteCommand implements Command {
    private TextDocument document;
    private String text;
    private int position;
    
    public WriteCommand(TextDocument document, String text) {
        this.document = document;
        this.text = text;
        this.position = document.length();
    }
    
    @Override
    public void execute() {
        document.write(text);
    }
    
    @Override
    public void undo() {
        document.delete(position, text.length());
    }
    
    @Override
    public String getDescription() {
        return "Write: \"" + text + "\"";
    }
}

/**
 * Text Editor with command history
 */
class TextEditor {
    private TextDocument document;
    private Stack<Command> history = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();
    
    public TextEditor() {
        this.document = new TextDocument();
    }
    
    public void write(String text) {
        Command cmd = new WriteCommand(document, text);
        cmd.execute();
        history.push(cmd);
        redoStack.clear();
        System.out.println("Executed: " + cmd.getDescription());
    }
    
    public void undo() {
        if (!history.isEmpty()) {
            Command cmd = history.pop();
            cmd.undo();
            redoStack.push(cmd);
            System.out.println("Undone: " + cmd.getDescription());
        } else {
            System.out.println("Nothing to undo");
        }
    }
    
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            history.push(cmd);
            System.out.println("Redone: " + cmd.getDescription());
        } else {
            System.out.println("Nothing to redo");
        }
    }
    
    public String getContent() {
        return document.getContent();
    }
}

// ============================================================================
// USAGE DEMONSTRATION
// ============================================================================

public class CommandDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║           COMMAND PATTERN DEMONSTRATION                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        // Demo 1: Smart Home Remote
        System.out.println("━━━ Smart Home Remote Example ━━━\n");
        demonstrateRemoteControl();
        
        // Demo 2: Macro Commands
        System.out.println("\n━━━ Macro Commands Example ━━━\n");
        demonstrateMacroCommand();
        
        // Demo 3: Text Editor Undo/Redo
        System.out.println("\n━━━ Text Editor Undo/Redo Example ━━━\n");
        demonstrateTextEditor();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║          COMMAND DEMO COMPLETED!                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }
    
    private static void demonstrateRemoteControl() {
        RemoteControl remote = new RemoteControl();
        
        // Create receivers
        Light livingRoomLight = new Light("Living Room");
        Light kitchenLight = new Light("Kitchen");
        Fan ceilingFan = new Fan("Ceiling");
        Stereo stereo = new Stereo("Living Room");
        
        // Create commands
        LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
        LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
        
        LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
        LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);
        
        FanHighCommand fanHigh = new FanHighCommand(ceilingFan);
        FanOffCommand fanOff = new FanOffCommand(ceilingFan);
        
        StereoOnWithCDCommand stereoOn = new StereoOnWithCDCommand(stereo);
        
        // Program remote
        remote.setCommand(0, livingRoomLightOn, livingRoomLightOff);
        remote.setCommand(1, kitchenLightOn, kitchenLightOff);
        remote.setCommand(2, fanHigh, fanOff);
        remote.setCommand(3, stereoOn, new NoCommand());
        
        System.out.println(remote);
        
        // Test buttons
        System.out.println("--- Pressing Buttons ---\n");
        remote.onButtonPressed(0);
        remote.onButtonPressed(1);
        remote.onButtonPressed(2);
        
        System.out.println();
        remote.offButtonPressed(0);
        remote.undoButtonPressed();
        
        System.out.println("\n✓ Commands encapsulate actions and support undo!");
    }
    
    private static void demonstrateMacroCommand() {
        Light light = new Light("Party");
        Stereo stereo = new Stereo("Party");
        Fan fan = new Fan("Party");
        
        // Create party mode commands
        Command[] partyOn = {
            new LightOnCommand(light),
            new LightDimCommand(light, 30),
            new StereoOnWithCDCommand(stereo),
            new FanHighCommand(fan)
        };
        
        Command[] partyOff = {
            new LightOffCommand(light),
            new FanOffCommand(fan)
        };
        
        MacroCommand partyOnMacro = new MacroCommand("Party Mode ON", partyOn);
        MacroCommand partyOffMacro = new MacroCommand("Party Mode OFF", partyOff);
        
        RemoteControl remote = new RemoteControl();
        remote.setCommand(0, partyOnMacro, partyOffMacro);
        
        // Execute macro
        remote.onButtonPressed(0);
        
        System.out.println("\n--- Party Time! ---\n");
        
        remote.offButtonPressed(0);
        
        // Undo entire macro
        remote.undoButtonPressed();
        
        System.out.println("\n✓ Macro commands execute multiple commands as one!");
    }
    
    private static void demonstrateTextEditor() {
        TextEditor editor = new TextEditor();
        
        System.out.println("Writing text...\n");
        editor.write("Hello ");
        System.out.println("Content: \"" + editor.getContent() + "\"\n");
        
        editor.write("World!");
        System.out.println("Content: \"" + editor.getContent() + "\"\n");
        
        editor.write(" How are you?");
        System.out.println("Content: \"" + editor.getContent() + "\"\n");
        
        System.out.println("--- Performing Undo ---\n");
        editor.undo();
        System.out.println("Content: \"" + editor.getContent() + "\"\n");
        
        editor.undo();
        System.out.println("Content: \"" + editor.getContent() + "\"\n");
        
        System.out.println("--- Performing Redo ---\n");
        editor.redo();
        System.out.println("Content: \"" + editor.getContent() + "\"\n");
        
        System.out.println("✓ Command pattern enables undo/redo functionality!");
    }
}

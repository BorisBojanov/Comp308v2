/**
 * GreenhouseControls.java
 * Assignment: TME4
 * @author: Boris B
 * @date  : Jan 29, 2025
 *
 * Description: GreenhouseControls class
 * 
 * Program Description:
 * GreenhouseControls:
 * GreenhouseControls will no longer handle event timing.
 * It will only start the threads for each event.
 *
 * 
 */


package tme4;

import tme4.events.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;


interface Fixable {
    void fix();
    void log();
}

public class GreenhouseControls extends Controller implements Serializable, Fixable{
    //To properly manage event execution, replace both eventMap and eventThreads with stateVariables, which is already declared
    // private final Map<Event, Integer> eventErrorCode = new HashMap<>();
    // private final Map<Event, String> eventNames = new HashMap<>();
    // private final Map<Event, String> eventDayOrNight = new HashMap<>();
    // private final Map<Event, Long> eventTimes = new HashMap<>();
    // private final Map<Event, String> eventDescriptions = new HashMap<>();
    // private final Map<Event, Boolean> eventMalfunctionIsFixed = new HashMap<>();
    // private final Map<Event, Boolean> eventIsOn = new HashMap<>(); 

    private static final Map<String, TwoTuple<String, Object>> stateVariables = Collections.synchronizedMap(new HashMap<>());
    private static final int MAX_WINDOWS = 5; // Limit of 5 windows
    private final boolean[] windowStates = new boolean[MAX_WINDOWS]; // Track window state


    // Getters for stateVariables 
    public Map<Event, Thread> getEventThreads() {
        Map<Event, Thread> eventThreadMap = new HashMap<>();
    
        synchronized (stateVariables) {
            stateVariables.forEach((key, value) -> {
                if (value.value instanceof Event) {
                    String eventName = key;
                    Event event = (Event) value.value;
    
                    // Check for the associated thread
                    TwoTuple<String, Object> threadTuple = stateVariables.get(eventName + "_thread");
                    if (threadTuple != null && threadTuple.value instanceof Thread) {
                        Thread thread = (Thread) threadTuple.value;
                        eventThreadMap.put(event, thread);
                    }
                }
            });
        }
    
        return eventThreadMap;
    }

    public static Map<String, TwoTuple<String, Object>> getStateVariables() {
        return stateVariables;
    }

    public static synchronized TwoTuple<String, Object> getVariable(String key) {  // getVariable() returns TwoTuple<String, Object>.
        return stateVariables.get(key);
    }
       
     /**
     * Retrieves error code for an event
     * @param event
     * @return int errorcode
     */
    public int getEventErrorCode(Event event) {
        TwoTuple<String, Object> errorEntry = getVariable(event.getClass().getSimpleName() + "_errorCode");
        return (errorEntry != null && errorEntry.value instanceof Integer) ? (int) errorEntry.value : 0;
    }

    /**
     * Retrieves the error code for a specific event from stateVariables.
     * @param event The event for which the error code is requested.
     * @return The error code as an integer. Returns 0 if the event has no error.
     */
    public int getError(Event event) {
        String key = event.getClass().getSimpleName() + "_errorCode";
        TwoTuple<String, Object> errorEntry = getVariable(key);

        // Check if the retrieved value exists and is an Integer
        return (errorEntry != null && errorEntry.value instanceof Integer) ? (int) errorEntry.value : 0;
    }

    /**
     * @param errorcode
     * @return Fixable object to fix the error
     */
    public Fixable getFixable(int errorcode){
        switch (errorcode) {
            case 1:
            return new FixWindow();
            case 2:
            return new PowerOn();
            default:
            return null;
        }
    }

    /**
     * Sets error code for an event
     */
    public void setEventErrorCode(Event event, int errorCode) {
        setVariable(event.getClass().getSimpleName() + "_errorCode", errorCode);
    }

    public void setEventNames(Event event, String name) {
        setVariable(event.getClass().getSimpleName() + "_name", name);
    }

    public void setEventDayOrNight(Event event, String dayOrNight) {
        setVariable(event.getClass().getSimpleName() + "_dayOrNight", dayOrNight);
    }

    public void setEventTimes(Event event, long time) {
        setVariable(event.getClass().getSimpleName() + "_time", time);
    }

    public void setEventDescriptions(Event event, String description) {
        setVariable(event.getClass().getSimpleName() + "_description", description);
    }

    /**
     * Sets malfunction status for an event
     */
    public void setEventMalfunctionIsFixed(Event event, boolean isFixed) {
        setVariable(event.getClass().getSimpleName() + "_isFixed", isFixed);
    }

    public void setEventIsOn(Event event, boolean isOn) {
        setVariable(event.getClass().getSimpleName() + "_isOn", isOn);
    }

    public void setEventThreads(Event event, Thread thread) {
        setVariable(event.getClass().getSimpleName() + "_thread", thread);
    }

    /**
     * Retrieves whether an event malfunction has been fixed
     */
    public boolean isEventFixed(Event event) {
        TwoTuple<String, Object> fixedEntry = getVariable(event.getClass().getSimpleName() + "_isFixed");
        return (fixedEntry != null && fixedEntry.value instanceof Boolean) ? (boolean) fixedEntry.value : true;
    }

    /**
     * Retrieves whether an event is on
     */
    public boolean isEventOn(Event event) {
        TwoTuple<String, Object> isOnEntry = getVariable(event.getClass().getSimpleName() + "_isOn");
        return (isOnEntry != null && isOnEntry.value instanceof Boolean) ? (boolean) isOnEntry.value : false;
    }
    
    // Set a variable in the system state
    public static synchronized void setVariable(String key, Object value) {     
        stateVariables.put(key, new TwoTuple<>(key, value));
        // System.out.println("Updated variable: " + key + " = " + value); // Debugging
    }
    
    // Window operations
    /**
     * Checks if a window is open
     */
    public boolean isWindowOpen(int windowID) {
        return windowID >= 0 && windowID < MAX_WINDOWS && windowStates[windowID];
    }

    /**
     * Opens a window if there is space available
     */
    public boolean openWindow(int windowID) {
        if (windowID < 0 || windowID >= MAX_WINDOWS) {
            System.err.println("Invalid window ID: " + windowID);
            return false;
        }
        if (windowStates[windowID]) {
            System.out.println("Window " + windowID + " is already open.");
            return false;
        }
        windowStates[windowID] = true;
        System.out.println("✅ Window " + windowID + " opened.");
        return true;
    }

    /**
     * Closes a window
     */
    public boolean closeWindow(int windowID) {
        if (windowID < 0 || windowID >= MAX_WINDOWS) {
            System.err.println("Invalid window ID: " + windowID);
            return false;
        }
        if (!windowStates[windowID]) {
            System.out.println("Window " + windowID + " is already closed.");
            return false;
        }
        windowStates[windowID] = false;
        System.out.println("❌ Window " + windowID + " closed.");
        return true;
    }

    // Fixable interface methods
    @Override
    public void fix() {
        synchronized (stateVariables) {
            // Fix all event malfunctions
            stateVariables.forEach((key, value) -> {
                if (key.endsWith("_isFixed")) {
                    setVariable(key, true); // Mark all events as fixed
                }
            });
    
            // Reset error codes for all events
            stateVariables.forEach((key, value) -> {
                if (key.endsWith("_errorCode")) {
                    setVariable(key, 0);
                }
            });
    
            System.out.println("All events fixed.");
            log();
        }
    }

    @Override
    public void log() {
        synchronized (stateVariables) {
            try (PrintWriter writer = new PrintWriter(new FileWriter("fix.log", true))) {
                String timestamp = Calendar.getInstance().getTime().toString();
    
                // Check if all windows are fixed and power is restored
                boolean allWindowsFixed = stateVariables.entrySet().stream()
                    .filter(e -> e.getKey().endsWith("_isFixed"))
                    .allMatch(e -> (boolean) e.getValue().value);
    
                boolean allPowerOn = stateVariables.entrySet().stream()
                    .filter(e -> e.getKey().endsWith("_isOn"))
                    .allMatch(e -> (boolean) e.getValue().value);
    
                writer.println("All events fixed at " + timestamp);
                writer.printf("%s | Fix applied. Window: %s, Power: %s, Error Code Reset.%n",
                      timestamp, allWindowsFixed ? "OK" : "Malfunction", allPowerOn ? "On" : "Off");
    
            } catch (IOException e) {
                System.err.println("Error writing to log file: " + e.getMessage());
            }
        }
    }
    
    
    /**
     * Checks if all windows are fixed.
     */
    private boolean allWindowsFixed() {
        synchronized (stateVariables) {
            return stateVariables.entrySet().stream()
                .filter(e -> e.getKey().endsWith("_isFixed"))
                .allMatch(e -> (boolean) e.getValue().value);
        }
    }

    /**
     * Checks if all power is on.
     */
    private boolean allPowerOn() {
        synchronized (stateVariables) {
            return stateVariables.entrySet().stream()
                .filter(e -> e.getKey().endsWith("_isOn"))
                .allMatch(e -> (boolean) e.getValue().value);
        }
    }

    // Method to serialize the state of the system
    private void serializeState() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dump.out"))) {
            out.writeObject(this);
            System.out.println("State serialized to dump.out");
        } catch (IOException e) {
            System.err.println("Failed to serialize state: " + e.getMessage());
        }
    }

    /**
     * Restores a serialized GreenhouseControls object from a file.
     */
    public static GreenhouseControls restoreState(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            GreenhouseControls restored = (GreenhouseControls) in.readObject();
            System.out.println("✅ Successfully deserialized GreenhouseControls from: " + filename);
            return restored;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Failed to restore state: " + e.getMessage());
            return null;
        }
    }

    // Event handling
    // Start event with delay and period, all events that have been created will be started.
    public void startEvent(String eventName, long delayTime, long period) {
        System.out.println("🟢 Attempting to start event: " + eventName);
    
        if (stateVariables.containsKey(eventName)) {
            System.out.println("❌ Duplicate event ignored: " + eventName);
            return;
        }
    
        try {
            Class<?> clazz = Class.forName("tme4.events." + eventName);
            Constructor<?> constructor = clazz.getConstructor(GreenhouseControls.class, long.class);
            Event event = (Event) constructor.newInstance(this, delayTime);
    
            setVariable(eventName, event);
            addEvent(event);  // ✅ Ensure event is added to Controller's event list
            new Thread(event).start();
            
            System.out.println(eventName + " event started.");
        } catch (Exception e) {
            System.err.println("Failed to create event: " + e.getMessage());
        }
    }
        
    // Suspend all event threads
    public void suspendEvents() {
        synchronized (stateVariables) {
            stateVariables.forEach((key, value) -> {
                if (value.value instanceof Event) {
                    ((Event) value.value).suspend();
                }
            });
        }
        System.out.println("All events suspended.");
    }

    // Resume all event threads
    public void resumeEvents() {
        synchronized (stateVariables) {
            stateVariables.forEach((key, value) -> {
                if (value.value instanceof Event) {
                    ((Event) value.value).resume();
                }
            });
        }
        System.out.println("All events resumed.");
    }
    
    // Inner classes for fixing errors
    // Inner class to restore power
    // Inner class to restore power
    public class PowerOn implements Fixable {
        @Override
        public void fix() {
            synchronized (stateVariables) {
                stateVariables.forEach((key, value) -> {
                    if (key.endsWith("_isOn")) {
                        setVariable(key, true);
                    }
                });
            }
            System.out.println("Power restored.");
            log();
        }

        @Override
        public void log() {
            synchronized (stateVariables) {
                try (PrintWriter writer = new PrintWriter(new FileWriter("fix.log", true))) {
                    String timestamp = Calendar.getInstance().getTime().toString();
                    boolean windowok = allWindowsFixed();
                    boolean poweron = allPowerOn();

                    writer.println("Power restored at " + timestamp);
                    writer.printf("%s | Fix applied. Window: %s, Power: %s, Error Code Reset.%n",
                        timestamp, windowok ? "OK" : "Malfunction", poweron ? "On" : "Off");

                } catch (IOException e) {
                    System.err.println("Error writing to log file: " + e.getMessage());
                }
            }
        }
    }

    // Inner class to fix window
    public class FixWindow implements Fixable {
        @Override
        public void fix() {
            synchronized (stateVariables) {
                stateVariables.forEach((key, value) -> {
                    if (key.endsWith("_isFixed")) {
                        setVariable(key, true);
                    }
                });
            }
            System.out.println("Window fixed.");
            log();
        }

        @Override
        public void log() {
            synchronized (stateVariables) {
                try (PrintWriter writer = new PrintWriter(new FileWriter("fix.log", true))) {
                    String timestamp = Calendar.getInstance().getTime().toString();
                    boolean windowok = allWindowsFixed();
                    boolean poweron = allPowerOn();

                    writer.println("Window fixed at " + timestamp);
                    writer.printf("%s | Fix applied. Window: %s, Power: %s, Error Code Reset.%n",
                        timestamp, windowok ? "OK" : "Malfunction", poweron ? "On" : "Off");

                } catch (IOException e) {
                    System.err.println("Error writing to log file: " + e.getMessage());
                }
            }
        }
    }

    // Custom exception class for controller errors
    public class ControllerException extends RuntimeException {
        public ControllerException(String message) {
          super(message);
        }
      }
    
    @Override
    public void run() {
        while (eventList.size() > 0) {
            for (Event e : new ArrayList<>(eventList)) {
                if (e.ready()) {
                    try {
                        System.out.println(e);
                        e.action(); // Execute event action
                        eventList.remove(e); // Remove event after execution
                    } catch (ControllerException ex) {
                        logError(ex.getMessage());
                        serializeState();
                        System.exit(1); // Exit after handling the error
                    }
                }
            }
        }
    }

    /**
     * Logs errors to a file with a timestamp and event error code.
     * @param message
     */
    private void logError(String message) {
        synchronized (stateVariables) {
            try (PrintWriter writer = new PrintWriter(new FileWriter("error.log", true))) {
                String timestamp = Calendar.getInstance().getTime().toString();

                // Collect all error codes dynamically
                StringBuilder errorCodes = new StringBuilder();
                stateVariables.forEach((key, value) -> {
                    if (key.endsWith("_errorCode")) {
                        errorCodes.append(key.replace("_errorCode", "")).append(": ")
                            .append(value.value).append(", ");
                    }
                });

                writer.println(timestamp + " | Errors Code: " + message + " | " + errorCodes.toString());

            } catch (IOException e) {
                System.err.println("Error writing to log file: " + e.getMessage());
            }
        }
    }

    // Load events from a file
    // Choosing not to convert to absolute path, as it is hard.
    public void loadEventsFromFile(String filePath) {
        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            System.err.println("❌ Error: File does not exist: " + filePath);
            return;
        }
        try(Scanner scanner = new Scanner(new File(filePath))){
            while(scanner.hasNextLine()){
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue; // Skip empty lines

                processEventLine(line);
            }
        } catch(IOException e){
            System.err.println("❌ Error reading file: " + e.getMessage());
        }
    }

    // Process a single event line
    private void processEventLine(String line) {
        try {
            // Parse key-value pairs
            // Local map to store key-value pairs
            Map<String, String> eventMap = new HashMap<>();
            for (String pair : line.split(",")) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    eventMap.put(keyValue[0].trim(), keyValue[1].trim());
                } else {
                    System.err.println("Invalid key-value pair: " + pair);
                }
            }

            // Extract event name
            // String eventName = eventMap.get("Event");
            String eventName = eventMap.get("Event").trim();
            if (eventName.startsWith("Event=")) {
                eventName = eventName.substring(6);
            }

            if (eventName == null) {
                System.err.println("Missing 'Event' key in line: " + line);
                return;
            }


            // Extract delay time
            long time = Long.parseLong(eventMap.get("time"));

            // Check if event already exists in eventThreads (to avoid duplicates)
            if (stateVariables.containsKey(eventName)) {
                System.out.println("❌ Duplicate event ignored: " + eventName);
                return;
            }

            // Use Reflection to create the Event dynamically
            Class<?> clazz = Class.forName("tme4.events." + eventName);

            // System.out.println("Available constructors for " + clazz.getName() + ":");  // Debugging
            for (Constructor<?> c : clazz.getConstructors()) {
                System.out.println(c);
            }

            Constructor<?> constructor = clazz.getConstructor(GreenhouseControls.class, long.class);
            Event event = (Event) constructor.newInstance(this, time);


            // Special cases: Events with extra parameters
            if (eventName.equals("Bell") && eventMap.containsKey("rings")) {
                int rings = Integer.parseInt(eventMap.get("rings"));
                constructor = clazz.getConstructor(GreenhouseControls.class, long.class, int.class);
                event = (Event) constructor.newInstance(this, time, rings);
            } else if ((eventName.equals("WindowOn") || eventName.equals("WindowOff")) && eventMap.containsKey("windowID")) {
                int windowID = Integer.parseInt(eventMap.get("windowID"));
                constructor = clazz.getConstructor(GreenhouseControls.class, long.class, int.class);
                event = (Event) constructor.newInstance(this, time, windowID);
            } else if (eventName.equals("Restart") && eventMap.containsKey("filename")) {
                String filename = eventMap.get("filename");
                constructor = clazz.getConstructor(GreenhouseControls.class, long.class, String.class);
                event = (Event) constructor.newInstance(this, time, filename);
            } else {
                // Default constructor (assume standard (GreenhouseControls, long) constructor)
                constructor = clazz.getConstructor(GreenhouseControls.class, long.class);
                event = (Event) constructor.newInstance(this, time);
            }

            // Store in stateVariables instead of separate maps
            stateVariables.put(eventName, new TwoTuple<>("event", event));
            setVariable(eventName, event);
            // System.out.println("✅ Successfully created event: " + eventName);

        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: Unknown event type '" + line + "'");
        } catch (NoSuchMethodException e) {
            System.err.println("❌ Error: Constructor not found for event '" + line + "'");
        } catch (Exception e) {
            System.err.println("❌ Error processing event line '" + line + "': " + e.getMessage());
        }
    }

}

    


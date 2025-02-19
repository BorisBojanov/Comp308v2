/**
 * GreenhouseControls class manages events and system state for the greenhouse simulation.
 * It controls multiple event threads, maintains system state, handles event execution,
 * and supports serialization for system restoration.
 *
 * Assignment: TME4
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */



import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GreenhouseControls extends Controller implements Serializable{
    
    /** Manages active event threads. */
    private final Map<Event, Thread> eventThreads = new ConcurrentHashMap<>();

    /** Stores system state variables for events. */
    private static final Map<String, List<TwoTuple<String, Object>>> stateVariables = 
        Collections.synchronizedMap(new HashMap<>());

    /** Maximum number of allowed GUI windows. */
    private static final int MAX_WINDOWS = 5;

    /** Tracks the state (open/closed) of GUI windows. */
    private final boolean[] windowStates = new boolean[MAX_WINDOWS];

    /**
     * Retrieves the current event threads.
     *
     * @return A map of active events and their corresponding threads.
     */
    public Map<Event, Thread> getEventThreads() {
        Map<Event, Thread> eventThreadMap = new HashMap<>();
        synchronized (stateVariables) {
            stateVariables.forEach((eventName, propertiesList) -> {
                if (propertiesList != null) {
                    Event event = null;
                    Thread thread = null;
                    for (TwoTuple<String, Object> property : propertiesList) {
                        if (property.value instanceof Event) {
                            event = (Event) property.value;
                        }
                        if (property.value instanceof Thread) {
                            thread = (Thread) property.value;
                        }
                    }
                    if (event != null && thread != null) {
                        eventThreadMap.put(event, thread);
                    }
                }
            });
        }
        return eventThreadMap;
    }

    /**
     * Prints the current state variables to the console.
     */
    public void printStateVariables() {
        synchronized (stateVariables) {
            System.out.println("Current State Variables:");
            for (Map.Entry<String, List<TwoTuple<String, Object>>> entry : stateVariables.entrySet()) {
                System.out.println("Event Name: " + entry.getKey());
                for (TwoTuple<String, Object> tuple : entry.getValue()) {
                    System.out.println("    " + tuple.key + " = " + tuple.value);
                }
            }
        }
    }

    /**
     * Sets a state variable for an event.
     *
     * @param key The event name.
     * @param propertyKey The specific property key.
     * @param value The value to store.
     */
    public static synchronized void setVariable(String key, String propertyKey, Object value) {
        synchronized (stateVariables) {
            stateVariables.computeIfAbsent(key, k -> new ArrayList<>()).add(new TwoTuple<>(propertyKey, value));
        }
    }
    
    /**
     * Gets the Value Pair based on Event
     * @param eventName
     * @return TwoTuple<String, Object>
    */
    public static synchronized Object getEventVariable(String eventName, String propertyKey) {
        synchronized (stateVariables) {
            List<TwoTuple<String, Object>> properties = stateVariables.get(eventName);
            if (properties != null) {
                for (TwoTuple<String, Object> tuple : properties) {
                    if (tuple.key.equals(propertyKey)) {
                        return tuple.value;
                    }
                }
            }
        }
        return null; // Return null if property not found
    }
    

    public synchronized Map<String, List<TwoTuple<String, Object>>> getStateVariables() {
        return stateVariables;
    }
    
    // Window operations
    /**
     * Checks if a window is open.
     *
     * @param windowID The window index.
     * @return true if the window is open, false otherwise.
     */
    public boolean getIsWindowOpen(int windowID) {
        return windowID >= 0 && windowID < MAX_WINDOWS && windowStates[windowID];
    }

    /**
     * Opens a window.
     *
     * @param windowID The window index.
     * @return true if the window was successfully opened, false otherwise.
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
        System.out.println("Window " + windowID + " opened.");
        return true;
    }

    /**
     * Closes a window.
     *
     * @param windowID The window index.
     * @return true if the window was successfully closed, false otherwise.
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
        System.out.println("Window " + windowID + " closed.");
        return true;
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
     * Restores a GreenhouseControls object from a serialized state file.
     *
     * @param filename The path to the serialized file.
     * @return The restored GreenhouseControls object.
     */
    public static GreenhouseControls restoreState(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            GreenhouseControls restored = (GreenhouseControls) in.readObject();
            System.out.println("Successfully deserialized GreenhouseControls from: " + filename);
            return restored;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to restore state: " + e.getMessage());
            return null;
        }
    }

    /**
    Iterate through stateVariables to find all loaded events.
    Check if each event has enough information to call startEvent().
    Call startEvent() with two or three parameters, depending on whether the event has a "rings" value.

    Iterates over stateVariables (which contains all stored events).
    Extracts "time" and, if available, "rings" from the TwoTuple list.
    If "time" is missing, it skips the event.
    Calls startEvent(eventName, delayTime, rings) if "rings" is available.
    Otherwise, calls startEvent(eventName, delayTime).
    */
    public void executeLoadedEvents() {
        synchronized (stateVariables) {
            System.out.println("Executing all loaded events...");
    
            for (Map.Entry<String, List<TwoTuple<String, Object>>> entry : stateVariables.entrySet()) {
                String eventName = entry.getKey();
                List<TwoTuple<String, Object>> properties = entry.getValue();
    
                Long delayTime = null;
                Integer rings = null;
    
                // Extract parameters from the properties list
                for (TwoTuple<String, Object> tuple : properties) {
                    if (tuple.key.equals("time")) {
                        if (tuple.value instanceof Integer) {
                            delayTime = ((Integer) tuple.value).longValue(); // Convert Integer to long
                        } else if (tuple.value instanceof Long) {
                            delayTime = (Long) tuple.value;
                        }
                    } else if (tuple.key.equals("rings") && tuple.value instanceof Integer) {
                        rings = (Integer) tuple.value;
                    }
                }
    
                // Ensure that the event has a delay time
                if (delayTime == null) {
                    System.err.println("Skipping event '" + eventName + "': Missing 'time' parameter.");
                    continue;
                }
    
                // Call the correct `startEvent()` method based on the presence of `rings`
                if (rings != null) {
                    startEvent(eventName, delayTime, rings);
                } else {
                    startEvent(eventName, delayTime);
                }
            }
        }
    }
    
    /**
     * Suspends all running event threads.
     */
    public void suspendEvents() {
        eventThreads.keySet().forEach(Event::suspend);
        System.out.println("All events suspended.");
    }

    /**
     * Resumes all suspended event threads.
     */
    public void resumeEvents() {
        eventThreads.keySet().forEach(Event::resume);
        System.out.println("All events resumed.");
    }


    // Custom exception class for controller errors
    public class ControllerException extends RuntimeException {
        public ControllerException(String message) {
          super(message);
        }
      }

    // Event handling
    // Start event with delay and period, all events that have been created will be started.
    // The event will be started in a new thread. 
    public void startEvent(String eventName, long delayTime) {
        try {
            Class<?> clazz = Class.forName("tme4.events." + eventName);
            Constructor<?> constructor = clazz.getConstructor(GreenhouseControls.class, long.class);
            Event event = (Event) constructor.newInstance(this, delayTime);
    
            Thread eventThread = new Thread(event);
            eventThreads.put(event, eventThread);
            eventThread.start();
    
            // Store event properties correctly
            setVariable(eventName, "status", "Started");
            setVariable(eventName, "time", delayTime);
            setVariable(eventName, "thread", eventThread); // Store thread reference
    
            System.out.println(" Event started: " + eventName);
        } catch (Exception e) {
            System.err.println(" Failed to start event: " + e.getMessage());
        }
    }
    
    
    public void startEvent(String eventName, long delayTime, int rings) {
        try {
            Class<?> clazz = Class.forName("tme4.events." + eventName);
            Constructor<?> constructor = clazz.getConstructor(GreenhouseControls.class, long.class, int.class);
            Event event = (Event) constructor.newInstance(this, delayTime, rings);
    
            Thread eventThread = new Thread(event);
            eventThreads.put(event, eventThread);
            eventThread.start();
    
            // Store event properties correctly
            setVariable(eventName, "status", "Started");
            setVariable(eventName, "time", delayTime);
            setVariable(eventName, "rings", rings);
            setVariable(eventName, "thread", eventThread); // Store thread reference
    
            System.out.println(" Event started with rings: " + eventName);
        } catch (Exception e) {
            System.err.println(" Failed to start event: " + e.getMessage());
        }
    }
    
    /**
     * Loads events from a specified file.
     *  Choosing not to convert to absolute path, as it is hard.
     * @param filePath The path to the event file.
     */
    public void loadEventsFromFile(String filePath) {
        File file = new File(filePath);

        if (!file.exists() || !file.isFile()) {
            System.err.println(" Error: File does not exist: " + filePath);
            return;
        }
        try(Scanner scanner = new Scanner(new File(filePath))){
            while(scanner.hasNextLine()){
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue; // Skip empty lines
                System.out.println("Format of line being passed to processEventLine: " + line);
                processEventLine(line);
            }
        } catch(IOException e){
            System.err.println(" Error reading file: " + e.getMessage());
        }
    }

    private static Object parseValue(String value) {
        if (value.matches("-?\\d+")) {
            return Integer.parseInt(value);  // Integer
        } else if (value.matches("-?\\d+\\.\\d+")) {
            return Double.parseDouble(value); // Double
        } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(value); // Boolean
        } else {
            return value; // Assume String
        }
    }
    

    /**
     * Processes a single event line from the file.
     * use Map<String, TwoTuple<String, Object>> stateVariables to store the event information
     * @param line The line to be processed.
     */
    private void processEventLine(String line) {
        // Example lines:
        // Event=Bell,time=2000,rings=2
        // Event=ThermostatNight,time=0

        Map<String, String> eventMap = new HashMap<>();
        String[] parts = line.split(",");

        for (String part : parts) {
            String[] keyValue = part.split("=");

            if (keyValue.length == 2) {
                eventMap.put(keyValue[0].trim(), keyValue[1].trim());
            } else {
                System.err.println("Invalid key-value pair: " + part);
            }
        }

        // Extract event name
        if (!eventMap.containsKey("Event")) {
            System.err.println("Missing 'Event' key in line: " + line);
            return;
        }

        String eventName = eventMap.get("Event");

        // Create a list to store event properties
        List<TwoTuple<String, Object>> eventProperties = new ArrayList<>();

        // Store all key-value pairs except "Event"
        for (Map.Entry<String, String> entry : eventMap.entrySet()) {
            if (!entry.getKey().equals("Event")) {
                eventProperties.add(new TwoTuple<>(entry.getKey(), parseValue(entry.getValue())));
            }
        }

        // Store event properties in stateVariables
        synchronized (stateVariables) {
            stateVariables.put(eventName, eventProperties);
        }
        // Print stateVariables to confirm storage
        printStateVariables();
    }

}

    


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
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Calendar;


public class GreenhouseControls extends Controller implements Serializable{
    //To properly manage event execution, replace both eventMap and eventThreads with stateVariables, which is already declared
    // private final Map<Event, Integer> eventErrorCode = new HashMap<>();
    // private final Map<Event, String> eventNames = new HashMap<>();
    // private final Map<Event, String> eventDayOrNight = new HashMap<>();
    // private final Map<Event, Long> eventTimes = new HashMap<>();
    // private final Map<Event, String> eventDescriptions = new HashMap<>();
    // private final Map<Event, Boolean> eventMalfunctionIsFixed = new HashMap<>();
    // private final Map<Event, Boolean> eventIsOn = new HashMap<>(); 
    private final Map<Event, Thread> eventThreads = new ConcurrentHashMap<>();
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


    public void printStateVariables() {
        synchronized (stateVariables) {
            System.out.println("📋 Current State Variables:");
            for (Map.Entry<String, TwoTuple<String, Object>> entry : stateVariables.entrySet()) {
                System.out.println(" Event Name: " + entry.getKey());
                System.out.println("     Key: " + entry.getValue().key);
                System.out.println("     Value: " + entry.getValue().value);
            }
        }
    }

    /**
     * Gets all the events and their assigned Variables
     * @return Map<String, TwoTuple<String, Object>>
    */ 
    public static Map<String, TwoTuple<String, Object>> getStateVariables() {
        return stateVariables;
    }


    /**
     * Gets the Value Pair based on Event
     * @param eventName
     * @return TwoTuple<String, Object>
    */
    public static synchronized TwoTuple<String, Object> getEventVariable(String eventName) {
          
        return stateVariables.get(eventName);
    }
    
    
     /**
     * Retrieves error code for an event
     * @param event
     * @return int errorcode
     */
    public int getEventErrorCode(Event event) {
        TwoTuple<String, Object> errorEntry = getEventVariable(event.getClass().getSimpleName() + "_errorCode");
        return (errorEntry != null && errorEntry.value instanceof Integer) ? (int) errorEntry.value : 0;
    }

    /**
     * Retrieves the error code for a specific event from stateVariables.
     * @param event The event for which the error code is requested.
     * @return The error code as an integer. Returns 0 if the event has no error.
     */
    public int getError(Event event) {
        String key = event.getClass().getSimpleName() + "_errorCode";
        TwoTuple<String, Object> errorEntry = getEventVariable(key);

        // Check if the retrieved value exists and is an Integer
        return (errorEntry != null && errorEntry.value instanceof Integer) ? (int) errorEntry.value : 0;
    }



    /**
     * Setters
     */

    // Set a variable in the system state
    public static synchronized void setVariable(String key, Object value) {     
        stateVariables.put(key, new TwoTuple<>(key, value));
        // System.out.println("Updated variable: " + key + " = " + value); // Debugging
    }

    public void setEventErrorCode(Event event, int errorCode) {
        setVariable(event.getClass().getSimpleName() + "_errorCode", errorCode);
    }


    public void setEventThreads(Event event, Thread thread) {
        setVariable(event.getClass().getSimpleName() + "_thread", thread);
    }

    /**
     * Retrieves whether an event is on/off
     */
    public boolean getIsEvent(Event event) {
        TwoTuple<String, Object> isOnEntry = getEventVariable(event.getClass().getSimpleName() + "_isOn");
        return (isOnEntry != null && isOnEntry.value instanceof Boolean) ? (boolean) isOnEntry.value : false;
    }
    
 
    
    // Window operations
    /**
     * Checks if a window is open
     */
    public boolean getIsWindowOpen(int windowID) {
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
    // The event will be started in a new thread. 
    public void startEvent(String eventName, long delayTime, Object rings) {
        try {
            Class<?> clazz = Class.forName("tme4.events." + eventName);
            if (eventName == "Bell") {
                Constructor<?> constructor = clazz.getConstructor(GreenhouseControls.class, long.class, int.class);
                Event event = (Event) constructor.newInstance(this, delayTime, rings);
                setVariable(eventName + "_rings", rings);

                Thread eventThread = new Thread(event);
                eventThreads.put(event, eventThread);
                eventThread.start();
                } else {
                Constructor<?> constructor = clazz.getConstructor(GreenhouseControls.class, long.class);
                Event event = (Event) constructor.newInstance(this, delayTime);
                
                Thread eventThread = new Thread(event);
                eventThreads.put(event, eventThread);
                eventThread.start();
                }
    
                setVariable(eventName, "Started");
                System.out.println("Event started: " + eventName);
        } catch (Exception e) {
            System.err.println("Failed to start event: " + e.getMessage());
        }
    }
    
    public void suspendEvents() {
        eventThreads.keySet().forEach(Event::suspend);
        System.out.println("⏸️ All events suspended.");
    }
    
    public void resumeEvents() {
        eventThreads.keySet().forEach(Event::resume);
        System.out.println("▶️ All events resumed.");
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


    // Process a single line from the event file
    // use Map<String, TwoTuple<String, Object>> stateVariables to store the event information
    private void processEventLine(String line) {
        // Example line:
        // Event=ThermostatNight,time=0
        // Event=Bell,time=2000,rings=2

        String eventName = null;
        long delayTime = 0;
        int rings = 0;  // Default: No rings specified

        String key;
        Object value;

        // Map to store event properties
        // Example map:
        // String: Event, Object: Bell
        // String: time, Object: 2000
        // String: rings, Object: 2
        Map<String, Object> eventNameMap= new TwoTuple<String,Object>(key, value);
    
        String[] parts = line.split(",");
        
        // Store event information in stateVariables
        synchronized (stateVariables) {
                for (String part : parts){
                    //Example part
                    //[Event=Bell]
                    //[time=2000]
                    //[rings=2]
                    String[] keyAndValue = part.split("=");
                    
        
                // Extract Event name
                if (part.contains("Event")) {
                    eventName = (String) eventNameMap.get("Event");

                    stateVariables.put(eventName ,eventNameMap.put(keyAndValue[0], keyAndValue[1]));
                } else {
                    System.err.println("Error: Event name not found in line: " + line);
                    return;
                }

                // Extract delay time
                if (eventNameMap.containsKey("time")) {
                    delayTime = Long.parseLong((String) eventNameMap.get("time"));
                } else {
                    System.err.println("Error: Delay time not found in line: " + line);
                    return;
                }

                // Extract rings for event with rings property
                if (eventNameMap.containsKey("rings")) {
                    try {
                        rings = Integer.parseInt((String) eventNameMap.get("rings"));
                    } catch (NumberFormatException e) {
                        System.err.println("Error: Invalid rings value in line: " + line);
                        return;
                    }
                }

                stateVariables.put(eventName, new TwoTuple<>("delayTime", delayTime));

                // If the event has rings, store it as well
                if (rings != -1) {
                    stateVariables.put(eventName, new TwoTuple<>("rings", rings));
                }
            }
        }


       
        

 
        // Start the event with the extracted properties

        // startEvent(eventName, delayTime, rings);
    }

}

    


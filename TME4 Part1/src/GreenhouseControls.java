import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * The GreenhouseControls class manages and controls events in a greenhouse automation system.
 * It allows for event creation, suspension, resumption, and state restoration.
 * 
 * @author Boris Bojanov
 * @version 1.0
 */

public class GreenhouseControls {
    /** A map storing currently running event threads. */
    private final Map<Event, Thread> eventThreads = new HashMap<>();

    /** A synchronized map storing state variables of the greenhouse system. */
    private static final Map<String, TwoTuple<String, Object>> stateVariables = 
        Collections.synchronizedMap(new HashMap<>());

    /**
     * Sets a state variable in the greenhouse system.
     *
     * @param key   The name of the variable.
     * @param value The value to set.
     */
    public static synchronized void setVariable(String key, Object value) {
        stateVariables.put(key, new TwoTuple<>(key, value));
    }

    /**
     * Retrieves a state variable from the greenhouse system.
     *
     * @param key          The name of the variable.
     * @param defaultValue The default value if the variable is not found.
     * @return A TwoTuple containing the key and its value.
     */
    public static synchronized TwoTuple<String, Object> getVariable(String key, Object defaultValue) {
        return stateVariables.getOrDefault(key, new TwoTuple<>(key, defaultValue));
    }

    /**
     * Starts an event in the greenhouse system by dynamically creating an instance of the specified event class.
     *
     * @param eventName  The name of the event class to instantiate.
     * @param delayTime  The delay before the event executes.
     * @param parameters Additional parameters required by the event constructor.
     */
    public void startEvent(String eventName, long delayTime, Object... parameters) {
        System.out.println("Attempting to start event: " + eventName);
    
        try {
            Class<?> clazz = Class.forName("tme4.events." + eventName);
            Constructor<?> constructor = clazz.getConstructor(GreenhouseControls.class, long.class, Object[].class);
            Event event = (Event) constructor.newInstance(this, delayTime, parameters);
    
            setVariable(eventName, event);
            
            Thread eventThread = new Thread(event);
            setVariable(eventName + "_thread", eventThread);
            eventThread.start(); // Immediately start event as thread
            
            System.out.println(eventName + " event started.");
        } catch (Exception e) {
            System.err.println("Failed to create event: " + e.getMessage());
        }
    }

    /**
     * Suspends all currently running events in the greenhouse system.
     */
    public void suspendEvents() {
        synchronized (stateVariables) {
            stateVariables.forEach((key, value) -> {
                if (value.value instanceof Event) {
                    ((Event) value.value).suspendEvent();
                }
            });
        }
        System.out.println("All events suspended.");
    }

    /**
     * Resumes all previously suspended events in the greenhouse system.
     */
    public void resumeEvents() {
        synchronized (stateVariables) {
            stateVariables.forEach((key, value) -> {
                if (value.value instanceof Event) {
                    ((Event) value.value).resumeEvent();
                }
            });
        }
        System.out.println("All events resumed.");
    }

    /**
     * Restores a saved GreenhouseControls state from a serialized file.
     *
     * @param filename The file containing the saved state.
     * @return The restored GreenhouseControls object, or null if restoration fails.
     */
    public static GreenhouseControls restoreState(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (GreenhouseControls) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to restore state: " + e.getMessage());
            return null;
        }
    }

    /**
     * Simulates fixing a window issue in the greenhouse system.
     * Updates system variables to indicate that the issue is resolved.
     */
    public void fixWindow() {
        System.out.println("Window issue fixed.");
        setVariable("window_ok", true);
        setVariable("error", 0);
    }
    
    /**
     * Simulates restoring power to the greenhouse system.
     * Updates system variables to indicate that power is back.
     */
    public void powerOn() {
        System.out.println("Power restored.");
        setVariable("power", true);
        setVariable("error", 0);
    }

    /**
     * Runs the greenhouse system, periodically checking and executing events as needed.
     */
    public void run() {
        System.out.println("Greenhouse system is now running...");
    
        while (true) {
            try {
                // Periodically check event statuses
                synchronized (stateVariables) {
                    for (Map.Entry<String, TwoTuple<String, Object>> entry : stateVariables.entrySet()) {
                        if (entry.getValue().value instanceof Event) {
                            Event event = (Event) entry.getValue().value;
                            if (event.ready()) {
                                new Thread(event).start();
                            }
                        }
                    }
                }

                // Optional: Print system status
                System.out.println("Current system state: " + stateVariables);

                // Sleep for a short interval before checking again
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.err.println("Greenhouse system interrupted: " + e.getMessage());
            }
        }
    }

    /**
     * The main method initializes the GreenhouseControls system, starts sample events, and tests suspension and resumption.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        GreenhouseControls gc = new GreenhouseControls();
        
        gc.startEvent("LightOn", 2000);
        gc.startEvent("WaterOn", 5000);
 
        setVariable("light", true);
        setVariable("water", true);

        System.out.println("Current state:");
        System.out.println(stateVariables);

        try {
            Thread.sleep(3000);
            gc.suspendEvents();
            Thread.sleep(2000);
            gc.resumeEvents();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

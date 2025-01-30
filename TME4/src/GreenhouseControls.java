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


import tme4.*;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GreenhouseControls {
    private final Map<Event, Thread> eventThreads = new HashMap<>();

    private static final Map<String, TwoTuple<String, Object>> stateVariables = Collections.synchronizedMap(new HashMap<>());

    public static synchronized void setVariable(String key, Object value) {
        stateVariables.put(key, new TwoTuple<>(key, value));
        System.out.println("Updated variable: " + key + " = " + value);
    }

    public static synchronized Object getVariable(String key) {
        return stateVariables.get(key);
    }

     // Method to add an event by name
    public void addEvent(String eventName, long delayTime) {
        try {
            Class<?> clazz = Class.forName("tme4." + eventName);
            Constructor<?> constructor = clazz.getConstructor(GreenhouseControls.class, long.class);
            Event event = (Event) constructor.newInstance(this, delayTime);
            addEvent(event);
        } catch (Exception e) {
            System.err.println("Failed to create event: " + e.getMessage());
        }
    }

 
     // Method to add and start an event
     public void addEvent(Event event) {
         Thread thread = new Thread(event);
         eventThreads.put(event, thread);
         thread.start(); // Start the thread immediately
     }
 
     // Suspend all event threads
     public void suspendEvents() {
         eventThreads.forEach((event, thread) -> event.suspend());
         System.out.println("All events suspended.");
     }
 
     // Resume all event threads
     public void resumeEvents() {
         eventThreads.forEach((event, thread) -> event.resume());
         System.out.println("All events resumed.");
     }
 
     // Main control loop (placeholder for logic)
     public void run() {
         while (true) {
             // Event management logic here
         }
     }
 
     public static void main(String[] args) {
         GreenhouseControls gc = new GreenhouseControls();
        
         // Add events
         gc.addEvent( "LightOn",2000);
         gc.addEvent( "WaterOn",5000);
 
         setVariable("light", true);
         setVariable("water", true);
 
         System.out.println("Current state:");
         System.out.println(stateVariables);

         // Example: suspend and resume
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
 
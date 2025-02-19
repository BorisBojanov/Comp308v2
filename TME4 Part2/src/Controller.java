/**
 * A reusable framework for controlling event-based systems.
 * The Controller class manages a list of scheduled events and executes them when ready.
 * 
 * Adapted for COMP308 Java for Programmers, SCIS, Athabasca University.
 * 
 * @author Steve Leung
 * @author Boris B (modifications)
 * @version 1.0 Jan 31, 2025
 */
import java.util.*;

public class Controller {
    
    /** List to store scheduled events for execution. */
    protected List<Event> eventList = new ArrayList<>();

    /**
     * Adds an event to the event list for execution.
     *
     * @param c The event to be added.
     */
    public void addEvent(Event c) { 
        eventList.add(c); 
    }

    /**
     * Runs the controller, executing events when they are ready.
     * Events are checked in a loop and executed once their conditions are met.
     * Executed events are removed from the list.
     */
    public void run() {
        System.out.println(" Controller started running events...");
        while (!eventList.isEmpty()) {
            for (Event e : new ArrayList<>(eventList)) {
                if (e.ready()) {
                    System.out.println(" Executing event: " + e);
                    e.action();
                    eventList.remove(e);
                    System.out.println(" Event executed: " + e);
                }
            }
        }
    }
} 

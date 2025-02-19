import java.util.*;

/**
 * The Controller class manages multiple event threads.
 * It can start, stop, and monitor scheduled events within the system.
 * 
 * @author Boris Bojanov
 * @version 1.0
 */
public class Controller {
    /** A synchronized map holding event threads. */
    protected final Map<Event, Thread> eventThreads = Collections.synchronizedMap(new HashMap<>());

    /**
     * Adds an event to the system and starts it in a new thread.
     *
     * @param e The event to be added and executed.
     */
    public void addEvent(Event e) {
        Thread eventThread = new Thread(e);
        eventThreads.put(e, eventThread);
        eventThread.start();
    }

    /**
     * Continuously monitors and removes completed event threads.
     */
    public void run() {
        while (!eventThreads.isEmpty()) {
            synchronized (eventThreads) {
                Iterator<Map.Entry<Event, Thread>> iterator = eventThreads.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<Event, Thread> entry = iterator.next();
                    Thread t = entry.getValue();

                    if (!t.isAlive()) { 
                        iterator.remove();
                    }
                }
            }
        }
    }

    /**
     * Suspends all running events in the system.
     */
    public void suspendEvents() {
        synchronized (eventThreads) {
            eventThreads.forEach((event, thread) -> event.suspendEvent());
        }
        System.out.println("All events suspended.");
    }

    /**
     * Resumes all previously suspended events.
     */
    public void resumeEvents() {
        synchronized (eventThreads) {
            eventThreads.forEach((event, thread) -> event.resumeEvent());
        }
        System.out.println("All events resumed.");
    }
}

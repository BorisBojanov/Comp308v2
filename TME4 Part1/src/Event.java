/**
 * Abstract class representing an event within the GreenhouseControl system.
 * Events execute their actions after a predefined delay and can be suspended or resumed.
 * Implements Runnable to allow execution in separate threads.
 * 
 * @author Boris Bojanov
 * @version 1.0
 */
public abstract class Event implements Runnable {
    /** Reference to the greenhouse control system managing the event. */
    protected final GreenhouseControls greenhouse;
    
    /** The time at which the event is scheduled to execute. */
    protected long eventTime;
    
    /** The delay time before execution. */
    protected final long delayTime;
    
    /** Controls the running state of the event thread. */
    private volatile boolean running = true; 
    
    /** Lock object used for wait/notify operations. */
    private final Object lock = new Object(); 

    /**
     * Constructs an Event with a specified delay time.
     *
     * @param greenhouse The greenhouse control system managing the event.
     * @param delayTime  The delay before the event executes.
     */
    public Event(GreenhouseControls greenhouse, long delayTime) {
        this.greenhouse = greenhouse;
        this.delayTime = delayTime;
        this.eventTime = System.currentTimeMillis() + delayTime;
    }

    /**
     * Sets a variable in the greenhouse control system.
     *
     * @param key   The key of the variable.
     * @param value The value to set.
     */
    @SuppressWarnings("static-access")
    protected void setVariable(String key, Object value) {
        greenhouse.setVariable(key, value);
    }

    /**
     * Checks if the event is ready to execute based on the scheduled time.
     *
     * @return True if the current time is past the event time, otherwise false.
     */
    public boolean ready() {
        return System.currentTimeMillis() >= eventTime;
    }

    /**
     * Runs the event thread, waiting until its execution time arrives.
     * Handles suspension and resumption of the event as needed.
     */
    @Override
    public void run() {
        try {
            long waitTime = eventTime - System.currentTimeMillis();
            if (waitTime > 0) {
                Thread.sleep(waitTime);
            }
            synchronized (lock) {
                while (!running) { 
                    lock.wait(); // Thread waits here when suspended
                }
            }
            action(); // Perform the event's action
        } catch (InterruptedException e) {
            System.err.println(" Event interrupted: " + e.getMessage());
        }
    }

    /**
     * Defines the specific action of the event, to be implemented by subclasses.
     */
    public abstract void action();

    /**
     * Suspends the event, preventing it from executing until resumed.
     */
    public void suspendEvent() { 
        synchronized (lock) {
            running = false; // Correct suspension behavior
        }
    }

    /**
     * Resumes a previously suspended event, allowing it to proceed.
     */
    public void resumeEvent() { 
        synchronized (lock) {
            running = true;
            lock.notify(); // Correct way to wake up the thread
        }
    }
}

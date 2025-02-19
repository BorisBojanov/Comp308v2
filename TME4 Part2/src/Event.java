/**
 * Abstract base class for all events in the GreenhouseControls system.
 * Events execute actions after a specified delay and can be suspended or resumed.
 *
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public abstract class Event implements Runnable {

    /** The GreenhouseControls instance associated with this event. */
    protected final GreenhouseControls greenhouse;

    /** The scheduled event time in milliseconds. */
    private long eventTime;

    /** The delay time before executing the event. */
    protected final long delayTime;

    /** Indicates whether the event is currently running. */
    private volatile boolean running = true;

    /** Lock object for thread synchronization. */
    private final Object lock = new Object();

    /** Event listener for notifying when an event is triggered. */
    private static EventListener eventListener;

    /**
     * Constructs an event with the specified delay time.
     *
     * @param greenhouse The GreenhouseControls instance managing the event.
     * @param delayTime The delay in milliseconds before execution.
     */
    public Event(GreenhouseControls greenhouse, long delayTime) {
        this.greenhouse = greenhouse;
        this.delayTime = delayTime;
    }

    /**
     * Sets the event listener for notifying event triggers.
     *
     * @param listener The event listener to be set.
     */
    public static void setEventListener(EventListener listener) {
        System.out.println("Event listener set: " + listener);
        eventListener = listener;
    }

    /**
     * Starts the event by scheduling it for execution.
     */
    public void start() {
        eventTime = System.currentTimeMillis() + delayTime;
        new Thread(this).start();
    }

    /**
     * Checks if the event is ready to be executed.
     *
     * @return true if the event time has passed, false otherwise.
     */
    public boolean ready() {
        return System.currentTimeMillis() >= eventTime;
    }

    /**
     * Runs the event, waits for the delay, and executes the action.
     * Notifies the event listener upon completion.
     */
    @Override
    public void run() {
        try {
            Thread.sleep(delayTime);
            synchronized (lock) {
                while (!running) {
                    lock.wait();
                }
            }
            action();

            if (eventListener != null) {
                eventListener.onEventTriggered(getClass().getSimpleName());
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Executes the event-specific action.
     * Must be implemented by subclasses.
     */
    public abstract void action();

    /**
     * Suspends the execution of the event.
     */
    public void suspend() {
        synchronized (lock) {
            running = false;
        }
    }

    /**
     * Resumes the execution of a suspended event.
     */
    public void resume() {
        synchronized (lock) {
            running = true;
            lock.notify();
        }
    }
}

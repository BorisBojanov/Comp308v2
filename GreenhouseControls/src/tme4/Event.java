package tme4;

import javax.swing.SwingUtilities;

public abstract class Event implements Runnable {

    protected final GreenhouseControls greenhouse;
    private long eventTime;
    protected final long delayTime;
    private volatile boolean running = true;
    private final Object lock = new Object();

    // Constructor
    public Event(GreenhouseControls greenhouse, long delayTime) {
        this.greenhouse = greenhouse;
        this.delayTime = delayTime;
        // Remove this line, let GreenhouseControls handle event execution
        // start();  
    }

    //Define a callback mechanism so that each event can send messages to a GUI panel
    // Add a listener for GUI output
    private static EventListener eventListener;

    // Interface for event updates (GUI will implement this)
    public interface EventListener {
        void onEventTriggered(String eventDescription);
    }

    public static void setEventListener(EventListener listener) {
        System.out.println("✅ Event listener set: " + listener);
        eventListener = listener;
    }


    protected void setVariable(String key, Object value) {
        GreenhouseControls.setVariable(key, value);
    }

    public void start() {
        eventTime = System.currentTimeMillis() + delayTime;
        new Thread(this).start();  // Automatically start thread
    }

    public boolean ready() {
        return System.currentTimeMillis() >= eventTime;
    }

    @Override
    public void run() {
        try {
            long waitTime = eventTime - System.currentTimeMillis();
            System.out.println("⏳ Waiting " + waitTime + "ms for event: " + this);
    
            if (waitTime > 0) {
                Thread.sleep(waitTime);
            }
    
            synchronized (lock) {
                while (!running) {
                    System.out.println("⏸️ Event suspended: " + this);
                    lock.wait();
                }
            }
    
            action(); // Execute event action
            System.out.println("✅ Event executed: " + this);
    
            // GUI Update
            if (eventListener != null) {
                SwingUtilities.invokeLater(() -> {
                    eventListener.onEventTriggered(getClass().getSimpleName() + " executed");
                });
            }
    
        } catch (InterruptedException e) {
            System.err.println("❌ Event interrupted: " + e.getMessage());
        }
    }


    public abstract void action();

    // Suspend thread
    public void suspend() {
        running = false;
    }

    // Resume thread
    public void resume() {
        synchronized (lock) {
            running = true;
            lock.notify();
        }
    }

}

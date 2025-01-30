



public abstract class Event implements Runnable {
    private final GreenhouseControls greenhouse;
    private long eventTime;
    protected final long delayTime;
    private volatile boolean running = true; // Controls thread state
    private final Object lock = new Object(); // Used for wait/notify

    public Event(GreenhouseControls greenhouse, long delayTime) {
        this.greenhouse = greenhouse;
        this.delayTime = delayTime;
    }

    protected void setVariable(String key, Object value) {
        greenhouse.setVariable(key, value);
    }


    public void start() {
        eventTime = System.currentTimeMillis() + delayTime;
    }

    public boolean ready() {
        return System.currentTimeMillis() >= eventTime;
    }

    @Override
    public void run() {
        try {
            long waitTime = eventTime - System.currentTimeMillis();
            if (waitTime > 0) {
                Thread.sleep(waitTime);
            }
            synchronized (lock) {
                while (!running) { // Pause if running is false
                    lock.wait();
                }
            }
            action(); // Perform the event's action
        } catch (InterruptedException e) {
            System.err.println("Event interrupted: " + e.getMessage());
        }
    }

    public abstract void action();

    // Suspend the thread
    public void suspend() {
        running = false;
    }

    // Resume the thread
    public void resume() {
        synchronized (lock) {
            running = true;
            lock.notify(); // Wake up the thread
        }
    }
}

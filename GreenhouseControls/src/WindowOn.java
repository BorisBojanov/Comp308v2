

public class WindowOn extends Event {
    private final int windowID;

    public WindowOn(GreenhouseControls greenhouse, long delayTime, int windowID) {
        super(greenhouse, delayTime);
        this.windowID = windowID;
    }

    @Override
    public void action() {
        boolean success = greenhouse.openWindow(windowID);
        if (success) {
            System.out.println("✅ Window " + windowID + " is now open.");
        } else {
            System.out.println("❌ Failed to open Window " + windowID + ".");
        }
    }

    @Override
    public String toString() {
        return "Window " + windowID + " is now open";
    }
}

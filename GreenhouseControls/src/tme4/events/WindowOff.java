package tme4.events;

import tme4.*;

public class WindowOff extends Event {
    private final int windowID;

    public WindowOff(GreenhouseControls greenhouse, long delayTime, int windowID) {
        super(greenhouse, delayTime);
        this.windowID = windowID;
    }

    @Override
    public void action() {
        boolean success = greenhouse.closeWindow(windowID);
        if (success) {
            System.out.println("❌ Window " + windowID + " is now closed.");
        } else {
            System.out.println("⚠️ Window " + windowID + " was already closed.");
        }
    }

    @Override
    public String toString() {
        return "Window " + windowID + " is now closed";
    }
}

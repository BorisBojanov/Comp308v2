/**
 * The WindowOn event opens a specified greenhouse window.
 * Ensures the window is properly opened and updates the system state.
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public class WindowOn extends Event {

    /** The ID of the window to be opened. */
    private final int windowID;

    /**
     * Constructs a WindowOn event with a specified delay.
     *
     * @param greenhouse The GreenhouseControls instance managing the event.
     * @param delayTime The delay before opening the window.
     * @param windowID The ID of the window to be opened.
     */
    public WindowOn(GreenhouseControls greenhouse, long delayTime, int windowID) {
        super(greenhouse, delayTime);
        this.windowID = windowID;
    }

    /**
     * Executes the action of opening the window.
     * Prints status messages indicating success or failure.
     */
    @Override
    public void action() {
        boolean success = greenhouse.openWindow(windowID);
        if (success) {
            System.out.println("Window " + windowID + " is now open.");
        } else {
            System.out.println("Failed to open Window " + windowID + ".");
        }
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string indicating the WindowOn event.
     */
    @Override
    public String toString() {
        return "Window " + windowID + " is now open";
    }
}

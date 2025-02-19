/**
 * The WindowOff event closes a specified greenhouse window.
 * Ensures the window is properly closed and updates the system state.
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public class WindowOff extends Event {

    /** The ID of the window to be closed. */
    private final int windowID;

    /**
     * Constructs a WindowOff event with a specified delay.
     *
     * @param greenhouse The GreenhouseControls instance managing the event.
     * @param delayTime The delay before closing the window.
     * @param windowID The ID of the window to be closed.
     */
    public WindowOff(GreenhouseControls greenhouse, long delayTime, int windowID) {
        super(greenhouse, delayTime);
        this.windowID = windowID;
    }

    /**
     * Executes the action of closing the window.
     * Prints status messages indicating success or failure.
     */
    @Override
    public void action() {
        boolean success = greenhouse.closeWindow(windowID);
        if (success) {
            System.out.println("Window " + windowID + " is now closed.");
        } else {
            System.out.println("Window " + windowID + " was already closed.");
        }
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string indicating the WindowOff event.
     */
    @Override
    public String toString() {
        return "Window " + windowID + " is now closed";
    }
}

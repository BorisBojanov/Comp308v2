/**
 * Represents an event that turns on the greenhouse fans.
 * Updates the system state variables accordingly.
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public class FansOn extends Event {

    /**
     * Constructs a FansOn event with a specified delay.
     *
     * @param greenhouse The GreenhouseControls instance managing the event.
     * @param delayTime The delay before the fans turn on.
     */
    public FansOn(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the action of turning on the fans.
     * Updates the system variables to reflect the change.
     */
    @Override
    public void action() {
        GreenhouseControls.setVariable("FansOn", "status", true);
        GreenhouseControls.setVariable("FansOn", "message", "Fans are on.");
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string indicating the FansOn event.
     */
    @Override
    public String toString() {
        return "FansOn event triggered";
    }
}

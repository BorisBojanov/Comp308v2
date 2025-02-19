/**
 * Represents an event that turns off the greenhouse fans.
 * Updates the system state variables accordingly.
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public class FansOff extends Event {

    /**
     * Constructs a FansOff event with a specified delay.
     *
     * @param greenhouse The GreenhouseControls instance managing the event.
     * @param delayTime The delay before the fans turn off.
     */
    public FansOff(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the action of turning off the fans.
     * Updates the system variables to reflect the change.
     */
    @Override
    public void action() {
        GreenhouseControls.setVariable("FansOff", "status", false);
        GreenhouseControls.setVariable("FansOff", "message", "Fans are off.");
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string indicating the FansOff event.
     */
    @Override
    public String toString() {
        return "FansOff event triggered";
    }
}

/**
 * Represents an event that turns off the greenhouse light.
 * Updates the system state variables accordingly.
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public class LightOff extends Event {

    /**
     * Constructs a LightOff event with a specified delay.
     *
     * @param greenhouse The GreenhouseControls instance managing the event.
     * @param delayTime The delay before the light turns off.
     */
    public LightOff(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the action of turning off the light.
     * Updates the system variables to reflect the change.
     */
    @Override
    public void action() {
        GreenhouseControls.setVariable("LightOff", "status", false);
        GreenhouseControls.setVariable("LightOff", "message", "Light is off.");
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string indicating the LightOff event.
     */
    @Override
    public String toString() {
        return "LightOff event";
    }
}

/**
 * Represents an event that turns on the greenhouse light.
 * Updates the system state variables accordingly.
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public class LightOn extends Event {

    /**
     * Constructs a LightOn event with a specified delay.
     *
     * @param greenhouse The GreenhouseControls instance managing the event.
     * @param delayTime The delay before the light turns on.
     */
    public LightOn(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the action of turning on the light.
     * Updates the system variables to reflect the change.
     */
    @Override
    public void action() {
        GreenhouseControls.setVariable("LightOn", "status", true);
        GreenhouseControls.setVariable("LightOn", "message", "Light is on.");
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string indicating the LightOn event.
     */
    @Override
    public String toString() {
        return "LightOn event";
    }
}

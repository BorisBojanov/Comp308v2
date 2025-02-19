/**
 * The WaterOff event turns off the greenhouse water supply.
 * Updates the system state variables accordingly.
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public class WaterOff extends Event {

    /**
     * Constructs a WaterOff event with a specified delay.
     *
     * @param greenhouse The GreenhouseControls instance managing the event.
     * @param delayTime The delay before turning the water off.
     */
    public WaterOff(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the action of turning off the water.
     * Updates the system variables to reflect the change.
     */
    @Override
    public void action() {
        GreenhouseControls.setVariable("WaterOff", "status", false);
        GreenhouseControls.setVariable("WaterOff", "message", "Water is off.");
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string indicating the WaterOff event.
     */
    @Override
    public String toString() {
        return "WaterOff event";
    }
}

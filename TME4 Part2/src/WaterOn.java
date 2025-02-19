/**
 * The WaterOn event turns on the greenhouse water supply.
 * Updates the system state variables accordingly.
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public class WaterOn extends Event {

    /**
     * Constructs a WaterOn event with a specified delay.
     *
     * @param greenhouse The GreenhouseControls instance managing the event.
     * @param delayTime The delay before turning the water on.
     */
    public WaterOn(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the action of turning on the water.
     * Updates the system variables to reflect the change.
     */
    @Override
    public void action() {
        GreenhouseControls.setVariable("WaterOn", "status", true);
        GreenhouseControls.setVariable("WaterOn", "message", "Water is on.");
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string indicating the WaterOn event.
     */
    @Override
    public String toString() {
        return "WaterOn event";
    }
}

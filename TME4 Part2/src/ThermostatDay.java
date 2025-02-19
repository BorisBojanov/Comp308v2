/**
 * The ThermostatDay event sets the thermostat to "Day" mode.
 * It updates the system state variables accordingly.
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public class ThermostatDay extends Event {

    /**
     * Constructs a ThermostatDay event with a specified delay.
     *
     * @param greenhouse The GreenhouseControls instance managing the event.
     * @param delayTime The delay before switching to day mode.
     */
    public ThermostatDay(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the action of switching the thermostat to "Day" mode.
     * Updates the system variables to reflect the change.
     */
    @Override
    public void action() {
        GreenhouseControls.setVariable("ThermostatDay", "status", "Day");
        GreenhouseControls.setVariable("ThermostatDay", "message", "Thermostat set to day mode.");
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string indicating the ThermostatDay event.
     */
    @Override
    public String toString() {
        return "ThermostatDay event triggered";
    }
}

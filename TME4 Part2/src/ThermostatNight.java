/**
 * The ThermostatNight event sets the thermostat to "Night" mode.
 * It updates the system state variables accordingly.
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public class ThermostatNight extends Event {

    /**
     * Constructs a ThermostatNight event with a specified delay.
     *
     * @param greenhouse The GreenhouseControls instance managing the event.
     * @param delayTime The delay before switching to night mode.
     */
    public ThermostatNight(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the action of switching the thermostat to "Night" mode.
     * Updates the system variables to reflect the change.
     */
    @Override
    public void action() {
        GreenhouseControls.setVariable("ThermostatNight", "status", "Night");
        GreenhouseControls.setVariable("ThermostatNight", "message", "Thermostat set to night mode.");
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string indicating the ThermostatNight event.
     */
    @Override
    public String toString() {
        return "ThermostatNight event triggered";
    }
}

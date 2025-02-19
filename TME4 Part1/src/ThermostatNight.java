/**
 * Represents an event that sets the thermostat to "Night" mode in the greenhouse control system.
 * This event updates the greenhouse state by setting the "thermostat" variable to "Night".
 * 
 * @author Boris Bojanov
 * @version 1.0
 */
public class ThermostatNight extends Event {

    /**
     * Constructs a ThermostatNight event.
     *
     * @param greenhouse The greenhouse control system managing the event.
     * @param delayTime  The delay before the event executes.
     */
    public ThermostatNight(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the ThermostatNight event action by setting the thermostat to "Night" mode.
     */
    @Override
    public void action() {
        System.out.println("Thermostat is now set to Night mode.");
        greenhouse.setVariable("thermostat", "Night");
    }

    /**
     * Returns a string representation of the ThermostatNight event.
     *
     * @return A message indicating that the ThermostatNight event was triggered.
     */
    @Override
    public String toString() {
        return "ThermostatNight event triggered";
    }
}

/**
 * Represents an event that sets the thermostat to "Day" mode in the greenhouse control system.
 * This event updates the greenhouse state by setting the "thermostat" variable to "Day".
 * 
 * @author Boris Bojanov
 * @version 1.0
 */
public class ThermostatDay extends Event {

    /**
     * Constructs a ThermostatDay event.
     *
     * @param greenhouse The greenhouse control system managing the event.
     * @param delayTime  The delay before the event executes.
     */
    public ThermostatDay(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the ThermostatDay event action by setting the thermostat to "Day" mode.
     */
    @Override
    public void action() {
        System.out.println("Thermostat is now set to Day mode.");
        greenhouse.setVariable("thermostat", "Day");
    }

    /**
     * Returns a string representation of the ThermostatDay event.
     *
     * @return A message indicating that the ThermostatDay event was triggered.
     */
    @Override
    public String toString() {
        return "ThermostatDay event triggered";
    }
}

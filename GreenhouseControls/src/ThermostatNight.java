

// ThermostatNight: Sets the thermostat to "Night" mode
public class ThermostatNight extends Event {
    public ThermostatNight(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    @Override
    public void action() {
        // Use correct setVariable() method with event name and property key
        GreenhouseControls.setVariable("ThermostatNight", "status", "Night");
        GreenhouseControls.setVariable("ThermostatNight", "message", "Thermostat set to night mode.");
    }

    @Override
    public String toString() {
        return "ThermostatNight event triggered";
    }
}
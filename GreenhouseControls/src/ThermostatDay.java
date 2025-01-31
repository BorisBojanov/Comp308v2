

// ThermostatDay: Sets the thermostat to "Day" mode
public class ThermostatDay extends Event {
    public ThermostatDay(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    @Override
    public void action() {
        // Use correct setVariable() method with event name and property key
        GreenhouseControls.setVariable("ThermostatDay", "status", "Day");
        GreenhouseControls.setVariable("ThermostatDay", "message", "Thermostat set to day mode.");
    }

    @Override
    public String toString() {
        return "ThermostatDay event triggered";
    }
}
package tme4.events;


import tme4.*;

// ThermostatDay: Sets the thermostat to "Day" mode
public class ThermostatDay extends Event {
    public ThermostatDay(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    @Override
    public void action() {
        setVariable("Thremostat", false);
        System.out.println("Thermostat is now set to Day mode.");
    }

    @Override
    public String toString() {
        return "ThermostatDay event triggered";
    }
}
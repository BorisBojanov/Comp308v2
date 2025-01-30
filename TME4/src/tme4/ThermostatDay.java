package tme4;

import Event;

// ThermostatDay: Sets the thermostat to "Day" mode
public class ThermostatDay extends Event {
    public ThermostatDay(long delayTime) {
        super(delayTime);
    }

    @Override
    public void action() {
        System.out.println("Thermostat is now set to Day mode.");
    }

    @Override
    public String toString() {
        return "ThermostatDay event triggered";
    }
}
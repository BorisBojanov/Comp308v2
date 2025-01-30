package tme4;

import Event;

// ThermostatNight: Sets the thermostat to "Night" mode
public class ThermostatNight extends Event {
    public ThermostatNight(long delayTime) {
        super(delayTime);
    }

    @Override
    public void action() {
        System.out.println("Thermostat is now set to Night mode.");
    }

    @Override
    public String toString() {
        return "ThermostatNight event triggered";
    }
}
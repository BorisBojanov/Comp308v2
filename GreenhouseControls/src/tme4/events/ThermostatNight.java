package tme4.events;


import tme4.*;

// ThermostatNight: Sets the thermostat to "Night" mode
public class ThermostatNight extends Event {
    public ThermostatNight(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    @Override
    public void action() {
        GreenhouseControls.setVariable("Thermostat", true);
        System.out.println("Thermostat is now set to Night mode.");
    }

    @Override
    public String toString() {
        return "ThermostatNight event triggered";
    }
}
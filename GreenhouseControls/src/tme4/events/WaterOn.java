package tme4.events;


import tme4.*;

public class WaterOn extends Event {
    public WaterOn(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    @Override
    public void action() {
        // Use correct setVariable() method with event name and property key
        GreenhouseControls.setVariable("WaterOn", "status", true);
        GreenhouseControls.setVariable("WaterOn", "message", "Water is on.");
    }

    @Override
    public String toString() {
        return "WaterOn event";
    }
}

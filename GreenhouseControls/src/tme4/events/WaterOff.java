package tme4.events;


import tme4.*;

public class WaterOff extends Event {
    public WaterOff(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    @Override
    public void action() {
        // Use correct setVariable() method with event name and property key
        GreenhouseControls.setVariable("WaterOff", "status", false);
        GreenhouseControls.setVariable("WaterOff", "message", "Water is off.");
    }

    @Override
    public String toString() {
        return "WaterOff event";
    }
}

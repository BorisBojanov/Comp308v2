package tme4.events;


import tme4.*;

public class WaterOff extends Event {
    public WaterOff(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    @Override
    public void action() {
        setVariable("Water", false);
        System.out.println("Water is off.");
    }

    @Override
    public String toString() {
        return "WaterOff event";
    }
}

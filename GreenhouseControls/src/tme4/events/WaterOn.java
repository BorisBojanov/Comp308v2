package tme4.events;


import tme4.*;

public class WaterOn extends Event {
    public WaterOn(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    @Override
    public void action() {
        GreenhouseControls.setVariable("Water", true);
        System.out.println("Water is on.");
    }

    @Override
    public String toString() {
        return "WaterOn event";
    }
}

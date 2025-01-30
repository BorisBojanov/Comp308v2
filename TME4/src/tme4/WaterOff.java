package tme4;

import Event;

public class WaterOff extends Event {
    public WaterOff(long delayTime) {
        super(delayTime);
    }

    @Override
    public void action() {
        System.out.println("Water is off.");
    }

    @Override
    public String toString() {
        return "WaterOff event";
    }
}

package tme4;

import Event;

public class WaterOn extends Event {
    public WaterOn(long delayTime) {
        super(delayTime);
    }

    @Override
    public void action() {
        System.out.println("Water is on.");
    }

    @Override
    public String toString() {
        return "WaterOn event";
    }
}

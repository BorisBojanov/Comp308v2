package tme4;

import Event;

public class LightOff extends Event {
    public LightOff(long delayTime) {
        super(delayTime);
    }

    @Override
    public void action() {
        System.out.println("Light is off.");
    }

    @Override
    public String toString() {
        return "LightOff event";
    }
}

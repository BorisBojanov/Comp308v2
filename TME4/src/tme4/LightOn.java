package tme4;

import Event;

public class LightOn extends Event{
    public LightOn(long delayTime) {
        super(delayTime);
    }

    @Override
    public void action() {
        System.out.println("Light is on.");
    }
    
    @Override
    public String toString() {
        return "LightOn event";
    }
}

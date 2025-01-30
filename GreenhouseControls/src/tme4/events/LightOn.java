package tme4.events;


import tme4.*;

public class LightOn extends Event{
    public LightOn(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    @Override
    public void action() {
        GreenhouseControls.setVariable("Light", true);
        System.out.println("Light is on.");
    }
    
    @Override
    public String toString() {
        return "LightOn event";
    }
}

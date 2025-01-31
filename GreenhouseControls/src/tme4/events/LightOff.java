package tme4.events;


import tme4.*;

public class LightOff extends Event {
    public LightOff(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    @Override
    public void action() {
        GreenhouseControls.setVariable("LightOff", "status", false);
        GreenhouseControls.setVariable("LightOff", "message", "Light is off.");
    }

    @Override
    public String toString() {
        return "LightOff event";
    }
}

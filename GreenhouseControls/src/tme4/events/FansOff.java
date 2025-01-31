package tme4.events;


import tme4.*;

// FansOff: Turns the fans off
public class FansOff extends Event {

    public FansOff(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse,delayTime);
    }

    @Override
    public void action() {
        // Use correct setVariable() method with event name and property key
        GreenhouseControls.setVariable("FansOff", "status", false);
        GreenhouseControls.setVariable("FansOff", "message", "Fans are off.");
    }

    @Override
    public String toString() {
        return "FansOff event triggered";
    }
}
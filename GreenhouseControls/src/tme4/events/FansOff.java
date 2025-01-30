package tme4.events;


import tme4.*;

// FansOff: Turns the fans off
public class FansOff extends Event {

    public FansOff(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse,delayTime);
    }

    @Override
    public void action() {
        setVariable("Fans", false);
        System.out.println("Fans are now off.");
    }

    @Override
    public String toString() {
        return "FansOff event triggered";
    }
}
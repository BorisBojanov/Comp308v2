package tme4;

import Event;

// FansOff: Turns the fans off
public class FansOff extends Event {
    public FansOff(long delayTime) {
        super(delayTime);
    }

    @Override
    public void action() {
        System.out.println("Fans are now off.");
    }

    @Override
    public String toString() {
        return "FansOff event triggered";
    }
}
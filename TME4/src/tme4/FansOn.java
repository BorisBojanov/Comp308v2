package tme4;

import Event;

// FansOn: Turns the fans on
public class FansOn extends Event {
    public FansOn(long delayTime) {
        super(delayTime);
    }

    @Override
    public void action() {
        GreenhouseControls.setVariable("water", false);
        System.out.println("Water is off.");
    }

    @Override
    public String toString() {
        return "FansOn event triggered";
    }
}
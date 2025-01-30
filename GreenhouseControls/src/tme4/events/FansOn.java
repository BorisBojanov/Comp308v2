package tme4.events;


import tme4.*;

// FansOn: Turns the fans on
public class FansOn extends Event {
    public FansOn(GreenhouseControls greenhouse ,long delayTime) {
        super(greenhouse, delayTime);
    }

    @Override
    public void action() {
        setVariable("Fans", true);
        System.out.println("Fans are now On.");
    }

    @Override
    public String toString() {
        return "FansOn event triggered";
    }
}
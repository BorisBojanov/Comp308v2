

// FansOn: Turns the fans on
public class FansOn extends Event {
    public FansOn(GreenhouseControls greenhouse ,long delayTime) {
        super(greenhouse, delayTime);
    }

    @Override
    public void action() {
        // Use correct setVariable() method with event name and property key
        GreenhouseControls.setVariable("FansOn", "status", true);
        GreenhouseControls.setVariable("FansOn", "message", "Fans are on.");
    }

    @Override
    public String toString() {
        return "FansOn event triggered";
    }
}
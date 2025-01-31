

public class LightOn extends Event{
    public LightOn(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    @Override
    public void action() {
        GreenhouseControls.setVariable("LightOn", "status", true);
        GreenhouseControls.setVariable("LightOn", "message", "Light is on.");
    }
    
    @Override
    public String toString() {
        return "LightOn event";
    }
}

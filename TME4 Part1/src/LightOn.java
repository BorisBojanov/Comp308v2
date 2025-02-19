/**
 * Represents an event that turns the light on in the greenhouse control system.
 * This event updates the greenhouse state by setting the "light" variable to true.
 * 
 * @author Boris Bojanov
 * @version 1.0
 */
public class LightOn extends Event {

    /**
     * Constructs a LightOn event.
     *
     * @param greenhouse The greenhouse control system managing the event.
     * @param delayTime  The delay before the event executes.
     */
    public LightOn(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the LightOn event action by turning the light on.
     */
    @Override
    public void action() {
        System.out.println("Light is on.");
        greenhouse.setVariable("light", true);
    }

    /**
     * Returns a string representation of the LightOn event.
     *
     * @return A message indicating that the LightOn event was triggered.
     */
    @Override
    public String toString() {
        return "LightOn event";
    }
}

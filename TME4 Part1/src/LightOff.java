/**
 * Represents an event that turns the light off in the greenhouse control system.
 * This event updates the greenhouse state by setting the "light" variable to false.
 * 
 * @author Boris Bojanov
 * @version 1.0
 */
public class LightOff extends Event {

    /**
     * Constructs a LightOff event.
     *
     * @param greenhouse The greenhouse control system managing the event.
     * @param delayTime  The delay before the event executes.
     */
    public LightOff(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the LightOff event action by turning the light off.
     */
    @Override
    public void action() {
        System.out.println("Light is off.");
        greenhouse.setVariable("light", false);
    }

    /**
     * Returns a string representation of the LightOff event.
     *
     * @return A message indicating that the LightOff event was triggered.
     */
    @Override
    public String toString() {
        return "LightOff event";
    }
}

/**
 * Represents an event that turns the water on in the greenhouse control system.
 * This event updates the greenhouse state by setting the "water" variable to true.
 * 
 * @author Boris Bojanov
 * @version 1.0
 */
public class WaterOn extends Event {

    /**
     * Constructs a WaterOn event.
     *
     * @param greenhouse The greenhouse control system managing the event.
     * @param delayTime  The delay before the event executes.
     */
    public WaterOn(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the WaterOn event action by turning the water on.
     */
    @Override
    public void action() {
        System.out.println("Water is on.");
        greenhouse.setVariable("water", true);
    }

    /**
     * Returns a string representation of the WaterOn event.
     *
     * @return A message indicating that the WaterOn event was triggered.
     */
    @Override
    public String toString() {
        return "WaterOn event";
    }
}

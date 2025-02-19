/**
 * Represents an event that turns the water off in the greenhouse control system.
 * This event updates the greenhouse state by setting the "water" variable to false.
 * 
 * @author Boris Bojanov
 * @version 1.0
 */
public class WaterOff extends Event {

    /**
     * Constructs a WaterOff event.
     *
     * @param greenhouse The greenhouse control system managing the event.
     * @param delayTime  The delay before the event executes.
     */
    public WaterOff(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the WaterOff event action by turning the water off.
     */
    @Override
    public void action() {
        System.out.println("Water is off.");
        greenhouse.setVariable("water", false);
    }

    /**
     * Returns a string representation of the WaterOff event.
     *
     * @return A message indicating that the WaterOff event was triggered.
     */
    @Override
    public String toString() {
        return "WaterOff event";
    }
}

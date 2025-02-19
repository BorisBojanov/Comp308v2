/**
 * Represents an event that turns the fans off in the greenhouse control system.
 * This event updates the greenhouse state by setting the "fans" variable to false.
 * 
 * @author Boris Bojanov
 * @version 1.0
 */
public class FansOff extends Event {

    /**
     * Constructs a FansOff event.
     *
     * @param greenhouse The greenhouse control system managing the event.
     * @param delayTime  The delay before the event executes.
     */
    public FansOff(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the FansOff event action by turning the fans off.
     */
    @Override
    public void action() {
        System.out.println("Fans are now off.");
        greenhouse.setVariable("fans", false);
    }

    /**
     * Returns a string representation of the FansOff event.
     *
     * @return A message indicating that the FansOff event was triggered.
     */
    @Override
    public String toString() {
        return "FansOff event triggered";
    }
}

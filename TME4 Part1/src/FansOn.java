/**
 * Represents an event that turns the fans on in the greenhouse control system.
 * This event updates the greenhouse state by setting the "fans" variable to true.
 * 
 * @author Boris Bojanov
 * @version 1.0
 */
public class FansOn extends Event {

    /**
     * Constructs a FansOn event.
     *
     * @param greenhouse The greenhouse control system managing the event.
     * @param delayTime  The delay before the event executes.
     */
    public FansOn(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
    }

    /**
     * Executes the FansOn event action by turning the fans on.
     */
    @Override
    public void action() {
        greenhouse.setVariable("fans", true);
        System.out.println("Fans are now on.");
    }

    /**
     * Returns a string representation of the FansOn event.
     *
     * @return A message indicating that the FansOn event was triggered.
     */
    @Override
    public String toString() {
        return "FansOn event triggered";
    }
}

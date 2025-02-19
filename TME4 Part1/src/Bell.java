/**
 * Represents a Bell event that rings a specified number of times.
 * This event is scheduled and executed within the GreenhouseControl system.
 * 
 * @author Boris Bojanov
 * @version 1.0
 */
public class Bell extends Event {
    /** Number of rings remaining for the bell event. */
    private int rings;

    /**
     * Constructs a Bell event.
     *
     * @param greenhouse The greenhouse control system managing the event.
     * @param delayTime  The delay before the bell starts ringing.
     * @param rings      The number of times the bell should ring.
     */
    public Bell(GreenhouseControls greenhouse, long delayTime, int rings) {
        super(greenhouse, delayTime);
        this.rings = rings;
    }

    /**
     * Executes the Bell event action by ringing the bell and scheduling the next ring.
     */
    @Override
    public void action() {
        if (rings > 0) {
            System.out.println("Bing!");
            rings--;
            // Create a new Bell event with updated rings and schedule it
            Bell newBell = new Bell(greenhouse, 2000, rings);
            Thread newThread = new Thread(newBell);
            newThread.start(); 
        }
    }

    /**
     * Returns a string representation of the Bell event.
     *
     * @return A string indicating the number of rings remaining.
     */
    @Override
    public String toString() {
        return "Bell event with " + rings + " rings remaining";
    }
}

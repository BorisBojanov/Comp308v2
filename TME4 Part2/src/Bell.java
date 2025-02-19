/**
 * Represents a Bell event in the Greenhouse Controls system.
 * The Bell event rings a certain number of times at specified intervals.
 *
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public class Bell extends Event {
    
    /** Number of times the bell should ring. */
    public int rings;

    /**
     * Creates a Bell event that rings once.
     *
     * @param greenhouse The GreenhouseControls instance managing the event.
     * @param delayTime The delay before the bell rings.
     */
    public Bell(GreenhouseControls greenhouse, long delayTime) {
        super(greenhouse, delayTime);
        rings = 1;
    }

    /**
     * Creates a Bell event with a specified number of rings.
     *
     * @param greenhouse The GreenhouseControls instance managing the event.
     * @param delayTime The delay before the first ring.
     * @param rings The number of times the bell should ring.
     */
    public Bell(GreenhouseControls greenhouse, long delayTime, int rings) {
        super(greenhouse, delayTime);
        this.rings = rings;
    }

    /**
     * Executes the bell ringing action.
     * Rings once and schedules another Bell event if additional rings are needed.
     */
    @Override
    public void action() {
        if (rings > 0) {
            System.out.println("Bing!");
            rings--;
            if (rings > 0) {
                Bell nextBell = new Bell(greenhouse, delayTime, rings);
                greenhouse.addEvent(nextBell);
            }
        }
    }

    /**
     * Returns a string representation of the Bell event.
     *
     * @return A string indicating the number of remaining rings.
     */
    @Override
    public String toString() {
        return "Bell Event - Remaining Rings: " + rings;
    }
}

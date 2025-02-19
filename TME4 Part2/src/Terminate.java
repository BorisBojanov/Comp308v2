/**
 * The Terminate event shuts down the Greenhouse system immediately upon execution.
 * This event is irreversible and stops all system operations.
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public class Terminate extends Event {

    /**
     * Constructs a Terminate event that stops the system after a delay.
     *
     * @param greenhouse The GreenhouseControls instance managing the event.
     * @param delayTime The delay before termination.
     */
    public Terminate(GreenhouseControls greenhouse, long delayTime) { 
        super(greenhouse, delayTime); 
    }

    /**
     * Executes the termination of the Greenhouse system.
     * The program exits immediately.
     */
    @Override
    public void action() { 
        System.exit(0); 
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string indicating the Terminate event.
     */
    @Override
    public String toString() { 
        return "Terminating";  
    }
}
